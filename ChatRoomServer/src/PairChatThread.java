import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Date;

public class PairChatThread extends Thread {

	String inputLine = "";
	boolean flag = false;
	String target = "";
	String receivedFileName = "";
	Credentials newUser;
	OnlineUserList onlineUsers;
	Connection conn;
	UserList allUsers = new UserList();
	String nickname = "";

	public PairChatThread(Credentials newUserIn, Connection connIn, UserList allUserIn, OnlineUserList onlineUserIn, String nicknameIn) {
		newUser = newUserIn;
		conn = connIn;
		allUsers = allUserIn;
		onlineUsers = onlineUserIn;
		nickname = nicknameIn;
	}

	public void getInputString(String a) {
		inputLine = a;
		flag = true;
	}

	@SuppressWarnings("static-access")
	public void run() {
		try {
			Statement st = conn.createStatement();
			while (!(Thread.currentThread().interrupted())) {
				if (flag) {
					if (inputLine.startsWith("#PU")) {

						// get target username
						target = inputLine.substring(3);

						// 修改数据库 FLOW(target username)
						String query = "UPDATE USERS SET FLOW = '" + target + "' WHERE USERNAME='"
								+ newUser.getUsername() + "';";
						st.executeUpdate(query);

						// check unread message and send to client
						//checkUnreadMessage(target);
						//out.println("#END");
					} else if (inputLine.startsWith("#PMESSAGE")) {
						PrintWriter outMe = new PrintWriter(newUser.getSocket().getOutputStream(), true);

						Date now = new Date();

						System.out.println(now.toString() + ", " + newUser.getUsername() + " to " + target +" : " + inputLine.substring(9));
						
						// save into database as unread
						String query = "INSERT INTO RECORDS VALUES ('FALSE', '" + newUser.getUsername() + "', '"
								+ target + "', '" + inputLine.substring(9) + "', '" + now.toString() + "','TRUE');";
						st.executeUpdate(query);
						
						// get target's socket
						Socket you = null;
						PrintWriter outYou = null;
						for (Credentials a : onlineUsers.getOnlineList()) {
							if (a.getUsername().equals(target)) {
								you = a.getSocket();
								outYou = new PrintWriter(you.getOutputStream(), true);
								
								// check target's flow
								query = "SELECT * FROM USERS WHERE USERNAME = '" + target + "';";
								ResultSet rs = st.executeQuery(query);
								String flow = "";
								String status = "FALSE";
								while (rs.next()) {
									flow = rs.getString("FLOW");
									status = rs.getString("STATUS");
								}

								// if target opens pair chat window towards this user, then send message
								if (newUser.getUsername().equals(flow)) {
									
									outYou.println("#PMESSAGE" + now.toString() + "   " + nickname + " : "
											+ inputLine.substring(9));

									// change unread to read in database
									query = "UPDATE RECORDS SET STATUS = 'TRUE' WHERE SENDER='" + newUser.getUsername()
											+ "' AND RECEIVER = '" + target + "' AND TIME ='" + now.toString()
											+ "' AND PERSONAL = 'TRUE';";
									st.executeUpdate(query);
								}

								// if target is online but not open window towards this user
								else if (status.equals("TRUE")) {
									outYou.println("#1" + newUser.getUsername());
								}
							}
						}
						outMe.println("#PMESSAGE" + now.toString() + "   "  + nickname + " : "
								+ inputLine.substring(9));
						
					}
					// if user leaves pair chat page
					else if (inputLine.startsWith("#PEXIT")) {

						// change this user's FLOW to GROUP
						String query = "UPDATE USERS SET FLOW = 'GROUP' WHERE USERNAME='" + newUser.getUsername()
								+ "';";
						st.executeUpdate(query);
					}

					// review records
					else if (inputLine.equals("#PRECORDS"))
						sendPairRecords();

					else if (inputLine.equals("#PREVIEWFILES"))
						sendPersonalFileList();

					// download file
					else if (inputLine.equals("#PFILENAME"))
						downloadPersonalFile(inputLine);

					// receive file from this user
					else if (inputLine.equals("#PSENDFILENAME")) {
						for (int i = inputLine.length() - 1; i >= 0; i--) {
							if (inputLine.charAt(i) == '/') {
								receivedFileName = inputLine.substring(i + 1);
							}
						}
					} else if (inputLine.equals("#PSENDINGFILE"))
						receivePersonalFile(inputLine);
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// check unread message and send to client
	public void checkUnreadMessage(String target) {
		try {
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);
			Statement st = conn.createStatement();
			String query = "SELECT * FROM RECORDS WHERE STATUS = 'FALSE' AND PERSONAL = 'TRUE' AND RECEIVER = '"
					+ newUser.getUsername() + "' AND SENDER = '" + target + "';";
			ResultSet rs = st.executeQuery(query);

			// if unread message exist
			while (rs.next()) {
				String sender = rs.getString("SENDER");
				String time = rs.getString("TIME");
				String message = rs.getString("MESSAGE");
				out.println("#PUNREADMESSAGE" + time + "   " + sender + " : " + message);
				query = "UPDATE RECORDS SET STATUS='TRUE' WHERE RECEIVER='" + newUser.getUsername() + "' AND SENDER = '"
						+ target + "' AND PERSONAL ='TRUE' AND TIME = '" + time + "';";
				st.executeUpdate(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// review records
	public void sendPairRecords() {
		try {
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);
			Statement st = conn.createStatement();
			String query = "SELECT * FROM RECORDS WHERE PERSONAL = 'TRUE' AND RECEIVER = '" + newUser.getUsername()
					+ "' AND SENDER = '" + target + "';";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String sender = rs.getString("SENDER");
				String time = rs.getString("TIME");
				String message = rs.getString("MESSAGE");
				out.println("#PRECORDS" + time + "   " + sender + " : " + message);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// review files
	public void sendPersonalFileList() {
		try {
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);

			// send file list in this target's folder
			File d = new File("files/personalFiles/" + target + "/" + newUser.getUsername());
			out.println("#PREVIEWFILES");
			String[] list = d.list();
			for (String s : list) {
				if (!s.startsWith("."))
					out.println(s);
			}
			System.out.println("personal file list sent");
			out.println("#END");
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// download file
	public void downloadPersonalFile(String inputLine) {
		try {
			BufferedReader br = null;
			String fromFile = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(newUser.getSocket().getInputStream()));
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);
			inputLine = inputLine.substring(18);
			File d = new File("files/personalFiles/" + target + "/" + newUser.getUsername() + "/" + inputLine);
			br = new BufferedReader(new FileReader(d));
			fromFile = br.readLine();
			while (fromFile != null) {
				out.println(fromFile);
				fromFile = br.readLine();
			}
			br.close();
			System.out.println("file list sent");
			in.close();
			out.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// receive file from this user
	public void receivePersonalFile(String inputLine) {
		try {
			File d = new File("files/personalFiles/" + newUser.getUsername() + "/" + target + "/" + receivedFileName);
			PrintWriter pw = new PrintWriter(new FileWriter(d),true);
			pw.println(inputLine.substring(13));
			pw.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
