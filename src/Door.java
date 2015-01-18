/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class Door extends ObstacleMain{
    
    public Door(int x, int y, int width, int height, String sprite)
    {
        super(x, y, width, height, sprite, true);
    }
    
    @Override
    public boolean collideX(PlayerMain p)
    {
        if (!p.hasKey())
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
            p.setKey(false);
            if (width < height)
            {
                MainGame.drawables.add(0, new Door(x + width/2, y, (int)(height*0.75), height, "images\\doorOpen.png"));
            }
            else
            {
                MainGame.drawables.add(0, new Door(x, y-height/2, width, (int)(width*75), "images\\doorSidewaysOpen.png"));
            }
            deleteThis();
        }
        return true;
    }
    @Override
    public boolean collideY(PlayerMain p)
    {
        if (!p.hasKey())
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
        else
        {
            p.setKey(false);
            if (width < height)
            {
                MainGame.drawables.add(0, new Door(x + width/2, y, width*4, height, "images\\doorOpen.png"));
            }
            else
            {
                MainGame.drawables.add(0, new Door(x, y+height/2-height*4, width, height*4, "images\\doorSidewaysOpen.png"));
            }
            deleteThis();
        }
        return true;
    }
}
