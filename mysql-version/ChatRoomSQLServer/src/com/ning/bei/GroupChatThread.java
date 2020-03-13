package com.ning.bei;
import java.io.*;
import java.util.Date;
import java.sql.*;

public class GroupChatThread extends Thread {

	String inputLine = "";
	boolean flag = false;
	String filename = "";
	Credentials newUser;
	OnlineUserList onlineUsers = new OnlineUserList();
	Connection conn;
	UserList allUsers = new UserList();
	String nickname = "";
	String downloadFileName = "";

	public GroupChatThread(Credentials newUserIn, Connection connIn, UserList allUserIn, OnlineUserList onlineUserIn,
			String nicknameIn) {
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
					if (inputLine.startsWith("#GMESSAGE")) {
						Date now = new Date();

						inputLine = inputLine.substring(9);
						System.out.println(now.toString() + " " + newUser.getUsername() + "(" + nickname + ")" + " : "
								+ inputLine);

						// save to DateBase as unread message
						for (String receiver : allUsers.getUserList()) {
							String query = "INSERT INTO RECORDS VALUES ('FALSE', '" + newUser.getUsername() + "', '"
									+ receiver + "', '" + inputLine + "', '" + now.toString() + "', 'FALSE');";
							st.executeUpdate(query);
						}

						// send to online users
						for (Credentials eachUser : onlineUsers.getOnlineList()) {
							PrintWriter pw = new PrintWriter(eachUser.getSocket().getOutputStream(), true);
							pw.println("#GMESSAGE" + now.toString() + "   " + nickname + " : " + inputLine);
							pw.flush();

							// change unread to read in database
							String query = "UPDATE RECORDS SET STATUS='TRUE' WHERE RECEIVER='" + eachUser.getUsername()
									+ "' AND SENDER = '" + newUser.getUsername()
									+ "' AND PERSONAL ='FALSE' AND TIME = '" + now.toString() + "';";
							st.executeUpdate(query);
						}
					}

					// if user wants to see group records
					else if (inputLine.startsWith("#GRECORDS"))
						sendGroupRecords();

					else if (inputLine.startsWith("#GREVIEWFILES"))
						sendGroupFileList();

					// user downloads file
					else if (inputLine.startsWith("#GDOWNLOADFILENAME")) {
						downloadFileName = inputLine.substring(18);
						downloadGroupFile();
					}

					// user uploads file
					else if (inputLine.startsWith("#GFILENAME")) {
						for (int i = inputLine.length() - 1; i >= 0; i--) {
							if (inputLine.charAt(i) == '/')
								filename = inputLine.substring(i + 1);
						}
					} 
					else if (inputLine.startsWith("#GSENDINGFILE"))
						receiveGroupFile(inputLine);

					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendGroupRecords() {
		try {
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);
			Statement st = conn.createStatement();
			String query = "SELECT * FROM RECORDS WHERE PERSONAL = 'FALSE' AND RECEIVER = '" + newUser.getUsername()
					+ "';";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String sender = rs.getString("SENDER");
				String time = rs.getString("TIME");
				String message = rs.getString("MESSAGE");
				out.println("#GRECORDS" + time + "   " + sender + " : " + message);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void sendGroupFileList() {
		try {
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);
			File d = new File("files/groupFiles/");
			out.println("#GREVIEWFILES");
			String[] list = d.list();
			for (String s : list) {
				if (!s.startsWith("."))
					out.println(s);
			}
			out.println("#END");
			System.out.println("group file list sent");
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void downloadGroupFile() {
		try {
			PrintWriter out = new PrintWriter(newUser.getSocket().getOutputStream(), true);
			out.println("#GDOWNLOADFILE" + downloadFileName);
			String inputLine = "";
			File d = new File("files/groupFiles/" + downloadFileName);
			BufferedReader br = new BufferedReader(new FileReader(d));
			inputLine = br.readLine();
			while (inputLine != null) {
				out.println(inputLine);
				inputLine = br.readLine();
			}
			br.close();
			out.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void receiveGroupFile(String inputLine) {
		try {
			File d = new File("files/groupFiles/" + filename);
			PrintWriter pw = new PrintWriter(new FileWriter(d), true);
			pw.println(inputLine.substring(13));
			pw.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}