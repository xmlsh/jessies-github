package e.tools;

import javax.swing.*;
import java.awt.*;
import e.util.*;

public class Xv extends JFrame {
    public Xv(String filename) {
        super(filename);
        Log.warn("Opening '" + filename + "'.");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon originalIcon = new ImageIcon(filename);
        Image originalImage = originalIcon.getImage();
        ImageIcon scaledIcon = new ImageIcon();
        scaledIcon.setImage(originalImage.getScaledInstance(200, -1, Image.SCALE_FAST));

        setContentPane(new JLabel(originalIcon));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        Log.setApplicationName("Xv");
        GuiUtilities.initLookAndFeel();
        for (int i = 0; i < arguments.length; ++i) {
            Xv xv = new Xv(arguments[i]);
        }
    }
}
