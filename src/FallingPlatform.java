
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class FallingPlatform extends Rectangle implements Drawables{
    
    private final int target;
    private final int targetWidth;
    private boolean fade;
    private int transparency = 255;
    
    public FallingPlatform(int x, int y, int width, int height, int playerWidth)
    {
        super(x, y, width, height);
        target = (int)(Math.random()*3)+1;
        targetWidth = playerWidth;
    }
    
    public void move(int speed)
    {
        y += speed;
    }

    @Override
    public void draw(Graphics g, int camx, int camy) {
        if (fade)
        {
            if (transparency > 0 && y > 0)
            {
                transparency -= transparency/y;
            }
            g.setColor(new Color(0, 0, 0, transparency));
        }
        else
        {
            g.setColor(Color.BLACK);
        }
        g.fillRect(x, y, width, height);
        
        if (fade)
        {
            g.setColor(new Color(255, 0, 0, transparency));
        }
        else
        {
            g.setColor(new Color(255, 0, 0));
        }
        switch(target)
        {
            case 1:
                g.fillRect(targetWidth, y, targetWidth, height);
                break;
            case 2:
                g.fillRect(Main.WIDTH/2 - targetWidth/2, y, targetWidth, height);
                break;
            case 3:
                g.fillRect(Main.WIDTH - targetWidth*2, y, targetWidth, height);
                break;
        }
    }
    
    public int getTarget()
    {
        return target;
    }
    
    public void setFade(boolean fade)
    {
        this.fade = fade;
    }
    
    public boolean getFade()
    {
        return fade;
    }
    
    
}
