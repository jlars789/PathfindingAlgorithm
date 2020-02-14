
public class Map {
	
	private int[][] mapArray = new int[Window.WIDTH/Window.DIMENSION][Window.HEIGHT/Window.DIMENSION];
	private int oddsOutOf;
 	public Map(int odds) {
 		
 		this.oddsOutOf = odds;
 		
 		for(int i = 0; i < mapArray.length; i++) {
 			for(int j = 0; j < mapArray[i].length; j++) {
 				mapArray[i][j] = 0;
 			}
 		}
 		
 		generate();
	}
 	
 	private void generate() {
 		for(int i = 0; i < mapArray.length; i++) {
 			for(int j = 1; j < mapArray[i].length-1; j++) {
 				if((int)(Math.random() * oddsOutOf) == 1) {
 					mapArray[i][j] = 1;
 				}
 			}
 		}
 	}
 	
 	public int[][] getMap(){
 		return this.mapArray;
 	}

}
