package cardGame;

/**The Attacker type card. Inherits methods from the base Card object
 * @author Akmal
 *
 */
public class Attacker extends Card {
	private int atkModifier;
	
	/**Create an Attacker card with defined parameters
	 * @param mhp Attacker's Hit Point
	 * @param ev Attacker's Energy value
	 * @param et Attacker's Energy type
	 * @param am Attacker's Attack Value Modifier
	 */
	public Attacker(int mhp, int ev, int et, int am) {
		super(mhp,ev,et);
		this.atkModifier = am;
	}

	/**Get Attacker's attack modifier value
	 * @return int representing attack modifier value
	 */
	public int getAtkModifier() {
		return atkModifier;
	}

	/**Set Attacker's attack modifier value
	 * @param atkModifier Attack modifier value to set
	 */
	public void setAtkModifier(int atkModifier) {
		this.atkModifier = atkModifier;
	}
	
	/**Increase experience of attacker.
	 *Level up when experience value is 20 
	 *Doubles Hit Point, Energy Value and Attack Modifier Value upon level up.
	 *Overrrides increaseExp() from Card.
	 */
	public void increaseExp() {
		this.setExp(this.getExp()+1);
		if(this.getExp() >= 20) {
			System.out.println("Level up!");
			setMaxHP(getMaxHP()*2);
			setEnergyValue(getEnergyValue()*2);
			this.setStage(this.getStage() + 1);
			this.setExp(0);
			this.setAtkModifier(this.getAtkModifier()*2);
		}
		
	}
	
	/**Information to print when called
	 *
	 */
	public String toString() {
		return "Type:"+this.getClass().getSimpleName()+" Stage:"+this.getStage()+" Experience:"+this.getExp()+" HP:"+(this.getMaxHP()-this.getCurrDamage())+" Energy:"+this.getEnergyValue()+" Energy Colour:"+this.getEnergyType()+" Attack Point:"+ this.getAtkModifier()+" Resistance Point: -"+" Status:"+this.getStatus();
	}
}
