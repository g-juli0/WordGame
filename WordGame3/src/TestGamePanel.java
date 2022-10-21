// CS 0401 2018
// This program tests the GamePanel class, which is required
// for Assignment 4.
// Note how the GamePanel class is created and used below, and
// also note its behavior in the snapshots file TestGame.htm

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This program will initially have 2 panels -- a WordPanel
// for found words and a WordPanel for guessed words.  They will
// both initially be empty. There will be a large "Start Game" button
// to the left of the panels.
//
// When the "Start Game" button is clicked a GamePanel will replace
// the "Start Game" button in the window.  This GamePanel will allow
// one Player to play as many rounds of the game has he / she wants to
// play.  Unlike our previous versions of the game, for this one the
// user will have an unlimited amount of time for each word.  For more
// information on how the game will be played, see the snapshots file
// "TestGame.htm".

// See more comments below.

public class TestGamePanel implements GameInterface
{
	private JFrame theWindow;
	private WordPanel leftPanel, rightPanel;
	private GamePanel theGame;
	private JButton startGame;
	private Player P;
	
	public TestGamePanel()
	{
		theWindow = new JFrame("Testing GamePanel");
		startGame = new JButton("Start Game");
		startGame.setFont(new Font("Serif", Font.BOLD, 25));
		
		ActionListener theListener = new GameListener();
		startGame.addActionListener(theListener);
		
		// For this test program, we are creating a fixed Player every time
		// the program is run.  In a real game, we would have the Player log in
		// and then play the game.  See Player specifications in Assignment 3.
		P = new Player("Herbert", "Weasel", 4, 8, 22);
		
		// The overall layout is (1,3), even though only one component is added
		// here.  The full layout is utilized via the GameListener.
		theWindow.setLayout(new GridLayout(1,3));
		theWindow.add(startGame);

		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.pack();
		theWindow.setVisible(true);
		
	}
	
	private class GameListener implements ActionListener
	{
		// When the "Start Game" button is clicked, a GamePanel and two WordPanels
		// will be created and they will replace the button in the display.  Note also
		// that the two WordPanels are also passed into the GamePanel constructor.  This
		// is necessary so that they can be accessed from within the GamePanel.  
		//
		// Note that the final argument to the GamePanel constructor is the TestGamePanel
		// object itself.  This enables the GamePanel to call the gameOver() method when
		// it is finished.  The gameOver() method is the sole method in GameInterface.  
		// The parameter type for that last argument within the GamePanel constructor 
		// should be GameInterface rather than TestGamePanel.  The idea is that if a 
		// different class also needed this same GamePanel, it could use it as long as
		// it also implements GameInterface.
		public void actionPerformed(ActionEvent e)
		{
			leftPanel = new WordPanel(10, 15, "Found Words");
			rightPanel = new WordPanel(10, 15, "Guessed Words");
			theGame = new GamePanel(P, leftPanel, rightPanel, TestGamePanel.this);
			leftPanel.setFontSize(25);
			rightPanel.setFontSize(25);
			theWindow.remove(startGame);
			theWindow.add(leftPanel);
			theWindow.add(rightPanel);
			theWindow.add(theGame);
			theWindow.pack();
		}
	}
	
	// This method will be called from the GamePanel when it is finished with the
	// game.  The method will then switch out the GamePanel and the two WordPanels and switch
	// in "Start Game" button.  Note that since the Player P was passed to the GamePanel,
	// updates to the Player from the GamePanel are accessible back here in the main
	// program.
	public void gameOver()
	{
		theWindow.remove(theGame);
		theWindow.remove(rightPanel);
		theWindow.remove(leftPanel);
		theWindow.add(startGame);
		theWindow.pack();
		theWindow.repaint();
		JOptionPane.showMessageDialog(theWindow, "Back in Main Program, here is the Player\n" +
			P.toString());
	}
	
	public static void main(String [] args)
	{
		new TestGamePanel();
	}
}