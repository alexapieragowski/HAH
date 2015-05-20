

public class Update implements Runnable {
	private Level level;
	private static final int DELAY = 16;//60 fps
	private boolean run;
	
	public Update(Level level) {
		changeLevel(level);
		run=true;
	}
	public void changeLevel(Level level){
		this.level=level;
	}
	public void toggle(){
		if (run) run=false;
		else {
			run=true;
			run();
		}
	}
	
	@Override
	public void run() {
		try{
			long last = System.currentTimeMillis();
			long current;
			long inc;
			while (run){
				current = System.currentTimeMillis();
				inc=current-last;
				last=current;
				for (Entity[] e : level.entities){
					for (Entity entity : e){
						entity.updateThis(inc);
					}
				}
				level.repaint();
				boolean nextLevel = level.getEmeralds().size()==0;
				Thread.sleep(DELAY);
			}
		}catch (InterruptedException exception){}
	}

}
