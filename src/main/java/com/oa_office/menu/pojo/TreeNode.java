package com.oa_office.menu.pojo;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private String text ="未命名";
	private String iconCls ="x-fa fa-desktop";
	private String viewType;
	private boolean leaf = true;
	private boolean expanded = true;
	private boolean selectable = true;
    private List<TreeNode> children =new ArrayList<TreeNode>();
	
    
    public TreeNode() {}
    
    
    public static TreeNode MenuToTreeNode (Menu menu){
			TreeNode treeNode = new TreeNode();
			treeNode.setText(menu.getMenuname());
			treeNode.setIconCls(menu.getIconcls());
			treeNode.setViewType(menu.getMenuurl());
    	return treeNode;
    }
    public static TreeNode MenuToTreeNode (MenuChird menu){
		TreeNode treeNode = new TreeNode();
		treeNode.setLeaf(false);
		treeNode.setText(menu.getMenuname());
		treeNode.setIconCls(menu.getIconcls());
		treeNode.setViewType(menu.getMenuurl());
	return treeNode;
}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	

	public void setTreeNodes(List<TreeNode> children) {
		this.children = children;
	}


	


	public List<TreeNode> getChildren() {
		return children;
	}


	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}


	public boolean isExpanded() {
		return expanded;
	}


	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}


	public boolean isSelectable() {
		return selectable;
	}


	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	

}
