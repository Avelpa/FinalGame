/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class Key extends ObstacleMain{
    public Key(int x, int y, int width, int height, String sprite)
    {
        super (x, y, width, height, sprite, true);
    }
    @Override
    public boolean collideX(PlayerMain p)
    {
        p.setKey(true);
        deleteThis();
        return true;
    }
    @Override
    public boolean collideY(PlayerMain p)
    {
        p.setKey(true);
        deleteThis();
        return true;
    }
}
