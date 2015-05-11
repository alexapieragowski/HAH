import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;




public class Entity extends Canvas{
	private Integer pointValue;
	protected DiggerMain dm;
	protected int position[] = new int[2];
	protected Color color;
	private Level level;
	//private image sprite[];
	
	public Entity(Color color, Integer pointValue,DiggerMain dm, int x_position, int y_position) {
		this.pointValue=pointValue;
		this.color=color;
		position[0]=x_position;
		position[1]=y_position;
		initDmLevel(dm);
	}
	public void initDmLevel(DiggerMain dm) {
		this.dm=dm;
		level = dm.currentLevel;
	}
	
	public void paint(Graphics g){ //Eventually these will be sprites instead of rectangles.
		super.paint(g);
		g.setColor(color);
		g.fillRect(0, 0,32,32);
	}
	public void movement(int dx, int dy) {
		level.move(position[0], position[1], dx, dy);
	}
	
	public void die(){
		//Remove enemy?
		awardPoints();
	}
	public void awardPoints(){
		dm.addScore(pointValue);
	}
}
