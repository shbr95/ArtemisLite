package artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import artemislite.Dice;

class TestDice {

	Dice dice;
	int diceRollValidLower, diceRollValidUpper, diceRollInvalidLower, diceRollInvalidUpper, totalValidLower,
			totalValidUpper, totalInvalidLower, totalInvalidUpper;

	@BeforeEach
	void setUp() throws Exception {

		dice = new Dice();

		diceRollValidLower = 1;
		diceRollValidUpper = 6;
		diceRollInvalidLower = 0;
		diceRollInvalidUpper = 7;
		totalValidLower = 2;
		totalValidUpper = 12;
		totalInvalidLower = 1;
		totalInvalidUpper = 13;

	}

	@Test
	void testDefaultConstructor() {

		Dice testConstructorDice = new Dice();
		assertNotNull(testConstructorDice);

	}

	@Test
	void testRollDice() {
		dice.rollDice();
		assertTrue(dice.getDiceRollFirst() >= 1 && dice.getDiceRollFirst() <= 6);
		assertTrue(dice.getDiceRollSecond() >= 1 && dice.getDiceRollSecond() <= 6);
		assertTrue(dice.getTotal() >= 2 && dice.getTotal() <= 12);

		assertEquals(dice.getTotal(), (dice.getDiceRollFirst() + dice.getDiceRollSecond()));

	}

	@Test
	void testGetSetDiceRollFirstValid() {

		dice.setDiceRollFirst(diceRollValidLower);
		assertEquals(dice.getDiceRollFirst(), diceRollValidLower);

		dice.setDiceRollFirst(diceRollValidUpper);
		assertEquals(dice.getDiceRollFirst(), diceRollValidUpper);
	}

	@Test
	void testSetDiceRollFirstInvalid() {

		assertThrows(Exception.class, () -> {
			dice.setDiceRollFirst(diceRollInvalidLower);
		});

		assertThrows(Exception.class, () -> {
			dice.setDiceRollFirst(diceRollInvalidUpper);
		});

	}

	@Test
	void testGetSetDiceRollSecondValid() {
		dice.setDiceRollSecond(diceRollValidLower);
		assertEquals(dice.getDiceRollSecond(), diceRollValidLower);

		dice.setDiceRollSecond(diceRollValidUpper);
		assertEquals(dice.getDiceRollSecond(), diceRollValidUpper);
	}

	@Test
	void testSetDiceRollSecondInvalid() {
		assertThrows(Exception.class, () -> {
			dice.setDiceRollSecond(diceRollInvalidLower);
		});

		assertThrows(Exception.class, () -> {
			dice.setDiceRollSecond(diceRollInvalidUpper);
		});
	}

	@Test
	void testGetSetTotalValid() {

		dice.setTotal(totalValidLower);
		assertEquals(dice.getTotal(), totalValidLower);

		dice.setTotal(totalValidUpper);
		assertEquals(dice.getTotal(), totalValidUpper);
	}

	@Test
	void testSetTotalInvalid() {

		assertThrows(Exception.class, () -> {
			dice.setTotal(totalInvalidLower);
		});

		assertThrows(Exception.class, () -> {
			dice.setTotal(totalInvalidUpper);
		});

	}

}
