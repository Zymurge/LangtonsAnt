package LangtonsAnt;

public class Langtons {

    public static void main(String[] args) throws Exception {
        byte[][] b = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 } };
        var g = new Grid(b);
        Ant[] ants = { new Ant(1, 1, Grid.Heading.SOUTH), new Ant(0, 0, Grid.Heading.WEST), new Ant(2, 2, Grid.Heading.NORTH) };
        
        // Start er up
        System.out.println("Starting Grid:\n");
        System.out.printf("%s", g.Draw(ants));
        // Run it
        Run(24, ants, g);
        // Shut er down
        System.out.println("End");
    }
    
    public static void Run(int numMoves, Ant[] ants, Grid g) {
    	for(; numMoves > 0; numMoves--) {
    		for(Ant a : ants) {
    			// appears to be a pass by value, so must modify in a primitive way
    			var updated = g.MoveAnt(a);
    			a.X = updated.X;
    			a.Y = updated.Y;
    			a.Heading = updated.Heading;
    		}
    		
            System.out.printf("%s", g.Draw(ants));
    	}
    }
    
}

