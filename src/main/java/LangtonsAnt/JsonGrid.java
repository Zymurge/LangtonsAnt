package LangtonsAnt;

import com.google.gson.Gson;

/*
 * 		"minx": -3,
		"maxx":  1,
		"miny": -2,
		"maxy":  2,
		"numants": 2,
		"map":
			[
				[ "B", "W", "W", "W", "W" ],
				[ "B", "B", "W", "W", "v" ],
				[ "B", "B", "B", "W", "W" ],
				[ "B", "B", "<", "B", "W" ],
				[ "B", "B", "B", "B", "B" ]
			]
 */
/**
 * Utility class used to create a custom JSON output for the LangstonsAnt Grid state
 * @author mark
 */
public class JsonGrid {
	int minx;
	int maxx;
	int miny;
	int maxy;
	int numants;
	char[][] map;
	// transient tag auto-excludes from JSON output using GSON
	transient Ant[] ants;
	
	/**
	 * Constructs a JsonGrid instance
	 * @param minx The lower boundary of the X axis
	 * @param maxx The upper boundary of the X axis
	 * @param miny The lower boundary of the Y axis
	 * @param maxx The upper boundary of the Y axis
     * @ants array of ants to plot inside the JSON output
	 * @throws NullPointerException if map isn't populated with at least 1 column with 1 row
	 */
	public JsonGrid(int minx, int maxx, int miny, int maxy, Ant[] ants, char[][] map) {
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.numants = ants.length;
		this.ants = ants;
		this.map = map;
		
		if( this.map == null ) {
			throw new NullPointerException("map parameter must be at least 1x1");
		}
	
		// update appropriate grid coordinates with ant locations
		for(Ant a : this.ants) {
			map[a.X][a.Y] = Grid.HeadingAsChar(a.Heading);
		}
	}
	
    /**
     * @returns a JSON representation of the grid, including location of all ants 
     */
	public String toJson() {
		return new Gson().toJson(this);
	}

}
