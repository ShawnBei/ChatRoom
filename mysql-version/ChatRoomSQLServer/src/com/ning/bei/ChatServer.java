package com.ning.bei;
import java.net.*;
import java.sql.*;

public class ChatServer {

	// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/chatRoom?useSSL=false&serverTimezone=UTC";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "root";

	public static void main(String[] args) {
		try {

			// connect
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			UserList allusers = new UserList();
			OnlineUserList onlineUsers = new OnlineUserList();

			Statement st = conn.createStatement();
			String query = "SELECT * FROM USERS";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("USERNAME");
				allusers.getUserList().add(username);
			}
			while (true) {
				ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

				Socket clientSocket = null;
				System.out.println("Waiting for connection.....");
				clientSocket = serverSocket.accept();
				System.out.println("Connection successful");
				Thread client = new ServerThread(clientSocket, conn, allusers, onlineUsers);
				client.start();
				serverSocket.close();
			}
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
