import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


//Please let this work
public class DiggerMain extends JFrame {
	protected transient KeyListener keyListener = new KeyListener();
	protected Level currentLevel;
	private Integer currentScore = 0;
	private JLabel score;
	private Integer currentLifes = 3;
	private JLabel lifes;
	private JPanel mainscreen;
	
	public static void main(String[] args) {
        DiggerMain mainFrame = new DiggerMain();
		mainFrame.setSize(528, 636);
		mainFrame.setTitle("Digger");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		mainFrame.requestFocusInWindow();
//		Thread t = new Thread(mainFrame.currentLevel);
//		t.start();
	}
	
	public DiggerMain() {
		Container window = getContentPane();
	    window.setLayout(new BorderLayout());
	    Font myfont = new Font("arial", Font.BOLD, 36);
	    
	    
	    score = new JLabel("Score: "+currentScore.toString());
	    score.setFont(myfont);
	    window.add(score, BorderLayout.NORTH);
	    
	    lifes = new JLabel("Lifes: "+currentLifes.toString());
	    lifes.setFont(myfont);
	    window.add(lifes, BorderLayout.SOUTH);
	    
	    boolean toggle = true;
	    //Below is just Testing to see what levels will currently look like
	    //Once we get some pre-made levels flushed out this can just load the 
	    //first one or display a menu screen of some sort.
	    if (toggle){
		    currentLevel = new Level(this);
		    currentLevel.initEntities();
		    mainscreen=currentLevel;
		    mainscreen.setFocusable(true);
		    window.add(mainscreen,BorderLayout.CENTER);
	    }
	    else{
		    mainscreen = new LevelBuilder(this);
		    window.add(mainscreen,BorderLayout.CENTER);
		    mainscreen.requestFocusInWindow();
	    }
	}
	
	public void addScore(Integer score){
		this.score.setText("Score: "+currentScore.toString()+" ");
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
		currentLevel.initDm(this);
		currentLevel.initEntities();
		remove(mainscreen);
		mainscreen=currentLevel;
		add(mainscreen,BorderLayout.CENTER);
		mainscreen.setFocusable(true);
		currentLevel.setFocusable(true);
		revalidate();
		repaint();
		currentLevel.repaint();
	}
}
