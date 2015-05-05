
import java.awt.event.*;



public class KeyListener {
	private DiggerMain main;
	private String saves[];
	private int current_level;
	
	public void initMain(DiggerMain main){
		this.main=main;
	}
	
	public int[] heroMovement(KeyEvent e){
		int delta_pos[] = {0,0};
		int keypressed=e.getKeyCode();
		switch (keypressed){
			case KeyEvent.VK_LEFT: {delta_pos[0]=-1; break;}
			case KeyEvent.VK_RIGHT: {delta_pos[0]=1; break;}
			case KeyEvent.VK_UP: {delta_pos[1]=1; break;}
			case KeyEvent.VK_DOWN: {delta_pos[1]=-1; break;}
			default: break;
		}
		return delta_pos;
	}
	public void levelSwitch(KeyEvent e){
		int keypressed=e.getKeyCode();
		switch (keypressed){
			case KeyEvent.VK_U: {main.loadLevel(saves[current_level+1]); break;}
			case KeyEvent.VK_D: {main.loadLevel(saves[current_level+1]); break;}
			default: break;
		}
	}
	
}
