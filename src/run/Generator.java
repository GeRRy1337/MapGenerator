package run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import tileset.Tile;
import tileset.TileType;
import tileset.Tiles;


public class Generator {

    static Tile[][] tileMatrix = new Tile[50][50];
    static ArrayList<int[]> changed = new ArrayList<>();
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                tileMatrix[i][j] = new Tile();
            }
        }
        
        while(waveFunctionCollapse());
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
        
        if(lowestEntropies.isEmpty()){
            return false;
        }
        
        int rand = (int) Math.round(Math.random()*(lowestEntropies.size()-1));
        int[] coords = lowestEntropies.get(rand);
        
        rand = (int) Math.round(Math.random()*(tileMatrix[coords[1]][coords[0]].goodTiles.size()-1));
        
        tileMatrix[coords[1]][coords[0]].tile = tileMatrix[coords[1]][coords[0]].goodTiles.get(rand);
        
        Stack<int[]> stack = new Stack<>();
        stack.push(coords);
        changed.add(coords);
        while(!stack.isEmpty()){
            int[] pair = stack.pop();
            updateEntropy(pair[0],pair[1]);
            
            boolean updated;
            //top
            updated = updateEntropy(pair[0],pair[1]-1);
            if(updated){
                stack.add(new int[]{pair[0],pair[1]-1});
                changed.add(new int[]{pair[0],pair[1]-1});
            }
            //bottom
            updated = updateEntropy(pair[0],pair[1]+1);
            if(updated){
                stack.add(new int[]{pair[0],pair[1]+1});
                changed.add(new int[]{pair[0],pair[1]+1});
            }
            
            //left
            updated = updateEntropy(pair[0]-1,pair[1]);
            if(updated){
                stack.add(new int[]{pair[0]-1,pair[1]});
                changed.add(new int[]{pair[0]-1,pair[1]});
            }
            
            //right
            updated = updateEntropy(pair[0]+1,pair[1]);
            if(updated){
                stack.add(new int[]{pair[0]+1,pair[1]});
                changed.add(new int[]{pair[0]+1,pair[1]});
            }
        }
        
        return true;
    }

    private static ArrayList<int[]> getLowestEntropies(){
        ArrayList<int[]> list = new ArrayList<>();
        int lowestEntropy = Tiles.values().length;
        for (int y = 0; y < 50; y++){
            for(int x=0; x < 50; x ++){
                int tileEntropy = tileMatrix[y][x].entropy;
                if(tileEntropy > 0){
                    if (tileEntropy < lowestEntropy){
                        list.clear();
                        lowestEntropy = tileEntropy;
                    }
                    if(tileEntropy == lowestEntropy){
                        list.add(new int[]{x,y});
                    }
                }
            }
        }
        
        return list;
    }
    
    private static boolean updateEntropy(int x, int y) {
        if(!inBounds(x,y)) {return false;}
        if(tileMatrix[y][x].tile != null) {
            if(tileMatrix[y][x].entropy != 0){
                tileMatrix[y][x].entropy = 0;
                tileMatrix[y][x].goodTiles.clear();
                return true;
            }
        }else{
            ArrayList<Tiles> newGoodTiles = getGoodTiles(x,y);
            int newEntropy = newGoodTiles.size();
            if(newEntropy == 0){
                return false;
            }
            if (inBounds(x,y) && tileMatrix[y][x].entropy != newEntropy){
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
     * 
     * @param i x coordinate of the tile
     * @param j y coordinate of the tile
     * @return an ArrayList of the possible tiles for the coordinates
     */
    private static ArrayList<Tiles> getGoodTiles(int i, int j){
        
        HashSet<Tiles> topTiles = new HashSet<>(); 
        HashSet<Tiles> bottomTiles = new HashSet<>(); 
        HashSet<Tiles> leftTiles = new HashSet<>(); 
        HashSet<Tiles> rightTiles = new HashSet<>(); 
        boolean top=false;
        boolean bottom=false;
        boolean left=false;
        boolean right=false;
        
        int x = i;
        int y = j-1;
        //top
        if(inBounds(x,y)){
            if(tileMatrix[y][x].tile == null){
                for(Tiles tiles: tileMatrix[y][x].goodTiles){
                    topTiles.addAll(searchConnecting("top", tiles.bottom));
                }
            }else{
                topTiles.clear();
                topTiles.addAll(searchConnecting("top",tileMatrix[y][x].tile.bottom));
            }
            top=true;
        }
        
        //bottom
        y = j+1;
        if(inBounds(x,y)){
            if(tileMatrix[y][x].tile == null){
                for(Tiles tiles: tileMatrix[y][x].goodTiles){
                    bottomTiles.addAll(searchConnecting("bottom", tiles.top));
                }
            }else{
                bottomTiles.clear();
                bottomTiles.addAll(searchConnecting("bottom",tileMatrix[y][x].tile.top));
            }
            bottom=true;
        }
        
        //left
        y = j;
        x = i-1;
        if(inBounds(x,y)){
            if(tileMatrix[y][x].tile == null){
                for(Tiles tiles: tileMatrix[y][x].goodTiles){
                    leftTiles.addAll(searchConnecting("left", tiles.right));
                }
            }else{
                leftTiles.clear();
                leftTiles.addAll(searchConnecting("left",tileMatrix[y][x].tile.right));
            }
            left=true;
        }
        
        //right
        x = i+1;

        if(inBounds(x,y)){
            if(tileMatrix[y][x].tile == null){
                for(Tiles tiles: tileMatrix[y][x].goodTiles){
                    rightTiles.addAll(searchConnecting("right", tiles.left));
                }
            }else{
                rightTiles.clear();
                rightTiles.addAll(searchConnecting("right",tileMatrix[y][x].tile.left));
            }
            right=true;
        }
        
        ArrayList<Tiles> goodTiles = new ArrayList<>(List.of(Tiles.values())); 
        if(top)
            goodTiles.retainAll(topTiles);
        if(bottom)
            goodTiles.retainAll(bottomTiles);
        if(left)
            goodTiles.retainAll(leftTiles);
        if(right)
            goodTiles.retainAll(rightTiles);
        return goodTiles;
    }
    
    private static ArrayList<Tiles> searchConnecting(String side, TileType tt) {
        return new ArrayList<> (List.of(Tiles.values()).stream().filter(tile -> tile.getSide(side).equals(tt)).toList());
    }
}
