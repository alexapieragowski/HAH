import java.awt.Color;

/**
 * 
 *Creates an unfallen gold bag that can drop and be pushed
 *
 * @author heshelhj.
 *         Created May 14, 2015.
 */
public class Gold extends Entity {
	private boolean canFall = true;
	private int originalPosition[] = this.position;

	public Gold(DiggerMain dm, int x_position, int y_position) {
		super(Color.yellow, 0, dm, x_position, y_position, "Gold");
	}
	/**
	 * 
	 * checks to see if the bag has fallen far enough and if it calls the breakOpen method
	 *
	 */
	public void fallenBlocks(){
		if (this.position[1]<this.originalPosition[1]-3){
			this.canFall=false;
			this.breakOpen();
		}
	}
	/**
	 * 
	 * creates a new Broken gold bag that can be collected for points
	 *
	 */
	public void breakOpen(){
		BrokenGold bg = new BrokenGold(dm, this.position[0],this.position[1]);
	}
	
	

}
