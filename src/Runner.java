import java.awt.Color;
import java.util.ArrayList;

public class Runner extends ModRectangle {
	
	private static final long serialVersionUID = 4540485489379644218L;
	private static final int SIZE = Window.dimension * 1;
	private final static Color C = Color.PINK; 
	private static int initialX = Window.WIDTH / 2;//(Window.WIDTH / Window.dimension) * (Window.WIDTH - Window.dimension);
	private static int initialY = Window.HEIGHT / 2;//(Window.HEIGHT / Window.dimension) * (Window.HEIGHT - Window.dimension);
	
	private ArrayList<Obstacle> inVision;
	
	public Runner() {
		super(initialX, initialY, SIZE, SIZE, C);
		inVision = new ArrayList<Obstacle>();
	}
	
	/**
	 * Current Algorithm:
	 * Checks to see where obstacles are around it. If obstacles are in front, it will move left, if obstacles are to left
	 * and front, it will move right, if left, right, and front are blocked, it will not move at all.
	 */
	
	public void tick() {
		
		boolean[] canMove = {true, true, true}; //left, up, right
		ArrayList<Obstacle> ref = Window.getList();
		
		for(int i = 0; i < ref.size(); i++) {
			if(Math.abs(ref.get(i).xCoor()-this.xCoor()) <= Window.dimension && Math.abs(ref.get(i).yCoor()-this.yCoor()) == 0) {
				inVision.add(ref.get(i));
				if(ref.get(i).xCoor() > xCoor()) canMove[2] = false;
				else canMove[0] = false;
			}
			else if(Math.abs(ref.get(i).yCoor()-this.yCoor()) <= Window.dimension && Math.abs(ref.get(i).xCoor()-this.xCoor()) == 0 && this.yCoor() > ref.get(i).yCoor()) {
				inVision.add(ref.get(i));
				canMove[1] = false;
			}
		}
		
		for(int i = 0; i < inVision.size(); i++) {
			if(Math.abs(inVision.get(i).xCoor()-this.xCoor()) >= Window.dimension) {
				inVision.remove(i);
			}
		}
		
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
		inVision = new ArrayList<Obstacle>();
		setLocation(x, y);
	}
	
	public void reset()
	{
		inVision = new ArrayList<Obstacle>();
		setLocation(initialX, initialY);
	}
	public void setResetLocation(int x, int y)
	{
		initialX = x;
		initialY = y;
	}
	

}
