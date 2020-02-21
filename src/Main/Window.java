package Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Entity.EndGoal;
import Entity.Obstacle;
import Entity.Runner;

/**
 * 
 * @author lucas
 *	NOTES 
 *	- We should really store the EntityList.obstacles in a tree for fast access.
 */




public class Window extends JPanel implements Runnable, MouseListener, MouseMotionListener, ActionListener {

	private static final long serialVersionUID = 8303110920509931321L;
	
	private Thread thread;
	public static boolean running;
	protected boolean editing;
	protected boolean runnerClicked = false;
	protected int runnerX = 0;
	protected int runnerY = 0;
	protected int speed;
	public static int dimension = 32;
	public static final int WIDTH = 1024, HEIGHT = 960; //Dimensions for Window
	private PopupMenu menu;
	private MenuItem menuItem;
	
	private static Runner runner;
	public static final EndGoal END = new EndGoal(); 

	private Map map;
	

	/**
	 * Creates a Window that has a KeyListener
	 */
	
	public Window() {
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		menu = new PopupMenu();
		menuItem = new MenuItem("Set default runner reset location");
		menuItem.setActionCommand("set");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		add(menu);
		
		start();
		
		runner = new Runner();
		setObstacles();
	}
	
	public void start() {
		running = true;
		editing = true;
		speed = 16;
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getActionCommand() == "set")
			runner.setResetLocation(runnerX, runnerY);
	}
	
	private void addObstacle(int x, int y)
	{
		if (runner.yCoor() != y || runner.xCoor() != x && y != 0)
		{	
			Obstacle temp = new Obstacle(x,y);
			if (!EntityList.obstacles.contains(temp))
			EntityList.obstacles.add(temp);
		}
	}
	
	private void deleteObstacle(int x, int y)
	{
		EntityList.obstacles.remove(new Obstacle(x, y));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = (e.getX() / dimension) * dimension;
		int y = (e.getY() / dimension) * dimension;
		
		if (editing)
		{
			if (SwingUtilities.isRightMouseButton(e) && y != 0 && !EntityList.obstacles.contains(new Obstacle(x,y)))
			{
				menu.show(this, x + (dimension / 2), y + (dimension / 2));
				System.out.println("Right mouse button pressed");
			}
			else if (Toolbar.isAddOn)
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
		int x = (e.getX() / dimension) * dimension;
		int y = (e.getY() / dimension) * dimension;
		
		if (editing)
		{
			if (runnerClicked)
			{
				if (!EntityList.obstacles.contains(new Obstacle(x, y)))
					runner.relocate(x, y);
			}
			else if (Toolbar.isAddOn)
			{
				addObstacle(x, y);
			}
			else if (Toolbar.isDeleteOn)
			{
				deleteObstacle(x, y);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
			int x = (e.getX() / dimension) * dimension;
			int y = (e.getY() / dimension) * dimension;
			runnerX = x;
			runnerY = y;
			runnerClicked = runner.yCoor() == y && runner.xCoor() == x;
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (runnerClicked)
			runnerClicked = false;
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
		g.fillRect(0, 0, WIDTH, HEIGHT - HEIGHT % dimension);
		//______DO NOT DRAW ABOVE THIS LINE_____//
		END.draw(g);
		for(int i = 0 ; i < EntityList.obstacles.size(); i++) {
			EntityList.obstacles.get(i).draw(g);
		}
		
		g.setColor(Color.WHITE);
		for(int i = 0; i < (int)(WIDTH/dimension); i++) {
			g.drawLine(0, dimension * i, WIDTH, dimension*i);
			g.drawLine(dimension * i, 0, dimension*i, (int)(HEIGHT - HEIGHT % dimension));
		}
		runner.draw(g);
	}
	
	public void reGrid(int dimension)
	{
		Window.dimension = dimension;
		END.reGrid();
		runner.reGrid(dimension);
		setObstacles();
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
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method handles all logic, and is the root of all actions in the program
	 * All 
	 */
	public void tick() {
		int rate = speed;
		rate = editing ? 14 : speed; // make editing smoother
		try {
			Thread.sleep(rate * 32); //sets the rate of the window (1000 / integer) frames per second
			} catch(Exception e) {
				e.printStackTrace();
			}
		if (!editing)
			runner.tick();
	}

	public static Runner getRunner()
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
		EntityList.obstacles = new ArrayList<Obstacle>();
		Obstacle runnerPos = new Obstacle(runner.xCoor(), runner.yCoor());
		for(int i = 0; i <  map.getMap().length; i++) {
			for(int j = 0; j < map.getMap()[i].length; j++) {
				if(map.getMap()[i][j] == 1) {
					Obstacle e = new Obstacle(i * dimension, j * dimension);
					if (!runnerPos.equals(e))
						EntityList.obstacles.add(e);
				}
			}
		}
	}
	
	public void mouseMoved(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	

	
	
	

}
