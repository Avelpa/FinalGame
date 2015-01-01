import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class ObstacleMain extends Rectangle implements Drawables{
    
    private Color c;
    
    public ObstacleMain(int x, int y, int width, int height, Color c)
    {
        super(x, y, width ,height);
        this.c = c;
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
        g.setColor(c);
        g.fillRect(x + camx, y, width ,height);
    }
    
}
