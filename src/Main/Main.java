package Main;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import Main.Toolbar;
import Main.Window;
import Main.ScoreBoard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;


public class Main 
{	
	private JFrame frame;
	private JPanel panel, superPanel;
	private Window gamepanel;
	private Toolbar tools;
	private ScoreBoard sb;
	private Component rigid;
	
	public Main() 
	{
		System.setProperty("-Dsun.java2d.opengl", "True");
		
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.GRAY);
		panel.setMaximumSize(new Dimension(Window.WIDTH, 1050));
		superPanel = new JPanel();
		superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.X_AXIS));
		superPanel.setBackground(Color.GRAY);
		
		gamepanel = new Window();
		tools = new Toolbar(gamepanel, this);
		sb = new ScoreBoard();
		
		//panel.add(Box.createRigidArea(new Dimension(0,25)));
		panel.add(gamepanel);
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(tools);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		
	
		superPanel.add(Box.createRigidArea(new Dimension(5,1050)));
		superPanel.add(panel);
		superPanel.add(Box.createRigidArea(new Dimension(5,1050)));
		rigid = Box.createRigidArea(new Dimension(5,1050));
		
		frame.setMaximumSize(new Dimension(1035, 1050));
		
		frame.add(superPanel);
		frame.pack();
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Testing Environment");
		
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args) 
	{
		new Main();
	}
	
	public void hideSB()
	{
		superPanel.remove(rigid);
		superPanel.remove(sb);
		superPanel.validate();
	
		frame.setSize(new Dimension(1035, 1050));
		frame.validate();
	}
	public void showSB()
	{
		superPanel.add(sb);
		superPanel.add(rigid);
		superPanel.validate();
 
		frame.setSize(new Dimension(1540, 1050));
		frame.validate();
	}
	public boolean hasSB()
	{
		return sb.getParent() != null;
	}
	
	

}
