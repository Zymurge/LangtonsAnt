package LangtonsAnt;

/*
 * Represents an Ant traversing the grid.
 */ 
class Ant {
    public int X;
    public int Y;
    public Grid.Heading Heading;
    
    public Ant(int x, int y, Grid.Heading d) {
    	this.X = x;
    	this.Y = y;
    	this.Heading = d;
    }
    
}
