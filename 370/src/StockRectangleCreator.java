import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockRectangleCreator extends JFrame {

    private JPanel inputPanel; // Panel for user input
    private JPanel drawingPanel; // Panel for displaying the rectangle
    private int width;
    private int height;
    private double scaleFactor = 0.8; // Default scale factor

    public StockRectangleCreator() {
        setTitle("Stock Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400); // Increased the window width to accommodate both panels

        // Create a panel for user input on the left side
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2)); // Using a grid layout for input fields
        getContentPane().add(inputPanel, BorderLayout.WEST);

        JLabel widthLabel = new JLabel("Enter width:");
        JTextField widthTextField = new JTextField(10);
        JLabel heightLabel = new JLabel("Enter height:");
        JTextField heightTextField = new JTextField(10);
        JButton createButton = new JButton("Create Rectangle");

        inputPanel.add(widthLabel);
        inputPanel.add(widthTextField);
        inputPanel.add(heightLabel);
        inputPanel.add(heightTextField);
        inputPanel.add(createButton);

        // Create a panel for displaying the rectangle on the right side
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);

                // Calculate scaled width and height
                int scaledWidth = (int) (width * scaleFactor);
                int scaledHeight = (int) (height * scaleFactor);

                // Calculate the position to center the rectangle
                int x = (getWidth() - scaledWidth) / 2;
                int y = (getHeight() - scaledHeight) / 2;

                // Draw the rectangle
                g.fillRect(x, y, scaledWidth, scaledHeight);

                // Draw width label horizontally
                g.setColor(Color.BLACK);
                String widthLabel = "Width: " + width;
                g.drawString(widthLabel, x + 10, y - 10);

                // Draw height label vertically
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.rotate(-Math.PI / 2); // Rotate 90 degrees counterclockwise
                String heightLabel = "Height: " + height;
                g2d.drawString(heightLabel, -y - scaledHeight - 10, x - 5);
                g2d.dispose();
            }
        };
        getContentPane().add(drawingPanel, BorderLayout.CENTER);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    width = Integer.parseInt(widthTextField.getText());
                    height = Integer.parseInt(heightTextField.getText());

                    // Adjust the scaleFactor based on the size of the rectangle
                    scaleFactor = (Math.min(1.0 * drawingPanel.getWidth() / width, 1.0 * drawingPanel.getHeight() / height)) * 0.8;

                    drawingPanel.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(StockRectangleCreator.this, "Please enter valid width and height values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StockRectangleCreator rectangleCreator = new StockRectangleCreator();
            rectangleCreator.setVisible(true);
        });
    }
}
