
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
public abstract class Level {
    
    boolean on = false;
    String backgroundName, objectsName;
    
    int camx;
    int initialOffset = 0;
    
    BufferedImage background;
    BufferedImage objects;
    ArrayList<Drawables> drawables;
    int[][] map;
    
    public Level(String oI, String bI)
    {
        this.objectsName = oI;
        this.backgroundName = bI;
    }
    
    public void initializeMap()
    {
        drawables = new ArrayList();
        try
        {
            background = ImageHelper.loadImage(backgroundName);
            objects = ImageHelper.loadImage(objectsName);
            map = new int[background.getHeight()][background.getWidth()];
        }catch (Exception e)
        {
            System.out.println("Error loading level image");
            throw e;
        }
        
        generateLevel();
    }
    
    public void run()
    {
        if (!on)
        {
            initializeMap();
            on = true;
        }
        update();
    }
    
    public void draw(Graphics g)
    {
        if (background != null)
        {
            g.drawImage(background, camx, 0, null);
        }
        if (drawables.size() > 0)
        {
            for (Drawables d: drawables)
            {
                d.draw(g, camx);
            }
        }
    }
    public abstract void keyPress(KeyEvent e);
    
    public abstract void keyRelease(KeyEvent e);
    
    public abstract void generateLevel();
    
    public abstract void update();
    
    // MAP GENERATION
    
    public abstract void fillMap();
    
    public void findRect(int[][] map)
    {
        int[] dimensions;
        
        for (int y = 0; y < map.length; y++)
        {
            for (int x = 0; x < map[y].length; x++)
            {
                if (map[y][x] != 0)
                {
                    dimensions = getRect(map, x, y, map[y][x]);
                    
                    addBlocks(dimensions);

                    x += dimensions[2];
                    
                }
            }
        }
    }
    
    public int[] getRect(int[][] map, int refX, int refY, int target)
    {
        int limitX = map[refY].length, limitY = map.length;
        int w = 0, h = 0, refW = 0;
        boolean wDone = false, scanning = false;
        
        for (int y = refY; y < limitY; y++)
        {
            for (int x = refX; x < limitX; x++)
            {
                
                if (map[y][x] == target)
                {
                    scanning = true;
                    // if width unkown, add width
                    if (!wDone)
                    {
                        w++;
                    }
                    // add reference width anyways
                    refW++;
                    if (x == limitX-1 && y == limitY-1)
                    {
                        int[] dimensions = {refX, refY, w, h+1, target};
                        map[y][x] = 0;
                        return dimensions;
                    }
                    
                    // erase pixel
                    map[y][x] = 0;
                }
                
                else if (map[y][x] != target)
                {
                    // if first out of bounds
                    if (scanning)
                    {
                        if (!wDone)
                        {
                            wDone = true;
                        }
                        // IMPORTANT  -- FILL --- EXCEPTION CASE
                        else
                        {
                            if (refW < w)
                            {
                                for (int i = 1; i < refW+1; i++)
                                {
                                    map[y][x-i] = target;
                                }
                                int[] dimensions = {refX, refY, w, h, target};
                                return dimensions;
                            }
                        }
                        
                        //BOTTOM LINE
                        if (y < limitY-1)
                        {
                            y++;
                        }
                        else
                        {
                            int[] dimensions = {refX, refY, w, h+1, target};
                            return dimensions;
                        }
                        limitX = x;
                        x = refX - 1; // -1 cause loop x++;
                    }
                    
                    else if (!scanning)
                    {
                        int[] dimensions = {refX, refY, w, h, target};
                        return dimensions;
                    }
                    scanning = false;
                    refW = 0;
                    h++;
                    
                }
            }
            if (scanning)
            {
                wDone = true;
                h++;
            }
            refW = 0;
            scanning = false;
        }
        
        return null;
    }
    
    public abstract void addBlocks(int[] dimensions);
    
}
