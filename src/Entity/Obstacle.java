package Entity;
import java.awt.Color;

import Main.ModRectangle;
import Main.Window;

public class Obstacle extends ModRectangle {

	private static final long serialVersionUID = -1033566932622000064L;

	public Obstacle(int x, int y) {
		super(x, y, Window.dimension, Window.dimension, Color.BLUE);
	}

	@Override
	public void tick() {
		
	}
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		else if (o == null || this.getClass() != o.getClass())
			return false;
		else
			return this.x == ((Obstacle)o).xCoor() && this.y == ((Obstacle)o).yCoor();
	}

}
