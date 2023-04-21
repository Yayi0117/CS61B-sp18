import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.HashMap;

public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }
    public int width() {
        return picture.width();
    }
    public int height() {
        return picture.height();
    }
    public  double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException();
        }
        int left = x - 1;
        int right = x + 1;
        int up = y - 1;
        int down = y + 1;
        if (left < 0) {
            left = width() - 1;
        }
        if (right > width() - 1) {
            right = 0;
        }
        if (up < 0) {
            up = height() - 1;
        }
        if (down > height() - 1) {
            down = 0;
        }
        Color pixelLeft, pixelRight, pixelUp, pixelDown;
        pixelLeft = picture.get(left, y);
        pixelRight = picture.get(right, y);
        pixelUp = picture.get(x, up);
        pixelDown = picture.get(x, down);
        int rX = pixelRight.getRed() - pixelLeft.getRed();
        int gX = pixelRight.getGreen() - pixelLeft.getGreen();
        int bX = pixelRight.getBlue() - pixelLeft.getBlue();
        int rY = pixelDown.getRed() - pixelUp.getRed();
        int gY = pixelDown.getGreen() - pixelUp.getGreen();
        int bY = pixelDown.getBlue() - pixelUp.getBlue();
        return rX * rX + bX * bX + gX * gX + rY * rY + bY * bY + gY * gY;
    }
    public int[] findHorizontalSeam() {
        return findSeam('h');
    }
    public int[] findVerticalSeam() {
        return findSeam('v');
    }
    private int[] findSeam(char direction) {
        int WIDTH = width();
        int HEIGHT = height();
        if (direction == 'h') {
            WIDTH = height();
            HEIGHT = width();
        }
        double[] energyForAll = new double[WIDTH * HEIGHT];
        int index = 0;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (direction == 'h') {
                    energyForAll[index] = energy(row, col);
                } else {
                    energyForAll[index] = energy(col, row);
                }
                index++;
            }
        }
        // store best paths for each colum
        int[][] paths = new int[WIDTH][HEIGHT];
        // store min total energy for last row
        HashMap<Integer, Double> minEnergy = new HashMap<>();
        // for the top row, M(i,0) is just e(i,0) for all colum
        for (int w = 0; w < WIDTH; w++) {
            paths[w][0] = w;
            minEnergy.put(w, energyForAll[w]);
        }
        for (int h = 1; h < HEIGHT; h++) {
            for (int w = 0; w < WIDTH; w++) {
                paths[w][h] = w;
            }
            int[][] pathsOld = new int[WIDTH][h+1];
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < h; j++){
                    pathsOld[i][j] = paths[i][j];
                }
            }
            HashMap<Integer, Double> minEnergyThisRow = new HashMap<>();
            for (int w = 0; w < WIDTH; w++) {
                int left = w - 1;
                int right = w + 1;
                double curEnergy = energyForAll[w + h * WIDTH];
                double upMinEnergy = minEnergy.get(w);
                MinPQ<Node> find = new MinPQ<>();
                Node upPixel = new Node(w,upMinEnergy);
                find.insert(upPixel);
                if (right <= WIDTH - 1) {
                    double rightMinEnergy = minEnergy.get(right);
                    Node rightPixel = new Node(right, rightMinEnergy);
                    find.insert(rightPixel);
                }
                if (left >= 0) {
                    double leftMinEnergy = minEnergy.get(left);
                    Node leftPixel = new Node(left, leftMinEnergy);
                    find.insert(leftPixel);
                }
                Node last = find.min();
                double newEnergy = curEnergy + last.minEnergy;
                int lastStep = last.pos;
                for (int pre = h-1; pre >= 0; pre--) {
                    paths[w][pre] = lastStep;
                    if (pre > 0) {
                        lastStep = pathsOld[lastStep][pre - 1];
                    }
                }
                minEnergyThisRow.put(w, newEnergy);
            }
            minEnergy = minEnergyThisRow;
        }
        MinPQ<Node> optimalFinal = new MinPQ<>();
        for (int w = 0; w < WIDTH; w++) {
            Node lastRow = new Node(w, minEnergy.get(w));
            optimalFinal.insert(lastRow);
        }
        int best = optimalFinal.min().pos;
        return paths[best];
    }
    private class Node implements Comparable<Node> {
        public int pos;
        public double minEnergy;
        public Node (int pos, double minEnergy) {
            this.pos = pos;
            this.minEnergy = minEnergy;
        }
        @Override
        public int compareTo (Node that) {
            double cmp = this.minEnergy - that.minEnergy;
            if (cmp > 0) {
                return 1;
            } else if (cmp < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(picture, seam);
    }
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
