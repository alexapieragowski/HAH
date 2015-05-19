import java.awt.Color;


public class FallingGold extends Entity {
	private static final long DELAY = 150;
	private long sinceLast;
	private int originalPosition;

	public FallingGold(DiggerMain dm, int x_position, int y_position) {
		super(Color.cyan, 0, dm, x_position, y_position, "FallingGold");
		originalPosition = position[1];
	}
	
	public void ifBreak(){
		if (position[1] > originalPosition+2*level.imageSize) breakOpen();
		else {
			Gold newGold = new Gold(dm, position[0], position[1]);
			level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = newGold;
		}
	}
	@Override
	public void updateThis(long time) {
		sinceLast+=DELAY;
		if (sinceLast>DELAY){
			movement(0,level.imageSize);
			ifBreak();
			sinceLast=0;
		}
	}
	public void breakOpen(){
		BrokenGold bg = new BrokenGold(dm, this.position[0],this.position[1]);  
		level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = bg;
	}

}
