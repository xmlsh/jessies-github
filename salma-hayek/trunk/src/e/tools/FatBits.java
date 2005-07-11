package e.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import e.util.*;

/**
 * A Java equivalent of Apple's Pixie.app magnifying glass utility. This is
 * useful for checking layout, graphics, and colors in your own programs and
 * others.
 */
public class FatBits extends JFrame {
    private Robot robot;
    private Timer timer;
    private ImageIcon icon;
    
    public FatBits() {
        super("FatBits");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Log.warn("failed to create a Robot", ex);
        }
        timer = new Timer(200, new MouseTracker());
        setSize(new Dimension(200, 200));
        icon = new ImageIcon();
        setContentPane(new JLabel(icon));
        timer.start();
    }
    
    private class MouseTracker implements ActionListener {
        private Point lastPosition = null;

        public void actionPerformed(ActionEvent e) {
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            Point center = pointerInfo.getLocation();
            if (lastPosition != null && lastPosition.equals(center)) {
                return;
            }
            lastPosition = center;
            
            Rectangle screenCaptureBounds = getScreenCaptureBounds(center);
            icon.setImage(robot.createScreenCapture(screenCaptureBounds));
            repaint();
        }
        
        private Rectangle getScreenCaptureBounds(Point center) {
            Point topLeft = new Point(center.x - getWidth() / 2, center.y - getHeight() / 2);
            Rectangle result = new Rectangle(topLeft, getSize());
            
            // Constrain the capture to the display.
            // Apple's 1.5 VM crashes if you don't.
            result.x = Math.max(result.x, 0);
            result.y = Math.max(result.y, 0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (result.x + result.width > screenSize.width) {
                result.x = screenSize.width - result.width;
            }
            if (result.y + result.height > screenSize.height) {
                result.y = screenSize.height - result.height;
            }
            
            return result;
        }
    }
    
    public static void main(String[] args) {
        new FatBits().setVisible(true);
    }
}
