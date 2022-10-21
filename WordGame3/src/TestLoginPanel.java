// CS 0401 2018
// This program tests the LoginPanel class, which is required for Assignment 4
// Note how it is used and also note the snapshots shown in TestLogin.htm
// Note also that this program also requires working versions of the
// Player and PlayerList classes as required in Assignment 3

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class TestLoginPanel implements LoginInterface
{
	private JFrame theWindow;
	private PlayerList players;
	private Player P;
	private LoginPanel logPan;
	private JButton login;
	
	public TestLoginPanel() throws FileNotFoundException
	{
		theWindow = new JFrame("Testing Login Panel");
		theWindow.setLayout(new GridLayout(1,2));
		players = new PlayerList("players.txt");
		
		// Note that the TestLoginPanel object is passed as an argument to
		// the LoginPanel.  Since TestLoginPanel implements LoginInterface
		// it can be accessed in that way from LoginPanel.  In other words,
		// the header for this constructor should be:
		// public LoginPanel (PlayerList pl, LoginInterface L)
		logPan = new LoginPanel(players, this);
		
		login = new JButton("Login");
		login.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 30));
		login.addActionListener(new LoginListener());
		
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.add(login);
		theWindow.pack();
		theWindow.setVisible(true);
	}

	// This method is from LoginInterface and will be called from the
	// LoginPanel.  The idea is that information obtained from LoginPanel
	// will be encapsulated within that Panel, and this method enables the
	// Player to be passed back to this program.
	public void setPlayer(Player newPlayer)
	{
		P = newPlayer;
		theWindow.remove(logPan);
		theWindow.add(login);
		theWindow.pack();
		JOptionPane.showMessageDialog(theWindow, "Player: " + P.toString());
	}
		
	// When the login button is clicked we add the LoginPanel to the window
	// and allow it to "do its job".  When it is finished, it will pass the
	// new Player back with the setPlayer method.  For details on the expected
	// functionality of LoginPanel, see the snapshot file TestLogin.htm
	private class LoginListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			theWindow.remove(login);
			theWindow.add(logPan);
			theWindow.pack();
		}
	}
	
	public static void main(String [] args) throws FileNotFoundException
	{
		new TestLoginPanel();	
	}
}