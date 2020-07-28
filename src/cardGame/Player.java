package cardGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**The Player
 * @author Akmal
 *
 */
public class Player {
	private String name;
	private int lives;
	private int score;
	private ArrayList<Card> deck;
	
	/**Creates a Player with a name, 3 lives, score set to 0 and an empty card list
	 * @param n Player name
	 */
	public Player(String n) {
		this.name = n;
		this.lives = 3;
		this.setScore(0);
		this.deck = new ArrayList<Card>();
	}

	/**Set player name
	 * @param name value of player name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**Get player name
	 * @return value of player name
	 */
	public String getName() {
		return name;
	}


	/**Set a player's card list
	 * @param deck card list to set
	 */
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}
	
	/**Gets the card list of a player
	 * @return Card list of a player
	 */
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	/**Get card from a player's card list if it exists
	 * @param i Card to get
	 * @return Card from card list if it exists
	 */
	public Card getCard(int i) {
		if(i > deck.size()-1) {
			System.out.println("Please choose a valid card");
			return null;
		}else {
			return deck.get(i);
		}
	}
	
	/**Generate card list for a player. Shuffle the cards afterwards
	 * A card list must have at least 2 Attackers and one Defender.
	 * Remaining cards are chosen at random.
	 * Card parameters are randomly generated.
	 * Card Hit Point is between 50 - 80.
	 * Card Energy Value is between 20 - 50.
	 * Attacker modifier value is between 2 - 5.
	 * Defender modifier value is between 1 - 3.
	 */
	public void populateDeck() {
		
		Random r = new Random();
		
		//Add Attacker
		for(int i = 0; i<2;i++) {			
			deck.add(new Attacker(r.nextInt(80-50+1)+50,r.nextInt(50-20+1)+20,r.nextInt(4),r.nextInt(5-2)+2));
		}
		
		//Add defender
		deck.add(new Defender(r.nextInt(80-50+1)+50,r.nextInt(50-20+1)+20,r.nextInt(4),r.nextInt(3-1)+1));
		
		//Add remaining cards. Types chosen at random
		for (int j = 0; j<3; j++) {
			int cardType = r.nextInt(3);
			switch(cardType) {
			case 0:
				deck.add(new Attacker(r.nextInt(80-50+1)+50,r.nextInt(50-20+1)+20,r.nextInt(4),r.nextInt(5-2)+2));
				break;
			case 1:
				deck.add(new Defender(r.nextInt(80-50+1)+50,r.nextInt(50-20+1)+20,r.nextInt(4),r.nextInt(3-1)+1));
				break;
			case 2:
				deck.add(new Fairy(r.nextInt(80-50+1)+50,r.nextInt(50-20+1)+20,r.nextInt(4)));
				break;
			}
			
		}
		
		Collections.shuffle(deck);
	}

	/**Show the player's card list along with the relevant card information
	 * 
	 */
	public void showDeck() {
		System.out.println(this.getName()+"'s cards:");
		int i = 1;
		for(Card c: this.deck) {
			
			System.out.println("No:"+i+" "+c);
			i++;
		}
	}

	/**Get a player's amount of lives
	 * @return amount of lives left for player
	 */
	public int getLives() {
		return lives;
	}

	/**Set the number of lives a player has
	 * @param lives Value to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	
	
	/**Reduce a player's life by 1 if their card is defeated.
	 * Player loses when their life is reduced to 0
	 */
	public void reduceLife() {
		this.setLives(this.getLives()-1);
	}

	/**Get a player's score
	 * @return Player's score
	 */
	public int getScore() {
		return score;
	}

	/**Set the player's current score
	 * @param score Amount to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**Prints out the Player name and score if called
	 *
	 */
	public String toString() {
		return "Player:"+this.getName()+" Score:"+this.getScore();
	}
}
