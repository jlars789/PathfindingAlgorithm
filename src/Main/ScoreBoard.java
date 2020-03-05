package Main;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;

/*
 *  The stats for each algorithm will need to be added in the style of the column titles.
 */
public class ScoreBoard extends JPanel 
{
	private Label title;
	private JPanel areas;
	private Label names;
	private Label speed;
	private Label completion;
	private Label combination;
	
	public ScoreBoard()
	{
		setFocusable(true);
		setPreferredSize(new Dimension(500, 1015));
		setMaximumSize(this.getPreferredSize());
		setMinimumSize(this.getPreferredSize());
		setBackground(Color.DARK_GRAY);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		title = new Label("PERFORMANCE");
		title.setPreferredSize(new Dimension(480, 25));
		title.setBackground(Color.GRAY);
		title.setForeground(Color.ORANGE);
		title.setAlignment(title.CENTER);
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		add(title, gbc);
		
		names = new Label("Algorithm");
		names.setPreferredSize(new Dimension(120, 25));
		names.setBackground(Color.GRAY);
		names.setForeground(Color.ORANGE);
		names.setAlignment(title.CENTER);
		speed = new Label("Speed");
		speed.setPreferredSize(new Dimension(115, 25));
		speed.setBackground(Color.GRAY);
		speed.setForeground(Color.ORANGE);
		speed.setAlignment(title.CENTER);
		completion = new Label("Completion");
		completion.setPreferredSize(new Dimension(115, 25));
		completion.setBackground(Color.GRAY);
		completion.setForeground(Color.ORANGE);
		completion.setAlignment(title.CENTER);
		combination = new Label("Overall");
		combination.setPreferredSize(new Dimension(120, 25));
		combination.setBackground(Color.GRAY);
		combination.setForeground(Color.ORANGE);
		combination.setAlignment(title.CENTER);
		
		areas = new JPanel();
		areas.setBackground(Color.DARK_GRAY);
		areas.add(names);
		areas.add(speed);
		areas.add(completion);
		areas.add(combination);
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		add(areas, gbc);
	}
	
	
}
