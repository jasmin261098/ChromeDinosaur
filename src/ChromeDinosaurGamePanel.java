import java.awt.*;
import javax.swing.*;

public class ChromeDinosaurGamePanel extends JPanel {
    public final static int BOARD_WIDTH = 750;
    public final static int BOARD_HEIGHT = 250;

    class ImagePositions {
        public int x;
        public int y;
        public int width;
        public int height;
        public Image img;

        ImagePositions(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //dinosaur dimensions
    public final int DINOSAUR_WIDTH = 88;
    public final int DINOSAUR_HEIGHT = 94;
    public int dinosaurX = 50;
    public int dinosaurY = BOARD_HEIGHT - DINOSAUR_HEIGHT;

    public ImagePositions dinosaur = new ImagePositions(dinosaurX, dinosaurY, DINOSAUR_WIDTH, DINOSAUR_HEIGHT, App.DINOSAUR_IMG);

    public int score = 0;


    public ChromeDinosaurGamePanel() {
        configurePanel();
    }

    private void configurePanel() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.lightGray);
        setFocusable(true); //can listen to space input
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImagesOnFrame(g);
    }

    public void drawImagesOnFrame(Graphics g) {
        //dinosaur
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);

        //cactus
        for (int i = 0; i < ChromeDinosaurGameLogic.cactusArray.size(); i++) {
            ImagePositions cactus = ChromeDinosaurGameLogic.cactusArray.get(i);
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }

        //score
        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (ChromeDinosaurGameLogic.gameOver) {
            g.drawString(("Game Over: ")+ String.valueOf(score), 10, 35);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }
}
