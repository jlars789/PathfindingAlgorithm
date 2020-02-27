package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Data.AlgData;

public class DataIO {
	
	private static ObjectOutputStream currStream;
	public static AlgData currDat;
	public static ArrayList<AlgData> lbEntries = new ArrayList<AlgData>();
	
	private static void createWriter(String name) {
	
		File file = new File("data/"+name+".dat");
		
		if(!file.exists()) {
			try {
				currStream = new ObjectOutputStream(new FileOutputStream("data/"+name+".dat"));	
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				currStream = new ObjectOutputStream(new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes a single AlgData object to data directory
	 * @param a AlgData object to be stored
	 */
	
	public static void writeOut(AlgData a) {
		
		createWriter(a.getName());
		
		try {
			currStream.writeObject(a);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				currStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes all data to data directory in project
	 */
	
	public static void saveAll() {
		for(int i = 0; i < lbEntries.size(); i++) {
			writeOut(lbEntries.get(i));
		}
	}
	
	/**
	 * Used to full modify an existing AlgData object by name
	 * @param s Name of Algorithm
	 * @param runs Additional runs completed
	 * @param speed Additional speed added
	 * @param success Additional successes added
	 */
	public static void modifyData(String s, int runs, int speed, int success) {
		
		for(int i = 0; i < lbEntries.size(); i++) {
			if(lbEntries.get(i).getName().equalsIgnoreCase(s)) {
				lbEntries.get(i).modify(runs, speed, success);
				writeOut(lbEntries.get(i));
				break;
			}
			if(i+1 == lbEntries.size()) {
				throw new RuntimeException("Algorithm does not exist");
			}
		}
	}
	
	/**
	 * Fully populates the lbEntries ArrayList from the data directory
	 */
	public static void populateArrList() {
		
		File[] files = new File("data").listFiles();
		
		for(int i = 0; i < files.length; i++) {
			ObjectInputStream dis;
			try {
				dis = new ObjectInputStream(new FileInputStream(files[i]));
				lbEntries.add((AlgData) dis.readObject());
			}catch (FileNotFoundException e) {
				System.out.println("File not found");
			} catch(IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sorts the lbEntries ArrayList by Score
	 */
	
	public static void sortByScore() {
		AlgData[] temp = new AlgData[lbEntries.size()];
		
		for(int i = 0; i < temp.length; i++) {
			temp[i] = lbEntries.get(i);
		}
		
		sort(temp, 0, temp.length-1, 0);
		arrToArrList(temp);
	}
	
	/**
	 * Sorts the lbEntries ArrayList by Success Rate
	 */
	
	public static void sortBySuccess() {
		AlgData[] temp = new AlgData[lbEntries.size()];
		
		for(int i = 0; i < temp.length; i++) {
			temp[i] = lbEntries.get(i);
		}
		
		sort(temp, 0, temp.length-1, 1);
		arrToArrList(temp);
	}
	
	/**
	 * Sorts the lbEntries ArrayList by Speed
	 */
	
	public static void sortBySpeed() {
		AlgData[] temp = new AlgData[lbEntries.size()];
		
		for(int i = 0; i < temp.length; i++) {
			temp[i] = lbEntries.get(i);
		}
		
		sort(temp, 0, temp.length-1, 2);
		arrToArrList(temp);
	}
	
	private static void arrToArrList(AlgData[] arr) {
		lbEntries.clear();
		for(int i = 0; i < arr.length; i++) {
			lbEntries.add(arr[i]);
		}
	}
	
	private static int partitionScore(AlgData arr[], int low, int high) { 
        AlgData pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) { 
            // If current element is smaller than the pivot 
            if (arr[j].getScore() > pivot.getScore()) { 
                i++; 
  
                // swap arr[i] and arr[j] 
                AlgData temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        AlgData temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
	
	
	private static int partitionSuccess(AlgData arr[], int low, int high) { 
        AlgData pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) { 
            // If current element is smaller than the pivot 
            if (arr[j].getSuccessRate() > pivot.getSuccessRate()) { 
                i++; 
  
                // swap arr[i] and arr[j] 
                AlgData temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        AlgData temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
	
	private static int partitionSpeed(AlgData arr[], int low, int high) { 
        AlgData pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) { 
            // If current element is smaller than the pivot 
            if (arr[j].getSpeed() < pivot.getSpeed()) { 
                i++; 
  
                // swap arr[i] and arr[j] 
                AlgData temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        AlgData temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
  
    private static void sort(AlgData arr[], int low, int high, int mode) { 
        if (low < high) {
        	int pi = 0;
        	switch(mode) {
        	case 0:
        		pi = partitionScore(arr, low, high); 
        		break;
        	case 1:
        		pi = partitionSuccess(arr, low, high);
        		break;
        	case 2:
        		pi = partitionSpeed(arr, low, high);
        		break;
        	}
        	
            sort(arr, low, pi-1, mode); 
            sort(arr, pi+1, high, mode); 
        } 
    } 

}
