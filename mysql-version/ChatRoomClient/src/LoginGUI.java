import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.net.*;
import java.io.*;

public class LoginGUI {

	private static JFrame mainFrame;
	private static JFrame lf; // login frame
	private static JFrame rf; // login frame
	
	static JTextField loginUsername;
	static JTextField registerUsername;
	static JTextField registerNickname;
	static JPasswordField loginPassword;
	static JPasswordField registerPassword;
	static JRadioButton male;
	static JRadioButton female;
	
	  private static Socket clientSocket ;
	    private static PrintWriter out;
	    private static BufferedReader in;

	public static void main(String args[]) {
		try {
		startGUI();
		clientSocket =new Socket("127.0.0.1", Integer.parseInt(args[0]));
		out=new PrintWriter(clientSocket.getOutputStream(),true);
		
		in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch(IOException e) {
			System.err.println(e);
		}
	}

	private static void startGUI() { // main Frame
		mainFrame = new JFrame("Main Menu");
		mainFrame.setBounds(300, 300, 440, 330);
		mainFrame.setLayout(new GridLayout(4, 1));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel menuOptions = new JLabel("Menu Options");
		menuOptions.setFont(new Font("Monospace", 1, 27));
		menuOptions.setForeground(Color.WHITE);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(menuOptions);
		topPanel.setBackground(new Color(173, 216, 230));

		JButton loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(100, 30));
		loginButton.setBackground(new Color(173, 216, 230));
		loginButton.setFont(new Font("Monospace", 1, 17));
		loginButton.setForeground(Color.red);
		loginButton.setBorder(null);
		loginButton.setActionCommand("Login");
		loginButton.addActionListener(new ButtonClickListener());

		JButton registerButton = new JButton("Register");
		registerButton.setPreferredSize(new Dimension(100, 30));
		registerButton.setBackground(new Color(173, 216, 230));
		registerButton.setFont(new Font("Monospace", 1, 17));
		registerButton.setForeground(Color.red);
		registerButton.setBorder(null);
		registerButton.setActionCommand("Register");
		registerButton.addActionListener(new ButtonClickListener());

		JButton quitButton = new JButton("Quit");
		quitButton.setPreferredSize(new Dimension(100, 30));
		quitButton.setBackground(new Color(173, 216, 230));
		quitButton.setFont(new Font("Monospace", 1, 17));
		quitButton.setForeground(Color.red);
		quitButton.setBorder(null);
		quitButton.setActionCommand("Quit");
		quitButton.addActionListener(new ButtonClickListener());

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		loginPanel.setBackground(Color.WHITE);
		loginPanel.add(loginButton);

		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		registerPanel.setBackground(Color.WHITE);
		registerPanel.add(registerButton);

		JPanel quitPanel = new JPanel();
		quitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		quitPanel.setBackground(Color.WHITE);
		quitPanel.add(quitButton);

		mainFrame.add(topPanel);
		mainFrame.add(loginPanel);
		mainFrame.add(registerPanel);
		mainFrame.add(quitPanel);

		mainFrame.setVisible(true);

	}

	private void loginFrame() { // login Frame
		lf = new JFrame("Login");
		lf.setBounds(300, 300, 440, 330);
		lf.setLayout(new GridLayout(4, 1));
		lf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// only close login
																// frame
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("Monospace", 1, 27));
		loginLabel.setForeground(Color.WHITE);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Monospace", 1, 17));
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Monospace", 1, 17));
		
		loginUsername = new JTextField(12);
		loginPassword = new JPasswordField(12);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		loginPanel.setBackground(new Color(173, 216, 230));
		loginPanel.add(loginLabel);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		usernamePanel.setBackground(Color.WHITE);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(loginUsername);

		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordPanel.setBackground(Color.white);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(loginPassword);

		JPanel blankPanel = new JPanel();
		blankPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		blankPanel.setBackground(Color.WHITE);

		JButton loginButton = new JButton("LOGIN");
		loginButton.setPreferredSize(new Dimension(100, 30));
		loginButton.setBackground(new Color(173, 216, 230));
		loginButton.setFont(new Font("Monospace", 1, 17));
		loginButton.setForeground(Color.red);
		loginButton.setBorder(null);
		loginButton.setActionCommand("LOGIN");
		loginButton.addActionListener(new ButtonClickListener());
		
		JButton backButton = new JButton("BACK");
		backButton.setPreferredSize(new Dimension(100, 30));
		backButton.setBackground(new Color(173, 216, 230));
		backButton.setFont(new Font("Monospace", 1, 17));
		backButton.setForeground(Color.red);
		backButton.setBorder(null);
	    backButton.setActionCommand("back");
		backButton.addActionListener(new ButtonClickListener());
		
		blankPanel.add(loginButton);
		blankPanel.add(backButton);

		lf.add(loginPanel);
		lf.add(usernamePanel);
		lf.add(passwordPanel);
		lf.add(blankPanel);

		lf.setVisible(true);

	}

	private void registerFrame() {
		rf = new JFrame();
		rf.setBounds(300, 300, 400, 300);
		rf.setLayout(new GridLayout(6, 1));
		rf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // only close
																// register
																// frame. But it
																// makes no
																// different in
		JLabel registerLabel = new JLabel("Register");
		registerLabel.setFont(new Font("Monospace", 1, 25));
		registerLabel.setForeground(Color.WHITE); // that case.

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Monospace", 1, 17));
		
		JLabel nicknameLabel = new JLabel("Nickname:");
		nicknameLabel.setFont(new Font("Monospace", 1, 17));
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Monospace", 1, 17));
		
		JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setFont(new Font("Monospace", 1, 17));
		
		registerUsername = new JTextField(12);
		registerNickname = new JTextField(12);
		registerPassword = new JPasswordField(12);
		
		male=new JRadioButton("male");
		female=new JRadioButton("female");
		ButtonGroup group = new ButtonGroup();
		group.add(male);
		group.add(female);

		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		registerPanel.setBackground(new Color(173, 216, 230));
		registerPanel.add(registerLabel);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		usernamePanel.setBackground(Color.WHITE);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(registerUsername);

		JPanel nicknamePanel = new JPanel();
		nicknamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		nicknamePanel.setBackground(Color.WHITE);
		nicknamePanel.add(nicknameLabel);
		nicknamePanel.add(registerNickname);

		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		genderPanel.setBackground(Color.WHITE);
		genderPanel.add(genderLabel);
		genderPanel.add(male);
		genderPanel.add(female);

		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(registerPassword);

		JPanel blankPanel = new JPanel();
		blankPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		blankPanel.setBackground(Color.WHITE);

		JButton registerButton = new JButton("REGISTER");
		registerButton.setPreferredSize(new Dimension(120, 30));
		registerButton.setBackground(new Color(173, 216, 230));
		registerButton.setFont(new Font("Monospace", 1, 15));
		registerButton.setForeground(Color.red);
		registerButton.setBorder(null);
		registerButton.setActionCommand("REGISTER");
		registerButton.addActionListener(new ButtonClickListener());

		JButton backButton = new JButton("BACK");
		backButton.setPreferredSize(new Dimension(120, 30));
		backButton.setBackground(new Color(173, 216, 230));
		backButton.setFont(new Font("Monospace", 1, 15));
		backButton.setForeground(Color.red);
		backButton.setBorder(null);
		backButton.setActionCommand("BACK");
		backButton.addActionListener(new ButtonClickListener());

		blankPanel.add(registerButton);
		blankPanel.add(backButton);

		rf.add(registerPanel);
		rf.add(usernamePanel);
		rf.add(nicknamePanel);
		rf.add(passwordPanel);
		rf.add(genderPanel);
		rf.add(blankPanel);

		rf.setVisible(true);
	}
	

	private static class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			LoginGUI guiTester2 = new LoginGUI();
			switch (command) {
			case "Login":
				mainFrame.dispose();// do not use setVisible(false), it is a
									// WASTE space.
				guiTester2.loginFrame();
				break;
			case "Register":
				mainFrame.dispose();
				guiTester2.registerFrame();
				break;
			case "Quit":
				System.exit(0);
			case "LOGIN":
				try {
					login();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "REGISTER":
				try {
					register();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "BACK":
				rf.dispose();
				guiTester2.startGUI();
				break;
			case "back":
				lf.dispose();
				guiTester2.startGUI();
				break;
			default:
				break;
			}

		}

	}

	private static void login() throws  Exception {
		ChatRoomSplitGUI login=new ChatRoomSplitGUI(clientSocket);

		String usernameIm = loginUsername.getText();
		String passwordIm= new String(loginPassword.getPassword());
																	
     	out.println("#LOGIN");
     	Thread.sleep(2000);
		out.println(usernameIm);
		out.println(passwordIm);
		
		String flag = in.readLine();
		
		if(flag.equals("#LOGINSUCCESS")){
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Monospace", 3, 17)));
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospace", 3, 17)));
		JOptionPane.showMessageDialog(null, "Congratulations! You have logged in successfully!");
		lf.dispose();
		Thread.sleep(500);
		login.client(usernameIm);
		login.chatRoomGUI();

		if(flag.equals("#WRONGPASSWORD")){
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Monospace", 3, 17)));
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospace", 3, 17)));
		JOptionPane.showMessageDialog(null, "Password is wrong,please try again.", "FAILED", JOptionPane.ERROR_MESSAGE);
		
		}
		if(flag.equals("#NOUSER")){
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Monospace", 3, 17)));
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospace", 3, 17)));
			JOptionPane.showMessageDialog(null, "You need to register first.", "FAILED", JOptionPane.ERROR_MESSAGE);
			lf.dispose();
			startGUI();
		}}

		}
	

	

	private static void register() throws Exception {

		String usernameIn = registerUsername.getText();
		String passwordIn = new String(registerPassword.getPassword());
		String nicknameIn = registerNickname.getText();
		String gender=male.isSelected()?male.getText():female.getText();
		gender=isFmale(gender);
		
		if (passwordIn.length() < 6) {
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Monospace", 3, 17)));
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospace", 3, 17)));
			JOptionPane.showMessageDialog(null, "Username and Password should longer than 6 characters!", "FAILED",
					JOptionPane.ERROR_MESSAGE); // Limitation of Username and Password
		} else {
		    out.println("#REGISTER");
		    Thread.sleep(500);
		    out.println(usernameIn);
		    Thread.sleep(500);
		    out.println(passwordIn);
		    Thread.sleep(500);
		    out.println(nicknameIn);
		    Thread.sleep(500);
		    out.println(gender);
		    String flag=in.readLine();
		    System.out.println(flag);
		    if (flag.equals("#REGISTERFAIL")){
		    		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Monospace", 3, 17)));
				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospace", 3, 17)));
				JOptionPane.showMessageDialog(null, "Sorry, the usename is already in use", "FAILED",
				JOptionPane.ERROR_MESSAGE);
		    }
		    if(flag.equals("#REGISTERSUCCESS")){
		    		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Monospace", 3, 17)));
				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospace", 3, 17)));
		    		JOptionPane.showMessageDialog(null, "Congratulations! You have registered successfully!");
		    		rf.dispose();
				startGUI();
		    }
		}

	}
	private static String isFmale(String gender) {
	if(gender.equals("male"))
		gender="FALSE";
	else
		gender="TRUE";
	return gender;
	}
/*	String showUserlist() {
		String userList = "Usernames:\n";
		for (int i = 0; i < allUsers.size(); i++) {
			userList += "[" + String.valueOf(i) + "]" + allUsers.get(i).getUsername(); // user method valueOf
			userList += "\n";
		}
		return userList;
	}*/

}
