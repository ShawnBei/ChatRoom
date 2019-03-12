import java.util.*;

public class OnlineUserList {
	ArrayList<Credentials> onlineUsers = new ArrayList<Credentials>();

	public synchronized void addUser(Credentials newUser) {
		onlineUsers.add(newUser);
	}

	public synchronized void removeUser(Credentials newUser) {
		onlineUsers.remove(newUser);
	}
	
	public ArrayList<Credentials> getOnlineList() {
		return onlineUsers;
	}
}