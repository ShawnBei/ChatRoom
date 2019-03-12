import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

public class ServerThread extends Thread {

	Socket clientSocket;
	String command = "";
	String username = "";
	String password = "";
	String nickname = "";
	String gender = ""; // false-male, true-female
	String query = "";
	Connection conn;
	String inputLine = "";
	OnlineUserList onlineUsers = new OnlineUserList();
	UserList allUsers = new UserList();

	public ServerThread(Socket s, Connection a, UserList c, OnlineUserList d) {
		clientSocket = s;
		conn = a;
		allUsers = c;
		onlineUsers = d;
	}

	public void run() {
		try {
			boolean flag = true;
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while (flag) {
				command = in.readLine();
				if (command.equals("#LOGIN")) {
					login();
					break;
				}
				else if (command.equals("#REGISTER"))
					register();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void register() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			username = in.readLine();
			password = in.readLine();
			nickname = in.readLine();
			gender = in.readLine();

			Statement st = conn.createStatement();
			query = "SELECT * FROM USERS WHERE USERNAME = '" + username + "';";
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				out.println("#REGISTERFAIL");
				System.out.println(username + " register fail");
			} else {
				query = "INSERT INTO USERS VALUES('FALSE','" + username + "','" + BCrypt.hashpw(password, BCrypt.gensalt()) + "', 'GROUP', '" + nickname + "', '" + gender + "');";
				st.executeUpdate(query);
				out.println("#REGISTERSUCCESS");
				System.out.println(username + " register success");

				// update file folder
				updateFolders(username);

				// add to user list
				allUsers.getUserList().add(username);

				// update online users' user list
				letOtherPeopleKnowIAmOff();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			username = in.readLine();
			password = in.readLine();
			Statement st = conn.createStatement();
			query = "SELECT * FROM USERS WHERE userName = '" + username + "';";
			ResultSet rs = st.executeQuery(query);

			// if user exists
			if (rs.next()) {
				if (BCrypt.checkpw(password, rs.getString("password"))) {
					query = "UPDATE USERS SET STATUS='TRUE' WHERE USERNAME='" + username + "';";
					st.executeUpdate(query);
					out.println("#LOGINSUCCESS");
					Credentials newUser = new Credentials(clientSocket, username);

					// update user list in online users
					letOtherPeopleKnowIAmUp();
					
					if(!(onlineUsers.getOnlineList().contains(newUser)))
						onlineUsers.addUser(newUser);
					System.out.println(username + " login success");

					// sent all users and their status to user
					sendUserList();
					out.println("#END");

					// send personal unread (usernames) to main page
					checkPersonalUnreadMessage();
					out.println("#END");
					System.out.println("user list sent");

					// check group unread message and send to main page
					//checkGroupUnreadMessage();
					//System.out.println("group unread message sent");

					// start group chat and personal chat threads
					GroupChatThread gct = new GroupChatThread(newUser, conn, allUsers, onlineUsers, nickname);
					gct.start();
					PairChatThread pct = new PairChatThread(newUser, conn, allUsers, onlineUsers,nickname);
					pct.start();

					// if user leave main page
					inputLine = in.readLine();
					while (inputLine != null) {
						if (inputLine.equals("#QUIT")) {

							// remove user from online user list
							onlineUsers.removeUser(newUser);
							query = "UPDATE USERS SET STATUS='FALSE' WHERE USERNAME='" + username + "';";
							st.executeUpdate(query);
							letOtherPeopleKnowIAmOff();
							gct.interrupt();
							pct.interrupt();
							gct.join();
							pct.join();
							System.out.println(username + " exits");
							break;
						}
						else if (inputLine.startsWith("#G")) {
							gct.getInputString(inputLine);
						}
						else if (inputLine.startsWith("#P")) {
							pct.getInputString(inputLine);
						}
						/*else {
							File d = new File("files/groupFiles/" + 1);
							PrintWriter pw = new PrintWriter(new FileWriter(d),true);
							pw.println(inputLine);
							pw.flush();
							}*/
						inputLine = in.readLine();
					}
				} else {
					out.println("#WRONGPASSWORD");
					System.out.println(username + " login fail: wrong password");
				}
			} else {
				out.println("#NOUSER");
				System.out.println(username + " login fail: not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// sent all users and their status to user
	public void sendUserList() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			Statement st = conn.createStatement();
			String query = "SELECT * FROM USERS;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String usernameSent = rs.getString("USERNAME");
				String nickname = rs.getString("NICKNAME");
				String status = rs.getString("STATUS");
				String gender = rs.getString("GENDER");
				out.println(usernameSent);
				out.println(nickname);
				out.println(gender);
				out.println(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// check group unread message and send to main page
	public void checkGroupUnreadMessage() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			Statement st = conn.createStatement();
			String query = "SELECT * FROM RECORDS WHERE STATUS = 'FALSE' AND PERSONAL = 'FALSE' AND RECEIVER = '"
					+ username + "';";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String time = rs.getString("TIME");
				String message = rs.getString("MESSAGE");
				String sender = rs.getString("SENDER");
				out.println("#GMESSAGE" + time + "   " +  sender + " : " + message);
				sleep(200);
			}
			query = "UPDATE RECORDS SET STATUS='TRUE' WHERE PERSONAL = 'FALSE' AND RECEIVER = '"+ username + "';";
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// send personal unread (usernames) to main page
	public void checkPersonalUnreadMessage() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			Statement st = conn.createStatement();
			String query = "SELECT * FROM RECORDS WHERE STATUS = 'FALSE' AND PERSONAL = 'FALSE' AND RECEIVER = '" + username
					+ "';";
			ResultSet rs = st.executeQuery(query);
			ArrayList<String> senderList = new ArrayList<String>();
			while (rs.next()) {
				String sender = rs.getString("SENDER");
				if (!senderList.contains(sender)) {
					senderList.add(sender);
					out.println("#1"+sender);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// update user list in online users
	public void letOtherPeopleKnowIAmUp() {
		try {
			for (Credentials eachUser : onlineUsers.getOnlineList()) {
				PrintWriter pw = new PrintWriter(eachUser.getSocket().getOutputStream(), true);
				pw.println("#2" + username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// update user list in online users
	public void letOtherPeopleKnowIAmOff() {
		try {
			for (Credentials eachUser : onlineUsers.getOnlineList()) {
				PrintWriter pw = new PrintWriter(eachUser.getSocket().getOutputStream(), true);
				pw.println("#3" + username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// update file folder
	public void updateFolders(String newUserIn) {
		try {
			File d = new File("files/personalFiles/" + newUserIn);
			d.mkdir();
			for (String eachUser : allUsers.getUserList()) {
				d = new File("files/personalFiles/" + newUserIn + "/" + eachUser); // add old users to new folder
				d.mkdir();
				d = new File("files/personalFiles/" + eachUser + "/" + newUserIn); // add new user to old folders
				d.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
