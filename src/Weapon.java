import java.awt.Color;
import java.awt.Graphics;


public class Weapon extends Entity implements Runnable {
	private Hero hero;
	private static final int DELAY = 1000;
//	KeyEvent e;

	public Weapon(DiggerMain dm, int x_position, int y_position) {
		super(Color.white, 0, dm, x_position, y_position,"Weapon");
//		addKeyListener(this);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 15, 15);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub.
		
	}
	
}