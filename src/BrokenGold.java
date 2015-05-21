import java.awt.Color;

/**
 * 
 * This class is the gold once it has fallen far enough
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class BrokenGold extends Entity {
	private static final long DELAY = 5000;
	private long sinceLast;
	
	public BrokenGold(DiggerMain dm, int x_position, int y_position, String spriteName) {
		super(Color.pink, 500, dm, x_position, y_position, spriteName);
	}
	@Override
	/**
	 * This removes the gold from the board after a certain number of seconds (DELAY)
	 */
	public void updateThis(long time) {
		sinceLast+=time;
		if (sinceLast>DELAY){
			level.entities[(position[0])/level.imageSize][(position[1])/level.imageSize]= new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
	}
}
