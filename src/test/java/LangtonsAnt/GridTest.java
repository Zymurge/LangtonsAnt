package LangtonsAnt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import LangtonsAnt.Grid.Heading;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

public class GridTest {

    byte[][] testInBytes = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 }, { 1, 1, 1 } };
    Grid target;
    
    @Nested
    class BasicTest {
	    @BeforeEach
		void setUp() throws Exception {
			target = new Grid(testInBytes);
			assertNotNull(target);	}
		
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
		void testDrawReallyBasic() {
			var result = target.Draw();
			assertEquals(result," -------\n| W W W |\n| W B B |\n| B B W |\n| B W B |\n -------\n");
		}
		
    }
    
	@Nested
	class ExpandGridTest {

		Grid result;
		int oldWidth;
		int oldHeight;
		
		@BeforeEach
		void setUp() {
			result = new Grid(testInBytes);
			oldWidth = result.Width();
			oldHeight = result.Height();
			
		}
		
		@Test
		void testAddColLeft() {
			var newWidth = result.AddCol(Grid.Heading.WEST);
			assertEquals(oldWidth+1, newWidth, "Expected width to expand by 1");
			// validate the first entry of each row, now x=-1 is the default new value (0)
			for(int y=0; y < result.Height(); y++) {
				assertEquals(Grid.BLACK, result.Get(-1, y), "Each row should have 0 as the first value");
			}
		}
		
		@Test
		void testAddColRight() {
			var newWidth = result.AddCol(Grid.Heading.EAST);
			assertEquals(oldWidth+1, newWidth, "Expected width to expand by 1");
			// validate the first entry of each row is the default new value (0)
			for(int y=0; y < result.Height(); y++) {
				assertEquals(Grid.BLACK, result.Get(newWidth-1, y), "Each row should have 0 as the first value");
			}
		}
		
		@Test
		void testAddRowTop() {
			var newHeight = result.AddRow(Grid.Heading.NORTH);
			assertEquals(oldHeight+1, newHeight, "Expected height to expand by 1");
			// validate all entries of the added row is the default new value (0)
			for(int x=0; x < result.Width(); x++) {
				assertEquals(Grid.BLACK, result.Get(x, newHeight-1), "Each col in the row should have 0 as the value");
			}
		}
		
		@Test
		void testAddRowBottom() {
			var newHeight = result.AddRow(Grid.Heading.SOUTH);
			assertEquals(oldHeight+1, newHeight, "Expected height to expand by 1");
			// validate all entries of the added row, now y=-1, is the default new value (0)
			for(int x=0; x < result.Width(); x++) {
				assertEquals(Grid.BLACK, result.Get(x, -1), "Each col in the row should have 0 as the value");
			}
		}
	}

	@Nested
	class TurnTest {
		
		void turnAndValidate(Grid.Direction turn, Grid.Heading start, Grid.Heading expected) {
			var result = Grid.Turn(start, turn);
			assertEquals(expected, result);
		}
		
		@Test
		void testLeftTurns() {
			turnAndValidate(Grid.Direction.LEFT, Grid.Heading.NORTH, Grid.Heading.WEST);
			turnAndValidate(Grid.Direction.LEFT, Grid.Heading.WEST, Grid.Heading.SOUTH);
			turnAndValidate(Grid.Direction.LEFT, Grid.Heading.SOUTH, Grid.Heading.EAST);
			turnAndValidate(Grid.Direction.LEFT, Grid.Heading.EAST, Grid.Heading.NORTH);
		}

		@Test
		void testRightTurns() {
			turnAndValidate(Grid.Direction.RIGHT, Grid.Heading.NORTH, Grid.Heading.EAST);
			turnAndValidate(Grid.Direction.RIGHT, Grid.Heading.WEST, Grid.Heading.NORTH);
			turnAndValidate(Grid.Direction.RIGHT, Grid.Heading.SOUTH, Grid.Heading.WEST);
			turnAndValidate(Grid.Direction.RIGHT, Grid.Heading.EAST, Grid.Heading.SOUTH);
		}
	}
	
	@Nested
	class MoveAntTest {
		
		void validateAnt(Ant a, int x, int xExpect, int y, int yExpect, Grid.Heading dir, Grid.Heading dirExpect) {
			assertEquals(x, a.X, String.format("X should be changed %d for move direction: %s", xExpect, dir));
			assertEquals(y, a.Y, String.format("Y should be changed %d for move direction: %s", yExpect, dir));
			assertEquals(dirExpect, a.Heading, String.format("Heading should be turned %s after move", dirExpect));
		}

		@Nested
		class InBoundryTest {
		
			Grid target;
			Ant antBefore;
			int heightBefore;
			int widthBefore;
			
			void validateGridUnexpanded() {
				assertEquals(heightBefore, target.Height(), "Grid height should not change for in bounds move");
				assertEquals(widthBefore, target.Width(), "Grid width should not change for in bounds move");
			}

			void testDirection(Grid.Heading inDir, Grid.Heading newDir, int deltaX, int deltaY) {
				// precalc the destination coords for convenience
				var newX = antBefore.X+deltaX;
				var newY = antBefore.Y+deltaY;
				// get the destination grid color before
				var colorBefore = target.Get(newX, newY );
				// precalc the expected change (toggle black<->white)
				var colorAfter = (colorBefore+1)%2;
				// override the default heading in global setup
				antBefore.Heading = inDir;
				
				// execute test method
				var antAfter = target.MoveAnt(antBefore);
				
				validateGridUnexpanded();
				validateAnt(antAfter, newX, deltaX, newY, deltaY, inDir, newDir);
				assertEquals(target.Get(newX, newY), colorAfter);
			}
			
			@BeforeEach
			void setUp() throws Exception {
				target = new Grid(testInBytes);
				heightBefore = target.Height();
				widthBefore = target.Width();
				antBefore = new Ant(1, 1, Grid.Heading.NORTH);
			}

			@Test
			void testNorth() { // 1,2 = Black
				testDirection(Heading.NORTH, Heading.WEST, 0, 1);
			}

			@Test
			void testWest() { // 0,1 = Black
				testDirection(Heading.WEST, Heading.SOUTH, -1, 0);
			}

			@Test
			void testSouth() { // 1,0 = White
				testDirection(Heading.SOUTH, Heading.WEST, 0, -1);
			}

			@Test
			void testEast() { // 2,1 = White
				testDirection(Heading.EAST, Heading.SOUTH, 1, 0);
			}
		}
		
		@Nested
		class OutOfBoundryTest {

			Grid tinyGrid;
			Ant antBefore;
			
			// replace 
			void validateGridExpanded(Grid.Heading d) {
				switch(d) {
				case NORTH:
				case SOUTH:
					assertEquals(2, tinyGrid.Height(), "Grid should expand Y axis by 1");
					assertEquals(1, tinyGrid.Width(), "Grid width should not change for Y expansion");
					break;
				case WEST:
				case EAST:
					assertEquals(1, tinyGrid.Height(), "Grid height should not change for X expansion");
					assertEquals(2, tinyGrid.Width(), "Grid should expand X axis by 1");
					break;
				}
			}

			void testDirection(Grid.Heading inDir, Grid.Heading newDir, int deltaX, int deltaY) {
				// precalc the destination coords for convenience
				var newX = antBefore.X+deltaX;
				var newY = antBefore.Y+deltaY;
				// all newly created points start as black (0)
				var colorBefore = (byte)0;
				// precalc the expected change (toggle black<->white)
				var colorAfter = (colorBefore+1)%2;
				// override the default heading in global setup
				antBefore.Heading = inDir;
				
				// execute test method
				var antAfter = tinyGrid.MoveAnt(antBefore);
				
				validateGridExpanded(inDir);
				validateAnt(antAfter, newX, deltaX, newY, deltaY, inDir, newDir);
				assertEquals(tinyGrid.Get(newX, newY), colorAfter);
			}

			@BeforeEach
			void setUp() throws Exception {
				byte[][] b = { {0} };
				tinyGrid = new Grid(b);
				antBefore = new Ant(0, 0, Grid.Heading.NORTH);
			}

			@Test
			void testNorth() { 
				testDirection(Heading.NORTH, Heading.WEST, 0, 1);
			}

			@Test
			void testWest() { 
				testDirection(Heading.WEST, Heading.SOUTH, -1, 0);
			}

			@Test
			void testSouth() { 
				testDirection(Heading.SOUTH, Heading.EAST, 0, -1);
			}

			@Test
			void testEast() { 
				testDirection(Heading.EAST, Heading.NORTH, 1, 0);
			}
		}

	} // End MoveAnt
	
}