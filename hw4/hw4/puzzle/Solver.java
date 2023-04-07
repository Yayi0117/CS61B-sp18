package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode goal;
    private Stack<WorldState> path;
    private class SearchNode implements Comparable<SearchNode> {
        WorldState word;
        int moves;
        SearchNode parent;
        int heuristic;
        SearchNode(WorldState word, int moves, SearchNode parent) {
            this.word = word;
            this.moves = moves;
            this.parent = parent;
            heuristic = word.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode other) {
            int totalThis = moves + heuristic;
            int totalOther = other.moves + other.heuristic;
            if (totalThis > totalOther) {
                return 1;
            } else if (totalThis < totalOther) {
                return -1;
            } else {
                return 0;
            }
        }

    }
    /*Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        int i = 0;
        SearchNode begin = new SearchNode(initial, i, null);
        pq.insert(begin);
        while (!pq.min().word.isGoal()) {
            SearchNode removed = pq.delMin();
            for (WorldState neighbor : removed.word.neighbors()) {
                if (removed.parent != null) {
                    if (!removed.parent.word.equals(neighbor)) {
                        SearchNode newNode = new SearchNode(neighbor, removed.moves + 1, removed);
                        pq.insert(newNode);
                    }
                } else {
                    SearchNode newNode = new SearchNode(neighbor, removed.moves + 1, removed);
                    pq.insert(newNode);
                }
            }
        }
        goal = pq.min();
        reconstructPath();
    }
    private void reconstructPath() {
        SearchNode current = goal;
        path = new Stack<>();
        while (current != null) {
            path.push(current.word);
            current = current.parent;
        }
    }

    /*Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.
     */
    public int moves() {
        return goal.moves;
    }

    /*Returns a sequence of WorldStates from the initial WorldState
     to the solution.
     */
    public Iterable<WorldState> solution() {
        return path;
    }
}
