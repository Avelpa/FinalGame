import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;


// make sure you rename this class if you are doing a copy/paste
public class Main extends JComponent implements KeyListener{

    boolean done = false; 
    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    Font font1 = new Font("Times New Roman", Font.PLAIN, 60);
    BufferedImage menuImage = ImageHelper.loadImage("images\\mainMenu.png");
        
    Level[] games = {new MainGame("images\\level1.png", "images\\level1Background.png"), new Triangle("images\\Triangle.png", "images\\TriangleBackground.png"), new MainGame2("images\\level2.png", "images\\level2Background.png"),
    new Falling("images\\empty.png", "images\\FallingBackground.png"),
    new MainGame3("images\\level3.png", "images\\level3Background.png"),
    new MainGame2("images\\level4.png", "images\\level4Background.png"),
    new MainGame("images\\level5.png", "images\\level5Background.png"),
    new MainGame("images\\test.png", "images\\testBackground.png")};
    int level = 0;
    static boolean levelTransition;
    int levelTransitionCounter;
    
    boolean mainMenu = true;
    
    boolean r;
    
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        // GAME DRAWING GOES HERE 
        if (!mainMenu)
        {
            if (!levelTransition)
            {
                games[level].draw(g);
                if (r)
                {
                    g.setColor(new Color(14, 240, 193));
                    g.fillRect(WIDTH/2-210, 0, 310, 90);
                    g.setFont(font1);
                    g.setColor(Color.RED);
                    g.drawString("restart?\n(y/n)", WIDTH/2-200, 50);
                }
            }
            else
            {
                g.setColor(new Color(14, 240, 193));
                g.fillRect(WIDTH/2-210, 0, 420, 90);
                g.setFont(font1);
                g.setColor(Color.RED);
                g.drawString("NEXT    LEVEL", WIDTH/2-200, 50);
            }
        }
        // mainmenu
        else
        {
            g.drawImage(menuImage, 0, 0, null);
        }
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        // the main game loop section
        // game will end if you set done = false;
        
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            if (!mainMenu)
            {
                if (levelTransitionCounter > 0)
                {
                    levelTransitionCounter --;
                }
                else if (levelTransition)
                {
                    levelTransition = false;
                    level ++;
                    if (level == games.length)
                    {
                        level = 0; // loops the game -- didn't add game end screen
                    }
                }

                // level transition + restart conditions
                if (!r && !levelTransition )
                {
                    games[level].run();
                    if (levelTransition)
                    {
                        levelTransitionCounter = 120; // transition takes 2 game seconds
                    }
                }
            }
            
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("Final Game");
       
        // creates an instance of my game
        Main game = new Main();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        
        // starts my game loop
        game.run();
        frame.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!mainMenu)
        {
            games[level].keyPress(e); // passes key pressed into current level
        }
        
        // restart thing
        if (Character.toLowerCase(e.getKeyChar()) == 'r')
        {
            if (!mainMenu && games[level].restartable)
            {
                r = true;
            }
        }
        if (r)
        {
            if (Character.toLowerCase(e.getKeyChar()) == 'y')
            {
                r = false;
                games[level].initializeMap();
            }
            if (Character.toLowerCase(e.getKeyChar()) == 'n')
            {
                r = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            mainMenu = (mainMenu ? false: true);
        }
        if (Character.toLowerCase(e.getKeyChar()) == 'q' && mainMenu)
        {
            done = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        games[level].keyRelease(e);
    }
}