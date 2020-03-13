package com.ning.bei;
import java.net.*;

public class Credentials {
	private Socket clientSocket = null;
	private String username = "";

	public Credentials(Socket s, String n) {
		clientSocket = s;
		username = n;
	}

	public String getUsername() {
		return username;
	}

	public Socket getSocket() {
		return clientSocket;
	}
}