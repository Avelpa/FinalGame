
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class LetterJumpBlock extends Rectangle implements Drawables{

    private Color c;
    
    private char[] ids = {'w', 'a', 's', 'd'};
    
    private boolean hit = false;
    
    private BufferedImage sprite;
    private BufferedImage hitSprite;
    private char id;
    private boolean superJump;
    public LetterJumpBlock(int x, int y, int width, int height)
    {
        super (x, y, width, height);
        
        c = Color.RED;
        id = ids[(int)(Math.random()*ids.length)];
        superJump = true;
        
        this.sprite = ImageHelper.loadImage("images\\" + id + "Block.png");
        this.hitSprite = ImageHelper.loadImage("images\\" + id + "BlockHit.png");
    }
    
    public void update(int speed)
    {
        x += speed;
    }
    
    @Override
    public void draw(Graphics g, int camx, int camy) {
        //g.setColor(c);
        //g.fillRect(x, y, width, height);
        if (!hit)
        {
            g.drawImage(sprite, x, y, null);
        }
        else
        {
            g.drawImage(hitSprite, x, y, null);
        }
    }
    
    public char getId()
    {
        return id;
    }
    public boolean getSuperJump()
    {
        return superJump;
    }
    public void setId(char id)
    {
        this.id = id;
    }
    
    public void setHit(boolean hit)
    {
        this.hit = hit;
    }
    public boolean getHit()
    {
        return hit;
    }
}
