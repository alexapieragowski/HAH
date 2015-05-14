import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Weapon extends Entity implements java.awt.event.KeyListener, Runnable {
	private Hero hero;
	private static final int DELAY = 1000;
	KeyEvent e;

	public Weapon(DiggerMain dm, int x_position, int y_position) {
		super(Color.white, 0, dm, x_position, y_position,"bullet");
		addKeyListener(this);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 15, 15);
	}
	
	public void weaponMovement(KeyEvent e){
		int spaceOrNothing = e.getKeyCode();
		switch (spaceOrNothing){
		    case KeyEvent.VK_SPACE: {
			    if (hero.facing == 0 || hero.facing == 1){
			        super.movement(-1, 0);
		        }
			    if (hero.facing == 2){
				    super.movement(1, 0);
			    }
			    if (hero.facing == 3){
				    super.movement(0, -1);
			    }
			    if (hero.facing == 4){
				    super.movement(0, 1);
			    }
		    }
		}
		
	}

	@Override
	public void run() {
		try{
			weaponMovement(e);
			Thread.sleep(DELAY);
		}catch (InterruptedException exception){}
	}	

	@Override
	public void keyPressed(KeyEvent e) {
		weaponMovement(e);
		dm.keyListener.keyPressed(e);
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
