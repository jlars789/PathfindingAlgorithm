package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextReader {

	private String fileName;
	private long[] values;
	
	public TextReader(String fileName) {
		this.fileName = fileName;
		values = new long[1000];
		readValues();
	}
	
	public void readValues() {
		try {
			File x = new File(fileName); //path to file
			Scanner sc = new Scanner(x);
			for(int i = 0; i < 1000; i++) {
				if(sc.hasNext()) {
					values[i] = sc.nextLong();
				}
			}
			sc.close();		
		} catch(FileNotFoundException e) {
			System.out.println("Error");
		}
	}
	
	public long[] getValue() {
		return values;
	}
	
}
