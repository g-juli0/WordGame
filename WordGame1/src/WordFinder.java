// CS 401
// Assignment 2 WordFinder class
// You must complete the implementation of this class.  You will need
// to use an instance of the Dictionary class within this class.  See
// Dictionary.java for details on the Dictionary class.

public class WordFinder
{
	// Think about the instance variables that you will need for this class.
	// Minimally you will need a Dictionary and a String.
	
	String word;
	String file;
	Dictionary d;
	
	// Initialize a WordFinder object.  String fileName is the name of a
	// Dictionary file from which the Dictionary instance variable will be
	// initialized.
	public WordFinder(String fileName)
	{
		file = fileName;
		d = new Dictionary(fileName);
	}
	
	// Obtain and store a random word from the Dictionary of "size" or more
	// letters.
	public void nextWord(int size)
	{
		word = d.randWord(size);
	}
	
	// Return the word that was obtained. This is necessary since the word itself
	// will be stored in a private instance variable.
	public String showWord()
	{
		return word;
	}
	
	// This is the most challenging method in this class.  The "test" argument is
	// a String that will be checked for validity within the current word that was
	// obtained from the Dictionary.  This method should return true only if all of
	// the characters in "test" are found within the word (such that each letter in
	// the word is used at most one time) and if "test" is also a valid word in the
	// Dictionary.  Think about how you will do this and consult the Java API for
	// some ideas.
	public boolean goodWord(String test)
	{
		if(d.contains(test))
		{
			StringBuilder strWord = new StringBuilder(word); // make word into a StringBuilder
			
			for (int i = 0; i < test.length(); i++) // loop through each letter in test
			{
				String letter = test.substring(i, i+1);
				
				if(strWord.indexOf(letter) == -1) // in that letter is NOT in the word
				{
					return false;
				}
				
				strWord.deleteCharAt(strWord.indexOf(letter)); // if it is, delete the letter so it cannot be used again
			}
			return true;			
		}
		
		return false;
	}
}