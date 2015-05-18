import java.awt.Color;


public class Hero extends Entity {
	String facing = "right";
	private static final long DELAY = 300;
	private static final long SHOOTDELAY = 1000;
	private long sinceLast;
	private long sinceLastShoot;
	protected int[] dpos = {0,0};
	protected boolean shoot;

	public Hero(DiggerMain dm, int x_position, int y_position) {
		super(Color.blue, 0, dm, x_position, y_position, "Hero");
		sinceLast=DELAY;
		sinceLastShoot=SHOOTDELAY;
		shoot = false;
	}
	public void updateThis(long time) {//TODO add shoot bullet
		sinceLast+=time;
		sinceLastShoot+=time;
		if (sinceLast>DELAY){
			if(sinceLastShoot>SHOOTDELAY&&shoot){
				shoot();
				shoot=false;
				sinceLastShoot=0;
				sinceLast=0;
			}
			else{
				movement(dpos[0],dpos[1]);
				findFacing();
				dpos[0]=0;
				dpos[1]=0;
				sinceLast=0;
			}
		}
	}
	
	public void die(){
		dm.loseLife();
	}
	
	public void shoot(){
		if (shoot){
			int x=0;
			int y=0;
			switch (facing){
				case "right": {x = level.imageSize; break;}
				case "left": {x = -level.imageSize; break;}
				case "up": {y = -level.imageSize; break;}
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
	

}
