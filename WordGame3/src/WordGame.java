import java.io.*;
import java.util.Scanner;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * WordGame - user tries to find as many words as they can from a randomly chosen word
 */
public class WordGame
{
	// initialize public variables that all methods can use
	public static int totalWords = 0;
	public static int totalScore = 0;
	public static int rounds = 0;
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner input = new Scanner(System.in);
		WordFinder dict = new WordFinder("dictionary.txt");
		MyTimer timer = new MyTimer();
		
		System.out.println("Welcome to WordFinder!\n");
		printInstructions();
		getPlay(input, dict, timer); // "y" or "n"
	}
	
	/**
	 * prints out instructions in a nice format
	 */
	public static void printInstructions()
	{
		System.out.println("How to play: ");
		System.out.println("\tFor each round you will see a randomly selected word.");
		System.out.println("\tYou will have 1 minute to find as many words as you can");
		System.out.println("\tthat can be generated with the letters in your random");
		System.out.println("\tword.  Your words do not have to use all of the letters");
		System.out.println("\tin the word but they must be valid words.  You will receive");
		System.out.println("\ta point for each letter in each word, so longer words count");
		System.out.println("\tmore than shorter words.");
	}
	
	/**
	 * @param input Scanner to get user input
	 * @param dict Dictionary with all potential words
	 * @param timer MyTimer to limit game time to 1 minute
	 * @throws FileNotFoundException if the file containing the guessed
	 */
	public static void getPlay(Scanner input, WordFinder dict, MyTimer timer) throws FileNotFoundException
	{
		String play;
		
		do
		{
			System.out.print("\nWould you like to play? (y / n): ");
			play = input.nextLine();
			
			if(play.equals("y"))
			{
				rounds++; // add one to the round count
				playGame(input, dict, timer);
			}
			else if(play.equals("n"))
			{
				System.out.println("\nThanks for playing!");
				
				if(rounds > 0) // if the player had played at least 1 round
				{
					// output stats
					System.out.println("You played " + rounds + " rounds");
					System.out.println("You found a total of " + totalWords + " words");
					System.out.println("You earned a total of " + totalScore + " points");
					System.out.println("Your average words found was " + totalWords/(double)rounds + " words per round");
					System.out.println("Your average points were " + totalScore/(double)rounds + " points per round");
				}
				
				// I was going to do the bonus but then i had to do euro we have a quiz tomorrow
				//System.out.print("\nEnter your username: ");
				//String username = input.nextLine();
			}
			else
				System.out.println("Invalid input. Try again.");
			
		} while(!(play.equals("y") || play.equals("n")));
	}
	
	/**
	 * @param input Scanner to get user input
	 * @param dict Dictionary with all potential words
	 * @param timer MyTimer to limit game time to 1 minute
	 * @throws FileNotFoundException if the file containing the guessed
	 */
	public static void playGame(Scanner input, WordFinder dict, MyTimer timer) throws FileNotFoundException
	{
		dict.nextWord(4); // get a random word with a minimum length of 4
		
		timer.set(60000); // initialize and start timer
		timer.start();
		
		// create new dictionary containing words that the user has already guessed
		Dictionary guessedWords = new Dictionary("guessedWords.txt");
		String guess;
		int count = 0;
		int score = 0;
		
		do
		{
			System.out.println("\nWord: " + dict.showWord());
			System.out.print("\tGuess: ");
			
			guess = input.nextLine().trim(); // get rid of extra spaces
			
			if(!dict.goodWord(guess)) // not in dictionary
				System.out.println(guess + " is not valid");
			else if(guessedWords.contains(guess)) // already guessed
				System.out.println(guess + " is a duplicate word");
			else
			{
				count++;
				System.out.println("Answer " + count + " is " + guess);
				guessedWords.addWord(guess);
				score += guess.length();
			}
			
		} while(timer.check()); // while timer is still running
		
		// update total score and word count
		totalScore += score;
		totalWords += count;
		
		// output stats and words for the past round
		System.out.println("\nSorry, time is up!");
		System.out.println("You found " + count + " words");
		System.out.println("You earned " + score + " points");
		System.out.println("Here are your words:");
		System.out.println(guessedWords);
		
		getPlay(input, dict, timer);
	}
}
