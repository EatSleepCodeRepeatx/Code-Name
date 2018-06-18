package Control;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Egg {

	private JFrame frame;
	private JLabel label;

	public void displayImage() 
	{
		String filePath = "src/code/egg.jpeg";	
		frame = new JFrame("Be Positive");
		frame.setVisible(true);
		ImageIcon icone = new ImageIcon(filePath);
		label = new JLabel(icone,JLabel.CENTER);
		frame.add(label);
		frame.pack();
		
	}

	
}

	


