package a2;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;

/**
 * Class creates an Instance for a single Image
 *
 * @author Shoaib Khan
 * @author Daniel Visca
 */
public class Image {
	
	//the Image that is being dealt with
	public File img;
	//The history of names that the Image has had
	public StringBuffer history = new StringBuffer();
	//a list of all of the tags that have been Used
	public static List<Tag> tagList = new ArrayList<Tag>();
	public static Scanner reader = new Scanner(System.in);  
	
	//the Path to text file
	public String txtPath;
	public boolean append_to_file = true;
	//Create file saveFile
	static File saveFile = new File("file.txt");
	//Create File logFile
	static File logFile = new File("log.txt");
	//path to saveFile
	static String path = saveFile.getAbsolutePath();
	//path to logFile
	static String logPath = logFile.getAbsolutePath();
	
	
	
	//time stamp
	public StringBuffer stamp = new StringBuffer();
	DateFormat df = new SimpleDateFormat("dd/MM/yy");
	Calendar calobj = Calendar.getInstance();
	
	//for changing the name when adding and removing tags
	String[] fileNameSplits;
	int extensionIndex;
	
	/** Creates Instance of Image given an Image
	 * 
	 * @param img 
	 * 			an Image
	 */
	Image(File img) {
		this.img = img;
		history.append(img.getName());
		fileNameSplits = img.getPath().split("\\.");
		extensionIndex = fileNameSplits.length - 1;
		stamp.append(img.getName() + " : ");
	}

	/** Adds a Tag to the Image, 
	 * Adds the tag to history and AllTags file
	 *  as well as variable allTags
	 * 
	 * @param img 
	 * 			an Image
	 */
	public void addTag(String tag) throws IOException {
		
		String[] nameArray = img.getName().split("@");
		//Check if tag already exists
		Boolean twice = false;
		for (String parts : nameArray) {
			if (("@"+tag.toLowerCase()).equals("@"+parts.toLowerCase()) || (("@"+tag.toLowerCase()+"." + fileNameSplits[extensionIndex]).equals("@"+parts.toLowerCase()))) {
				twice = true;
			}
		}
		if (!twice) {
			//proceed to add tags
			File name = new File(fileNameSplits[0] +"@" + tag + "." + fileNameSplits[extensionIndex]);

			if (img.renameTo(name)) {
				System.out.println("Tag succesfully added.");
				//places name withing path
				img = new File(fileNameSplits[0] +"@" + tag + "." + fileNameSplits[extensionIndex]);
				fileNameSplits = img.getPath().split("\\.");
				extensionIndex = fileNameSplits.length - 1;
				//adds to history
				history.append("\n" + img.getName());
				//update stamp
				stamp.append(img.getName() + " : ");
				stamp.append(df.format(calobj.getTime()));
				//add to log
				log(stamp.toString(), logFile);
				//delete timestamp
				stamp.delete(0, stamp.length());
				stamp.append(img.getName() + " : ");
				//writes tag to all tag file
					try {
						boolean tagExists = false;
						for (Tag tags : tagList){
							if (tag.equals(tags.toString())) {
								tagExists = true;
							}
						}
						//write to tag file if tag does not exist in tag file
						if (!tagExists){
							writeToFile(tag, saveFile);
							tagList.add(new Tag(tag));
							Tag.count++;
						}
					
						System.out.println(saveFile.getAbsolutePath());
					}
					catch (IOException e) {
						e.printStackTrace();
					}
			}
			else {
				System.out.println("Adding tag failed!");
			}
		} else {
		System.out.println("This image already contains this tag!");
		}
	}
	
	/**
	 * removes tag from Image, if tag doesnt exist do nothing
	 *            
	 * @param tag   
	 * 				tag to be removed
	 */
	public void removeTag(String tag) throws IOException {

		String replacedStr = img.getPath().replaceAll("@" + tag, "");

		File name = new File(replacedStr);

		if (img.renameTo(name)) {
			System.out.println("Tag succesfully removed.");
			Tag.count--;
			img = new File(img.getPath().replaceAll("@" + tag, ""));
			stamp.append(img.getName() + " : ");
			stamp.append(df.format(calobj.getTime()));
			log(stamp.toString(), logFile);
			stamp.delete(0, stamp.length());
			stamp.append(img.getName() + " : ");
		}
		else {
			System.out.println("Removing tag failed!");
		}
	}
	
	/**
	 * Returns the history
	 *            
	 * @return history   
	 * 				StringBuffer of History
	 */
	public StringBuffer getHistory() {
		return history;
	}
	
	/**
	 * Returns name of image
	 *            
	 * @return name   
	 * 				the name
	 */
	@Override
	public String toString() {
		return img.getName();
	}
	
	/** Select an existing Tag from all Tags 
	 * @throws IOException */
	public void tagSelection() throws IOException {
		int numberSelect = 0;
		//loop through tags in allTags and print the tag with its corresponding number
		Object[] tagArray = tagList.toArray();
		for (int i=0; i < tagList.size(); i++){
			if (tagArray[i] != null || tagArray[i].toString() != "") {
				numberSelect++;
				String tag = tagArray[i].toString();
				System.out.println( numberSelect + ": " + tag);
			}
		}
		//check tagList isnt empty
		if (tagList.size() != 0) {
			System.out.println("Choose a Tag.");
			//read the users input
			while (!reader.hasNextInt()) {
				System.out.println("Please enter an integer.");
				reader.nextLine();
			}
				int tagIndex = reader.nextInt() - 1;
				if ((tagIndex >= 0)  && (tagIndex < tagList.size())){
					//select corresponding Tag
					String chosenTag = tagArray[tagIndex].toString();
					//add the tag to the current image
					this.addTag(chosenTag);
					}
				else{
					System.out.println("Please select a valid option");
					this.tagSelection();
					}	
			}
		else {
			System.out.println("There are no pre-existing tags!");
		}
		}
	/**
	 * Reverts name to previous name given input by user          
	 */
	public void revert() {
		String[] listNames = history.toString().split("\n");
		System.out.println("Choose a number from the list of previous names.");
		int counterV = 1;
		for (String items: listNames) {
			System.out.println(counterV + ". " + items);
			counterV++;
		}
	
		while (!reader.hasNextInt()) {
			System.out.println("Please enter an integer.");
			reader.nextLine();
		}
		int nameIndex = reader.nextInt() - 1;
		if ((nameIndex >= 0) && (nameIndex <= counterV)) {
			String replacedStr = img.getPath().replaceAll(img.getName(), listNames[nameIndex]);
			File nameV = new File(replacedStr);
			if (img.renameTo(nameV)) {
				System.out.println("Image name succesfully reverted.");
				img = new File(img.getPath().replaceAll(img.getName(), listNames[nameIndex]));
				stamp.append(img.getName() + " : ");
			}
			else {
				System.out.println("Reverting image name failed!");
			}
		}
		else {
			System.out.println("Reverting image name failed!");
		}
	}
		
	/**
	 * write textLine to txtFile
	 *            
	 * @param textLine   
	 * 				The String we want to add to File
	 * @param txtFile
	 * 				The file we want to add it to
	 * @throws IOException
	 */
	public void writeToFile( String textLine, File txtFile ) throws IOException {
		
		txtPath = txtFile.getPath();
		FileWriter writer = new FileWriter( txtPath , append_to_file);
		PrintWriter linePrinter = new PrintWriter( writer );
		linePrinter.printf( "%s" + "%n" , textLine);
		linePrinter.close();
	}
	
	/**
	 * open the File	 *            
	 * @throws IOException   
	 * 	
	 */
	public static void openFile() throws IOException {
		
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
		String[ ] textData = new String[numberOfLines];
		
		int i;
		for (i=0; i < numberOfLines; i++) {
		textData[ i ] = textReader.readLine();
		}
		textReader.close( );
		
		for (String items: textData){
			tagList.add(new Tag(items));
			
		}
	}
	
	/**
	 * helper fucntion for openFiles
	 * Returns the number of lines in File
	 *            
	 * @return numberOfLinrs   
	 * 						The number of Lines in File
	 */
	public static int readLines() throws IOException {
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);
		
		String aLine;
		int numberOfLines = 0;
		
		while ((aLine = bf.readLine()) != null){
			numberOfLines++;
		}
		bf.close();
		return numberOfLines;
	}

	/**
	 * creates log file if it doesnt exist and writes to it
	 * @throws IOException
	 */
	public void log(String logger, File txtFile) throws IOException {
		logPath = logFile.getPath();
		FileWriter writer2 = new FileWriter(logPath , append_to_file);
		PrintWriter linePrinter2 = new PrintWriter( writer2 );
		linePrinter2.printf( "%s" + "%n" , logger);
		System.out.println(logFile.getAbsolutePath());
		linePrinter2.close();
	}
	/**
	 * Make file pop up
	 *            
	 * @throws IOException
	 */
	public static void open(File document) throws IOException {
	    Desktop dt = Desktop.getDesktop();
	    dt.open(document);
	}
}