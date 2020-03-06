//Masashi Takahashi
import java.util.ArrayList;
//import Entity.Obstacle;
//import Entity.Runner;
//import Main.EntityList;
//import Main.MoveNode;
//import Main.Pathfind;
//import Main.Window;

public class RoombaAlgorithm{
  private int[] target;
  public int[] location;
  private int[] loctarget;
  private int[][] arena;

  public RoombaAlgorithm(int[] start, int[] end,int sizex, int sizey){
    location = start.clone();
    target = end.clone();
    loctarget = target.clone();
    arena = new int[sizex][sizey];
  }
  
  public MoveNode update(){
    int[] displacement = new int[0];
    int[] direction = {loctarget[0]-location[0],loctarget[1]-location[1]};
    double theta = Math.tan(direction[0]/direction[1]);

    if(Math.abs(Math.sin(theta)) > .5){
      displacement[1] = 1*(int)(Math.abs(Math.sin(theta))/Math.sin(theta));
    }

    if(Math.abs(Math.cos(theta)) > .5){
      displacement[0] = 1*(int)(Math.abs(Math.cos(theta))/Math.cos(theta));
    }

    if(arena[location[0] + displacement[0]][location[1] + displacement[1]] == 0){
      location[0] += displacement[0];
      location[1] += displacement[1];
    }else{
      loctarget[0] = (int)(Math.random()*arena.length);
      loctarget[1] = (int)(Math.random()*arena[0].length);
      return update();
    }
    MoveNode newpos = new MoveNode(location[0]*Window.DIMENSION,location[1]*Window.DIMENSION);
    return newpos;
  }

  public void applyObstacles(ArrayList<MoveNode> obstacles){
    for(int i=0; i< obstacles.size(); i++){
       MoveNode e= obstacles.get(i);
       int[] alpha = {e.xCoor()/Window.DIMENSION,e.yCoor()/Window.DIMENSION};
       arena[alpha[0]][alpha[1]] = 1;
    }
  }

}