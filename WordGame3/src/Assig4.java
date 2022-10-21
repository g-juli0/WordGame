// CS 0401 2018
// 
// This program tests all 3 of the Assignment 4 required classes in a simple
// graphical implementation of the Word Finder Game.  If your WordPanel,
// LoginPanel and GamePanel (as well as Player and PlayerList from
// Assignment 3) classes are all written correctly then this program
// should also run correctly.

// See more comments below.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * Driver class for WordGame GUI
 */
public class Assig4 implements GameInterface, LoginInterface, NewPlayerInterface
{
	// Note the variables -- we have variables for the window and all of
	// the various panels.  Not all of these will be visible at the same
	// time however.  We also have 3 buttons which will initially be shown
	// to the user.
	private JFrame theWindow;
	private WordPanel leftPanel, rightPanel;
	private LoginPanel theLogin;
	private GamePanel theGame;
	private NewPlayerPanel newPlayer;
	private JPanel buttonPanel;
	private JButton quitButton, loginButton, playButton, newPlayerButton;
	private Player P;
	private PlayerList PL;
	
	public Assig4() throws FileNotFoundException
	{
		theWindow = new JFrame("Java Word Finder Program");
		PL = new PlayerList("players.txt");
		
		// instantiate buttons
		loginButton = new JButton("Player Login");
		loginButton.setFont(new Font("Serif", Font.BOLD, 25));
		playButton = new JButton("Start Game");
		playButton.setFont(new Font("Serif", Font.BOLD, 25));
		playButton.setEnabled(false); // cannot play until we have a Player
		quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Serif", Font.BOLD, 25));
		newPlayerButton = new JButton("New Player");
		newPlayerButton.setFont(new Font("Serif", Font.BOLD, 25));
		
		// add buttons to button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,1));
		buttonPanel.add(quitButton);
		buttonPanel.add(loginButton);
		buttonPanel.add(newPlayerButton);
		buttonPanel.add(playButton);
		
		// add ActionListeners
		ActionListener theListener = new GameListener();
		quitButton.addActionListener(theListener);
		loginButton.addActionListener(theListener);
		playButton.addActionListener(theListener);
		newPlayerButton.addActionListener(theListener);
		
		theWindow.setLayout(new GridLayout(1,3));
		theWindow.add(buttonPanel);

		theWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		theWindow.setSize(300, 400);
		theWindow.setVisible(true);
		
	}
	
	// This single listener will handle the 3 buttons in the buttonPanel.
	// Note the logic.  Initially, playButton is not enabled because there
	// is no Player.  loginButton creates a LoginPanel to allow a new Player
	// to login.  That panel will call setPlayer() when it finishes, which
	// will enable the playButton for that player.   playButton will create
	// the two WordPanel objects and the GamePanel so that the current Player
	// can play the game.  When the Player finishes, the GamePanel will call
	// the gameOver() method.  This will reset the buttons so that a new
	// Player will have to login to play.  It will also set the Player to null
	// (so even if the same Player wants to play twice in a row, he / she
	// would have to login again to do it).
	private class GameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == loginButton)
			{
				if (P != null)
				{
					JOptionPane.showMessageDialog(theWindow, "Player " + P.getId() +
						" quitting without playing ");
				}
				theLogin = new LoginPanel(PL, Assig4.this);
				theWindow.remove(buttonPanel);
				theWindow.add(theLogin);
				theWindow.pack();
			}
			else if (e.getSource() == playButton)
			{
				leftPanel = new WordPanel(10, 18, "Found Words");
				rightPanel = new WordPanel(10, 18, "Guessed Words");
				leftPanel.setFontSize(25);
				rightPanel.setFontSize(25);
				theGame = new GamePanel(P, leftPanel, rightPanel, Assig4.this);
				theWindow.remove(buttonPanel);
				theWindow.add(leftPanel);
				theWindow.add(rightPanel);
				theWindow.add(theGame);
				theWindow.pack();
			}
			else if (e.getSource() == quitButton)
			{
				if (P != null)
				{
					JOptionPane.showMessageDialog(theWindow, "Player " + P.getId() +
						" quitting without playing ");
				}
				try
				{
					PL.saveList();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				JTextArea end = new JTextArea(10, 30);
				end.setText("Overall results:\n" + PL.toString());
				end.setCaretPosition(0); // scroll starts at top
				JScrollPane scrollPane = new JScrollPane(end);
				
				JOptionPane.showMessageDialog(theWindow, scrollPane);
				System.exit(0);
			}
			else if(e.getSource() == newPlayerButton)
			{
				newPlayer = new NewPlayerPanel(PL, Assig4.this);
				theWindow.remove(buttonPanel);
				theWindow.add(newPlayer); // create and add new panel to window
				theWindow.pack();
			}
		}
	}
	
	public void setPlayer(Player pl)
	{
		P = pl;
		playButton.setEnabled(true);
		playButton.setText(P.getId() + " Start Game ");
		theWindow.remove(theLogin);
		theWindow.add(buttonPanel);
		JOptionPane.showMessageDialog(theWindow, "Ready to play with \n" +
			P.toString());
		theWindow.setSize(300,400);
	}
	
	// This method will be called from the GamePanel when it is finished with the
	// game.  It removes the GamePanel, adds back the buttonPanel and sets the
	// Player P back to null.
	public void gameOver()
	{
		theWindow.remove(theGame);
		theWindow.remove(rightPanel);
		theWindow.remove(leftPanel);
		theWindow.add(buttonPanel);
		P = null;
		playButton.setText("Start Game");
		playButton.setEnabled(false);
		theWindow.setSize(300,400);
		theWindow.repaint();
		
	}
	
	public static void main(String [] args) throws FileNotFoundException
	{
		new Assig4();
	}

	@Override
	public void loginNewPlayer(Player pl)
	{
		P = pl; // set new player
		playButton.setEnabled(true); // play button can now be pressed
		playButton.setText(P.getId() + " Start Game ");
		theWindow.remove(newPlayer); // remove new player creation panel
		theWindow.add(buttonPanel);
		JOptionPane.showMessageDialog(theWindow, "Ready to play with \n" +
			P.toString());
		theWindow.setSize(300,400);
	}
}
