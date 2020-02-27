package Entity;
import java.awt.Color;
import java.util.ArrayList;

import Main.EntityList;
import Main.ModRectangle;
import Main.Pathfind;
import Main.Window;

public class Runner extends ModRectangle {
	
	private static final long serialVersionUID = 4540485489379644218L;
	private static final int SIZE = Window.dimension * 1;
	private final static Color C = Color.PINK; 
	private static int initialX = Window.WIDTH / 2;//(Window.WIDTH / Window.dimension) * (Window.WIDTH - Window.dimension);
	private static int initialY = Window.HEIGHT - Window.dimension;
	
	private Pathfind path;
	private boolean inSimulation;
	
	//private ArrayList<Obstacle> inVision;
	
	
	public Runner(Pathfind p, boolean inSim) {
		super(initialX, initialY, SIZE, SIZE, C);
		this.inSimulation = inSim;
		path = p;
		
		if(!inSim) {
			p.setObstacleList(EntityList.obstacles);
		}
	}
	
	public Runner(Pathfind p) {
		this(p, false);
	}
	
	/**
	 * Current Algorithm:
	 * Checks to see where obstacles are around it. If obstacles are in front, it will move left, if obstacles are to left
	 * and front, it will move right, if left, right, and front are blocked, it will not move at all.
	 */
	
	public void tick() {
		
		path.update(this);
		
		//scans to see if to left and right
		
		
		
	}
	
	public void move(boolean[] canMove) {
		if(yCoor() > Window.END.getY()) {
			
			if(canMove[1]) this.shift(0, -Window.dimension);
			else if(canMove[0]) this.shift(-Window.dimension, 0);
			else if(canMove[2]) this.shift(Window.dimension, 0);
		}		
	}
	
	public void reGrid(int dimension)
	{
		this.width = dimension;
		this.height = dimension;
		initialX = Window.WIDTH / 2;
		initialY = (int)((Window.HEIGHT / 2.0) - (Window.HEIGHT / 2.0) % dimension ) ;
		//this.width = dimension;
		
		reset();
	}
	
	public void relocate(int x, int y)
	{
		setLocation(x, y);
	}
	
	public void reset()
	{
		setLocation(initialX, initialY);
	}
	public void setResetLocation(int x, int y)
	{
		initialX = x;
		initialY = y;
	}
	
	public void setAlgorithm(Pathfind p) {
		path = p;
	}
	
	public void updateAlg(ArrayList<Obstacle> obs) {
		path.setObstacleList(obs);
	}

}
