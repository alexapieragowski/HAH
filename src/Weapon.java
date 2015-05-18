import java.awt.Color;


public class Weapon extends Entity {
	private Hero hero;
	private static final int DELAY = 150;
	private long sinceLast;
	private int[] dpos = {0,0};
	private String facing;
//	KeyEvent e;

	public Weapon(DiggerMain dm, int x_position, int y_position, String facing) {
		super(Color.white, 0, dm, x_position, y_position,"Weapon");
		this.facing=facing;
//		addKeyListener(this);
	}
	
//	@Override
//	public void paint(Graphics g){
//		super.paint(g);
//		g.setColor(Color.white);
//		g.fillRect(0, 0, 15, 15);
//	}

	public void updateThis(long time) {
		sinceLast+=time;
		if (sinceLast>DELAY){
			//howToMove();
			move();
			dpos[0]=0;
			dpos[1]=0;
			sinceLast=0;
		}
	}
	
	public void move(){
		if (facing == "right") { this.movement(level.imageSize, 0);}
		if (facing == "left") { this.movement(-level.imageSize, 0);}
		if (facing == "up") { this.movement(0, -level.imageSize);}
		if (facing == "down") { this.movement(0, level.imageSize);}
	}
	
	

	
}