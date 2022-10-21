import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * LoginPanel - allows user to login to WordGame and update stats
 */
public class LoginPanel extends JPanel implements ActionListener
{
	private Player p;
	private String username, password;
	
	private JPanel topPanel, userPanel, passPanel, inputPanel;
	private JLabel lblLogin, lblUser, lblPass;
	private JButton btnSubmit;
	private JTextField txtUser;
	private JPasswordField txtPass;
	
	private PlayerList players;
	private LoginInterface L;
	
	/**
	 * Constructor for LoginPanel
	 * @param players list of existing players (from file)
	 * @param L LoginInterface used to easily exit out of LoginPanel in driver class
	 */
	public LoginPanel(PlayerList players, LoginInterface L)
	{
		// instantiate variables and components
		this.players = players;
		this.L = L;
		
		setLayout(new BorderLayout());
		
		topPanel = new JPanel(new BorderLayout());
		lblLogin = new JLabel("Please log in to the site", SwingConstants.CENTER);
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
		
		// add components to screen
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
			username = txtUser.getText();
			password = new String(txtPass.getPassword());
			
			// create new player with entered username and password
			Player p = new Player(username);
			p.setPass(password);
			
			if(players.containsID(username)) // make sure player exists with same username and password entered
				L.setPlayer(players.authenticate(p));
			else
			{
				JOptionPane.showMessageDialog(this, "ID \"" + username + "\" not found.");
				// reset username and password text fields
				txtUser.setText("");
				txtPass.setText("");
			}
		}
		
	}	
}