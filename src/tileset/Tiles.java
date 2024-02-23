package tileset;
import static tileset.TileType.*;

public enum Tiles {
    GRASS(GRASS_TYPE,GRASS_TYPE,GRASS_TYPE,GRASS_TYPE),
    WATER(WATER_TYPE,WATER_TYPE,WATER_TYPE,WATER_TYPE),
    EAST_GRASS_WATER(WATER_GRASS_TYPE,GRASS_TYPE,WATER_GRASS_TYPE,WATER_TYPE),
    NORTH_EAST_GRASS_WATER(WATER_GRASS_TYPE, GRASS_WATER_TYPE, WATER_TYPE, WATER_TYPE),
    NORTH_GRASS_WATER(GRASS_TYPE,GRASS_WATER_TYPE,WATER_TYPE,GRASS_WATER_TYPE),
    NORTH_WEST_GRASS_WATER(GRASS_WATER_TYPE, WATER_TYPE, WATER_TYPE, GRASS_WATER_TYPE),
    SOUTH_EAST_GRASS_WATER(WATER_TYPE, WATER_GRASS_TYPE, WATER_GRASS_TYPE, WATER_TYPE),
    SOUTH_GRASS_WATER(WATER_TYPE, WATER_GRASS_TYPE, GRASS_TYPE, WATER_GRASS_TYPE),
    SOUTH_WEST_GRASS_WATER(WATER_TYPE, WATER_TYPE, GRASS_WATER_TYPE, WATER_GRASS_TYPE),
    WEST_GRASS_WATER(GRASS_WATER_TYPE, WATER_TYPE, GRASS_WATER_TYPE, GRASS_TYPE);
    
    public final TileType top, right, bottom, left;
    
    private Tiles(TileType top, TileType right, TileType bottom, TileType left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
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














