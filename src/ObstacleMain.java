import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class ObstacleMain extends Rectangle implements Drawables{
    
    private Color c;
    private BufferedImage sprite;
    protected boolean toDelete = false;
    
    public ObstacleMain(int x, int y, int width, int height, Color c)
    {
        super(x, y, width ,height);
        this.c = c;
    }
    public ObstacleMain(int x, int y, int width, int height, String spriteName)
    {
        super(x, y, width ,height);
        c = null;
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
    
    public void collideX(PlayerMain p)
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
    
    public void collideY(PlayerMain p)
    {
        if (p.getYspd() > 0)
        {
            p.y -= this.intersection(p).height;
            p.setOnGround(true);
        }
        else if (p.getYspd() < 0)
        {
            p.y += this.intersection(p).height;
        }
        p.setYspd(0);
    }
    
    @Override
    public void draw(Graphics g, int camx)
    {
        if (c != null)
        {
            g.setColor(c);
            g.fillRect(x + camx, y, width ,height);
        }
        else
        {
            g.drawImage(sprite, x+camx, y, null);
        }
    }
    
}
