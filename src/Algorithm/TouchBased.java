package Algorithm;
import java.util.ArrayList;

import Entity.Obstacle;
import Entity.Runner;
import Main.MoveNode;
import Main.Pathfind;
import Main.Window;

public class TouchBased extends Pathfind {

	private ArrayList<Obstacle> ref;
	
	public TouchBased() {
	}

	@Override
	public void update(Runner runRef) {
		
		boolean[] canMove = {true, true, true}; //left, up, right
		
		
		for(int i = 0; i < ref.size(); i++) {
			if(Math.abs(ref.get(i).xCoor()-runRef.xCoor()) <= Window.dimension && Math.abs(ref.get(i).yCoor()-runRef.yCoor()) == 0) {
				if(ref.get(i).xCoor() > runRef.xCoor()) canMove[2] = false;
				else canMove[0] = false;
			}
			else if(Math.abs(ref.get(i).yCoor()-runRef.yCoor()) <= Window.dimension && Math.abs(ref.get(i).xCoor()-runRef.xCoor()) == 0 && runRef.yCoor() > ref.get(i).yCoor()) {
				canMove[1] = false;
			}
		}
		
		runRef.move(canMove);

	}

	@Override
	public ArrayList<MoveNode> getMapPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveNode giveNext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObstacleList(ArrayList<Obstacle> obs) {
		this.ref = obs;
		
	}

	@Override
	public ArrayList<Obstacle> getObstacleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "TouchBased";
	}

}
