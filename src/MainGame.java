
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class MainGame extends Level{
    
    static ArrayList<ObstacleMain> blocks; // all the obstacles in the game inherit ObstacleMain
    PlayerMain p;
    boolean w, a, d;
    
    BufferedImage key = ImageHelper.loadImage("images\\key.png");
    
    public MainGame(String oI, String bI)
    {
        super(oI, bI);
    }
    
    @Override
    public void generateLevel() {
        initialOffsetX = -2;
        initialOffsetY = Main.HEIGHT - background.getHeight();
        
        blocks = new ArrayList();
        
        fillMap();
        findRect(map);
        
        
    }

    @Override
    public void keyPress(KeyEvent e)
    {
        if (Character.toLowerCase(e.getKeyChar()) == 'w' || e.getKeyCode() == KeyEvent.VK_UP)
        {
            w = true;
        }
        if (Character.toLowerCase(e.getKeyChar()) == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            a = true;
        }
        if (Character.toLowerCase(e.getKeyChar()) == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            d = true;
        }
    }
    @Override
    public void keyRelease(KeyEvent e)
    {
        if (Character.toLowerCase(e.getKeyChar()) == 'w' || e.getKeyCode() == KeyEvent.VK_UP)
        {
            w = false;
        }
        if (Character.toLowerCase(e.getKeyChar()) == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            a = false;
        }
        if (Character.toLowerCase(e.getKeyChar()) == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            d = false;
        }
    }
    
    @Override
    public void update() {
        p.update(w, a, d, blocks);
        
        
        //camx = (camx > initialOffsetX ? initialOffsetX: camx < (Main.WIDTH - background.getWidth()) ? (Main.WIDTH - background.getWidth()) : camx);
        
        camx = Main.WIDTH/2 - p.x - p.width/2;
        if (camx > initialOffsetX)
        {
            camx = initialOffsetX;
        }
        else if (camx < Main.WIDTH - background.getWidth())
        {
            camx = Main.WIDTH - background.getWidth();
        }
        
        
        camy = Main.HEIGHT/2 - p.y - p.height/2;
        
        if (camy < initialOffsetY)
        {
            camy = initialOffsetY;
        }
        if (camy > 0)
        {
            camy = 0;
        }
        // cameras constantly attempt to center the player, and don't if the player is at the extremes of the map
        
        
    }

    @Override
    public void addBlocks(int[] dimensions) {
        int id = dimensions[4];
        if (id == 1) // normal block
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
            blocks.add(new BouncyMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], true));
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
    }

    @Override
    public void fillMap() {
        
        for (int y = 0; y < objects.getHeight(); y++)
        {
            for (int x = 0; x < objects.getWidth(); x++)
            {
                if (objects.getRGB(x, y) == -16777216) // (0, 0, 0) -- normal block
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
                
            }
        }
    }
    
    @Override
    public void draw(Graphics g)
    {
        if (background != null)
        {
            g.drawImage(background, camx, camy, null);
        }
        if (drawables != null && drawables.size() > 0)
        {
            for (Drawables d: drawables)
            {
                d.draw(g, camx, camy); // once again, since drawable objects implement Drawables, drawing is very easy
            }
        }
        if (p != null && p.hasKey())
        {
            g.setColor(new Color(100, 100, 100, 100));
            g.fillRect(15, 15, key.getWidth(), key.getHeight());
            g.drawImage(key, 15, 15, null);
        }
    }
}
