import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NewUI{
    private JFrame frame;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private JButton addButton;
    private JButton drawButton;
    private JButton eraseButton;
    private List<RectangleInputPanel> rectangleInputPanels = new ArrayList<>();

    public NewUI() {
        frame = new JFrame("Multiple Rectangle Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout for buttons
        addButton = new JButton("Add Rectangle Input");
        drawButton = new JButton("Draw Rectangles");
        eraseButton = new JButton("Erase Rectangles");

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

        buttonPanel.add(addButton);
        buttonPanel.add(drawButton);
        buttonPanel.add(eraseButton);

        displayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRectangles(g);
            }
        };

        inputPanel.add(buttonPanel); // Add the button panel at the top
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(new JScrollPane(displayPanel), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addRectangleInputPanel() {
        RectangleInputPanel rectangleInputPanel = new RectangleInputPanel(rectangleInputPanels.size() + 1);
        rectangleInputPanels.add(rectangleInputPanel);
        inputPanel.add(rectangleInputPanel);
        inputPanel.revalidate();
    }

    private void drawRectangles(Graphics g) {
        for (RectangleInputPanel inputPanel : rectangleInputPanels) {
            inputPanel.drawRectangle(g);
        }
    }

    private void clearRectangles() {
        rectangleInputPanels.clear();
        inputPanel.removeAll();
        inputPanel.revalidate();
        inputPanel.repaint();
        displayPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewUI();
            }
        });
    }

    private class RectangleInputPanel extends JPanel {
        private JTextField heightField;
        private JTextField widthField;
        private JTextField quantityField;
        private int rectangleNumber;

        public RectangleInputPanel(int number) {
            this.rectangleNumber = number;
            setLayout(new GridLayout(1, 5));
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

            heightField.setPreferredSize(new Dimension(40, 25));
            quantityField.setPreferredSize(new Dimension(40, 25));
        }

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
