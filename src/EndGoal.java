import java.awt.Color;

public class EndGoal extends ModRectangle {
	
	private static final long serialVersionUID = 576838099210155207L;
	private static final int PIXEL_HEIGHT = 32;
	private static final Color GOAL_COLOR = Color.RED;

	public EndGoal() {
		super(0, 0, Window.WIDTH, PIXEL_HEIGHT, GOAL_COLOR);
	}

	@Override
	public void tick() {
		
	}

}
