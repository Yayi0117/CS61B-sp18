package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF set;
    private WeightedQuickUnionUF setNoBottom;
    private int width;
    private int openSiteNum;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        width = N;
        grid = new boolean[N][N];
        set = new WeightedQuickUnionUF(N * N + 2);
        setNoBottom = new WeightedQuickUnionUF(N * N + 1);
        openSiteNum = 0;

    }
    // convert (x,y) coordinate into 1-dimension
    private int xyTo1D(int row, int col) {
        return row * width + col + 1;
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > width - 1 || col < 0 || col > width - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSiteNum++;
        }
        int setPosition = xyTo1D(row, col);
        // set a virtual top site connected to all open items in top row.
        if (row == 0) {
            set.union(0, setPosition);
            setNoBottom.union(0, setPosition);
        }
        // union with all surrounded open sites
        if (row > 0 && isOpen(row - 1, col)) {
            set.union(xyTo1D(row - 1, col), setPosition);
            setNoBottom.union(xyTo1D(row - 1, col), setPosition);
        }
        if (row < width - 1 && isOpen(row + 1, col)) {
            set.union(xyTo1D(row + 1, col), setPosition);
            setNoBottom.union(xyTo1D(row + 1, col), setPosition);
        }
        if (col > 0 && isOpen(row, col - 1)) {
            set.union(xyTo1D(row, col - 1), setPosition);
            setNoBottom.union(xyTo1D(row, col - 1), setPosition);
        }
        if (col < width - 1 && isOpen(row, col + 1)) {
            set.union(xyTo1D(row, col + 1), setPosition);
            setNoBottom.union(xyTo1D(row, col + 1), setPosition);
        }
        // set a virtual bottom site connected to all items in bottom row.
        if (row == width - 1) {
            set.union(width * width + 1, setPosition);
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > width - 1 || col < 0 || col > width - 1) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > width - 1 || col < 0 || col > width - 1) {
            throw new IndexOutOfBoundsException();
        }
        return setNoBottom.connected(0, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSiteNum;
    }
    // does the system percolate?
    public boolean percolates() {
        return set.connected(width * width + 1, 0);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation test = new Percolation(5);
        test.open(0, 0);
        test.open(1, 0);
        test.open(2, 1);
        test.open(2, 2);
        test.open(3, 2);
        test.open(2, 0);
        boolean notPercolate = test.percolates();
        test.open(4, 2);
        boolean doPercolate = test.percolates();
        test.open(4, 4);
        test.open(3, 4);
        // test if there is a backwash
        boolean notFull = test.isFull(3, 4);
    }
}
