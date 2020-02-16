import java.awt.Color;

public class Obstacle extends ModRectangle {

	private static final long serialVersionUID = -1033566932622000064L;

	public Obstacle(int x, int y) {
		super(x, y, Window.DIMENSION, Window.DIMENSION, Color.DARK_GRAY);
	}

	@Override
	public void tick() {
		
	}

}
