package com.LSC.Homework2;

import java.util.Scanner;

public class Question8
{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int heapNum = 0;
		while(true)
		{
			System.out.println("Input the number of the heaps:");
			heapNum = sc.nextInt();
			int[] heap = new int[heapNum];
			int[][] sum = new int[heapNum][heapNum + 1];
			int temp = 0;
			System.out.println("Input "+ heapNum +" amount of every heap:" );
			for(int i = 0; i < heap.length; i++)
			{
				heap[i] = sc.nextInt();
			}
			for(int i = 1; i <= heapNum; i++)		//����ʯ�Ӻϲ���i = 1һ�Ѻϲ���i = 2���Ѻϲ���i=3���Ѻϲ����������heapNum�Ѻϲ���
			{
				for(int j = 0; j <= heapNum - 1; j++)		//�ӵڼ���ʯ�ӿ�ʼ�ϲ���0�ӵ�һ�ѿ�ʼ�ϲ�i�ѣ�1�ӵڶ��ѿ�ʼ�ϲ�i�ѡ�����
				{
					if(i == 1)	//�ϲ�һ��ʯ�ӣ�����Ϊ0
					{
						sum[j][i] = 0;
					}
					else if(i == 2)
					{
						sum[j][i] = heap[j] + heap[(j +1)%heapNum];	//�ϲ�����ʯ�ӣ�����Ϊ����ʯ�ӵĸ�����
					}
					else	//�ϲ���������ʯ��
					{
						int leftBondary = j;
						int rightBondary =( j + i - 1)%heapNum;
						int count1 = 0;
						int count2 = 1;
						int minPrice = Integer.MAX_VALUE;
						int cutPoint = (leftBondary + 1)%heapNum;
						int totalSum = 0;
						for(int m = j; ; m = (m + 1)%heapNum)
						{
							totalSum+=heap[m];
							if(m == (j + i - 1)%heapNum)
								break;
						}
						while(cutPoint != (rightBondary + 1)%heapNum)
						{
							count1 =  0;
							count2 = 1;
							for(int k = leftBondary; k != cutPoint; k = (k + 1)%heapNum)
							{
								count1++;
							}
							for(int k = cutPoint; k != rightBondary; k = (k + 1)%heapNum)
							{
								count2++;
							}
							minPrice = Math.min(minPrice, sum[leftBondary][count1] + sum[cutPoint][count2] +totalSum);
							cutPoint = (cutPoint + 1)%heapNum; 
						}
						sum[j][i] = minPrice;
					}
						
				}//for j
			}//for i
			int totalMinPrice = Integer.MAX_VALUE;
			for(int i = 0; i <heapNum; i++)
			{
				for(int j = 0; j<=heapNum; j++)
				{
					System.out.print(sum[i][j] + " ");
				}
				System.out.println();
			}
			for (int i = 0; i < heapNum; i++)
			{
				totalMinPrice = Math.min(totalMinPrice, sum[i][heapNum]);
			}
			System.out.println("totalMinPrice = " + totalMinPrice);

		}
	}

}
