import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * NewPlayerPanel - allows new user to create account to keep track of score in WordGame
 */
public class NewPlayerPanel extends JPanel implements ActionListener
{
	private JPanel topPanel, userPanel, passPanel, inputPanel;
	private JLabel lblLogin, lblUser, lblPass;
	private JButton btnSubmit;
	private JTextField txtUser;
	private JPasswordField txtPass;
	
	private PlayerList players;
	private NewPlayerInterface N;
	
	public NewPlayerPanel(PlayerList players, NewPlayerInterface N)
	{
		this.players = players;
		this.N = N;
		
		setLayout(new BorderLayout());
		
		topPanel = new JPanel(new BorderLayout());
		lblLogin = new JLabel("Create a new account", SwingConstants.CENTER);
		lblLogin.setFont(new Font("Serif", Font.BOLD, 30));
		
		topPanel.add(new JPanel(), BorderLayout.NORTH);
		topPanel.add(lblLogin, BorderLayout.CENTER);
		topPanel.add(new JPanel(), BorderLayout.SOUTH);
		
		add(topPanel, BorderLayout.NORTH);
		
		inputPanel = new JPanel(new GridLayout(2, 1));
		userPanel = new JPanel(new GridLayout(1, 2));
		passPanel = new JPanel(new GridLayout(1, 2));
		
		lblUser = new JLabel("User ID:");
		lblPass = new JLabel("Password:");
		
		txtUser = new JTextField(15);
		txtPass = new JPasswordField(15);
		
		userPanel.add(lblUser);
		userPanel.add(txtUser);
		
		passPanel.add(lblPass);
		passPanel.add(txtPass);
		
		inputPanel.add(userPanel);
		inputPanel.add(passPanel);
		
		add(inputPanel, BorderLayout.CENTER);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(this);
		add(btnSubmit, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnSubmit)
		{
			if(players.containsID(txtUser.getText()))
			{
				JOptionPane.showMessageDialog(this, "User ID already taken. Try again.");
				txtUser.setText("");
				txtPass.setText("");
			}
			else if(txtUser.getText().equals("") || new String(txtPass.getPassword()).equals(""))
			{
				JOptionPane.showMessageDialog(this, "Invalid user ID or password. Try again.");
			}
			else
			{
				Player p = new Player(txtUser.getText(), new String(txtPass.getPassword()), 0, 0, 0);
				players.addPlayer(p);
				N.loginNewPlayer(p);
			}
		}
		
	}
}
