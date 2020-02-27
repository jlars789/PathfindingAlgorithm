package Main;
import java.util.ArrayList;

import Entity.Obstacle;
import Entity.Runner;

/**
 * @author jlars
 * Used to create a pathfinding algorithm. Place logic in update method.
 */

public abstract class Pathfind {
	
	public Pathfind() {
		
	}
	
	/**
	 * Called every frame on the {@link Runner} class
	 */
	
	public abstract void update(Runner runRef);
	
	public abstract ArrayList<MoveNode> getMapPlan();
	
	public abstract MoveNode giveNext();
	
	/**Name must contain no spaces, and only characters recognizable by windows filing system*/
	public abstract String getName();
	
	public abstract void setObstacleList(ArrayList<Obstacle> obs);
	
	public abstract ArrayList<Obstacle> getObstacleList();
	
	

}
