package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean isCircle = false;
    private int[] edgeToDup;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        edgeToDup = new int[maze.V()];
    }

    @Override
    public void solve() {
        Stack<Integer> fringe = new Stack<>();
        fringe.push(0);
        marked[0] = true;
        edgeToDup[0] = 0;
        distTo[0] = 0;
        while (!fringe.isEmpty()) {
            int v = fringe.pop();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    fringe.push(w);
                    marked[w] = true;
                    edgeToDup[w] = v;
                    distTo[w] = distTo[v] + 1;
                    announce();
                } else {
                    if (w != edgeToDup[v]) {
                        edgeTo[w] = v;
                        makeCircle(w,v);
                        announce();
                        return;
                    }
                }
            }
        }
    }

    private void makeCircle(int w, int v){
        int mutRoot = findMutRoot(w,v);
        int current = w;
        while (current != mutRoot) {
            edgeTo[edgeToDup[current]] = current;
            current = edgeToDup[current];
        }
        current = v;
        while (current != mutRoot) {
            edgeTo[current] = edgeToDup[current];
            current = edgeToDup[current];
        }
    }

    private int findMutRoot(int w, int v) {
        int mutRoot = 0;
        int i = distTo[w] - distTo[v];
        if (i == 0) {
            while (edgeToDup[w] != edgeToDup[v]) {
                w = edgeToDup[w];
                v = edgeToDup[v];
            }
            mutRoot = edgeToDup[w];
        } else if (i < 0) {
            for (int j = 0; j < Math.abs(i); j++) {
                v = edgeToDup[v];
            }
            while (edgeToDup[w] != edgeToDup[v]) {
                w = edgeToDup[w];
                v = edgeToDup[v];
            }
            mutRoot = edgeToDup[w];
        } else if (i > 0) {
            for (int j = 0; j < Math.abs(i); j++) {
                w = edgeToDup[w];
            }
            while (edgeToDup[w] != edgeToDup[v]) {
                w = edgeToDup[w];
                v = edgeToDup[v];
            }
            mutRoot = edgeToDup[w];
        }
        return mutRoot;
    }
}

