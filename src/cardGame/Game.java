package cardGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import cardGame.Card.statusType;

/**The gameboard
 * @author Akmal
 *
 */
public class Game {
	/**Coin value. Can only be Heads or Tails
	 * @author Akmal
	 *
	 */
	public static enum coinValue{Heads, Tails}
	/**The game mode values. Can be either Player, AI, or Watch
	 * @author Akmal
	 *
	 */
	public static enum gameMode{player,ai,watch}
	/**File name that stores the scores
	 * 
	 */
	public static String scoreFile = "Score.txt";
	
	Scanner command = new Scanner(System.in);
	
	private Player p1;
	private Player p2;
	
	/**Build a game with two players in it
	 * @param p1 Represents Player 1. Default name is "Rara"
	 * @param p2 Represents Player 2. Default name is "Rasputin"
	 */
	public Game(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**Starts the game. Players can choose to start game or display top 10 scores
	 * If start game, players can choose to play against each other, play against the AI, or watch two AI play
	 * @throws IOException Exception thrown when an invalid input is detected
	 */
	public void start() throws IOException {
		System.out.println("Choose Option:\n1. Start Game\n2. Display top 10 scores");
		System.out.printf("Option:");
		int gameOption = command.nextInt();
		switch(gameOption) {
		case 1:
			System.out.println("Choose Game Type:\n1. Vs Player\n2. Vs AI\n3. Watch");
			System.out.printf("Option:");
			int gameType = command.nextInt();
			gameMode gm = null;
			if(gameType == 2) {
				gm = gameMode.ai;
			}else if(gameType == 3) {
				gm = gameMode.watch;
			}else {
				gm = gameMode.player;
			}
			
			System.out.printf("Insert name for Player 1:");
			String np1 = command.next();
			p1.setName(np1);
			System.out.printf("Insert name for Player 2:");
			String np2 = command.next();
			p2.setName(np2);
			
			Player attacker = null;
			Player defender = null;
			
			System.out.println("Drawing cards for Player 1...");
			p1.populateDeck();
			System.out.println("Drawing cards for Player 2...");
			p2.populateDeck();
			System.out.println("Flipping coin to determine turn order");
			coinValue coin = flipCoin();
			if(coin == coinValue.Heads) {
				System.out.println("Heads. Player 1 starts first");
				attacker = p1;
				defender = p2;
				
			}else {
				System.out.println("Tails. Player 2 starts first");
				attacker = p2;
				defender = p1;
			}
			
			int turn = 1;
			int turnCounter = 1;
			while(defender.getLives() != 0) {
				System.out.println("Turn "+turn);
				System.out.println(attacker.getName()+"'s turn");
				commands(attacker, defender, gm);
				
				
				if(defender.getLives() != 0) {
					if(attacker.getName().equals(p1.getName())) {
						attacker = p2;
						defender = p1;
					}else {
						attacker = p1;
						defender = p2;
					}
					turn+=(turnCounter/2);
					turnCounter++;
					if(turnCounter>2) {
						
						for(Card c: attacker.getDeck()) {
							if(c.getStatusCounter()>0) {
								c.setStatusCounter(c.getStatusCounter()-1);
							}
							
							if(c.getStatusCounter() == 0 && c.getStatus() != statusType.Active) {
								c.setStatus(statusType.Active);
							}
						}
						
						for(Card c: defender.getDeck()) {
							if(c.getStatusCounter()>0) {
								c.setStatusCounter(c.getStatusCounter()-1);
							}
							
							if(c.getStatusCounter() == 0 && c.getStatus() != statusType.Active) {
								c.setStatus(statusType.Active);
							}
						}
						
						turnCounter = 1;
					}
				}else {
					System.out.println(attacker.getName()+" is the winner!");
					attacker.setScore(100000/turn);
					if(attacker.getScore()<=0) {
						attacker.setScore(0);
					}
					System.out.println("Turns:"+turn+" Score:"+attacker.getScore());
					writeToScore(scoreFile,attacker);
				}
			}
			break;
		case 2:
			getScoreBoard(scoreFile);
			break;
		default:
			System.out.println("Invalid command. Please restart the game");
			break;
		}
	}
	
	/**Simulates a coin flip. Used to determine turn order. Value can only be Heads or Tails
	 * @return enum value representing Heads or Tails
	 */
	public coinValue flipCoin() {
		Random r = new Random();
		int coin = r.nextInt(100);
		if(coin >=49) {
			return coinValue.Heads;
		}else {
			return coinValue.Tails;
		}
	}
	
	/**List of commands available to the player. 
	 * "Attack" lets players attack.
	 * "Recharge" lets players recharge their cards. Calls the recharge() method of a card
	 * "Train" lets players gain experience at the cost of energy points
	 * @param attacker Represents attacking player
	 * @param defender Represents defending player
	 * @param gm Represents current game mode. Turns on AI depending on current game mode 
	 */
	public void commands(Player attacker, Player defender, gameMode gm) {
		
		boolean finish = false;
		Random aiMove = new Random();
		
		while(!finish) {
			System.out.println("Option 1:Attack\nOption 2:Recharge\nOption 3:Train");
			System.out.printf("Choose Option:");
			int option = 0;
			if((attacker.getName().equals(p2.getName()) && gm == gameMode.ai)||(gm == gameMode.watch)){
				option = aiMove.nextInt(3)+1;
				System.out.print(option);
			}else {
				option = command.nextInt();
			}
			System.out.println();
			System.out.println();
			attacker.showDeck();
			System.out.println();
			System.out.println();
			switch(option){
			case 1:
				System.out.printf("Choose attacker:");
				int atkTarget = 0;
				if((attacker.getName().equals(p2.getName()) && gm == gameMode.ai)||(gm == gameMode.watch)){
					atkTarget = aiMove.nextInt(attacker.getDeck().size())+1;
					System.out.println(atkTarget);
					atkTarget = atkTarget-1;
				}else {
					atkTarget = command.nextInt()-1;
				}
				if(canMove(attacker,atkTarget)) {
					if(attacker.getCard(atkTarget).getEnergyValue() < 1) {
						System.out.println("Not enough energy. Choose another command");
					}else {
						Card a = attacker.getCard(atkTarget);
						System.out.println();
						System.out.println();
						defender.showDeck();
						System.out.println();
						System.out.println();
						System.out.printf("Choose target:");
						int defTarget = 0;
						if((attacker.getName().equals(p2.getName()) && gm == gameMode.ai)||(gm == gameMode.watch)){
							defTarget = aiMove.nextInt(defender.getDeck().size())+1;
							System.out.println(defTarget);
							defTarget = defTarget-1;
						}else {
							defTarget = command.nextInt()-1;
						}
						if(defTarget > defender.getDeck().size()-1 || defTarget < 0) {
							System.out.println("Invalid card. Please choose another command");
						}else {
							Card b = defender.getCard(defTarget);
							
							if(sameEnergy(a,b) && a.getEnergyValue() <2) {
								System.out.println("Not enough energy. Choose another command");
							}else {
								damageCalculator(attacker, atkTarget, defender, defTarget);
								finish = true;
								System.out.println();
							}
						}
					}
				}
				
				break;
			case 2:
				System.out.printf("Choose target:");
				int rechargeTarget = 0;
				if((attacker.getName().equals(p2.getName()) && gm == gameMode.ai)||(gm == gameMode.watch)){
					rechargeTarget = aiMove.nextInt(defender.getDeck().size())+1;
					System.out.println(rechargeTarget);
					rechargeTarget = rechargeTarget-1;
				}else {
					rechargeTarget = command.nextInt()-1;
				}
				if(canMove(attacker,rechargeTarget)) {
					attacker.getCard(rechargeTarget).recharge();
					finish = true;
					System.out.println();
				}
				break;
			case 3:
				System.out.printf("Choose target:");
				int trainTarget = 0;
				if((attacker.getName().equals(p2.getName()) && gm == gameMode.ai)||(gm == gameMode.watch)){
					trainTarget = aiMove.nextInt(defender.getDeck().size())+1;
					System.out.println(trainTarget);
					trainTarget = trainTarget-1;
				}else {
					trainTarget = command.nextInt()-1;
				}
				if(canMove(attacker,trainTarget)) {
					if(attacker.getCard(trainTarget).getEnergyValue()<5) {
						System.out.println("Not enough energy. Please choose another command");
					}else {
						attacker.getCard(trainTarget).increaseExp();
						attacker.getCard(trainTarget).setEnergyValue(attacker.getCard(trainTarget).getEnergyValue()-5);
						System.out.println("Training complete!");
						finish = true;
						System.out.println();
					}
				}
				break;
			default:
				System.out.println("Please choose a valid option");
			}
		}
		
	}
	
	/**Checks if a choice is valid. 
	 * @param p The player conducting the choice
	 * @param i Represent player choice
	 * @return Return true if valid. False otherwise
	 */
	public boolean canMove(Player p, int i) {
		boolean move = false;
		if(i > p.getDeck().size()-1 || i < 0) {
			System.out.println("Invalid card. Please choose another command");
		}else {
			if(p.getCard(i).getStatus()!= statusType.Active) {
				System.out.println("Unable to move. Please choose another command");
			}else {
				move = true;
			}
		}
		return move;
	}
	
	/**Checks if two cards are of the same energy type
	 * @param a Represents first card
	 * @param b Represents second card
	 * @return Return true if same energy type. False otherwise
	 */
	public boolean sameEnergy(Card a, Card b) {
		if(a.getEnergyType().equals(b.getEnergyType())) {
			return true;
		}else {
			return false;
		}
	}
	
	/**Calculates the damage dealt to an opposing card.
	 * Takes into account Card type, Card Energy type and Card current status
	 * @param a Represents attacking player
	 * @param attacker Represent Card used by attacker
	 * @param b Represents defending player
	 * @param defender Represent Card used by defender
	 */
	public void damageCalculator(Player a, int attacker, Player b, int defender) {
		int atkModifier = 0;
		int defModifier = 0;
		int weakness = 1;
		int statusMultiplier = 1;
		int energyCost = 1;
		
		Card atk = a.getCard(attacker);
		Card def = b.getCard(defender);
		
		if(atk instanceof Attacker) {
			atkModifier = ((Attacker) atk).getAtkModifier();
		}
		
		if(def instanceof Defender) {
			defModifier = ((Defender) def).getDefModifier();
		}
		
		if(sameEnergy(atk,def)) {
			weakness = 2;
			energyCost = 2;
		}
		
		if(def.getStatus() != statusType.Active) {
			statusMultiplier = 2;
		}
		
		int damage = statusMultiplier*((weakness*1)+atkModifier)-defModifier;
		
		if(damage <= 0) {
			damage = 0;
		}
		
		System.out.println("Dealt "+damage+" points of damage!");		
		
		def.setCurrDamage(def.getCurrDamage()+damage);
		
		if(atk instanceof Fairy) {
			((Fairy) a.getCard(attacker)).specialSkill(b.getCard(defender));
		}
		
		if((def.getMaxHP() - def.getCurrDamage()) <=0) {
			System.out.println("Knock out!");
			b.getDeck().remove(defender);
			b.reduceLife();
		}
		
		a.getCard(attacker).setEnergyValue(a.getCard(attacker).getEnergyValue()-energyCost);
		
		if(atk instanceof Attacker) {
			((Attacker) a.getCard(attacker)).increaseExp();
		}else if(atk instanceof Defender) {
			((Defender) a.getCard(attacker)).increaseExp();
		}else {
			a.getCard(attacker).increaseExp();
		}
		
	}
	
	/**Get the top 10 scores from "Score.txt"
	 * @param textFile Represents "Score.txt"
	 * @throws FileNotFoundException Exception thrown if file is not found
	 */
	public void getScoreBoard(String textFile) throws FileNotFoundException {
		ArrayList<Player> playerScore = new ArrayList<Player>();
		File f = new File(textFile);
		Scanner s = new Scanner(f);
		while(s.hasNextLine()) {
			String data = s.nextLine();
			String[] splitData = data.split(":|\\ ");
			Player p = new Player(splitData[1]);
			p.setScore(Integer.parseInt(splitData[3]));
			playerScore.add(p);
		}
		int i = 1;
		for(Player p:playerScore) {
			System.out.println(i+" - "+p);
			i++;
		}
		
		s.close();
	}
	
	/**Write score to "Score.txt" and automatically arranges according to highest score
	 * @param textFile Represents "Score.txt"
	 * @param newPlayer Represent player name to put in "Score.txt"
	 * @throws IOException Exception thrown if invalid input detected
	 */
	public void writeToScore(String textFile, Player newPlayer) throws IOException{
		ArrayList<Player> playerScore = new ArrayList<Player>();
		File f = new File(textFile);
		Scanner s = new Scanner(f);
		
		//Get names and score from "Score.txt" and store in ArrayList
		while(s.hasNextLine()) {
			String data = s.nextLine();
			String[] splitData = data.split(":|\\ ");
			Player p = new Player(splitData[1]);
			p.setScore(Integer.parseInt(splitData[3]));
			playerScore.add(p);
		}
		s.close();
		
		//Add score of new player and sort by highest score
		playerScore.add(newPlayer);
		playerScore.sort(Comparator.comparing(Player::getScore).reversed());
		
		//Write top 10 players to "Score.txt"
		FileWriter fw = new FileWriter(f);
		for(int i=0; i<playerScore.size();i++) {
			if(i == 0) {
				fw.write(playerScore.get(i).toString()+"\n");
			}else {
				if(i<10) {
					fw.append(playerScore.get(i).toString()+"\n");
				}
				
			}
		}
		fw.close();
	}
}
