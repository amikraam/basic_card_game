package tester;
import java.io.IOException;

import cardGame.*;

/**Class used to test class methods and functionality
 * @author Akmal
 *
 */
public class TestBench {

	public static void main(String[] args) throws IOException {
//		Player p1 = new Player("Jim");
//		p1.populateDeck();
//		p1.showDeck();
		
//		Card c = new Card(50, 30, 1);
//		System.out.println(c);
//		c.setExp(19);
//		c.increaseExp();
//		System.out.println(c);
		
//		Defender d = new Defender(60,20,2,1);
//		Fairy f = new Fairy(70,18, 1);
//		
//		System.out.println(d);
//		System.out.println(f);
//		System.out.println();
//		System.out.println("Fairy Attacks");
//		f.specialSkill(d);
//		System.out.println();
//		System.out.println(d);
		
		Player p1 = new Player("Player1");
		Player p2 = new Player("Player2");
		Game g = new Game(p1,p2);
		g.start();
		
	}

}
