import java.awt.Color;

public class Obstacle extends ModRectangle {

	private static final long serialVersionUID = -1033566932622000064L;

	public Obstacle(int x, int y) {
		super(x, y, Window.DIMENSION, Window.DIMENSION, Color.DARK_GRAY);
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
