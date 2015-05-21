import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 *Creates a hobbin enemy that attempts to collect the emeralds before the hero is able to
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class Hobbin extends Entity{
	private ArrayList<Integer> treasure = new ArrayList<Integer>();
	private int indexOfClosest;
	private static final long DELAY = 500;
	private long sinceLast;
	private int[] dpos = {0,0};
	private static Random r = new Random();
	
	public Hobbin(DiggerMain dm, int x_position, int y_position) {
		super(Color.red,250,dm,x_position,y_position, "Hobbin");
		sinceLast=DELAY;
		initDmLevel(dm);
	}
	/**
	 * 
	 * Moves the hobbin in the direction of the closest emerald
	 *
	 */
	public void howToMove(){
		treasure.clear();
		treasure.addAll(level.getEmeralds());
//		treasure.addAll(level.getGold());
		getClosestTreasure();
		if (treasure.size()!=0){
			int x = treasure.get(indexOfClosest);
			int y = treasure.get(indexOfClosest+1);
			if (position[0]!=x){
				if (position[0]>x) dpos[0]=-level.imageSize;
				else dpos[0]=level.imageSize;
			}
			else if (position[1]!=y){
				if (position[1]>y) dpos[1]=-level.imageSize;
				else dpos[1]=level.imageSize;
			}
			String nextSprite = level.entities[(position[0]+dpos[0])/level.imageSize][(position[1]+dpos[1])/level.imageSize].spriteName;
			if(nextSprite.equals("Emerald")) dm.addScore(-100);
			if(nextSprite.contains("Gold")) level.entities[(position[0]+dpos[0])/level.imageSize][(position[1]+dpos[1])/level.imageSize] = new Entity(Color.black,0,dm,position[0]+dpos[0],position[1]+dpos[1],"Empty");
		}
	}
	/**
	 * updates the board as hobbin needs to move
	 * @param time long time since last update.
	 */
	public void updateThis(long time) {
		sinceLast+=time;
		if (sinceLast>DELAY){
			int x = r.nextInt(20);
			if (x!=0){
				howToMove();
				movement(dpos[0],dpos[1]);
				dpos[0]=0;
				dpos[1]=0;
				sinceLast=0;
			}else {
				Nobbin nobbin = new Nobbin(dm, position[0], position[1]);
				level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = nobbin;
			}
		}
	}
	/**
	 * 
	 * gets the position of the closest emerald using Pythagorean Theorem.  
	 *
	 */
	public void getClosestTreasure(){
		double distanceOfClosest=2000;//Arbitrarily large number
		double distance;
		for (int i=0;i<treasure.size();i+=2){
			distance=Math.sqrt(Math.pow((treasure.get(i)-this.position[0]),2)+Math.pow((treasure.get(i+1)-this.position[1]),2));
			if (distance<distanceOfClosest){
				distanceOfClosest=distance;
				indexOfClosest=i;
			}
		}
	}
}
