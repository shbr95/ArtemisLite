/**
 * 
 */
package artemislite;

import java.util.Random;

/**
 * @author cbuch
 *
 *         A class that represents the rolling of two dice, generating two
 *         random numbers and then totalling
 */
public class Dice {

	private int diceRollFirst;
	private int diceRollSecond;
	private int total;

	private static final int MIN_DICE_ROLL = 1;
	private static final int MAX_DICE_ROLL = 6;
	private static final int MIN_TOTAL = 2;
	private static final int MAX_TOTAL = 12;

	public Dice() {

	}

	public void rollDice() {

		Random rand = new Random();

		this.diceRollFirst = rand.nextInt(MAX_DICE_ROLL) + MIN_DICE_ROLL;
		this.diceRollSecond = rand.nextInt(MAX_DICE_ROLL) + MIN_DICE_ROLL;
		this.total = this.diceRollFirst + this.diceRollSecond;

	}

	/**
	 * @return the diceRollFirst
	 */
	public int getDiceRollFirst() {
		return diceRollFirst;
	}

	/**
	 * @param diceRollFirst the diceRollFirst to set
	 */
	public void setDiceRollFirst(int diceRollFirst) {

		if (diceRollFirst >= MIN_DICE_ROLL && diceRollFirst <= MAX_DICE_ROLL) {
			this.diceRollFirst = diceRollFirst;
		} else {
			throw new IllegalArgumentException("Error throwing dice. Die can only accept a number 1-6");
		}
	}

	/**
	 * @return the diceRolSecond
	 */
	public int getDiceRollSecond() {
		return diceRollSecond;
	}

	/**
	 * @param diceRolSecond the diceRolSecond to set
	 */
	public void setDiceRollSecond(int diceRollSecond) {

		if (diceRollSecond >= MIN_DICE_ROLL && diceRollSecond <= MAX_DICE_ROLL) {
			this.diceRollSecond = diceRollSecond;
		} else {
			throw new IllegalArgumentException("Error throwing dice. Die can only accept a number 1-6");
		}
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {

		if (total >= MIN_TOTAL && total <= MAX_TOTAL) {
			this.total = total;
		} else {
			throw new IllegalArgumentException("Error throwing dice. Total from dice throw must be number from 2-12");
		}
	}

}
