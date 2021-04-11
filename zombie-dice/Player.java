
public class Player {
	
	private String name;
	private int brain;
	private int shots;
	private int foot;
	private boolean isAlive;
	
	public Player(String name) {
		this.name = name;
		this.brain = 0;
		this.shots = 0;
		this.isAlive = true;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return brain;
	}

	public void setScore(int score) {
		this.brain = score;
	}
	public void setShot(int shot){
		this.shots = shot;
		if (this.shots == 3) {
			isAlive = false;
		}
	}


	
	
	
	
}