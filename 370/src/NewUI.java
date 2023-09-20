import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NewUI {
    private JFrame frame;
    private JPanel inputPanel; // Main panel for user inputs
    private JPanel buttonPanel; // Panel for buttons
    private JPanel displayPanel; // Panel for displaying rectangles
    private JButton addButton;
    private JButton drawButton;
    private JButton eraseButton;
    private List<RectangleInputPanel> rectangleInputPanels = new ArrayList<>();

    public NewUI() {
        // Create the main frame
        frame = new JFrame("Multiple Rectangle Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 400); // Adjusted frame width

        // Create a sub-panel for buttons with a horizontal FlowLayout
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Arrange buttons from left to right

        // Create buttons and set their preferred sizes
        addButton = new JButton("Add");
        eraseButton = new JButton("Remove");
        drawButton = new JButton("Calculate");
        addButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        drawButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        eraseButton.setPreferredSize(new Dimension(120, 30)); // Set button size

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRectangleInputPanel();
            }
        });

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.repaint();
            }
        });

        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRectangles();
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(drawButton);
        buttonPanel.add(eraseButton);

        // Create a panel for displaying rectangles and customize its paint behavior
        displayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRectangles(g);
            }
        };

        // Create the main input panel with a vertical BoxLayout
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        // Add the button panel at the top of the input panel
        inputPanel.add(buttonPanel);

        // Wrap the inputPanel in a JScrollPane and set its preferred size
        JScrollPane inputScrollPane = new JScrollPane(inputPanel);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Remove horizontal scrollbar
        inputScrollPane.setPreferredSize(new Dimension(380, frame.getHeight())); // Adjusted preferred width

        // Wrap the displayPanel in a JScrollPane and set its preferred size
        JScrollPane displayScrollPane = new JScrollPane(displayPanel);
        displayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        displayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Remove horizontal scrollbar
        displayScrollPane.setPreferredSize(new Dimension(500, 500));

        // Add input panel to the left side of the main frame and display panel to the center
        frame.add(inputScrollPane, BorderLayout.WEST);
        frame.add(displayScrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to add a new RectangleInputPanel to the input panel
    private void addRectangleInputPanel() {
        RectangleInputPanel rectangleInputPanel = new RectangleInputPanel(rectangleInputPanels.size() + 1);
        rectangleInputPanels.add(rectangleInputPanel);
        inputPanel.add(rectangleInputPanel);
        inputPanel.revalidate();
    }

    // Method to draw rectangles on the display panel
    private void drawRectangles(Graphics g) {
        for (RectangleInputPanel inputPanel : rectangleInputPanels) {
            inputPanel.drawRectangle(g);
        }
    }

    // Method to clear all rectangles and input panels
    private void clearRectangles() {
        if (!rectangleInputPanels.isEmpty()) {
            rectangleInputPanels.remove(rectangleInputPanels.size() - 1);
            inputPanel.remove(inputPanel.getComponentCount() - 1);
            inputPanel.revalidate();
            inputPanel.repaint();
            displayPanel.repaint();
        }
    }

    // Main method to create and run the UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewUI();
            }
        });
    }

    // Inner class for user input panels
    private class RectangleInputPanel extends JPanel {
        private JTextField heightField;
        private JTextField widthField;
        private JTextField quantityField;
        private int rectangleNumber;

        public RectangleInputPanel(int number) {
            this.rectangleNumber = number;
            setBorder(BorderFactory.createTitledBorder("Rectangle " + number));

            heightField = new JTextField();
            widthField = new JTextField();
            quantityField = new JTextField();

            add(new JLabel("H:"));
            add(heightField);
            add(new JLabel("W:"));
            add(widthField);
            add(new JLabel("Qty:"));
            add(quantityField);

            widthField.setPreferredSize(new Dimension(40, 25));
            heightField.setPreferredSize(new Dimension(40, 25));
            quantityField.setPreferredSize(new Dimension(40, 25));
        }

        // Method to draw a rectangle based on user input
        public void drawRectangle(Graphics g) {
            String heightStr = heightField.getText();
            String widthStr = widthField.getText();
            String quantityStr = quantityField.getText();

            try {
                int height = Integer.parseInt(heightStr);
                int width = Integer.parseInt(widthStr);
                int quantity = Integer.parseInt(quantityStr);

                for (int i = 0; i < quantity; i++) {
                    int x = 50 + i * (width + 10);
                    int y = 50 + rectangleNumber * 100;
                    g.drawRect(x, y, width, height);
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        }
    }
}
