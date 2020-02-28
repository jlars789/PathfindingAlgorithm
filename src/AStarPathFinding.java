/*
Masashi Takahashi
trying to copile previous logic from processing 3 into an object
*/
import java.util.ArrayList;

public class AStarPathFinding{

  public int[][] area ;
  public int[][] path ;                         
  public short[][][] parents;  
  public int[] startloc =   new int[2];               
  public int[] location = startloc.clone();                               // placing the path head at the starting location
  public int[] target =   new int[2];                                       //the search algorithms objective
  public int lowest = Integer.MAX_VALUE;                         //used to search for next low, logically redundant, need to change variable to method local
  public ArrayList<MoveNode> pathnode;
  public ArrayList<MoveNode> keynode;
  public ArrayList<Obstacle> localObs;
  public ArrayList<MoveNode> localtrace;

  private Boolean pathinit = false;
  private Boolean areainit = false;
  private Boolean startlocinit = false;
  private Boolean targetinit = false;
  private Boolean parentinit = false;
  public boolean omnipresent = false;


  public AStarPathFinding(){
  }

  public AStarPathFinding(int sizex ,int sizey, int[] start, int [] end){
    area = new int[sizex][sizey];
    path = area.clone();
    parents = new short[sizex][sizey][start.length];
    startloc = start.clone();
    target = end.clone();

    pathinit = true;
    areainit = true;
    startlocinit = true;
    targetinit = true;
    parentinit = true;
  }

  public AStarPathFinding(int[][] course, int[] start, int [] end){
    path = course.clone();
    area = new int[course.length][course[0].length];
    parents = new short[course.length][course[0].length][start.length];
    startloc = start.clone();
    target = end.clone();

    pathinit = true;
    areainit = true;
    startlocinit = true;
    targetinit = true;
    parentinit = true;
    omnipresent = true;
  }

  public Boolean initConditionsSet(){
    if((pathinit == true) && (areainit == true) && (startlocinit == true) && (targetinit == true)){
       return true;
    }else {
       return false;
    }
  }

  public void SetStart(int[] a){
    startloc = a.clone();

    startlocinit = true;
  }

  public void SetTarget(int[] a){
    target = a.clone();

    targetinit = true;
  }

  public void GiveCourseFull(int[][] a){
    path = a.clone();
    area = new int[a.length][a[0].length];
    
    pathinit =true;
    areainit = true;
    omnipresent = true;
  }

  public void GiveCourse(int[][] a){
    path = a.clone();
    area = new int[a.length][a[0].length];
    
    pathinit =true;
    areainit = true;
    omnipresent = false;
  }

  public void setParent(){
    if(parentinit == true){
      return;
    }else if ((startlocinit == true) && ((pathinit == true) && (areainit == true))){
      parents = new short[path.length][path[0].length][startloc.length];
      parentinit = true;
    }else{
      parentinit = false;
    }
  }

  public int deterdist(int[] a, int[] b){               //finds the distance between any 2 points
    int alpha = a[0]-b[0];
    int aleph = a[1]-b[1];
    int tome = 0;
    alpha = (int)Math.pow(alpha,2);
    aleph = (int)Math.pow(aleph,2);
    tome = alpha + aleph;
    if(tome != 0){
      tome = (int)(Math.pow(tome,0.5)*1000);     //times ten for seperating values due to round off by int
    }
    return tome;
  }
 
  public void run(){
    if(initConditionsSet() == true){
      if(omnipresent == true){
         moveOmni();
      }else{
         moveStep();
      }
    }
  }

  public void moveOmni(){                                      //executes the pathfinding algorithm
    while(path[target[0]][target[1]] < 1){           //case is testing for whether the path has already crossed over target
      //System.out.println("running step of pathfinding algorithm, location is "+location[0] +"," + location[1]);
      path[location[0]][location[1]] = 2;
      fillNextStep();
      location = findNextLowest();
      pathnode.add(new MoveNode(location[0],location[1]));
    }
    printLine();
  }

  
  public void moveStep(){                                      //iterates the pathfinding algorithm one step
    if(path[target[0]][target[1]] < 1){           //case is testing for whether the path has already crossed over target
      path[location[0]][location[1]] = 2;
      fillNextStep();
      MoveNode alpha = new MoveNode(location[0],location[1]);
      location = findNextLowest();
      MoveNode beta = new MoveNode(location[0],location[1]);
      pathnode.addAll(trace(alpha,beta));
    }else{
      printLine();
    }
  }

  public ArrayList<MoveNode> GetPath(){
    return pathnode;
  }

  public ArrayList<MoveNode> trace(MoveNode a,MoveNode b){
    ArrayList<MoveNode> startone = new ArrayList<MoveNode>();
    startone.add(a);
    int[] loca = new int[2];
    ArrayList<MoveNode> starttwo = new ArrayList<MoveNode>();
    starttwo.add(b);
    int[] locb = new int[2];
    ArrayList<MoveNode> finalstitcha = new ArrayList<MoveNode>();
    ArrayList<MoveNode> finalstitchb = new ArrayList<MoveNode>();
    while(startloc.equals(startone.get(startone.size()-1).pos()) != false){
      loca[0] = loca[0]+parents[loca[0]][loca[1]][0];
      loca[1] = loca[1]+parents[loca[0]][loca[1]][1];
      startone.add(new MoveNode(loca[0],loca[1]));
    }
    while(startloc.equals(starttwo.get(starttwo.size()-1).pos()) != false){
      locb[0] = locb[0]+parents[locb[0]][locb[1]][0];
      locb[1] = locb[1]+parents[locb[0]][locb[1]][1];
      starttwo.add(new MoveNode(locb[0],locb[1]));
    }
    for(int i =0; i < startone.size(); i++){
      for(int j =0; j < starttwo.size(); j++){
        if(startone.get(i).pos().equals(starttwo.get(j).pos())){
            
            finalstitcha.addAll(startone.subList(0,i));
            finalstitchb.addAll(starttwo.subList(0,j));
            for(int k =1; k <= finalstitchb.size(); k++){ 
              finalstitcha.add(finalstitchb.get(finalstitchb.size()-i));
            }
            break;
        }
      }
      if(finalstitcha.size() > 1){
        break;
      }
    }
    return finalstitcha;
  }

  public void printLine(){                  
    int[] line = target.clone();  
    short[] prevparent = new short[2];
    short[] newparent = new short[2];

    for(int i = 0; i < 1200; i++){                                                    
      path[line[0]][line[1]] = 4;
      int alpha = line[0];
      int aleph = line[1];
      newparent[0] = parents[alpha][aleph][0];
      newparent[1] = parents[alpha][aleph][1];
      //System.out.println(pathnode.size());
      if((Math.abs(newparent[0]) != Math.abs(prevparent[0]))&&((Math.abs(newparent[1]) != Math.abs(prevparent[1])))){
        keynode.add(new MoveNode(line[0],line[1]));
        path[line[0]][line[1]] = 5;
      }
      line[0] -= parents[alpha][aleph][0];
      line[1] -= parents[alpha][aleph][1];
      //System.out.println( parents[line[0]][line[1]][0] +""+ parents[line[0]][line[1]][1]);
      //if((parents[line[0]][line[1]][0] == 0) && (parents[line[0]][line[1]][1] == 0)){
        //break;
      //}
      
    }
  }

  public void fillNextStep(){                               //calculates the heurisitc distance values around the location head
    Boolean testw = (true);                          
    Boolean testh = (true);
    int[] pos =new int[2];

    for(int i = -1; i < 2; i++){
      for(int j = -1; j < 2; j++){
        pos[0] = location[0]+i;
        pos[1] = location[1]+j;
        testw = ((pos[0]>=0)&&((pos[0])<(area.length)));
        testh = ((pos[1]>=0)&&((pos[1])<(area[1].length)));
        
        
        if(((testw==true) && (testh==true))&&(path[pos[0]][pos[1]] == 0)){
          area[pos[0]][pos[1]] = deterdist(pos,location) + deterdist(pos,target) + area[location[0]][location[1]];
          path[pos[0]][pos[1]] = 1;
          parents[pos[0]][pos[1]][0] = (short)i;       //places direction of parent in storage
          parents[pos[0]][pos[1]][1] = (short)j;
        }else if((testw==true) && (testh==true)&&(path[pos[0]][pos[1]] == 1)){                                               //looking over already scanned pixels to see if displacement can be reduced
          if((deterdist(pos,location) + deterdist(pos,target) + area[location[0]][location[1]]) < area[pos[0]][pos[1]]){      //testing to see if displacement from start can be reduced by passing through current location pixel 
            area[pos[0]][pos[1]] = deterdist(pos,location) + deterdist(pos,target) + area[location[0]][location[1]];
            parents[pos[0]][pos[1]][0] = (short)i;       
            parents[pos[0]][pos[1]][1] = (short)j;
          }
        }
      } 
    }
  }

  public int[] findNextLowest(){                                 //choosing another head position due to previous head having higher neighbors
    ArrayList<MoveNode> nextlows = new ArrayList<MoveNode>();                  //likely method for expanded dimension path array breaking, have no idea what breaks
    int min = Integer.MAX_VALUE;                           
    int[] nextpos = location.clone();

    for(int i =0; i < area.length; i++){                    //would like in the future for path to be an arraylist, so we don't have to scan through every position value
      for(int j =0; j < area[i].length; j++){
        MoveNode alpha = new MoveNode(i,j);
        if(path[i][j] == 1){
          if((area[i][j] < min)){
            min = area[i][j];
            nextlows.clear();
            nextpos[0] = i;
            nextpos[1] = j;
            nextlows.add(alpha);
            System.out.println(nextpos.toString());
          }else if(area[i][j] == min){
            nextlows.add(alpha);
          }
        }
      }
    }
    if(nextlows.size() > 1){
      nextpos = searchThroughNL(nextlows);
    }
    return nextpos;
    
  }

  public void ApplyLocalObstacles(ArrayList<Obstacle> LocalOb){
    for(int i =0; i< localOb.size(); i++){
      Obstacle e = LocalOb.get(i);
      path[e.xCoor()][e.yCoor()] = 3;
    }
  }

  public int[] searchThroughNL(ArrayList<MoveNode> a){    //chooses among equidistant heuristic values
    int[] pos = new int[2];
    int num =0;
    int min = Integer.MAX_VALUE;

    for(int i = 0; i < a.size(); i++){
      int[] suspos = {a.get(i).xCoor(),a.get(i).yCoor()};
      if(deterdist(suspos,target)<min){
        min = deterdist(suspos,target);
        pos[0] = a.get(i).xCoor();
        pos[1] = a.get(i).yCoor();
        num = 1;
      }else if(deterdist(suspos,target) == min){
        num++;
      }
    }
    if(num > 1){
      //pos = (int[])a.get((int)(Math.random()*(a.size())));
    }
    return pos;
  }

  public void fillBuffer(){                            //calculates the heurisitc distance values around the location head
    Boolean testw = (true);                     //buffer gen logic is off for sake of creating more natural looking terrain to path around
    Boolean testh = (true);                     //can be fixed by removing code snippet from previous version
    int[] pos =new int[2];
    for(int i = 0; i <path.length; i++){
      for(int j = 0; j <path.length; j++){
        for(int k = 0; k < 2; k++){
          for(int l = 0; l < 2; l++){
           
            pos[0] = i+k;
            pos[1] = j+l;
            testw = ((pos[0]>=0)&&((pos[0])<(path.length)));
            testh = ((pos[1]>=0)&&((pos[1])<(path[1].length)));
        
        
            if(((testw==true) && (testh==true))){
              if(((path[pos[0]][pos[1]] == 3)&&((k+l)==1))&&((pos.equals(startloc) != true)&&(pos.equals(target) != true))){  //now doesn't overlap start or end location
                path[i][j] = 3;
              }
            }
          }
        }
      }
    }
    for(int i = path.length-1; i > -1; i--){
      for(int j = path[1].length-1; j > -1; j--){
        for(int k = -1; k < 1; k++){
          for(int l = -1; l < 1; l++){
           
            pos[0] = i+k;
            pos[1] = j+l;
            testw = ((pos[0]>=0)&&((pos[0])<(path.length)));
            testh = ((pos[1]>=0)&&((pos[1])<(path[1].length)));
        
        
            if(((testw==true) && (testh==true))){
              if((path[pos[0]][pos[1]] == 3)&&((k+l)==-1)&&(pos.equals(startloc) != true)&&(pos.equals(target) != true)){
                path[i][j] = 3;
              }
            }
          }
        }
      }
    }
  }

  public void clearAllReset(){                //method to clear path and area array so program can be run again without restart (needs to be finished)
    for(int i = 0; i < path.length; i++){
      for(int j = 0; j < path[i].length; j++){
        if(path[i][j] != 3){
          path[i][j] = 0;
        }
      }
    }
   
    for(int i = 0; i < area.length; i++){
      for(int j = 0; j < area[i].length; j++){
        area[i][j] = 0;
      }
    }
    location = startloc;
  }  

}