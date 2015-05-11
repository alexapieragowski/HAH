import java.awt.Color;
import java.util.ArrayList;


public class Hobbin extends Enemies implements Runnable{
	private Level level;
	private Entity entities[][];
	private ArrayList<Integer> emeralds;
	private int indexOfClosest;
	private static final int DELAY = 1000;
	
	public Hobbin(DiggerMain dm, int x_position, int y_position) {
		super(Color.red,250,dm,x_position,y_position);
		initDmLevel(dm);
	}
	public void initDmLevel(DiggerMain dm) {
		this.dm=dm;
		level=dm.currentLevel;
	}
	public void howToMove(){
		entities=level.entities;
		getEmeralds();
		getClosestEmerald();
		int x = emeralds.get(indexOfClosest);
		int y = emeralds.get(indexOfClosest+1);
		int dx=0,dy=0;
		if (position[0]!=x){
			if (position[0]<x) dx=-1;
			else dx=1;
		}
		if (position[1]!=y){
			if (position[1]<y) dy=-1;
			else dy=1;
		}
		super.movement(dx,dy);
	}
	public void getEmeralds(){
		emeralds.clear();
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (entities[j][i].color==Color.green){
					emeralds.add(entities[j][i].position[0]);
					emeralds.add(entities[j][i].position[1]);
				}
			}
		}
	}
	public void getClosestEmerald(){
		double distanceOfClosest=200;
		double distance;
		for (int i=0;i<emeralds.size();i+=2){
			distance=Math.sqrt(Math.pow((emeralds.get(i)+this.position[0]),2)+Math.pow((emeralds.get(i+1)+this.position[1]),2));
			if (distance<distanceOfClosest){
				distanceOfClosest=distance;
				indexOfClosest=i;
			}
		}
	}
	@Override
	public void run() {
		try{
			howToMove();
			Thread.sleep(DELAY);
		}catch (InterruptedException exception){}
	}
}
