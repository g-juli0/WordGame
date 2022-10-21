// CS 0401
// Program to test PlayerList and Player classes.  Note the sample output shown
// for this program -- the first time run some new players are added but the
// second time none are added since the updated list was saved back to the file.

// Your Player and PlayerList classes can be implemented in various ways and must
// have additional methods to implement your word finder game effectively.  However, the
// required methods below demonstrate how the classes should fundamentally work, and
// your Player and PlayerList classes must produce the same results given the same
// players.txt file.

// For some help with these files you should look at Lab 7 and, in particular the
// Movie class and the MovieDB class.

import java.util.*;
import java.io.*;

public class PlayerListTest
{
	public static void main(String [] args) throws IOException
	{
		System.out.println("Testing PlayerList and Player classes");
		
		// Create a PlayerList from a file of players.  Note the format of the file
		// and note that after this constructor all of the player information is stored
		// within the allPlayers object.  Note also the effect of the toString() method.
		// Parse the output carefully in order to figure out what it entails.
		PlayerList allPlayers = new PlayerList("players.txt");
		System.out.println("Here are the player stats: ");
		System.out.println(allPlayers.toString());
		
		System.out.println();
		String [] names = {"Marge", "Fezzik", "Ingmar", "Inigo"};
		String [] passes = {"IHeartCS401", "Sportsmanlike", "Programming!", "YouKeepUsingThatWord"};
		for (int i = 0; i < names.length; i++)
		{
			String S = names[i];
			String P = passes[i];
			// containsId will return true if the Player's id is within the PlayerList and
			// false otherwise.  This will be useful in your main program.
			boolean found = allPlayers.containsID(S);
			if (found)
			{
				System.out.println(S + " is an id in the PlayerList\n");
			}
			else
			{
				System.out.println(S + " is not in the list -- will be added:");
				// Create a new player with just a String for the name.  The remaining
				// fields should be null or 0
				Player onePlayer = new Player(S);
				// Set the password for the player
				onePlayer.setPass(passes[i]);
				System.out.println("\tNew player info: ");
				System.out.println(onePlayer.toString());
				// Add the player to the PlayerList
				if (allPlayers.addPlayer(onePlayer))
					System.out.println("has been added to the PlayerList");
				else
					System.out.println("Not added!");
			}
		}
		
		System.out.println("Checking for valid Players");
		for (int i = 0; i < names.length; i++)
		{
			String S = names[i];
			String P = passes[i];
			// containsId will return true if the Player's id is within the PlayerList and
			// false otherwise.
			Player temp = new Player(S);
			temp.setPass(P);
			Player play = allPlayers.authenticate(temp);
			if (play != null)
			{
				// The authenticate() method will return the reference of a Player within the
				// PlayerList and NOT a copy of the Player.  We discussed this issue in lecture.
				// It may not always be the best idea to do this, but since we have a reference into
				// the same Player that is in the PlayerList, we can mutate it and the effect will
				// occur within the PlayerList.  Note the mutations below.
				System.out.println("Player " + play + " has been authenticated and can play!\n");
				// The methods below can be used to update a Player during the game (after each
				// completed round).  You may want some additional mutators as well.
				play.addRounds(1);
				play.addWords(4);
				play.addPoints(15);
			}
			else // In this example Inigo will not be valid since the password we have
			{	 // for him in the passes[] array does not match the password in the file
				System.out.println("Player " + temp + " does not have a valid password\n");
			}
			
			
		}
		// Note the format and information returned by the toString() method.  Your
		// output should contain the same, well formatted information.
		System.out.println("Here are the player stats: ");
		System.out.println(allPlayers.toString());
		
		// Write the players back to a text file so that they can be retrieved later.
		// Clearly to write the Player information to the file, the PlayerList class must
		// be able to get the information from each of the Player objects.  Use this to
		// help you design both the Player and PlayerList classes.
		allPlayers.saveList();
	}		
}