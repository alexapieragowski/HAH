import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
			repaint();
		}
		private class menuButton extends JButton implements ActionListener{
			public void actionPerformed(ActionEvent event){}
		}
		private menuButton[] controls(){
			menuButton buttons[] = new menuButton[controls];
			buttons[0] = new menuButton(){//reset button
	            public void actionPerformed(ActionEvent event) {
	                  gamePanel.reset();
	                  repaint();
	            }
			};
			buttons[1] = new menuButton(){//Opens a window to save the game state.
	            public void actionPerformed(ActionEvent event) {
	            	new File("Saves").mkdirs();
	            	String path = Paths.get("Saves").toAbsolutePath().toString();
	            	JFileChooser choose = new JFileChooser(path);
	            	disablestuff(choose);
	            	int choice =choose.showSaveDialog(this);
	            	if (choice == JFileChooser.APPROVE_OPTION){
	            		try{
	            			FileOutputStream fileout = new FileOutputStream("Saves/"+choose.getSelectedFile().getName());
	            			ObjectOutputStream out = new ObjectOutputStream(fileout);
	            			out.writeObject(this);//TODO Make this save the Level created.
	            			//To do so level must have a constructor that takes a list of entities.
	            			out.close();
	            			fileout.close();
	            		}catch(IOException e){throw new IllegalStateException(e);}
	            	}
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
		/*
		 * Disables directory navigation for save/load windows,
		 * because the user cannot be trusted with that grand power.
		 */
		private void disablestuff(Container container){
			Component c[] = container.getComponents();
			for(int i=0;i<c.length;i++){
				if (c[i] instanceof JComboBox) ((JComboBox<?>)c[i]).setEnabled(false);//Disables drop down menus.
				else if (c[i] instanceof JButton){
					String text = ((JButton)c[i]).getText();
					if (text==null||text.isEmpty()) ((JButton)c[i]).setEnabled(false);//Disables directory related buttons
				}else if (c[i] instanceof Container) disablestuff((Container)c[i]);
			}
		}
	}
	
	
	
	
	
	public class LevelPanel extends JPanel implements ActionListener{
		private int gameSize = 16;
		private JButton button[][] = new JButton[gameSize][gameSize];
		private Entity entities[][]= new Entity[gameSize][gameSize];
		protected String entityToBePlaced;
		
		
		
		public LevelPanel () {
			entityToBePlaced="";
			setLayout(new GridLayout(gameSize, gameSize, 1, 1));
			setBackground(Color.black);
			repaint();
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
					entities[j][i]=new Entity(Color.gray,0,dm,j,i);
				}
			}
		}
		public void reset(){
			for (int i=0;i<gameSize;i++) {
				for (int j=0;j<gameSize;j++){
					button[j][i].setBackground(Color.gray);
					entities[j][i]=new Entity(Color.gray,0,dm,j,i);
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
					entities[x][y]=new Entity(Color.gray,0,dm, x,y);
					break;
				}
				case "Hero":{
					button[x][y].setBackground(Color.blue);
					entities[x][y]=new Entity(Color.blue,0,dm,x,y);//TODO Make Hero
					break;
				}
				case "Emerald":{
					button[x][y].setBackground(Color.green);
					entities[x][y]=new Entity(Color.green,100,dm,x,y);
					break;
				}
				case "Nothing":{
					button[x][y].setBackground(Color.black);
					entities[x][y]=new Entity(Color.black,0,dm,x,y);
					break;
				}
				case "Hobbin":{
					button[x][y].setBackground(Color.red);
					entities[x][y]=new Entity(Color.red,50,dm,x,y);//TODO Make Hobbin
					break;
				}
				case "Nobbin":{
					button[x][y].setBackground(Color.orange);
					entities[x][y]=new Entity(Color.orange,75,dm,x,y);//TODO Make Nobbin
					break;
				}
				case "Gold":{
					button[x][y].setBackground(Color.yellow);
					entities[x][y]=new Entity(Color.yellow,100,dm,x,y);//TODO Make Gold
					break;
				}
				default: break;
			}
			
		}
	}
}

