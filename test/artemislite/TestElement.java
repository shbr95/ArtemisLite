package artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestElement {

	Element e;
	String elementNameValid;
	String elementNameInvalid;
	SystemName sysNameValid;
	SystemName sysNameInvalid1;
	SystemName sysNameInvalid2;
	int cost;
	int costNoPurchase;
	Player owner;
	int devLevel;
	boolean specialDev;
	boolean isOwned;

	@BeforeEach
	void setUp() throws Exception {

		elementNameValid = "testOwner";
		elementNameInvalid = "Test////////////////////////////////////////////////////////////////////////////////////";
		sysNameValid = SystemName.SPACECRAFT;
		sysNameInvalid1 = SystemName.LAUNCHPAD;
		sysNameInvalid2 = SystemName.FREETOUROFNASA;
		cost = 150;
		costNoPurchase=0;
		owner = new Player();
		devLevel = 1;
		isOwned = true;
		e = new Element(elementNameValid, sysNameValid, cost);

	}

	@Test
	void testElementDefaultConstructor() {

		Element testDefConstructor = new Element();
		assertNotNull(testDefConstructor);

	}

	@Test
	void testElementConstructorWithArgsValid() {
		Element e = new Element(elementNameValid, sysNameValid, cost);
		assertEquals(elementNameValid, e.getName());
		assertEquals(sysNameValid, e.getSysName());
		assertEquals(cost, e.getCost());
	}

	@Test
	void testElementConstructorWithArgsInvalid() {

		assertThrows(Exception.class, () -> {
			Element e = new Element(elementNameInvalid, sysNameValid, cost);
		});

	}

	@Test
	void testGetSetNameValid() {
		e.setName(elementNameValid);
		assertEquals(elementNameValid, e.getName());
	}

	@Test
	void testGetSetNameInvalid() {
		assertThrows(Exception.class, () -> {
			e.setName(elementNameInvalid);
		});
	}

	@Test
	void testGetSetSysName() {
		e.setSysName(sysNameValid);
		assertEquals(sysNameValid, e.getSysName());
	}

	@Test
	void testGetSetCost() {
		e.setCost(cost);
		assertEquals(cost, e.getCost());
	}

	@Test
	void testGetSetCostNonPurchasable() {
		e.setSysName(sysNameInvalid1);
		e.setCost(cost);
		assertEquals(costNoPurchase, e.getCost());

		e.setSysName(sysNameInvalid2);
		e.setCost(cost);
		assertEquals(costNoPurchase, e.getCost());
	}

	@Test
	void testGetSetOwnerValid() {
		e.setOwner(owner);
		assertEquals(owner, e.getOwner());
	}

	@Test
	void testGetSetOwnerNonPurchasable() {

		e.setSysName(sysNameInvalid1);
		assertThrows(Exception.class, () -> {
			e.setOwner(owner);
		});
		e.setSysName(sysNameInvalid2);
		assertThrows(Exception.class, () -> {
			e.setOwner(owner);
		});
	}

	@Test
	void testGetSetDevLevelValid() {

		e.upgradeDevLevel();
		assertEquals(devLevel, e.getDevLevel());

	}

	@Test
	void testGetSetDevLevelInvalid() {

		for (int loop = 1; loop <= 4; loop++) {
			e.upgradeDevLevel();
		}
		assertThrows(Exception.class, () -> {
			e.upgradeDevLevel();
		});

	}

	void testGetSetDevLevelNonPurchasable() {

		e.setSysName(sysNameInvalid1);
		assertThrows(Exception.class, () -> {
			e.upgradeDevLevel();
		});
		e.setSysName(sysNameInvalid2);
		assertThrows(Exception.class, () -> {
			e.upgradeDevLevel();
		});

	}

	@Test
	void testIsOwnedValid() {
		e.setOwned(isOwned);
		assertEquals(isOwned, e.isOwned());
	}

	@Test
	void testIsOwnedNonPurchasable() {

		e.setSysName(sysNameInvalid1);
		assertThrows(Exception.class, () -> {
			e.setOwned(isOwned);
		});
		e.setSysName(sysNameInvalid2);
		assertThrows(Exception.class, () -> {
			e.setOwned(isOwned);
		});

	}

	@Test
	void testSysIsOwned() {

		e.setSysIsOwned(isOwned);
		assertEquals(isOwned, e.isSysIsOwned());

	}

	@Test
	void testSysIsOwnedNonPurchasable() {

		e.setSysName(sysNameInvalid1);
		assertThrows(Exception.class, () -> {
			e.setSysIsOwned(isOwned);
		});
		e.setSysName(sysNameInvalid2);
		assertThrows(Exception.class, () -> {
			e.setSysIsOwned(isOwned);
		});

	}
}
