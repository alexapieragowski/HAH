import java.awt.Color;


public class FallingGold extends Entity {
	private static final long DELAY = 150;
	private long sinceLast;
	private int originalPosition;

	public FallingGold(DiggerMain dm, int x_position, int y_position, String spriteName) {
		super(Color.cyan, 0, dm, x_position, y_position, spriteName);
		originalPosition = position[1];
	}
	
	public void ifBreak(){
		if (position[1] > originalPosition+level.imageSize) breakOpen();
		else {
			Gold newGold = new Gold(dm, position[0], position[1],"Gold"+spriteName.substring(11));
			level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = newGold;
		}
	}
	private void howToMove() {
		if (position[1]/level.imageSize+1<level.gameSize){
			String ex = level.entities[position[0]/level.imageSize][position[1]/level.imageSize+1].spriteName;
			if (!(ex.equals("Dirt")||ex.equals("Emerald")||ex.equals("Gold"))) movement(0,level.imageSize);
			else ifBreak();
		} else ifBreak();
	}
	@Override
	public void updateThis(long time) {
		sinceLast+=time;
		if (sinceLast>DELAY){
			howToMove();
			sinceLast=0;
		}
	}
	public void breakOpen(){
		BrokenGold bg = new BrokenGold(dm, this.position[0],this.position[1],"BrokenGold"+spriteName.substring(11));  
		level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = bg;
	}

}
