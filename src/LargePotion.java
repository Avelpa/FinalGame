/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class LargePotion extends ObstacleMain{
    public LargePotion(int x, int y, int width, int height, String sprite)
    {
        super(x, y, width, height, sprite, true);
    }
    
    @Override 
    public boolean collideX(PlayerMain p) // shrinks player
    {
        p.width /= 2;
        p.height /= 2;
        p.x += p.width/2;
        p.y += p.height/2;
        deleteThis();
        return true; // unfortunately the way it's structured i can't return false after deleteThis()
                    // FORTUNATELY since deleteThis() is called this block is not called the next tick
    }
    
    @Override 
    public boolean collideY(PlayerMain p)
    {
        p.width /= 2;
        p.height /= 2;
        p.x += p.width/2;
        p.y += p.height/2;
        deleteThis();
        return true;
    }
    
    
}
