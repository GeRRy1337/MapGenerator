package tileset;

public class RuleSet {
    public static int landmass = 1;
    public static int water = 1;
    public static int edge = 1;
    public static int cross = 1;
    public static int round = 1;
    
    public static int getModifier(Tiles tile){
        return switch(tile){
            case GRASS -> Tiles.GRASS.weight * landmass;
            case WATER -> Tiles.WATER.weight * water;
            case NORTH_GRASS_WATER -> Tiles.NORTH_GRASS_WATER.weight * edge;
            case EAST_GRASS_WATER -> Tiles.EAST_GRASS_WATER.weight * edge;
            case SOUTH_GRASS_WATER -> Tiles.SOUTH_GRASS_WATER.weight * edge;
            case WEST_GRASS_WATER -> Tiles.WEST_EAST_GRASS_WATER.weight * edge;
            case NORTH_WEST_GRASS_WATER -> Tiles.NORTH_WEST_GRASS_WATER.weight * round;
            case NORTH_EAST_GRASS_WATER -> Tiles.NORTH_EAST_GRASS_WATER.weight * round;
            case SOUTH_WEST_GRASS_WATER -> Tiles.SOUTH_WEST_GRASS_WATER.weight * round;
            case SOUTH_EAST_GRASS_WATER -> Tiles.SOUTH_EAST_GRASS_WATER.weight * round;
            case NORTH_WEST_WATER_GRASS -> Tiles.NORTH_WEST_WATER_GRASS.weight * round;
            case NORTH_EAST_WATER_GRASS -> Tiles.NORTH_EAST_WATER_GRASS.weight * round;
            case SOUTH_WEST_WATER_GRASS -> Tiles.SOUTH_WEST_WATER_GRASS.weight * round;
            case SOUTH_EAST_WATER_GRASS -> Tiles.SOUTH_EAST_WATER_GRASS.weight * round;
            case WEST_EAST_GRASS_WATER -> Tiles.WEST_GRASS_WATER.weight*cross;
            case EAST_WEST_GRASS_WATER -> Tiles.EAST_WEST_GRASS_WATER.weight*cross;
                    
        };
    };
}
