import java.io.*;

class wrapperInt
{
	int value;
	/*
	wrapperInt(int d)
	{
		value=d;
	}
	wrapperInt()  //default value of "value" =0
	{
	}   */
}

class sudokuBacktrack
{
	static int count=0;
	static boolean sudokuSolve(int[][] grid)
	{
		wrapperInt row=new wrapperInt();  //default value of "value" =0
		wrapperInt col=new wrapperInt();
		if(!findUnassigned(grid,row,col)) //This is the function with reference variables in C++ geeks implementation. SO THIS REQUIRES OBJECT REFERENCE VARIABLES TO BE PASSED THROUGH
			return true;
		int num;
		for(num=1;num<=9;num++)
		{
			if(isSafe(grid,row.value,col.value,num))	//using row, col only while passing the values and row.value, col.value while using them
			{
				int rowValue=row.value, colValue=col.value;
				grid[rowValue][colValue]=num;
				count++;
				if(sudokuSolve(grid))
					return true;
				grid[rowValue][colValue]=0; //Unassign it after backtracking from a wrong path
			}
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
	
	static boolean isSafe(int[][] grid, int rowValue, int colValue, int num) //This does not need the wrapperInt objects as no change has to be made to the original variables
	{
		if(!usedInRow(grid,rowValue,num) && !usedInCol(grid,colValue,num) && !usedInBox(grid,rowValue-rowValue%3,colValue-colValue%3,num))
		{
			return true;
		}
		return false;
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
	
	static void printGrid(int[][] grid)	
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
	
	
	public static void main(String args[]) 
	{
		/*
		int[][] grid={{3, 0, 6, 5, 0, 8, 4, 0, 0},
                      {5, 2, 0, 0, 0, 0, 0, 0, 0},
                      {0, 8, 7, 0, 0, 0, 0, 3, 1},
                      {0, 0, 3, 0, 1, 0, 0, 8, 0},
                      {9, 0, 0, 8, 6, 3, 0, 0, 5},
                      {0, 5, 0, 0, 9, 0, 6, 0, 0},
                      {1, 3, 0, 0, 0, 0, 2, 5, 0},
                      {0, 0, 0, 0, 0, 0, 0, 7, 4},
                      {0, 0, 5, 2, 0, 6, 3, 0, 0}};   
                      
                      
        int[][] grid={{2, 0, 9, 0, 3, 0, 0, 0, 0},
                      {0, 3, 8, 0, 6, 0, 0, 0, 5},
                      {0, 0, 0, 8, 0, 4, 0, 0, 0},
                      {0, 0, 7, 0, 0, 5, 8, 1, 0},
                      {0, 0, 0, 0, 0, 0, 0, 0, 0},
                      {0, 8, 4, 1, 0, 0, 5, 0, 0},
                      {0, 0, 0, 6, 0, 3, 0, 0, 0},
                      {6, 0, 0, 0, 1, 0, 7, 8, 0},
                      {0, 0, 0, 0, 2, 0, 9, 0, 6}}; */
                      
        int[][] grid={{3, 0, 6, 5, 0, 8, 4, 0, 0},
		              {5, 2, 0, 0, 0, 0, 0, 0, 0},
		              {0, 8, 7, 0, 0, 0, 0, 3, 1},
		              {0, 0, 3, 0, 1, 0, 0, 8, 0},
		              {9, 0, 0, 8, 6, 3, 0, 0, 5},
		              {0, 5, 0, 0, 9, 0, 6, 0, 0},
		              {1, 3, 0, 0, 0, 0, 2, 5, 0},
		              {0, 0, 0, 0, 0, 0, 0, 7, 4},
		              {0, 0, 5, 2, 0, 6, 3, 0, 0}}; 
                      
		printGrid(grid);
		if(sudokuSolve(grid))                     
		{
			System.out.print("\n\nAfter solving:\n");
			printGrid(grid);
			System.out.println("\n\nNode count="+count);	
		}
		else
		 System.out.println("No solution exists");
        
	}	
}