import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * GamePanel class - allows user to enter a guess, adds guess to WordPanels, adds points
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener
{
	private Player p;
	private WordPanel leftPanel, rightPanel;
	private GameInterface G;
	private WordFinder w;
	
	private JPanel topPanel, bottomPanel;
	private JButton btnNext, btnQuit;
	private JLabel lblWord, lblGuess, lblStatus, word, status;
	private JTextField txtGuess;
	
	private int tempWords, tempPoints;
	
	/**
	 * Constructor for GamePanel
	 * @param p player that is logged in
	 * @param left left WordPanel for valid words
	 * @param right right WordPanel for all guesses
	 * @param G GameInterface
	 */
	public GamePanel(Player p, WordPanel left, WordPanel right, GameInterface G)
	{
		// instantiate variables
		this.p = p;
		leftPanel = left;
		rightPanel = right;
		this.G = G;
		
		// get starting word
		w = new WordFinder("dictionary.txt");
		w.nextWord(5);
		
		setLayout(new GridLayout(2, 1));
		
		JOptionPane.showMessageDialog(this, "Welcome to Word Finder, " + p.getId());
		
		// instantiate components
		topPanel = new JPanel(new GridLayout(2, 2));
		bottomPanel = new JPanel(new GridLayout(2, 2));
		
		btnNext = new JButton("Next Word");
		btnQuit = new JButton("Quit Game");
		
		// add ActionListeners
		btnNext.addActionListener(this);
		btnQuit.addActionListener(this);
		
		lblWord = new JLabel("Word: ");
		lblGuess = new JLabel("Guess: ");
		lblStatus = new JLabel("Status: ");
		word = new JLabel(w.showWord());
		status = new JLabel("");
		
		txtGuess = new JTextField();
		txtGuess.setBorder(BorderFactory.createEtchedBorder());
		
		// add components
		topPanel.add(btnNext);
		topPanel.add(btnQuit);
		topPanel.add(lblWord);
		topPanel.add(word);
		
		bottomPanel.add(lblGuess);
		bottomPanel.add(txtGuess);
		bottomPanel.add(lblStatus);
		bottomPanel.add(status);
		
		// add KeyListener to listen for ENTER key to be pressed
		txtGuess.addKeyListener(this);
		
		add(topPanel);
		add(bottomPanel);
		
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnNext)
		{
			// get next word, clear WordPanels, add stats
			w.nextWord(5);
			word.setText(w.showWord());
			leftPanel.clear();
			rightPanel.clear();
			p.addRounds(1);
			p.addWords(tempWords);
			p.addPoints(tempPoints);
			
			JOptionPane.showMessageDialog(this, p.getId() + ", you found " + tempWords + " words and " + tempPoints
					+ " points this round.");
			
			tempWords = 0;
			tempPoints = 0;
		}
		
		if(e.getSource() == btnQuit)
		{
			G.gameOver(); // exit out of GamePanel
		}
	}
	
	public void keyPressed(KeyEvent k)
	{
		int key = k.getKeyCode();
		
		if(key == KeyEvent.VK_ENTER)
		{
			String guess = txtGuess.getText();
			
			if(w.goodWord(guess))
			{
				status.setText(guess + " is valid");
				leftPanel.addWord(guess);
				tempWords++;
				tempPoints += guess.length();
			}
			
			else
				status.setText(guess + " is invalid");
			
			rightPanel.addWord(guess);
			txtGuess.setText("");
		}
	}

	// needed to implement KeyListener interface
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}