import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;



/**
 * Displays a Jframe with buttons to select game or level editor
 * If theres time there will be a controls and options menu
 *
 * @author hannaam.
 *         Created May 21, 2015.
 */
public class StartMenu extends JFrame{
	
	public static void main(String[] args) throws Exception {
        new StartMenu();
	}
	
	
	public StartMenu() {
		initialize();
		setSize(600, 700);
		setTitle("StartScreen");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Initializes buttons.
	 *
	 */
	private void initialize(){
		setLayout(new GridLayout(1,2,1,1));
		menuButton[] buttons = controls(this);
		for (int i=0;i<buttons.length;i++){
			add(buttons[i]);
			if (i==0) buttons[i].setText("Play Digger(LoTR Themed)");
			else buttons[i].setText("Open Level Editor");
			buttons[i].addActionListener(buttons[i]); 
		}
	}
	/**
	 * Makes a couple buttons to select what to do.
	 *
	 */
	private class menuButton extends JButton implements ActionListener{
		public void actionPerformed(ActionEvent event){}
	}
	private menuButton[] controls(StartMenu SM){
		menuButton buttons[] = new menuButton[2];
		buttons[0] = new menuButton(){
            public void actionPerformed(ActionEvent event) {
            	SM.setVisible(false);
            	DiggerMain mainFrame = new DiggerMain();
        		mainFrame.setSize(528, 636);
        		mainFrame.setTitle("Digger");
        		mainFrame.setVisible(true);
        		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		if (mainFrame.t!=null) mainFrame.t.start();
            }
		};
		buttons[1] = new menuButton(){
            public void actionPerformed(ActionEvent event) {
            	SM.setVisible(false);
            	DiggerMain mainFrame = new DiggerMain();
            	new LevelBuilder(mainFrame);
            }
		};
		return buttons;
	}
	
}
