import java.awt.*;
import javax.swing.*;

public class App {

    public final static Image DINOSAUR_IMG = loadImage("./img/dino-run.gif");
    public final static Image DINOSAUR_DEAD_IMG = loadImage("./img/dino-dead.png");
    public final static Image DINOSAUR_JUMP_IMG = loadImage("./img/dino-jump.png");
    public final static Image CACTUS_1_IMG = loadImage("./img/cactus1.png");
    public final static Image CACTUS_2_IMG = loadImage("./img/cactus2.png");
    public final static Image CACTUS_3_IMG = loadImage("./img/cactus3.png");

    public static void main(String[] args) throws Exception {
        
        JFrame chromeDinosaurFrame = new JFrame("Chromis Jumping Adventure");
        setupChromeDinosaurFrame(ChromeDinosaurGamePanel.BOARD_WIDTH, ChromeDinosaurGamePanel.BOARD_HEIGHT, chromeDinosaurFrame);

        ChromeDinosaurGamePanel gamePanel = new ChromeDinosaurGamePanel();
        addGamePanelToFrame(chromeDinosaurFrame, gamePanel);

        ChromeDinosaurGameLogic gameLogic = new ChromeDinosaurGameLogic(gamePanel);
        gameLogic.startGame();
    }

    private static void addGamePanelToFrame(JFrame chromeDinosaurFrame, ChromeDinosaurGamePanel chromeDinosaur) {
        chromeDinosaurFrame.add(chromeDinosaur);
        chromeDinosaurFrame.pack();
        chromeDinosaur.requestFocus();
        chromeDinosaurFrame.setVisible(true);
    }

    private static void setupChromeDinosaurFrame(int boardWidth, int boardHeight, JFrame chromeDinosaurFrame) {
        chromeDinosaurFrame.setVisible(true);
        chromeDinosaurFrame.setSize(boardWidth, boardHeight);
        chromeDinosaurFrame.setLocationRelativeTo(null);
        chromeDinosaurFrame.setResizable(false);
        chromeDinosaurFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static Image loadImage(String filePath) {
        return new ImageIcon(App.class.getResource(filePath)).getImage();
    }
}
