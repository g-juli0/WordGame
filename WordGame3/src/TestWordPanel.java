// CS 0401 2018
// This program tests the WordPanel class, which is required
// for Assignment 4.
// Note how the WordPanel class is created and used below, and
// also note its behavior in the snapshots file TestWord.htm

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This program will have 3 panels -- a WordPanel on the left and right
// and a panel with some JButtons / TextFields in the middle.  The
// WordPanels are initialized and the components are laid out in the
// window.  The JTextFields and JButtons demonstrate some additional
// required mutators for the WordPanel class.

// See more comments below.

public class TestWordPanel
{
	private JFrame theWindow;
	private WordPanel leftPanel, rightPanel;
	private JLabel addLeftLabel, addRightLabel;
	private JTextField addLeft, addRight;
	private JButton clearLeft, clearRight;
	private JPanel modPanel;
	
	public TestWordPanel()
	{
		theWindow = new JFrame("Testing WordPanel");
		// Note the arguments to the WordPanel constructor.  The first
		// is the rows, the second the columns in the embedded JTextArea
		// The third argument is a String that will be used as a header
		// for the WordPanel.  A mutator is then called to set the font
		// size for each WordPanel
		leftPanel = new WordPanel(10, 15, "This is the Left Panel");
		rightPanel = new WordPanel(10, 15, "This is the Right Panel");
		// Setting the font size for the two panels.  This will affect all of
		// the components within the panels.
		leftPanel.setFontSize(25);
		rightPanel.setFontSize(15);
		
		// Here are some components that will test some other features
		// of the WordPanels.  They will all be put into a separate
		// JPanel which will be in the middle of the window.
		addLeftLabel = new JLabel("Add word to Left");
		addLeftLabel.setFont(new Font("Seri", Font.PLAIN, 20));
		addLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addLeftLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		addLeft = new JTextField();
		addLeft.setFont(new Font("Seri", Font.BOLD, 20));
		addRightLabel = new JLabel("Add word to Right");
		addRightLabel.setFont(new Font("Seri", Font.PLAIN, 20));
		addRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addRightLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		addRight = new JTextField();
		addRight.setFont(new Font("Seri", Font.BOLD, 20));
		clearLeft = new JButton("Clear Left Panel");
		clearLeft.setFont(new Font("Seri", Font.PLAIN, 20));
		clearRight = new JButton("Clear Right Panel");
		clearRight.setFont(new Font("Seri", Font.PLAIN, 20));
		ActionListener theListener = new PanelListener();
		clearLeft.addActionListener(theListener);
		clearRight.addActionListener(theListener);
		addLeft.addActionListener(theListener);
		addRight.addActionListener(theListener);
		
		// Add some components to the middle panel
		modPanel = new JPanel();
		modPanel.setLayout(new GridLayout(6,1));
		modPanel.add(addLeftLabel);
		modPanel.add(addLeft);
		modPanel.add(addRightLabel);
		modPanel.add(addRight);
		modPanel.add(clearLeft);
		modPanel.add(clearRight);
		
		// The overall JFrame will have a (1, 3) layout with the
		// two WordPanels on the ends.
		theWindow.setLayout(new GridLayout(1,3));
		theWindow.add(leftPanel);
		theWindow.add(modPanel);
		theWindow.add(rightPanel);

		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.pack();
		theWindow.setVisible(true);
		
		// Add some words to the WordPanels using a mutator.
		leftPanel.addWord("Hello");
		leftPanel.addWord("There");
		leftPanel.addWord("Fellow");
		leftPanel.addWord("Java");
		leftPanel.addWord("Programming");
		leftPanel.addWord("Gurus");
		
		rightPanel.addWord("Rodents");
		rightPanel.addWord("Of");
		rightPanel.addWord("Unusual");
		rightPanel.addWord("Size");
	}
	
	private class PanelListener implements ActionListener
	{
		// This is testing the clear() mutator and (again) the
		// addWord() mutator for the WordPanels
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == clearLeft)
				leftPanel.clear();
			else if (e.getSource() == clearRight)
				rightPanel.clear();
			else if (e.getSource() == addLeft)
			{
				String word = addLeft.getText();
				leftPanel.addWord(word);
				addLeft.setText("");
			}
			else if (e.getSource() == addRight)
			{
				String word = addRight.getText();
				rightPanel.addWord(word);
				addRight.setText("");
			}
		}
	}
	
	public static void main(String [] args)
	{
		new TestWordPanel();
	}
}