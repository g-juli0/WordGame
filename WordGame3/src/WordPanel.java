import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * WordPanel class - keeps track of guessed words, sorted alphabetically or in order of guess
 */
public class WordPanel extends JPanel implements ActionListener
{
	private JTextArea txtGuessedWords;
	private JButton btnSort;
	private JLabel title;
	private JPanel topPanel;
	
	private Dictionary d; // for sorted words
	private String words; // for unsorted words
	private boolean sorted;

	/**
	 * Constructor for WordPanel
	 * @param rows rows of text area
	 * @param cols columns of text area
	 * @param t title
	 */
	public WordPanel(int rows, int cols, String t)
	{
		// set layout and instantiate variables
		setLayout(new BorderLayout());
		d = new Dictionary();
		words = "\n";
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 1));
		
		title = new JLabel(t);
		btnSort = new JButton("Show Sorted");
		sorted = false; // start unsorted by default
		txtGuessedWords = new JTextArea(rows, cols);
		txtGuessedWords.setBorder(BorderFactory.createEtchedBorder());
		
		// add ActionListener
		btnSort.addActionListener(this);
		
		// add components
		topPanel.add(title);
		topPanel.add(btnSort);
		
		add(topPanel, BorderLayout.NORTH);
		add(txtGuessedWords, BorderLayout.CENTER);
	}
	
	/**
	 * adds new guessed word to lists
	 * @param newWord new word that the user entered
	 */
	public void addWord(String newWord)
	{
		if(!d.contains(newWord)) // if not already guessed
		{
			// add to text field, dictionary, and guessed words string
			txtGuessedWords.setText(txtGuessedWords.getText() + "\n" + newWord);
			d.addWord(newWord);
			words += newWord + "\n";
		}
	}
	
	/**
	 * sets font size
	 * @param sz size of font
	 */
	public void setFontSize(int sz)
	{
		txtGuessedWords.setFont(new Font(txtGuessedWords.getFont().getFontName(), Font.PLAIN, sz));
	}
	
	/**
	 * clears guessed words from text field, dictionary, and guessed words String
	 */
	public void clear()
	{
		txtGuessedWords.setText("");
		d = new Dictionary();
		words = "";
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnSort)
		{
			if(sorted) // unsorted
			{
				txtGuessedWords.setText(words.toString());
				btnSort.setText("Show Sorted");
				sorted = false;
			}
			else // sorted
			{
				txtGuessedWords.setText(d.toString());
				btnSort.setText("Show Unsorted");
				sorted = true;
			}
		}	
	}	
}