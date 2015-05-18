import java.awt.Color;


public class FallingGold extends Entity {

	public FallingGold(DiggerMain dm, int x_position, int y_position) {
		super(Color.cyan, 0, dm, x_position, y_position, "FallingGold");
	}
	
	public void breakOpen(){
		BrokenGold bg = new BrokenGold(dm, this.position[0],this.position[1]);
	}

}
