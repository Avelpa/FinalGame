
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
public class FallingPlayer extends Rectangle implements Drawables{
    
    private int pos;
    
    public FallingPlayer(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        pos = 2;
    }
    
    public void move(char key)
    {
        if (key == '1')
        {
            x = width;
            pos = 1;
        }
        if (key == '2')
        {
            x = Main.WIDTH/2 - width/2;
            pos = 2;
        }
        if (key == '3')
        {
            x = Main.WIDTH - width*2;
            pos = 3;
        }
    }

    @Override
    public void draw(Graphics g, int camx, int camy) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public int getPos() {
        return pos;
    }
    
    
    
}
