import java.awt.Color;


public class Gold extends Entity {
	private boolean canFall = true;
	private int originalPosition[] = this.position;

	public Gold(DiggerMain dm, int x_position, int y_position, String spriteName) {
		super(Color.yellow, 0, dm, x_position, y_position, "Gold");
	}
	
	public void fallenBlocks(){
		if (this.position[1]<this.originalPosition[1]-3){
			this.canFall=false;
			this.breakOpen();
		}
	}
	
	public void breakOpen(){
		BrokenGold bg = new BrokenGold(dm, this.position[0],this.position[1]);
	}
	
	

}
