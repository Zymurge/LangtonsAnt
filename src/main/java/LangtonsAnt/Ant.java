package LangtonsAnt;

/**
 * Represents an Ant traversing the grid.
 */ 
class Ant {
    public int X;
    public int Y;
    public Grid.Heading Heading;
    
    /**
     * Basic ctor
     * @param x Ant's current X coord
     * @param y Ant's current Y coord
     * @param heading Ant's current @Grid.Heading
     */
    public Ant(int x, int y, Grid.Heading heading) {
    	this.X = x;
    	this.Y = y;
    	this.Heading = heading;
    }
    
    /**
     * @return a simple debugging string
     */
    public String ToString() {
    	return String.format("(%d,%d) %s", this.X, this.Y, this.Heading);
    }
    
}
