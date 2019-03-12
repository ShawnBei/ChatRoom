import java.net.*;
import java.sql.*;

public class ChatServer {

	public static void main(String[] args) {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
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
				ServerSocket serverSocket = new ServerSocket(20666);

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
