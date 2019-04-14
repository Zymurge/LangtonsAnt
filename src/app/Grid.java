package app;

import java.util.ArrayList;

/*
 * Represents the two dimensional map and status of the coordinates
 * Uses a standard X,Y coord system, with 0,0 in the lower left.
 */ 
class Grid {
    ArrayList<ArrayList<Byte>> map;

    static final byte BLACK  = 0;
    static final byte WHITE  = 1;
    static final int  TOP    = 0;
    static final int  BOTTOM = 1;
    static final int  LEFT   = 2;
    static final int  RIGHT  = 3;

    /*
     * Constructor that accepts a preallocated mapping of coordinates
     */ 
    public Grid(byte[][] m) {
        map = new ArrayList<ArrayList<Byte>>(m.length);
        for (byte[] byteRow : m) {
            var alRow = new ArrayList<Byte>(byteRow.length);
            for (Byte b : byteRow) {
                alRow.add(b);
            }
            map.add(alRow);
        }
    }
    
    /*
     * Generates a string representation of the grid with one row per line.
     */
    public String Draw() {
        var out = new StringBuilder();
        out.append(DrawHorizontalLine(this.Width()));
        
        // Walk the rows in reverse order, since we want row 0 on bottom and increasing up the Y axis
        for (int r=this.Height()-1; r >= 0; r--) {
            var row = this.map.get(r);
            out.append("| ");
            for (byte b : row) {
                out.append(ByteToColor(b));
                out.append(' ');
            }
            out.append("|\n");
        }
    
        out.append(DrawHorizontalLine(this.Width()));
        return out.toString();
    }
    
    /*
     * Returns the value at the specified coordinate
     */ 
    public byte Get(int x, int y) {
        return this.map.get(y).get(x);
    }
    
    /*
     * Sets the value at the specified coordinate
     */
    public void Set(int x, int y, byte value) {
        this.map.get(y).set(x, value);
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
     * Adds a 0 filled column to either the 'left' or 'right' of the grid, maintaining 0,0 as the origin
     * If inserted left, the new col becomes y=0, and all existing cols shift right by 1
     * Returns the new Width of the grid.
     */
    public int AddCol(int where) {
        for(int y=0; y<this.Height(); y++) {
            switch (where) {
            case LEFT: 
                this.map.get(y).add(0, BLACK);
                break;
            case RIGHT:
                this.map.get(y).add(BLACK);
                break;
            }
        }
        return this.Width();
    }

    /*
     * Adds a 0 filled row to either the 'top' or 'bottom' of the grid, maintaining 0,0 as the origin
     * If inserted below, the new row becomes x=0, and all existing rows shift up by 1
     * Returns the new Height of the grid.
     */
    public int AddRow(int where) {
        var newRow = new ArrayList<Byte>(this.Width()+1);
        for(int x=0; x<this.Width(); x++) {
            newRow.add(BLACK);
        }
        switch (where) {
        case TOP: 
            this.map.add(this.Height(), newRow);
            break;
        case BOTTOM:
            this.map.add(0, newRow);
            break;
        }
        return this.Height();
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
     * Returns B for black, W for white
     */ 
    protected static char ByteToColor(byte b) {
        switch (b % 2) {
        case BLACK:
            return 'B';
        case WHITE:
            return 'W';
        default:
            // dumb compiler
            return 0;
        }
    }

    public static void main(String[] args) {
        byte[][] b = { { 00, 10, 20, 30 }, { 01, 11, 21, 31 }, { 02, 12, 22, 32 } };
        var g = new Grid(b);
        System.out.printf("Iniital Map:\n%s", g.Draw());

        System.out.printf("Adding a row to top to make %d rows\n", g.AddRow(TOP));
        System.out.printf("Map:\n%s", g.Draw());        
        System.out.printf("Adding a row to bottom to make %d rows\n", g.AddRow(BOTTOM));
        System.out.printf("Change 0,0 to make it cooler looking...\n");
        g.Set(0, 0, WHITE);
        System.out.printf("Map:\n%s", g.Draw());        
        System.out.printf("Adding a col to the left to make %d cols\n", g.AddCol(LEFT));
        System.out.printf("Map:\n%s", g.Draw());        
        System.out.printf("Adding a col to the right to make %d cols\n", g.AddCol(RIGHT));
        System.out.printf("Map:\n%s", g.Draw());        

        System.out.printf("H=%d, W=%d\n", g.Height(), g.Width());
        System.out.printf("Get(0,0)=%d\n", g.Get(0, 0));
        System.out.printf("Get(1,1)=%d\n", g.Get(1, 1));
        System.out.printf("Before: Get(2,1)=%d\n", g.Get(2, 1));
        g.Set(2, 1, BLACK);
        System.out.printf("After: Get(2,1)=%d\n", g.Get(2, 1));
    }

}
