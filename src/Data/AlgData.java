package Data;

import java.io.Serializable;

public class AlgData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int runs;
	private int success;
	private int speed;
	
	public AlgData(String name, int success, int speed, int runs) {
		this.name = name;
		this.success = success;
		this.speed = speed;
		this.runs = runs;
	}
	
	public double getSuccessRate() {
		return ((double)success/(double)runs);
	}
	
	public int getSpeed() {
		return speed/runs;
	}
	
	public int getScore() {
		return (int)(getSuccessRate() * getSpeed());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void modify(int runs, int speed, int success) {
		this.runs = runs;
		this.speed = speed;
		this.success = success;
	}
	
	public int compareTotal(AlgData d) {
		int comp = 0;
		if(this.getScore() > d.getScore()) comp = 1;
		else if (this.getScore() < d.getScore()) comp = -1;
		return comp;
	}
	
	public int compareSuccessRate(AlgData d) {
		int comp = 0;
		if(this.getSuccessRate() > d.getSuccessRate()) comp = 1;
		else if (this.getSuccessRate() < d.getSuccessRate()) comp = -1;
		return comp;
	}
	
	public int compareSpeed(AlgData d) {
		int comp = 0;
		if(this.getSpeed() > d.getSpeed()) comp = 1;
		else if (this.getSpeed() < d.getSpeed()) comp = -1;
		return comp;
	}

}
