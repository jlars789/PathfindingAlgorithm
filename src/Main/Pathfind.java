package Main;
import java.util.ArrayList;

import Entity.Runner;

/**
 * @author jlars
 * Used to create a pathfinding algorithm. Place logic in update method.
 */

public interface Pathfind {
	
	/**
	 * Called every frame on the {@link Runner} class
	 */
	
	public abstract void update();
	
	public abstract ArrayList<MoveNode> getMapPlan();
	
	public abstract MoveNode giveNext();
	
	

}
