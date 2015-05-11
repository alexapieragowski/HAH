import java.awt.Color;
import java.util.ArrayList;


public class Nobbin extends Enemies implements Runnable{
	private Level level;
	private Entity entities[][];
	private ArrayList<Integer> hero;
	private static final int DELAY = 1000;
	
	public Nobbin(DiggerMain dm, int x_position, int y_position) {
		super(Color.orange,250,dm,x_position,y_position);
		initDmLevel(dm);
	}
	public void initDmLevel(DiggerMain dm) {
		this.dm=dm;
		level=dm.currentLevel;
	}
	public void howToMove(){
		entities=level.entities;
		getHero();
		int x = hero.get(0);
		int y = hero.get(1);
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
	public void getHero(){
		hero.clear();
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (entities[j][i].color==Color.blue){
					hero.add(entities[j][i].position[0]);
					hero.add(entities[j][i].position[1]);
				}
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
