package Control;

import javax.swing.*;
public class IMGFinalStage
{

	private JFrame frame;
	private JLabel label;
	public void displayImage() 
	{
		String filePath = "src/code/eggs.jpeg";
		printImg(filePath);		
	}
	
	private void printImg(String filePath) 
	{
		frame = new JFrame("Winner Winner Chicken Dinner");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icone = new ImageIcon(filePath);
		label = new JLabel(icone,JLabel.CENTER);
		frame.add(label);
		frame.pack();
	}
}
