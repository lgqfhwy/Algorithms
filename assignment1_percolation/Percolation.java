import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    int[][] grid;
    private int[] percolatedArray;
    private int percolatedArrayCount;
    WeightedQuickUnionUF myWeightedQuickUnionUF;
    int line;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        int number = 0;
        grid = new int[n+2][n+2];
        for(int i = 0; i < (n + 2); i++) {
            for(int j = 0; j < (n + 2); j++) { 
                grid[i][j] = number++;
            }
        }
        line = n+1;

        percolatedArrayCount = 0;
        percolatedArray = new int[(n + 2) * (n + 2)];
        int endPer = (n + 2) * (n + 2);
        for (int i = 0; i < endPer; i++) {
            percolatedArray[i] = 0; // 0 means block, 1 means open.
        }

        myWeightedQuickUnionUF = new WeightedQuickUnionUF((n + 2) * (n + 2));
    }

    // open site (row, col) if it is not open already
    // public void open(int row, int col){
    //     percolatedArray [percolatedArrayCount++] = grid[row][col];
    // }

    // Origin version is right, below is a more quicker version.
    public void open(int row, int col) {
        int pointRowCol = grid[row][col];
        percolatedArray[pointRowCol] = 1;
        percolatedArrayCount++;
    }

    // is site (row, col) open?
    // public boolean isOpen(int row, int col) {
    //     for(int i : percolatedArray){
    //         if(i == grid[row][col]){ return true; }
    //     }return false;
    // }

    // Origin version is right, below is a more quicker version
    public boolean isOpen(int row, int col) {
        int pointRowCol = grid[row][col];
        return (percolatedArray[pointRowCol] == 1);
    }

    // Maybe wrong
    // // is site (row, col) full?
    // public boolean isFull(int row, int col) {
    //     return(isOpen(row+1, col) || isOpen(row-1, col)
    //             || isOpen(row, col+1) || isOpen(row, col-1));
    // }

    //  A full site is an open site that can be connected to an open site in the top row via a 
    // chain of neighboring (left, right, up, down) open sites.
    // We say the system percolates if there is a full site in the bottom row. In other words, 
    // a system percolates if we fill all open sites connected to the top row and that process 
    // fills some open site on the bottom row. 
    public boolean isFull(int row, int col) {
        return ((isOpen(row - 1, col) && myWeightedQuickUnionUF.connected(grid[0][0], grid[row - 1][col])) ||
                (isOpen(row + 1, col) && myWeightedQuickUnionUF.connected(grid[0][0], grid[row + 1][col])) ||
                (isOpen(row, col - 1) && myWeightedQuickUnionUF.connected(grid[0][0], grid[row][col - 1])) ||
                (isOpen(row, col + 1) && myWeightedQuickUnionUF.connected(grid[0][0], grid[row][col + 1])));
    }

    // number of open sites
    public int numberOfOpenSites(){
        // int num = 0;
        // for (int i : percolatedArray) {
        //     if(i != 0) { 
        //         num++; 
        //     }
        // }
        // return num;
        return percolatedArrayCount;
    }

    // does the system percolate?
    public boolean percolates(){
        return (myWeightedQuickUnionUF.connected(grid[0][0],grid[line][line]));
    }

    public static void main(String[] args)
    {
        int count = 0;
        Percolation p = new Percolation(4);
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 6; j++)
                if(count < 5) {
                    System.out.print(String.format("%02d",p.grid[i][j]) + " ");
                    count++;
                }else{
                    System.out.println(String.format("%02d",p.grid[i][j]));
                    count = 0;
                }
    }
}