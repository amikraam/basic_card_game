package cardGame;
import java.util.Random;

/**The Fairy type Card. Inherits methods from the base Card object
 * @author Akmal
 *
 */
public class Fairy extends Card {
	
	/**Create a Fairy card with defined parameters
	 * @param mhp Fairy Hit Points
	 * @param ev Fairy Energy Value
	 * @param et Fairy Energy Type
	 */
	public Fairy(int mhp, int ev, int et) {
		super(mhp,ev,et);
	}
	
	/**Changes a target Card's current status.
	 * Select a random number between 0 to 99
	 * If 49 or above, target is poisoned and cannot move for 1 turn
	 * Otherwise, target is paralyzed and cannot move for 2 turns
	 * @param target The target Card
	 */
	public void specialSkill(Card target) {
		Random r = new Random();
		int coinToss = r.nextInt(100);
		if(coinToss >=49) {
			System.out.println("Target is poisoned!");
			target.setStatus(statusType.Poison);
			target.setStatusCounter(1);
		}else {
			System.out.println("Target is paralyzed!");
			target.setStatus(statusType.Paralyze);
			target.setStatusCounter(2);
		}
	}
}
