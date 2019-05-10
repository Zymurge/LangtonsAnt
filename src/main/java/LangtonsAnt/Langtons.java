package LangtonsAnt;

public class Langtons {

    public static void main(String[] args) throws Exception {
        byte[][] b = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 } };
        var g = new Grid(b);
        var a = new Ant(1, 1, Grid.Heading.SOUTH);
        
        // Start er up
        System.out.println("Starting Grid:\n");
        System.out.printf("%s", g.Draw(a));
        // Run it
        Run(24, a, g);
        // Shut er down
        System.out.println("End");
    }
    
    public static void Run(int numMoves, Ant a, Grid g) {
    	for(; numMoves > 0; numMoves--) {
    		a = g.MoveAnt(a);
            System.out.printf("%s", g.Draw(a));
    	}
    }
    
}

