package run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import tileset.Tile;
import tileset.TileType;
import tileset.Tiles;
import static tileset.Tiles.GRASS;

public class Generator {

    static Tile[][] tileMatrix = new Tile[50][50];
    static int entropy[][] = new int[50][50];

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                entropy[i][j] = 10;
            }
        }
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                tileMatrix[i][j] = new Tile();
            }
        }
        entropy[24][24] = 0;
        tileMatrix[24][24].tile = GRASS;
        updateEntropy(24, 24);
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
        for (int[] row : entropy) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println("");
        }
    }

    public static void updateEntropy(int i, int j) {
        if(!inBounds(i,j)) {return;}
        //top
        int x = i;
        int y = j-1;
        int newEntropy = getGoodTiles(x,y).size();
        if (inBounds(x,y) && entropy[y][x] != newEntropy){
            entropy[y][x] = newEntropy;
            updateEntropy(x,y);
        }
        //bottom
        y = j+1;
        newEntropy = getGoodTiles(x,y).size();
        if (inBounds(x,y) && entropy[y][x] != newEntropy){
            entropy[y][x] = newEntropy;
            updateEntropy(x,y);
        }
        //left
        y = j;
        x = i-1;
        newEntropy = getGoodTiles(x,y).size();
        if (inBounds(x,y) && entropy[y][x] != newEntropy){
            entropy[y][x] = newEntropy;
            updateEntropy(x,y);
        }
        //right
        x = i-1;
        newEntropy = getGoodTiles(x,y).size();
        if (inBounds(x,y) && entropy[y][x] != newEntropy){
            entropy[y][x] = newEntropy;
            updateEntropy(x,y);
        }
    }

    public static boolean inBounds(int i, int j) {
        return i >= 0 && i < 50 && j >= 0 && j < 50;
    }

    public static void waveFunctionCollapse() {

    }
    
    /**
     * 
     * @param i x coordinate of the tile
     * @param j y coordinate of the tile
     * @return an ArrayList<Tiles> of the possible tiles for the coordinates
     */
    public static ArrayList<Tiles> getGoodTiles(int i, int j){
        
        ArrayList<Tiles> topTiles = new ArrayList<>(); 
        ArrayList<Tiles> bottomTiles = new ArrayList<>();
        ArrayList<Tiles> leftTiles = new ArrayList<>(); 
        ArrayList<Tiles> rightTiles = new ArrayList<>(); 
        
        
        return new ArrayList<>();
    }
    
    public static ArrayList<Tiles> searchConnecting(String side, TileType tt) {
        return new ArrayList<> (List.of(Tiles.values()).stream().filter(tile -> tile.getSide(side).equals(tt)).toList());
    }
}
