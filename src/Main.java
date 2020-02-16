import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
		panel.add(tools);
		panel.add(gamepanel);
		
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Testing Environment");
		frame.setPreferredSize(new Dimension(800, 800));
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args) 
	{
		new Main();
	}

}