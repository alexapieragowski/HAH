import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;



/**
 * This class makes a JPanel window for making custom levels
 * It contains two inner classes for the Menu panel and Level Panel
 */
public class LevelBuilder extends JPanel{
	private DiggerMain dm;
	private int buttonsize=32;
	
	public LevelBuilder(DiggerMain dm) {
		this.dm=dm;
		setLayout(new FlowLayout());
		MenuButtonPanel menuPanel = new MenuButtonPanel();
		add(menuPanel);
		add(menuPanel.gamePanel);
	}
	
	
	
	
	
	
	
	public class MenuButtonPanel extends JPanel{
		public LevelPanel gamePanel;
		private int controls=9;
		private menuButton buttons[];
		
		public MenuButtonPanel() {
			gamePanel = new LevelPanel();
			buttons=controls();
			initialize();
		}
		private void initialize(){
			setLayout(new GridLayout(1,8,1,1));
			buttons = controls();
			Color buttonColor[]={Color.gray,Color.blue,Color.green,Color.black,Color.red,Color.orange,Color.yellow};
			for (int i=0;i<controls;i++){
				add(buttons[i]);
				if (i==0) buttons[i].setText("reset");
				else if (i==1) buttons[i].setText("save");
				else buttons[i].setBackground(buttonColor[i-2]);
				buttons[i].addActionListener(buttons[i]); 
			}
		}
		private class menuButton extends JButton implements ActionListener{
			public void actionPerformed(ActionEvent event){}
		}
		private menuButton[] controls(){
			menuButton buttons[] = new menuButton[controls];
			buttons[0] = new menuButton(){//reset button
	            public void actionPerformed(ActionEvent event) {
	                  gamePanel.reset();
	            }
			};
			buttons[1] = new menuButton(){//Opens a window to save the game state.
	            public void actionPerformed(ActionEvent event) {
	            	Level level = new Level(dm,gamePanel.entities,gamePanel.hero);
	            	level.saveLevel();
	            }
			};
			buttons[2] = new menuButton(){//dirt
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Dirt";
	            }
			};
			buttons[3] = new menuButton(){//hero
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Hero";
	            }
			};
			buttons[4] = new menuButton(){//emerald
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Emerald";
	            }
			};
			buttons[5] = new menuButton(){//remove
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Nothing";
	            }
			};
			buttons[6] = new menuButton(){//Hobbin
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Hobbin";
	            }
			};
			buttons[7] = new menuButton(){//Nobbin
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Nobbin";
	            }
			};
			buttons[8] = new menuButton(){//Gold
	            public void actionPerformed(ActionEvent event) {
	            	gamePanel.entityToBePlaced="Gold";
	            }
			};
			return buttons;
		}
	}
	
	
	
	
	
	public class LevelPanel extends JPanel implements ActionListener{
		private int gameSize = 16;
		private int imageSize = 32;
		private JButton button[][] = new JButton[gameSize][gameSize];
		protected Entity entities[][]= new Entity[gameSize][gameSize];
		protected String entityToBePlaced;
		protected Entity hero;
		
		
		
		public LevelPanel () {
			entityToBePlaced="";
			setLayout(new GridLayout(gameSize, gameSize, 1, 1));
			setBackground(Color.black);
			initialize();
		}
		private void initialize(){
			for (int i=0;i<gameSize;i++) {
				for (int j=0;j<gameSize;j++){
					button[j][i] = new JButton();
					button[j][i].setPreferredSize(new Dimension(buttonsize,buttonsize));
					add(button[j][i]);
					button[j][i].setBackground(Color.gray);
					button[j][i].addActionListener(this);
					entities[j][i]=new Entity(Color.gray,0,dm,j*imageSize,i*imageSize,"Dirt");
				}
			}
		}
		public void reset(){
			for (int i=0;i<gameSize;i++) {
				for (int j=0;j<gameSize;j++){
					button[j][i].setBackground(Color.gray);
					entities[j][i]=new Entity(Color.gray,0,dm,j*imageSize,i*imageSize,"Dirt");
				}
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			addEntity(button.getX()/buttonsize,button.getY()/buttonsize);
		}
		public void addEntity(int x, int y){
			switch (entityToBePlaced){
				case "Dirt":{
					button[x][y].setBackground(Color.gray);
					entities[x][y]=new Entity(Color.gray,0,dm,x*imageSize,y*imageSize,"Dirt");
					break;
				}
				case "Hero":{
					button[x][y].setBackground(Color.blue);
					entities[x][y]=new Hero(dm,x*imageSize,y*imageSize);
					hero=entities[x][y];
					break;
				}
				case "Emerald":{
					button[x][y].setBackground(Color.green);
					entities[x][y]=new Entity(Color.green,100,dm,x*imageSize,y*imageSize,"Dirt");
					break;
				}
				case "Nothing":{
					button[x][y].setBackground(Color.black);
					entities[x][y]=new Entity(Color.black,0,dm,x*imageSize,y*imageSize,"Empty");
					break;
				}
				case "Hobbin":{
					button[x][y].setBackground(Color.red);
					entities[x][y]=new Hobbin(dm,x*imageSize,y*imageSize);
					break;
				}
				case "Nobbin":{
					button[x][y].setBackground(Color.orange);
					entities[x][y]=new Entity(Color.orange,75,dm,x*imageSize,y*imageSize,"Nobbin");//TODO Make Nobbin
					break;
				}
				case "Gold":{
					button[x][y].setBackground(Color.yellow);
					entities[x][y]=new Entity(Color.yellow,100,dm,x*imageSize,y*imageSize,"Gold");//TODO Make Gold
					break;
				}
				default: break;
			}
			
		}
	}
}

