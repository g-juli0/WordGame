// CS 401
// Assignment 2 WordFinderTest
// Your WordFinder class must work with this program and the output
// must be correct.  Since the words are random, the output will not
// exactly match mine, but see a sample in WFTest.txt

import java.util.*;

public class WordFinderTest
{
	public static String [] tests = {"a", "ae", "re", "so", "um", "sit", "rate", "tot"};
		// Note: "ae" is not a valid word in the dictionary, so it should
		// never be a "goodWord".  The others may or may not be good
		// depending upon the value of the randomly selected "nextWord".
	public static void main(String [] args)
	{
		WordFinder F = new WordFinder("dictionary.txt");  // Create a new WordFinder
		for (int i = 0; i < 20; i++)
		{
			F.nextWord(7);	// Obtain a new random word from the Dictionary
			System.out.println("Word is " + F.showWord());  // Return the word
			for (int j = 0; j < tests.length; j++)
			{
				if (F.goodWord(tests[j]))	// Test strings to see if they can be
											// generated from word
					System.out.println("\t" + tests[j] + " is valid");
				else
					System.out.println("\t" + tests[j] + " is invalid");
			}
		}
	}
}