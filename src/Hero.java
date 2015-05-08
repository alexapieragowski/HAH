import java.awt.Color;
import java.awt.event.KeyEvent;


public class Hero extends Entity implements java.awt.event.KeyListener {

	public Hero(DiggerMain dm, int x_position, int y_position) {
		super(Color.blue, 0, dm, x_position, y_position);
		addKeyListener(this);
	}
	
	public int[] position(){
		return this.position();
	}
	
	public void heroMovement(KeyEvent e){
		int delta_pos[] = {0,0};
		int keypressed=e.getKeyCode();
		switch (keypressed){
			case KeyEvent.VK_LEFT: {delta_pos[0]=-1; break;}
			case KeyEvent.VK_RIGHT: {delta_pos[0]=1; break;}
			case KeyEvent.VK_UP: {delta_pos[1]=-1; break;}
			case KeyEvent.VK_DOWN: {delta_pos[1]=1; break;}
			default: break;
		}
		super.movement(delta_pos[0], delta_pos[1]);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		heroMovement(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub.
		
	}

}
