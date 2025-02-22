import java.awt.event.*;

public class ChromeDinosaurActionListener implements ActionListener, KeyListener {

    private ChromeDinosaurGameLogic gameLogic;

    public ChromeDinosaurActionListener(ChromeDinosaurGameLogic gameLogic) {
        this.gameLogic = gameLogic;
        gameLogic.gamePanel.addKeyListener(this);
    }

    public void setGameLogic(ChromeDinosaurGameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    //game loop action listener recalling draw
    @Override
    public void actionPerformed(ActionEvent e) {
        gameLogic.movingImages(); //update positions
        gameLogic.gamePanel.repaint();
        if (ChromeDinosaurGameLogic.gameOver) {
            ChromeDinosaurGameLogic.placeCactusTimer.stop();
            ChromeDinosaurGameLogic.gameLoop.stop();
        }
    }

    //Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameLogic.dinosaurJump();
        }

        if (ChromeDinosaurGameLogic.gameOver) {
            gameLogic.restartGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {} //not used

    @Override
    public void keyReleased(KeyEvent e) {} //not used
}
