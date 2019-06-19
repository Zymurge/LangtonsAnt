package LangtonsAnt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonGridTest {

	@Test
	void testJsonGrid() {
		char[][] map = {{'B','v','B'},{'W','B','W'},{'B','W','B'}};
		var target = new JsonGrid(0, 3, -1, 2, 1, map);
		assertNotNull(target, "Valid ctor should create instance");		
	}

	@Test
	void testToJson() {
		char[][] map = {{'B','v','B'},{'W','B','W'},{'B','W','B'}};
		var expected = "{`minx`:0,`maxx`:3,`miny`:-1,`maxy`:2,`numants`:1,`map`:[[`B`,`v`,`B`],[`W`,`B`,`W`],[`B`,`W`,`B`]]}".replace('`','"');
		var target = new JsonGrid(0, 3, -1, 2, 1, map);
		var result = target.toJson();
		assertEquals(expected, result);
	}
	
	@Test
	void testNullMap() {
		var msg = assertThrows(NullPointerException.class, () -> new JsonGrid(0, 2, -1, 1, 1, null));
		assertTrue(msg.getMessage().contains("map"));
	}

}
