
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Target extends Rectangle implements Drawables{
    Color[] colors = {new Color(163, 73, 164), new Color(255, 201, 14), new Color(0, 162, 232)};
    int color = 0;
    int rotateTimer = 0;
    int rotateTime = 8;
    BufferedImage sprite;
    int angle = 0;
    BufferedImage rotSprite;
    public Target(int x, int y, int width, String sprite)
    {
        super(x, y, width, width);
        this.sprite = ImageHelper.loadImage(sprite);
        this.sprite = ImageHelper.resize(this.sprite, width, width);
        rotSprite = this.sprite;
        
    }
    
    public void turn()
    {
        
        if (rotateTimer == 0)
        {
            color ++;
            if (color == colors.length)
            {
                color = 0;
            }
            rotateTimer = rotateTime;
        }
    }
    
    public int checkCollide(ArrayList<Bullet> bullets, ArrayList<Drawables> d)
    {
        Iterator<Bullet> bIt = bullets.iterator();
        while (bIt.hasNext())
        {
            Bullet b = bIt.next();
            if (this.intersects(b))
            {
                if (this.intersection(b).height == b.height)
                {
                    if (b.getColor().getRGB() != colors[color].getRGB())
                    {
                        return -1;
                    }
                    d.remove(b);
                    bIt.remove();
                    return 1;
                }
            }
        }
        return 0;
    }
    

    @Override
    public void draw(Graphics g, int camx, int camy) {
//        g.setColor(colors[color]);
//        g.fillRect(x, y, width, height);
        
        if (rotateTimer > 0)
        {
            angle += 120/rotateTime;
            rotSprite = ImageHelper.rotate(sprite, angle);
            rotateTimer --;
        }
        g.drawImage(rotSprite, x, y-20, null);
    }
    
    
}
