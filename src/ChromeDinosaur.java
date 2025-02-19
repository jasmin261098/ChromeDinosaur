import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //to store all cactus
import javax.swing.*;

public class ChromeDinosaur extends JPanel {
    int boardWidth = 750;
    int boardHeight = 250;

    public ChromeDinosaur() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.lightGray);
    }
}
