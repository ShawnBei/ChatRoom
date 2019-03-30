import java.net.*;
import java.sql.*;

public class ChatServer {

	public static void main(String[] args) {
		try {

			//connect h2 database to server
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

			//create userlists: alluser list and oline user list
			UserList allusers = new UserList();
			OnlineUserList onlineUsers = new OnlineUserList();

			//add all users in database into alluser list
			Statement st = conn.createStatement();
			String query = "SELECT * FROM USERS";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("USERNAME");
				allusers.getUserList().add(username);
			}

			//create sockets for clients to connect
			while (true) {
				ServerSocket serverSocket = new ServerSocket(20666); //server socket port set to 20666

				Socket clientSocket = null;
				System.out.println("Waiting for connection.....");
				clientSocket = serverSocket.accept(); //server will stop here and listen for connection
				System.out.println("Connection successful");

				//create a new ServerThread and send connection/access of database/alluser list/online userlist to this thread
				Thread client = new ServerThread(clientSocket, conn, allusers, onlineUsers); 
				client.start(); //start thread
				serverSocket.close(); //close this server socket
			}
		} catch (Exception e) { //catch any error
			System.err.println(e);
			System.exit(1);
		} 
	}
}
