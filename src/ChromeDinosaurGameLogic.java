import java.awt.event.*;
import java.util.ArrayList; 
import javax.swing.*;

public class ChromeDinosaurGameLogic {
    
    public static Timer gameLoop;
    public static Timer placeCactusTimer;
    public ChromeDinosaurGamePanel gamePanel;
    public static boolean gameOver = false;
    private ChromeDinosaurActionListener actionListener;

    private final int CACTUS_1_WIDTH = 34;
    private final int CACTUS_2_WIDTH = 69;
    private final int CACTUS_3_WIDTH = 102;
    private final int CACTUS_HEIGHT = 70;

    private int cactusX = 700;
    private int cactusY = ChromeDinosaurGamePanel.BOARD_HEIGHT - CACTUS_HEIGHT;
    public static ArrayList<ChromeDinosaurGamePanel.ImagePositions> cactusArray = new ArrayList<ChromeDinosaurGamePanel.ImagePositions>();

    //physics
    private int velocityX = -12; //cactus moving left speed
    private int velocityY = 0; //dinosaur jump speed
    private int gravity = 1; //positive numbers means downwards

    public ChromeDinosaurGameLogic(ChromeDinosaurGamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.actionListener = new ChromeDinosaurActionListener(this);
        actionListener.setGameLogic(this);
    }
    
    public void startGame() {
        initializeGameLoop();
        initializingCactusTimer();
    }

    private void initializeGameLoop() {
        gameLoop = new Timer(1000/60, actionListener); //1000/60 = 60 frames per 1000ms (1s), update
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
            ChromeDinosaurGamePanel.ImagePositions cactus = gamePanel.new ImagePositions(cactusX, cactusY, CACTUS_3_WIDTH, CACTUS_HEIGHT, App.CACTUS_3_IMG);
            cactusArray.add(cactus);
        } else if (placeCactusChance > .70) { //20% chance to get cactus 2
            ChromeDinosaurGamePanel.ImagePositions cactus = gamePanel.new ImagePositions(cactusX, cactusY, CACTUS_2_WIDTH, CACTUS_HEIGHT, App.CACTUS_2_IMG);
            cactusArray.add(cactus);
        } else if (placeCactusChance > .50) { //20% chance to get cactus 1
            ChromeDinosaurGamePanel.ImagePositions cactus = gamePanel.new ImagePositions(cactusX, cactusY, CACTUS_1_WIDTH, CACTUS_HEIGHT, App.CACTUS_1_IMG);
            cactusArray.add(cactus);
        } //50% chance to actually get a cactus

        if (cactusArray.size() > 10) {
            cactusArray.remove(0); // remove the first cactus from the ArrayList
        }
    }

    public void restartGame() {
        gamePanel.dinosaur.y = gamePanel.dinosaurY;
        gamePanel.dinosaur.img = App.DINOSAUR_IMG;
        velocityY = 0;
        ChromeDinosaurGameLogic.cactusArray.clear();
        gamePanel.score = 0;
        ChromeDinosaurGameLogic.gameOver = false;
        ChromeDinosaurGameLogic.gameLoop.start();
        ChromeDinosaurGameLogic.placeCactusTimer.start();
    }

    boolean detactCollisions(ChromeDinosaurGamePanel.ImagePositions a, ChromeDinosaurGamePanel.ImagePositions b) {
        return  a.x < b.x + b.width &&      // a's top left corner doesn't reach b's top right corner   
                a.x + a.width > b.x &&      // a's top right corner passes b's top left corner
                a.y < b.y + b.height &&     // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;       // a's bottom left corner passes b's top left corner
    }

    public void dinosaurJump() {
        if (gamePanel.dinosaur.y == gamePanel.dinosaurY) { 
            velocityY = -17;
            gamePanel.dinosaur.img = App.DINOSAUR_JUMP_IMG;
        }
    }

    public void movingImages() {
        //dinosaur
        velocityY += gravity; 
        gamePanel.dinosaur.y += velocityY;

        if (gamePanel.dinosaur.y > gamePanel.dinosaurY) {
            gamePanel.dinosaur.y = gamePanel.dinosaurY;
            velocityY = 0;
            gamePanel.dinosaur.img = App.DINOSAUR_IMG;
        }

        //cactus
        for (int i = 0; i < ChromeDinosaurGameLogic.cactusArray.size(); i++) {
            ChromeDinosaurGamePanel.ImagePositions cactus = ChromeDinosaurGameLogic.cactusArray.get(i);
            cactus.x += velocityX;

            if (detactCollisions(gamePanel.dinosaur, cactus)) {
                ChromeDinosaurGameLogic.gameOver = true;
                gamePanel.dinosaur.img = App.DINOSAUR_DEAD_IMG;
            }
        }

        //score
        gamePanel.score++;
    }
}
