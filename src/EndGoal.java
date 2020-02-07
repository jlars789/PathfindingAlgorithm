import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EndGoal {
	
	private static final int PIXEL_HEIGHT = 32;
	private static final Color RUNNER_COLOR = Color.RED;
	
	private Rectangle end;

	public EndGoal() {
		end = new Rectangle(0, 0, Window.WIDTH, PIXEL_HEIGHT);
	}
	
	public void draw(Graphics g) {
		g.setColor(RUNNER_COLOR);
		g.fillRect((int)end.getX(), (int)end.getY(), (int)end.getWidth(), (int)end.getHeight());
	}
	
	public Rectangle getRect() {
		return this.end;
	}

}
