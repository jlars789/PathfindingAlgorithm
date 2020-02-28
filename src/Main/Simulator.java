package Main;

import java.util.ArrayList;

import Data.AlgData;
import Entity.*;

public class Simulator implements Runnable {
	
	private Thread t;
	
	private int runs;
	private int[] moveTotal;
	private boolean[] successTotal;
	private boolean running;
	private Runner runner;
	private Map map;
	
	private TextReader tr;
	private ArrayList<Obstacle> obs;
	private EndGoal eg = new EndGoal();
	
	
	public Simulator(int runs, Pathfind p) {
		this.runs = runs;
		moveTotal = new int[runs];
		successTotal = new boolean[runs];
		running = false;
		runner = new Runner(p); 
		tr = new TextReader("seeds.txt");
		obs = new ArrayList<Obstacle>();
		t = new Thread(this);
		AlgData d = new AlgData("TouchBased", 0, 0, 0);
		DataIO.writeOut(d);
		//map = new Map(10, tr.getValue()[0]);
	}
	
	public Simulator(Pathfind p) {
		this(1000, p);
	}
	
	public void setRuns(int runs) {
		this.runs = runs;
	}
	
	public void setAlgorithm(Pathfind p) {
		runner.setAlgorithm(p);
	}

	@Override
	public void run() {
		while(running) {
			
			obs.clear();
			runner.relocate(Window.WIDTH/2, Window.HEIGHT + 32);
			
			
			map = new Map(10, tr.getValue()[runs]);
			generateObs();
			this.runner.updateAlg(obs);
			for(int i = 0; i < 100; i++) {
				runner.tick();
				if(runner.intersects(eg)){
					moveTotal[runs] = i;
					successTotal[runs] = true;
					break;
				}
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			runs++;
			if(runs > 999) {
				int su = 0;
				for(int i = 0; i < successTotal.length; i++) {
					if(successTotal[i])su++;
				}
				int to = 0;
				for(int i = 0; i < moveTotal.length; i++) {
					to += moveTotal[i];
				}
				DataIO.modifyData("TouchBased", runs, to, su);
				DataIO.saveAll(); //change later to specify what to write for optimization
				running = false;
			}
		}
		
	}
	
	public void startSim() {
		t.start();
	}
	
	public boolean isRunning() {
		return t.isAlive();
	}
	
	public void stopSim() {
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateObs() {
		obs = new ArrayList<Obstacle>();
		Obstacle runnerPos = new Obstacle(runner.xCoor(), runner.yCoor());
		for(int i = 0; i <  map.getMap().length; i++) {
			for(int j = 0; j < map.getMap()[i].length; j++) {
				if(map.getMap()[i][j] == 1) {
					Obstacle e = new Obstacle(i * 32, j * 32);
					if (!runnerPos.equals(e))
						obs.add(e);
				}
			}
		}
	}

}
