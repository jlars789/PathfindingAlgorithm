package Main;

import java.util.Random;

public class Map {
	
	private int[][] mapArray;
	private int oddsOutOf;
	private Random r;
 	
	public Map(int odds, long seed) {
		mapArray = new int[Window.WIDTH/Window.dimension][(int)(Window.HEIGHT/Window.dimension)];
		r = new Random(seed);
 		this.oddsOutOf = odds;
 		
 		generate();
	}
	
	public Map(int odds) {
 		this(odds, (long)(Long.MAX_VALUE * Math.random()));
		
	}
 	
 	private void generate() {

 		for(int i = 0; i < mapArray.length; i++) {
 			for(int j = 0; j < mapArray[i].length; j++) {
 				mapArray[i][j] = 0;
 			}
 		}
 		
 		
 		for(int i = 0; i < mapArray.length; i++) {
 			for(int j = 1; j < mapArray[i].length; j++) {
 				if((int)(r.nextInt(oddsOutOf)) == 1) {
 					mapArray[i][j] = 1;
 				}
 			}
 		}
 	}
 	
 	public void updateMap(long newSeed) {
 		r = new Random(newSeed);
 		generate();
 	}
 	
 	public int[][] getMap(){
 		return this.mapArray;
 	}

}
