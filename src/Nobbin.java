import java.awt.Color;
import java.util.ArrayList;


public class Nobbin extends Enemies{
	private Entity hero;
	private int goalx;
	private int goaly;
	private ArrayList<int[]> path = new ArrayList<int[]>();
	private static final long DELAY = 250;
	private long sinceLast;
	private int[] dpos = {0,0};
	
	public Nobbin(DiggerMain dm, int x_position, int y_position) {
		super(Color.orange,250,dm,x_position,y_position,"Nobbin");
	}
	public void myInitDmLevel(DiggerMain dm) {
		this.dm=dm;
		level=dm.currentLevel;
		hero=level.hero;
		goalx = hero.position[0];
		goaly = hero.position[1];
		sinceLast=DELAY;
	}
	private void aStar(){//Massive overkill?
		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(new Node(this,null));
		ArrayList<Node> successors;
		Node current = null;
		int currentf;
		boolean isContinue;
		while (open.size()!=0){
			currentf=-1;
			for (int i=0;i<open.size();i++){
				if (currentf==-1||open.get(i).fScore<currentf) {
					current=open.get(i);
					currentf=current.fScore;
				}
			}
			if (current.currentx==hero.position[0]&&current.currenty==hero.position[1]){
				path.clear();
				getpath(current);
				break;
			}
			open.remove(current);
			closed.add(current);
			successors=getNeighbors(current);
			for (Node n : successors){
				isContinue=false;
				for (int i=0;i<closed.size();i++){
					if (closed.get(i).currentx==n.currentx && closed.get(i).currenty==n.currenty) {
						isContinue=true;
						break;
					}
				}
				if(isContinue) continue;
				for (int i=0;i<open.size();i++){
					if (open.get(i).currentx==n.currentx && open.get(i).currenty==n.currenty) {
						isContinue=true;
						break;
					}
				}
				if(isContinue) continue;
				open.add(n);
			}
			System.out.println(open.size());
			System.out.println(closed.size());
		}
	}
	private ArrayList<Node> getNeighbors(Node n){
		ArrayList<Node> successors = new ArrayList<Node>();
		for (int i = n.currentx/level.imageSize-1;i<=n.currentx/level.imageSize+1;i+=2){
			if(0<=i&&i<level.gameSize){
				Node newN = new Node(level.entities[i][n.currenty/level.imageSize],n);
				if(newN.entity.spriteName.equals("Empty")|| newN.entity.spriteName.equals("Hero")){
					successors.add(newN);
				}
			}
		}
		for (int j = n.currenty/level.imageSize-1;j<=n.currenty/level.imageSize+1;j+=2){
			if(0<=j&&j<level.gameSize){
				Node newN = new Node(level.entities[n.currentx/level.imageSize][j],n);
				if(newN.entity.spriteName.equals("Empty")|| newN.entity.spriteName.equals("Hero")){
					successors.add(newN);
				}
			}
		}		
		return successors;
	}
	private void getpath(Node n){
		while (n!=null){
			int[] delta = {n.dx,n.dy};
			path.add(delta);
			n=n.parent;
		}
	}
	public void howToMove(){
		hero = level.hero;
		if (path.size()==0||!(hero.position[0]==goalx && hero.position[1]==goaly)){
			goalx = hero.position[0];
			goaly = hero.position[1];
			aStar();
		}
		if (path.size()!=0) dpos = path.remove(path.size()-1);
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
	
	
	private class Node{
		public Entity entity;
		public Node parent;
		public int gScore;
		public int fScore;
		public int currentx;
		public int currenty;
		public int dx;
		public int dy;
		
		public Node(Entity entity, Node parent) {
			this.entity=entity;
			this.parent=parent;
			currentx=entity.position[0];
			currenty=entity.position[1];
			if (parent!=null){
				dx=currentx-parent.currentx;
				dy=currenty-parent.currenty;
			}
			fScore();
		}
		private void gScore(){
			if (parent==null) gScore=0;
			else gScore=parent.gScore+distance(false);
		}
		private void fScore(){
			gScore();
			fScore=gScore+distance(parent==null);
		}
		private int distance(boolean isStart){//Manhattan Distance
			int distx;
			int disty;
			if (isStart){
				distx = Math.abs(goalx-currentx);
				disty = Math.abs(goaly-currenty);
			}else{
				distx = Math.abs(parent.currentx-currentx);
				disty = Math.abs(parent.currenty-currenty);
			}
			return distx+disty;
		}
	}
}
