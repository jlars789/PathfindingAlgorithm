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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;



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
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.GRAY);
		panel.setMaximumSize(new Dimension(Window.WIDTH, 1050));
		superPanel = new JPanel();
		superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.X_AXIS));
		superPanel.setBackground(Color.GRAY);
		
		gamepanel = new Window();
		tools = new Toolbar(gamepanel, this);
		sb = new ScoreBoard();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		panel.add(gamepanel, gbc);
	
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		
		panel.add(tools, gbc);
		
	
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
