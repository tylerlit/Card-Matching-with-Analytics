//file for card class
public class Card{
	
	private Suits suit;
	private int rank;
	private String card;
	private boolean isFaceDown;
	private boolean isFound;

	public enum Suits{
		S, H, D, C;	    

		private String print;

		public String toString(){

			if (this == S){ print = "S"; }
			if (this == H){ print = "H"; }
			if (this == D){ print = "D"; }
			if (this == C){ print = "C"; }
			
			return this.print;

		}

	}
	public Card(){}

	public Card(Suits suit, int rank){
		this.suit = suit;
		this.rank = rank;
		this.isFaceDown = true;
		this.isFound = false;
	}
	
	public int getRank(){
		return this.rank;
	}

	public Suits getSuit(){
		return this.suit;
	}
	
	public boolean getFaceDown(){
		if (this.isFaceDown){
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFound(){
		return this.isFound;
	}

	public void setFound(){
		if (this.isFound){
			this.isFound = false;
		}
		else {
			this.isFound = true;
		}
	}

	public void flip(){
		if (this.isFaceDown){
			this.isFaceDown = false;
		}
		else {
			this.isFaceDown = true;
		}
	}
	
    //give cards their abbreviations for printing
	public String toString(){

		if (this.isFound()){
			return "XX";
		}
		
		if (!isFaceDown){
			switch(this.rank){
				case 0:
				this.card = "A" + this.suit;
				break;
				case 1:
				this.card = "J" + this.suit;
				break;
				case 10:
				this.card = "T" + this.suit;
				break;
				case 11:
				this.card = "Q" + this.suit;
				break;
				case 12:
				this.card = "K" + this.suit;
				break;
				default:
				this.card = this.rank + "" +  this.suit;
				
			}
			
			return card;
			
		}
		else{
			return "OO";
		}
	}
	
	public boolean equals(Object other){
		
		if(!(other instanceof Card) || other == null)
		{
			return false;
		}
		Card otherCard = (Card) other;

		if((this.getSuit() == Suits.H && otherCard.getSuit() == Suits.D) ||
			(this.getSuit() == Suits.D && otherCard.getSuit() == Suits.H) ||
			(this.getSuit() == Suits.S && otherCard.getSuit() == Suits.C) ||
			(this.getSuit() == Suits.C && otherCard.getSuit() == Suits.S)){
			if(this.getRank() == otherCard.getRank()){
				return true;
			}
		}
		return false;
	}
}