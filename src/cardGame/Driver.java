package cardGame;

import java.io.IOException;

/**Creates and starts the game
 * @author Akmal
 *
 */
public class Driver {

	/**Main game driver
	 * @param args Any addition arguments if necessary
	 * @throws IOException Throws exception if invalid input detected
	 */
	public static void main(String[] args) throws IOException{
		
		Player p1 = new Player("Rara");
		Player p2 = new Player("Rasputin");
		Game g = new Game(p1,p2);
		g.start();
		
	}

}
