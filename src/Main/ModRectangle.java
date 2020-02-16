package Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author jlars789
 *
 * Abstract class that streamlines use of rectangles and simplifies object creation.
 *
 */

public abstract class ModRectangle extends Rectangle {

	private static final long serialVersionUID = 8349985943281756891L;
	
	private Color color;

	/**
	 * Creates a ModRectangle object
	 * @param x Specifies x coordinate
	 * @param y Specifies y coordinate
	 * @param width Specifies width of rectangle
	 * @param height Specifies height of rectangle
	 * @param color Specifies color of rectangle. Should be Color.[VALUE], for example, Color.PINK
	 */
	
	public ModRectangle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;
	}
	
	/**
	 * Creates a ModRectangle object
	 * @param x Specifies x coordinate
	 * @param y Specifies y coordinate
	 * @param color Specifies color of rectangle. Should be Color.[VALUE], for example, Color.PINK
	 */
	
	public ModRectangle(int x, int y, Color color) {
		super(x, y, Window.dimension, Window.dimension);
		this.color = color;
	}
	
	/**
	 * Creates a ModRectangle object
	 * @param color Specifies color of rectangle. Should be Color.[VALUE], for example, Color.PINK
	 */
	
	public ModRectangle(Color color) {
		super(0, 0, Window.dimension, Window.dimension);
		this.color = color;
	}
	
	/**
	 * Method that handles the logic/actions of the rectangle. 
	 * All specifications should be added to this.
	 */
	
	public abstract void tick();
	
	/**
	 * Draw method. This must be called in a method that is/can be traced back
	 * to the draw method in the {@link Window} class.
	 * @param g Graphics object from the Window class.
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Used to shift the ModRectangle in the x or y axis. 
	 * @param xShift Magnitude of a Left (negative) or Right (positive) shift
	 * @param yShift Magnitude of an Up (negative) or Down (positive) shift
	 */
	
	public void shift(int xShift, int yShift) {
		x+=xShift;
		y+=yShift;
	}
	
	/**
	 * The x coordinate in the form of an integer
	 * @return X location
	 */
	
	public int xCoor() {
		return (int)super.getX();
	}
	
	/**
	 * The y coordinate in the form of an integer
	 * @return Y location
	 */
	
	public int yCoor() {
		return (int)super.getY();
	}
	
	/**
	 * The width in the form of an integer
	 * @return width
	 */
	
	public int width() {
		return (int)super.getWidth();
	}
	
	/**
	 * The height in the form of an integer
	 * @return height
	 */
	
	public int height() {
		return (int)super.getHeight();
	}

}
