package LangtonsAnt;

import java.util.ArrayList;

/**
 * Represents the two dimensional map and status of the coordinates
 * Uses a standard X,Y coord system, with 0,0 in the lower left
 * @author mark
 */ 
public class Grid {
    ArrayList<ArrayList<ColorMap>> map;
   // Need to track the edge indices given that coordinates are dynamic and won't remain 0 based
    int minX = 0;
    int maxX = 0;
    int minY = 0;
    int maxY = 0;

    /**
     * Constant values for Grid coordinates
     */
    public enum ColorMap {
    	BLACK, WHITE;
    }
    
    /**
     * Constant values for Grid directions. Uses compass coordinates where NORTH is up, WEST is left, etc.
     */
   public enum Heading {
	    // ordering is important. Must be clockwise for turn calculations
    	NORTH('\u21E7'), EAST('\u21E8'), SOUTH('\u21E9'), WEST('\u21E6');
	    private char printValue;
	    
	    private Heading(char p) {
	    	this.printValue = p;
	    }
	    
	    public char PrintValue() {
	    	return this.printValue;
	    }
    }
    
   /**
    * Constant values for direction changes, where LEFT is a clockwise turn and RIGHT is counter
    */
   public enum Direction {
    	LEFT, RIGHT;
    }

    /**
     * Constructor that accepts a preallocated mapping of coordinates
     * Will construct a 0 based grid with X and Y coords increasing per additional row/col
     * @param mapValues A two dimensional array of [y] rows or [x] grid elements. Even values will be interpreted as white, 
     * while odd will be mapped as black. 
     * @throws IllegalArgumentException if mapValues are either empty or have inconsistent row lengths
     */ 
    public Grid(byte[][] mapValues) {
    	if(mapValues == null || mapValues.length < 1 || mapValues[0].length < 1) { 
    		throw new IllegalArgumentException("cannot instantiate Grid with null or empty mapValues");
    	}
        var firstRowLength = mapValues[0].length;
        map = new ArrayList<ArrayList<ColorMap>>(mapValues.length);
        for (byte[] byteRow : mapValues) {
        	if(byteRow.length != firstRowLength) { 
        		throw new IllegalArgumentException("all mapValues rows must be the same length");
        	}
            var alRow = new ArrayList<ColorMap>(byteRow.length);
            for (Byte b : byteRow) {
                alRow.add(ByteToColorMap(b));
            }
            map.add(alRow);
        }
        maxX = mapValues[0].length - 1;
        maxY = mapValues.length - 1;
    }
    
    /*
     * Generates a string representation of the grid with one row per line.
     * Takes an Ant as input to generate a marking for it's location and heading.
     */
    public String Draw(Ant loc) {
        var out = new StringBuilder();
        out.append(DrawHorizontalLine(this.Width()));
        
        // Walk the rows in reverse order, since we want row 0 on bottom and increasing up the Y axis
        for (int r=this.Height()-1; r >= 0; r--) {
            var row = this.map.get(r);
            out.append("| ");
            for (int x=0; x<row.size(); x++) {
            	var b = row.get(x);
            	var adjX = x+minX;
            	var adjY = r+minY;
            	if(adjY == loc.Y && adjX == loc.X) {
            		out.append(HeadingAsChar(loc.Heading));
            	} else {
            		char c = (b == ColorMap.BLACK ? 'B' : 'W');
            		out.append(c);
            	}
                out.append(' ');
            }
            out.append("|\n");
        }
    
        out.append(DrawHorizontalLine(this.Width()));
        out.append("Ant at " + loc.ToString() + "\n");
        return out.toString();
    }
    
    /**
     * @ants Array of ants to plot inside the JSON output
     * @returns a JSON representation of the grid, including location of all ants 
     */
    public String ToJson(Ant[] ants) {
        return new JsonGrid(
        		this.minX, this.maxX, this.minY, this.maxY, 
        		ants, 
        		this.toCharMap()
    		)
    		.toJson();
    }

	/**
	 * @return A simplified char representation of the Grid
	 */
	protected char[][] toCharMap() {
   		var result = new char[this.Height()][this.Width()];
  	    for (int r= 0; r < this.Height(); r++) {
  	    	var row = this.map.get(r);
            for (int x=0; x<row.size(); x++) {
            	var b = row.get(x);
        		char c = (b == ColorMap.BLACK ? 'B' : 'W');
 	            result[r][x] = c;
	        }
  	    }
  	    return result;
	}
 
    	/*
     * Returns the value at the specified coordinate
     */ 
    public ColorMap Get(int x, int y) {
        return this.map.get(y-minY).get(x-minX);
    }
    
    /*
     * Sets the value at the specified coordinate
     */
    public void Set(int x, int y, ColorMap value) {
        this.map.get(y-minY).set(x-minX, value);
    }
    
    /*
     * Returns the number of rows in the grid
     */
    public int Height() {
        return this.map.size();
    }
    
    /*
     * Returns the number of columns in the grid
     */
    public int Width() {
        return this.map.get(0).size();
    }
    
    /*
     * Adds a 0 filled column to either the 'west' or 'east' of the grid.
     * If inserted left (south), the cols are shifted for insertion and minX is decremented to track offset.
     * Returns the new Width of the grid.
     */
    public int AddCol(Heading where) {
        switch (where) {
        case WEST: 
            // insert a new first entry for each row
        	for(int y=0; y<this.Height(); y++) {
            	this.map.get(y).add(0, ColorMap.BLACK);
            }
            // decrement offset for values < 0
            minX--;
            break;
        case EAST:
            // insert a new last entry for each row
            for(int y=0; y<this.Height(); y++) {
            	this.map.get(y).add(ColorMap.BLACK);
            }
            maxX++;
            break;
        case NORTH:
        case SOUTH:
        	throw new IllegalArgumentException("AddCol can only accept WEST or EAST headings");
        }
        return this.Width();
    }

    /*
     * Adds a 0 filled row to either the 'north' or 'south' of the grid.
     * If inserted below (south), the rows are shifted for insertion and minY is decremented to track the offset.
     * Returns the new Height of the grid.
     */
    public int AddRow(Heading where) {
        var newRow = new ArrayList<ColorMap>(this.Width()+1);
        for(int x=0; x<this.Width(); x++) {
            newRow.add(ColorMap.BLACK);
        }
        switch (where) {
        case NORTH: 
            this.map.add(this.Height(), newRow);
            maxY++;
            break;
        case SOUTH:
            this.map.add(0, newRow);
            // decrement offset for values < 0
            minY--;
            break;
        case WEST:
        case EAST:
        	throw new IllegalArgumentException("AddRow can only accept NORTH or SOUTH headings");
        }
        return this.Height();
    }
    
    /*
     * Moves an ant object in the direction it is currently pointed, by returning a new
     * Ant instance with the post-move coordinates, while maintaining the same direction.
     * Will expand the Grid as needed when the move results in a point outside the boundaries.
     */
    public Ant MoveAnt(Ant a) {
    	var n = new Ant(a.X, a.Y, a.Heading);
    	switch(a.Heading) {
    	case NORTH:
    		n.Y = a.Y + 1;
    		if(n.Y == this.Height()+minY) {
    			this.AddRow(Heading.NORTH);
    		}
    		break;
    	case SOUTH:
    		if(a.Y == minY) {
    			this.AddRow(Heading.SOUTH);
    		}
    		n.Y = a.Y - 1;
    		break;
    	case EAST:
    		n.X = a.X + 1;
    		if(n.X == this.Width()+minX) {
    			this.AddCol(Heading.EAST);
    		}
    		break;
    	case WEST:
    		if(a.X == minX) {
    			this.AddCol(Heading.WEST);
     		}
    		n.X = a.X - 1;
    		break;
    	}
    	// Turn the Ant based on the current grid color and rules
    	n.Heading = this.Get(n.X, n.Y) == ColorMap.BLACK ?
    			Turn(n.Heading, Direction.LEFT) : 
    			Turn(n.Heading, Direction.RIGHT);
    	// Update the grid per rules
    	this.Set(n.X, n.Y, FlipColor(this.Get(n.X, n.Y)));
    	return n;
    }
   
    /*
     * Returns the opposite color of the input color
     */
    protected static ColorMap FlipColor(ColorMap in) {
    	return in == ColorMap.BLACK ? ColorMap.WHITE : ColorMap.BLACK;
    }
    
    /*
     * Helper function to generate a right sized horizontal dashed line
     */
    protected static String DrawHorizontalLine(int numElements) {
        var out = new StringBuilder(" -");
        for (var x = 0; x < numElements; x++) {
            out.append("--");
        }
        out.append("\n");
        return out.toString();
    }
    
    /*
     * Map byte values to either black (even) or white (odd)
     */ 
    protected static ColorMap ByteToColorMap(byte b) {
        ColorMap result = null;
    	switch (b % 2) {
        case 0:
            result = ColorMap.BLACK;
            break;
        case 1:
            result = ColorMap.WHITE;
            break;
        }
    	return result;
    }

    /**
     * @deprecated
     * TODO: replace calls with direct enum method
     */ 
    protected static char HeadingAsChar(Grid.Heading h) {
        return h.PrintValue();
    }

    /*
     * Calculates the result of a 90 degree turn from a specified heading.
     */
    protected static Heading Turn(Heading h, Direction d) {
    	var delta = (d == Direction.RIGHT) ? 1 : 3;
    	var change = (h.ordinal()+delta)%4;
    	return Heading.values()[change];
    }
    
}