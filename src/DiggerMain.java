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
	protected KeyListener keyListener = new KeyListener();
	private Level currentLevel;
	private Integer currentScore = 0;
	private JLabel score;
	private Integer currentLifes = 3;
	private JLabel lifes;
	
	public static void main(String[] args) {
        DiggerMain mainFrame = new DiggerMain();
		mainFrame.setSize(550, 660);
		mainFrame.setTitle("Digger");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.requestFocusInWindow();
	}
	
	public DiggerMain() {
		keyListener.initMain(this);
		addKeyListener(keyListener);
		
		Container window = getContentPane();
	    window.setLayout(new BorderLayout());
	    Font myfont = new Font("arial", Font.BOLD, 36);
	    
	    score = new JLabel("Score: "+currentScore.toString());
	    score.setFont(myfont);
	    window.add(score, BorderLayout.NORTH);
	    
	    lifes = new JLabel("Lifes: "+currentLifes.toString());
	    lifes.setFont(myfont);
	    window.add(lifes, BorderLayout.SOUTH);
	    
	    //Below is just Testing to see what levels will currently look like
	    //Once we get some pre-made levels flushed out this can just load the 
	    //first one or display a menu screen of some sort.
	    currentLevel = new Level(this);
	    currentLevel.addHero(5,5);
	    for (int i=2;i<4;i++){
	    	for (int j=6;j<9;j++){
	    		currentLevel.addEmerald(i,j);
	    	}
	    }
	    window.add(currentLevel,BorderLayout.CENTER);
	}
	
	public void addScore(int score){
		this.currentScore += score;
		this.score.setText("Score: "+currentScore.toString());
	}
	
	public void loseLife(){
		this.currentLifes--;
		this.lifes.setText("Lifes: "+currentLifes.toString());
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
		currentLevel=load;
		repaint();
	}
}
