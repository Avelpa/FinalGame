
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class MainGame2 extends MainGame {
    public MainGame2(String oI, String bI)
    {
        super(oI, bI);
    }
    
    @Override 
    public void addBlocks(int[] dimensions)
    {
        int id = dimensions[4];
        if (id == 1) // normal block solidCeiling = true;
        {
            blocks.add(new ObstacleMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], true));
        }
        if (id == 2) // player
        {
            p = (new PlayerMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], Color.ORANGE));
            drawables.add(p);
        }
        if (id == 3) // trampoline
        {
            blocks.add(new BouncyMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], false));
        }
        if (id == 4) // LargePotion
        {
            blocks.add(new LargePotion(dimensions[0], dimensions[1], dimensions[2], dimensions[3], "images\\largePotion.png"));
            drawables.add(blocks.get(blocks.size()-1));
        }
        if (id == 5) // SmallPotion
        {
            blocks.add(new SmallPotion(dimensions[0], dimensions[1], dimensions[2], dimensions[3], "images\\smallPotion.png"));
            drawables.add(blocks.get(blocks.size()-1));
        }
        if (id == 6) // Door
        {
            if (dimensions[3] > dimensions[2])
            {
                blocks.add(new Door(dimensions[0], dimensions[1], dimensions[2], dimensions[3], "images\\door.png"));
            }
            else
            {
                blocks.add(new Door(dimensions[0], dimensions[1], dimensions[2], dimensions[3], "images\\doorSideways.png"));
            }
            
            drawables.add(blocks.get(blocks.size()-1));
        }
        if (id == 7) // Key
        {
            blocks.add(new Key(dimensions[0], dimensions[1], dimensions[2], dimensions[3], "images\\key.png"));
            drawables.add(blocks.get(blocks.size()-1));
        }
        if (id == 8) // Endlevel
        {
            blocks.add(new EndLevel(dimensions[0], dimensions[1], dimensions[2], dimensions[3]));
        }
        if (id == 9) // normal block solidCeiling = false;
        {
            blocks.add(new ObstacleMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], false));
        }
    }
    @Override
    public void fillMap() {
        
        for (int y = 0; y < objects.getHeight(); y++)
        {
            for (int x = 0; x < objects.getWidth(); x++)
            {
                if (objects.getRGB(x, y) == -16777216) // (0, 0, 0) -- normal block solidCeiling = true;
                {
                    map[y][x] = 1;
                }
                if (objects.getRGB(x, y) == -14066) // (255, 201, 14) -- player
                {
                    map[y][x] = 2;
                }
                if (objects.getRGB(x, y) == -14503604) // (34, 177, 76) trampoline
                {
                    map[y][x] = 3;
                }
                
                if (objects.getRGB(x, y) == -4856291) // (34, 177, 76) large potion
                {
                    map[y][x] = 4;
                }
                if (objects.getRGB(x, y) == -6694422) // (153, 217, 234) small potion
                {
                    map[y][x] = 5;
                }
                if (objects.getRGB(x, y) == -4621737) // (185, 122, 87) door
                {
                    map[y][x] = 6;
                }
                if (objects.getRGB(x, y) == -3584) // (255, 242, 0) key
                {
                    map[y][x] = 7;
                }
                if (objects.getRGB(x, y) == -1055568) // (239, 228, 176) end level
                {
                    map[y][x] = 8;
                }
                if (objects.getRGB(x, y) == -8421505) // (127, 127, 127) -- normal block solidCeiling = false;
                {
                    map[y][x] = 9;
                }
                
            }
        }
    }
}
