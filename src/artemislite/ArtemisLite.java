package artemislite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ArtemisLite {

	/**
	 * Instantiate sound class, gives access to sound methods
	 */
	private static Sound sound = new Sound();

	/**
	 * Used to set the time of a thread sleep
	 */
	private static final long LONGSLEEP = 500;

	/**
	 * Used to set the time of a thread sleep
	 */
	private static final long SHORTSLEEP = 300;

	/**
	 * Scanner to allow users to input responses to the console when prompted
	 */
	private static Scanner scanner = new Scanner(System.in);
	/**
	 * Declaring the board as a TreeMap which stores the elements
	 */
	private static Map<Integer, Element> board = new TreeMap<>();
	/**
	 * Ends the game if one of the conditions is met to change it to false
	 */
	private static boolean continueGame = true;
	/**
	 * ArrayList which stores the player names when they're entered
	 */
	private static ArrayList<String> playerNames = new ArrayList<String>();
	/**
	 * ArrayList to store the players
	 */
	private static ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * The maximum numbers of players allowed in the game
	 */
	private static final int MAX_NUM_OF_PLAYERS = 4;
	/**
	 * The minimum number of players allowed in the game
	 */
	private static final int MIN_NUM_OF_PLAYERS = 2;
	/**
	 * The number of elements on the board
	 */
	private static final int NUM_OF_ELEMENTS = 12;
	/**
	 * The player's starting position on the board
	 */
	private static final int START_POSITION = 0;
	/**
	 * The final position on the board
	 */
	private static final int LAST_POSITION = 11;
	/**
	 * The maximum possible dice roll (two virtual dice)
	 */
	private static final int MAX_DICE_ROLL = 12;

	/**
	 * Main method to run the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// prints ArtemisLite logo
		printTitle();
		// generates the game board
		createBoard();
		// prints the main menu
		titleScreen();
		// quitting game from main menu
		if (continueGame == false) {
			quitGame();
		} else {
			// get number of players and ask for names
			int numOfPlayers = getNumOfPlayers();
			getPlayerNames(numOfPlayers);
			// creates players with entered names
			createPlayers();
			// do while loop - keeps the game running until one of the game ending
			// conditions is met
			do {
				sound.playBackgroundMusicNoSleep();

				for (Player p : players) {
					// game ends if current player decides to quit
					if (!takeTurn(p)) {
						continueGame = false;
						break;
					}
					// game ends if a player runs out of resources
					if (!endGameNoResources(p)) {
						continueGame = false;
						break;
					}
					// game ends if winning conditions are met
					if (!winGame()) {
						continueGame = false;
						break;
					}
				}
			} while (continueGame == true);
		}
		System.out.println("\nThe game has ended.");
	}

	/**
	 * generates the board for the game using a sortedmap
	 */
	private static Map<Integer, Element> createBoard() {

		// generating board
		Element start = new Element("LAUNCHPAD", SystemName.LAUNCHPAD, 0);
		Element spaceXStarship = new Element("SpaceX Starship [SpaceLaunchSystem]", SystemName.SPACELAUNCHSYS, 150);
		Element falconHeavyLauncher = new Element("Falcon Heavy Launcher [SpaceLaunchSystem]",
				SystemName.SPACELAUNCHSYS, 150);
		Element slsBoosters = new Element("SLS Boosters [SpaceLaunchSystem]", SystemName.SPACELAUNCHSYS, 150);
		Element astronauts = new Element("Astronauts [Manpower]", SystemName.MANPOWER, 100);
		Element scientists = new Element("Scientists [Manpower]", SystemName.MANPOWER, 100);
		Element freeTour = new Element("FREE TOUR OF NASA", SystemName.FREETOUROFNASA, 0);
		Element orion = new Element("Orion [Spacecraft]", SystemName.SPACECRAFT, 200);
		Element gateway = new Element("Gateway [Spacecraft]", SystemName.SPACECRAFT, 200);
		Element starshipHLS = new Element("Starship HLS [HumanLandingSystem]", SystemName.HUMANLANDINGSYS, 150);
		Element heraclesLander = new Element("Heracles Lander [HumanLandingSystem]", SystemName.HUMANLANDINGSYS, 150);
		Element rover = new Element("Rover [HumanLandingSystem]", SystemName.HUMANLANDINGSYS, 150);

		// adding to sorted map with Integer(place on board) as key
		while (board.size() < NUM_OF_ELEMENTS) {
			board.put(0, start);
			board.put(1, spaceXStarship);
			board.put(2, falconHeavyLauncher);
			board.put(3, slsBoosters);
			board.put(4, astronauts);
			board.put(5, scientists);
			board.put(6, freeTour);
			board.put(7, orion);
			board.put(8, gateway);
			board.put(9, starshipHLS);
			board.put(10, heraclesLander);
			board.put(11, rover);
		}
		return board;
	}

	/**
	 * Allows user to enter the number of players
	 * 
	 * @return numOfPlayers - the number of players the user enters
	 */
	private static int getNumOfPlayers() {
		// boolean to check for problems with user input
		boolean problem = false;
		int numOfPlayers = 0;
		System.out.println("\nHow many players? Please enter a number between " + MIN_NUM_OF_PLAYERS + " and "
				+ MAX_NUM_OF_PLAYERS);
		// do while loop continues to ask for number of players in the valid range
		// until a valid number is entered
		do {
			try {
				problem = false;
				numOfPlayers = scanner.nextInt();
				if (numOfPlayers > MAX_NUM_OF_PLAYERS || numOfPlayers < MIN_NUM_OF_PLAYERS) {
					problem = true;
					System.out.println("Players must be between " + MIN_NUM_OF_PLAYERS + " and " + MAX_NUM_OF_PLAYERS);
					scanner.nextLine();
				}
			} catch (Exception e) {
				problem = true;
				System.out.println("\nPlease enter number of players as numeric value between " + MIN_NUM_OF_PLAYERS
						+ " and " + MAX_NUM_OF_PLAYERS);
				scanner.nextLine();
			}
		} while (numOfPlayers > MAX_NUM_OF_PLAYERS || numOfPlayers < MIN_NUM_OF_PLAYERS || problem == true);
		return numOfPlayers;
	}

	/**
	 * Asks for names of each player, adds them to an arraylist with the number of
	 * players
	 * 
	 * @param numOfPlayers - the number of players the user enters
	 */
	private static ArrayList<String> getPlayerNames(int numOfPlayers) {
		// for loop to ask each player for their name
		for (int loop = 0; loop < numOfPlayers; loop++) {
			System.out.println("Player " + (loop + 1) + ", please enter your name.");
			String playerName;
			boolean repeat;
			playerName = scanner.next();
			do {
				repeat = false;
				// prevents user from entering an empty name
				while (playerName.length() == 0) {
					System.out.println("Please enter a valid name.");
					playerName = scanner.nextLine();
					repeat = true;
				}
				// prevents user from entering duplicate names
				while (playerNames.contains(playerName)) {
					System.out.println("'" + playerName + "' already entered. Please enter another name");
					playerName = scanner.nextLine();
					repeat = true;
				}
				// prevents user from entering a name shorter than 2 characters
				while (playerName.length() < 2) {
					System.out.println(
							"Player name must be greater than 2 characters and less than 15 characters. Please enter another name");
					playerName = scanner.nextLine();
					repeat = true;
				}
				// prevents user from entering a name longer than 15 characters
				while (playerName.length() > 15) {
					System.out.println(
							"Player name must be greater than 2 characters and less than 15 characters. Please enter another name");
					playerName = scanner.nextLine();
					repeat = true;
				}
			} while (repeat == true);
			// add player names to arraylist
			playerNames.add(playerName);

		}

		return playerNames;
	}

	/**
	 * Creates an arraylist of player objects
	 * 
	 * @param getPlayerNames
	 * @return
	 */
	private static ArrayList<Player> createPlayers() {

		// create players, assign resources and place them at "start" on the board
		for (int loop = 0; loop < playerNames.size(); loop++) {
			Player p = new Player(playerNames.get(loop), 1000, 0);
			// add players to arraylist
			players.add(p);
		}
		return players;
	}

	/**
	 * Allows each player to take their turn
	 * 
	 * @param p - current player
	 */
	private static boolean takeTurn(Player p) {
		// game continues to run if endTurn is true
		boolean endTurn = true;
		// execute each player's turn
		System.out.println();
		try {
			Thread.sleep(SHORTSLEEP);
			System.out.println("It is now " + p.getPlayerName() + "'s turn");
			Thread.sleep(SHORTSLEEP);
			acquireSystem(p);
			System.out.println(p.getPlayerName() + " has " + p.getResources()
					+ " Galactic Credits. Current position is " + board.get(p.getPosition()).getName() + ".");
		} catch (InterruptedException e1) {
			// catches exception if thread is interrupted
			e1.printStackTrace();
		} // for the player***
		System.out.println();

		// check if player wants to take turn - if not the game has to be ended
		int playerOption = 0;
		do {
			try {
				System.out.println("Would you like to continue playing? Please enter a number.");
				System.out.println("1....... Yes");
				System.out.println("2....... No");
				playerOption = scanner.nextInt();
				// if player enters an integer other than 1 or 2
				if (playerOption != 1 && playerOption != 2) {
					System.out.println("Please enter 1 or 2.");
					playerOption = 0;
				}
				// player decides to take turn
				if (playerOption == 1) {

					Dice dice = new Dice();
					dice.rollDice();

					// plays dice rolling sound effect
					sound.playDiceRollSound();

					// Slowly prints to screen for readability
					try {
						// shows player's dice roll
						System.out.println("Rolling dice!\n");

						Thread.sleep(2500);

						System.out.println("The first die was a " + dice.getDiceRollFirst() + ", the second die was a "
								+ dice.getDiceRollSecond() + ".");
						Thread.sleep(LONGSLEEP);

						System.out.println("Total rolled is " + dice.getTotal() + ".\n");

						Thread.sleep(LONGSLEEP);

						System.out.println("Taking turn... ");
						changePosition(dice.getTotal(), p);

						Thread.sleep(LONGSLEEP);

					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					System.out.println("Position changed to: " + p.getPosition());
					// get the element the player landed on
					Element e = board.get(p.getPosition());

					System.out.println("You have landed on " + e.getName());
					// allows the player to buy the element if it isn't owned
					if (e.isOwned() == false) {
						// prevents player from buying start or free parking elements
						if (p.getPosition() != 0 && p.getPosition() != 6) {
							do {
								try {
									System.out.println(e.getName() + " costs " + e.getCost() + " Galactic Credits. "
											+ "You have " + p.getResources() + " Galactic Credits.");
									System.out
											.println("Would you like to purchase this element? Please enter a number.");
									System.out.println("1........ Yes");
									System.out.println("2........ No");
									playerOption = scanner.nextInt();
									// if player enters an integer other than 1 or 2
									if (playerOption != 1 && playerOption != 2) {
										System.out.println("Please enter 1 or 2.");
										playerOption = 0;
									}
									// asks if the player wants to buy the element, offer to other players
									// if they don't
									if (playerOption == 1) {
										acquireElement(p, e);
									} else if (playerOption == 2) {
										// offer element to other players if current players
										// chooses not to purchase it
										System.out.println("You have chosen not to purchase this element.");
										offerElementToPlayers(e, p);
									}
									// catches exception if a player does not enter an integer
								} catch (InputMismatchException e3) {
									System.out.println("Input invalid. Please enter 1 or 2.");
									// playerOption reset to a valid value to continue loop
									playerOption = 0;
									scanner.nextLine();
								}
								// keeps looping until the player enters 1 or 2
							} while (playerOption != 1 && playerOption != 2);
						}
						// if the element is owned the current player may
						// have to pay resources to the owner
					} else if (e.isOwned() == true) {
						// player doesn't have to pay resources if it's start or free parking
						if (p.getPosition() != 0 && p.getPosition() != 6) {
							payOwner(p, e);
						}
					}
					// checks if player owns an entire system
					acquireSystem(p);
					// asks if player would like to develop elements
					if (p.isSLSOwner() == true || p.isManpowerOwner() == true || p.isSpacecraftOwner() == true
							|| p.isHLSOwner() == true) {
						developElement(p);
					}
					// display state of play at end of each player's turn
					System.out.println(p.getPlayerName() + "'s turn is over.\n");
					displayStateOfPlay();
					// runs quitGame if the player decides not to take their turn
					// ends game if player confirms they want to quit
				} else if (playerOption == 2) {
					// do while loop runs until a valid number is entered
					do {
						try {
							System.out.println("Are you sure you want to quit? This will end the game for all players");
							System.out.println("Please select a number: ");
							System.out.println("1....... Yes");
							System.out.println("2....... No");
							playerOption = scanner.nextInt();
							// if player decides to quit or continue
							switch (playerOption) {
							// ends the game
							case 1:
								quitGame();
								endTurn = false;
								break;
							case 2:
								// continues the game
								System.out.println("Continuing game...");
								endTurn = true;
								break;
							default:
								endTurn = true;
							}
							// catches exception if a player does not enter an integer
						} catch (InputMismatchException e3) {
							System.out.println("Input invalid. Please enter 1 or 2.");
							// set playerOption to a valid value to continue loop
							playerOption = 0;
							scanner.nextLine();
						}
					} while (playerOption != 1 && playerOption != 2);
				}
			} catch (InputMismatchException e3) {
				System.out.println("Input invalid. Please enter 1 or 2.");
				scanner.nextLine();
			}
		} while (playerOption != 1 && playerOption != 2);
		// keeps game running if true or ends it if player decides to quit
		return endTurn;
	}

	/**
	 * Changes the player's position according to the diceRoll
	 * 
	 * @param diceRoll
	 * @param p
	 * @throws IllegalArgumentException
	 */
	private static void changePosition(int diceRoll, Player p) throws IllegalArgumentException {
		if ((p.getPosition() + diceRoll >= START_POSITION) && (p.getPosition() + diceRoll <= LAST_POSITION)) {
			p.setPosition(p.getPosition() + diceRoll);
		} else if ((p.getPosition() + diceRoll > LAST_POSITION)
				&& (p.getPosition() + diceRoll <= (LAST_POSITION + MAX_DICE_ROLL))) { // 11 + 12(maxRoll) = 23
			p.setPosition((p.getPosition() + diceRoll) % MAX_DICE_ROLL); // mod 12 in this situation
			passStart(p);
		} else {
			System.out.println(p.getPosition());
			throw new IllegalArgumentException("Position out of bounds!");

		}

	}

	/**
	 * displays the final state of play when player selects to quit the game
	 */
	private static void quitGame() {
		System.out.println("Quitting game...");
		displayStateOfPlay();
		sound.playGameOverSound();
		//ensures that sound clip closes
		sound.setStartSound(false);
	}

	/**
	 * Ends the game if a player runs out of resources
	 * 
	 * @param p - current player
	 */
	private static boolean endGameNoResources(Player p) {

		if (p.getResources() <= 0) {
			System.out.println(
					"\n*** A player has run out of Galactic Credits! - You cannot continue the mission! ***\n        ***  GAME OVER! ***\n");
			System.out.println();
			displayStateOfPlay();
			sound.playGameOverSound();
			
			//ensures that sound clip closes
			sound.setStartSound(false);
			return false;
		}
		return true;
	}

	/**
	 * Displays messages if players meet conditions to win the game
	 */
	private static boolean winGame() {

		int elementsFullyDeveloped = 0;
		// checks if all 10 obtainable elements have development level 4
		// runs the win game sequence if they do
		for (Integer e : board.keySet()) {
			if (board.get(e).getDevLevel() == 4) {
				elementsFullyDeveloped++;
				if (elementsFullyDeveloped == 10) {
					System.out.println("Congratulations! All systems have been successfully upgraded.");
					System.out.println("Artemis is now ready to launch!");
					printLaunchSequence();
					displayStateOfPlay();
					sound.playGameOverSound();
					//ensures that sound clip closes
					sound.setStartSound(false);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * allows player to purchase element when they land on it, checks if they have
	 * enough resources, offers it to other players if not
	 * 
	 * @param p - current player
	 * @param e - current element
	 */
	private static void acquireElement(Player p, Element e) {

		System.out.println(p.getPlayerName() + " has " + p.getResources() + " Galactic Credits.");
		System.out.println(e.getName() + " costs " + e.getCost() + " Galactic Credits.");
		// check if player has enough resources to purchase element
		// sets player as owner if they do
		if (p.getResources() >= e.getCost()) {
			System.out.println("Element acquired!");
			// plays level up sound
			sound.playLevelUpSound();
			e.setOwned(true);
			e.setOwner(p);
			deductResources(p, e);
		} else {
			System.out.println("You do not have enough Galactic Credits to purchase this element.");
			// offers element to other players
			offerElementToPlayers(e, p);
		}
	}

	/**
	 * deduct resources from player when they land on an element owned by another
	 * player or purchase an element
	 * 
	 * @param p - current player
	 * @param e - current element
	 */
	private static void deductResources(Player p, Element e) {
		p.setResources(p.getResources() - e.getCost());
		System.out.println(p.getPlayerName() + " has " + p.getResources() + " Galactic Credits left.");
	}

	/**
	 * offer element to other players if player who lands on it doesn't buy it
	 * 
	 * @param e - current element
	 * @param p - current player
	 */
	private static void offerElementToPlayers(Element e, Player p) {
		int playerOption = 0;
		// cycle through arraylist of players to offer element to each player
		for (Player player : players) {
			// doesn't offer it to the current player again
			if (player != p && e.isOwned() == false) {
				do {
					try {
						System.out.println(player.getPlayerName() + ", would you like to " + "purchase this element?");
						System.out.println("Please enter a number: ");
						System.out.println("1....... Yes");
						System.out.println("2....... No");
						playerOption = scanner.nextInt();
						// if player enters an integer other than 1 or 2
						if (playerOption != 1 && playerOption != 2) {
							System.out.println("Please enter 1 or 2.");
							playerOption = 0;
						}

						if (playerOption == 1) {
							System.out.println(
									player.getPlayerName() + " has " + player.getResources() + " Galactic Credits.");
							System.out.println(e.getName() + " costs " + e.getCost() + " Galactic Credits.");
							// check if player has enough resources to purchase
							if (player.getResources() >= e.getCost()) {
								System.out.println("Element acquired!");
								// plays level up sound
								sound.playLevelUpSound();
								e.setOwned(true);
								e.setOwner(player);
								deductResources(player, e);
							} else {
								System.out.println(
										"You do not have enough Galactic Credits to purchase " + "this element");
							}
							// offers it to the next player if player selects "No"
						} else if (playerOption == 2) {
							System.out.println("You have chosen not to purchase this element.");
						}
					} catch (InputMismatchException e3) {
						System.out.println("Input invalid. Please enter 1 or 2.");
						playerOption = 0;
						scanner.nextLine();
					}
				} while (playerOption != 1 && playerOption != 2);
			}
		}
	}

	/**
	 * pay the element owner when a player lands on their element gives the element
	 * owner the option to reject the payment
	 * 
	 * @param p - current player
	 * @param e - current element
	 */
	private static void payOwner(Player p, Element e) {
		int playerOption = 0;
		// check if current player has landed on their own element
		if (p != e.getOwner()) {
			do {
				try {
					System.out.println(
							e.getOwner().getPlayerName() + " is the owner of this element. " + "Would you like "
									+ p.getPlayerName() + " to pay you Galactic Credits " + "for landing on it?");
					System.out.println("Please enter a number: ");
					System.out.println("1........ Yes");
					System.out.println("2........ No");
					playerOption = scanner.nextInt();
					// if player enters an integer other than 1 or 2
					if (playerOption != 1 && playerOption != 2) {
						System.out.println("Please enter 1 or 2.");
						playerOption = 0;
					}
					// if owner wants player to pay them
					if (playerOption == 1) {
						System.out.println(p.getPlayerName() + " must pay " + (e.getCost()) + " Galactic Credits to "
								+ e.getOwner().getPlayerName());
						// plays change rattling sound
						sound.playDeductResourceSound();
						deductResources(p, e);
						addResources(e);
						// owner decides current player shouldn't have to pay
					} else if (playerOption == 2) {
						System.out.println(p.getPlayerName() + " does not have to pay Galactic Credits.");
					}
				} catch (InputMismatchException e3) {
					System.out.println("Input invalid. Please enter 1 or 2.");
					playerOption = 0;
					scanner.nextLine();
				}
			} while (playerOption != 1 && playerOption != 2);
			// check if current player is the owner
		} else if (p == e.getOwner()) {
			System.out.println("You are the owner of this element.");
		}
		System.out.println();
	}

	/**
	 * adds resources to a player when another player lands on their element
	 * 
	 * @param e - current element
	 */
	private static void addResources(Element e) {

		e.getOwner().setResources(e.getOwner().getResources() + e.getCost());
		System.out.println(
				e.getOwner().getPlayerName() + " now has " + e.getOwner().getResources() + " Galactic Credits.");
		// makes a change rattling sound
		sound.playDeductResourceSound();
	}

	/**
	 * adds resources to player when they pass start - added to changePosition
	 * method
	 * 
	 * @param p - current player
	 */
	private static void passStart(Player p) {

		System.out.println("You have passed start. Collect 500 Galactic Credits.");
		// makes a change rattling sound
		sound.playDeductResourceSound();
		p.setResources(p.getResources() + 500);
		System.out.println(p.getPlayerName() + " now has " + p.getResources() + " Galactic Credits.");
	}

	/**
	 * Checks if player has acquired an entire system
	 * 
	 * @param p - current player
	 */
	private static void acquireSystem(Player p) {
		// check if all elements in system are owned
		if (board.get(1).getOwner() == p && board.get(2).getOwner() == p && board.get(3).getOwner() == p) {
			// set to true if not already true
			if (p.isSLSOwner() != true) {
				System.out.println(p.getPlayerName() + " has acquired the Space Launch System!");
				p.setSLSOwner(true);
				// set system owned to true to allow these elements to be developed
				board.get(1).setSysIsOwned(true);
				board.get(2).setSysIsOwned(true);
				board.get(3).setSysIsOwned(true);
			}
		}
		// each system works the same way
		if (board.get(4).getOwner() == p && board.get(5).getOwner() == p) {
			if (p.isManpowerOwner() != true) {
				System.out.println(p.getPlayerName() + " has acquired Manpower!");
				p.setManpowerOwner(true);
				board.get(4).setSysIsOwned(true);
				board.get(5).setSysIsOwned(true);
			}
		}

		if (board.get(7).getOwner() == p && board.get(8).getOwner() == p) {
			if (p.isSpacecraftOwner() != true) {
				System.out.println(board.get(7).getOwner().getPlayerName() + " has acquired the Spacecraft!");
				p.setSpacecraftOwner(true);
				board.get(7).setSysIsOwned(true);
				board.get(8).setSysIsOwned(true);
			}
		}

		if (board.get(9).getOwner() == p && board.get(10).getOwner() == p && board.get(11).getOwner() == p) {
			if (p.isHLSOwner() != true) {
				System.out.println(p.getPlayerName() + " has acquired the Human Landing System!");
				p.setHLSOwner(true);
				board.get(9).setSysIsOwned(true);
				board.get(10).setSysIsOwned(true);
				board.get(11).setSysIsOwned(true);
			}
		}
		System.out.println();
	}

	/**
	 * Allows player to develop elements
	 * 
	 * @param p - current player
	 */
	private static void developElement(Player p) {

		int playerOption = 0;
		do {
			try {
				System.out
						.println(p.getPlayerName() + ", would you like to upgrade an element? Please enter a number.");
				System.out.println("1....... Yes");
				System.out.println("2....... No");
				playerOption = scanner.nextInt();
				// if player enters an integer other than 1 or 2
				if (playerOption != 1 && playerOption != 2) {
					System.out.println("Please enter 1 or 2.");
					playerOption = 0;
				}
				// if player decides to develop element
				if (playerOption == 1) {
					for (Integer e : board.keySet()) {
						// check if current player is owner, check if system is owned, check if
						// development
						// level is less than 3 before allowing player to develop the element
						if (board.get(e).getOwner() == p && board.get(e).isSysIsOwned() == true
								&& board.get(e).getDevLevel() < 3) {
							do {
								try {
									System.out.println("Upgrade " + board.get(e).getName() + "? Current Upgrade Level: "
											+ board.get(e).getDevLevel());
									System.out.println("Cost to upgrade: " + board.get(e).getCost());
									System.out.println("Please enter a number.");
									System.out.println("1........ Yes");
									System.out.println("2........ No");
									playerOption = scanner.nextInt();
									// if player enters an integer other than 1 or 2
									if (playerOption != 1 && playerOption != 2) {
										System.out.println("Please enter 1 or 2.");
										playerOption = 0;
									}
									// check if player has enough resources
									if (playerOption == 1 && p.getResources() >= board.get(e).getCost()) {
										// plays level up sound
										sound.playLevelUpSound();
										// increase development level by 1
										board.get(e).upgradeDevLevel();
										System.out.println("Upgrade level for " + board.get(e).getName() + " is now "
												+ board.get(e).getDevLevel());
										deductResources(p, board.get(e));
										changeDevelopmentCost(board.get(e));
										// if player decides not to develop element
									} else if (playerOption == 2) {
										System.out.println("Continuing play...");
										// if player does not have enough resources
									} else if (p.getResources() < board.get(e).getCost()) {
										System.out.println(
												"You do not have enough Galactic Credits to upgrade this element.");
									}
								} catch (InputMismatchException e3) {
									System.out.println("Input invalid. Please enter 1 or 2.");
									playerOption = 0;
									scanner.nextLine();
								}
							} while (playerOption != 1 && playerOption != 2);
							// if element has been developed 3 times a major development
							// must be purchased to fully develop the element
						} else if (board.get(e).getOwner() == p && board.get(e).getDevLevel() == 3) {
							do {
								try {
									System.out.println("Purchase a Major Upgrade for " + board.get(e).getName() + "?");
									System.out.println("Cost to upgrade: " + board.get(e).getCost());
									System.out.println("1....... Yes");
									System.out.println("2....... No");
									playerOption = scanner.nextInt();
									// if player enters an integer other than 1 or 2
									if (playerOption != 1 && playerOption != 2) {
										System.out.println("Please enter 1 or 2.");
										playerOption = 0;
									}
									// check if player has enough resources
									if (playerOption == 1 && p.getResources() >= board.get(e).getCost()) {
										System.out.println(board.get(e).getName() + " is now fully upgraded.");
										// plays level up sound
										sound.playLevelUpSound();
										deductResources(p, board.get(e));
										changeDevelopmentCost(board.get(e));
										board.get(e).upgradeDevLevel();
									} else if (playerOption == 2) {
										System.out.println("Continuing play...");
										// if player does not have enough credits
									} else if (p.getResources() < board.get(e).getCost()) {
										System.out.println("You do not have enough Galactic Credits.");
									}
								} catch (InputMismatchException e3) {
									System.out.println("Input invalid. Please enter 1 or 2.");
									playerOption = 0;
									scanner.nextLine();
								}
							} while (playerOption != 1 && playerOption != 2);
							// checks if element is already fully developed
						} else if (board.get(e).getOwner() == p && board.get(e).getDevLevel() == 4) {
							System.out.println(board.get(e).getName() + " is fully upgraded.");
						}
					}
				}
			} catch (InputMismatchException e3) {
				System.out.println("Input invalid. Please enter 1 or 2.");
				playerOption = 0;
				scanner.nextLine();
			}
		} while (playerOption != 1 && playerOption != 2);
		System.out.println();
	}

	/**
	 * Prints a message to screen to inform players when a system is fully upgraded
	 */
	private static void systemFullyUpgraded() {

		try {
			if (board.get(1).getDevLevel() == 4 && board.get(2).getDevLevel() == 4 && board.get(3).getDevLevel() == 4) {
				Thread.sleep(LONGSLEEP);
				System.out.println("\n - The Space Launch System is fully upgraded!");
			}
			if (board.get(4).getDevLevel() == 4 && board.get(5).getDevLevel() == 4) {
				Thread.sleep(LONGSLEEP);
				System.out.println("\n - Manpower is fully upgraded!");
			}
			if (board.get(7).getDevLevel() == 4 && board.get(8).getDevLevel() == 4) {
				Thread.sleep(LONGSLEEP);
				System.out.println("\n - The Spacecraft is fully upgraded!");
			}
			if (board.get(9).getDevLevel() == 4 && board.get(10).getDevLevel() == 4
					&& board.get(11).getDevLevel() == 4) {
				Thread.sleep(LONGSLEEP);
				System.out.println("\n - The Human Landing System is fully upgraded!");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Changes element cost depending on development level of element
	 * 
	 * @param e
	 */
	private static void changeDevelopmentCost(Element e) {
		// changes the cost of each element each time a new
		// development for it is purchased
		if (e.getDevLevel() == 1) {
			e.setCost(e.getCost() + 100);
		} else if (e.getDevLevel() == 2) {
			e.setCost(e.getCost() + 100);
		} else if (e.getDevLevel() == 3) {
			e.setCost(e.getCost() + 100);
		} else if (e.getDevLevel() == 4) {
			e.setCost(e.getCost() + 100);
		}
	}

	/**
	 * prints launch sequence after winning game
	 */
	private static void printLaunchSequence() {

		FileReader fileRead;

		System.out.println("CONGRATULATIONS. THE ARTEMIS SPACE PROGRAM IS READY TO PROGRESS!");
		try {
			Thread.sleep(SHORTSLEEP);

			System.out.println(
					"Summer 2022 - Artemis I, an uncrewed flight test of the Space Launch System and spacecraft around the Moon");
			Thread.sleep(SHORTSLEEP);
			System.out
					.println("2024 - Artemis II, a crewed flight test of SLS and Orion spacecraft orbitting the Moon");
			Thread.sleep(LONGSLEEP);
			System.out.println("2025 - Artemis III - a crewed mission to the Moon, for the first time since 1972!");
		} catch (InterruptedException e1) {
			System.out.println("Error. Printing end sequence.");
		}
		try {
			fileRead = new FileReader("rocketascii.txt");
			BufferedReader buffRead = new BufferedReader(fileRead);
			String line;

			line = buffRead.readLine();

			while (line != null) {

				System.out.println(line);

				line = buffRead.readLine();
			}

			for (int loop = 1; loop <= 50; loop++) {

				System.out.println("            |||||||||||||||||||||||");
				Thread.sleep(50);
			}
			buffRead.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error, printing end sequence. Please contact adminstrator.");
		} catch (IOException e) {
			System.out.println("Error, printing end sequence. Please contact adminstrator.");
		} catch (InterruptedException e) {
			System.out.println("Error, printing end sequence. Please contact adminstrator.");
		}

	}

	/**
	 * Displays all elements and who owns them
	 * 
	 * @param board
	 */
	private static void displayStateOfPlay() {

		System.out.println("          ~~~ Board ~~~");

		for (Integer e : board.keySet()) {
			// prints start and free tour without comment on ownership, since they cannot be
			// owned
			try {
				Thread.sleep(SHORTSLEEP);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (e == 0 || e == 6) {
				System.out.println(" - " + board.get(e).getName());

			} else if (board.get(e).isOwned() == true) {
				if (e != 0 && e != 6) {
					System.out.println(" - " + board.get(e).getName() + " owned by "
							+ board.get(e).getOwner().getPlayerName().toUpperCase() + " | "
							+ " Upgrade Level: " + board.get(e).getDevLevel());
				}
			} else if (board.get(e).isOwned() == false) {
				System.out.println(" - " + board.get(e).getName() + " is not owned.");
			}
		}
		systemFullyUpgraded();
		System.out.println();

		// prints players current position and resources
		for (Player player : players) {
			try {
				Thread.sleep(SHORTSLEEP);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println(" - " + player.getPlayerName().toUpperCase() + " is currently on "
					+ board.get(player.getPosition()).getName() + " with " + player.getResources()
					+ " Galactic Credits.");
		}
	}

	/**
	 * Prints the ArtemisLite title screen
	 */
	private static void printTitle() {
		File file = new File("titlescreen.txt");

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {

				for (int loop = 0; loop < line.length(); loop++) {
					try {
						if (line.charAt(loop) == ' ') {
							System.out.printf("%c", line.charAt(loop));
						} else if (line.charAt(loop) != ' ') {
							System.out.printf("%c", line.charAt(loop));
							Thread.sleep(3);// pause between characters
						}
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}

				System.out.println();
				line = br.readLine();
			}
			br.close();

		} catch (IOException e) {
			System.out.println("Error printing title...Please contact adminstrator.");
			return;
		} catch (NullPointerException e) {
			System.out.println("Error printing title...Please contact adminstrator.");
			return;
		}

	}

	/*
	 * prints options for user
	 */
	private static void printOptions() {
		System.out.println("   ----- ARTEMIS LITE MAIN MENU -----");
		System.out.println("Please enter an option as numeric value.");
		System.out.println("1....... Start a new game");
		System.out.println("2....... Instructions");
		System.out.println("3....... Print title screen");
		System.out.println("4....... Quit Game");
	}

	/*
	 * executes user option from title screen selection
	 */
	private static void titleScreen() {

		boolean repeat;

		do {
			repeat = true;
			try {

				printOptions();
				int userOption = scanner.nextInt();

				switch (userOption) {
				case 1:
					repeat = false;
					break;
				case 2:
					printInstructions();
					break;
				case 3:
					printTitle();
					break;
				case 4:
					repeat = false;
					continueGame = false;
					break;
				default:
					repeat = true;
					break;
				}
			} catch (Exception E) {
				repeat = true;
				scanner.nextLine();

			}

		} while (repeat == true);

	}

	/*
	 * Prints instructions for user from title screen
	 */
	private static void printInstructions() {
		String enterKey = null;
		boolean repeat;

		System.out.println("ArtemisLite is a virtual board game based on NASA's mission "
				+ "\nto land the first woman and next man on the moon by 2024. \n"
				+ "\nThe game must be played with between 2 and 4 players. Players take"
				+ "\nturns to roll virtual dice. They are then informed of the opportunties"
				+ "\nand obligations associated with the element they land on.");
		System.out.println("\nPress enter to continue.");
		scanner.nextLine();

		do {
			enterKey = scanner.nextLine();
			if (enterKey.equals("")) {
				repeat = false;
				System.out.println("Players start with 1000 Galactic Credits and collect 500 each time they pass the"
						+ "\nLaunchpad square.\n"
						+ "\nWhen a player lands on an unowned square they may choose to purchase"
						+ "\nit. If a player chooses not to purchase the element, it will be offered to"
						+ "\nanother player at the same cost.");
				System.out.println("\nPress enter to continue.");

			} else {
				repeat = true;
				System.out.println("Press enter to continue.");
			}
		} while (repeat == true);

		do {
			enterKey = scanner.nextLine();
			if (enterKey.equals("")) {
				repeat = false;
				System.out.println("There are four systems which are made up of their associated elements.\n"
						+ "\nThe Space Launch System system is comprised of the SpaceX StarShip, "
						+ "\nFalcon Heavy Launcher and SLS Boosters elements.  "
						+ "\nThe Manpower system is comprised of the Astronauts and Scientists elements. "
						+ "\nThe Spacecraft system is comprised of the Orion and Gateway elements."
						+ "\nThe Human Landing system is comprised of the Starship HLS, Heracles Lander and Rover elements.\n"
						+ "\nIf a player owns all elements within a system they can upgrade any of the "
						+ "\nindividual elements during their turn provided they have enough Galactic Credits."
						+ "\nEach element must be upgraded 3 times before a major upgrade on that "
						+ "\nelement can be completed. An element that has undergone a major upgrade "
						+ "\nis fully upgraded.");
				System.out.println("\nPress enter to continue");
			} else {
				repeat = true;
				System.out.println("Press enter to continue.");
			}
		} while (repeat == true);

		do {
			enterKey = scanner.nextLine();
			if (enterKey.equals("")) {
				repeat = false;
				System.out.println("The game is lost if a player runs out of Galactic Credits or chooses to quit."
						+ "\nThe game is won if all elements on the board have undergone a major upgrade.");
				System.out.println("\nPress enter to continue");
			} else {
				repeat = true;
				System.out.println("Press enter to continue.");
			}
		} while (repeat == true);

		do {
			enterKey = scanner.nextLine();
			if (enterKey.equals("")) {
				repeat = true;
				break;
			} else {
				System.out.println("Press enter to continue");
				repeat = true;

			}
		} while (repeat == true);
	}

}
