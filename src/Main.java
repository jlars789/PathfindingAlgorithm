import javax.swing.JFrame;

public class Main {

	public Main() {
		JFrame frame = new JFrame();
		Window gamepanel = new Window();
		
		System.setProperty("-Dsun.java2d.opengl", "True");
		
		frame.add(gamepanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Testing Environment");
		
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new Main();
	}

}
