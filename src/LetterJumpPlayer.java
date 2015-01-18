
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class LetterJumpPlayer extends Rectangle implements Drawables{

    
    private double grav = 0.3;
    private double yspd = 0;
    
    private Color c;
    
    public LetterJumpPlayer(int x, int y, int width, int height)
    {
        super (x, y, width, height);
        c = Color.GREEN;
    }
    
    public boolean update(boolean w, boolean a, boolean s, boolean d, ArrayList<LetterJumpBlock> blocks) // true if valid press (score ++)
    {
        yspd += grav;
        
        boolean validHit = false;
        
        char colChar = (w ? 'w' : a ? 'a': s ? 's': d ? 'd' : 'g' );
        
        for (LetterJumpBlock b: blocks)
        {
            if (this.intersects(b))
            {
                if (b.getId() == colChar)
                {
                    if (!b.getHit())
                    {
                        if (b.getSuperJump())
                        {
                            yspd = -9;
                        }
                        else
                        {
                            yspd = -6;
                        }
                        b.setHit(true);
                        validHit = true;
                    }
                }
            }
        }
        
        y += yspd;
        
        return validHit;
    }
    
    @Override
    public void draw(Graphics g, int camx, int camy) {
        g.setColor(c);
        g.fillRect(x + camx, y + camy, width, height);
    }
    
    public void setGrav(double grav)
    {
        this.grav = grav;
    }
    
}
