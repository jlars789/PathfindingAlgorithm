package Main;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Choice;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Main.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicComboBoxUI;



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

public class Toolbar extends JPanel implements ActionListener, KeyListener, MouseListener
{
	private static final long serialVersionUID = 8589842146747058791L;
	private static final int DIMENSION = Window.dimension;
	private static final int WIDTH = 1024, HEIGHT = 2 * DIMENSION; //Dimensions for Window
	private Window window;
	private Main main;
	
	private Button startPause; 
	private Button reset;
	private Button newMap;
	private Button modObstacle;
	private Button[] buttons = {startPause, reset, newMap, modObstacle};
	private String[] initialButtonNames = {"Start", "Reset", "New Map", "Delete"};
	private Label oddsLabel;
	private TextField oddsField;
	private JPanel scalePanel;
	private JPanel scaleButtonPanel;
	private Label scaleLabel;
	private Button up;
	private Button down;
	private TextField scaleField;
	private Label algorithmLabel;
	private JComboBox<String> algorithmChoice;
	private final String[] algorithms = {"a*", "alfalfa", "arte", "bk", "deeber", "Glem" };
	private Button scoreboard;
	private Button simulator;
	
	protected static boolean isAddOn = false;
	protected static boolean isDeleteOn = true;
	
	
	public Toolbar()
	{
		System.exit(0);
	}
	
	public Toolbar(Window window, Main main)
	{
		super(new FlowLayout());
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT -20));
		setMaximumSize(getPreferredSize());
		//setMinimumSize(new Dimension(WIDTH, HEIGHT -10));
		setBackground(Color.DARK_GRAY);
		
		this.window = window;
		this.main = main;
		
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
		
		algorithmChoice = new JComboBox<String>(algorithms);//Choice();
		algorithmChoice.setBackground(Color.WHITE);
		//addPopupMouseListener(algorithmChoice);
		algorithmChoice.addKeyListener(this);
		add(algorithmChoice);
		
		scoreboard = new Button("Score Board");
		scoreboard.addActionListener(this);
		scoreboard.setBackground(Color.ORANGE);
		add(scoreboard);
		
		simulator = new Button("Simulator");
		simulator.addActionListener(this);
		simulator.setBackground(Color.ORANGE);
		add(simulator);
		
		
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
				Window.getRunner().reset();
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
			Window.getRunner().reset();
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
			pressed.setLabel("Add");
			isDeleteOn = false;
			isAddOn = true;
		}
		else if (pressed == up)
		{
			int val = Integer.parseInt(scaleField.getText());
			if (val < 256)
			{
				scaleField.setText(Integer.toString(val * 2));
				window.reGrid(1024/ (val * 2));
			}
		}
		else if (pressed == down)
		{
			int val = Integer.parseInt(scaleField.getText());
			if (val > 4)
			{
				scaleField.setText(Integer.toString(val / 2));
				window.reGrid(1024 / (val / 2));
			}
		}
		else if (pressed == scoreboard)
		{
			if (main.hasSB())
				main.hideSB();
			else
				main.showSB();				
		}
		else if (pressed == simulator)
		{
			
			if (!window.isSimRunning())
				window.startSim();
			else
				window.endSim();
		}
	}
	/*private void addPopupMouseListener(JComboBox box)
	{
		try
		{
			Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
			popupInBasicComboBoxUI.setAccessible(true);
			BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI());
			
			popup.addMouseListener(this);
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	*/
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
		//algorithmChoice.transferFocus(); // This is the one
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (algorithmChoice.hasFocus())
			algorithmChoice.transferFocus();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
