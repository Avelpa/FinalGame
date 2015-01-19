
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class Sprite implements Drawables{
    
    private int x, y, width, height;
    private BufferedImage[] sprites;
    private int curSprite = 0;
    
    private int animLength;
    
    private int animationCounter;
    private int timePerSprite;

    public Sprite(int x, int y, int width, int height, String spriteSheet, int rows, int cols, int animLength)
    {
        this.sprites = ImageHelper.splitImage(ImageHelper.loadImage(spriteSheet), rows, cols);
        for (int i = 0; i < sprites.length; i++)
        {
            sprites[i] = ImageHelper.resize(sprites[i], width, height);
        }
        this.x = x;
        this.y = y;
        this.animLength = animLength;
        animationCounter = animLength;
        timePerSprite = animLength/sprites.length;
    }
    
    @Override
    public void draw(Graphics g, int camx, int camy) {
        g.drawImage(sprites[curSprite], x + camx, y + camy, null);
        animationCounter --;
        if (animationCounter % timePerSprite == 0)
        {
            curSprite ++;
            if (curSprite == sprites.length)
            {
                curSprite = 0;
            }
        }
        if (animationCounter == 0)
        {
            animationCounter = animLength;
        }
        
    }
    
}
