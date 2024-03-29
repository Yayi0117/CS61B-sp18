package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles;
    private int N;

    public Board(int[][] tiles) {
        N = tiles[0].length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > (N - 1) || j < 0 || j > (N - 1)) {
            throw new IndexOutOfBoundsException();
        } else {
            return tiles[i][j];
        }
    }

    public int size() {
        return N;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * @source CS61B HW 4: 8 Puzzle
     */
    @Override
    public Iterable<WorldState> neighbors() {
        int blank = 0;
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == blank) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = blank;
                    int[][] ili1li0 = new int[hug][hug];
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            ili1li0[i][j] = ili1li1[i][j];
                        }
                    }
                    Board neighbor = new Board(ili1li0);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = blank;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    int goal = i * N + j + 1;
                    if (goal == N * N) {
                        goal = 0;
                    }
                    if (tiles[i][j] != goal) {
                        count += 1;
                    }
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    int goal = i * N + j + 1;
                    if (goal == N * N) {
                        goal = 0;
                    }
                    if (tiles[i][j] != goal) {
                        int iGoal = (tiles[i][j] - 1) / N;
                        int jGoal = (tiles[i][j] - 1) % N;
                        count = count + Math.abs(i - iGoal) + Math.abs(j - jGoal);
                    }
                }
            }
        }
        return count;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
        //return hamming();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board tiles1 = (Board) y;
        if (tiles1.size() != size()) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != tiles1.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int num = size();
        s.append(num + "\n");
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        return tiles.hashCode();
    }
}
