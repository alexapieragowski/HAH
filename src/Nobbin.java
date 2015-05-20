import java.awt.Color;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 
 *Creates a Nobbin enemy that will go after the hero
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class Nobbin extends Entity{
	private Entity hero;
	private int goalx;
	private int goaly;
	private ArrayList<int[]> path = new ArrayList<int[]>();
	private static final long DELAY = 250;
	private long sinceLast;
	private int[] dpos = {0,0};
	private static Random r = new Random();
	
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
	/**
	 * 
	 * The pathfinding method for the Nobbin. Uses the A* algorithm to find the go after the hero
	 *
	 * @return
	 */
	private Node aStar(){//Massive overkill?
		boolean[][] visited = new boolean[16][16];
		PriorityQueue<Node> open = new PriorityQueue<Node>();
		Node current;
		
		open.add(new Node(this,null));
		
		while (open.size()!=0){
			current=open.poll();
			if (current.currentx==hero.position[0]&&current.currenty==hero.position[1]) return current;
			visited[current.currentx/level.imageSize][current.currenty/level.imageSize]=true;
			
			for (Node n : getNeighbors(current,visited)){
				visited[n.currentx / level.imageSize][n.currenty / level.imageSize] = true;
				open.add(n);
			}
		}
		return null;
	}
	
	private PriorityQueue<Node> getNeighbors(Node n,boolean visited[][]){
		PriorityQueue<Node> successors = new PriorityQueue<Node>();
		Node newN;
		boolean isValid;
		for (int i = n.currentx/level.imageSize-1;i<=n.currentx/level.imageSize+1;i+=2){
			if(0<=i&&i<level.gameSize){//Checks that neighbor is in map
				newN = new Node(level.entities[i][n.currenty/level.imageSize],n);
				isValid = (newN.entity.spriteName.equals("Empty") || newN.entity.spriteName.equals("Hero"))
						&& !visited[newN.currentx / level.imageSize][newN.currenty / level.imageSize];
				if(isValid) successors.add(newN);
			}
		}
		for (int j = n.currenty/level.imageSize-1;j<=n.currenty/level.imageSize+1;j+=2){
			if(0<=j&&j<level.gameSize){//Checks that neighbor is in map
				newN = new Node(level.entities[n.currentx/level.imageSize][j],n);
				isValid = (newN.entity.spriteName.equals("Empty") || newN.entity.spriteName.equals("Hero"))
						&& !visited[newN.currentx / level.imageSize][newN.currenty / level.imageSize];
				if(isValid)	successors.add(newN);
			}
		}
		return successors;
	}
	private void getPath(Node n){
		while (n!=null){
			int[] delta = {n.dx,n.dy};
			path.add(delta);
			n=n.parent;
		}
	}
	/**
	 * 
	 * Tells the Nobbin to move based on A*
	 *
	 */
	public void howToMove(){
		hero = level.hero;
		if (path.size()==0||!(hero.position[0]==goalx && hero.position[1]==goaly)){
			goalx = hero.position[0];
			goaly = hero.position[1];
			path.clear();
			getPath(aStar());
		}
		if (path.size()!=0) dpos = path.remove(path.size()-1);
	}
	/**
	 * updates the Nobbins movement continuously
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
				Hobbin hobbin = new Hobbin(dm, position[0], position[1]);
				level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = hobbin;
			}
		}
	}
	
	
	private class Node implements Comparable<Node>{
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
		private int gScore(){//Cost to get here from start
			if (parent!=null) gScore=parent.gScore+distanceFromPrevious();
			else gScore=distanceFromPrevious();
			return gScore;
		}
		private int hScore(){//Cost estimate from here to the goal
			return distanceToGoal();
		}
		private void fScore(){
			fScore=gScore()+hScore();
		}
		
		private int distanceFromPrevious(){//Manhattan Distance
			if (parent!=null){
				int distx = Math.abs(parent.currentx-currentx);
				int	disty = Math.abs(parent.currenty-currenty);
				return distx+disty;
			}
			return 0;
		}
		private int distanceToGoal(){//Manhattan Distance
			int distx = Math.abs(goalx-currentx);
			int	disty = Math.abs(goaly-currenty);
			return distx+disty;
		}
		
		public int compareTo(Node o) {
			return fScore-o.fScore;
		}
	}
}
