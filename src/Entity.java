

public class Entity {
	private int pointValue;
	private DiggerMain dm;
	private Level level;
	
	public Entity(int image, int pointValue) {
		this.pointValue=pointValue;
	}
	
	public void initMain(DiggerMain dm){
		this.dm=dm;
		level=dm.current_level;
	}
	
	public void draw() {
		
	}
	public void move(int dx, int dy) {
		
	}
	public void die(){
		
	}
	public void awardPoints(){
		dm.addScore(pointValue);
	}
}
