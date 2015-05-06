
import java.awt.event.*;

//Class not for sure,
//may just make heroMovement be in the hero class
//and levelSwitch in the main class.

public class KeyListener implements java.awt.event.KeyListener{
	private DiggerMain main;
	private String saves[]={"Test"};
	private int current_level=0;
	
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

	@Override
	public void keyPressed(KeyEvent e) {
		levelSwitch(e);
		heroMovement(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
