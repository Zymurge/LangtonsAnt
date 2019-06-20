package LangtonsAnt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonGridTest {
	
	static Ant[] testAnts = {
   	    new Ant( 1, 1, Grid.Heading.WEST),
	    new Ant( 2, 0, Grid.Heading.SOUTH)
	};		

	@Test
	void testJsonGrid() {
		char[][] map = {{'B','B','B'},{'W','B','W'},{'B','W','B'}};
		var target = new JsonGrid(0, 3, -1, 2, testAnts, map);
		assertNotNull(target, "Valid ctor should create instance");		
	}

	@Test
	void testToJson() {
		char[][] map = {{'B','B','B'},{'W','B','W'},{'B','W','B'}};
		var expected = (
				"{`minx`:0,`maxx`:3,`miny`:-1,`maxy`:2,`numants`:2,`map`:[[`B`,`B`,`B`],[`W`,`"
				+ Grid.Heading.WEST.PrintValue() 
				+ "`,`W`],[`" 
				+ Grid.Heading.SOUTH.PrintValue() 
				+ "`,`W`,`B`]]}"
			).replace('`','"');
		var target = new JsonGrid(0, 3, -1, 2, testAnts, map);
		var result = target.toJson();
		assertEquals(expected, result);
	}
	
	@Test
	void testNullMap() {
		var msg = assertThrows(NullPointerException.class, () -> new JsonGrid(0, 2, -1, 1, testAnts, null));
		assertTrue(msg.getMessage().contains("map"));
	}

}
