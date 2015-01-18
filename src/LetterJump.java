
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class LetterJump extends Level{

    boolean w, a, s, d;
    int speed = -4;
    
    int score = 0;
    
    
    
    
    Font font1 = new Font("Times New Roman", Font.PLAIN, 60);
    
    int[] blockY = {150, 300};
    
    int[] spawnTimes = {61, 50};
    int goal = 0;
    int timer;
    int[] counts = {5,5};
    int stage = 0;
    int curCount = 0;
    double[] gravs = {0.3,0.20};
    
    
    ArrayList<LetterJumpBlock> blocks = new ArrayList();
    LetterJumpPlayer p;
    public LetterJump(String oI, String bI)
    {
        super(oI, bI);
    }
    
    @Override
    public void keyPress(KeyEvent e) {
        if (e.getKeyChar() == 'w')
        {
            w = true;
        }
        if (e.getKeyChar() == 'a')
        {
            a = true;
        }
        if (e.getKeyChar() == 's')
        {
            s = true;
        }
        if (e.getKeyChar() == 'd')
        {
            d = true;
        }
    }

    @Override
    public void keyRelease(KeyEvent e) {
        if (e.getKeyChar() == 'w')
        {
            w = false;
        }
        if (e.getKeyChar() == 'a')
        {
            a = false;
        }
        if (e.getKeyChar() == 's')
        {
            s = false;
        }
        if (e.getKeyChar() == 'd')
        {
            d = false;
        }
    }

    @Override
    public void generateLevel() {
        initialOffsetX = 0;
        initialOffsetY = 0;
        blocks.clear();
        drawables.clear();
        
        p = new LetterJumpPlayer(Main.WIDTH/2-50, 0, 50, 50);
        drawables.add(p);
        
        blocks.add(new LetterJumpBlock(p.x+200, p.y+300, 100, 100));
        drawables.add(0,blocks.get(blocks.size()-1));
        blocks.add(new LetterJumpBlock(p.x+400, p.y+300, 100, 100));
        drawables.add(0,blocks.get(blocks.size()-1));
        
        score = 0;
        
        goal = 0;
        for (Integer n: counts)
        {
            goal += n;
        }
        
        stage = 0;
        curCount = 0;
    }

    @Override
    public void update() {
        
        if (p.update(w, a, s, d, blocks))
        {
            score ++;
        }
        w = false;
        a = false;
        s = false;
        d = false;
        
        Iterator<LetterJumpBlock> bIt = blocks.iterator();
        while (bIt.hasNext())
        {
            LetterJumpBlock b = bIt.next();
            b.update(speed);
            if (b.x + b.width <= 0)
            {
                drawables.remove(b);
                bIt.remove();
            }
        }
        if (timer == 0)
        {
            //blocks.add(new LetterJumpBlock(Main.WIDTH, (int)(Math.random()*(Main.HEIGHT-100-blocks.get(blocks.size()-1).y)+blocks.get(blocks.size()-1).y-100), 100, 100));
            blocks.add(new LetterJumpBlock(Main.WIDTH, blockY[(int)(Math.random()*(blockY.length))], 100, 100));

            drawables.add(0,blocks.get(blocks.size()-1));
            curCount ++;
            if (curCount > counts[stage])
            {
                System.out.println("hi");
                curCount = 0;
                stage ++;
            }
            timer = spawnTimes[stage];
        }
        else if (timer > 0)
        {
            timer --;
        }
        
        if (p.y > Main.HEIGHT)
        {
            generateLevel();
        }
        
        if (score == goal)
        {
            Main.levelTransition = true;
        }
    }

    @Override
    public void fillMap() {
    }

    @Override
    public void addBlocks(int[] dimensions) {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(255, 200, 150));
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        if (drawables.size() > 0)
        {
            for (Drawables d: drawables)
            {
                d.draw(g, camx, camy);
            }
        }
        
        g.setFont(font1);
        g.setColor(Color.RED);
        g.drawString("Score: " + score + "/" + goal, Main.WIDTH/2 - 100, 50);
    }
    
}
