import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    double[] ldppLevels;
    int maxDepth;


    public Rasterer() {
        maxDepth = 8;
        ldppLevels = new double[maxDepth];
        ldppLevels[0] = 0.00034332275390625;
        for (int i = 1; i < maxDepth; i++) {
            ldppLevels[i] = ldppLevels[i-1] / 2;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           //+ "your browser.");
        boolean querySuccess = checkQuery(params);
        if (querySuccess != true) {
            results.put("render_grid", null);
            results.put("raster_ul_lon", 0);
            results.put("raster_ul_lat", 0);
            results.put("raster_lr_lon", 0);
            results.put("raster_lr_lat", 0);
            results.put("depth", 0);
            results.put("query_success", false);
            return results;
        }
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double w = params.get("w");
        double lonDppQuery = (lrlon - ullon) / w;
        int depth = getDepth(lonDppQuery);
        double width = ldppLevels[depth] * MapServer.TILE_SIZE;
        double height = height(depth);
        int xStart = (int) ((ullon - MapServer.ROOT_ULLON) / width);
        if (xStart < 0) {
            xStart = 0;
        }
        int xEnd = (int) ((lrlon - MapServer.ROOT_ULLON) / width);
        if (xEnd >= Math.pow(2, depth)) {
            xEnd = (int) Math.pow(2, depth) - 1;
        }
        int yStart = (int) ((MapServer.ROOT_ULLAT - ullat) / height);
        if (yStart < 0) {
            yStart = 0;
        }
        int yEnd = (int) ((MapServer.ROOT_ULLAT - lrlat) / height);
        if (yEnd >= Math.pow(2, depth)) {
            yEnd = (int) Math.pow(2, depth) - 1;
        }
        int gridWidth = xEnd - xStart + 1;
        int gridLength = yEnd - yStart + 1;
        String[][] grid = new String[gridLength][gridWidth];
        for (int j = 0; j < gridLength; j++) {
            for (int i = 0; i < gridWidth; i++) {
                grid[j][i] = String.format("d%d_x%d_y%d.png",depth,xStart + i,yStart + j);
            }
        }
        results.put("render_grid", grid);
        double[] upLeft = boundingBox(depth, xStart, yStart);
        double[] lowRight = boundingBox(depth,xEnd, yEnd);
        results.put("raster_ul_lon", upLeft[0]);
        results.put("raster_ul_lat", upLeft[1]);
        results.put("raster_lr_lon", lowRight[2]);
        results.put("raster_lr_lat", lowRight[3]);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;
    }

    private int getDepth(double lonDppQuery) {
        for (int i = 0; i < maxDepth; i++) {
            if (lonDppQuery >= ldppLevels[i]) {
                return i;
            }
        }
        return maxDepth - 1;
    }

    private double[] boundingBox (int depth, int x, int y) {
        double ulLON = MapServer.ROOT_ULLON;
        double lrLON = MapServer.ROOT_LRLON;
        double ulLAT = MapServer.ROOT_ULLAT;
        double lrLAT = MapServer.ROOT_LRLAT;
        double[] bounding = new double[4];
        int tileNumEachRow = (int) Math.pow(2, depth);
        bounding[0] = ulLON + ((lrLON -ulLON) / tileNumEachRow) * x; //up left longitude
        bounding[1] = ulLAT - ((ulLAT - lrLAT) / tileNumEachRow) * y; // up left latitude
        bounding[2] = bounding[0] + (lrLON -ulLON) /tileNumEachRow; //low right longitude
        bounding[3] = bounding[1] - ((ulLAT - lrLAT) / tileNumEachRow); // low right latitude
        return bounding;
    }

    private double height(int depth) {
        double[] sample = boundingBox(depth, 0, 0);
        double coefficient = (sample[2] - sample[0]) / (sample[1] - sample[3]);
        double width = ldppLevels[depth] * MapServer.TILE_SIZE;
        return width / coefficient;
    }

    private boolean checkQuery (Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double w = params.get("w");
        double h = params.get("h");
        if (ullon > MapServer.ROOT_LRLON
                || lrlon < MapServer.ROOT_ULLON
                || ullat < MapServer.ROOT_LRLAT
                || lrlat > MapServer.ROOT_ULLAT) {
            return false;
        }
        if (ullon > lrlon || ullat < lrlat) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Rasterer test = new Rasterer();
        Map<String, Double> params = new HashMap<>();
        params.put("lrlon", -122.20908713544797);
        params.put("ullon", -122.3027284165759);
        params.put("w", 305.0);
        params.put("h", 300.0);
        params.put("ullat", 37.88708748276975);
        params.put("lrlat", 37.848731523430196);
        Map<String, Object> result = test.getMapRaster(params);
        System.out.println(result.get("render_grid"));
    }

}
