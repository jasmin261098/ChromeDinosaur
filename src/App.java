import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 750;
        int boardHeight = 250;

        JFrame chromeDinosaurFrame = new JFrame("Chromis Jumping Adventure");
        chromeDinosaurFrame.setVisible(true);
        chromeDinosaurFrame.setSize(boardWidth, boardHeight);
        chromeDinosaurFrame.setLocationRelativeTo(null);
        chromeDinosaurFrame.setResizable(false);
        chromeDinosaurFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChromeDinosaur chromeDinosaur = new ChromeDinosaur();
        chromeDinosaurFrame.add(chromeDinosaur);
        chromeDinosaurFrame.pack();
        chromeDinosaur.requestFocus();
        chromeDinosaurFrame.setVisible(true);
    }
}
