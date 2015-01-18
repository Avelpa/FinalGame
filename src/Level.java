
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Level {
    
    boolean restartable = true;
    boolean on = false; // used to judge initialiseLevel time
    String backgroundName, objectsName; // paths to images
    
    int camx, camy; 
    int initialOffsetX = 0; // initial camera offset X
    int initialOffsetY = 0; // initla camera offset Y
    
    BufferedImage background;
    BufferedImage objects;
    static ArrayList<Drawables> drawables; // list of all the drawable objects in the game (implement Drawables)
    int[][] map; // the pixel map that will be processed by the map reader
    
    public Level(String oI, String bI)
    {
        this.objectsName = oI;
        this.backgroundName = bI;
    }
    
    public void initializeMap() // run first time map starts
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
        
        generateLevel(); // generate level-specific stuff
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
            g.drawImage(background, camx, camy, null);
        }
        if (drawables.size() > 0)
        {
            for (Drawables d: drawables)
            {
                d.draw(g, camx, camy); // once again, since drawable objects implement Drawables, drawing is very easy
            }
        }
    }
    public abstract void keyPress(KeyEvent e); // used to obtain keyPresses from the Main class
    
    public abstract void keyRelease(KeyEvent e); // used to obtain keyReleases from the Main class
    
    public abstract void generateLevel(); // generates level-specific stuff
    
    public abstract void update(); // run every loop
    
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
                    dimensions = getRect(map, x, y, map[y][x]); // [x, y, width, height, "pixel]
                    
                    addBlocks(dimensions); // this is level specific. it adds all the right integer "pixels" to the map
                    // based on the requriements of each level itself

                    x += dimensions[2]; // += width of previous block
                    
                }
            }
        }
    }
    
    public int[] getRect(int[][] map, int refX, int refY, int target)
    {
        int limitX = map[refY].length, limitY = map.length;
        int w = 0, h = 0, refW = 0; // w = width of block, h = height of block, refW = explained in its usage
        boolean wDone = false, scanning = false;
        
        for (int y = refY; y < limitY; y++)
        {
            for (int x = refX; x < limitX; x++)
            {
                
                if (map[y][x] == target) // target is the "pixel" identifier
                {
                    scanning = true; // scannign means inside black
                    // if width unkown, add width
                    if (!wDone) // if width is still undetermined
                    {
                        w++;
                    }
                    // add reference width regardless of whether width is known or not
                    refW++;
                    if (x == limitX-1 && y == limitY-1) // if reach bottom of screen/scan
                    {
                        map[y][x] = 0;
                        return new int[] {refX, refY, w, h+1, target};
                    }
                    
                    // erase pixel
                    map[y][x] = 0;
                }
                
                else if (map[y][x] != target) // if pixel is 0
                {
                    // if previous pixel was target
                    if (scanning)
                    {
                        if (!wDone) // since previous was target and width is undefined, it's now defined
                        {
                            wDone = true;
                        }
                        else
                        {
                            if (refW < w) // used case1
                            {
                                for (int i = 1; i < refW+1; i++)
                                {
                                    map[y][x-i] = target; // rollsback the scanning, changes it back to target
                                }
                                //int[] dimensions = {refX, refY, w, h, target};
                                return new int[] {refX, refY, w, h, target};
                            }
                        }
                        
                        //BOTTOM LINE
                        if (y < limitY-1) // makes sure scan doesn't go out of bounds since refY is map.length
                        {
                            y++;
                        }
                        else
                        {
                            return new int[] {refX, refY, w, h+1, target};
                        }
                        limitX = x;
                        x = refX - 1; // resets the scan, makes x = -1 cause loop x++;
                    }
                    
                    else if (!scanning)
                    {
                        return new int[] {refX, refY, w, h, target};
                    }
                    scanning = false;
                    refW = 0;
                    h++;
                    
                }
            }
            
            // takes care of horizontal scan
            if (scanning)
            {
                wDone = true;
                h++;
            }
            refW = 0;
            scanning = false;
        }
        
        return null;
        
        // this entire method is not well structured, but contructing the map scanner recursively proved too hard
        // I will improve on this in the future
    }
    
    public abstract void addBlocks(int[] dimensions); // based on the blocks' "id" (1, 2, 3....) adds the appropriate
                                                        // in-game object (ie. door, obstacle, bouncy obstacle...)
    
}
