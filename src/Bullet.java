
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
public class Bullet extends Rectangle implements Drawables{
    Color[] colors = {new Color(0, 162, 232), new Color(255, 201, 14), new Color(163, 73, 164)};
    Color c;
    public Bullet(int x, int y, int radius)
    {
        super(x, y, radius, radius);
        c = colors[(int)(Math.random()*colors.length)];
    }

    public void move(int speed)
    {
        y += speed;
    }
    
    @Override
    public void draw(Graphics g, int camx, int camy) {
        g.setColor(c);
        g.fillOval(x, y, width, height);
    }
    
    public Color getColor()
    {
        return c;
    }
}
