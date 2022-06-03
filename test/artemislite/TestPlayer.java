package artemislite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPlayer {

	Player p;
	String playerNameValidLower;
	String playerNameValidUpper;
	String playerNameInvalidUpper;
	String playerNameInvalidLower;
	int resources, positionValidUpper, positionInvalidUpper, positionValidLower, positionInvalidLower;
	boolean SLSOwner, manpowerOwner, spacecraftOwner, HLSOwner;

	@BeforeEach
	void setUp() throws Exception {

		p = new Player();
		playerNameValidUpper = "VALIDNAMEUPPER";
		playerNameValidLower = "VA";
		playerNameInvalidUpper = "INVALIDNAMEUPPR";
		playerNameInvalidLower = "I";
		resources = 500;
		positionValidUpper = 11;
		positionValidLower = 0;
		positionInvalidUpper = 12;
		positionInvalidLower = -1;
		SLSOwner = true;
		manpowerOwner = true;
		spacecraftOwner = true;
		HLSOwner = true;

	}

	@Test
	void testDefaultConstructor() {

		assertNotNull(p);

	}

	@Test
	void testConstructorWithArgsValid() {

		p = new Player(playerNameValidUpper, resources, positionValidUpper);

		assertEquals(playerNameValidUpper, p.getPlayerName());
		assertEquals(resources, p.getResources());
		assertEquals(positionValidUpper, p.getPosition());
		
		p = new Player(playerNameValidLower, resources, positionValidLower);

		assertEquals(playerNameValidLower, p.getPlayerName());
		assertEquals(resources, p.getResources());
		assertEquals(positionValidLower, p.getPosition());

	}

	@Test
	void testConstructorWithArgsInvalid() {

		assertThrows(Exception.class, () -> {
			p = new Player(playerNameInvalidLower, resources, positionValidUpper);
		});
		assertThrows(Exception.class, () -> {
			p = new Player(playerNameInvalidUpper, resources, positionValidUpper);
		});
		assertThrows(Exception.class, () -> {
			p = new Player(playerNameValidLower, resources, positionInvalidLower);
		});
		assertThrows(Exception.class, () -> {
			p = new Player(playerNameValidLower, resources, positionInvalidUpper);
		});

	}

	@Test
	void testGetSetplayerNameValid() {

		p.setplayerName(playerNameValidUpper);
		assertEquals(playerNameValidUpper, p.getPlayerName());
		
		p.setplayerName(playerNameValidLower);
		assertEquals(playerNameValidLower, p.getPlayerName());

	}
	
	@Test
	void testGetSetplayerNameInvalid() {

		assertThrows(Exception.class, () -> {
			p.setplayerName(playerNameInvalidLower);
		});
		assertThrows(Exception.class, () -> {
			p.setplayerName(playerNameInvalidUpper);
		});
	}

	@Test
	void testGetSetResources() {
		p.setResources(resources);
		assertEquals(resources, p.getResources());
	}

	@Test
	void testGetSetPositionValid() {
		
		p.setPosition(positionValidLower);
		assertEquals(positionValidLower, p.getPosition());
		
		p.setPosition(positionValidUpper);
		assertEquals(positionValidUpper, p.getPosition());
	}

	@Test
	void testGetSetSLSOwner() {
		p.setSLSOwner(SLSOwner);
		assertEquals(SLSOwner, p.isSLSOwner());
	}

	@Test
	void testGetSetManpowerOwner() {
		p.setManpowerOwner(manpowerOwner);
		assertEquals(manpowerOwner, p.isManpowerOwner());
	}

	@Test
	void testGetSetSpacecraftOwner() {
		p.setSpacecraftOwner(spacecraftOwner);
		assertEquals(spacecraftOwner, p.isSpacecraftOwner());
	}

	@Test
	void testGetSetHLSOwner() {
		p.setHLSOwner(HLSOwner);
		assertEquals(HLSOwner, p.isHLSOwner());
	}

}
