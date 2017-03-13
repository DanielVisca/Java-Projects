package a2;

import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;


/**
 * The listener for the button to choose a directory. This is where most of the
 * work is done. 
 * SINGLETON DESIGN PATTERN : LAZY INITIALIZATION not called until necessary
 * Stores results for later usage
 */
public class FileChooserButtonListener implements ActionListener {

	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	private JTextArea textArea;
	
	public static List<File> imageList = new ArrayList<File>();
	
	public static List<Image> imageFileList = new ArrayList<Image>();
	
	public static Scanner reader = new Scanner(System.in);  // Reading from System.in
	
	public static int numberSelector = 0;
	
	public static Image[] ImageListArray;
	
	public static boolean go = false;
	
	public static ListSelector select = new ListSelector();

	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public FileChooserButtonListener(JFrame dirFrame, JLabel dirLabel, JTextArea textArea, JFileChooser fileChooser) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.textArea = textArea;
		this.fileChooser = fileChooser;
	}

	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {
				directoryLabel.setText("Selected File" + file.getAbsolutePath());
				// Display a temporary message while the directory is
				// traversed.
				this.textArea.setText("Building file tree");

				// Make the root.
				FileNode fileTree = new FileNode(file.getName(), null, FileType.DIRECTORY);
				FileChooserButtonListener.buildTree(file, fileTree);

				// Build the string representation and put it into the text area.
				
				System.out.println(fileTree);
				StringBuffer contents = new StringBuffer();
				buildDirectoryContents(fileTree, contents);
				
				//buildDirectoryFiles(fileTree, imageList);
				//FileNode[] ArrayFile = new FileNode[countLines(contents.toString())];
				//buildDirectoryList(fileTree, contents, ArrayFile);
				this.textArea.setText(contents.toString());
				listImageFiles(fileTree, imageList, imageFileList);
				convertList(imageFileList);
				go = true;
				int counter = 0;
				for (File files : FileChooserButtonListener.imageList) {
					Start.dlm.add(counter, counter + 1 + ") " + files.getName());
					counter++;
				ListSelector.change(Start.list);
				Start.list.addListSelectionListener(FileChooserButtonListener.select);
				}
			}
			
		} else {
			directoryLabel.setText("No Path Selected");
		}
	
	} 

	/**
	 * Build the tree of nodes rooted at file in the file system; note curr is
	 * the FileNode corresponding to file, so this only adds nodes for children
	 * of file to the tree. Precondition: file represents a directory.
	 * 
	 * @param file
	 *            the file or directory we are building
	 * @param curr
	 *            the node representing file
	 */
	private static void buildTree(File file, FileNode curr) {

		// Creating two arrays, one which includes all the files in the
		// directory and another which will include the fileNodes of those files
		File[] things = file.listFiles();
		FileNode[] nodeThings = new FileNode[things.length];

		// Adding fileNode versions of the files or directories as children of
		// the original node
		// This is being done recursively to also get the children's children
		// and so on
		
		if (things.length != 0) {
			for (int i = 0; i < things.length; i++) {
				if (things[i].isDirectory() == true) {
					nodeThings[i] = new FileNode(things[i].getName(), curr, FileType.DIRECTORY);
					curr.addChild(nodeThings[i].getName(), nodeThings[i]);
					buildTree(things[i], nodeThings[i]);
					
				}
		        //If file is not hidden and is a common Image file
		        if (!things[i].isHidden() && things[i].getName().toLowerCase().endsWith(".png") ||
		        		things[i].getName().toLowerCase().endsWith(".jpg") ||
		        		things[i].getName().toLowerCase().endsWith(".jpeg") ||
		        		things[i].getName().toLowerCase().endsWith(".mpeg"))  {
		        	if (things[i].isFile() == true && things[i]!=null) {
		        		nodeThings[i] = new FileNode(things[i].getName(), curr, FileType.FILE,things[i]);
		        		curr.addChild(nodeThings[i].getName(), nodeThings[i]);
		        		//imageList.add(things[i]);
		        		//System.out.println(imageList);
					}
		        }
			}
		}
		
	}

	/**
	 * Build a string buffer representation of the contents of the tree rooted
	 * at n, prepending each file name with prefix, and adding an additional
	 * DirectoryExplorer.PREFIX for subdirectory contents.
	 *
	 * @param fileNode
	 *            the root of the subtree
	 * @param contents
	 *            the string to display
	 * @param prefix
	 *            the prefix to prepend
	 */
	private static void buildDirectoryContents(FileNode fileNode, StringBuffer contents) {
		if (!fileNode.isDirectory()) {
			numberSelector++;
			contents.append(numberSelector + ". ");
			contents.append(fileNode.getName());
			contents.append("\n");
			
		}
		if (fileNode.isDirectory()) {
			for (FileNode nodess : fileNode.getChildren()) {
				buildDirectoryContents(nodess, contents);
			}
		}
	}
	
	/**
	 * Build a list of all of the Image Files within the tree
	
	 * @param fileNode
	 *            the root of the subtree
	 * @param list
	 *            the list of found Image Files so far
	 */
	public void listImageFiles(FileNode fileNode,List<File> list, List<Image> list2){

		if (!fileNode.isDirectory()) {
			list.add(fileNode.file);
			list2.add(new Image(fileNode.file));
			
			
		}
		if (fileNode.isDirectory()) {
			for (FileNode nodess : fileNode.getChildren()) {
				listImageFiles(nodess, list, list2);
		
			}
		}
	}
	
	public void convertList( List<Image> list) {
		ImageListArray = list.toArray(new Image[list.size()]);
	}
	
}