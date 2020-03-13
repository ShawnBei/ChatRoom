import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.sql.Date;

public class ChatRoomSplitGUI {

	private static JFrame chatRoomFrame;
	private static JSplitPane hSplitPane;
	private static JSplitPane vLeftSplitPane;
	private static JSplitPane vRightSplitPane;
	private static JSplitPane vLeftBottomSplitPane;
	private static JSplitPane vFunctionSplitPane;
	private static JScrollPane chatHistoryBoxPanel;
	private static JScrollPane chatBoxPanel;
	private static JScrollPane credentialListPanel;
	private static JPanel sendPanel;
	private static JPanel functionPanel;
	private static JTextArea chatHistoryArea;
	private static JTextArea chatBoxTextArea;

	private static JFrame emojiFrame;
	private static JPanel emojiPanel;
	private static JButton emojilistButton;

	private static JFrame fileDownloadingFrame;
	private static JScrollPane fileDownloadingpanel;

	private static JFrame fileUploadingFrame;
	private static JLabel fileUploadingLabel;
	private static JTextArea fileUploadingTextarea;
	private static JButton fileUploadingButtonConfirm;
	private static JButton fileUploadingButtonClear;
	private static ButtonGroup group;
	private static JRadioButton file;

	private static JFrame historyFrame;
	private static JScrollPane historyPanel;
	private static JTextArea historyArea;

	public static ArrayList<Credentials> allUsers = new ArrayList<Credentials>();
	private static ArrayList<Credentials> status1 = new ArrayList<Credentials>();
	private static ArrayList<Credentials> status2 = new ArrayList<Credentials>();
	private static ArrayList<Credentials> status3 = new ArrayList<Credentials>();
	private static ArrayList<Credentials> status4 = new ArrayList<Credentials>();
	private static ArrayList<String> filePathList = new ArrayList<String>();
	private static ArrayList<String> PfilePathList = new ArrayList<String>();
	private static Socket clientSocket;
	private static BufferedReader in;
	private static PrintWriter out;
	static String username = null;
	static String nickname = null;
	static String gender = null;
	static String status = null;
	static String rStatus = null;
	static File fileName;
	static String titleName = null;

	private static String filePath;
	private static PersonalChatRoomGUI tester;

	private static JFrame PChatRoomFrame;
	private static JSplitPane PhSplitPane;
	private static JSplitPane PvLeftSplitPane;
	private static JSplitPane PvRightSplitPane;
	private static JSplitPane PvLeftBottomSplitPane;
	private static JSplitPane PvFunctionSplitPane;
	private static JScrollPane PChatHistoryBoxPanel;
	private static JScrollPane PChatBoxPanel;
	private static JPanel PShowPanel;
	private static JPanel PSendPanel;
	private static JPanel PFunctionPanel;
	private static JTextArea PChatHistoryArea;
	private static JTextArea PChatBoxTextArea;

	private static JFrame PEmojiFrame;
	private static JPanel PEmojiPanel;
	private static JButton PEmojilistButton;

	private static JFrame PFileDownloadingFrame;
	private static JScrollPane PFileDownloadingpanel;
	private static JRadioButton PFile;

	private static JFrame PFileUploadingFrame;
	private static JLabel PFileUploadingLabel;
	private static JTextArea PFileUploadingTextarea;
	private static JButton PFileUploadingButtonConfirm;
	private static JButton PFileUploadingButtonClear;

	private static JFrame PHistoryFrame;
	private static JScrollPane PHistoryPanel;
	private static JTextArea PHistoryArea;


	public ChatRoomSplitGUI(Socket s) throws IOException {
		clientSocket = s;

	}

	//	public static void main(String[] args) {
	//		
	//
	//	}

	public static void client(String s) throws IOException {

		titleName = s;

		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		//接收用户列表
		username = in.readLine();
		while (username != null && !username.equals("#END")) {
			nickname = in.readLine();
			gender = in.readLine();
			status = in.readLine();
			Credentials newUser = new Credentials(username, nickname, gender, status);
			allUsers.add(newUser);
			username = in.readLine();

		}

		//接收未读信息提醒
		username = in.readLine();
		while (username != null && !username.equals("#END")) {
			for (int i = 0; i < allUsers.size(); i++) {
				if (username.substring(2).equals(allUsers.get(i).getUsername()))
					allUsers.get(i).setRStatus("FALSE");
				break;
			}
			username = in.readLine();
		}


		ClientRead R = new ClientRead();
		Thread Read = new Thread(R);

		Read.start();

	}

	public static void chatRoomGUI() {

		chatRoomFrame=new JFrame(titleName + "'s Chat Room");
		chatRoomFrame.setBounds(100, 100, 700, 700);
		chatRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		hSplitPane = new JSplitPane();
		hSplitPane.setDividerLocation(500);
		hSplitPane.setDividerSize(0);
		hSplitPane.setContinuousLayout(true);

		vLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vLeftSplitPane.setDividerLocation(380);
		vLeftSplitPane.setDividerSize(0);

		vLeftBottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vLeftBottomSplitPane.setDividerLocation(50);
		vLeftBottomSplitPane.setDividerSize(0);

		vRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vRightSplitPane.setDividerLocation(590);
		vRightSplitPane.setDividerSize(0);
		vRightSplitPane.setContinuousLayout(true);
		vRightSplitPane.setOneTouchExpandable(true);

		chatHistoryArea = new JTextArea();
		chatHistoryArea.setSize(500, 380);
		chatHistoryArea.setEditable(false);
		chatHistoryBoxPanel = new JScrollPane(chatHistoryArea);
		chatHistoryBoxPanel.setSize(500, 380);
		chatHistoryBoxPanel.setBackground(Color.PINK);

		chatBoxTextArea = new JTextArea();
		chatBoxTextArea.setSize(500, 270);
		chatBoxTextArea.setLineWrap(true);
		chatBoxTextArea.setEditable(true);
		chatBoxPanel = new JScrollPane(chatBoxTextArea);
		chatBoxPanel.setSize(500, 300);

		credentialListPanel = new JScrollPane();
		credentialListPanel.setLayout(null);
		credentialListPanel.setBounds(500, 0, 200, 590);
		credentialListPanel.setBackground(Color.WHITE);

		//清空 status 列表
		status1.clear();
		status2.clear();
		status3.clear();
		status4.clear();

		for (int i = 0; i < allUsers.size(); i++) {

			if (allUsers.get(i).getStatus().equals("TRUE") && allUsers.get(i).getRStatus().equals("FALSE")) {
				Credentials list1 = new Credentials(allUsers.get(i).getUsername(), allUsers.get(i).getNickname(),
						allUsers.get(i).getGender(), allUsers.get(i).getStatus());
				status1.add(list1);
			} else if (allUsers.get(i).getStatus().equals("TRUE") && allUsers.get(i).getRStatus().equals("TRUE")) {
				Credentials list2 = new Credentials(allUsers.get(i).getUsername(), allUsers.get(i).getNickname(),
						allUsers.get(i).getGender(), allUsers.get(i).getStatus());
				status2.add(list2);
			} else if (allUsers.get(i).getStatus().equals("FALSE") && allUsers.get(i).getRStatus().equals("TRUE")) {
				Credentials list3 = new Credentials(allUsers.get(i).getUsername(), allUsers.get(i).getNickname(),
						allUsers.get(i).getGender(), allUsers.get(i).getStatus());
				status3.add(list3);

			} else if (allUsers.get(i).getStatus().equals("FALSE") && allUsers.get(i).getRStatus().equals("FALSE")) {
				Credentials list4 = new Credentials(allUsers.get(i).getUsername(), allUsers.get(i).getNickname(),
						allUsers.get(i).getGender(), allUsers.get(i).getStatus());
				status4.add(list4);
			}
		}
		for (int j = 0; j < status1.size(); j++) {
			JButton listButton = new JButton(status1.get(j).getNickname());
			listButton.setBounds(0, j * 30, 180, 30);
			listButton.setBackground(new Color(173, 216, 230));
			listButton.setFont(new Font("Monospace", 1, 17));
			listButton.setForeground(Color.RED);
			//			ImageIcon Rstatus = new ImageIcon("icon.JPG");
			//			listButton.setIcon(Rstatus);
			credentialListPanel.add(listButton);
			listButton.setActionCommand("user" + j);
			listButton.addActionListener(new ButtonClickListener());
		}
		for (int j = 0; j < status2.size(); j++) {
			JButton listButton = new JButton(status2.get(j).getNickname());
			listButton.setBounds(0, status1.size() * 30 + j * 30, 180, 30);
			listButton.setBackground(new Color(173, 216, 230));
			listButton.setFont(new Font("Monospace", 1, 17));
			listButton.setForeground(Color.GREEN);
			credentialListPanel.add(listButton);
			listButton.setActionCommand("user" + (j + status1.size()));
			listButton.addActionListener(new ButtonClickListener());

		}
		for (int j = 0; j < status3.size(); j++) {
			JButton listButton = new JButton(status3.get(j).getNickname());
			listButton.setBounds(0, (status1.size() + status2.size()) * 30 + j * 30, 180, 30);
			listButton.setBackground(new Color(173, 216, 230));
			listButton.setFont(new Font("Monospace", 1, 17));
			listButton.setForeground(Color.GRAY);
			//			ImageIcon Rstatus = new ImageIcon("icon.JPG");
			//			listButton.setIcon(Rstatus);
			credentialListPanel.add(listButton);
			listButton.setActionCommand("user" + (j + status1.size() + status2.size()));
			listButton.addActionListener(new ButtonClickListener());
		}
		for (int j = 0; j < status4.size(); j++) {
			JButton listButton = new JButton(status4.get(j).getNickname());
			listButton.setBounds(0, (status1.size() + status2.size() + status3.size()) * 30 + j * 30, 180, 30);
			listButton.setBackground(new Color(173, 216, 230));
			listButton.setFont(new Font("Monospace", 1, 17));
			listButton.setForeground(Color.ORANGE);
			credentialListPanel.add(listButton);
			listButton.setActionCommand("user" + (j + status1.size() + status2.size() + status3.size()));
			listButton.addActionListener(new ButtonClickListener());

		}
		sendPanel = new JPanel();
		sendPanel.setLayout(null);
		sendPanel.setBounds(500, 590, 200, 50);
		JButton sendButton = new JButton();
		sendButton.setBounds(0, 0, 175, 50);
		ImageIcon send = new ImageIcon("send.JPG");
		sendButton.setIcon(send);
		sendButton.setActionCommand("send");
		sendButton.addActionListener(new ButtonClickListener());
		sendPanel.add(sendButton);

		functionPanel = new JPanel();
		functionPanel.setLayout(null);
		functionPanel.setBounds(0, 400, 500, 50);

		JButton emojiButton = new JButton("emoji");
		emojiButton.setBounds(5, 0, 50, 50);
		ImageIcon emoji = new ImageIcon("emoji.jpg");
		emojiButton.setIcon(emoji);
		emojiButton.setActionCommand("emoji");
		emojiButton.addActionListener(new ButtonClickListener());

		JButton voiceButton = new JButton("voice");
		voiceButton.setBounds(65, 0, 50, 50);
		ImageIcon voice = new ImageIcon("voice.JPG");
		voiceButton.setIcon(voice);
		voiceButton.setActionCommand("voice");
		voiceButton.addActionListener(new ButtonClickListener());

		JButton imageButton = new JButton("image");
		imageButton.setBounds(125, 0, 50, 50);
		ImageIcon image = new ImageIcon("image.JPG");
		imageButton.setIcon(image);
		imageButton.setActionCommand("image");
		imageButton.addActionListener(new ButtonClickListener());

		JButton fileDownloadButton = new JButton("fileDowload");
		fileDownloadButton.setBounds(185, 0, 50, 50);
		ImageIcon fileDownload = new ImageIcon("fileDownload.JPG");
		fileDownloadButton.setIcon(fileDownload);
		fileDownloadButton.setActionCommand("fileDownload");
		fileDownloadButton.addActionListener(new ButtonClickListener());

		JButton fileUploadButton = new JButton("fileUpload");
		fileUploadButton.setBounds(245, 0, 50, 50);
		ImageIcon fileUpload = new ImageIcon("fileUpload.JPG");
		fileUploadButton.setIcon(fileUpload);
		fileUploadButton.setActionCommand("fileUpload");
		fileUploadButton.addActionListener(new ButtonClickListener());

		JButton historyButton = new JButton("history");
		historyButton.setBounds(305, 0, 50, 50);
		ImageIcon history = new ImageIcon("history.JPG");
		historyButton.setIcon(history);
		historyButton.setActionCommand("history");
		historyButton.addActionListener(new ButtonClickListener());

		functionPanel.add(emojiButton);
		functionPanel.add(voiceButton);
		functionPanel.add(imageButton);
		functionPanel.add(fileDownloadButton);
		functionPanel.add(fileUploadButton);
		functionPanel.add(historyButton);
		functionPanel.setBackground(new Color(173, 216, 230));

		vLeftBottomSplitPane.setTopComponent(functionPanel);
		vLeftBottomSplitPane.setBottomComponent(chatBoxPanel);

		vLeftSplitPane.setTopComponent(chatHistoryBoxPanel);
		vLeftSplitPane.setBottomComponent(vLeftBottomSplitPane);
		vRightSplitPane.setTopComponent(credentialListPanel);
		vRightSplitPane.setBottomComponent(sendPanel);

		hSplitPane.setLeftComponent(vLeftSplitPane);
		hSplitPane.setRightComponent(vRightSplitPane);

		chatRoomFrame.add(hSplitPane, BorderLayout.CENTER);
		chatRoomFrame.setVisible(true);

	}

	public static void emojiGUI() {
		emojiFrame = new JFrame("Emoji");
		emojiFrame.setBounds(100, 330, 300, 200);

		emojiPanel = new JPanel();
		emojiPanel.setLayout(null);
		emojiPanel.setBounds(100, 330, 300, 200);

		for (int i = 0; i < 8; i++) {
			emojilistButton = new JButton("emoji" + i);
			emojilistButton.setBounds(4 + i * 34, 0, 30, 30);
			ImageIcon emoji = new ImageIcon("emoji" + i + ".jpg");
			emojilistButton.setIcon(emoji);
			emojilistButton.setActionCommand("emoji" + i);
			emojilistButton.addActionListener(new ButtonClickListener());
			emojiPanel.add(emojilistButton);
		}
		for (int i = 0; i < 8; i++) {
			emojilistButton = new JButton("emoji" + (i + 8));
			emojilistButton.setBounds(4 + i * 34, 37, 30, 30);
			ImageIcon emoji = new ImageIcon("emoji" + (i + 8) + ".jpg");
			emojilistButton.setIcon(emoji);
			emojilistButton.setActionCommand("emoji" + (i + 8));
			emojilistButton.addActionListener(new ButtonClickListener());
			emojiPanel.add(emojilistButton);
		}
		for (int i = 0; i < 8; i++) {
			emojilistButton = new JButton("emoji" + (i + 16));
			emojilistButton.setBounds(4 + i * 34, 74, 30, 30);
			ImageIcon emoji = new ImageIcon("emoji" + (i + 16) + ".jpg");
			emojilistButton.setIcon(emoji);
			emojilistButton.setActionCommand("emoji" + (i + 16));
			emojilistButton.addActionListener(new ButtonClickListener());
			emojiPanel.add(emojilistButton);
		}
		for (int i = 0; i < 8; i++) {
			emojilistButton = new JButton("emoji" + (i + 24));
			emojilistButton.setBounds(4 + i * 34, 111, 30, 30);
			ImageIcon emoji = new ImageIcon("emoji" + (i + 24) + ".jpg");
			emojilistButton.setIcon(emoji);
			emojilistButton.setActionCommand("emoji" + (i + 24));
			emojilistButton.addActionListener(new ButtonClickListener());
			emojiPanel.add(emojilistButton);
		}

		emojiFrame.add(emojiPanel);

		emojiFrame.setVisible(true);
	}

	public static void fileUploadingGUI() {
		fileUploadingFrame = new JFrame("File Transfer");
		fileUploadingFrame.setSize(350, 200);
		fileUploadingFrame.setLocation(300, 300);
		fileUploadingFrame.setLayout(new GridLayout(3, 1));
		fileUploadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		fileUploadingLabel = new JLabel("Please input the path of the file:");
		JPanel topPanel = new JPanel();
		topPanel.add(fileUploadingLabel);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// fileUploadingTextarea = new JTextArea(80,20);
		// fileUploadingTextarea.setLineWrap(true);
		// JScrollPane middleScrollPane = new JScrollPane();
		// middleScrollPane.add(fileUploadingTextarea);
		// JPanel middlePanel = new JPanel();
		// middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		// middlePanel.add(middleScrollPane);
		// fileUploadingTextarea.setEditable(true);

		fileUploadingTextarea = new JTextArea(80, 20);
		fileUploadingTextarea.setLineWrap(true);
		JPanel middlePanel = new JPanel();
		middlePanel.add(fileUploadingTextarea);
		fileUploadingTextarea.setEditable(true);

		fileUploadingButtonConfirm = new JButton("confirm");
		fileUploadingButtonClear = new JButton("clear");

		fileUploadingButtonConfirm.setActionCommand("fileUploadingConfirm");
		fileUploadingButtonClear.setActionCommand("fileUploadingClear");

		fileUploadingButtonClear.addActionListener(new ButtonClickListener());
		fileUploadingButtonClear.addActionListener(new ButtonClickListener());

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(fileUploadingButtonConfirm);
		bottomPanel.add(fileUploadingButtonClear);

		fileUploadingFrame.add(topPanel);
		fileUploadingFrame.add(middlePanel);
		fileUploadingFrame.add(bottomPanel);
		fileUploadingFrame.setVisible(true);

	}

	public static void fileDownloadingGUI() {
		fileDownloadingFrame = new JFrame("File Transfer");
		fileDownloadingFrame.setSize(350, 200);
		fileDownloadingFrame.setLocation(300, 300);
		fileDownloadingFrame.setLayout(new GridLayout(3, 1));
		fileDownloadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel fileDownloadingLabel = new JLabel("please slect a file to download");
		JPanel topPanel = new JPanel();
		topPanel.add(fileDownloadingLabel);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JPanel optionsPanel = new JPanel();
		JScrollPane fileDownloadingScrollPane = new JScrollPane(optionsPanel);
		optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		group = new ButtonGroup();
		for(int i=0;i<filePathList.size();i++) {
			file=new JRadioButton(filePathList.get(i));
			group.add(file);
			fileDownloadingScrollPane.add(file);
		}





		JButton fileDownloadingButtonConfirm = new JButton("Download");
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(fileDownloadingButtonConfirm);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		fileDownloadingButtonConfirm.setActionCommand("fileDownloadingConfirm");
		fileDownloadingButtonConfirm.addActionListener(new ButtonClickListener());

		fileDownloadingFrame.add(topPanel);
		fileDownloadingFrame.add(optionsPanel);
		fileDownloadingFrame.add(bottomPanel);
		fileDownloadingFrame.setVisible(true);

	}

	public static void historyGUI() {
		historyFrame = new JFrame("History Records");
		historyFrame.setBounds(700, 300, 500, 600);
		historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		historyArea = new JTextArea();
		historyArea.setEditable(false);
		historyPanel = new JScrollPane(historyArea);
		historyFrame.add(historyPanel);
		historyFrame.setVisible(true);
	}

	private static class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch (command) {
			case "send":
				send();
				break;
			case "emoji":
				emojiGUI();
				break;
			case "voice":
				break;
			case "image":
				break;
			case "fileDownload":
				out.println("#REVIEWFILE");

				break;


			case "fileDownloadConfirm":
				for(int i=0;i<filePathList.size();i++){
					if(file.getText().equals(filePathList.get(i))){
						fileName = new File(file.getText()); 
						break;
					}
				}

				out.println("#DOWNLOADGROUPFILE"+fileName);   



			case "fileUpload":

				fileUploadingGUI();// open file transfer frame
				break;

			case "fileUploadingConfirm":
				FileUploading();
				break;


			case "fileUploadingClear":
				fileUploadingTextarea.setText("");
				break;
			case "history":
				out.println("#GRECORDS");
				historyGUI();
				break;

			case "user0":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(0).getGender());
				username="获取";
				out.print("#USERNAME");                                        //获取username
				for (int i = 0; i < allUsers.size(); i++) {
					if (username.equals(allUsers.get(i).getUsername()))
						allUsers.get(i).setRStatus("TRUE");
					break;
				}
				chatRoomFrame.dispose();						
				chatRoomGUI();
				break;

			case "user1":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(1).getGender());
				break;
			case "user2":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(2).getGender());
				break;
			case "user3":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(3).getGender());
				break;
			case "user4":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(4).getGender());
				break;
			case "user5":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(5).getGender());
				break;
			case "user6":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(6).getGender());
				break;
			case "user7":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(7).getGender());
				break;
			case "user8":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(8).getGender());
				break;
			case "user9":

				PersonalChatRoomGUI.pChatRoomGUI(allUsers.get(9).getGender());
				break;
			default:
				break;
			}

		}

	}

	private static void send() {
		String groupSend = chatBoxTextArea.getText();
		chatBoxTextArea.setText("");

		//		System.out.println(groupSend);
		out.println("#GMESSAGE" + groupSend);

	}

	private static void FileUploading(){
		out.println("#SENDFILE");
		fileName = new File(fileUploadingTextarea.getText());
		out.println("#FILENAME"+fileUploadingTextarea.getText());
		try {
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));

			while (true) {
				int datum;

				datum = bin.read();

				if (datum == -1)
					break;
				out.write(datum);
			}
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static class ClientRead extends Thread {

		public void run() {
			BufferedReader SocketIn = null;
			try {
				String clientReceive = in.readLine();
				while (true) {
					//group record				
					if (clientReceive.startsWith("#GRECORDS")) {
						historyArea.append(clientReceive.substring(9));
					}   

					//download group file				
					if (clientReceive.startsWith("#DOWNLOADGROUPFILE")) {
						BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(clientReceive.substring(18)));
						clientReceive = in.readLine();
						while (!clientReceive.equals("-1")) {
							bout.write(Integer.parseInt(clientReceive));
							clientReceive = in.readLine();
						}
						bout.flush();
					}

					//status change
					if (clientReceive.startsWith("#2")) {
						username = clientReceive.substring(2);
						for (int i = 0; i < allUsers.size(); i++) {
							if (username.equals(allUsers.get(i).getUsername())) {
								allUsers.get(i).setStatus("TRUE");
								break;
							}
						}
						chatRoomFrame.dispose();						
						chatRoomGUI();
					}
					if (clientReceive.startsWith("#3")) {
						username = clientReceive.substring(2);

						for (int i = 0; i < allUsers.size(); i++) {
							if (username.equals(allUsers.get(i).getUsername()))
								allUsers.get(i).setStatus("FALSE");
							break;
						}
						chatRoomFrame.dispose();						
						chatRoomGUI();
					}

					//file update
					if (clientReceive.startsWith("#REVIEWFILE")) {
						filePathList.clear();
						filePath = in.readLine();
						while(!filePath.equals(null)&&!filePath.equals("#END")){
							filePathList.add(filePath);
							filePath=in.readLine();
						}
						fileDownloadingGUI();
					}

					//group chat
					if (clientReceive.startsWith("#GMESSAGE")) {

						chatHistoryArea.append(clientReceive.substring(9) +"\n");
					}

					clientReceive = in.readLine();
				}





			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					SocketIn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void emoji(String buttonName) {
		if (buttonName.equals("emoji0")) {
			String str0 = "( ●-● )";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji1")) {
			String str0 = "(^^)／▽ ▽＼(^^)";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji2")) {
			String str0 = "\\(0.0)//";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji3")) {
			String str0 = "（╯＾╰）";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji4")) {
			String str0 = "(T_T)";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji5")) {
			String str0 = "(．Q．)";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji6")) {
			String str0 = "'（*∩_∩*）'";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji7")) {
			String str0 = "(”O〃)~~~";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji8")) {
			String str0 = "(^3^)-☆";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji9")) {
			String str0 = "((o(^_ ^)o))";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji10")) {
			String str0 = ":-D";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji11")) {
			String str0 = ">>d(‧-‧)b<<";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji12")) {
			String str0 = "p( ^ O ^ )q";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji13")) {
			String str0 = "o (^^) o";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji14")) {
			String str0 = "o -_-)=○)°O°)";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji15")) {
			String str0 = "~>__<~";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji16")) {
			String str0 = "( $ _ $ ) ";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji17")) {
			String str0 = "!_!";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji18")) {
			String str0 = "O__O\"";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji19")) {
			String str0 = "■D〃";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji20")) {
			String str0 = "( > c < )";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji21")) {
			String str0 = "〤ι〤";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji22")) {
			String str0 = "^3^";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji23")) {
			String str0 = "‘（*^﹏^*）′";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji24")) {
			String str0 = "（*^〔^*〕";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji25")) {
			String str0 = "(>＿<)}}";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji26")) {
			String str0 = "( - __ - )y--～";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji27")) {
			String str0 = "f‘（*∩_∩*）′";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji28")) {
			String str0 = "( ^＾^ )";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji29")) {
			String str0 = "*@_@*";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji30")) {
			String str0 = "(ˋ＾ˊ)";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		} else if (buttonName.equals("emoji31")) {
			String str0 = "-_-|||";
			chatBoxTextArea.append(str0);
			emojiFrame.dispose();

		}
	}

	public static class PersonalChatRoomGUI {

		public static void pChatRoomGUI(String genderIn) {
			PChatRoomFrame = new JFrame(" Personal Chat Room");
			PChatRoomFrame.setBounds(100, 100, 700, 700);
			PChatRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			PhSplitPane = new JSplitPane();
			PhSplitPane.setDividerLocation(500);
			PhSplitPane.setDividerSize(0);
			PhSplitPane.setContinuousLayout(true);

			PvLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			PvLeftSplitPane.setDividerLocation(380);
			PvLeftSplitPane.setDividerSize(0);

			PvLeftBottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			PvLeftBottomSplitPane.setDividerLocation(50);
			PvLeftBottomSplitPane.setDividerSize(0);

			PvRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			PvRightSplitPane.setDividerLocation(590);
			PvRightSplitPane.setDividerSize(0);
			PvRightSplitPane.setContinuousLayout(true);
			PvRightSplitPane.setOneTouchExpandable(true);

			PChatHistoryArea = new JTextArea();
			PChatHistoryArea.setSize(500, 380);
			PChatHistoryArea.setEditable(false);
			PChatHistoryBoxPanel = new JScrollPane(PChatHistoryArea);
			PChatHistoryBoxPanel.setSize(500, 380);
			PChatHistoryBoxPanel.setBackground(Color.PINK);

			PChatBoxTextArea = new JTextArea();
			PChatBoxTextArea.setSize(500, 270);
			PChatBoxTextArea.setLineWrap(true);
			PChatBoxTextArea.setEditable(true);
			PChatBoxPanel = new JScrollPane(PChatBoxTextArea);
			PChatBoxPanel.setSize(500, 300);

			PShowPanel = new JPanel();
			PShowPanel.setLayout(null);
			PShowPanel.setBounds(500, 0, 200, 590);
			JButton PShowButton = new JButton();
			PShowButton.setBounds(0, 0, 200, 590);
			if (genderIn.equals("TRUE")) {
				ImageIcon show = new ImageIcon("showGirl.JPG");
				PShowButton.setIcon(show);
			} else if (genderIn.equals("TRUE")) {
				ImageIcon show = new ImageIcon("showBoy.JPG");
				PShowButton.setIcon(show);
			}
			PShowPanel.add(PShowButton);

			PSendPanel = new JPanel();
			PSendPanel.setLayout(null);
			PSendPanel.setBounds(500, 590, 200, 50);
			JButton PSendButton = new JButton();
			PSendButton.setBounds(0, 0, 175, 50);
			ImageIcon send = new ImageIcon("send.JPG");
			PSendButton.setIcon(send);
			PSendButton.setActionCommand("Psend");
			PSendButton.addActionListener(new ButtonClickListener());
			PSendPanel.add(PSendButton);

			PFunctionPanel = new JPanel();
			PFunctionPanel.setLayout(null);
			PFunctionPanel.setBounds(0, 400, 500, 50);

			JButton PEmojiButton = new JButton("emoji");
			PEmojiButton.setBounds(5, 0, 50, 50);
			ImageIcon emoji = new ImageIcon("emoji.jpg");
			PEmojiButton.setIcon(emoji);
			PEmojiButton.setActionCommand("Pemoji");
			PEmojiButton.addActionListener(new ButtonClickListener());

			JButton PVoiceButton = new JButton("voice");
			PVoiceButton.setBounds(65, 0, 50, 50);
			ImageIcon voice = new ImageIcon("voice.JPG");
			PVoiceButton.setIcon(voice);
			PVoiceButton.setActionCommand("Pvoice");
			PVoiceButton.addActionListener(new ButtonClickListener());

			JButton PImageButton = new JButton("image");
			PImageButton.setBounds(125, 0, 50, 50);
			ImageIcon image = new ImageIcon("image.JPG");
			PImageButton.setIcon(image);
			PImageButton.setActionCommand("Pimage");
			PImageButton.addActionListener(new ButtonClickListener());

			JButton PFileDownloadButton = new JButton("fileDowload");
			PFileDownloadButton.setBounds(185, 0, 50, 50);
			ImageIcon fileDownload = new ImageIcon("fileDownload.JPG");
			PFileDownloadButton.setIcon(fileDownload);
			PFileDownloadButton.setActionCommand("PfileDownload");
			PFileDownloadButton.addActionListener(new ButtonClickListener());

			JButton PFileUploadButton = new JButton("fileUpload");
			PFileUploadButton.setBounds(245, 0, 50, 50);
			ImageIcon fileUpload = new ImageIcon("fileUpload.JPG");
			PFileUploadButton.setIcon(fileUpload);
			PFileUploadButton.setActionCommand("PfileUpload");
			PFileUploadButton.addActionListener(new ButtonClickListener());

			JButton PHistoryButton = new JButton("history");
			PHistoryButton.setBounds(305, 0, 50, 50);
			ImageIcon history = new ImageIcon("history.JPG");
			PHistoryButton.setIcon(history);
			PHistoryButton.setActionCommand("Phistory");
			PHistoryButton.addActionListener(new ButtonClickListener());

			PFunctionPanel.add(PEmojiButton);
			PFunctionPanel.add(PVoiceButton);
			PFunctionPanel.add(PImageButton);
			PFunctionPanel.add(PFileDownloadButton);
			PFunctionPanel.add(PFileUploadButton);
			PFunctionPanel.add(PHistoryButton);
			PFunctionPanel.setBackground(new Color(173, 216, 230));

			PvLeftBottomSplitPane.setTopComponent(PFunctionPanel);
			PvLeftBottomSplitPane.setBottomComponent(PChatBoxPanel);

			PvLeftSplitPane.setTopComponent(PChatHistoryBoxPanel);
			PvLeftSplitPane.setBottomComponent(PvLeftBottomSplitPane);
			PvRightSplitPane.setTopComponent(PShowPanel);
			PvRightSplitPane.setBottomComponent(PSendPanel);

			PhSplitPane.setLeftComponent(PvLeftSplitPane);
			PhSplitPane.setRightComponent(PvRightSplitPane);

			PChatRoomFrame.add(PhSplitPane, BorderLayout.CENTER);
			PChatRoomFrame.setVisible(true);

		}

		public static void pEmojiGUI() {
			PEmojiFrame = new JFrame("Emoji");
			PEmojiFrame.setBounds(100, 330, 300, 200);

			PEmojiPanel = new JPanel();
			PEmojiPanel.setLayout(null);
			PEmojiPanel.setBounds(100, 330, 300, 200);

			for (int i = 0; i < 8; i++) {
				PEmojilistButton = new JButton("emoji" + i);
				PEmojilistButton.setBounds(4 + i * 34, 0, 30, 30);
				ImageIcon emoji = new ImageIcon("emoji" + i + ".jpg");
				PEmojilistButton.setIcon(emoji);
				PEmojilistButton.setActionCommand("Pemoji" + i);
				PEmojilistButton.addActionListener(new ButtonClickListener());
				PEmojiPanel.add(PEmojilistButton);
			}
			for (int i = 0; i < 8; i++) {
				PEmojilistButton = new JButton("emoji" + (i + 8));
				PEmojilistButton.setBounds(4 + i * 34, 37, 30, 30);
				ImageIcon emoji = new ImageIcon("emoji" + (i + 8) + ".jpg");
				PEmojilistButton.setIcon(emoji);
				PEmojilistButton.setActionCommand("Pemoji" + (i + 8));
				PEmojilistButton.addActionListener(new ButtonClickListener());
				PEmojiPanel.add(PEmojilistButton);
			}
			for (int i = 0; i < 8; i++) {
				PEmojilistButton = new JButton("emoji" + (i + 16));
				PEmojilistButton.setBounds(4 + i * 34, 74, 30, 30);
				ImageIcon emoji = new ImageIcon("emoji" + (i + 16) + ".jpg");
				PEmojilistButton.setIcon(emoji);
				PEmojilistButton.setActionCommand("Pemoji" + (i + 16));
				PEmojilistButton.addActionListener(new ButtonClickListener());
				PEmojiPanel.add(PEmojilistButton);
			}
			for (int i = 0; i < 8; i++) {
				PEmojilistButton = new JButton("emoji" + (i + 24));
				PEmojilistButton.setBounds(4 + i * 34, 111, 30, 30);
				ImageIcon emoji = new ImageIcon("emoji" + (i + 24) + ".jpg");
				PEmojilistButton.setIcon(emoji);
				PEmojilistButton.setActionCommand("Pemoji" + (i + 24));
				PEmojilistButton.addActionListener(new ButtonClickListener());
				PEmojiPanel.add(PEmojilistButton);
			}

			PEmojiFrame.add(PEmojiPanel);

			PEmojiFrame.setVisible(true);
		}

		public static void pFileUploadingGUI() {
			PFileUploadingFrame = new JFrame("File Transfer");
			PFileUploadingFrame.setSize(350, 200);
			PFileUploadingFrame.setLocation(300, 300);
			PFileUploadingFrame.setLayout(new GridLayout(3, 1));
			PFileUploadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			PFileUploadingLabel = new JLabel("Please input the path of the file:");
			JPanel topPanel = new JPanel();
			topPanel.add(PFileUploadingLabel);
			topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

			// fileUploadingTextarea = new JTextArea(80,20);
			// fileUploadingTextarea.setLineWrap(true);
			// JScrollPane middleScrollPane = new JScrollPane();
			// middleScrollPane.add(fileUploadingTextarea);
			// JPanel middlePanel = new JPanel();
			// middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			// middlePanel.add(middleScrollPane);
			// fileUploadingTextarea.setEditable(true);

			PFileUploadingTextarea = new JTextArea(80, 20);
			PFileUploadingTextarea.setLineWrap(true);
			JPanel middlePanel = new JPanel();
			middlePanel.add(PFileUploadingTextarea);
			PFileUploadingTextarea.setEditable(true);

			PFileUploadingButtonConfirm = new JButton("confirm");
			PFileUploadingButtonClear = new JButton("clear");

			PFileUploadingButtonConfirm.setActionCommand("PfileUploadingConfirm");
			PFileUploadingButtonClear.setActionCommand("PfileUploadingClear");

			PFileUploadingButtonClear.addActionListener(new ButtonClickListener());
			PFileUploadingButtonClear.addActionListener(new ButtonClickListener());

			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			bottomPanel.add(PFileUploadingButtonConfirm);
			bottomPanel.add(PFileUploadingButtonClear);

			PFileUploadingFrame.add(topPanel);
			PFileUploadingFrame.add(middlePanel);
			PFileUploadingFrame.add(bottomPanel);
			PFileUploadingFrame.setVisible(true);

		}

		public static void pFileDownloadingGUI() {
			PFileDownloadingFrame = new JFrame("File Transfer");
			PFileDownloadingFrame.setSize(350, 200);
			PFileDownloadingFrame.setLocation(300, 300);
			PFileDownloadingFrame.setLayout(new GridLayout(3, 1));
			PFileDownloadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JLabel PFileDownloadingLabel = new JLabel("please slect a file to download");
			JPanel topPanel = new JPanel();
			topPanel.add(PFileDownloadingLabel);
			topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

			JPanel POptionsPanel = new JPanel();
			JScrollPane fileDownloadingScrollPane = new JScrollPane(POptionsPanel);
			POptionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			ButtonGroup PGroup = new ButtonGroup();
			for(int i=0;i<filePathList.size();i++) {
				PFile=new JRadioButton(filePathList.get(i));
				PGroup.add(file);
				fileDownloadingScrollPane.add(PFile);}

		}

		public static void pHistoryGUI() {
			PHistoryFrame = new JFrame("Personal History Records");
			PHistoryFrame.setBounds(700, 300, 500, 600);
			PHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			PHistoryArea = new JTextArea();
			PHistoryArea.setEditable(false);
			PHistoryPanel = new JScrollPane(PHistoryArea);
			PHistoryFrame.add(PHistoryPanel);
			PHistoryFrame.setVisible(true);
		}

		private static void pSend() {

			String groupSend = PChatBoxTextArea.getText();
			PChatBoxTextArea.setText("");// 从文本框获取聊天内容
			System.out.println(groupSend);
			out.println(groupSend);

		}

		private static class ButtonClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				switch (command) {
				case "Psend":
					pSend();
					break;
				case "Pemoji":
					pEmojiGUI();
					break;
				case "Pvoice":
					break;
				case "Pimage":
					break;
				case "PfileDownload":
					pFileDownloadingGUI();
					break;
				case "PfileDownloadConfirm":
					break;
				case "PfileUpload":
					pFileUploadingGUI();// open file transfer frame
					break;

				case "PfileUploadingConfirm":

					break;
				case "PfileUploadingClear":

					break;
				case "Phistory":
					out.println("#GRECORDS");
					historyGUI();
					break;
				case "Pemoji0":
					pEmoji("emoji0");
					break;
				case "Pemoji1":
					pEmoji("emoji1");
					break;
				case "Pemoji2":
					pEmoji("emoji2");
					break;
				case "Pemoji3":
					pEmoji("emoji3");
					break;
				case "Pemoji4":
					pEmoji("emoji4");
					break;
				case "Pemoji5":
					pEmoji("emoji5");
					break;
				case "Pemoji6":
					pEmoji("emoji6");
					break;
				case "Pemoji7":
					pEmoji("emoji7");
					break;
				case "Pemoji8":
					pEmoji("emoji8");
					break;
				case "Pemoji9":
					pEmoji("emoji9");
					break;
				case "Pemoji10":
					pEmoji("emoji10");
					break;
				case "Pemoji11":
					pEmoji("emoji11");
					break;
				case "Pemoji12":
					pEmoji("emoji12");
					break;
				case "Pemoji13":
					pEmoji("emoji13");
					break;
				case "Pemoji14":
					pEmoji("emoji14");
					break;
				case "Pemoji15":
					pEmoji("emoji15");
					break;
				case "Pemoji16":
					pEmoji("emoji16");
					break;
				case "Pemoji17":
					pEmoji("emoji17");
					break;
				case "Pemoji18":
					pEmoji("emoji18");
					break;
				case "Pemoji19":
					pEmoji("emoji19");
					break;
				case "Pemoji20":
					pEmoji("emoji20");
					break;
				case "Pemoji21":
					pEmoji("emoji21");
					break;
				case "Pemoji22":
					pEmoji("emoji22");
					break;
				case "Pemoji23":
					pEmoji("emoji23");
					break;
				case "Pemoji24":
					pEmoji("emoji24");
					break;
				case "Pemoji25":
					pEmoji("emoji25");
					break;
				case "Pemoji26":
					pEmoji("emoji26");
					break;
				case "Pemoji27":
					pEmoji("emoji27");
					break;
				case "Pemoji28":
					pEmoji("emoji28");
					break;
				case "Pemoji29":
					pEmoji("emoji29");
					break;
				case "Pemoji30":
					pEmoji("emoji30");
					break;
				case "Pemoji31":
					pEmoji("emoji31");
					break;
				default:
					break;
				}

			}

		}

		public static void pEmoji(String buttonName) {
			if (buttonName.equals("emoji0")) {
				String str0 = "( ●-● )";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji1")) {
				String str0 = "(^^)／▽ ▽＼(^^)";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji2")) {
				String str0 = "\\(0.0)//";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji3")) {
				String str0 = "（╯＾╰）";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji4")) {
				String str0 = "(T_T)";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji5")) {
				String str0 = "(．Q．)";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji6")) {
				String str0 = "'（*∩_∩*）'";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji7")) {
				String str0 = "(”O〃)~~~";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji8")) {
				String str0 = "(^3^)-☆";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji9")) {
				String str0 = "((o(^_ ^)o))";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji10")) {
				String str0 = ":-D";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji11")) {
				String str0 = ">>d(‧-‧)b<<";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji12")) {
				String str0 = "p( ^ O ^ )q";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji13")) {
				String str0 = "o (^^) o";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji14")) {
				String str0 = "o -_-)=○)°O°)";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji15")) {
				String str0 = "~>__<~";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji16")) {
				String str0 = "( $ _ $ ) ";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji17")) {
				String str0 = "!_!";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji18")) {
				String str0 = "O__O\"";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji19")) {
				String str0 = "■D〃";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji20")) {
				String str0 = "( > c < )";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji21")) {
				String str0 = "〤ι〤";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji22")) {
				String str0 = "^3^";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji23")) {
				String str0 = "‘（*^﹏^*）′";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji24")) {
				String str0 = "（*^〔^*〕";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji25")) {
				String str0 = "(>＿<)}}";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji26")) {
				String str0 = "( - __ - )y--～";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji27")) {
				String str0 = "f‘（*∩_∩*）′";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji28")) {
				String str0 = "( ^＾^ )";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji29")) {
				String str0 = "*@_@*";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji30")) {
				String str0 = "(ˋ＾ˊ)";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			} else if (buttonName.equals("emoji31")) {
				String str0 = "-_-|||";
				PChatBoxTextArea.append(str0);
				PEmojiFrame.dispose();

			}
		}

	}
}


//case "emoji0":
//	emoji("emoji0");
//	break;
//case "emoji1":
//	emoji("emoji1");
//	break;
//case "emoji2":
//	emoji("emoji2");
//	break;
//case "emoji3":
//	emoji("emoji3");
//	break;
//case "emoji4":
//	emoji("emoji4");
//	break;
//case "emoji5":
//	emoji("emoji5");
//	break;
//case "emoji6":
//	emoji("emoji6");
//	break;
//case "emoji7":
//	emoji("emoji7");
//	break;
//case "emoji8":
//	emoji("emoji8");
//	break;
//case "emoji9":
//	emoji("emoji9");
//	break;
//case "emoji10":
//	emoji("emoji10");
//	break;
//case "emoji11":
//	emoji("emoji11");
//	break;
//case "emoji12":
//	emoji("emoji12");
//	break;
//case "emoji13":
//	emoji("emoji13");
//	break;
//case "emoji14":
//	emoji("emoji14");
//	break;
//case "emoji15":
//	emoji("emoji15");
//	break;
//case "emoji16":
//	emoji("emoji16");
//	break;
//case "emoji17":
//	emoji("emoji17");
//	break;
//case "emoji18":
//	emoji("emoji18");
//	break;
//case "emoji19":
//	emoji("emoji19");
//	break;
//case "emoji20":
//	emoji("emoji20");
//	break;
//case "emoji21":
//	emoji("emoji21");
//	break;
//case "emoji22":
//	emoji("emoji22");
//	break;
//case "emoji23":
//	emoji("emoji23");
//	break;
//case "emoji24":
//	emoji("emoji24");
//	break;
//case "emoji25":
//	emoji("emoji25");
//	break;
//case "emoji26":
//	emoji("emoji26");
//	break;
//case "emoji27":
//	emoji("emoji27");
//	break;
//case "emoji28":
//	emoji("emoji28");
//	break;
//case "emoji29":
//	emoji("emoji29");
//	break;
//case "emoji30":
//	emoji("emoji30");
//	break;
//case "emoji31":
//	emoji("emoji31");
//	break;
