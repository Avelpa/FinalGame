
public class BouncyMain extends ObstacleMain{
    
    public BouncyMain(int x, int y, int width, int height, boolean solidCeiling)
    {
        super(x, y, width ,height, solidCeiling);
    }
    
    @Override
    public boolean collideY(PlayerMain p)
    {
        if (p.getYspd() > 0)
        {
            p.y -= this.intersection(p).height;
            p.setYspd(-10);
        }
        else if (p.getYspd() < 0)
        {
            if (solidCeiling)
            {
                p.y += this.intersection(p).height;
                p.setYspd(0);
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    
    
}
