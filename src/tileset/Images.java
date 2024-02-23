package tileset;

import javax.swing.ImageIcon;

public class Images {
    public static ImageIcon getImage(Tiles tile){
        
        String path = switch(tile){
            case GRASS -> "Images/grass.png";
            case WATER -> "Images/water.png";
            case EAST_GRASS_WATER -> "Images/EastGrassWater.png";
            case NORTH_EAST_GRASS_WATER -> "Images/NorthEastGrassWater.png";
            case NORTH_GRASS_WATER -> "Images/NorthGrassWater.png";
            case NORTH_WEST_GRASS_WATER -> "Images/NorthWestGrassWater.png";
            case SOUTH_EAST_GRASS_WATER -> "Images/SouthEastGrassWater.png";
            case SOUTH_GRASS_WATER -> "Images/SouthGrassWater.png";
            case SOUTH_WEST_GRASS_WATER -> "Images/SouthWestGrassWater.png";
            case WEST_GRASS_WATER -> "Images/WestGrassWater.png";
        };
        return new ImageIcon(path);
    }
}
