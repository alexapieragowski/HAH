import java.awt.Color;

/**
 * 
 * This class creates the hero of the game
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class Hero extends Entity {
	String facing = "right";
	private static final long DELAY = 300;
	private static final long SHOOTDELAY = 2250;
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
	/**
	 * this method handles updating the hero's movement and their ability to shoot the weapon
	 * @param time long time since last update
	 */
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
				pushGold(); 
//				movement(dpos[0],dpos[1]);
				howToMoveAndNotDieToGold();
				findFacing();
				dpos[0]=0;
				dpos[1]=0;
				sinceLast=0;
			}
		}
	}
	/**
	 * 
	 *Allows the hero to not die when it runs into gold
	 *
	 */
	private void howToMoveAndNotDieToGold() {
		if(0<=position[0]+dpos[0] && position[0]+dpos[0]<level.gameSize*level.imageSize && 0<=position[1]+dpos[1] && position[1]+dpos[1]<level.gameSize*level.imageSize){
			Entity e1=level.entities[(position[0]+dpos[0])/level.imageSize][(position[1]+dpos[1])/level.imageSize];
			if (!e1.spriteName.contains("Gold")||e1.spriteName.startsWith("BrokenGold")) movement(dpos[0],dpos[1]);
		}
	}
	/**
	 * 
	 * allows hero to push the gold
	 *
	 */
	private void pushGold() {
		if(0<=position[0]+dpos[0] && position[0]+dpos[0]<level.gameSize*level.imageSize){
			Entity e1=level.entities[(position[0]+dpos[0])/level.imageSize][position[1]/level.imageSize];
			if (e1.spriteName.startsWith("Gold")){
				e1.movement(dpos[0],0);
			}
		}
	}
	/**
	 * updates the amount of size(subtracts 1) and checks if the game needs a hard or soft reset
	 * based on lives left. Soft reset means that there are remaining lives and the hero resets. Hard reset 
	 * means the game is over
	 */
	public void die(){
		dm.loseLife();
		if (dm.currentLifes<=0){
			level.hardReset();
			return;
		}
		else {
			level.resetAfterDie();
			return;
		}
	}
	/**
	 * 
	 * This method checks the direction the hero is facing and allows them to fire their weapon. 
	 * It creates a new weapon that moves in the correct direction
	 *
	 */
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
			if ((0<=position[0]+x && position[0]+x<level.gameSize*level.imageSize && 0<=position[1]+y && position[1]+y<level.gameSize*level.imageSize)){
				String nextSprite = level.entities[(position[0]+x)/level.imageSize][(position[1]+y)/level.imageSize].spriteName;
				if (!nextSprite.contains("Gold") && !nextSprite.equals("Emerald") && !nextSprite.equals("Dirt")){
					Weapon weapon = new Weapon(dm, x+position[0], y+position[1],facing);
					level.entities[(x+position[0])/level.imageSize][(y+position[1])/level.imageSize].die();
					level.entities[(x+position[0])/level.imageSize][(y+position[1])/level.imageSize] = weapon;
				}
			}
				
		}
	}
	/**
	 * 
	 * Determines the direction the hero is facing by checking its last dpos x and dpos y. The
	 * default direction is to the right
	 *
	 */
	public void findFacing(){
		if (dpos[0] == level.imageSize && dpos[1] == 0) {facing = "right";}
		if (dpos[0] == -level.imageSize && dpos[1] == 0) {facing = "left";}
		if (dpos[0] == 0 && dpos[1] == -level.imageSize) {facing = "up";}
		if (dpos[0] == 0 && dpos[1] == level.imageSize) {facing = "down";}
	}
	

}
