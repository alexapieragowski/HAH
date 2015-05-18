import java.awt.Color;


public class Hero extends Entity {
	String facing = "right";
	private static final long DELAY = 300;
	private long sinceLast;
	protected int[] dpos = {0,0};
	protected boolean shoot;

	public Hero(DiggerMain dm, int x_position, int y_position) {
		super(Color.blue, 0, dm, x_position, y_position, "Hero");
		sinceLast=DELAY;
		shoot = false;
//		addKeyListener(this);
	}
	public void updateThis(long time) {//TODO add shoot bullet
		sinceLast+=time;
		if (sinceLast>DELAY){
			shoot();
			movement(dpos[0],dpos[1]);
			findFacing();
			dpos[0]=0;
			dpos[1]=0;
			shoot=false;
			sinceLast=0;
		}
	}
	
	//Why is this here when we could just use hero.position?
	public int[] position(){
		return this.position();
	}
	
//	public void movement(int dx, int dy) {
//		Entity next;
//		if (position[0]+dx<0||position[0]+dx>level.gameSize||position[1]+dy<0||position[1]+dy>level.gameSize) next=null;
//		else next = level.entities[position[0]+dx][position[1]+dy];
//		if (next!=null){
//			if (killPriority>next.killPriority){
//				next.die();
//				level.entities[position[0]+dx][position[1]+dy]=this;
//				level.entities[position[0]][position[1]]= new Entity(Color.black,0,dm,position[0],position[0],"Empty");
//				position[0]+=dx;
//				position[1]+=dy;
//			}
//			else die();
//		}
//	}
	
//	public void heroMovement(KeyEvent e){
//		int delta_pos[] = {0,0};
//		int keypressed=e.getKeyCode();
//		switch (keypressed){
//			case KeyEvent.VK_LEFT: {delta_pos[0]=-1; facing=1; break;}
//			case KeyEvent.VK_RIGHT: {delta_pos[0]=1; facing=2; break;}
//			case KeyEvent.VK_UP: {delta_pos[1]=-1; facing=3; break;}
//			case KeyEvent.VK_DOWN: {delta_pos[1]=1; facing=4; break;}
//			default: break;
//		}
//		super.movement(delta_pos[0], delta_pos[1]);
//	}
	
	public void die(){
		dm.loseLife();
	}
	
	public void shoot(){
		if (shoot){
			int x=0;
			int y=0;
			switch (facing){
			case "right": {x = level.imageSize; break;
			}
			case "left": {x = -level.imageSize; break;
			}
			case "up": {y = -level.imageSize; break;
			}
			case "down": {y = level.imageSize; break;}
			}
			Weapon weapon = new Weapon(dm, x+position[0], y+position[1],facing);
			level.entities[(x+position[0])/level.imageSize][(y+position[1])/level.imageSize] = weapon;
		}
	}

	public void findFacing(){
		if (dpos[0] == level.imageSize && dpos[1] == 0) {facing = "right";}
		if (dpos[0] == -level.imageSize && dpos[1] == 0) {facing = "left";}
		if (dpos[0] == 0 && dpos[1] == -level.imageSize) {facing = "up";}
		if (dpos[0] == 0 && dpos[1] == level.imageSize) {facing = "down";}
	}
	

//	@Override
//	public void keyPressed(KeyEvent e) {
//		heroMovement(e);
//		dm.keyListener.keyPressed(e);
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub.
//		
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub.
//		
//	}

}
