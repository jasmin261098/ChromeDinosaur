import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //to store all cactus
import javax.swing.*;

public class ChromeDinosaurGameLogic extends JPanel implements ActionListener, KeyListener {
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

    //cactus dimensions
    int cactus1Width = 34;
    int cactus2Width = 69;
    int cactus3Width = 102;
    int cactusHeight = 70;
    int cactusX = 700;
    int cactusY = boardHeight - cactusHeight;
    ArrayList<Block> cactusArray;

    //physics
    int velocityX = -12; //cactus moving left speed
    int velocityY = 0; //dinosaur jump speed
    int gravity = 1; //positive numbers means downwards

    boolean gameOver = false;
    int score = 0;

    Timer gameLoop;
    Timer placeCactusTimer;


    public ChromeDinosaurGameLogic() {
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
        //cactus
        cactusArray = new ArrayList<Block>();

        //game loop
        gameLoop = new Timer(1000/60, this); //1000/60 = 60 frames per 1000ms (1s), update
        gameLoop.start();

        //place cactus timer
        placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeCactus();
            }
        });
        placeCactusTimer.start();
    }

    void placeCactus() {
        if (gameOver) {
            return;
        }

        double placeCactusChance = Math.random(); // 0-0.99999
        if (placeCactusChance > .90) { //10% chance to get cactus 3
            Block cactus = new Block(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
            cactusArray.add(cactus);
        } else if (placeCactusChance > .70) { //20% chance to get cactus 2
            Block cactus = new Block(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
            cactusArray.add(cactus);
        } else if (placeCactusChance > .50) { //20% chance to get cactus 1
            Block cactus = new Block(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
            cactusArray.add(cactus);
        } //50% chance to actually get a cactus

        if (cactusArray.size() > 10) {
            cactusArray.remove(0); // remove the first cactus from the ArrayList
        }
    }

    //draw images on the Panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //dinosaur
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);

        //cactus - interaring over the cactus array and drawing each of them 
        for (int i = 0; i < cactusArray.size(); i++) {
            Block cactus = cactusArray.get(i);
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }

        //score
        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString(("Game Over: ")+ String.valueOf(score), 10, 35);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }

    //handle movement updates on the screen
    public void move() {
        //dinosaur
        velocityY += gravity; 
        dinosaur.y += velocityY;

        if (dinosaur.y > dinosaurY) {
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }

        //cactus
        for (int i = 0; i < cactusArray.size(); i++) {
            Block cactus = cactusArray.get(i);
            cactus.x += velocityX;

            if (collision(dinosaur, cactus)) {
                gameOver = true;
                dinosaur.img = dinosaurDeadImg;
            }
        }

        //score
        score++;
    }

    //collision detection formular
    boolean collision(Block a, Block b) {
        return  a.x < b.x + b.width &&      // a's top left corner doesn't reach b's top right corner   
                a.x + a.width > b.x &&      // a's top right corner passes b's top left corner
                a.y < b.y + b.height &&     // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;       // a's bottom left corner passes b's top left corner
    }

    //game loop action listener recalling draw
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); //update positions
        repaint();
        if (gameOver) {
            placeCactusTimer.stop();
            gameLoop.stop();
        }
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

        if (gameOver) {
            //restart game by resetting conditions
            dinosaur.y = dinosaurY;
            dinosaur.img = dinosaurImg;
            velocityY = 0;
            cactusArray.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            placeCactusTimer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} //not used

    @Override
    public void keyReleased(KeyEvent e) {} //not used
}
