import java.io.*;
import java.util.Scanner;

/**
 * @author 21gjulio30
 * Gianna Julio
 * period 4 - Java
 * WordGame - user tries to find as many words as they can from a randomly chosen word
 */
public class Assig3
{
	// initialize public variables that all methods can use
	public static int totalWords = 0;
	public static int totalScore = 0;
	public static int rounds = 0;
	
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		WordFinder dict = new WordFinder("dictionary.txt");
		MyTimer timer = new MyTimer();
		PlayerList allPlayers = new PlayerList("players.txt");
		
		System.out.println("Welcome to WordFinder!\n");
		printInstructions();
		getIDinput(input, allPlayers, dict, timer);
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
	 * prints instructions on how to login or create a new account
	 */
	public static void printIdInfo()
	{
		System.out.println("\nPlease sign in with your id and password\n" + 
				"If you are a new player, leave your id field blank\n" + 
				"and we will set you up with a new account\n" + 
				"If you would like to end the game, enter 'Quit'\n");
	}
	
	/**
	 * @param input Scanner to get user input
	 * @param allPlayers PlayerList containing player data
	 * @param dict WordFinder containing Dictionary for gameplay
	 * @param timer MyTimer to count down 60 seconds for gameplay
	 * @throws IOException
	 */
	public static void getIDinput(Scanner input, PlayerList allPlayers, WordFinder dict, MyTimer timer) throws IOException
	{
		printIdInfo(); // print instructions

		System.out.print("\tID: ");
		String id = input.nextLine();
		
		if(id.equals("Quit") || id.equals("quit") || id.equals("QUIT")) // if user chooses to quit
		{
			System.out.println("Game over. Here are the overall stats:");
			System.out.println(allPlayers); // output stats
			System.out.println("Saving player information to file...");
			allPlayers.saveList(); // save to same file
			System.out.println("Player data successfully saved to file!");
		}
		
		else if(allPlayers.containsID(id)) // if returning user
		{
			System.out.print("\tPlease enter your password: ");
			String pw = input.nextLine();
			Player x = new Player(id);
			x.setPass(pw);
			
			if(allPlayers.authenticate(x) != null) // if password is valid
			{
				System.out.println("Welcome " + id + "!");
				Player temp = new Player(id);
				temp.setPass(pw);
				Player p = allPlayers.authenticate(temp); // return reference so Player is mutable
				
				getPlay(input, dict, timer, allPlayers, p); // "y" or "n"
			}
			else // incorrect password
			{
				for(int i = 0; i < 2; i++)
				{
					System.out.print("\tIncorrect password. Try again: ");
					pw = input.nextLine();
					
					if(allPlayers.authenticate(x) != null)
					{
						System.out.println("Welcome " + id + "!");
						Player temp = new Player(id);
						temp.setPass(pw);
						Player p = allPlayers.authenticate(temp); // return reference so Player is mutable
						getPlay(input, dict, timer, allPlayers, p); // "y" or "n"
					}
				}
				// out of attempts, must register as new player
				System.out.println("Out of attempts. Your sign in had been cancelled.\nPlease register as a new player");
				System.out.println("Welcome new player!\nBefore playing, we must get some information from you.");
				getNewPlayer(input, allPlayers, dict, timer);
			}			
		}
		else // not a returning player
		{
			System.out.println("Welcome new player!\nBefore playing, we must get some information from you.");
			getNewPlayer(input, allPlayers, dict, timer);
		}
		
	}

	/**
	 * @param input Scanner to get user input
	 * @param allPlayers PlayerList containing player data
	 * @param dict WordFinder containing Dictionary for gameplay
	 * @param timer MyTimer to count down 60 seconds for gameplay
	 * @throws IOException
	 */
	public static void getNewPlayer(Scanner input, PlayerList allPlayers, WordFinder dict, MyTimer timer) throws IOException
	{
		System.out.print("\tEnter an ID for your account: ");
		String id = input.nextLine();
		
		if(!allPlayers.containsID(id)) // if ID is not already used
		{
			String pw = confirmPassword(input);
			Player temp = new Player(id);
			temp.setPass(pw);
			allPlayers.addPlayer(temp);
			Player p = allPlayers.authenticate(temp); // return reference so Player is mutable
			System.out.println("Welcome " + id + "!");
			getPlay(input, dict, timer, allPlayers, p);
		}
		else // ID already used
		{
			System.out.println("Sorry, but that ID is already taken. Try again.");
			getNewPlayer(input, allPlayers, dict, timer);
		}
	}
	
	/**
	 * @param input Scanner to get user input
	 * @return password once it has been confirmed
	 */
	public static String confirmPassword(Scanner input)
	{

		System.out.print("\tEnter your password: ");
		String pw1 = input.nextLine().trim();
		System.out.print("\tConfirm password: ");
		String pw2 = input.nextLine().trim();
		
		if(!pw1.equals(pw2)) // make sure first and second attempt match
		{
			System.out.println("Sorry, your passwords do not match. Try again!");
			confirmPassword(input); // if not, try again
		}
		return pw1;
	}
	
	/**
	 * @param input Scanner to get user input
	 * @param dict Dictionary with all potential words
	 * @param timer MyTimer to limit game time to 1 minute
	 * @throws IOException 
	 */
	public static void getPlay(Scanner input, WordFinder dict, MyTimer timer, PlayerList allPlayers, Player p) throws IOException
	{
		String play;
		
		do
		{
			allPlayers.saveList(); // save player data periodically so if player forgets to formally quit not all data is lost
			System.out.print("\nWould you like to play? (y / n): ");
			play = input.nextLine();
			
			if(play.equals("y"))
			{
				rounds++; // add one to the round count
				playGame(input, dict, timer, allPlayers, p);
			}
			else if(play.equals("n"))
			{
				System.out.println("\nThanks for playing!");
				
				if(rounds > 0) // if the player had played at least 1 round
				{
					System.out.println("In this game: ");
					// output stats
					System.out.println("\tYou played " + rounds + " rounds");
					System.out.println("\tYou found a total of " + totalWords + " words");
					System.out.println("\tYou earned a total of " + totalScore + " points");
					System.out.println("\tYour average words found was " + totalWords/(double)rounds + " words per round");
					System.out.println("\tYour average points were " + totalScore/(double)rounds + " points per round");
				}
				
				p.addPoints(totalScore);				
				p.addWords(totalWords);				
				p.addRounds(rounds);				
				totalWords = 0; totalScore = 0; rounds = 0;
				
				System.out.println("Overall, here are your stats:");
				System.out.println(p);
				
				getIDinput(input, allPlayers, dict, timer);
				
			}
			else
				System.out.println("Invalid input. Try again.");
			
		} while(!(play.equals("y") || play.equals("n")));
	}
	
	/**
	 * @param input Scanner to get user input
	 * @param dict Dictionary with all potential words
	 * @param timer MyTimer to limit game time to 1 minute
	 * @param id 
	 * @param allPlayers 
	 * @throws IOException 
	 */
	public static void playGame(Scanner input, WordFinder dict, MyTimer timer, PlayerList allPlayers, Player p) throws IOException
	{
		dict.nextWord(6); // get a random word with a minimum length of 4
		
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
		
		getPlay(input, dict, timer, allPlayers, p);
	}
}
