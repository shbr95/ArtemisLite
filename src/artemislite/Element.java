/**
 * 
 */
package artemislite;

/**
 * Class that represents an Element(square) on the artemisLite game board
 *
 */
public class Element {

	private static final int MAX_DEV_LEVEL = 4;
	private String name;
	private SystemName sysName;
	private int cost;
	private Player owner;
	private int devLevel;
	private boolean isOwned = false;
	private boolean sysIsOwned = false;

	/**
	 * default constructor
	 */
	public Element() {

	}

	/**
	 * constructor with arguments
	 * @param name
	 * @param sysName
	 * @param cost
	 */
	public Element(String name, SystemName sysName, int cost) {
		setName(name);
		this.sysName = sysName;
		setCost(cost);

	}

	/**
	 * getter for element name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter for element name, validates that name is less than 50 characters, otherwise throws illegal argument exception
	 * @param name the name to set
	 */
	public void setName(String name) throws IllegalArgumentException {

		if (name.length() < 50) {

			this.name = name;
		} else {
			throw new IllegalArgumentException("Element name must be 50 characters or less");
		}
	}

	/**
	 * getter for system that element belongs to
	 * @return the sysName
	 */
	public SystemName getSysName() {
		return sysName;
	}

	/**
	 * setter to set which system the element belongs to
	 * @param sysName the sysName to set
	 */
	public void setSysName(SystemName sysName) {
		this.sysName = sysName;
	}

	/**
	 * getter for cost to purchase element
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * setter for cost of element, LAUNCHPAD and FREETOUROFNASA are set to 0 as they are not purchasable.
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		if (getSysName().equals(SystemName.LAUNCHPAD) || getSysName().equals(SystemName.FREETOUROFNASA)) {
			this.cost = 0;
		} else {
			this.cost = cost;
		}
	}

	/**
	 * getter for the owner of the element
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * setter to set owner of element, if element cannot be purchased i.e. LAUNCHPAD or FREETOUROFNASA system throws illegal argument exception
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) throws IllegalArgumentException {
		if (getSysName().equals(SystemName.LAUNCHPAD) || getSysName().equals(SystemName.FREETOUROFNASA)) {
			throw new IllegalArgumentException("LAUNCHPAD or FREE TOUR OF NASA cannot be owned!");
		} else {
			this.owner = owner;
		}
	}

	/**
	 * getter for the upgrade level of element
	 * @return the devLevel
	 */
	public int getDevLevel() {
		return devLevel;
	}

	/**
	 * setter to set the upgrade level of element, if element cannot be upgraded i.e. LAUNCHPAD or FREETOUROFNASA system throws illegal argument exception
	 * if element is already full upgraded system informs user and does not progress further with development
	 * @param devLevel the devLevel to set
	 */
	public void upgradeDevLevel() throws IllegalArgumentException {
		if (getSysName().equals(SystemName.LAUNCHPAD) || getSysName().equals(SystemName.FREETOUROFNASA)) {
			throw new IllegalArgumentException("LAUNCHPAD or FREE TOUR OF NASA cannot be developed!");
		} else {
			if (this.devLevel < MAX_DEV_LEVEL) {
				++this.devLevel;
			} else {
				throw new IllegalArgumentException("Element cannot be developed more than four times!");
			}
		}
	}

	/**
	 * getter for whether the element is currently owned by a player
	 * @return the isOwned
	 */
	public boolean isOwned() {

		return isOwned;
	}

	/**
	 * setter to set whether the element is owned, if element cannot be purchased i.e. LAUNCHPAD or FREETOUROFNASA system throws illegal argument exception
	 * @param isOwned the isOwned to set
	 */
	public void setOwned(boolean set) throws IllegalArgumentException {
		if (getSysName().equals(SystemName.LAUNCHPAD) || getSysName().equals(SystemName.FREETOUROFNASA)) {
			throw new IllegalArgumentException("LAUNCHPAD or FREE TOUR OF NASA cannot be owned!");
		} else {
			this.isOwned = true;
		}
	}

	/**
	 * getter for whether the system the element is in is owned
	 * @return the sysIsOwned
	 */
	public boolean isSysIsOwned() {
		return sysIsOwned;
	}

	/**
	 * setter to set if system element is in is owned by one player if element cannot be purchased i.e. LAUNCHPAD or FREETOUROFNASA system throws illegal argument exception
	 * @param sysIsOwned the sysIsOwned to set
	 */
	public void setSysIsOwned(boolean sysIsOwned) {
		if (this.getSysName().equals(SystemName.LAUNCHPAD) || this.getSysName().equals(SystemName.FREETOUROFNASA)) {
			throw new IllegalArgumentException("LAUNCHPAD or FREE TOUR OF NASA cannot be owned!");
		} else {
			this.sysIsOwned = sysIsOwned;
		}

	}

}