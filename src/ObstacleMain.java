import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class ObstacleMain extends Rectangle implements Drawables{
    
    private Color c;
    protected boolean solidCeiling;
    protected BufferedImage sprite;
    
    public ObstacleMain(int x, int y, int width, int height, Color c, boolean solidCeiling)
    {
        super(x, y, width ,height);
        this.c = c;
        this.solidCeiling = solidCeiling; // whether the player can jump through the block or not (up)
    }
    public ObstacleMain(int x, int y, int width, int height, boolean solidCeiling)
    {
        super(x, y, width ,height);
        this.solidCeiling = solidCeiling;
    }
    public ObstacleMain(int x, int y, int width, int height, String spriteName, boolean solidCeiling)
    {
        super(x, y, width ,height);
        c = null;
        this.solidCeiling = solidCeiling;
        try
        {
            sprite = ImageHelper.loadImage(spriteName);
            sprite = ImageHelper.resize(sprite, width, height);
        }
        catch(Exception e)
        {
            System.out.println("Error Loading ObstacleMain sprite");
            throw e;
        }
    }
    
    public boolean collideX(PlayerMain p) // if the collision should be solid (the player must be pushed back) return true
                                        // else return false
    {
        if (solidCeiling)
        {
            if (p.getXspd() > 0)
            {
                p.x -= this.intersection(p).width;
            }
            else if (p.getXspd() < 0)
            {
                p.x += this.intersection(p).width;
            }
            p.setXspd(0);
        }
        else
        {
            return false;
        }
        return true;
    }
    
    public boolean collideY(PlayerMain p) // same stuff as above
    {
        if (p.getYspd() > 0)
        {
            p.y -= this.intersection(p).height;
            p.setOnGround(true);
            p.setYspd(0);
        }
        else if (p.getYspd() < 0)
        {
            if (solidCeiling)
            {
                p.y += this.intersection(p).height;
                p.setYspd(0);
            }
            else
            {
                return false;
            }
        }
        return true;
        
    }
    
    public void deleteThis() // delete the block from both blocks and drawables
    {
        MainGame.blocks.remove(this);
        MainGame.drawables.remove(this);
    }
    
    @Override
    public void draw(Graphics g, int camx, int camy)
    {
        if (c != null)
        {
            g.setColor(c);
            g.fillRect(x + camx, y + camy, width ,height);
        }
        else
        {
            g.drawImage(sprite, x+camx, y + camy, null);
        }
    }
}
