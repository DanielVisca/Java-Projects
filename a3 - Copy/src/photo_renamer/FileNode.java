package a2;

import java.util.Map;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;

/**
 * File Node turns a File into a Node to be added to a Tree
 * @author Daniel Visca
 * @author Shoaib khan
 */
public class FileNode {

	/** The name of the file or directory this node represents. */
	public String name;
	/** Whether this node represents a file or a directory. */
	public FileType type;
	/** This node's parent. */
	public FileNode parent;
	/**The file associated with this Node*/
	public File file;
	
	/**
	 * This node's children, mapped from the file names to the nodes. If type is
	 * FileType.FILE, this is null.
	 */
	private Map<String, FileNode> children;

	/**
	 * A node in this tree.
	 *
	 * @param name
	 *            the file name
	 * @param parent
	 *            the parent node.
	 * @param type
	 *            file or directory
	 * @param file
	 * 			  The File 
	 * @see buildFileTree
	 */
	public FileNode(String name, FileNode parent, FileType type, File file) {
		this.name = name;
		this.parent = parent;
		this.type = type;
		this.file = file;
		// the HashMap will only be created if the type is Directory,
		// since there is no need for one if the file is just a file
		if (this.type == FileType.DIRECTORY) {
			children = new HashMap<String, FileNode>();
		}
	}
	/**
	 * 
	 * A node in this tree. Second Constructor
	 *
	 * @param name
	 *            the file
	 * @param parent
	 *            the parent node.
	 * @param type
	 *            file or directory
	 * @see buildFileTree
	 */
	public FileNode(String name, FileNode parent, FileType type) {
		this.name = name;
		this.parent = parent;
		this.type = type;
		// the HashMap will only be created if the type is Directory,
		// since there is no need for one if the file is just a file
		if (this.type == FileType.DIRECTORY) {
			children = new HashMap<String, FileNode>();
		}
	}

	/**
	 * Find and return a child node named name in this directory tree, or null
	 * if there is no such child node.
	 *
	 * @param name
	 *            the file name to search for
	 * @return the node named name
	 */
	public FileNode findChild(String name) {
		FileNode result = null;

		// Search within the children map keys to see if 'name' is in there
		// if it is, then the value of the key will be returned (which is the
		// fileNode object)
		// if it is a directory though, it will recursively check within that
		// directory using the same parameter
		for (String item : children.keySet()) {
			if (item == name) {
				return children.get(item);
			} else if (children.get(item).isDirectory())
				children.get(item).findChild(name);
		}
		return result;
	}

	/**
	 * Return the name of the file or directory represented by this node.
	 *
	 * @return name of this Node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the current node
	 *
	 * @param name
	 *            of the file/directory
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the child nodes of this node.
	 *
	 * @return the child nodes directly underneath this node.
	 */
	public Collection<FileNode> getChildren() {
		return this.children.values();
	}

	/**
	 * Return this node's parent.
	 * 
	 * @return the parent
	 */
	public FileNode getParent() {
		return parent;
	}

	/**
	 * Set this node's parent to p.
	 * 
	 * @param p
	 *            the parent to set
	 */
	public void setParent(FileNode p) {
		this.parent = p;
	}

	/**
	 * Add childNode, representing a file or directory named name, as a child of
	 * this node.
	 * 
	 * @param name
	 *            the name of the file or directory
	 * @param childNode
	 *            the node to add as a child
	 */
	public void addChild(String name, FileNode childNode) {
		this.children.put(name, childNode);
	}

	/**
	 * Return whether this node represents a directory.
	 * 
	 * @return whether this node represents a directory.
	 */
	public boolean isDirectory() {
		return this.type == FileType.DIRECTORY;
	}

	/**
	 * Return whether this node represents a file.
	 * 
	 * @return whether this node represents a file.
	 */
	public boolean isFile() {
		return this.type == FileType.FILE;
	}

	/**
	 * Return a string representation of just the name of the current tree node
	 * 
	 * @return (type String)string representation of just the name of the
	 *         current tree node.
	 */
	// I made this method just to check if the findChild() method was working
	// correctly
	public String toString() {
		return this.name + " : " + this.type;
	}

	

}