import java.awt.Color;

public class Runner extends ModRectangle {
	
	private static final long serialVersionUID = 4540485489379644218L;
	private static final int SIZE = Window.DIMENSION * 1;
	private final static Color C = Color.PINK; 
	
	public Runner() {
		super(448, 928, SIZE, SIZE, C);
	}
	
	public void tick() {
		if(yCoor() > Window.END.getY()) {
			this.shift(0, -Window.DIMENSION);
		}
		
		
	}
	
	

}
