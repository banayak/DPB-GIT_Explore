package com.mbusa.dpb.common.helper;


import java.util.ArrayList;
import java.util.Collections;


/*********************************************************************************************
 * Project Name	: DPB
 * Module Name	: 
 * Program Name	: 
 * Description	:  
 *
 * Created By	: SYNTEL 		Date: 		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name							Date				Purpose
 * -------------------------------------------------------------------------------------------
 * SYNTEL						Aug 5, 2011			First Draft
 *
 ********************************************************************************************/

/**
 * 
 * 
 * @author SYNTEL
 * @version 1.0
 */
public class TreeNode implements Comparable<TreeNode>{
	private int index;
	private Tree tree;
	private MenuNode menuNode;
	private ArrayList data;
	private ArrayList<TreeNode> children;
	private ArrayList<Integer> immchildren;
	

	/**
	 * 
	 * @param key
	 */
	public TreeNode(MenuNode key) {
		this(key, new ArrayList(3));
	}

	/**
	 * 
	 * @param menuNode
	 * @param data
	 */
	public TreeNode(MenuNode menuNode, ArrayList data) {
		this.menuNode = menuNode;
		this.data = data;
		children = new ArrayList<TreeNode>();
		immchildren = new ArrayList<Integer>();

	}

	/**
	 * 
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 
	 * 
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 
	 * 
	 * @param tree
	 */
	public void setTree(Tree tree) {
		this.tree = tree;
	}

	/**
	 * 
	 * 
	 * @return tree
	 */
	public Tree getTree() {
		return tree;
	}

	

	/**
	 * 
	 * 
	 * @param index1
	 * @return object
	 */
	public Object getData(int index1) {
		return data.get(index1);
	}

	/**
	 * 
	 * 
	 * @param data
	 */
	public void setData(ArrayList data) {
		this.data = data;
	}

	/**
	 * 
	 * 
	 * @return children
	 */
	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	/**
	 * 
	 * 
	 * @return has children
	 */
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	
	/**
	 * 
	 * 
	 * @param node
	 */
	public void addChild(TreeNode node) {
		if (node != null && tree != null) {
			node.setIndex(tree.getNextNodeIndex());
			node.setTree(tree);
		}
		children.add(node);
		Collections.sort(children);
	}
	
	public boolean isImmChild(Integer node) {
		if(immchildren.contains(node)) 
			return true;
		else 
			return false;
	}
	
	
	public void addImmChild(Integer node) {
		if(immchildren!=null){
			immchildren.add(node);
		}else{
			immchildren = new ArrayList<Integer>();
			immchildren.add(node);
		}
		Collections.sort(immchildren);
	}
	
	
	/**
	 * @return the menuNode
	 */
	public MenuNode getMenuNode() {
		return menuNode;
	}

	/**
	 * @param menuNode the menuNode to set
	 */
	public void setMenuNode(MenuNode menuNode) {
		this.menuNode = menuNode;
	}

	public int compareTo(TreeNode o) {
		if(this.menuNode.getSequence()>o.menuNode.getSequence())
			return 1;
		else if(this.menuNode.getSequence() == o.menuNode.getSequence())
			return 0;
		else
			return -1;
	}
	
	

	
}
