package run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import tileset.Tile;
import tileset.TileType;
import tileset.Tiles;

public class Generator {

    static Tile[][] tileMatrix = new Tile[50][50];
    static Stack<int[]> changed = new Stack<>();
    static final ArrayList<Tiles> values = new ArrayList<>(List.of(Tiles.values()));

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                tileMatrix[i][j] = new Tile();
            }
        }

        while (waveFunctionCollapse());
        printEntropy();
    }

    public static void printMatrix() {
        for (Tile[] row : tileMatrix) {
            for (Tile tile : row) {
                System.out.print(tile.toString() + " ");
            }
            System.out.println("");
        }
    }

    public static void printEntropy() {
        for (Tile[] row : tileMatrix) {
            for (Tile i : row) {
                System.out.print(i.entropy + " ");
            }
            System.out.println("");
        }
    }

    public static boolean waveFunctionCollapse() {
        ArrayList<int[]> lowestEntropies = getLowestEntropies();
        if (lowestEntropies.isEmpty()) {
            return false;
        }
        Random random = new Random();
        
        int rand = random.nextInt(lowestEntropies.size());
        int[] coords = lowestEntropies.get(rand);

        //int sumWeight = tileMatrix[coords[1]][coords[0]].goodTiles.stream().map(element -> element.weight).reduce(0, (acc, e) -> acc + e);
        Tile currTile = tileMatrix[coords[1]][coords[0]];
        ArrayList<Tiles> currGoods = currTile.goodTiles;

        int sumWeight = 0;
        for (Tiles t : currGoods) {
            sumWeight += t.weight;
        }

        rand =  random.nextInt(sumWeight);
        int idx = 0;
        while ((rand -= currGoods.get(idx).weight) > 0) {
            idx++;
        }

        currTile.tile = currGoods.get(idx);

        Stack<int[]> stack = new Stack<>();
        stack.push(coords);
        changed.add(coords);
        updateEntropy(coords[0], coords[1]);
        while (!stack.isEmpty()) {
            int[] pair = stack.pop();

            boolean updated;
            //top
            int[] currNeighbour = new int[]{pair[0], pair[1] - 1};
            updated = updateEntropy(currNeighbour[0], currNeighbour[1]);
            if (updated) {
                stack.add(currNeighbour);
                changed.add(currNeighbour);
            }
            //bottom
            currNeighbour = new int[]{pair[0], pair[1] + 1};
            updated = updateEntropy(currNeighbour[0], currNeighbour[1]);
            if (updated) {
                stack.add(currNeighbour);
                changed.add(currNeighbour);
            }

            //left
            currNeighbour = new int[]{pair[0] - 1, pair[1]};
            updated = updateEntropy(currNeighbour[0], currNeighbour[1]);
            if (updated) {
                stack.add(currNeighbour);
                changed.add(currNeighbour);
            }

            //right
            currNeighbour = new int[]{pair[0] + 1, pair[1]};
            updated = updateEntropy(currNeighbour[0], currNeighbour[1]);
            if (updated) {
                stack.add(currNeighbour);
                changed.add(currNeighbour);
            }
        }

        return true;
    }

    private static ArrayList<int[]> getLowestEntropies() {
        ArrayList<int[]> list = new ArrayList<>();
        int lowestEntropy = values.size();
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                int tileEntropy = tileMatrix[y][x].entropy;
                if (tileEntropy > 0) {
                    if (tileEntropy < lowestEntropy) {
                        list.clear();
                        lowestEntropy = tileEntropy;
                    }
                    if (tileEntropy == lowestEntropy) {
                        list.add(new int[]{x, y});
                    }
                }
            }
        }

        return list;
    }

    private static boolean updateEntropy(int x, int y) {
        if (!inBounds(x, y)) {
            return false;
        }
        if (tileMatrix[y][x].tile != null) {
            if (tileMatrix[y][x].entropy != 0) {
                tileMatrix[y][x].entropy = 0;
                tileMatrix[y][x].goodTiles.clear();
                return true;
            }
        } else {
            ArrayList<Tiles> newGoodTiles = getGoodTiles(x, y);
            int newEntropy = newGoodTiles.size();
            if (newEntropy == 0) {
                return false;
            }
            if (tileMatrix[y][x].entropy != newEntropy) {
                tileMatrix[y][x].entropy = newEntropy;
                tileMatrix[y][x].goodTiles = newGoodTiles;
                return true;
            }
        }
        return false;
    }

    private static boolean inBounds(int i, int j) {
        return i >= 0 && i < 50 && j >= 0 && j < 50;
    }

    /**
     * Returns the possible tiles for the coordinates
     *
     * @param i x coordinate of the tile
     * @param j y coordinate of the tile
     * @return an ArrayList of the possible tiles for the coordinates
     */
    private static ArrayList<Tiles> getGoodTiles(int i, int j) {

        HashSet<Tiles> topTiles = new HashSet<>();
        HashSet<Tiles> bottomTiles = new HashSet<>();
        HashSet<Tiles> leftTiles = new HashSet<>();
        HashSet<Tiles> rightTiles = new HashSet<>();

        int x = i;
        int y = j - 1;
        //top
        if (inBounds(x, y)) {
            if (tileMatrix[y][x].tile == null) {
                for (Tiles tiles : tileMatrix[y][x].goodTiles) {
                    topTiles.addAll(searchConnecting("top", tiles.bottom));
                }
            } else {
                topTiles.addAll(searchConnecting("top", tileMatrix[y][x].tile.bottom));
            }
        }

        //bottom
        y = j + 1;
        if (inBounds(x, y)) {
            if (tileMatrix[y][x].tile == null) {
                for (Tiles tiles : tileMatrix[y][x].goodTiles) {
                    bottomTiles.addAll(searchConnecting("bottom", tiles.top));
                }
            } else {
                bottomTiles.addAll(searchConnecting("bottom", tileMatrix[y][x].tile.top));
            }
        }

        //left
        y = j;
        x = i - 1;
        if (inBounds(x, y)) {
            if (tileMatrix[y][x].tile == null) {
                for (Tiles tiles : tileMatrix[y][x].goodTiles) {
                    leftTiles.addAll(searchConnecting("left", tiles.right));
                }
            } else {
                leftTiles.addAll(searchConnecting("left", tileMatrix[y][x].tile.right));
            }
        }

        //right
        x = i + 1;

        if (inBounds(x, y)) {
            if (tileMatrix[y][x].tile == null) {
                for (Tiles tiles : tileMatrix[y][x].goodTiles) {
                    rightTiles.addAll(searchConnecting("right", tiles.left));
                }
            } else {
                rightTiles.addAll(searchConnecting("right", tileMatrix[y][x].tile.left));
            }
        }

        ArrayList<Tiles> goodTiles = new ArrayList<>(values);
        if (!topTiles.isEmpty()) {
            goodTiles.retainAll(topTiles);
        }
        if (!bottomTiles.isEmpty()) {
            goodTiles.retainAll(bottomTiles);
        }
        if (!leftTiles.isEmpty()) {
            goodTiles.retainAll(leftTiles);
        }
        if (!rightTiles.isEmpty()) {
            goodTiles.retainAll(rightTiles);
        }
        return goodTiles;
    }

    private static ArrayList<Tiles> searchConnecting(String side, TileType tt) {
        ArrayList<Tiles> connecting = new ArrayList<>();

        for (Tiles currTile : values) {
            if (currTile.getSide(side).equals(tt)) {
                connecting.add(currTile);
            }
        }

        return connecting;
    }
    /*
    private static ArrayList<Tiles> searchConnecting(String side, TileType tt) {
        return new ArrayList<>(values.stream().filter(tile -> tile.getSide(side).equals(tt)).toList());
    }*/
}
