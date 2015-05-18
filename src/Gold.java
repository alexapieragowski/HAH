import java.awt.Color;


public class Gold extends Entity {
	private boolean canFall = true;
	private int originalPosition[] = this.position;

	public Gold(DiggerMain dm, int x_position, int y_position) {
		super(Color.yellow, 0, dm, x_position, y_position, "Gold");
	}
	
	public void fallenBlocks(){
		if (this.position[1]<this.originalPosition[1]-3){
			this.canFall=false;
			this.fallDown();
		}
	}
	
	public void fallDown(){
		String ex = level.entities[position[0]/level.imageSize][position[1]/level.imageSize+1].spriteName;
		if (!(ex.equals("Dirt")||ex.equals("Emerald"))){
			//movement(0, level.imageSize);
			FallingGold fallingGold = new FallingGold(dm, position[0], position[1]);
			level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = fallingGold;
		}
	}
	
	

}
