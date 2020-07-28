package cardGame;
import java.util.Random;

/**The basic card structure
 * @author Akmal
 *
 */
public class Card {
	private int stage;
	private int exp;
	private int maxHP;
	private int currDamage;
	private int energyValue;
	private String energyType;
	private String[] elementList = {"Red","Blue","Green","Colorless"};
	private statusType status;
	private int statusCounter;
	/**A card's status. Can be Active, Poison or Paralyze
	 * @author Akmal
	 *
	 */
	public static enum statusType {Active, Poison, Paralyze};
	
	
	/**Creates a card object with Hit Point value, Energy value and Energy type 
	 * @param mhp The card's hit point value
	 * @param ev The card's energy value
	 * @param et The card's energy type
	 */
	public Card(int mhp, int ev, int et) {
		this.setMaxHP(mhp);
		this.energyValue = ev;
		this.energyType = elementList[et];
		this.setStage(0);
		this.exp = 0;
		this.currDamage = 0;
		this.status = statusType.Active;
		this.statusCounter = 0;
	}

	/**Set the base amount of hit points the card has
	 * @param maxHP The base amount of hit points to set
	 */
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	/**Return the base amount of hit points the card has
	 * @return int value representing base amount of hit points
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**Sets the current amount of damage taken by this card
	 * @param currDamage value of current damage
	 */
	public void setCurrDamage(int currDamage) {
		this.currDamage = currDamage;
	}

	/**Returns current amount of damage taken by this card
	 * @return int value of current damage
	 */
	public int getCurrDamage() {
		return currDamage;
	}

	/**Set amount of energy this card has
	 * @param energyValue amount of energy to set
	 */
	public void setEnergyValue(int energyValue) {
		this.energyValue = energyValue;
	}

	/**Returns amount of energy this card has
	 * @return int representing card's energy value
	 */
	public int getEnergyValue() {
		return energyValue;
	}

	/**Return energy type this card has
	 * @return String representing energy type
	 */
	public String getEnergyType() {
		return energyType;
	}

	/**Get the card's current experience value
	 * @return int representing the card's current experience value
	 */
	public int getExp() {
		return exp;
	}

	/**Set the card's experience value
	 * @param exp value to set the card's experience value
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**Return the list of Energy types available for all cards
	 * @return String array representing Energy types
	 */
	public String[] getElementList() {
		return elementList;
	}

	/**Allows the card to add energy to itself if it gets the same energy type.
	 * If the card's energy type is Colorless, add energy regardless of what energy card is drawn
	 */
	public void recharge() {
		Random r = new Random();
		String getEnergy = elementList[r.nextInt(4)];
		System.out.println("Draw card... Color drawn:"+getEnergy);
		if(getEnergy.equals(this.energyType) || this.energyType.equals(elementList[3])) {
			System.out.println("Success!");
			this.setEnergyValue(this.getEnergyValue()+5);
		}else {
			System.out.println("Not matched. Energy value unchanged");
		}
	}

	/**Return current status of a card. Values can only be Active, Poison, or Paralyze
	 * @return enum representing card status
	 */
	public statusType getStatus() {
		return status;
	}

	/**Set the status of the card. Values can only be Active, Poison, or Paralyze
	 * @param status enum representing card status
	 */
	public void setStatus(statusType status) {
		this.status = status;
	}

	/**Get the amount of turns left before a card can move.
	 * Default value is 0, meaning a card can take action during a turn
	 * @return int representing the amount of turns
	 */
	public int getStatusCounter() {
		return statusCounter;
	}

	/**Set the amount of turns a card cannot move.
	 * If set to 0, cards can move during their turn
	 * @param statusCounter value to set
	 */
	public void setStatusCounter(int statusCounter) {
		this.statusCounter = statusCounter;
	}
	
	/**Increase a card's experience by 1. 
	 * If experience is equal to 20, a card levels up by 1.
	 * A card's hit point and energy value is also doubled upon level up
	 */
	public void increaseExp() {
		this.exp++;
		if(this.exp>=20) {
			System.out.println("Level up!");
			setMaxHP(getMaxHP()*2);
			setEnergyValue(getEnergyValue()*2);
			this.setStage(this.getStage() + 1);
			this.exp = 0;
		}
	}

	/**Get the card's current level. Default value is 0
	 * @return int representing card's level
	 */
	public int getStage() {
		return stage;
	}

	/**Sets the card's level
	 * @param stage Value to set
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	/**Information to print out if a card is called
	 *
	 */
	public String toString() {
		return "Type:"+this.getClass().getSimpleName()+" Stage:"+this.getStage()+" Experience:"+this.getExp()+" HP:"+(this.getMaxHP()-this.getCurrDamage())+" Energy:"+this.getEnergyValue()+" Energy Colour:"+this.getEnergyType()+" Attack Point: - Resistance Point: -"+" Status:"+this.getStatus();
	}

}
