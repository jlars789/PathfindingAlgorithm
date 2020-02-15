import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;

/**
 * 
 * @author lucas
 * This menu class provides testing environment customization features. These features include pausing
 * and resuming the runner object. When the runner is paused, one is able to create a new map, add or 
 * delete obstacle objects (by clicking or dragging), and drag the runner object to a new, valid location.
 * The runner object's location can be set and playback speed can be changed whether or not it is paused.
 * 
 * Caveat: I had to make substantial changes to the Window class, as well as some minor modifications to 
 * the Main class in order to make this work.
 * 
 * If desired, I could 
 */

public class Toolbar extends JPanel implements ActionListener
{
	private static final int DIMENSION = Window.DIMENSION;
	private static final int WIDTH = 1024, HEIGHT = 2 * DIMENSION; //Dimensions for Window
	private Window window;
	
	private Button startPause; 
	private Button reset;
	private Button newMap;
	private Button modObstacle;
	private Button playback;
	private Button[] buttons = {startPause, reset, newMap, modObstacle, playback};
	private String[] initialButtonNames = {"Start", "Reset", "New Map", "Delete", "Playback: 1"};
	private Label oddsLabel;
	private TextField oddsField;
	
	protected static boolean isAddOn = false;
	protected static boolean isDeleteOn = true;
	protected static boolean isDragOn = false;
	
	public Toolbar()
	{
		System.exit(0);
	}
	
	public Toolbar(Window window)
	{
		super(new FlowLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.CYAN);
		
		this.window = window;
		
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new Button(initialButtonNames[i]);
			buttons[i].addActionListener(this);
			buttons[i].setBackground(Color.ORANGE);
			add(buttons[i]);
			if (i == 2)
			{
				oddsLabel = new Label("New Map Odds:");
				oddsField = new TextField("");
				oddsField.setPreferredSize(new Dimension(60, 25));
				add(oddsLabel);
				add(oddsField);
			}
		}
		startPause = buttons[0];
		reset = buttons[1];
		newMap = buttons[2];
		modObstacle = buttons[3];
		playback = buttons[4];
	}
	/**
	 * You can drag-delete in 'delete' mode, as you can in 'add' mode.
	 * 'Drag' mode will allow you to reposition the runner object.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Button pressed = (Button) e.getSource();
		String buttonName = pressed.getLabel();
		
		if (buttonName.equals("Start")) // Allows runner object to tick by setting Window editing var to true
		{ 
			pressed.setLabel("Pause");
			newMap.setEnabled(false);
			modObstacle.setEnabled(false);
			window.editing = false;
		} 
		else if (buttonName.equals("Pause"))
		{ 
			pressed.setLabel("Start");
			newMap.setEnabled(true);
			modObstacle.setEnabled(true);
			window.editing = true;
		}
		else if (buttonName.equals("Reset")) // runner put back in initial position
		{ 
			window.getRunner().reset();
			startPause.setLabel("Start");
			modObstacle.setEnabled(true);
			newMap.setEnabled(true);
			window.editing = true;
		}
		else if (buttonName.equals("New Map")) // creates new map and repaints Window
		{ 
			String odds = oddsField.getText();
			int oddsNum;
			
			try
			{
				oddsNum = Integer.parseInt(odds);
			}
			catch (NumberFormatException ex)
			{
				oddsNum = 10;
			}
			if (oddsNum < 0)
				oddsNum = 10;
			if (oddsNum > 200)
				oddsNum = 200;
			
			oddsField.setText("" + oddsNum);
			
			window.setObstacles(oddsNum);
			window.getRunner().reset();
			startPause.setLabel("Start");
			modObstacle.setEnabled(true);
			window.editing = true;
		}
		else if (buttonName.equals("Add"))// mouse click function changes from add to delete
		{ 
			pressed.setLabel("Delete");
			isAddOn = false;
			isDeleteOn = true;
		}
		else if (buttonName.equals("Delete"))// mouse click function changes from delete to drag
		{ 
			pressed.setLabel("Drag");
			isDeleteOn = false;
			isDragOn = true;
		}
		else if (buttonName.contentEquals("Drag"))// mouse click function changes from drag to add
		{ 
			pressed.setLabel("Add");
			isDragOn = false;
			isAddOn = true;
		}
		else
		{
			String name = "Playback: ";
			int mode = Integer.parseInt(buttonName.substring(buttonName.length() - 1));
			switch (mode)
			{
			case 1:
				mode++;
				window.speed = 7;
				name += "2";
				break;
			case 2:
				mode++;
				window.speed = 15;
				name += "3";
				break;
			default:
				mode = 1;
				window.speed = 0;
				name += "1";
			}
			pressed.setLabel(name);
		}
		
		
	}
	
}
