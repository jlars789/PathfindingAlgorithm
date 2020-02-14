import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * @author lucas
 *	NOTES 
 *	- We should really store the obstacles in a tree for fast access.
 */




public class Window extends JPanel implements Runnable, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 8303110920509931321L;
	
	private Thread thread;
	public static boolean running;
	protected boolean editing;
	protected boolean runnerClicked = false;
	protected int runnerX = 0;
	protected int runnerY = 0;
	protected int speed;
	public static final int WIDTH = 1024, HEIGHT = 960; //Dimensions for Window
	public static final int DIMENSION = 32;
	
	private Runner runner;
	public static final EndGoal END = new EndGoal(); 
	
	private static ArrayList<Obstacle> obstacles;
	private Map map;
	

	/**
	 * Creates a Window that has a KeyListener
	 */
	
	public Window() {
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		start();
		
		setObstacles();
		runner = new Runner();
	}
	
	public void start() {
		running = true;
		editing = true;
		speed = 16;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The root of all Graphics
	 * All objects that are to be drawn on the screen at any time
	 * must have the Graphics Object passed through this method into their method.
	 * 
	 */
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//______DO NOT DRAW ABOVE THIS LINE_____//
		END.draw(g);
		
		for(int i = 0 ; i < obstacles.size(); i++) {
			obstacles.get(i).draw(g);
		}
		g.setColor(Color.WHITE);
		for(int i = 0; i < (int)(WIDTH/DIMENSION); i++) {
			g.drawLine(0, DIMENSION * i, WIDTH, DIMENSION*i);
			g.drawLine(DIMENSION * i, 0, DIMENSION*i, HEIGHT);
		}
		runner.draw(g);
	}
	
	/**
	 * This method handles all logic, and is the root of all actions in the program
	 * All 
	 */
	
	public void tick() {
		int rate = speed;
		rate = editing ? 2 : speed; // make editing smoother
		try {
			Thread.sleep(rate * DIMENSION); //sets the rate of the window (1000 / integer) frames per second
			} catch(Exception e) {
				e.printStackTrace();
			}
		if (!editing)
			runner.tick();
	}

	public Runner getRunner()
	{
		return runner;
	}
	
	public void setObstacles()
	{
		setObstacles(10);
	}
	public void setObstacles(int odds)
	{
		map = new Map(odds);
		obstacles = new ArrayList<Obstacle>();
		for(int i = 0; i <  map.getMap().length; i++) {
			for(int j = 0; j < map.getMap()[i].length; j++) {
				if(map.getMap()[i][j] == 1) {
					Obstacle e = new Obstacle(i * DIMENSION, j * DIMENSION);
					obstacles.add(e);
				}
			}
		}
	}
	
	/**
	 * Method that is called initially to start the program
	 */
	@Override
	public void run() {
		while(running) {
			tick(); //runs ticks while running is true
			repaint(); 
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = (e.getX() / DIMENSION) * DIMENSION;
		int y = (e.getY() / DIMENSION) * DIMENSION;
		
		if (editing)
		{
			if (Toolbar.isAddOn)
			{
				addObstacle(x, y);
			}
			else if (Toolbar.isDeleteOn)
			{
				deleteObstacle(x, y);
			}
		}	
	}
	
	public void mouseDragged(MouseEvent e)
	{
		int x = (e.getX() / DIMENSION) * DIMENSION;
		int y = (e.getY() / DIMENSION) * DIMENSION;
		
		if (editing)
		{
			if (Toolbar.isAddOn)
			{
				addObstacle(x, y);
			}
			else if (Toolbar.isDeleteOn)
			{
				deleteObstacle(x, y);
			}
			else if (Toolbar.isDragOn && runnerClicked)
			{
				boolean clear = true;
				for (int i = 0; i < obstacles.size(); i++)
					if (obstacles.get(i).yCoor() == y && obstacles.get(i).xCoor() == x)
					{
						clear = false;
						break;
					}
				if (clear)
					runner.reset(x, y);
			}
		}
	}
	
	private void deleteObstacle(int x, int y)
	{
		if ((runner.yCoor() != y || runner.xCoor() != x) && y != 0)
		{
			for (int i = 0; i < obstacles.size(); i++)
			{
				if (obstacles.get(i).yCoor() == y && obstacles.get(i).xCoor() == x)
				{
					obstacles.remove(i);
					break;
				}
			}
		}
	}
	
	private void addObstacle(int x, int y)
	{
		boolean clear = true;
		if (runner.yCoor() == y && runner.xCoor() == x || y == 0)
			clear = false;
		for (int i = 0; i < obstacles.size(); i++)
		{
			if (obstacles.get(i).yCoor() == y && obstacles.get(i).xCoor() == x)
			{
				clear = false;
				break;
			}
		}
		if (clear)
			obstacles.add(new Obstacle(x, y));
	}
	
	
	public void mouseMoved(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (Toolbar.isDragOn)
		{	
			int x = (e.getX() / DIMENSION) * DIMENSION;
			int y = (e.getY() / DIMENSION) * DIMENSION;
			if (runner.yCoor() == y && runner.xCoor() == x)
			{
				runnerClicked = true;
				runnerX = x;
				runnerY = y;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (runnerClicked)
			runnerClicked = false;
		
	}
	
	public static ArrayList<Obstacle> getList(){
		return obstacles;
	}

}
