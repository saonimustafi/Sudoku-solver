import java.io.*;
import java.util.*;

class wrapperInt
{
	int value;	
}

class sudokuForwardCheck
{
	static int count=0;
	static boolean sudokuSolve(int[][] grid, boolean[][][] domain, PrintWriter output)
	{
		output.println("");
		output.println("STARTING SUDOKUSOLVE");
		wrapperInt row=new wrapperInt();  //default value of "value" =0
		wrapperInt col=new wrapperInt();
		if(!findUnassigned(grid,row,col)) //This is the function with reference variables in C++ geeks implementation. SO THIS REQUIRES OBJECT REFERENCE VARIABLES TO BE PASSED THROUGH
			return true;	
			
		boolean[][][] domaincpy=new boolean[9][9][9];
        int i,j;
        for(i=0;i<9;i++)
        {
        	for(j=0;j<9;j++)
        	{
        		domaincpy[i][j]=new boolean[9];
        	}
       	}
       	boolean[][][] domaincpy1=new boolean[9][9][9];
        
        for(i=0;i<9;i++)
        {
        	for(j=0;j<9;j++)
        	{
        		domaincpy1[i][j]=new boolean[9];
        	}
       	}
		int num;
		
		for(num=1;num<=9;num++)
		{			
			int rowValue=row.value, colValue=col.value;
			output.println("");
			output.print("At row="+rowValue+" col="+colValue+" num="+num+" Domain=");
			domaincpy=domain.clone();
			showDomain(domaincpy,rowValue,colValue,output);
			if(!domain[rowValue][colValue][num-1])
			{
				output.print(" Not in Domain ");
				continue;
			}
			
			output.println("");
			output.println("All domains here: (DOMAINCPY1)");
			for(i=0;i<9;i++)
			{
				for(j=0;j<9;j++)
				{
					domaincpy1[i][j]=domain[i][j].clone();
				}
			}
			
			showAllDomains(grid,domaincpy1,output);			
					
			if(!isSafeNext(grid,domain,rowValue,colValue,num,output))
			{
				
				output.println(" UNSAFE Peforming Backtrack. Domain before:");
				showAllDomains(grid,domain,output);
				domain=domaincpy1;
				output.println("");
				output.println("Domain now:");
				showAllDomains(grid,domain,output);
				output.println("DOMAINCPY1 now:");
				showAllDomains(grid,domaincpy1,output);
				continue;
			}
			
			output.println("");					
			output.println("Verifying domains:");
			showAllDomains(grid,domain,output);
			grid[rowValue][colValue]=num;
			output.print(" @Assigning "+num+" at row="+rowValue+" col="+colValue);			
			count++;
			
			output.println("");
			//output.println("DOMAIN CPY1:");
			output.println("DOMAIN after assigning:");
			showAllDomains(grid,domain,output);
			if(sudokuSolve(grid,domain,output))
				return true;
			grid[rowValue][colValue]=0; //Unassign it after backtracking from a wrong path
			output.println("");
			output.println("Unassigning ["+rowValue+"]["+colValue+"]");
			output.println("Previous domain:");
			showAllDomains(grid,domain,output);
			output.println("");
			output.print("Current domain:");
			domain=domaincpy1;
			showAllDomains(grid,domain,output);
			
		}
		return false;	
	}
	
	static void showAllDomains(int[][] grid, boolean[][][] domain, PrintWriter output)
	{
		int i=0,j,k;
		for(i=0;i<3;i++)
		{
			for(j=0;j<9;j++)
			{
				if(grid[i][j]>0)
					continue;			
				output.print("["+i+"]["+j+"]=");
				for(k=0;k<9;k++)
				{
					if(domain[i][j][k])
						output.print((k+1)+",");
				}
				output.print("  ");
			}
		output.println("");
		}
	}
	
	static void showDomain(boolean[][][] domain, int rowValue, int colValue, PrintWriter output)
	{
		int i;
		for(i=0;i<9;i++)
		{
			if(domain[rowValue][colValue][i])
				output.print((i+1)+",");
		}
	}
	
	static boolean isSafeNext(int[][] grid, boolean[][][] domain, int rowValue, int colValue, int num, PrintWriter output) //NO NEED OF WRAPPER FOR DOMAIN AS ARRAYS ARE PASSED BY REFERENCE
	{
		output.print("\nPERFORMING ISSAFENEXT\n");
		int i,j,r,c;		
		boolean[][][] domaincpy=new boolean[9][9][9];
        
        for(i=0;i<9;i++)
        {
        	for(j=0;j<9;j++)
        	{
        		domaincpy[i][j]=new boolean[9];
        	}
       	}
       	domaincpy=domain.clone();
		for(j=0;j<9;j++)
		{
			if(grid[rowValue][j]>0)
				continue;
			if(j==colValue)
				continue;
			domain[rowValue][j][num-1]=false;
			
			if(isAllFalse(domain,rowValue,j))
			{
				//domain[rowValue][j][num-1]=true;
				domain=domaincpy;
				output.println("\nAll false at row "+rowValue+" at ["+rowValue+"]["+j+"]");
				return false;	
			}				
		}
		for(i=0;i<9;i++)
		{
			if(grid[i][colValue]>0)
				continue;
			if(i==rowValue)
				continue;
			domain[i][colValue][num-1]=false;
			if(isAllFalse(domain,i,colValue))
			{
				//domain[i][colValue][num-1]=true;
				//output.print("Performing BackTrack : ");
				domain=domaincpy;
				output.println("\nAll false at col "+colValue+" at ["+i+"]["+colValue+"]");
				return false;
			}
				
		}
		r=rowValue-rowValue%3;
		c=colValue-colValue%3;
		for(i=r;i<r+3;i++)
		{
			for(j=c;j<c+3;j++)
			{
				if(grid[i][j]>0)
					continue;
				if((i==rowValue) && (j==colValue))
					continue;
				domain[i][j][num-1]=false;
				if(isAllFalse(domain,i,j))
				{
					//domain[i][j][num-1]=true;	
					//output.print("Performing BackTrack : ");
					domain=domaincpy;
					output.println("\nAll false at box ["+r+"]["+c+"]"+" at ["+i+"]["+j+"]");
					return false;
				}					
			}
		}
		output.println(num+" at ["+rowValue+"]["+colValue+"] is SAFE. Presently, all Domains:");
		showAllDomains(grid,domain,output);
		return true;
	}
	
	static boolean isAllFalse(boolean[][][] domain, int row, int col)
	{
		int i;
		for(i=0;i<9;i++)
		{
			if(domain[row][col][i]==true)
			{				
				return false;
			}
		}
		return true;
	}
	
	static boolean isSafe(int[][] grid, int rowValue, int colValue, int num) //This does not need the wrapperInt objects as no change has to be made to the original variables
	{
		if(!usedInRow(grid,rowValue,num) && !usedInCol(grid,colValue,num) && !usedInBox(grid,rowValue-rowValue%3,colValue-colValue%3,num))
		{
			return true;
		}
		return false;
	}
	
	static boolean findUnassigned(int[][] grid, wrapperInt row, wrapperInt col)
	{
		for(row.value=0;row.value<9;row.value++)
		{
			for(col.value=0;col.value<9;col.value++)
			{
				if(grid[row.value][col.value]==0) //At this point row.value and col.value attains the values of row and col which is unassigned
					return true;
			}
		}
		return false;
	}
	
	static void printGrid(int[][] grid, PrintWriter output)	
	{
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
			{
				output.print(grid[i][j]+" ");
			}
			output.println("");
		}
	}
	
	static void printGridSys(int[][] grid)	
	{
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
			{
				System.out.print(grid[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	static boolean usedInRow(int[][] grid, int rowValue, int num)
	{
		int y;
		for(y=0;y<9;y++)	
		{
			if(grid[rowValue][y]==num)
				return true;
		}
		return false;
	}
	
	static boolean usedInCol(int[][] grid, int colValue, int num)
	{
		int x;
		for(x=0;x<9;x++)	
		{
			if(grid[x][colValue]==num)
				return true;
		}
		return false;
	}
	
	static boolean usedInBox(int[][] grid, int rowValue, int colValue, int num)
	{
		int x,y;
		for(x=0;x<3;x++)
		{
			for(y=0;y<3;y++)	
			{			
				if(grid[rowValue+x][colValue+y]==num)
					return true;
			}	
		}		
		return false;
	}
		
	public static void main(String args[]) throws IOException
	{
		
		int[][] grid={{3, 0, 6, 5, 0, 8, 4, 0, 0},
                      {5, 2, 0, 0, 0, 0, 0, 0, 0},
                      {0, 8, 7, 0, 0, 0, 0, 3, 1},
                      {0, 0, 3, 0, 1, 0, 0, 8, 0},
                      {9, 0, 0, 8, 6, 3, 0, 0, 5},
                      {0, 5, 0, 0, 9, 0, 6, 0, 0},
                      {1, 3, 0, 0, 0, 0, 2, 5, 0},
                      {0, 0, 0, 0, 0, 0, 0, 7, 4},
                      {0, 0, 5, 2, 0, 6, 3, 0, 0}};   
                      
		/*                      
        int[][] grid={{2, 0, 9, 0, 3, 0, 0, 0, 0},
                      {0, 3, 8, 0, 6, 0, 0, 0, 5},
                      {0, 0, 0, 8, 0, 4, 0, 0, 0},
                      {0, 0, 7, 0, 0, 5, 8, 1, 0},
                      {0, 0, 0, 0, 0, 0, 0, 0, 0},
                      {0, 8, 4, 1, 0, 0, 5, 0, 0},
                      {0, 0, 0, 6, 0, 3, 0, 0, 0},
                      {6, 0, 0, 0, 1, 0, 7, 8, 0},
                      {0, 0, 0, 0, 2, 0, 9, 0, 6}};  //this not working
        */              
        boolean[][][] domain=new boolean[9][9][9];
        int i,j,k,p,q,p1,q1,m;
        for(i=0;i<9;i++)
        {
        	for(j=0;j<9;j++)
        	{
        		domain[i][j]=new boolean[9];
        	}
       	}
       	
       	for(i=0;i<9;i++)  //not required if default to true
       	{
       		for(j=0;j<9;j++)
       		{
       			for(k=0;k<9;k++)
       			{
       				domain[i][j][k]=true;     //is in domain, can be assigned
       			}
       		}
       	}
       	
       	File file = new File("sudokuCheckAllDomains1.txt");  
		FileWriter writer = new FileWriter(file);  
		PrintWriter output = new PrintWriter(writer); 
			
		//output.close();
       	
        for(i=0;i<9;i++)
        {
        	for(j=0;j<9;j++)
        	{
        		if(grid[i][j]>0)  //default to true, I hope, check it, however not bothered only about the assigned ones.
        			continue;
        		output.println("");
        		output.println("For ["+i+"]["+j+"]");
        		for(q=0;q<9;q++)  //check that row
        		{
        			if((m=grid[i][q])>0)
        			{
        				output.println("["+i+"]["+q+"]="+m+" So,making domain["+i+"]["+j+"]["+(m-1)+"]=false");
        				domain[i][j][m-1]=false;
        			}
        				
        		}
        		for(p=0;p<9;p++)  //check that col
        		{
        			if((m=grid[p][j])>0)
        			{
        				output.println("["+p+"]["+j+"]="+m+" So,making domain["+i+"]["+j+"]["+(m-1)+"]=false");
        				domain[i][j][m-1]=false;
        			}
        		}
        		p1=i-i%3;
        		q1=j-j%3;
        		for(p=p1;p<p1+3;p++)
        		{
        			for(q=q1;q<q1+3;q++)
        			{
        				if((m=grid[p][q])>0)
        				{
        					output.println("Box["+p1+"]["+q1+"]="+m+" So,making domain["+i+"]["+j+"]["+(m-1)+"]=false");
        					domain[i][j][m-1]=false;
        				}
        			}
        		}        		        	
        	}
       	}
		printGrid(grid,output);
		printGridSys(grid);
				
		if(sudokuSolve(grid,domain,output))                     
		{
			output.println("");
			output.println("After solving:");
			System.out.println("\nAfter solving:");
			printGridSys(grid);
		}
		else
		 System.out.println("\nNo solution exists");
		
		System.out.println("\n\nNode count="+count);
        
        
        output.println("PRINTING ALL DOMAINS:");
		for(i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
			{
				if(grid[i][j]>0)
					continue;			
				output.print("["+i+"]["+j+"]=");
				for(k=0;k<9;k++)
				{
					if(domain[i][j][k])
						output.print((k+1)+",");
				}
				output.print("  ");
			}
		output.println("");
		}
		
        
        output.close();
	}	
}