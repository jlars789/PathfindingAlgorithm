import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Astar3dandpathnodetest extends PApplet {

//Masashi  Takahashi 2/11/2020
//for anyone reading this code, go check out sebastion lague's youtube video to understand the general logic of a*
//tried to move to 3d and improve path node function
//shifted size to 1000*1000 canvas

short pixelh =25;
short pixelw =25;
int[][] area = new int[1000/pixelw][1000/pixelh];
int[][] path = area.clone();                         //for some reason, changing the number of dimensions breaks the pathfinding algorithm
short[][][] parents = new short[1000/pixelw][1000/pixelh][2];  //created to not break the pathfinding algorithm
int[] startloc =   {0,0};               //randpoint();
int[] location = startloc;                               // placing the path head at the starting location
int[] target =   {area.length-1,area[1].length-1};           //randpoint();                             //the search algorithms objective
int lowest = Integer.MAX_VALUE;                         //used to search for next low, logically redundant, need to change variable to method local
ArrayList pathnode = new ArrayList();
int randfill = 12;


public void setup(){
  
}



public void draw(){
  background(0);
  printPath();
  noStroke();
  if(mousePressed == true){                          // used for painting obstacles
    path[mouseX/pixelw][mouseY/pixelh] = 3;
    
  }
}

public void keyTyped(){
  if(key == 'd'){
    move();
  }else if(key == 'c'){
    clearAllReset();
  }else if(key == 'p'){
    printLine();
  }else if(key == 'n'){
    startloc = randpoint();
    location = startloc;
  }else if(key == 'm'){
    target = randpoint();
  }else if(key == 'f'){
    fillbuffer();
  }else if(key == 'r'){
    randfill(); 
  }
  
}

public int[] randpoint(){
  int[] alpha = new int[2];
  alpha[0] = (int)(Math.random()*(900/pixelw));
  alpha[1] = (int)(Math.random()*(900/pixelw));
  return alpha;
}

public int deterdist(int[] a, int[] b){               //finds the distance between any 2 points
  int alpha = a[0]-b[0];
  int aleph = a[1]-b[1];
  int tome = 0;
  alpha = (int)Math.pow(alpha,2);
  aleph = (int)Math.pow(aleph,2);
  tome = alpha + aleph;
  if(tome != 0){
    tome = (int)(Math.pow(tome,0.5f)*1000);     //times ten for seperating values due to round off by int
  }
  return tome;
}

public void printPath(){                         //draws the path described by the path array
  for(int i =0; i < area.length; i++){
    for(int j =0; j < area[i].length; j++){
      switch(path[i][j]){
        case 1:               //read pixel position
          fill(256,0,0);
          rect(i*pixelw,j*pixelh,pixelw,pixelh);
          break;           
        case 2:               //position on path
          fill(0,256,0);
          rect(i*pixelw,j*pixelh,pixelw,pixelh);
          break;
        case 3:               //obstacle
          fill(0,0,256);
          rect(i*pixelw,j*pixelh,pixelw,pixelh);
          break;
        case 4:               //final path line
          fill(128,128,256);
          rect(i*pixelw,j*pixelh,pixelw,pixelh);
          break;
        case 5:               //final path line
          fill(0,0,0);
          rect(i*pixelw,j*pixelh,pixelw,pixelh);
          break;
        default:              //background
          fill(256,256,256);
          rect(i*pixelw,j*pixelh,pixelw,pixelh);
          break;
      }
    }
  }
  fill(128,128,128);
  rect(target[0]*pixelw,target[1]*pixelh,pixelw,pixelh);
  rect(startloc[0]*pixelw,startloc[1]*pixelh,pixelw,pixelh);
  for(int i =0 ; i <pathnode.size(); i++){
    int[] alpha = (int[])pathnode.get(i);
    fill(256);
    rect(alpha[0]*pixelw,alpha[1]*pixelh,pixelw,pixelh);
  }
}

public void printLine(){    //printing a single contigous path from start to finish                //XX(fixed):not working, stuck in infinite loop with while loop, parent path might be broken
  int[] line = target.clone();  //XX(fixed)new version: infinite loop found out, parent tree not functioning around turns.
  short[] prevparent = new short[2];
  short[] newparent = new short[2];
  for(int i = 0; i < 1200; i++){                                                     //XX(fixed): adden.- weird off line displacement by parent array, needs to be fixed 
    path[line[0]][line[1]] = 4;
    int alpha = line[0];
    int aleph = line[1];
    newparent[0] = parents[alpha][aleph][0];
    newparent[1] = parents[alpha][aleph][1];
    System.out.println(pathnode.size());
    if((Math.abs(newparent[0]) != Math.abs(prevparent[0]))&&((Math.abs(newparent[1]) != Math.abs(prevparent[1])))){
      pathnode.add(line);
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

public void move(){                                      //executes the pathfinding algorithm
  while(path[target[0]][target[1]] < 1){           //case is testing for whether the path has already crossed over target
    //System.out.println("running step of pathfinding algorithm, location is "+location[0] +"," + location[1]);
    path[location[0]][location[1]] = 2;
    fillNextStep();
    location = findNextLowest();
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
  ArrayList nextlows = new ArrayList();                  //likely method for expanded dimension path array breaking, have no idea what breaks
  int min = Integer.MAX_VALUE;                           
  int[] nextpos = location.clone();
  for(int i =0; i < area.length; i++){                    //would like in the future for path to be an arraylist, so we don't have to scan through every position value
    for(int j =0; j < area[i].length; j++){
      if(path[i][j] == 1){
        if((area[i][j] < min)){
          min = area[i][j];
          nextlows.clear();
          nextpos[0] = i;
          nextpos[1] = j;
          nextlows.add(nextpos);
          System.out.println(nextpos.toString());
        }else if(area[i][j] == min){
          int[] aleph= {i,j};
          nextlows.add(aleph);
        }
      }
    }
  }
  if(nextlows.size() > 1){
    nextpos = searchThroughNL(nextlows);
  }
  return nextpos;
  
}

public int[] searchThroughNL(ArrayList a){    //chooses among equidistant heuristic values
  int[] pos = new int[2];
  int num =0;
  int min = Integer.MAX_VALUE;
  for(int i = 0; i < a.size(); i++){
    int[] suspos = (int[])a.get(i);
    if(deterdist(suspos,target)<min){
      min = deterdist(suspos,target);
      pos = (int[])a.get(i);
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

public void fillbuffer(){                            //calculates the heurisitc distance values around the location head
  Boolean testw = (true);                     //buffer gen logic is off for sake if creating more natrual looking terrain to path around
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

public void randfill(){
  for(int i =0; i<path.length; i++){
    for(int j =0; j<path[1].length; j++){
      int a = (int)(Math.random()*randfill);
      int[] alpha = {i,j};
      if((a > (randfill-2))&&(path[i][j] == 0)&&(alpha.equals(startloc) == false)&&(alpha.equals(target) == false)){
        path[i][j] = 3;
      }
    }
  }
}
  public void settings() {  size(1000,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Astar3dandpathnodetest" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
