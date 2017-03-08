package Warehouse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WarehouseSystem {
	private ArrayList<String> input;
	private String inputFile;
	

	public WarehouseSystem(){}
	
	public WarehouseSystem(String inputFile){
		this.inputFile = inputFile;
	}
//TEST readFile	
	/**
	 * add input to the list of tasks to do
	 * 
	 * @param fileName
	 * @return void
	 */
	public void readFile( String fileName){
		
		try{ 
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			String str;
			while ((str = br.readLine()) != null) {
				input.add(str);
				}
			br.close();
			
		} catch (IOException e){
			System.out.println("File not found");
		}

	}
	public static void main(String[] args){
		WarehouseSystem Ws = new WarehouseSystem();
		Ws.readFile("TestText.txt");		
	}
}
