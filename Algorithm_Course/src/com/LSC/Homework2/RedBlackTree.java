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
		System.out.println("�������:");
		inOrder(TreeRoot);
		/*System.out.println("\n�������:");
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
	
	public RedBlackTreeNode<T> search(T value)//���ڵ������Ѿ��ڲ���������жϹ����������ﲻ���ж��Ƿ�Ϊ����
	{																			//������û���ͳһ
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
		
		node.RChild.Parent = node;			//ע���޸ĸ��ڵ�ָ�룡��������
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
		if(TreeRoot == null)		//��Ϊ����
		{
			TreeRoot = new RedBlackTreeNode<T>(value, "BLACK");
			TreeRoot.LChild = new RedBlackTreeNode<T>(TreeRoot);
			TreeRoot.RChild = new RedBlackTreeNode<T>(TreeRoot);
		}
		else									//����Ϊ��
		{
			RedBlackTreeNode<T> node = null;
			node = search(value);
			if(value.compareTo(node.nodeValue) == 0)
			{
				System.out.println("����Ԫ���Ѵ��ڣ��޷�����");
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
					//�Ȱѽڵ���ϣ�������֮ǰ�Ѿ��жϹ�
				insertAdjust(newNode);
			}
		}
	}
	
	private void insertAdjust(RedBlackTreeNode<T> newNode)
	{
		RedBlackTreeNode<T> parentNode = newNode.Parent;
		if(parentNode.getColor().equals("BLACK"))		//����ڵ㣨�������ڵ㣩�ĸ��ڵ�Ϊ��ɫ
		{
			return;
		}
		else		//����ڵ�ĸ��ڵ�Ϊ��ɫ,�����ڵ�Ϊ��ɫ��һ�����游�ڵ�
		{																		//RB����Ĭ��Ҷ��㣨null�ڵ㣩Ϊ��ɫ
			RedBlackTreeNode<T> grandNode = newNode.Parent.Parent;																			
				if(grandNode.LChild.getColor().equals("RED") && 	grandNode.RChild.getColor().equals("RED"))	//�游�ڵ���������Ӷ�Ϊ��ɫ
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
				else	//һ������Ϊ��һ������Ϊ�ڣ���ʱһ���ǲ���ڵ�ĸ��ڵ�Ϊ�죬�常�ڵ�Ϊ�ڣ�
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
						newNode.setColor("BLACK");			//ע������ת��֮����newNode
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
			System.out.println("ɾ��Ԫ�ز�����");
			return;
		}
		else
		{
			if(dNode.LChild.isLeaf == false && dNode.RChild.isLeaf == false)	//��ɾ���ڵ�����������
			{
				T sValue = successor(dNode);
				delete(successor(dNode));				//�øýڵ�ĺ�̵�ֵȡ���ýڵ㣬�ݹ�ɾ���ýڵ�ĺ��
				dNode.nodeValue = sValue;
			}
			else		//��ɾ���ڵ�ֻ��һ��������ΪҶ��㣨����ֵΪnull�Ľڵ�)
			{
				if(dNode.getColor().equals("RED"))		//��ɾ���ڵ�Ϊ��ڵ�
				{
					if(dNode.Parent.LChild == dNode)//ɾ���ڵ����丸�ڵ������
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
						else	//ɾ���ڵ�Ϊ��ɫҶ���
						{
							dNode.Parent.LChild = dNode.LChild;
						}
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
					else		//ɾ���ڵ����丸�ڵ������
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
						else	//ɾ���ڵ�Ϊ��ɫҶ���
						{
							dNode.Parent.RChild = dNode.LChild;
						}
						dNode.LChild = dNode.RChild = dNode.Parent = null;
					}
				}
				else		//��ɾ���ڵ�Ϊ�ڽڵ㣬��ʱ���ýڵ�ֻ��һ����������������ĸ�һ��Ϊ��ڵ�
				{
					if(TreeRoot == dNode)		//ɾ�����ڵ㣬����û�к��
					{
						if(dNode.LChild.isLeaf == false)//���ڵ���ǰ��
						{
							dNode.LChild.setColor("BLACK");
							TreeRoot = dNode.LChild;
							dNode.LChild.Parent = null;//����Ӧ���ǵ���null������ת�����б���һ��
							return;
						}
						TreeRoot = null;		//ֻ��һ�����ڵ�
						return;
					}
					if(dNode.LChild.isLeaf == false)	//������
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
					else if(dNode.RChild.isLeaf == false)	//������
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
					else		//��ɾ���ڵ�Ϊ��ɫҶ���
					{
						if(dNode.Parent.LChild == dNode)
						{
							dNode.Parent.LChild = dNode.LChild;
							deleteBlackLeafAdjust(dNode.Parent, "LEFT");		//�Դ�ɾ���ڵ�ĸ��ڵ���Ϊ�������ڵ�
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
		if(node.LChild.getColor().equals("RED"))	//ɾ���ڵ���ֵ�Ϊ��ɫ(�򸸽ڵ��ɫ)
		{
			node.setColor("RED");
			node.LChild.setColor("BLACK");
			rightRotate(node);
			deleteBlackLeafAdjust(node, side);		//����ת��Ϊɾ���ڵ���ֵܽڵ�Ϊ��ɫ��������ݹ���е���
		}
		else if(node.RChild.getColor().equals("RED"))		//������ü���side���ж�
		{
			node.setColor("RED");
			node.RChild.setColor("BLACK");
			leftRotate(node);
			deleteBlackLeafAdjust(node, side);
		}
		else	//ɾ���ڵ���ֵܽڵ�Ϊ��ɫ
		{
			if(side.equals("LEFT"))		//ɾ���ڵ�Ϊ�丸�ڵ������
			{
				if(node.RChild.RChild.getColor().equals("RED"))		//ɾ���ڵ���ֵܽڵ��Զ���������ڵ�Ϊ��ɫ
				{
					node.RChild.RChild.setColor("BLACK");
					if(node.getColor().equals("RED"))	//�������ڵ����ֵܽڵ����ɫ
					{
						node.setColor("BLACK");
						node.RChild.setColor("RED");
					}
					leftRotate(node);
					return;
				}
				else if(node.RChild.LChild.getColor().equals("RED"))	//ɾ���ڵ���ֵܽڵ�Ľ����������ڵ�Ϊ��ɫ
				{
					node.RChild.setColor("RED");
					node.RChild.LChild.setColor("BLACK");
					rightRotate(node.RChild);					//����ת��ΪԶ�������ĸ��ڵ�Ϊ��ɫ
					
					node.RChild.RChild.setColor("BLACK");
					if(node.getColor().equals("RED"))	//�������ڵ����ֵܽڵ����ɫ
					{
						node.setColor("BLACK");
						node.RChild.setColor("RED");
					}
					leftRotate(node);
					return;
				}
				else		//ɾ���ڵ���ֵܽڵ��������Ϊ��ɫ
				{
					if(node.getColor().equals("RED"))		//�ֵܽڵ�Ϊ�ڣ����ֵܽڵ��������Ϊ�ڣ����ڵ�Ϊ��
					{
						node.setColor("BLACK");
						node.RChild.setColor("RED");
						return;
					}
					else		//�ֵܽڵ�Ϊ�ڣ��ֵܽڵ��������Ϊ�ڣ����ڵ�ҲΪ��
					{
						node.RChild.setColor("RED");
						if(node.Parent == null)		//���ݹ���������ڵ㣬��������
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
			else		//ɾ���ڵ�Ϊ�丸�ڵ������
			{
				if(node.LChild.LChild.getColor().equals("RED"))		//ɾ���ڵ���ֵܽڵ��Զ���������ڵ�Ϊ��ɫ
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
				else if(node.LChild.RChild.getColor().equals("RED"))	//ɾ���ڵ���ֵܽڵ�Ľ����������ڵ�Ϊ��ɫ
				{
					node.LChild.setColor("RED");
					node.LChild.RChild.setColor("BLACK");
					leftRotate(node.LChild);
					
					node.LChild.LChild.setColor("BLACK");
					if(node.getColor().equals("RED"))	//�������ڵ����ֵܽڵ����ɫ
					{
						node.setColor("BLACK");
						node.LChild.setColor("RED");
					}
					rightRotate(node);
					return;
				}
				else		//ɾ���ڵ���ֵܽڵ��������Ϊ��ɫ����ʱ�ֵܽڵ������ֻ�ܶ�ΪҶ��㣩
				{
					if(node.getColor().equals("RED"))//�ֵܽڵ�Ϊ�ڣ����ֵܽڵ��������Ϊ�ڣ����ڵ�Ϊ��
					{
						node.setColor("BLACK");
						node.LChild.setColor("RED");
						return;
					}
					else	//�ֵܽڵ�Ϊ�ڣ��ֵܽڵ��������Ϊ�ڣ����ڵ�ҲΪ��
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
