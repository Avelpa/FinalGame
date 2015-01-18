
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Triangle extends Level{

    private boolean space;
    ArrayList<Bullet> bullets = new ArrayList();
    Target target;
    int spawnTimer = 0;
    
    int score = 0;
    
    Font font1 = new Font("Times New Roman", Font.PLAIN, 60);
    
    int[] levels = {3,8,10,9,5};
    int goal = 0;
    int stage = 0;
    int[] speeds = {3, 4, 5, 6, 8};
    int level = 0;
    int[] spawnTimes = {120, 70, 50, 50, 50};
    
    int[] spawnPoint = new int[3];
    
    public Triangle(String oI, String bI)
    {
        super(oI, bI);
        restartable = false;
    }
    
    @Override
    public void keyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if (!space && target != null)
            {
                target.turn();
                space = true;
            }
        }
    }

    @Override
    public void keyRelease(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            space = false;
        }
    }

    @Override
    public void generateLevel() {
        drawables.clear();
        bullets.clear();
        
        stage = 0;
        level = 0;
        score = 0;
        
        goal = 0;
        for (Integer i: levels)
        {
            goal += i;
        }
        
        fillMap();
        findRect(map);
    }

    @Override
    public void update() {
        for (Bullet b: bullets)
        {
            b.move(speeds[level]);
        }
        int collideResult = target.checkCollide(bullets, drawables);
        if (collideResult > 0)
        {
            score ++;
        }
        if (collideResult < 0)
        {
            initializeMap();
        }
        
        
        if (spawnTimer == 0)
        {
            bullets.add(new Bullet(spawnPoint[0], spawnPoint[1], spawnPoint[2]));
            drawables.add(0,bullets.get(bullets.size()-1));
            stage ++;
            if (stage > levels[level])
            {
                stage = 0;
                level ++;
            }
            spawnTimer = spawnTimes[level];
        }
        else
        {
            spawnTimer --;
        }
        if (score == goal)
        {
            Main.levelTransition = true;
        }
    }

    @Override
    public void fillMap() {
        
        for (int y = 0; y < objects.getHeight(); y++)
        {
            for (int x = 0; x < objects.getWidth(); x++)
            {
                if (objects.getRGB(x, y) == -16777216) // (0, 0, 0) -- target
                {
                    map[y][x] = 1;
                }
                if (objects.getRGB(x, y) == -14503604) // (34, 177, 76) -- bullet
                {
                    map[y][x] = 2;
                }
            }
        }
    }

    @Override
    public void addBlocks(int[] dimensions) {
        int id = dimensions[4];
        if (id == 1)
        {
            target = new Target(dimensions[0], dimensions[1], dimensions[2], "images\\target.png");
            drawables.add(target);
        }
        if (id == 2)
        {
            spawnPoint[0] = dimensions[0];
            spawnPoint[1] = dimensions[1];
            spawnPoint[2] = dimensions[2];
        }
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
        g.setColor(Color.RED);
        g.drawString("Score: " + score + "/" + goal, 0, Main.HEIGHT/2);
    }
    
}
