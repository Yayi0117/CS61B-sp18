package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.*;


public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private int canvasWide = 50;
    private int canvasHeight = 60;
    private boolean gameResult = true;
    private WorldGenerator world;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        displayMainMenu();
        while (!StdDraw.hasNextKeyTyped()) {
            continue;
        }
        char command = StdDraw.nextKeyTyped();
        if (command == 'N') {
            beginNewGame();
        } else if (command == 'L') {
            beginLastGame();
        } else if (command == 'Q') {
            System.exit(0);
        }
    }
    private void beginLastGame() {
        world = loadGame();
        TETile[][] finalWorldFrame = world.getTiles();
        ter.initialize(WIDTH, HEIGHT + 1);
        ter.renderFrame(finalWorldFrame);
        playGame(finalWorldFrame);
    }

    private TETile[][] beginLastGame(String input) {
        world = loadGame();
        TETile[][] finalWorldFrame = world.getTiles();
        playGame(finalWorldFrame,input.substring(1));
        return finalWorldFrame;
    }

    private void quitGame() {
        saveGame(world);
        System.exit(0);
    }

    private void beginNewGame() {
        long seedNum = promptForSeed();
        world = new WorldGenerator(WIDTH, HEIGHT, seedNum);
        TETile[][] finalWorldFrame = world.getTiles();
        ter.initialize(WIDTH, HEIGHT + 1);
        ter.renderFrame(finalWorldFrame);
        playGame(finalWorldFrame);
    }

    private TETile[][] beginNewGame(String input) {
        int indexS = input.indexOf('S');
        long seedNum = Long.parseLong(input.substring(1, indexS), 10);
        world = new WorldGenerator(WIDTH, HEIGHT, seedNum);
        TETile[][] finalWorldFrame = world.getTiles();
        playGame(finalWorldFrame,input.substring(indexS+1));
        return finalWorldFrame;
    }

    private void playGame(TETile[][] finalWorldFrame, String commands) {
        int[] playerPosition = world.getPlayerPosition();
        for (int i = 0; i < commands.length(); i++) {
            int xBefore = playerPosition[0];
            int yBefore = playerPosition[1];
            finalWorldFrame[xBefore][yBefore] = Tileset.FLOOR;
            switch (commands.charAt(i)) {
                case 'W':
                    playerPosition[1] += 1; // move up
                    break;
                case 'A':
                    playerPosition[0] -= 1; // move left
                    break;
                case 'S':
                    playerPosition[1] -= 1; // move down
                    break;
                case 'D':
                    playerPosition[0] += 1; // move right
                    break;
                case ':':
                    finalWorldFrame[xBefore][yBefore] = Tileset.PLAYER;
                    quitGame();
                    break;
                default: break;
            }
            int xAfter = playerPosition[0];
            int yAfter = playerPosition[1];
            finalWorldFrame[xAfter][yAfter] = Tileset.PLAYER;
            if (world.containsSameItems(world.getWallTiles(), playerPosition)) {
                gameResult = false;
                break;
            }
            if (xAfter == world.getGateLocation()[0]
                    && yAfter == world.getGateLocation()[1]) {
                break;
            }
        }
        if (world.containsSameItems(world.getWallTiles(), playerPosition)
                || (playerPosition[0] == world.getGateLocation()[0]
                && playerPosition[1] == world.getGateLocation()[1])) {
            gameOver(gameResult);
        }
    }


    private void playGame(TETile[][] finalWorldFrame) {
        int[] playerPosition = world.getPlayerPosition();
        // get mouse original position
        int xMouse = (int) StdDraw.mouseX();
        int yMouse = (int) StdDraw.mouseY();
        displayHUD(finalWorldFrame, xMouse, yMouse);
        // wait for player to move using 'W' 'A' 'S' 'D' or 'w' 'a' 's' 'd'
        while (true) {
            // diSplay HUD if the mouse has moved
            int xMouseNow = (int) StdDraw.mouseX();
            int yMouseNow = (int) StdDraw.mouseY();
            if (xMouseNow != xMouse || yMouseNow != yMouse) {
                displayHUD(finalWorldFrame, xMouseNow, yMouseNow);
                xMouse = xMouseNow;
                yMouse = yMouseNow;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_W)
                    || StdDraw.isKeyPressed(KeyEvent.VK_A)
                    || StdDraw.isKeyPressed(KeyEvent.VK_S)
                    || StdDraw.isKeyPressed(KeyEvent.VK_D)
                    || StdDraw.isKeyPressed(KeyEvent.VK_W + 32)
                    || StdDraw.isKeyPressed(KeyEvent.VK_A + 32)
                    || StdDraw.isKeyPressed(KeyEvent.VK_S + 32)
                    || StdDraw.isKeyPressed(KeyEvent.VK_D + 32)
                    || StdDraw.isKeyPressed(KeyEvent.VK_Q)
                    || StdDraw.isKeyPressed(KeyEvent.VK_Q + 32)
            ) {
                break;
            }
        }
        // if player begin to move, change the player's location
        // until the player hits the wall or arrives the door
        while (playerPosition[0] != world.getGateLocation()[0]
                || playerPosition[1] != world.getGateLocation()[1]) {
            if (StdDraw.hasNextKeyTyped()) {
                char direction = StdDraw.nextKeyTyped();
                if (direction == 'Q' || direction == 'q') {
                    quitGame();
                }
                int xBefore = playerPosition[0];
                int yBefore = playerPosition[1];
                finalWorldFrame[xBefore][yBefore] = Tileset.FLOOR;
                //finalWorldFrame[xBefore][yBefore].draw(xBefore,yBefore);
                switch (direction) {
                    case 'W':
                        playerPosition[1] += 1; // move up
                        break;
                    case 'A':
                        playerPosition[0] -= 1; // move left
                        break;
                    case 'S':
                        playerPosition[1] -= 1; // move down
                        break;
                    case 'D':
                        playerPosition[0] += 1; // move right
                        break;
                    default: break;
                }
                if (world.containsSameItems(world.getWallTiles(), playerPosition)) {
                    gameResult = false;
                    break;
                }
                int xAfter = playerPosition[0];
                int yAfter = playerPosition[1];
                finalWorldFrame[xAfter][yAfter] = Tileset.PLAYER;
                ter.renderFrame(finalWorldFrame);
            } else {
                // diSplay HUD if the mouse has moved
                int xMouseNow = (int) StdDraw.mouseX();
                int yMouseNow = (int) StdDraw.mouseY();
                if (xMouseNow != xMouse || yMouseNow != yMouse) {
                    displayHUD(finalWorldFrame, xMouseNow, yMouseNow);
                    xMouse = xMouseNow;
                    yMouse = yMouseNow;
                }
            }
        }
        gameOver(gameResult);
    }

    private void gameOver(boolean result) {
        StdDraw.setCanvasSize(canvasWide * 16, canvasHeight * 16);
        StdDraw.setXscale(0, canvasWide);
        StdDraw.setYscale(0, canvasHeight);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.text(canvasWide / 2, canvasHeight * 2 / 3, "GAME OVER !");
        if (result) {
            StdDraw.text(canvasWide / 2, canvasHeight / 2, "Congratulation, You win !");
        } else {
            StdDraw.text(canvasWide / 2, canvasHeight / 2, "Bad luck, You lose");
        }
        StdDraw.show();
    }
    private long promptForSeed() {
        StdDraw.clear(Color.black);
        StdDraw.text(canvasWide / 2,canvasHeight * 4 / 7,"Please enter a seed:");
        StdDraw.show();
        String inputSeed = "";
        //wait for user to type seed
        while (!StdDraw.hasNextKeyTyped()) {}
        //wait for user to finish typing a seed
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'S') {
                    break;
                }
                inputSeed += String.valueOf(key);
            }
        }
        return Long.parseLong(inputSeed);
    }

    private void displayMainMenu() {
        StdDraw.setCanvasSize(canvasWide*16, canvasHeight*16);
        StdDraw.setXscale(0, canvasWide);
        StdDraw.setYscale(0, canvasHeight);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.text(canvasWide/2,canvasHeight*2/3,"CS61B: THE GAME");
        Font smallFont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(canvasWide/2,canvasHeight/2,"New Game (N)");
        StdDraw.text(canvasWide/2,27,"Load Game (L)");
        StdDraw.text(canvasWide/2,24,"Quit (Q)");
        StdDraw.show();
    }

    private void displayHUD(TETile[][] finalWorldFrame, int x, int y) {
       // check if mouse is over a valid tile
        if (x >= 0 && x < finalWorldFrame.length && y >= 0 && y < finalWorldFrame[0].length) {
            ter.renderFrame(finalWorldFrame);
            // get the tile object at (i, j)
            TETile tile = finalWorldFrame[x][y];
            // save the original pen color and font
            Color originalColor = StdDraw.getPenColor();
            Font originalFont = StdDraw.getFont();
            // set HUD color as white
            StdDraw.setPenColor(Color.white);
            // display the information of the tile in the top-left corner of the generated world.
            Font tinyFont = new Font("Arial", Font.PLAIN, 10);
            StdDraw.setFont(tinyFont);
            StdDraw.text(3, finalWorldFrame[0].length, tile.description());
            StdDraw.show();
            // restore the original pen color and font
            StdDraw.setPenColor(originalColor);
            StdDraw.setFont(originalFont);
        }
    }

    private static void saveGame (WorldGenerator world) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("world.ser"));
            out.writeObject(world);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static WorldGenerator loadGame() {
        WorldGenerator returnWord = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("world.ser"));
            returnWord = (WorldGenerator) in.readObject();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnWord;
    }
        /* In the case no World has been saved yet, we return a new one. */

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = null;
        char command = input.charAt(0);
        if (command == 'N') {
            finalWorldFrame = beginNewGame(input.toUpperCase());
        } else if (command == 'L') {
            finalWorldFrame = beginLastGame(input.toUpperCase());
        } else if (command == 'Q') {
            System.exit(0);
        }
        return finalWorldFrame;
    }

    public static void main(String[] arg) {
        Game test = new Game();
        test.playWithKeyboard();
        /*
        TETile[][] world = test.playWithInputString("po10");
        test.ter.initialize(WIDTH, HEIGHT);
        test.ter.renderFrame(world);
         */
    }
}
