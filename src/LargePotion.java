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
        super(x, y, width, height, sprite);
    }
    
    @Override 
    public void collideX(PlayerMain p)
    {
        p.width /= 2;
        p.height /= 2;
        p.x += p.width/2;
        p.y += p.height/2;
        toDelete = true;
    }
    
    @Override 
    public void collideY(PlayerMain p)
    {
        p.width /= 2;
        p.height /= 2;
        p.x += p.width/2;
        p.y += p.height/2;
        toDelete = true;
    }
    
    
}
