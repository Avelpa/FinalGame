
import java.awt.Rectangle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class EndLevel extends ObstacleMain{
    public EndLevel(int x, int y, int width, int height)
    {
        super(x, y, width, height, true);
    }
    
    @Override
    public boolean collideX(PlayerMain p)
    {
        Main.levelTransition = true;
        return false;
    }
    @Override
    public boolean collideY(PlayerMain p)
    {
        Main.levelTransition = true;
        return false;
    }
}
