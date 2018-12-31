package com.LSC.Homework2;

import java.util.Scanner;

public class Homework2
{

	public static void main(String[] args)
	{
		RedBlackTree<Integer> RBtree = new RedBlackTree<>();
		RBTreeDisplay d = new RBTreeDisplay(RBtree);
		Scanner sc = new Scanner(System.in);
		String input = null;
		while(true)
		{
			if(RBtree.TreeRoot == null)
			{
				System.out.println("input 'i' to insert \n"
						+ "or input 'p' to create a RB tree with a prepared set\n"
						+ "or input 'r' to create a RB tree randomly:");
				input = sc.nextLine();
			}
			else
			{
				System.out.println("input i to insert or input d to delete\n");
				input = sc.nextLine();
			}
			
			if(input.equals("p") && RBtree.TreeRoot == null)
			{
				int[] a = {34,23,12,43,1,45,9,13,25,19,100,200,300,400,143,234,674,745,345};
				for(int i = 0; i< a.length; i++)
					RBtree.insert(a[i]);
				d.repaint();
			}
			else if(input.equals("r") && RBtree.TreeRoot == null)
			{
				double[] rt = new double[20];
				int[] r =  new int[20];
				for(int i = 0; i < 20; i++)										
				{
					rt[i] = 500*Math.random();
					rt[i] = (double)((int)(rt[i]*100)) / 100;	
					r[i] = (int) rt[i];
				}
				for(int i = 0; i< r.length; i++)
					RBtree.insert(r[i]);
				d.repaint();
			}
			else
			{
				if(input.equals("i"))
				{
					while(true)
					{
						System.out.println("input integer element to insert,\n "
								+ "or input 'b' to choose delete or insert\n"
								+ "or input 'clr' to delete whole tree:");
						input = sc.nextLine();
						if(input.equals("b"))
							break;
						else if(input.equals("clr"))
						{
							RBtree.TreeRoot = null;
							d.repaint();
							break;
						}
						else
						{
							try
							{
								int temp = Integer.parseInt(input);
								RBtree.insert(temp);
								d.repaint();
							}catch(NumberFormatException e)
							{
								System.out.println("input type error!");
							}
						}
					}
				}
				else if(input.equals("d"))
				{
					while(true)
					{
						System.out.println("input integer element to delete,\n"
								+ " or input 'b' to choose delete or insert\n"
								+ "or input 'clr' to delete whole tree:");
						input = sc.nextLine();
						if(input.equals("b"))
							break;
						else if(input.equals("clr"))
						{
							RBtree.TreeRoot = null;
							d.repaint();
							break;
						}
						else
						{
							try
							{
								int temp = Integer.parseInt(input);
								RBtree.delete(temp);
								d.repaint();
								if(RBtree.TreeRoot == null)
									break;
							}catch(NumberFormatException e)
							{
								System.out.println("input type error!");
							}
						}
					}
				}
				else
				{
					System.out.println("input error!");
				}
			}
		}
		
		
	}

}
