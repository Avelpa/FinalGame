
import java.awt.Color;
import java.awt.event.KeyEvent;
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
    
    ArrayList<ObstacleMain> blocks = new ArrayList<>();
    PlayerMain p;
    boolean w, a, d;
    
    public MainGame(String oI, String bI)
    {
        super(oI, bI);
    }
    
    @Override
    public void generateLevel() {
        initialOffset = -2;
        camx = initialOffset;
    }

    @Override
    public void keyPress(KeyEvent e)
    {
        if (e.getKeyChar() == 'w')
        {
            w = true;
        }
        if (e.getKeyChar() == 'a')
        {
            a = true;
        }
        if (e.getKeyChar() == 'd')
        {
            d = true;
        }
        if (e.getKeyChar() == 'r')
        {
            initializeMap();
        }
    }
    @Override
    public void keyRelease(KeyEvent e)
    {
        if (e.getKeyChar() == 'w')
        {
            w = false;
        }
        if (e.getKeyChar() == 'a')
        {
            a = false;
        }
        if (e.getKeyChar() == 'd')
        {
            d = false;
        }
    }
    
    @Override
    public void update() {
        p.update(w, a, d, blocks);
        if (p.x - camx > Main.WIDTH/2)
        {
            if (background.getWidth() + camx - Main.WIDTH > 0)
            {
                camx -= p.getXspd();
            }
            else if (p.x + camx < Main.WIDTH/2 && p.getXspd() < 0)
            {
                camx -= p.getXspd();
            }
        }
        else
        {
            
            camx = initialOffset;
        }
        

    }

    @Override
    public void addBlocks(int[] dimensions) {
        int id = dimensions[4];
        if (id == 1) // normal block
        {
            blocks.add(new ObstacleMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], Color.BLACK));
        }
        if (id == 2) // player
        {
            p = (new PlayerMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], Color.ORANGE));
            drawables.add(p);
        }
        if (id == 3) // trampoline
        {
            blocks.add(new BouncyMain(dimensions[0], dimensions[1], dimensions[2], dimensions[3], Color.BLACK));
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
                /*
                if (objects.getRGB(x, y) == -4856291) // (34, 177, 76) large potion
                {
                    map[y][x] = 4;
                }
                if (objects.getRGB(x, y) == -6694422) // (153, 217, 234) small potion
                {
                    map[y][x] = 5;
                }*/
            }
        }
    }
}
