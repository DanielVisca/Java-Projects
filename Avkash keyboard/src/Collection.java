import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Collection {
	
	//The number of times a specific character has been used
	private Map<String , Integer> characterFrequency = new HashMap<String, Integer>();
	//The number of times the given pair has been used in a Document 
	private Map<String , Integer> pairFrequency = new HashMap<String, Integer>();
	//The number of times a specific sequence of 3 letters is used 
	private Map<String , Integer> tripleFrequency = new HashMap<String, Integer>();
	//the file as represented by a String
	private ArrayList fileText;
	
	private Collection(ArrayList fileText){
		this.fileText = fileText;
	}
	
	private void countFrequencies(){
		//iterate through lines of the fileText
		for ( String line : this.fileText){
			//iterate through characters in the line, add characters to the appropriate spots
		}
	}
}
