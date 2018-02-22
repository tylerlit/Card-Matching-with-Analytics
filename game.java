import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
public class game extends Card{

	private boolean playing;
	private int player1;
	private int player2;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> memory = new ArrayList<Card>();
	private int[] free_indices;

	public game(){
		this.player1 = 0;
		this.player2 = 0;
		this.free_indices = new int[52];
		this.playing = true;
	}

	public boolean isPlaying(){
		return this.playing;
	}

	public void setDeck(){

		for (Suits s: Suits.values()){
			for (int r = 0; r < 13; r++){
				Card c = new Card(s, r);
				
				this.deck.add(c);
			}
		}
		Collections.shuffle(this.deck);
	}

	public String printDeck(){

		String print = "";
		
		for (int i = 0; i < deck.size(); i++){
			if (i % 13 == 0 && i != 0){
				print += "\n";
			}
			print += this.deck.get(i) + " ";
		}
		print += "\n";

		return print;
	}

    //chooses a random card from cards that are face down
    //if there are no cards to choose from, ends game
	public Card chooseRandomCard(){
		
	//clear memory
		for (int i = 0; i < this.deck.size(); i++){
			this.free_indices[i] = -1;
		}

		Random r = new Random();
		int index = 0;
		int length = 0;
		Card choose;
		
		for (int j = 0; j < this.deck.size(); j++){
			if (!this.deck.get(j).isFound() && this.deck.get(j).getFaceDown()){
				this.free_indices[index] = j;
				index++;
			}
		}

		for (int k = 0; k < this.deck.size(); k++){
			if (this.free_indices[k] == -1){
				break;
			}
			length++;
		}

		if (length > 1){
			int random = r.nextInt(length - 1);
			choose = this.deck.get(free_indices[random]);
		}
		else if (length == 1){
			choose = this.deck.get(free_indices[0]);
		}
		else if (this.free_indices[0] == -1) {
	    //END GAME
			this.playing = false;
			choose = this.deck.get(0);
		}
		else {
			return null;
		}

		return choose;
		
	}
	
	public boolean isInMemory(Card check){

		for (int i = 0; i < this.memory.size(); i++){
			Card test = this.memory.get(i);
			if (test.getSuit() == check.getSuit()){
				if (test.getRank() == check.getRank()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean matches(Card match1, Card match2){

	//cards are equal to each other, cannot pick them ever again
		if (match1.equals(match2)){
			
			if (this.isInMemory(match1)){
				this.memory.remove(match1);
			}
			if (this.isInMemory(match2)){
				this.memory.remove(match2);
			}
			return true;
		}
		return false;
	}

    //simulates the move of player one
	public String playerOne(){	

		String print = "";

	//pick cards
		Card match1 = this.chooseRandomCard();

		if (!this.playing){
			return "";
		}
		if (match1.getFaceDown()){
			match1.flip();
		}

		Card match2 = this.chooseRandomCard();

		if (match2.getFaceDown()){
			match2.flip();
		}

	//print table to file
		print += this.printDeck();

		if (this.matches(match1, match2)){
			print += "Congrats! You matched the cards " +
			match1 + " and " + match2 + "\n";
			match1.setFound();
			match2.setFound();
			player1++;
			print += this.playerOne();
		}
		else{
			if (!this.isInMemory(match1)){
				match1.flip();
				this.memory.add(match1);
			}
			if (!this.isInMemory(match2)){
				match2.flip();
				this.memory.add(match1);
			}
	    //flip cards for next turn
			if (!match1.getFaceDown()){
				match1.flip();
			}
			if (!match2.getFaceDown()){
				match2.flip();
			}
		}
		return print;
	}

	public String playerTwo(){

	//clear table for player two's move
		String print = "";

	//choose cards
		Card match1 = this.chooseRandomCard();

		if (!this.playing){
			return "";
		}

	//flip facedown card
		if (match1.getFaceDown()){
			match1.flip();
		}

	//remove from memory so we dont get duplicates
		if (this.isInMemory(match1)){
			this.memory.remove(match1);
		}

		Card match2;
	//shuffle memory deck and pick first element
	//if nothing in memory, pick random card
		if (this.memory.size() > 0){
			Collections.shuffle(this.memory);
			match2 = this.memory.get(0);
		}
		else {
			match2 = this.chooseRandomCard();
		}

		if (match2.getFaceDown()){
			match2.flip();
		}

		print += this.printDeck();

		if (this.matches(match1, match2)){
			print += "Congrats! You matched the cards " +
			match1 + " and " + match2 + "\n";
			match1.setFound();
			match2.setFound();
			player2++;
			print += this.playerTwo();
		}
		else{

	    //add to memory if not already there
			if (!this.isInMemory(match1)){
				match1.flip();
				this.memory.add(match1);
			}
			if (!this.isInMemory(match2)){
				match2.flip();
				this.memory.add(match2);
			}
            //flip cards for next turn                                                    
			if (!match1.getFaceDown()){
				match1.flip();
			}
			if (!match2.getFaceDown()){
				match2.flip();
			}


		}
		return print;
	}

	public String gameEnd(){
		String print = "Winner of the game: ";

		if (player1 > player2){
			print+= "Player 1 with " + player1 + " matches!";;
		}
		else if (player2 > player1){
			print += "Player 2 with " + player2 + " matches!";
		}
		else {
			print = "it was a tie";
		}

		return print;
	}

	public static void main(String[] Args) {
		
		Random r = new Random();
		int random = 0;

		while (random == 0){
			random = r.nextInt(40);
		}
		
		try{
			PrintWriter output = new PrintWriter( new FileOutputStream("out.txt"));
	    //play random number (between 1 and 40) of games
			for (int i = 0; i < random; i++){
				game g = new game();
				g.setDeck();
				while (g.isPlaying()){
					output.println(g.playerOne());
					output.println(g.playerTwo());
				}
				output.println(g.gameEnd());
			}
			output.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Cannot output to out.txt??");
		}
	}
	
}