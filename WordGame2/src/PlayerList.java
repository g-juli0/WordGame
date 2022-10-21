import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;

public class PlayerList
{
	// initialize instance variables
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private String fileName;

	/**
	 * @param name String name of file
	 * @throws FileNotFoundException
	 */
	public PlayerList(String name) throws FileNotFoundException
	{
		fileName = name;
		Scanner readFile = new Scanner(new File(name));
		
		while(readFile.hasNextLine())
		{
			Scanner line = new Scanner(readFile.nextLine());
			line.useDelimiter(","); // change delimiter
			playerList.add(new Player(line.next(), line.next(), line.nextInt(), line.nextInt(), line.nextInt()));
		}
	}
	
	/**
	 * @param id String id of a player
	 * @return true if the id is already taken, false if otherwise
	 */
	public boolean containsID(String id)
	{
		for(Player p : playerList)
			if(id.equals(p.getID()))
				return true;
		return false;
	}
	
	/**
	 * @param p Player to add to ArrayList
	 * @return true when player had been added
	 */
	public boolean addPlayer(Player p)
	{
		playerList.add(p);
		return true;
	}
	
	/**
	 * @param temp Player to be checked
	 * @return String of memory location so player can be mutated
	 */
	public Player authenticate(Player temp)
	{
		for(Player p : playerList)
			if(p.getID().equals(temp.getID()) && p.getPass().equals(temp.getPass()))
				return p;
		return null;
	}
	
	/**
	 * @throws IOException
	 */
	public void saveList() throws IOException
	{
		PrintWriter outFile = new PrintWriter(new FileWriter("players.txt"));
		
		for(Player p : playerList)
			outFile.println(p.getID() + "," + p.getPass() + "," + p.getRounds() + "," + p.getWords() + "," + p.getPoints());
		outFile.close();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * returns string of # of players and all player stats
	 */
	public String toString()
	{
		String fmt = "%-20s%-30s%-20s%-20s%-20s%n";
		String str = "\nTotal Players: " + playerList.size();
		DecimalFormat df = new DecimalFormat("#.##");
		
		int totalRounds = 0, totalWords = 0, totalPoints = 0, totalAvgPoints = 0, totalAvgWords = 0;
		
		for(Player p : playerList)
		{
			str += "\n\n\t" + p.getID();
			str += "\n\tTotal Rounds Played: " + p.getRounds();
			str += "\n\tTotal Words Found: " + p.getWords();
			str += "\n\tTotal Points Earned: " + p.getPoints();
			if(p.getRounds() > 0)
			{
				str += "\n\tAvg Words Found: " + df.format(p.getWords()/p.getRounds());
				str += "\n\tAvg Points Earned: " + df.format(p.getPoints()/p.getRounds());
				
				totalAvgWords += p.getWords()/p.getRounds();
				totalAvgPoints += p.getPoints()/p.getRounds();
			}
			
			totalRounds += p.getRounds();
			totalWords += p.getWords();
			totalPoints += p.getPoints();
		}
		
		str += "\n\nAggregate results for all Players:";
		str += "\n\tTotal Rounds Played: " + totalRounds +
			   "\n\tTotal Words Found: " + totalWords +
			   "\n\tTotal Points Earned: " + totalPoints +
			   "\n\tAve Words Found: " + df.format(totalWords/totalRounds) +
			   "\n\tAve Points Earned: " + df.format(totalPoints/totalRounds);
		
		
		return str;
	}

}
