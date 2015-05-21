

/**
 * Controls all of the entities in the game.
 * @author pieragab.
 *         Created May 20, 2015.
 */
public class Update implements Runnable {
	private Level level;
	private static final int DELAY = 16;//60 fps
	
	public Update(Level level) {
		changeLevel(level);
	}
	public void changeLevel(Level level){
		this.level=level;
	}
	
	@Override
	public void run() {
		try{
			long last = System.currentTimeMillis();
			long current;
			long inc;
			while (true){
				current = System.currentTimeMillis();
				inc=current-last;
				last=current;
				for (Entity[] e : level.entities){
					for (Entity entity : e){
						entity.updateThis(inc);
					}
				}
				level.nextLevel();
				level.update(inc);
				level.repaint();
				Thread.sleep(DELAY);
			}
		}catch (InterruptedException exception){}
	}

}
