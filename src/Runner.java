import java.awt.Color;
import java.util.ArrayList;

public class Runner extends ModRectangle {
	
	private static final long serialVersionUID = 4540485489379644218L;
	private static final int SIZE = Window.DIMENSION * 1;
	private final static Color C = Color.PINK; 
	private final static int INITIAL_X = 448;
	private final static int INITIAL_Y = 928;
        
	private AStarPathFinding alpha;
	private ArrayList<Obstacle> inVision;
	
	public Runner() {
		super(INITIAL_X, INITIAL_Y, SIZE, SIZE, C);
		inVision = new ArrayList<Obstacle>();
                int[] aleph = {INITIAL_X/Window.DIMENSION,INITIAL_Y/Window.DIMENSION};
                int[] objective = {0,0};
                alpha  = new AStarPathFinding(Window.WIDTH/Window.DIMENSION,Window.HEIGHT/Window.DIMENSION,aleph,objective);
	}
	
	/**
	 * Current Algorithm:
	 * Checks to see where obstacles are around it. If obstacles are in front, it will move left, if obstacles are to left
	 * and front, it will move right, if left, right, and front are blocked, it will not move at all.
	 */
	
	public void tick() {
		
		ArrayList<Obstacle> ref = Window.getList();
                alpha.ApplyLocalObstacles(ref);
                if(alpha.initConditionsSet()== true){
                  alpha.run();
                }
                ArrayList<MoveNode> Step = alpha.GetPath();
                int[] absom = {(Step(Step.size()-1).xCoor*Window.DIMENSION),(Step(Step.size()-1).yCoor*Window.DIMENSION)};
                this.shift(absom[0]-this.xCoor,absom[1]-this.yCoor);
				
	}
	
	public void reset()
	{
		inVision = new ArrayList<Obstacle>();
		setLocation(INITIAL_X, INITIAL_Y);
	}
	public void reset(int x, int y)
	{
		inVision = new ArrayList<Obstacle>();
		setLocation(x, y);
	}
	
	

}
