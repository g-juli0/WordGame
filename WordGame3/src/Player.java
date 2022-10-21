
public class Player
{
	// initialize instance variables
	private String id;
	private String password;
	private int rounds;
	private int wordsFound;
	private int points;

	/**
	 * @param x String to be set as username/ID
	 * @param pw String to be set as Password
	 * @param r int number of rounds played
	 * @param words int number of words guessed
	 * @param p int number of points earned
	 */
	public Player(String x, String pw, int r, int words, int p)
	{
		id = x;
		password = pw;
		rounds = r;
		wordsFound = words;
		points = p;
	}
	
	/**
	 * @param x String to be set as username/ID
	 */
	public Player(String x)
	{
		id = x;
		password = "";
		rounds = 0;
		wordsFound = 0;
		points = 0;
	}
	
	/**
	 * @param pw String to be set as Password
	 */
	public void setPass(String pw)
	{
		password = pw;
	}

	/**
	 * @return String password
	 */
	public String getPass()
	{
		return password;
	}

	/**
	 * @return int number of rounds
	 */
	public int getRounds()
	{
		return rounds;
	}

	/**
	 * @return int number of words
	 */
	public int getWords()
	{
		return wordsFound;
	}

	/**
	 * @return int number of points
	 */
	public int getPoints()
	{
		return points;
	}
	
	/**
	 * @return String username/ID
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * @param r int number of rounds to add
	 */
	public void addRounds(int r)
	{
		rounds += r;
	}
	
	/**
	 * @param w int number of words to add
	 */
	public void addWords(int w)
	{
		wordsFound += w;
	}
	
	/**
	 * @param p int number of points to add
	 */
	public void addPoints(int p)
	{
		points += p;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * returns String containing ID, rounds, words, and points
	 */
	public String toString()
	{
		String str = "\n\tID: " + id + "\n\tTotal Rounds Played: " + rounds 
				+ "\n\tTotal Words Found: " + wordsFound + "\n\tTotal Points Earned: " + points + "\n";
		return str;
	}
}
