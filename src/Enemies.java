import java.awt.Color;


public class Enemies extends Entity {
	
	public boolean toggle;

	public Enemies(Color color, Integer pointValue, DiggerMain dm,
			int x_position, int y_position,String spriteName) {
		super(color, pointValue, dm, x_position, y_position, spriteName);
		toggle = true;
	}
	
	public void enemySwitch(){
		toggle = !toggle;
	}
	
	
	
}
