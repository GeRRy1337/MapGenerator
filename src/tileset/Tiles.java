package tileset;
import static tileset.TileType.*;

public enum Tiles {
    GRASS(GRASS_TYPE,GRASS_TYPE,GRASS_TYPE,GRASS_TYPE,4),
    WATER(WATER_TYPE,WATER_TYPE,WATER_TYPE,WATER_TYPE,2),
    EAST_GRASS_WATER(WATER_GRASS_TYPE,GRASS_TYPE,WATER_GRASS_TYPE,WATER_TYPE,1),
    NORTH_EAST_GRASS_WATER(WATER_GRASS_TYPE, GRASS_WATER_TYPE, WATER_TYPE, WATER_TYPE,1),
    NORTH_EAST_WATER_GRASS(GRASS_WATER_TYPE, WATER_GRASS_TYPE, GRASS_TYPE, GRASS_TYPE,1),
    NORTH_GRASS_WATER(GRASS_TYPE,GRASS_WATER_TYPE,WATER_TYPE,GRASS_WATER_TYPE,1),
    NORTH_WEST_GRASS_WATER(GRASS_WATER_TYPE, WATER_TYPE, WATER_TYPE, GRASS_WATER_TYPE,1),
    NORTH_WEST_WATER_GRASS(WATER_GRASS_TYPE, GRASS_TYPE, GRASS_TYPE, WATER_GRASS_TYPE,1),
    SOUTH_EAST_GRASS_WATER(WATER_TYPE, WATER_GRASS_TYPE, WATER_GRASS_TYPE, WATER_TYPE,1),
    SOUTH_EAST_WATER_GRASS(GRASS_TYPE,GRASS_WATER_TYPE,GRASS_WATER_TYPE,GRASS_TYPE,1),
    SOUTH_GRASS_WATER(WATER_TYPE, WATER_GRASS_TYPE, GRASS_TYPE, WATER_GRASS_TYPE,1),
    SOUTH_WEST_GRASS_WATER(WATER_TYPE, WATER_TYPE, GRASS_WATER_TYPE, WATER_GRASS_TYPE,1),
    SOUTH_WEST_WATER_GRASS(GRASS_TYPE,GRASS_TYPE,WATER_GRASS_TYPE,GRASS_WATER_TYPE,1),
    WEST_GRASS_WATER(GRASS_WATER_TYPE, WATER_TYPE, GRASS_WATER_TYPE, GRASS_TYPE,1),
    WEST_EAST_GRASS_WATER(GRASS_WATER_TYPE, WATER_GRASS_TYPE, WATER_GRASS_TYPE, GRASS_WATER_TYPE,1),
    EAST_WEST_GRASS_WATER(WATER_GRASS_TYPE,GRASS_WATER_TYPE,GRASS_WATER_TYPE,WATER_GRASS_TYPE,1);
    
    public final TileType top, right, bottom, left;
    public final int weight;
    
    private Tiles(TileType top, TileType right, TileType bottom, TileType left, int weight) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.weight = weight;
    }
    
    public TileType getSide(String side){
        return switch (side) {
            case "left" -> this.left;
            case "right" -> this.right;
            case "top" -> this.top;
            case "bottom" -> this.bottom;
            default -> null;
        };
    }
  
}














