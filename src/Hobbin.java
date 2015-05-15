import java.awt.Color;
import java.util.ArrayList;


public class Hobbin extends Enemies{
	private ArrayList<Integer> emeralds = new ArrayList<Integer>();
	private int indexOfClosest;
	private static final long DELAY = 500;
	private long sinceLast;
	private int[] dpos = {0,0};
	
	public Hobbin(DiggerMain dm, int x_position, int y_position) {
		super(Color.red,250,dm,x_position,y_position, "Hobbin");
		sinceLast=DELAY;
		initDmLevel(dm);
	}
	
	public void howToMove(){
		getEmeralds();
		getClosestEmerald();
		if (emeralds.size()!=0){
			int x = emeralds.get(indexOfClosest);
			int y = emeralds.get(indexOfClosest+1);
			if (position[0]!=x){
				if (position[0]>x) dpos[0]=-level.imageSize;
				else dpos[0]=level.imageSize;
			}
			if (position[1]!=y){
				if (position[1]>y) dpos[1]=-level.imageSize;
				else dpos[1]=level.imageSize;
			}
		}
	}
	public void updateThis(long time) {
		sinceLast+=time;
		if (sinceLast>DELAY){
			howToMove();
			movement(dpos[0],dpos[1]);
			dpos[0]=0;
			dpos[1]=0;
			sinceLast=0;
		}
	}
	public void getEmeralds(){
		emeralds.clear();
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (level.entities[j][i].color.equals(Color.green)){
					emeralds.add(level.entities[j][i].position[0]);
					emeralds.add(level.entities[j][i].position[1]);
				}
			}
		}
	}
	public void getClosestEmerald(){
		double distanceOfClosest=2000;//Arbitrarily large number
		double distance;
		for (int i=0;i<emeralds.size();i+=2){
			distance=Math.sqrt(Math.pow((emeralds.get(i)+this.position[0]),2)+Math.pow((emeralds.get(i+1)+this.position[1]),2));
			if (distance<distanceOfClosest){
				distanceOfClosest=distance;
				indexOfClosest=i;
			}
		}
	}
}
