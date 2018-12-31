package com.LSC.Homework2;


public class RedBlackTreeNode<T extends Comparable<T>>
{
	public T nodeValue;
	public RedBlackTreeNode<T> LChild;
	public RedBlackTreeNode<T> RChild;
	public RedBlackTreeNode<T> Parent;
	private String color;
	public boolean isLeaf;
	public RedBlackTreeNode(RedBlackTreeNode<T> Parent)	//创建叶结点
	{
		nodeValue = null;
		LChild = RChild  = null;
		this.Parent = Parent;
		color = "BLACK";
		isLeaf = true;
	}
	public RedBlackTreeNode(T nodeValue ,String color)			//创建普通节点
	{
		super();
		this.nodeValue = nodeValue;
		this.color = color;
		LChild = RChild = Parent = null;
		isLeaf = false;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		if(color == "BLACK" || color == "RED")
			this.color = color;
		else
			System.out.println("input color error!");
			return;
	}
	
	public String toString()
	{
		return "[Value=" + nodeValue + " Color=" + color + "]  ";
	}
}
