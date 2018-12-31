package com.LSC.Homework2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RBTreeDisplay extends JPanel
{
	public int flag;
	public int WinWidth = 1600;
	public int WinHeight = 800;
	public int LROffset = 270;
	public int DownOffset = 75;
	public int nodeD = 50;
	public int levelOffset = 50;
	public RedBlackTree RBTree;
	public RBTreeDisplay(RedBlackTree RBTree)
	{
		this.RBTree = RBTree;
		JFrame f = new JFrame("RBTree");
		f.setLocation(200, 0);
		f.setSize( WinWidth,WinHeight);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.getContentPane().add(this);
		this.setBackground(Color.white);
		
		
	}
	/*public void paint1(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);		//…Ë÷√—’…´
		Font font = new Font("∫⁄ÃÂ", Font.PLAIN, 18);
		g2.setFont(font);
		if(flag == 1)
		{
			g2.setColor(Color.red);
			g2.fillOval(340, 90, 60, 60);
			g2.setColor(Color.BLACK);
			g2.drawString("LSC",350, 120 + 5);
		}//ª≠‘≤
		else
		{
			
			g2.setColor(Color.BLACK);
			g2.fillOval(100, 20, 50, 50);
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(420, 50, 150, 20);
			g2.setColor(Color.CYAN);
			g2.fillOval(370, 20, 50, 50);
		}
	}*/
	
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		RBTreePaint(RBTree.TreeRoot, g2, WinWidth/2-25, 0, 0);
		
	}
	private void RBTreePaint(RedBlackTreeNode node, Graphics2D g, int x, int y, int level)
	{
		if(node != null)
		{
			int nodeR = nodeD/2;
			int nextLNodeX = x - LROffset +level*levelOffset;
			int nextRNodeX = x + LROffset - level*levelOffset;
			int nextNodeY = y + DownOffset;
			if(node.isLeaf ==false)
			{
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(2));
				if(node.LChild.isLeaf == false)
					g.drawLine(nextLNodeX + nodeR, nextNodeY + nodeR, x + nodeR , y + nodeR);
				if(node.RChild.isLeaf == false)
					g.drawLine(nextRNodeX + nodeR, nextNodeY + nodeR, x + nodeR, y + nodeR);
				if(node.getColor().equals("RED"))
					g.setColor(Color.RED);
				else
					g.setColor(Color.BLACK);
				g.fillOval(x, y, nodeD - level, nodeD - level);
				g.setColor(Color.GRAY);
				Font font = new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 17);
				g.setFont(font);
				g.drawString(node.nodeValue.toString(), x + nodeR - 10, y+ nodeR);
				
			}
			else
			{
				/*g.setColor(Color.BLACK);
				g.fillRect(x, y + nodeR, nodeD, nodeD/2);*/
				
			}
			RBTreePaint(node.LChild, g, nextLNodeX,  nextNodeY, level + 1);
			RBTreePaint(node.RChild, g, nextRNodeX, nextNodeY, level + 1);
		}
	}
}
