package com.LSC.Homework2;


public class RedBlackTree<T extends Comparable<T>>
{
	public RedBlackTreeNode<T> TreeRoot;
	
	public RedBlackTree()
	{
		TreeRoot = null;
	}
	
	public void print()
	{
		System.out.println("中序遍历:");
		inOrder(TreeRoot);
		/*System.out.println("\n先序遍历:");
		preOrder(TreeRoot);*/
	}
	
	private void preOrder(RedBlackTreeNode<T> node)
	{
		if(node != null)
		{
			if(node.isLeaf == false)
				System.out.print(node.toString());
			preOrder(node.LChild);
			preOrder(node.RChild);
		}
	}

	private void inOrder(RedBlackTreeNode<T> node)
	{
		if(node != null)
		{
			inOrder(node.LChild);
			if(node.isLeaf == false)
				System.out.print(node.toString());
			inOrder(node.RChild);
		}
	}
	
	public RedBlackTreeNode<T> search(T value)//根节点的情况已经在插入程序中判断过，所以这里不用判断是否为空树
	{																			//不过最好还是统一
		RedBlackTreeNode<T> p = TreeRoot;
		RedBlackTreeNode<T> pre = null;
		while(true)
		{
			if(value.compareTo(p.nodeValue) == 0)
			{
				return p;
			}
			else if(value.compareTo(p.nodeValue) == -1)
			{
				pre = p;
				p = p.LChild;
				if(p.isLeaf == true)
					return pre;
			}
			else if(value.compareTo(p.nodeValue) == 1)
			{
				pre = p;
				p = p.RChild;
				if(p.isLeaf == true)
					return pre;
			}
		}
	}
	
	private void leftRotate(RedBlackTreeNode<T> node)
	{
		RedBlackTreeNode<T> p = node.RChild;
		if(node.Parent != null)
		{
			if(node.Parent.LChild == node)
			{
				node.Parent.LChild = p;
				p.Parent = node.Parent;
			}
			else
			{
				node.Parent.RChild = p;
				p.Parent = node.Parent;
			}
		}
		else
		{
			TreeRoot = p;
			p.Parent = null;
		}
		node.RChild = node.RChild.LChild;
		p.LChild = node;
		
		node.RChild.Parent = node;			//注意修改父节点指针！！！！！
		node.Parent = p;
	}

	private void rightRotate(RedBlackTreeNode<T> node)
	{
		RedBlackTreeNode<T> p = node.LChild;
		if(node.Parent != null)
		{
			if(node.Parent.LChild == node)
			{
				node.Parent.LChild = p;
				p.Parent = node.Parent;
			}
			else
			{
				node.Parent.RChild = p;
				p.Parent = node.Parent;
			}
		}
		else
		{
			TreeRoot = p;
			p.Parent = null;
		}
		node.LChild = node.LChild.RChild;
		p.RChild = node;
		
		node.LChild.Parent = node;		
		node.Parent = p;
	}
	
	public void insert(T value)
	{
		if(TreeRoot == null)		//树为空树
		{
			TreeRoot = new RedBlackTreeNode<T>(value, "BLACK");
			TreeRoot.LChild = new RedBlackTreeNode<T>(TreeRoot);
			TreeRoot.RChild = new RedBlackTreeNode<T>(TreeRoot);
		}
		else									//树不为空
		{
			RedBlackTreeNode<T> node = null;
			node = search(value);
			if(value.compareTo(node.nodeValue) == 0)
			{
				System.out.println("插入元素已存在，无法插入");
				return;
			}
			else
			{
				RedBlackTreeNode<T> newNode = new RedBlackTreeNode<T>(value, "RED");
				newNode.LChild = new RedBlackTreeNode<T>(newNode);
				newNode.RChild = new RedBlackTreeNode<T>(newNode);
				newNode.Parent = node;
				if(value.compareTo(node.nodeValue) == -1)
				{
					node.LChild = newNode;
				}
				else
				{
					node.RChild = newNode;
				}
					//先把节点接上，相等情况之前已经判断过
				insertAdjust(newNode);
			}
		}
	}
	
	private void insertAdjust(RedBlackTreeNode<T> newNode)
	{
		RedBlackTreeNode<T> parentNode = newNode.Parent;
		if(parentNode.getColor().equals("BLACK"))		//插入节点（待调整节点）的父节点为黑色
		{
			return;
		}
		else		//插入节点的父节点为红色,若父节点为红色则一定有祖父节点
		{																		//RB树种默认叶结点（null节点）为黑色
			RedBlackTreeNode<T> grandNode = newNode.Parent.Parent;																			
				if(grandNode.LChild.getColor().equals("RED") && 	grandNode.RChild.getColor().equals("RED"))	//祖父节点的两个孩子都为红色
				{
					grandNode.LChild.setColor("BLACK");
					grandNode.RChild.setColor("BLACK");
					if(grandNode.Parent == null)
						return;
					else
					{
						grandNode.setColor("RED");
						insertAdjust(grandNode);
					}
				}
				else	//一个孩子为红一个孩子为黑（此时一定是插入节点的父节点为红，叔父节点为黑）
				{
					if(grandNode.LChild.LChild == newNode)
					{
						grandNode.setColor("RED");
						parentNode.setColor("BLACK");
						rightRotate(grandNode);
						
					}
					else if(grandNode.LChild.RChild == newNode)
					{
						leftRotate(parentNode);
						grandNode.setColor("RED");
						newNode.setColor("BLACK");			//注意这里转过之后是newNode
						rightRotate(grandNode);
					}
					else if(grandNode.RChild.RChild == newNode)
					{
						grandNode.setColor("RED");
						parentNode.setColor("BLACK");
						leftRotate(grandNode);
					}
					else if(grandNode.RChild.LChild == newNode)
					{
						rightRotate(parentNode);
						grandNode.setColor("RED");
						newNode.setColor("BLACK");
						leftRotate(grandNode);
					}
				}
		}
	}
	
	public void delete(T value)
	{
		RedBlackTreeNode<T> dNode = null;
		dNode = search(value);
		if(dNode.nodeValue.compareTo(value) != 0)
		{
			System.out.println("删除元素不存在");
			return;
		}
		else
		{
			if(dNode.LChild.isLeaf == false && dNode.RChild.isLeaf == false)	//待删除节点有两个子树
			{
				T sValue = successor(dNode);
				delete(successor(dNode));				//用该节点的后继的值取代该节点，递归删除该节点的后继
				dNode.nodeValue = sValue;
			}
			else		//待删除节点只有一个子树或为叶结点（不是值为null的节点)
			{
				if(dNode.getColor().equals("RED"))		//待删除节点为红节点
				{
					if(dNode.Parent.LChild == dNode)//删除节点是其父节点的左子
					{
						if(dNode.LChild.isLeaf == false)
						{
							dNode.Parent.LChild = dNode.LChild;
							dNode.LChild.Parent = dNode.Parent;
						}
						else if(dNode.RChild.isLeaf == false)
						{
							dNode.Parent.LChild = dNode.RChild;
							dNode.RChild.Parent = dNode.Parent;
						}
						else	//删除节点为红色叶结点
						{
							dNode.Parent.LChild = dNode.LChild;
						}
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
					else		//删除节点是其父节点的右子
					{
						if(dNode.LChild.isLeaf == false)
						{
							dNode.Parent.RChild = dNode.LChild;
							dNode.LChild.Parent = dNode.Parent;
							
						}
						else if(dNode.RChild.isLeaf == false)
						{
							dNode.Parent.RChild = dNode.RChild;
							dNode.RChild.Parent = dNode.Parent;
						}
						else	//删除节点为红色叶结点
						{
							dNode.Parent.RChild = dNode.LChild;
						}
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
				}
				else		//待删除节点为黑节点，此时若该节点只有一个子树，则该子树的根一定为红节点
				{
					if(TreeRoot == dNode)		//删除根节点，并且没有后继
					{
						if(dNode.LChild.isLeaf == false)//根节点有前驱
						{
							dNode.LChild.setColor("BLACK");
							TreeRoot = dNode.LChild;
							dNode.LChild.Parent = null;//这里应该是等于null，和旋转函数中保持一致
							return;
						}
						TreeRoot = null;		//只有一个根节点
						return;
					}
					if(dNode.LChild.isLeaf == false)	//有左子
					{
						if(dNode.Parent.LChild == dNode)
						{
							dNode.Parent.LChild = dNode.LChild;
							dNode.LChild.Parent = dNode.Parent;
						}
						else
						{
							dNode.Parent.RChild = dNode.LChild;
							dNode.LChild.Parent = dNode.Parent;
						}
						dNode.LChild.setColor("BLACK");
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
					else if(dNode.RChild.isLeaf == false)	//有右子
					{
						if(dNode.Parent.LChild == dNode)
						{
							dNode.Parent.LChild = dNode.RChild;
							dNode.RChild.Parent = dNode.Parent;
						}
						else
						{
							dNode.Parent.RChild = dNode.RChild;
							dNode.RChild.Parent = dNode.Parent;
						}
						dNode.RChild.setColor("BLACK");
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
					else		//待删除节点为黑色叶结点
					{
						if(dNode.Parent.LChild == dNode)
						{
							dNode.Parent.LChild = dNode.LChild;
							deleteBlackLeafAdjust(dNode.Parent, "LEFT");		//以待删除节点的父节点作为待调整节点
						}
						else
						{
							dNode.Parent.RChild = dNode.LChild;
							deleteBlackLeafAdjust(dNode.Parent, "RIGHT");		
						}
						
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
				}
			}
		}
		System.gc();
	}

	private void deleteBlackLeafAdjust(RedBlackTreeNode<T> node, String side)
	{
		if(node.LChild.getColor().equals("RED"))	//删除节点的兄弟为红色(则父节点黑色)
		{
			node.setColor("RED");
			node.LChild.setColor("BLACK");
			rightRotate(node);
			deleteBlackLeafAdjust(node, side);		//将其转变为删除节点的兄弟节点为黑色的情况，递归进行调整
		}
		else if(node.RChild.getColor().equals("RED"))		//这里最好加上side的判断
		{
			node.setColor("RED");
			node.RChild.setColor("BLACK");
			leftRotate(node);
			deleteBlackLeafAdjust(node, side);
		}
		else	//删除节点的兄弟节点为黑色
		{
			if(side.equals("LEFT"))		//删除节点为其父节点的左子
			{
				if(node.RChild.RChild.getColor().equals("RED"))		//删除节点的兄弟节点的远端子树根节点为红色
				{
					node.RChild.RChild.setColor("BLACK");
					if(node.getColor().equals("RED"))	//交换父节点与兄弟节点的颜色
					{
						node.setColor("BLACK");
						node.RChild.setColor("RED");
					}
					leftRotate(node);
					return;
				}
				else if(node.RChild.LChild.getColor().equals("RED"))	//删除节点的兄弟节点的近端子树根节点为红色
				{
					node.RChild.setColor("RED");
					node.RChild.LChild.setColor("BLACK");
					rightRotate(node.RChild);					//将其转变为远端子树的根节点为红色
					
					node.RChild.RChild.setColor("BLACK");
					if(node.getColor().equals("RED"))	//交换父节点与兄弟节点的颜色
					{
						node.setColor("BLACK");
						node.RChild.setColor("RED");
					}
					leftRotate(node);
					return;
				}
				else		//删除节点的兄弟节点的子树都为黑色
				{
					if(node.getColor().equals("RED"))		//兄弟节点为黑，且兄弟节点的子树都为黑，父节点为红
					{
						node.setColor("BLACK");
						node.RChild.setColor("RED");
						return;
					}
					else		//兄弟节点为黑，兄弟节点的子树都为黑，父节点也为黑
					{
						node.RChild.setColor("RED");
						if(node.Parent == null)		//若递归调整到根节点，调整结束
							return;
						else
						{
							if(node.Parent.LChild == node)
								deleteBlackLeafAdjust(node.Parent, "LEFT");
							else
								deleteBlackLeafAdjust(node.Parent, "RIGHT");
						}
					}
				}
			}
			else		//删除节点为其父节点的右子
			{
				if(node.LChild.LChild.getColor().equals("RED"))		//删除节点的兄弟节点的远端子树根节点为红色
				{
					node.LChild.LChild.setColor("BLACK");
					if(node.getColor().equals("RED"))
					{
						node.setColor("BLACK");
						node.LChild.setColor("RED");
					}
					rightRotate(node);
					return;
					
				}
				else if(node.LChild.RChild.getColor().equals("RED"))	//删除节点的兄弟节点的近端子树根节点为红色
				{
					node.LChild.setColor("RED");
					node.LChild.RChild.setColor("BLACK");
					leftRotate(node.LChild);
					
					node.LChild.LChild.setColor("BLACK");
					if(node.getColor().equals("RED"))	//交换父节点与兄弟节点的颜色
					{
						node.setColor("BLACK");
						node.LChild.setColor("RED");
					}
					rightRotate(node);
					return;
				}
				else		//删除节点的兄弟节点的子树都为黑色（此时兄弟节点的子树只能都为叶结点）
				{
					if(node.getColor().equals("RED"))//兄弟节点为黑，且兄弟节点的子树都为黑，父节点为红
					{
						node.setColor("BLACK");
						node.LChild.setColor("RED");
						return;
					}
					else	//兄弟节点为黑，兄弟节点的子树都为黑，父节点也为黑
					{
						node.LChild.setColor("RED");
						if(node.Parent == null)
							return;
						else
						{
							if(node.Parent.LChild == node)
								deleteBlackLeafAdjust(node.Parent, "LEFT");
							else
								deleteBlackLeafAdjust(node.Parent, "RIGHT");
						}
					}
				}
			}
		}
		
	}

	private T successor(RedBlackTreeNode<T> dNode)
	{
		RedBlackTreeNode<T> p = null;
		p = dNode.RChild;
		while(p.LChild.isLeaf == false)
		{
			p = p.LChild;
		}
		return p.nodeValue;
	}
}
