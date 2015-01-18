
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class Falling extends Level{

    Font font1 = new Font("Times New Roman", Font.PLAIN, 60);
    ArrayList<FallingPlatform> blocks = new ArrayList();
    FallingPlayer p;
    int[] times = {70, 50, 35, 50, 30, 25, 15};
    int[] speeds = {-2,-3, -3, -4, -4, -4, -5};
    int[] nums;
    int stage = 0;
    int timer = 0;
    
    int goal = times.length;
    
    boolean restart = false;
    
    public Falling(String oI, String bI)
    {
        super (oI, bI);
        restartable = false;
    }
    
    @Override
    public void keyPress(KeyEvent e) {
        if (p != null)
        {
            p.move(e.getKeyChar());
        }
    }

    @Override
    public void keyRelease(KeyEvent e) {
    }

    @Override
    public void generateLevel() {
        blocks.clear();
        drawables.clear();
        
        timer = 0;
        if (stage+1 >= 6)
        {
            stage = 5;
        }
        else if (stage+1 >= 4)
        {
            stage = 3;
        }
        else
        {
            stage = 0;
        }
        
        
        nums = new int[] {5, 5, 10, 10, 10, 15, 5};
        
        restart = false;
        
        p = new FallingPlayer(Main.WIDTH/2-50, 50, 100, 100);
        drawables.add(p);
        
    }

    @Override
    public void update() {
        if (timer == 0 && nums[stage] > 0)
        {
            blocks.add(new FallingPlatform(0, Main.HEIGHT, Main.WIDTH, 30, p.width));
            drawables.add(0, blocks.get(blocks.size()-1));
            timer = times[stage];
        }
        else if (timer > 0)
        {
            timer--;
        }
        for (FallingPlatform b: blocks)
        {
            b.move(speeds[stage]);
            if (b.intersects(p) && !b.getFade())
            {
                if (p.getPos() == b.getTarget())
                {
                    b.setFade(true);
                    nums[stage] --;
                }
                else
                {
                    restart = true;
                }
                break;
            }
        }
        
        Iterator<FallingPlatform> it = blocks.iterator();
        while (it.hasNext())
        {
            FallingPlatform temp = it.next();
            if (temp.y + temp.height <= 0)
            {
                drawables.remove(temp);
                it.remove();
            }
        }
        
        if (nums[stage] <= 0 && blocks.isEmpty())
        {
            stage ++;
            if (stage+1 > goal)
            {
                Main.levelTransition = true;
                drawables.clear();
            }
        }
        
        if (restart)
        {
            generateLevel();
        }
    }

    @Override
    public void fillMap() {
    }

    @Override
    public void addBlocks(int[] dimensions) {
    }
    
    @Override 
    public void draw(Graphics g)
    {
        if (background != null)
        {
            g.drawImage(background, camx, 0, null);
        }
        if (drawables.size() > 0)
        {
            for (Drawables d: drawables)
            {
                d.draw(g, camx, camy);
            }
        }
        g.setFont(font1);
        g.setColor(Color.WHITE);
        g.drawString("Wave: " + (stage+1) + "/" + goal, 0, Main.HEIGHT/2);
    }
    
    
}
