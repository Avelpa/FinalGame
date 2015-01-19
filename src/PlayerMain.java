
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
public class PlayerMain extends Rectangle implements Drawables{
    
    private Color c;
    private double grav = 0.2;
    private int jumpSpeed; // = 5
    private double yspd = 0;
    private double xspd;
    private double walkSpeed = 3;
    
    private boolean key = false;
    
    private boolean onGround = false;
    
    public PlayerMain(int x, int y, int width, int height, Color c)
    {
        super(x, y, width, height);
        this.c = c;
        
    }
    
    public void update(boolean w, boolean a, boolean d, ArrayList<ObstacleMain>... ObstacleMains)
    {
        
        if (a)
        {
            xspd = -walkSpeed;
        }
        if (d)
        {
            xspd = walkSpeed;
        }
        if (a && d || !a && !d)
        {
            xspd = 0;
        }
        
        if (xspd != 0)
        {
            collisionloop:
            for (int i = 0; i < Math.abs(walkSpeed); i++)
            {
                x += xspd/Math.abs(xspd);
                for (ArrayList<ObstacleMain> os: ObstacleMains)
                {
                    for (ObstacleMain o: os)
                    {
                        if (this.intersects(o))
                        {
                            if (o.collideX(this))
                            {
                                break collisionloop;
                            }
                        }
                    }
                }
                
            }
        }
        
        
        
        yspd += grav;
        if (w)
        {
            if (onGround)
            {
                yspd = jumpSpeed;
            }
        }
        onGround = false;
        jumpSpeed = -5;
        
        collisionloop:
        for (int i = 0; i < Math.abs(yspd); i++)
        {
            y += yspd/Math.abs(yspd);
            for (ArrayList<ObstacleMain> os: ObstacleMains)
            {
                for (ObstacleMain o: os)
                {
                    if (this.intersects(o))
                    {
                        if (o.collideY(this))
                        {
                            break collisionloop;
                        }
                    }
                }
            }
        }
        
    }
    
    @Override
    public void draw(Graphics g, int camx, int camy)
    {
        g.setColor(c);
        if (onGround)
        {
            if (xspd == 0)
            {
                g.fillRect(x + camx, y + camy, width, height);
            }
            else if (xspd > 0)
            {
                g.fillPolygon(new int[] {x + camx, x + camx, x + width + camx}, new int[] {y + camy, y + height + camy, y + height/2 + camy}, 3);
            }
            else
            {
                g.fillPolygon(new int[] {x + camx, x + width + camx, x + width + camx}, new int[] {y + height/2 + camy, y + height + camy, y + camy}, 3);
            }
        }
        else
        {
            g.fillOval(x + camx, y + camy, width, height);
        }
    }

    public void setGrav(double grav)
    {
        this.grav = grav;
    }
    
    public double getYspd() {
        return yspd;
    }

    public void setYspd(double yspd) {
        this.yspd = yspd;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    
    public void setJumpSpeed(int jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public double getXspd() {
        return xspd;
    }
    
    public void setXspd(double xspd) {
        this.xspd = xspd;
    }

    public void setWalkSpeed(double walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public boolean hasKey()
    {
        return key;
    }
    public void setKey(boolean key)
    {
        this.key = key;
    }
}
