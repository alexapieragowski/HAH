import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;




public class Entity extends Canvas{
	private int pointValue;
	private DiggerMain dm;
	private int position[] = new int[2];
	private Color color;
	//private image sprite[];
	
	public Entity(Color color, int pointValue,DiggerMain dm) {
		this.pointValue=pointValue;
		this.dm=dm;
		this.color=color;
		position[0]=0;
		position[1]=0;
	}
	
	public void paint(Graphics g){ //Eventually these will be sprites instead of rectangles.
		super.paint(g);
		g.setColor(color);
		g.fillRect(position[0], position[1],32,32);
	}
	public void movement(int dx, int dy) {
		position[0]+=dx;
		position[1]+=dy;
		this.setLocation(position[0], position[1]);
		System.out.println("Meow");//Does this work?
	}
	
	public void die(){
		//Remove enemy?
		awardPoints();
	}
	public void awardPoints(){
		dm.addScore(pointValue);
	}
}
