package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.w3c.dom.html.HTMLDOMImplementation;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 50;
    private static final long SEED = 28757;
    private static final Random RANDOM = new Random(SEED);
    public static class Position{
        public int x;
        public int y;

        public Position (int xPos, int yPos){
            x = xPos;
            y = yPos;
        }
    }
    public static void tessellate19 (TETile[][] world, Position p, int s){
            drawRandomVerticleHexes(3,world,p,s);
            Position right1 = bottomRightNeighbor(s,p);
            drawRandomVerticleHexes(4,world,right1,s);
            Position right2 = bottomRightNeighbor(s,right1);
            drawRandomVerticleHexes(5,world,right2,s);
            Position right3 = topRightNeighbor(s,right2);
            drawRandomVerticleHexes(4,world,right3,s);
            Position right4 = topRightNeighbor(3,right3);
            drawRandomVerticleHexes(3,world,right4,s);
    }
    private static void drawRandomVerticleHexes(int N, TETile[][] world, Position p, int width) {
        for (int n = 0; n < N; n++) {
            TETile t = randomTile();
            Position start = new Position(p.x,(p.y+n*2*width));
            addHexagon(world, start, width, t);
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        //int random1 = StdRandom.uniform(9);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.SAND;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.WATER;
        }
    }

    private static Position topRightNeighbor(int width, Position p) {
        Position newHex = new Position((p.x+2*width-1),(p.y+width));
        return  newHex;
    }

    private static Position bottomRightNeighbor(int width, Position p) {
        Position newHex = new Position((p.x+2*width-1),(p.y-width));
        return newHex;
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        for (int i = 0; i < s * 2; i = i+1 ){
            int width = hexRowWidth(s, i);
            int startX = p.x + hexRowOffset(s, i);
            int startY = p.y + i ;
            Position pRow = new Position(startX,startY);
            addHexRow(world, pRow, width, t);
        }
    }

    private static int hexRowWidth(int s, int i){
        if (i < s){
            return s + i * 2;
        }
        return s + (s -1) * 2 - (i - s) * 2;
    }

    private static int  hexRowOffset(int s,int i) {
        if (i < s){
            return  - i;
        }
        return  - (s - 1) + (i - s);
    }

    private static void addHexRow (TETile[][] world, Position p, int width, TETile t){
        for (int i = 0; i < width; i++){
            //world[p.x+i][p.y] = TETile.colorVariant(t,32,32, 32, RANDOM);
            world[p.x+i][p.y] = t;
        }
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(2,hexRowWidth(2,3));
        assertEquals(7,hexRowWidth(3,2));
        assertEquals(4,hexRowWidth(4,0));
        assertEquals(13,hexRowWidth(5,5));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

    @Test
    public void testRightNeighbor() {
        Position test = new Position(2,3);
        Position expect1 = new Position(7,6);
        Position result1 = topRightNeighbor(3,test);
        assertEquals(expect1.x,result1.x);
        assertEquals(expect1.y,result1.y);
        Position expect2 = new Position(7,0);
        Position result2 = bottomRightNeighbor(3,test);
        assertEquals(expect2.x,result2.x);
        assertEquals(expect2.y,result2.y);
    }

    public static void main(String[] arg) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(20,15);
        //drawRandomVerticleHexes(4,world,p,4);
        tessellate19(world,p,3);
        //TETile t = Tileset.FLOWER;
        //addHexagon (world, p,10, t);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }
}
