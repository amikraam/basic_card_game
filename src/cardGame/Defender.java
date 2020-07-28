package cardGame;

/**The Defender type card. Inherits methods from the base Card object 
 * @author Akmal
 *
 */
public class Defender extends Card {
	private int defModifier;
	
	/**Create a Defender card with defined parameters
	 * @param mhp Defender's Hit Point
	 * @param ev Defender's Energy Value
	 * @param et Defender's Energy Type
	 * @param dm Defender's Defense Modifier Value
	 */
	public Defender(int mhp, int ev, int et, int dm) {
		super(mhp,ev,et);
		this.defModifier = dm;
	}

	/**Get Defender's modifier value
	 * @return int as modifier value
	 */
	public int getDefModifier() {
		return defModifier;
	}

	/**Set Defender's modifier value
	 * @param defModifier Value to set as modifier value
	 */
	public void setDefModifier(int defModifier) {
		this.defModifier = defModifier;
	}
	
	/**Increase experience of Defender.
	 *Level up when experience value is 20 
	 *Doubles Hit Point, Energy Value and defend Modifier Value upon level up.
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
			this.setDefModifier(this.getDefModifier()*2);
		}
	}
	
	/**Information to print when called
	 *
	 */
	public String toString() {
		return "Type:"+this.getClass().getSimpleName()+" Stage:"+this.getStage()+" Experience:"+this.getExp()+" HP:"+(this.getMaxHP()-this.getCurrDamage())+" Energy:"+this.getEnergyValue()+" Energy Colour:"+this.getEnergyType()+" Attack Point: - Resistance Point:"+this.getDefModifier()+" Status:"+this.getStatus();
	}
}
