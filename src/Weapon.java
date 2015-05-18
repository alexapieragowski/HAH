import java.awt.Color;


public class Weapon extends Entity {
	private static final int DELAY = 150;
	private long sinceLast;
	private int[] dpos = {0,0};
	private String facing;
	public Weapon(DiggerMain dm, int x_position, int y_position, String facing) {
		super(Color.white, 0, dm, x_position, y_position,"Weapon");
		this.facing=facing;
	}
	

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
		if (facing == "right") { 
			if (position[0]/level.imageSize+1<level.gameSize){
				this.movement(level.imageSize, 0);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
		if (facing == "left") {
			if (position[0]/level.imageSize-1>=0){
				this.movement(-level.imageSize, 0);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
			
		}
		if (facing == "up") { 
			if (position[1]/level.imageSize-1>=0){
				this.movement(0, -level.imageSize);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
		if (facing == "down") {
			if (position[1]/level.imageSize+1<level.gameSize){
				this.movement(0, level.imageSize);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
	}
	
	

	
}