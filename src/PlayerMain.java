
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
    private int jumpSpeed;
    private double yspd = 0;
    private double xspd;
    private double walkSpeed = 3;
    
    private boolean onGround = false;
    
    public PlayerMain(int x, int y, int width, int height, Color c)
    {
        super(x, y, width, height);
        this.c = c;
        //walkSpeed = 6;
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
                            o.collideX(this);
                            break collisionloop;
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
                        o.collideY(this);
                        break collisionloop;
                    }
                }
            }
        }
        
    }
    
    @Override
    public void draw(Graphics g, int camx)
    {
        g.setColor(c);
        g.fillRect(x + camx, y, width, height);
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
    
    
    
}
