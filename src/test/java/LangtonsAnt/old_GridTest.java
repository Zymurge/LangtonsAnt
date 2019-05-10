package LangtonsAnt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

    byte[][] testInBytes = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 }, { 1, 1, 1 } };
    Grid target;
    
	@BeforeEach
	void setUp() throws Exception {
		target = new Grid(testInBytes);
		assertNotNull(target);	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCtorHeightAndWidth() {
		assertEquals(4, target.Height(), "Validate pre-load with height check");
		assertEquals(3, target.Width(), "Validate pre-load with width check");
	}
	
	@Test
	void testAccessors() {
		byte[][] b = { {0, 1}, {1, 0} };
		Grid result = new Grid(b);
		// validate that Get works on the preset value of 0
		assertEquals(0, result.Get(0, 0), "Initial value");
		// check that it can be set to 1 and fetched
		result.Set(0,  0, (byte) 1 );
		assertEquals(1, result.Get(0, 0), "After Set");
	}

	@Test
	void testAddColLeft() {
		Grid result = new Grid(testInBytes);
		var oldWidth = result.Width();
		var newWidth = result.AddCol(Grid.LEFT);
		assertEquals(oldWidth+1, newWidth, "Expected width to expand by 1");
		// validate the first entry of each row is the default new value (0)
		for(int x=0; x < result.Height(); x++) {
			assertEquals(0, result.Get(0, x), "Each row should have 0 as the first value");
		}
	}
	
	@Test
	void testAddColRight() {
		Grid result = new Grid(testInBytes);
		var oldWidth = result.Width();
		var newWidth = result.AddCol(Grid.RIGHT);
		assertEquals(oldWidth+1, newWidth, "Expected width to expand by 1");
		// validate the first entry of each row is the default new value (0)
		for(int x=0; x < result.Height(); x++) {
			assertEquals(0, result.Get(newWidth-1, x), "Each row should have 0 as the first value");
		}
	}
	
	@Test
	void testAddRowTop() {
		Grid result = new Grid(testInBytes);
		var oldHeight = result.Height();
		var newHeight = result.AddRow(Grid.TOP);
		assertEquals(oldHeight+1, newHeight, "Expected height to expand by 1");
		// validate all entries of the added row is the default new value (0)
		for(int x=0; x < result.Width(); x++) {
			assertEquals(0, result.Get(x, newHeight-1), "Each col in the row should have 0 as the value");
		}
	}
	
	@Test
	void testDrawReallyBasic() {
		var result = target.Draw();
		assertEquals(result," -------\n| W W W |\n| W B B |\n| B B W |\n| B W B |\n -------\n");
	}
	
}
