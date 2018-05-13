import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] resultArray;
    private int N;
    int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        resultArray = new double[trials];
        int[][] myGrid;
        N = n;
        this.trials = trials;

        for(int j = 0; j < trials; j++) {
            Percolation myPercolation = new Percolation(n);
            myGrid = myPercolation.grid;
            int row;
            int col;

            for(int i = 1; i < myPercolation.line; i++) {
                myPercolation.myWeightedQuickUnionUF.union(myGrid[0][0], myGrid[1][i]);
                myPercolation.myWeightedQuickUnionUF.union(
                        myGrid[myPercolation.line][myPercolation.line], myGrid[myPercolation.line - 1][i]);
            }

            while(! myPercolation.percolates()) {
                //generate 2 random numbers
                while(true) {
                    row = StdRandom.uniform(1,n+1);
                    col = StdRandom.uniform(1,n+1);
                    if(! myPercolation.isOpen(row, col)) {
                        myPercolation.open(row,col);
                        break; 
                    }
                }

                if(myPercolation.isOpen(row-1,col))
                    myPercolation.myWeightedQuickUnionUF.union(myGrid[row-1][col],myGrid[row][col]);
                if(myPercolation.isOpen(row+1,col))
                    myPercolation.myWeightedQuickUnionUF.union(myGrid[row+1][col],myGrid[row][col]);
                if(myPercolation.isOpen(row,col-1))
                    myPercolation.myWeightedQuickUnionUF.union(myGrid[row][col-1],myGrid[row][col]);
                if(myPercolation.isOpen(row,col+1))
                    myPercolation.myWeightedQuickUnionUF.union(myGrid[row][col+1],myGrid[row][col]);
            }

            resultArray[j] = myPercolation.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){ return StdStats.mean(resultArray); }

    // sample standard deviation of percolation threshold
    public double stddev(){ return StdStats.stddev(resultArray); }

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){return mean()-1.96*stddev()/Math.sqrt(trials);}

    // high endpoint of 95% confidence interval
    public double confidenceHi(){return mean()+1.96*stddev()/Math.sqrt(trials);}

    // test client (described below)
    public static void main(String[] args){
        System.out.print("Please input n: ");
        int n = StdIn.readInt();
        System.out.print("Please input t: ");
        int t = StdIn.readInt();
        PercolationStats myPercolationStats = new PercolationStats(n, t);
        System.out.println("Mean = "+myPercolationStats.mean());
        //System.out.println("Rate = "+myPercolationStats.mean()/(n*n));
        System.out.println("Stddev = "+myPercolationStats.stddev());
        System.out.println("95% confidence interval = ["+myPercolationStats.confidenceLo()+
                ", "+myPercolationStats.confidenceHi()+"]");
    }
}