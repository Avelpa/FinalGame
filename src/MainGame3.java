
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class MainGame3 extends MainGame{
    
    public MainGame3(String oI, String bI)
    {
        super(oI, bI);
    }
    
    
    @Override
    public void generateLevel() {
        initialOffsetX = -2;
        initialOffsetY = Main.HEIGHT - background.getHeight();
        
        blocks = new ArrayList();
        
        fillMap();
        findRect(map);
        
        p.setGrav(0.05);
        
    }
}
