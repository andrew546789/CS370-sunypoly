import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScreenCaptureApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Screen Capture UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JButton captureButton = new JButton("Capture Screenshot");
        ImageIcon icon = new ImageIcon("path/to/icon.png"); // Replace with the path to your icon
        captureButton.setIcon(icon);

        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                captureAndExportScreenshot();
            }
        });

        frame.add(captureButton);
        frame.setVisible(true);
    }

    private static void captureAndExportScreenshot() {
        try {
            Robot robot = new Robot();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Rectangle screenSize = new Rectangle(toolkit.getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenSize);

            File outputFile = new File("screenshot.png");
            ImageIO.write(screenshot, "png", outputFile);

            JOptionPane.showMessageDialog(null, "Screenshot captured and saved to: " + outputFile.getAbsolutePath());
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to capture screenshot");
        }
    }
}
