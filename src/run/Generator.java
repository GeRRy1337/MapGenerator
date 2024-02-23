package run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import tileset.TileType;
import tileset.Tiles;
import static tileset.Tiles.GRASS;

public class Generator {

    static Tiles[][] tileMatrix = new Tiles[50][50];
    static int entropy[][] = new int[50][50];

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                entropy[i][j] = 10;
            }
        }
        entropy[24][24] = 0;
        tileMatrix[24][24] = GRASS;
        updateEntropy(24, 24);
        printEntropy();
    }

    public static void printMatrix() {
        for (Tiles[] row : tileMatrix) {
            for (Tiles tile : row) {
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
    
    public static ArrayList<Tiles> getGoodTiles(int i, int j){
        if(!inBounds(i,j) || tileMatrix[j][i] != null){return new ArrayList();}
        //check top
        int x = i;
        int y = j-1;
        ArrayList<Tiles> topTiles = new ArrayList();
        if(inBounds(x,y)){
            if (entropy[y][x] == 10){
                topTiles = new ArrayList<>(List.of(Tiles.values()));
            }else if(entropy[y][x] == 0){
                topTiles = new ArrayList<>();
                topTiles.add(tileMatrix[y][x]);
            }else{
                topTiles = getGoodTiles(x, y);
            }
        }
        
        //bottom
        x = i;
        y = j+1;
        ArrayList<Tiles> bottomTiles = new ArrayList();
        if(inBounds(x,y)){
            if (entropy[y][x] == 10){
                bottomTiles = new ArrayList<>(List.of(Tiles.values()));
            }else if(entropy[y][x] == 0){
                bottomTiles = new ArrayList<>();
                bottomTiles.add(tileMatrix[y][x]);
            }else{
                bottomTiles = getGoodTiles(x, y);
            }
        }
        
        //left
        x = i-1;
        y = j;
        ArrayList<Tiles> leftTiles = new ArrayList();
        if(inBounds(x,y)){
            if (entropy[y][x] == 10){
                leftTiles = new ArrayList<>(List.of(Tiles.values()));
            }else if(entropy[y][x] == 0){
                leftTiles = new ArrayList<>();
                leftTiles.add(tileMatrix[y][x]);
            }else{
                leftTiles = getGoodTiles(x, y);
            }
        }
        
        //right
        x = i+1;
        y = j;
        ArrayList<Tiles> rightTiles = new ArrayList();
        if(inBounds(x,y)){
            if (entropy[y][x] == 10){
                rightTiles = new ArrayList<>(List.of(Tiles.values()));
            }else if(entropy[y][x] == 0){
                rightTiles = new ArrayList<>();
                rightTiles.add(tileMatrix[y][x]);
            }else{
                rightTiles = getGoodTiles(x, y);
            }
        }
        
        HashSet<Tiles> goodTiles = new HashSet();
        
        for(Tiles tile : topTiles){
            if(tile != null)
                goodTiles.addAll(searchConnecting("top", tile.bottom));
        }
        for(Tiles tile : bottomTiles){
            if(tile != null)
                goodTiles.retainAll(searchConnecting("bottom", tile.top));
        }
        for(Tiles tile : leftTiles){
            if(tile != null)
                goodTiles.retainAll(searchConnecting("left", tile.right));
        }
        for(Tiles tile : rightTiles){
            if(tile != null)
                goodTiles.retainAll(searchConnecting("right", tile.left));
        }
        
        return new ArrayList<>(goodTiles.stream().toList());
    }

    public static ArrayList<Tiles> searchConnecting(String side, TileType tt) {
        return new ArrayList<> (List.of(Tiles.values()).stream().filter(tile -> tile.getSide(side).equals(tt)).toList());
    }
}
