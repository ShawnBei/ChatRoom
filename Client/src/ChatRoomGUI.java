
import java.awt.*;
import javax.swing.*;

public class ChatRoomGUI {
	private static JFrame chatRoomFrame;
	
	static JScrollPane chatHistoryBoxPanel;
	static JPanel functions;
	static JPanel chatBoxPanel;
	static JScrollPane credentialListPanel;
	static JPanel sendPanel;


	public static void main(String[] args) {
		chatRoomUI();
	}

	protected static void chatRoomUI() {
		chatRoomFrame = new JFrame("CN5120 Chat Room");
		chatRoomFrame.setLayout(null);
		chatRoomFrame.setBounds(200, 200, 700, 700);
		
		

		chatHistoryBoxPanel = new JScrollPane();
		chatHistoryBoxPanel.setLayout(null);
		chatHistoryBoxPanel.setBounds(0, 0, 500, 400);
		JTextArea chatHistoryArea = new JTextArea("chatHistoryBox");
		chatHistoryArea.setBounds(0,0,500,400);
		chatHistoryArea.setEditable(false);
		chatHistoryBoxPanel.add(chatHistoryArea);
		
		
		
		functions = new JPanel();
		functions.setLayout(null);
		functions.setBounds(0, 400, 500, 100);
		
		chatBoxPanel = new JPanel();
		chatBoxPanel.setLayout(null);
		chatBoxPanel.setBounds(0, 500, 500, 200);
		JTextArea chatBoxTextArea = new JTextArea();
		chatBoxTextArea.setBounds(0,0,500,200);
		chatBoxTextArea.setEditable(true);
		chatBoxPanel.add(chatBoxTextArea);
		
		credentialListPanel = new JScrollPane();
		credentialListPanel.setLayout(null);
		credentialListPanel.setBounds(500, 0, 200, 400);
		JTextArea credentialListArea = new JTextArea("credentialListPanel");
		credentialListArea.setBounds(0,0,200,400);
		credentialListArea.setEditable(false);
		credentialListPanel.add(credentialListArea);
		
		sendPanel = new JPanel();
		sendPanel.setLayout(null);
		sendPanel.setBounds(500, 400, 200, 300);
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(0,0,200,300);	
		sendPanel.add(sendButton);
		
		chatRoomFrame.add(chatHistoryBoxPanel);
		chatRoomFrame.add(functions);
		chatRoomFrame.add(chatBoxPanel);
		chatRoomFrame.add(credentialListPanel);
		chatRoomFrame.add(sendPanel);
		
		chatRoomFrame.setVisible(true);
		chatRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
