package tileset;

import javax.swing.ImageIcon;

public class Images {
    public static ImageIcon getImage(Tiles tile){
        String temp[] = tile.name().split("_");
        String name = "";
        for(String s:temp){
            name += s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return new ImageIcon("Images/"+name+".png");
    }
}
