


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
				level.repaint();
				level.nextLevel();
				Thread.sleep(DELAY);
			}
		}catch (InterruptedException exception){}
	}

}
