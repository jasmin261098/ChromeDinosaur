import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //to store all cactus
import javax.swing.*;

public class ChromeDinosaur extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 750;
    int boardHeight = 250;

    //images
    Image dinosaurImg;
    Image dinosaurDeadImg;
    Image dinosaurJumpImg;
    Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;

    //inner Class to specify all the image positions
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //dinosaur dimensions
    int dinosaurWidth = 88;
    int dinosaurHeight = 94;
    int dinosaurX = 50;
    int dinosaurY = boardHeight - dinosaurHeight;

    Block dinosaur;

    //physics
    int velocityY = 0; //dinosaur jump speed
    int gravity = 1; //positive numbers means downwards

    Timer gameLoop;


    public ChromeDinosaur() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.lightGray);
        setFocusable(true); //can listen to space input
        addKeyListener(this); //this reveres to the ChromeDinosaur Class, adding the Listener to the Panel

        //load images
        dinosaurImg = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
        dinosaurDeadImg = new ImageIcon(getClass().getResource("./img/dino-dead.png")).getImage();
        dinosaurJumpImg = new ImageIcon(getClass().getResource("./img/dino-jump.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./img/cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./img/cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./img/cactus3.png")).getImage();

        //dinosaur
        dinosaur = new Block(dinosaurX, dinosaurY, dinosaurWidth, dinosaurHeight, dinosaurImg);

        //game loop
        gameLoop = new Timer(1000/60, this); //1000/60 = 60 frames per 1000ms (1s), update
        gameLoop.start();
    }

    //draw images on the Panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);
    }

    //handle movement updates on the screen
    public void move() {
        velocityY += gravity; 
        dinosaur.y += velocityY;

        if (dinosaur.y > dinosaurY) {
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }
    }

    //game loop action listener recalling draw
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); //update positions
        repaint();
    }

    //Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (dinosaur.y == dinosaurY) { //dinosaur only jumps when on the ground
                velocityY = -17;
                dinosaur.img = dinosaurJumpImg;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} //not used

    @Override
    public void keyReleased(KeyEvent e) {} //not used
}
