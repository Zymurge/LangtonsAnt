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
	
	/**
	 * Constructs a JsonGrid instance
	 * @param minx The lower boundary of the X axis
	 * @param maxx The upper boundary of the X axis
	 * @param miny The lower boundary of the Y axis
	 * @param maxx The upper boundary of the Y axis
	 * @throws NullPointerException if map isn't populated with at least 1 column with 1 row
	 */
	public JsonGrid(int minx, int maxx, int miny, int maxy, int numants, char[][] map) {
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.numants = numants;
		this.map = map;
		
		if( this.map == null ) {
			throw new NullPointerException("map parameter must be at least 1x1");
		}
	}
	
	/**
	 * Generates the JSON output
	 * @return A JSON string
	 */
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
