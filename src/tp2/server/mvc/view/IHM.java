package tp2.server.mvc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tp2.server.mvc.model.entity.DataBaseCarnet;

public class IHM extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected static JTextArea serverConsole;
	protected static JPanel console;
	protected static JPanel boutons;
	protected JTextField login;
	protected JTextField password;
	
	private static final Dimension dimensionConsoleServer = new Dimension(1000,300);
	
	private static final IHM ihm = new IHM();

	private IHM() {
		this.setTitle("Repertoire Server Console");
		this.setSize(dimensionConsoleServer);
		
		serverConsole = new JTextArea();
		serverConsole.setEditable(false);

		JScrollPane scrollConsole = new JScrollPane(serverConsole);
		
		boutons = new JPanel(new FlowLayout());
		boutons.setSize(new Dimension(this.getWidth(),20));
		
		JButton clearConsole = new JButton("Clear console");
		clearConsole.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverConsole.setText("");
			}
		});
		boutons.add(clearConsole);
		
		JButton addUser = new JButton("Add user");
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String loginValue = login.getText().trim();
				String passwordValue = password.getText().trim();
				DataBaseCarnet.getInstance().addUser(loginValue, passwordValue);
			}
		});
		boutons.add(addUser);
		boutons.add(new JLabel("login :"));
		login = new JTextField("tarik");
		boutons.add(login);
		boutons.add(new JLabel("password :"));
		password = new JTextField("djebien");
		boutons.add(password);
		
		this.getContentPane().add(BorderLayout.CENTER, scrollConsole);
		this.getContentPane().add(BorderLayout.SOUTH, boutons);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static IHM getInstance(){
		return ihm;
	}

	public static void writeOnConsoleServer(String message){
		serverConsole.append(message + "\n");
	}

}

