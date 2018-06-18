package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Control.Game;
import View.*;

public class Driver implements Runnable {

	private GUI _gui;
	private Game _game;
	private JFrame _frame;
	
	/*
	 * Constructor that takes in an existing Game object to initialize _game
	 */
	public Driver() {
		_game = new Game(this, 2);
	}
		
	/*
	 * The main method initializes a new Game object, then prepares a new instance of this Driver,
	 * with the newly created Game object, to invoke on running (see the run() method below).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Driver());
	}
	
	/*
	 * Sets up the GUI aspects of the game in a separate thread--this is how I was taught to
	 * do it in 115. I believe the idea is that the GUI can be set up more or less instantly
	 * on running, instead of having to wait for other code to run.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		_gui = new GUI(this);
		_frame = _gui.getFrame();
		_frame.pack();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
	}
	
	/*
	 * This is what we'll call when we need to update the GUI.
	 */
	public void updateJFrame() {
		_frame.pack();
		_frame.repaint();
	}
	
	/*
	 * Getter method for the game method used by this driver; the GUI code will
	 * need to reference this.
	 */
	public Game getGame() {
		return _game;
	}
	
	public GUI getGUI() {
		return _gui;
	}
}