
import java.awt.event.KeyEvent;

//Class not for sure,
//may just make heroMovement be in the hero class
//and levelSwitch in the main class.

public class KeyListener implements java.awt.event.KeyListener{
	private DiggerMain main;
	private String saves[]={"Level1","Level2","Level3"};
	private int current_level=-1;
	
	public void initMain(DiggerMain main){
		this.main=main;
	}
	
	
	public void levelSwitch(KeyEvent e){
		int keypressed=e.getKeyCode();
		switch (keypressed){
			case KeyEvent.VK_U: {
				if(current_level<saves.length){
					main.loadLevel(saves[current_level+1]);
					current_level++;
					break;
				}
			}
			case KeyEvent.VK_D: {
				if(current_level>0){
					main.loadLevel(saves[current_level-1]);
					current_level--;
					break;
				}
			}
			default: break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		levelSwitch(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
