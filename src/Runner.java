import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Runner {
	
	private int x, y;
	private Rectangle rectangle;
	private int size = Window.DIMENSION * 2;
	private final static Color C = Color.PINK; 
	
	public Runner() {
		x = 448;
		y = 928;
		rectangle = new Rectangle(468, 936, size, size);
	}
	
	public void draw(Graphics g) {
		g.setColor(C);
		g.fillRect((int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
	}
	
	public void tick() {
		if(rectangle.getY() > Window.END.getRect().getY()) {
			y-=Window.DIMENSION;
		}
		
		rectangle.setLocation(x, y);
	}
	
	

}
