import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * This is the main class for the Digger game. This is where the board is created.
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */

//Please let this work
public class DiggerMain extends JFrame {
	protected Level currentLevel;
	protected int currentLevelNumber=-1;
	private Integer currentScore = 0;
	private JLabel score;
	protected Integer currentLifes = 3;
	private JLabel lifes;
	private JPanel mainscreen;
	protected transient Update u;
	protected transient Thread t;
	
	
/**
 * 	
 * This method contructs the Digger game board itself
 *
 */
	public DiggerMain() {
		Container window = getContentPane();
	    window.setLayout(new BorderLayout());
	    
//	    boolean toggle = false;
	    //Below is just Testing to see what levels will currently look like
	    //Once we get some pre-made levels flushed out this can just load the 
	    //first one or display a menu screen of some sort.
//	    if (toggle){
	    	Font myfont = new Font("arial", Font.BOLD, 36);
	    	
	    	score = new JLabel("Score: "+currentScore.toString());
		    score.setFont(myfont);
		    window.add(score, BorderLayout.NORTH);
		    
		    lifes = new JLabel("Lifes: "+currentLifes.toString());
		    lifes.setFont(myfont);
		    window.add(lifes, BorderLayout.SOUTH);
		    
		    currentLevel = new Level(this);
		    currentLevel.initEntities();
		    mainscreen = currentLevel;
		    mainscreen.setFocusable(true);
		    window.add(mainscreen,BorderLayout.CENTER);
		    u = new Update(currentLevel);
		    t = new Thread(u);
//	    }
//	    else{
//	    	new LevelBuilder(this);
//		    mainscreen = new LevelBuilder(this);
//		    window.add(mainscreen,BorderLayout.CENTER);
//		    mainscreen.requestFocusInWindow();
//	    }
	    
	}
/**
 * 	
 * This method handles the scoring of the game. It resets the label on the DiggerMain if the hero scores points
 *
 * @param score
 */
	public void addScore(Integer score){
		this.score.setText("Score: "+currentScore.toString()+" ");
		this.currentScore += score;
		this.score.setText("Score: "+currentScore.toString());
	}
/**
 * 	
 * This method handles the resetting the lives label on the DiggerMain
 *
 */
	public void loseLife(){
		this.currentLifes--;
		this.lifes.setText("Lifes: "+currentLifes.toString());
	}
	
	
	/*
	 * Placeholder for now, once the game window is written it will replace the Level in the game window with the load.
	 */
/**
 * 	
 *Loads a game level from a file
 *
 * @param FileName
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
		final Level loaded=load;
		Thread t2 = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				t.suspend();
				currentLevel.entities=loaded.entities;
				currentLevel.hero=loaded.hero;
				currentLevel.enemySpawn=loaded.enemySpawn;
				currentLevel.initEntities();
				currentLevel.initializeStartConditions();
				t.resume();
			}
		};
		t2.start();
		try {t2.join();} catch (InterruptedException exception) {}
		repaint();
		currentLevel.repaint();
	}
	
}
