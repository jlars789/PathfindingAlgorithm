package Entity;
import java.awt.Color;

import Main.ModRectangle;
import Main.Window;

public class EndGoal extends ModRectangle {
	
	private static final long serialVersionUID = 576838099210155207L;
	private static final int PIXEL_HEIGHT = Window.dimension;
	private static final Color GOAL_COLOR = Color.RED;

	public EndGoal() {
		super(0, 0, Window.WIDTH, PIXEL_HEIGHT, GOAL_COLOR);
	}
	

	@Override
	public void tick() {
		
	}
	public void reGrid()
	{
		this.height = Window.dimension;
	}

}
