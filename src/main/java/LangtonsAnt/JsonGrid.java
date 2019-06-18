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

public class JsonGrid {
	int minx;
	int maxx;
	int miny;
	int maxy;
	int numants;
	char[][] map;
	
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
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
