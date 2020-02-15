import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

public class Toolbar extends JPanel implements ActionListener, KeyListener
{
	private static final int DIMENSION = Window.dimension;
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
	private JPanel scalePanel;
	private JPanel scaleButtonPanel;
	private Label scaleLabel;
	private Button up;
	private Button down;
	private TextField scaleField;
	private Label algorithmLabel;
	private Choice algorithmChoice;
	
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
		setFocusable(true);
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
				oddsLabel.setBackground(Color.ORANGE);
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
		
		scaleLabel = new Label("Scale:");
		scaleLabel.setBackground(Color.ORANGE);
		add(scaleLabel);
		
		scalePanel = new JPanel();
		scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.X_AXIS));
		scalePanel.setPreferredSize(new Dimension(60, 25));
		
		scaleButtonPanel = new JPanel();
		scaleButtonPanel.setLayout(new BoxLayout(scaleButtonPanel, BoxLayout.Y_AXIS));
		scaleButtonPanel.setPreferredSize(new Dimension(20, 25));
		
		up = new Button("^");
		up.setPreferredSize(new Dimension(20, 12));
		up.setBackground(Color.ORANGE);
		up.addActionListener(this);
		down = new Button("\u02C5"); //U+02C5
		down.setPreferredSize(new Dimension(20, 12));
		down.setBackground(Color.ORANGE);
		down.addActionListener(this);
		scaleButtonPanel.add(up);
		scaleButtonPanel.add(down);
		scalePanel.add(scaleButtonPanel);
		scaleField = new TextField("32");
		scaleField.setEditable(false);
		scalePanel.add(scaleField);
		add(scalePanel);
		
		algorithmLabel = new Label("Algorithm:");
		algorithmLabel.setBackground(Color.ORANGE);
		add(algorithmLabel);
		
		algorithmChoice = new Choice();
		algorithmChoice.setBackground(Color.WHITE);
		algorithmChoice.addKeyListener(this);
		algorithmChoice.add("a*");
		algorithmChoice.add("alfalfa");
		algorithmChoice.add("JUST");
		algorithmChoice.add("AN");
		algorithmChoice.add("EXAMPLE");
		algorithmChoice.add(":)");
		add(algorithmChoice);
		
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
			up.setEnabled(false);
			down.setEnabled(false);
			window.editing = false;
		} 
		else if (buttonName.equals("Pause") || buttonName.equals("Reset")) // Stops runner or resets runner position
		{ 
			startPause.setLabel("Start");
			newMap.setEnabled(true);
			modObstacle.setEnabled(true);
			up.setEnabled(true);
			down.setEnabled(true);
			window.editing = true;
			if (buttonName.equals("Reset"))
				window.getRunner().reset();
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
			if (oddsNum <= 0)
				oddsNum = 10;
			else if (oddsNum > 200)
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
		else if (buttonName.equals("Drag"))// mouse click function changes from drag to add
		{ 
			pressed.setLabel("Add");
			isDragOn = false;
			isAddOn = true;
		}
		else if (e.getSource() == playback)
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
		else if (e.getSource() == up)
		{
			int val = Integer.parseInt(scaleField.getText());
			if (val < 256)
			{
				scaleField.setText(Integer.toString(val * 2));
				window.reGrid(1024/ (val * 2));
			}
		}
		else if (e.getSource() == down)
		{
			int val = Integer.parseInt(scaleField.getText());
			if (val > 4)
			{
				scaleField.setText(Integer.toString(val / 2));
				window.reGrid(1024 / (val / 2));
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
}
