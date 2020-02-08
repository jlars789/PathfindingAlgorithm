import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Window extends JPanel implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 8303110920509931321L;
	
	private Thread thread;
	public static boolean running;
	public static final int WIDTH = 1024, HEIGHT = 1024; //Dimensions for Window
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
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		start();
		
		map = new Map(10);
		
		obstacles = new ArrayList<Obstacle>();
		
		for(int i = 0; i <  map.getMap().length; i++) {
			for(int j = 0; j < map.getMap()[i].length; j++) {
				if(map.getMap()[i][j] == 1) {
					Obstacle e = new Obstacle(i * DIMENSION, j * DIMENSION);
					obstacles.add(e);
				}
			}
		}
		runner = new Runner();
	}
	
	public void start() {
		running = true;
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
		}
		for(int i = 0; i < (int)(HEIGHT/DIMENSION); i++) {
			g.drawLine(DIMENSION * i, 0, DIMENSION*i, HEIGHT);
		}
		
		
		runner.draw(g);
	}
	
	/**
	 * This method handles all logic, and is the root of all actions in the program
	 * All 
	 */
	
	public void tick() {
		try {
			Thread.sleep(16 * DIMENSION); //sets the rate of the window (1000 / integer) frames per second
			} catch(Exception e) {
				e.printStackTrace();
			}
		
		runner.tick();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static ArrayList<Obstacle> getList(){
		return obstacles;
	}

}
