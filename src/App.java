import java.awt.*;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        JFrame chromeDinosaurFrame = new JFrame("Chromis Jumping Adventure");
        setupChromeDinosaurFrame(ChromeDinosaurGameLogic.BOARD_WIDTH, ChromeDinosaurGameLogic.BOARD_HEIGHT, chromeDinosaurFrame);

        ChromeDinosaurGameLogic chromeDinosaur = new ChromeDinosaurGameLogic();
        addGameLogicToFrame(chromeDinosaurFrame, chromeDinosaur);
    }

    private static void addGameLogicToFrame(JFrame chromeDinosaurFrame, ChromeDinosaurGameLogic chromeDinosaur) {
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
}
