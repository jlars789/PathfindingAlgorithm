import java.awt.Color;
import java.util.ArrayList;

public class Runner extends ModRectangle {
	
	private static final long serialVersionUID = 4540485489379644218L;
	private static final int SIZE = Window.DIMENSION * 1;
	private final static Color C = Color.PINK; 
	
	private ArrayList<Obstacle> inVision;
	
	public Runner() {
		super(448, 928, SIZE, SIZE, C);
		inVision = new ArrayList<Obstacle>();
	}
	
	/**
	 * Current Algorithm:
	 * Checks to see where obstacles are around it. If obstacles are in front, it will move left, if obstacles are to left
	 * and front, it will move right, if left, right, and front are blocked, it will not move at all.
	 */
	
	public void tick() {
		
		boolean[] canMove = {true, true, true}; //left, up, right
		ArrayList<Obstacle> ref = Window.getList();
		
		//scans to see if to left and right
		for(int i = 0; i < ref.size(); i++) {
			if(Math.abs(ref.get(i).xCoor()-this.xCoor()) <= Window.DIMENSION && Math.abs(ref.get(i).yCoor()-this.yCoor()) == 0) {
				inVision.add(ref.get(i));
				if(ref.get(i).xCoor() > xCoor()) canMove[2] = false;
				else canMove[0] = false;
			}
			//scans to see if above or below
			else if(Math.abs(ref.get(i).yCoor()-this.yCoor()) <= Window.DIMENSION && Math.abs(ref.get(i).xCoor()-this.xCoor()) == 0) {
				inVision.add(ref.get(i));
				canMove[1] = false;
			}
		}
		
		for(int i = 0; i < inVision.size(); i++) {
			if(Math.abs(inVision.get(i).xCoor()-this.xCoor()) >= Window.DIMENSION) {
				inVision.remove(i);
			}
		}
		
		if(yCoor() > Window.END.getY()) {
			
			if(canMove[1]) this.shift(0, -Window.DIMENSION);
			else if(canMove[0]) this.shift(-Window.DIMENSION, 0);
			else if(canMove[2]) this.shift(Window.DIMENSION, 0);
		}
		
		
	}
	
	

}
