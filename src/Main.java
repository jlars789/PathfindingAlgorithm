import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.Toolbar;
import Main.Window;

import java.awt.Dimension;
import java.awt.FlowLayout;


public class Main 
{	
	public Main() 
	{
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		
		Window gamepanel = new Window();
		Toolbar tools = new Toolbar(gamepanel);
		
		System.setProperty("-Dsun.java2d.opengl", "True");
		
		panel.add(gamepanel);
		panel.add(tools);
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Testing Environment");
		frame.setPreferredSize(new Dimension(1050, 1050));
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args) 
	{
		new Main();
	}

}
