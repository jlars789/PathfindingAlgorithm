import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Window extends JPanel implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 8303110920509931321L;
	
	private Thread thread;
	public static boolean running;
	public static final int WIDTH = 1024, HEIGHT = 1024; //Dimensions for Window
	public static final int DIMENSION = 32;
	
	private Runner runner;
	public static final EndGoal END = new EndGoal(); 

	/**
	 * Creates a Window that has a KeyListener
	 */
	
	public Window() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		start();
		
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
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		END.draw(g);
		
		g.setColor(Color.BLUE);
		int dim = 16;
		for(int i = 0; i < (int)(WIDTH/dim); i++) {
			g.drawLine(0, DIMENSION * i, WIDTH, DIMENSION*i);
		}
		for(int i = 0; i < (int)(HEIGHT/dim); i++) {
			g.drawLine(DIMENSION * i, 0, DIMENSION*i, HEIGHT);
		}
		
		
		runner.draw(g);
	}
	
	/**
	 * 
	 */
	
	public void tick() {
		try {
			Thread.sleep(16); //sets the rate of the game (1000 / integer) frames per second
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

}
