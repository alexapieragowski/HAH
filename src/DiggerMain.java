import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;


//Please let this work
public class DiggerMain extends JFrame {
	private Level current_level;
	private Integer currentScore = 0;
	
	public DiggerMain() {
		Container window = getContentPane();
	    window.setLayout(new BorderLayout());
	    JLabel score = new JLabel(currentScore.toString());
	    Font myfont = new Font("arial", Font.BOLD, 36);
	    score.setFont(myfont);
	    window.add(score, BorderLayout.NORTH);
	}
	

	
	public static void main(String[] args) {
        DiggerMain mainFrame = new DiggerMain();
		mainFrame.setSize(600, 700);
		mainFrame.setTitle("Digger");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addScore(int score){
		this.currentScore += score;
		repaint();
	}
	
	
	/*
	 * Placeholder for now, once the game window is written it will replace the Level in the game window with the load.
	 */
	public void loadLevel(String FileName){
		Level load=null;
		try{
			FileInputStream filein = new FileInputStream("Saves/"+FileName);
			ObjectInputStream in = new ObjectInputStream(filein);
			load=(Level) in.readObject();
			in.close();
			filein.close();
		}catch(IOException e){
			System.out.println("The class Level has been changed since this was saved, please update saves.");
			throw new IllegalStateException(e);
		}catch(ClassNotFoundException e){
			System.out.println("File not found.");
			throw new IllegalStateException(e);
		}
		current_level=load;
		repaint();
	}
}
