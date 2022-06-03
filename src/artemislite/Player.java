package artemislite;

/**
 * 
 * A class that represents a player
 *
 */

public class Player {

	private String playerName;
	private int resources;
	private int position;
	private boolean SLSOwner;
	private boolean manpowerOwner;
	private boolean spacecraftOwner;
	private boolean HLSOwner;

	/**
	 * default constructor
	 */
	public Player() {

	}

	/**
	 * Constructor with arguments
	 * @param playerName
	 * @param resources
	 * @param position
	 */
	public Player(String playerName, int resources, int position) {
		setplayerName(playerName);
		this.resources = resources;
		setPosition(position);
	}

	/**
	 * getter for player name
	 * 
	 * @return
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * setter for player name ensures player name can only be between 2 and 15
	 * throws illegal argument exception otherwise characters
	 * 
	 * @param playerName
	 * @throws IllegalArgumentException
	 */
	public void setplayerName(String playerName) throws IllegalArgumentException {
		if (playerName.length() > 1 && playerName.length() < 15) {
			this.playerName = playerName.toUpperCase();
		} else {
			throw new IllegalArgumentException("Player name must be between 2 and 15 characters!");
		}
	}

	/**
	 * getter for amount of resources (galactic credits) held by player
	 * 
	 * @return
	 */
	public int getResources() {
		return resources;
	}

	/**
	 * setter for player resources
	 * 
	 * @param resources
	 */
	public void setResources(int resources) {
		this.resources = resources;
	}

	/**
	 * getter for player position on board
	 * 
	 * @return
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * setter for player position on board, only permits position between 0(START)
	 * and 11(end of board)
	 * 
	 * @param position
	 * @throws IllegalArgumentException
	 */
	public void setPosition(int position) throws IllegalArgumentException {
		if (position >= 0 && position <= 11) {
			this.position = position;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * getter for whether player is owner of SLS system
	 * 
	 * @return
	 */
	public boolean isSLSOwner() {
		return SLSOwner;
	}

	/**
	 * setter for set if player is owner of SLS system
	 * 
	 * @param sLSOwner
	 */
	public void setSLSOwner(boolean sLSOwner) {
		SLSOwner = sLSOwner;
	}

	/**
	 * getter for whether player is owner of Manpower system
	 * 
	 * @return
	 */
	public boolean isManpowerOwner() {
		return manpowerOwner;
	}

	/**
	 * setter to set if player is owner of Manpower system
	 * 
	 * @param manpowerOwner
	 */
	public void setManpowerOwner(boolean manpowerOwner) {
		this.manpowerOwner = manpowerOwner;
	}

	/**
	 * getter for whether player is owner of Spacecraft system
	 * 
	 * @return
	 */
	public boolean isSpacecraftOwner() {
		return spacecraftOwner;
	}

	/**
	 * setter to set if player is owner of Spacecraft system
	 * 
	 * @param spacecraftOwner
	 */
	public void setSpacecraftOwner(boolean spacecraftOwner) {
		this.spacecraftOwner = spacecraftOwner;
	}

	/**
	 * getter for whether player is owner of HLS system
	 * 
	 * @return
	 */
	public boolean isHLSOwner() {
		return HLSOwner;
	}

	/**
	 * setter to set if player is owner of HLS system
	 * 
	 * @param hLSOwner
	 */
	public void setHLSOwner(boolean hLSOwner) {
		HLSOwner = hLSOwner;
	}

}
