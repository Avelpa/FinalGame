import java.awt.Color;

public class BouncyMain extends ObstacleMain{
    
    public BouncyMain(int x, int y, int width, int height, Color c)
    {
        super(x, y, width ,height, c);
    }
    
    @Override
    public void collideY(PlayerMain p)
    {
        if (p.getYspd() > 0)
        {
            p.y -= this.intersection(p).height;
            p.setOnGround(true);
            p.setJumpSpeed(-9);
        }
        else if (p.getYspd() < 0)
        {
            p.y += this.intersection(p).height;
        }
        p.setYspd(0);
    }
    
    
}
