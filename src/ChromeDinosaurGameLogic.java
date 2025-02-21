import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; 
import javax.swing.*;

public class ChromeDinosaurGameLogic extends JPanel implements ActionListener, KeyListener {
    public final static int BOARD_WIDTH = 750;
    public final static int BOARD_HEIGHT = 250;

    private final Image DINOSAUR_IMG = loadImage("./img/dino-run.gif");
    private final Image DINOSAUR_DEAD_IMG = loadImage("./img/dino-dead.png");
    private final Image DINOSAUR_JUMP_IMG = loadImage("./img/dino-jump.png");
    private final Image CACTUS_1_IMG = loadImage("./img/cactus1.png");
    private final Image CACTUS_2_IMG = loadImage("./img/cactus2.png");
    private final Image CACTUS_3_IMG = loadImage("./img/cactus3.png");

    class ImagePositions {
        private int x;
        private int y;
        private int width;
        private int height;
        private Image img;

        ImagePositions(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //dinosaur dimensions
    private final int DINOSAUR_WIDTH = 88;
    private final int DINOSAUR_HEIGHT = 94;
    private int dinosaurX = 50;
    private int dinosaurY = BOARD_HEIGHT - DINOSAUR_HEIGHT;

    private ImagePositions dinosaur = new ImagePositions(dinosaurX, dinosaurY, DINOSAUR_WIDTH, DINOSAUR_HEIGHT, DINOSAUR_IMG);

    //cactus dimensions
    private final int CACTUS_1_WIDTH = 34;
    private final int CACTUS_2_WIDTH = 69;
    private final int CACTUS_3_WIDTH = 102;
    private final int CACTUS_HEIGHT = 70;
    private int cactusX = 700;
    private int cactusY = BOARD_HEIGHT - CACTUS_HEIGHT;

    private ArrayList<ImagePositions> cactusArray = new ArrayList<ImagePositions>();

    //physics
    private int velocityX = -12; //cactus moving left speed
    private int velocityY = 0; //dinosaur jump speed
    private int gravity = 1; //positive numbers means downwards

    private boolean gameOver = false;
    private int score = 0;

    private Timer gameLoop;
    private Timer placeCactusTimer;


    public ChromeDinosaurGameLogic() {
        configurePanel();
        initializeGameLoop();
        initializingCactusTimer();
    }

    private void configurePanel() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.lightGray);
        setFocusable(true); //can listen to space input
        addKeyListener(this); //this reveres to the ChromeDinosaur Class, adding the Listener to the Panel
    }

    private Image loadImage(String filePath) {
        return new ImageIcon(getClass().getResource(filePath)).getImage();
    }

    private void initializeGameLoop() {
        gameLoop = new Timer(1000/60, this); //1000/60 = 60 frames per 1000ms (1s), update
        gameLoop.start();
    }

    private void initializingCactusTimer() {
        placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placingCactusProbability();
            }
        });
        placeCactusTimer.start();
    }

    void placingCactusProbability() {
        if (gameOver) {
            return;
        }

        double placeCactusChance = Math.random(); // 0-0.99999
        if (placeCactusChance > .90) { //10% chance to get cactus 3
            ImagePositions cactus = new ImagePositions(cactusX, cactusY, CACTUS_3_WIDTH, CACTUS_HEIGHT, CACTUS_3_IMG);
            cactusArray.add(cactus);
        } else if (placeCactusChance > .70) { //20% chance to get cactus 2
            ImagePositions cactus = new ImagePositions(cactusX, cactusY, CACTUS_2_WIDTH, CACTUS_HEIGHT, CACTUS_2_IMG);
            cactusArray.add(cactus);
        } else if (placeCactusChance > .50) { //20% chance to get cactus 1
            ImagePositions cactus = new ImagePositions(cactusX, cactusY, CACTUS_1_WIDTH, CACTUS_HEIGHT, CACTUS_1_IMG);
            cactusArray.add(cactus);
        } //50% chance to actually get a cactus

        if (cactusArray.size() > 10) {
            cactusArray.remove(0); // remove the first cactus from the ArrayList
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImagesOnFrame(g);
    }

    public void drawImagesOnFrame(Graphics g) {
        //dinosaur
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);

        //cactus
        for (int i = 0; i < cactusArray.size(); i++) {
            ImagePositions cactus = cactusArray.get(i);
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

    public void movingImages() {
        //dinosaur
        velocityY += gravity; 
        dinosaur.y += velocityY;

        if (dinosaur.y > dinosaurY) {
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = DINOSAUR_IMG;
        }

        //cactus
        for (int i = 0; i < cactusArray.size(); i++) {
            ImagePositions cactus = cactusArray.get(i);
            cactus.x += velocityX;

            if (detactCollisions(dinosaur, cactus)) {
                gameOver = true;
                dinosaur.img = DINOSAUR_DEAD_IMG;
            }
        }

        //score
        score++;
    }

    boolean detactCollisions(ImagePositions a, ImagePositions b) {
        return  a.x < b.x + b.width &&      // a's top left corner doesn't reach b's top right corner   
                a.x + a.width > b.x &&      // a's top right corner passes b's top left corner
                a.y < b.y + b.height &&     // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;       // a's bottom left corner passes b's top left corner
    }

    //game loop action listener recalling draw
    @Override
    public void actionPerformed(ActionEvent e) {
        movingImages(); //update positions
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
                dinosaur.img = DINOSAUR_JUMP_IMG;
            }
        }

        if (gameOver) {
            restartGame();
        }
    }

    private void restartGame() {
        dinosaur.y = dinosaurY;
        dinosaur.img = DINOSAUR_IMG;
        velocityY = 0;
        cactusArray.clear();
        score = 0;
        gameOver = false;
        gameLoop.start();
        placeCactusTimer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {} //not used

    @Override
    public void keyReleased(KeyEvent e) {} //not used
}
