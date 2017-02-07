/*********************************************************************************************
 * Project Name	: 
 * Module Name	: 
 * Program Name	: 
 * Description	:  
 *
 * Created By	: SYNTEL 		Date: 		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name		Jeevan					Date				Purpose
 * -------------------------------------------------------------------------------------------
 * SYNTEL									First Draft
 *
 ********************************************************************************************/
package com.mbusa.dpb.common.helper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;




/*********************************************************************************************
 * Project Name	: MBUSA
 * Module Name	: 
 * Program Name	: Tree.java
 * Description	:  
 *
 * Created By	: SYNTEL 		Date: 		Version: 1.0
 * *******************************************************************************************
 * Modification Details:
 *
 * Name	:Jeevan						Date				Purpose
 * -------------------------------------------------------------------------------------------
 * SYNTEL								First Draft
 *
 ********************************************************************************************/

/**
 * 
 * 
 * @author SYNTEL 
 * @version 1.0
 */
public class Tree{
	
	ArrayList<TreeNode> top = new ArrayList<TreeNode>();
	private int nodeCounter = 0;
	List<TreeNode> all = new ArrayList<TreeNode>();
	int i=0;


	/**
	 * 
	 * 
	 * @return tree
	 */
	public ArrayList<TreeNode> getChildren() { return top; }

	
	
	/**
	 * 
	 * 
	 * @param node
	 */
	public void addChild(TreeNode node)
	{
		if(node != null)
		{
			node.setIndex(getNextNodeIndex());
			node.setTree(this);
			top.add(node);
			Collections.sort(top);
		
		}
	}
	/**
	 * 
	 * 
	 * @return node counter
	 */
	public int getNextNodeIndex()
	{
		return ++nodeCounter;
	}
	/**
	 * 
	 * 
	 * @param node
	 */
	public void addChildrentoAll(TreeNode node){
		if(node.hasChildren()){
			ArrayList childs = node.getChildren();
			Iterator ite = childs.iterator();
			while(ite.hasNext()){
				TreeNode child = (TreeNode) ite.next();
				if(child.hasChildren()){
					addChildrentoAll(child);
				}
				else{
					all.add(child);
				}
			}
		}
		else{
			all.add(node);
		}
		
	}
	/**
	 * 
	 * 
	 * @param key
	 * @return tree node
	 */
	public TreeNode findNode(MenuNode key){
		TreeNode node = null;

		Enumeration children = Collections.enumeration(top);
		while(children.hasMoreElements())
		{
			node = (TreeNode) children.nextElement();
			addChildrentoAll(node);
		}
		
		for (Iterator iterator = all.iterator(); iterator.hasNext();) {
			node = (TreeNode) iterator.next();
			if(node.getMenuNode().getNodeID() == key.getParentNode())
			{
					return node;
			}

		}			
		

		return null;
	}
	
	public TreeNode findHirNode(MenuNode key){
		TreeNode node = null;

		Enumeration children = Collections.enumeration(top);
		while(children.hasMoreElements())
		{
			node = (TreeNode) children.nextElement();
			addChildrentoAll(node);
		}
		
		for (Iterator iterator = all.iterator(); iterator.hasNext();) {
			node = (TreeNode) iterator.next();
			if(node.getMenuNode().getNodeID() == key.getParentNode())
			{
				if(node.isImmChild(Integer.valueOf(key.getNodeID()))){
					System.out.println("=========humm found duplicate");
				}else{
					node.addImmChild(key.getNodeID());
					return node;
				}
			}

		}			
		

		return null;
	}
	
	
	/**
	 * 
	 * 
	 * @param tree
	 * @return output tree
	 */
	public String displayTree(Tree tree,String selectedMenu,String contextPath){
		String output ="";
		ArrayList nodes = tree.getChildren();
		Iterator ite = nodes.iterator();
	
	
		while(ite.hasNext()){
			TreeNode node = (TreeNode)ite.next();
			if(	node.getMenuNode().getNodeID()== 1) /* Jeevan Changes Strats Here */
			{
				//on="{0}"+node.getMenuNode().getNodeID();
				if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
						if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
						output += "<ul><li class="+" {"+i+"} "+"><h2><a title='"+ node.getMenuNode().getMouseOverText()+"'  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li></ul>";
						else
							output += "<ul><li class="+" {"+i+"} "+"><h2><a  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li></ul>";
						i++;
					}
					else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
						if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
						output += "<ul><li class='"+"on"+"'><h2><a title='"+ node.getMenuNode().getMouseOverText()+"'  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li></ul>";
						else
							output += "<ul><li class='"+"on"+"'><h2><a   href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li></ul>";
					}
					else{
						output += displayChild(node,selectedMenu,contextPath);
					}
			}
			
			if(node.getMenuNode().getNodeID()!= 1)
			{
				//on="MENIITEM"+node.getMenuNode().getNodeID();
				
				if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
					if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
					output += "<li class="+"on"+"><h2><a title='"+ node.getMenuNode().getMouseOverText()+"'  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li>";
					else
						output += "<li class='"+"on"+"'><h2><a  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li>";
				}
				else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
					if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
					output += "<li class='"+"on"+"'><h2><a title='"+ node.getMenuNode().getMouseOverText()+"'  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li>";
					else
						output += "<li class='"+"on"+"'><h2><a   href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2></li>";
				}
				else{
					output += displayChild(node,selectedMenu,contextPath);
				}
				
			}
			
			
			
			
		}
		return output;
	}
	/**
	 * 
	 * 
	 * @param node
	 * @return output child tree
	 */
	public String displayChild(TreeNode node,String selectedMenu,String contextPath){
		String output="";
		
		if(node.hasChildren()){
			if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
			output += "<li title='"+node.getMenuNode().getMouseOverText()+"'>"+node.getMenuNode().getLabelName()+"<ul>";
			else{
				if(node.getMenuNode().getParentNode() != 0)
				{
					output += "<ul><li class="+" {"+i+"} "+" ><h2><a  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2><ul>"; /*Jeevan Changes */
				}
				else
				{
					output += "<ul><li class="+" {"+i+"} "+" ><h2><a  href='#"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></h2><ul>"; /*Jeevan Changes */
				}				
				i++;
			}
			ArrayList childs = node.getChildren();
			Iterator it = childs.iterator();
			while(it.hasNext()){
				TreeNode child = (TreeNode)it.next();
				output += displayChild(child,selectedMenu,contextPath);
			}
			output+="</ul></li></ul>";
		}
		else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
			if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
			output += "<li><a title='"+ node.getMenuNode().getMouseOverText()+"'  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></li>";
			else {
				if(selectedMenu!=null && node.getMenuNode().getLabelName().equals(selectedMenu)){
					output += "<li style='background-color: #F2F2F2;'><a   href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></li>";	
				}else{
				output += "<li style='background-color: #FFFFFF;'><a   href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></li>";
				}
				
			}
		}
		else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
			if(node.getMenuNode().getMouseOverText()!=null && node.getMenuNode().getMouseOverText().trim().length()>0)
			output += "<li style='background-color: #F2F2F2;'><a title='"+ node.getMenuNode().getMouseOverText()+"'  href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></li>";
			else{
				if(selectedMenu!=null && node.getMenuNode().getLabelName().equals(selectedMenu)){
					output += "<li style='background-color: #F2F2F2;'><a   href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></li>";	
					
				}else{
				output += "<li  style='background-color: #FFFFFF;'><a   href='"+node.getMenuNode().getMethodName()+"'>"+node.getMenuNode().getLabelName()+"</a></li>";
				}
			}
		}
		return output;
	}

	// Methods for Tree view display
	/**
	 * 
	 * 
	 * @param tree
	 * @return output tree
	 */
	public String displayTreeView(Tree tree){
		String output ="";
		ArrayList nodes = tree.getChildren();
		Iterator ite = nodes.iterator();
		while(ite.hasNext()){
			TreeNode node = (TreeNode)ite.next();
			if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
				output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
			}
			else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
				output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
			}
			else{
				output += displayChildView(node);
			}
		}
		return output;
	}
	/**
	 * 
	 * 
	 * @param node
	 * @return output child tree
	 */
	public String displayChildView(TreeNode node){
		String output="";
		
		if(node.hasChildren()){
			output += "<li>"+node.getMenuNode().getLabelName()+"<ul>";
			ArrayList childs = node.getChildren();
			Iterator it = childs.iterator();
			while(it.hasNext()){
				TreeNode child = (TreeNode)it.next();
				output += displayChildView(child);
			}
			output+="</ul></li>";
		}
		else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
			output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
		}
		else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
			output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
		}
		return output;
	}

	// Methods for Tabular view display
	/**
	 * 
	 * 
	 * @param tree
	 * @return output tree
	 */
	public String displayTabTreeView(Tree tree){
		String output ="";
		ArrayList nodes = tree.getChildren();
		Iterator ite = nodes.iterator();
		String color1=null;
		int[] count= { 1 };
		while(ite.hasNext()){
			TreeNode node = (TreeNode)ite.next();
			color1=( (count[0]%2)!=0) ?"sdbTdBkgrnd01" : "sdbTdBkgrnd02";
		
			if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
				//output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
				output += "<tr class=\""+color1+"\" >"+node.getMenuNode().getLabelName()+"</tr>";
				count[0]=count[0]+1;
			}
			else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
				//output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
				output += "<tr class=\""+color1+"\" >"+node.getMenuNode().getLabelName()+"</tr>";
				count[0]=count[0]+1;
			}
			else{
				output += displayTabChildView(node,count);
			}
		}
		return output;
	}
	/**
	 * 
	 * 
	 * @param node
	 * @return output child tree
	 */
	public String displayTabChildView(TreeNode node,int[] count){
		String output="";
		String color1=null;
		color1=( (count[0]%2)!=0) ?"sdbTdBkgrnd01" : "sdbTdBkgrnd02";
		if(node.hasChildren()){
			//output += "<li>"+node.getMenuNode().getLabelName()+"<ul>";
			output += "<tr class=\""+color1+"\" >"+node.getMenuNode().getLabelName()+"</tr>";
			count[0]=count[0]+1;
			ArrayList childs = node.getChildren();
			Iterator it = childs.iterator();
			while(it.hasNext()){
				TreeNode child = (TreeNode)it.next();
				output += displayTabChildView(child,count);
			}
			//output+="</ul></li>";
		}
		else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()!=0){
			//output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
			output += "<tr class=\""+color1+"\" >"+node.getMenuNode().getLabelName()+"</tr>";
			count[0]=count[0]+1;
		}
		else if(!node.hasChildren() && node.getMenuNode().getMethodName().length()==0){
			//output += "<li>"+node.getMenuNode().getLabelName()+"</li>";
			output += "<tr class=\""+color1+"\" >"+node.getMenuNode().getLabelName()+"</tr>";
			count[0]=count[0]+1;
		}
		return output;
	}

	
}
