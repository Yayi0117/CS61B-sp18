package byog.Core;
import java.util.ArrayList;
import java.util.Random;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldGenerator {
    private int width;
    private int height;
    private TETile[][] tiles;
    private Random random;
    private ArrayList<Room> rooms;

    private class Room {
        // The properties of the room
        int x;
        int y;
        int w;
        int h;

        // The constructor of the room
        private Room(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        // The method that checks if the room intersects with another room
        public boolean intersects(Room other) {
            // Check if the horizontal and vertical projections of the rooms overlap
            return (this.x < other.x + other.w && this.x + this.w > other.x
                    && this.y < other.y + other.h && this.y + this.h > other.y);
        }
    }
    public WorldGenerator(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.tiles = new TETile[width][height];
        this.random = new Random(seed);
        this.rooms = new ArrayList<>();
        generateWorld();
    }
    private void generateWorld() {
        // generate random rooms in the current world
        generateRooms();
        // Create hallways between adjacent rooms
        generateHallways();
        // Add walls around the floor tiles
        for (int m1 = 0; m1 < width; m1++) {
            for (int n = 0; n < height; n++) {
                if (tiles[m1][n].description().equals("floor")) {
                    int[][] directions = new int[][]
                            {{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
                    for (int[] pair : directions) {
                        int h = m1 + pair[0];
                        int v = n + pair[1];
                        if (!tiles[h][v].description().equals("floor")) {
                            tiles[h][v] = Tileset.WALL;
                        }
                    }
                }
            }
        }
        generateGate();
    }

    private void generateRooms() {
        // Fill the grid with wall tiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        // Define the size of the grid and the number of rooms
        int m = random.nextInt(50) + 15; // number of rooms

        // Create a list of rooms, where each room is an object with
        // x, y, width, and height properties
        rooms = new ArrayList<Room>();

        // Randomly generate rooms and add them to the grid and the list
        for (int k = 0; k < m; k++) {
            // Choose a random position, width, and height for the room
            int w = random.nextInt(5) + 2;
            int h = random.nextInt(5) + 5;
            int x = random.nextInt(width - w - 1) + 1;
            int y = random.nextInt(height - h - 1) + 1;

            // Create a new room object
            Room room = new Room(x, y, w, h);

            // Check if the room overlaps with any existing room
            boolean overlap = false;
            for (Room r : rooms) {
                if (room.intersects(r)) {
                    overlap = true;
                    break;
                }
            }
            // If the room does not overlap, add it to the grid and the list
            if (!overlap) {
                // Fill the grid with floor tiles for the room
                for (int i = x; i < x + w; i++) {
                    for (int j = y; j < y + h; j++) {
                        tiles[i][j] = Tileset.FLOOR;
                    }
                }
                // Add the room to the list
                rooms.add(room);
            }
        }
    }

    private void generateHallways() {
        for (int k = 0; k < rooms.size() - 1; k++) {
            // Get the centers of the current room and the next room
            Room r1 = rooms.get(k);
            Room r2 = rooms.get(k + 1);
            int x1 = r1.x + r1.w / 2;
            int y1 = r1.y + r1.h / 2;
            int x2 = r2.x + r2.w / 2;
            int y2 = r2.y + r2.h / 2;

            // Choose a random direction to connect the rooms: horizontal or vertical
            int dir = random.nextBoolean() ? 0 : 1;
            //int dir = random.nextInt(2);

            // If horizontal, create a horizontal line
            // from x1 to x2 and a vertical line from y1 to y2
            if (dir == 0) {
                // Fill the grid with floor tiles for the horizontal line
                for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
                    tiles[i][y1] = Tileset.FLOOR;
                }
                // Fill the grid with floor tiles for the vertical line
                for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
                    tiles[x2][j] = Tileset.FLOOR;
                }
            } else {
                // If vertical, create a vertical line
                // from y1 to y2 and a horizontal line from x1 to x2.
                // Fill the grid with floor tiles for the vertical line
                for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
                    tiles[x1][j] = Tileset.FLOOR;
                }
                // Fill the grid with floor tiles for the horizontal line
                for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
                    tiles[i][y2] = Tileset.FLOOR;
                }
            }
        }
    }
    public int[] generateGate() {
        ArrayList<int[]> walls = new ArrayList<>();
        for (int h1 = 0; h1 < width; h1++) {
            for (int v1 = 0; v1 < height; v1++) {
                if (tiles[h1][v1].description().equals("wall")) {
                    try {
                        if ((tiles[h1 - 1][v1].description().equals("floor")
                                || tiles[h1 + 1][v1].description().equals("floor")
                                || tiles[h1][v1 - 1].description().equals("floor")
                                || tiles[h1][v1 + 1].description().equals("floor"))) {
                            int[] location = new int[]{h1, v1};
                            walls.add(location);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        int gateIndex = random.nextInt(walls.size());
        int[] gateLocation = walls.get(gateIndex);
        tiles[gateLocation[0]][gateLocation[1]] = Tileset.UNLOCKED_DOOR;
        return gateLocation;
    }

    public TETile[][] getTiles() {
        return tiles;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

}



