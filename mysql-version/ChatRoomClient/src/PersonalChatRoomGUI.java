import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;


public class PersonalChatRoomGUI {

	private static JFrame chatRoomFrame;
	private static JSplitPane hSplitPane;
	private static JSplitPane vLeftSplitPane;
	private static JSplitPane vRightSplitPane;
	private static JSplitPane vLeftBottomSplitPane;
	private static JSplitPane vFunctionSplitPane;
	private static JScrollPane chatHistoryBoxPanel;
	private static JScrollPane chatBoxPanel;
	private static JPanel showPanel;
	private static JPanel sendPanel;
	private static JPanel functionPanel;
	
	private static JFrame emojiFrame;
	private static JPanel emojiPanel;
	
	

	
	public static void main(String[] args) {
        chatRoomGUI();
	}

	public static void chatRoomGUI() {
		chatRoomFrame = new JFrame("Chat Room");
		chatRoomFrame.setBounds(100, 100, 700, 700);
		chatRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		hSplitPane = new JSplitPane();
		hSplitPane.setDividerLocation(500);
		hSplitPane.setDividerSize(0);
		hSplitPane.setContinuousLayout(true);

		vLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vLeftSplitPane.setDividerLocation(380);
		vLeftSplitPane.setDividerSize(0);
		
		vLeftBottomSplitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vLeftBottomSplitPane.setDividerLocation(50);
		vLeftBottomSplitPane.setDividerSize(0);
		
		vRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vRightSplitPane.setDividerLocation(590);
		vRightSplitPane.setDividerSize(0);
		vRightSplitPane.setContinuousLayout(true);
		vRightSplitPane.setOneTouchExpandable(true);
		
		
		JTextArea chatHistoryArea = new JTextArea("chatHistoryBox");
		chatHistoryArea.setSize(500,380);
		chatHistoryArea.setEditable(false);
		chatHistoryBoxPanel = new JScrollPane(chatHistoryArea);
		chatHistoryBoxPanel.setSize(500,380);
		chatHistoryBoxPanel.setBackground(Color.PINK);
		
		
		JTextArea chatBoxTextArea = new JTextArea();
		chatBoxTextArea.setSize(500,270);
		chatBoxTextArea.setLineWrap(true);
		chatBoxTextArea.setEditable(true);
		chatBoxPanel = new JScrollPane(chatBoxTextArea);
		chatBoxPanel.setSize(500,300);
		

		showPanel = new JPanel();
		showPanel.setLayout(null);                                                                 
		showPanel.setBounds(500, 0, 200, 590);
		JButton showButton=new JButton();
		showButton.setBounds(0,0,200,590);
		ImageIcon show=new ImageIcon("show.JPG");
		showButton.setIcon(show);
		showPanel.add(showButton);
	
		
		sendPanel = new JPanel();
		sendPanel.setLayout(null);
		sendPanel.setBounds(500, 590, 200, 50);
		JButton sendButton = new JButton();
		sendButton.setBounds(0,0,175,50);
		ImageIcon send=new ImageIcon("send.JPG");
		sendButton.setIcon(send);
		sendPanel.add(sendButton);
		
		functionPanel=new JPanel();
		functionPanel.setLayout(null);
		functionPanel.setBounds(0, 400, 500, 50);
		
		JButton emojiButton=new JButton("emoji");
	
		emojiButton.setBounds(5, 0, 50, 50);
		ImageIcon emoji=new ImageIcon("emoji.jpg");
		emojiButton.setIcon(emoji); 
		emojiButton.setActionCommand("Emoji");
		emojiButton.addActionListener(new ButtonClickListener());

		JButton voiceButton=new JButton("voice");
		voiceButton.setBounds(65, 0, 50, 50);
		ImageIcon voice=new ImageIcon("voice.JPG");
		voiceButton.setIcon(voice); 

		
		JButton imageButton=new JButton("image");
		imageButton.setBounds(125, 0, 50, 50);
		ImageIcon image=new ImageIcon("image.JPG");
		imageButton.setIcon(image); 
		
		JButton fileDownloadButton=new JButton("fileDowload");
		fileDownloadButton.setBounds(185, 0, 50, 50);
		ImageIcon fileDownload=new ImageIcon("fileDownload.JPG");
		fileDownloadButton.setIcon(fileDownload); 
		
		JButton fileTransferButton=new JButton("fileTransfer");
		fileTransferButton.setBounds(245, 0, 50, 50);
		ImageIcon fileTransfer=new ImageIcon("fileTransfer.JPG");
		fileTransferButton.setIcon(fileTransfer);
		
		JButton historyButton=new JButton("history");
		historyButton.setBounds(305, 0, 50, 50);
		ImageIcon history=new ImageIcon("history.JPG");
		historyButton.setIcon(history); 
		
		functionPanel.add(emojiButton);
		functionPanel.add(voiceButton);
		functionPanel.add(imageButton);
		functionPanel.add(fileDownloadButton);
		functionPanel.add(fileTransferButton);
		functionPanel.add(historyButton);
		functionPanel.setBackground(Color.PINK);
		
		
	
		
		vLeftBottomSplitPane.setTopComponent(functionPanel);
		vLeftBottomSplitPane.setBottomComponent(chatBoxPanel);
		
		vLeftSplitPane.setTopComponent(chatHistoryBoxPanel);
		vLeftSplitPane.setBottomComponent(vLeftBottomSplitPane);
		vRightSplitPane.setTopComponent(showPanel);
		vRightSplitPane.setBottomComponent(sendPanel);
		

		hSplitPane.setLeftComponent(vLeftSplitPane);
		hSplitPane.setRightComponent(vRightSplitPane);
		

		chatRoomFrame.add(hSplitPane, BorderLayout.CENTER);
		chatRoomFrame.setVisible(true);

	}

	public static void emojiGUI() {
		emojiFrame = new JFrame("Emoji");
		emojiFrame.setBounds(100, 330, 300, 200);
		
		emojiPanel=new JPanel();
		emojiPanel.setLayout(null);
		emojiPanel.setBounds(100, 330, 300, 200);
		
		JButton emojiButton0=new JButton("emoji0");
		emojiButton0.setBounds(4, 0, 30, 30);
		ImageIcon emoji0=new ImageIcon("emoji0.jpg");
		emojiButton0.setIcon(emoji0);
		
		JButton emojiButton1=new JButton("emoji1");
		emojiButton1.setBounds(38, 0, 30, 30);
		ImageIcon emoji1=new ImageIcon("emoji1.jpg");
		emojiButton1.setIcon(emoji1);
		
		JButton emojiButton2=new JButton("emoji2");
		emojiButton2.setBounds(72, 0, 30, 30);
		ImageIcon emoji2=new ImageIcon("emoji2.jpg");
		emojiButton2.setIcon(emoji2);
		
		JButton emojiButton3=new JButton("emoji3");
		emojiButton3.setBounds(106, 0, 30, 30);
		ImageIcon emoji3=new ImageIcon("emoji3.jpg");
		emojiButton3.setIcon(emoji3);
		
		JButton emojiButton4=new JButton("emoji4");
		emojiButton4.setBounds(140, 0, 30, 30);
		ImageIcon emoji4=new ImageIcon("emoji4.jpg");
		emojiButton4.setIcon(emoji4);
		
		JButton emojiButton5=new JButton("emoji5");
		emojiButton5.setBounds(174, 0, 30, 30);
		ImageIcon emoji5=new ImageIcon("emoji5.jpg");
		emojiButton5.setIcon(emoji5);
		
		JButton emojiButton6=new JButton("emoji6");
		emojiButton6.setBounds(208, 0, 30, 30);
		ImageIcon emoji6=new ImageIcon("emoji6.jpg");
		emojiButton6.setIcon(emoji6);
		
		JButton emojiButton7=new JButton("emoji7");
		emojiButton7.setBounds(242, 0, 30, 30);
		ImageIcon emoji7=new ImageIcon("emoji7.jpg");
		emojiButton7.setIcon(emoji7);
		
		JButton emojiButton8=new JButton("emoji8");
		emojiButton8.setBounds(4, 37, 30, 30);
		ImageIcon emoji8=new ImageIcon("emoji8.jpg");
		emojiButton8.setIcon(emoji8);
		
		JButton emojiButton9=new JButton("emoji9");
		emojiButton9.setBounds(38, 37, 30, 30);
		ImageIcon emoji9=new ImageIcon("emoji9.jpg");
		emojiButton9.setIcon(emoji9);
		
		JButton emojiButton10=new JButton("emoji10");
		emojiButton10.setBounds(72, 37, 30, 30);
		ImageIcon emoji10=new ImageIcon("emoji10.jpg");
		emojiButton10.setIcon(emoji10);
		
		JButton emojiButton11=new JButton("emoji11");
		emojiButton11.setBounds(106, 37, 30, 30);
		ImageIcon emoji11=new ImageIcon("emoji11.jpg");
		emojiButton11.setIcon(emoji11);
		
		JButton emojiButton12=new JButton("emoji12");
		emojiButton12.setBounds(140, 37, 30, 30);
		ImageIcon emoji12=new ImageIcon("emoji12.jpg");
		emojiButton12.setIcon(emoji12);
		
		JButton emojiButton13=new JButton("emoji13");
		emojiButton13.setBounds(174, 37, 30, 30);
		ImageIcon emoji13=new ImageIcon("emoji13.jpg");
		emojiButton13.setIcon(emoji13);
		
		JButton emojiButton14=new JButton("emoji14");
		emojiButton14.setBounds(208, 37, 30, 30);
		ImageIcon emoji14=new ImageIcon("emoji14.jpg");
		emojiButton14.setIcon(emoji14);
		
		JButton emojiButton15=new JButton("emoji15");
		emojiButton15.setBounds(242, 37, 30, 30);
		ImageIcon emoji15=new ImageIcon("emoji15.jpg");
		emojiButton15.setIcon(emoji15);
		
		JButton emojiButton16=new JButton("emoji16");
		emojiButton16.setBounds(4, 74, 30, 30);
		ImageIcon emoji16=new ImageIcon("emoji16.jpg");
		emojiButton16.setIcon(emoji16);
		
		JButton emojiButton17=new JButton("emoji17");
		emojiButton17.setBounds(38, 74, 30, 30);
		ImageIcon emoji17=new ImageIcon("emoji17.jpg");
		emojiButton17.setIcon(emoji17);
		
		JButton emojiButton18=new JButton("emoji18");
		emojiButton18.setBounds(72, 74, 30, 30);
		ImageIcon emoji18=new ImageIcon("emoji18.jpg");
		emojiButton18.setIcon(emoji18);
		
		JButton emojiButton19=new JButton("emoji19");
		emojiButton19.setBounds(106, 74, 30, 30);
		ImageIcon emoji19=new ImageIcon("emoji19.jpg");
		emojiButton19.setIcon(emoji19);
		
		JButton emojiButton20=new JButton("emoji20");
		emojiButton20.setBounds(140, 74, 30, 30);
		ImageIcon emoji20=new ImageIcon("emoji20.jpg");
		emojiButton20.setIcon(emoji20);
		
		JButton emojiButton21=new JButton("emoji21");
		emojiButton21.setBounds(174, 74, 30, 30);
		ImageIcon emoji21=new ImageIcon("emoji21.jpg");
		emojiButton21.setIcon(emoji21);
		
		JButton emojiButton22=new JButton("emoji22");
		emojiButton22.setBounds(208, 74, 30, 30);
		ImageIcon emoji22=new ImageIcon("emoji22.jpg");
		emojiButton22.setIcon(emoji22);
		
		JButton emojiButton23=new JButton("emoji23");
		emojiButton23.setBounds(242, 74, 30, 30);
		ImageIcon emoji23=new ImageIcon("emoji23.jpg");
		emojiButton23.setIcon(emoji23);
		
		JButton emojiButton24=new JButton("emoji124");
		emojiButton24.setBounds(4, 111, 30, 30);
		ImageIcon emoji24=new ImageIcon("emoji24.jpg");
		emojiButton24.setIcon(emoji24);
		
		JButton emojiButton25=new JButton("emoji125");
		emojiButton25.setBounds(38, 111, 30, 30);
		ImageIcon emoji25=new ImageIcon("emoji25.jpg");
		emojiButton25.setIcon(emoji25);
		
		JButton emojiButton26=new JButton("emoji126");
		emojiButton26.setBounds(72, 111, 30, 30);
		ImageIcon emoji26=new ImageIcon("emoji26.jpg");
		emojiButton26.setIcon(emoji26);
		
		JButton emojiButton27=new JButton("emoji127");
		emojiButton27.setBounds(106, 111, 30, 30);
		ImageIcon emoji27=new ImageIcon("emoji27.jpg");
		emojiButton27.setIcon(emoji27);
		
		JButton emojiButton28=new JButton("emoji128");
		emojiButton28.setBounds(140, 111, 30, 30);
		ImageIcon emoji28=new ImageIcon("emoji28.jpg");
		emojiButton28.setIcon(emoji28);
		
		JButton emojiButton29=new JButton("emoji129");
		emojiButton29.setBounds(174, 111, 30, 30);
		ImageIcon emoji29=new ImageIcon("emoji29.jpg");
		emojiButton29.setIcon(emoji29);
		
		JButton emojiButton30=new JButton("emoji130");
		emojiButton30.setBounds(208, 111, 30, 30);
		ImageIcon emoji30=new ImageIcon("emoji30.jpg");
		emojiButton30.setIcon(emoji30);
		
		JButton emojiButton31=new JButton("emoji131");
		emojiButton31.setBounds(242, 111, 30, 30);
		ImageIcon emoji31=new ImageIcon("emoji31.jpg");
		emojiButton31.setIcon(emoji31);
				
		emojiPanel.add(emojiButton0);
		emojiPanel.add(emojiButton1);
		emojiPanel.add(emojiButton2);
		emojiPanel.add(emojiButton3);
		emojiPanel.add(emojiButton4);
		emojiPanel.add(emojiButton5);
		emojiPanel.add(emojiButton6);
		emojiPanel.add(emojiButton7);
		emojiPanel.add(emojiButton8);
		emojiPanel.add(emojiButton9);
		emojiPanel.add(emojiButton10);
		emojiPanel.add(emojiButton11);
		emojiPanel.add(emojiButton12);
		emojiPanel.add(emojiButton13);
		emojiPanel.add(emojiButton14);
		emojiPanel.add(emojiButton15);
		emojiPanel.add(emojiButton16);
		emojiPanel.add(emojiButton17);
		emojiPanel.add(emojiButton18);
		emojiPanel.add(emojiButton19);
		emojiPanel.add(emojiButton20);
		emojiPanel.add(emojiButton21);	
		emojiPanel.add(emojiButton22);
		emojiPanel.add(emojiButton23);
		emojiPanel.add(emojiButton24);
		emojiPanel.add(emojiButton25);
		emojiPanel.add(emojiButton26);
		emojiPanel.add(emojiButton27);
		emojiPanel.add(emojiButton28);
		emojiPanel.add(emojiButton29);
		emojiPanel.add(emojiButton30);
		emojiPanel.add(emojiButton31);
		
		
		emojiFrame.add(emojiPanel);

		emojiFrame.setVisible(true);
	}
	
	private static class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch (command) {
			case "Emoji":
				PersonalChatRoomGUI.emojiGUI();
				break;
			default:
				break;
			}

		}

	}



}
