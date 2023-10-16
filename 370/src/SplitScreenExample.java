import javax.swing.*;
import java.awt.*;

public class SplitScreenExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Split Screen Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the main panel that divides the screen into left and right panels
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the left panel that is wider and further divides into top and bottom halves
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JPanel topLeftPanel = new JPanel();
        JPanel bottomLeftPanel = new JPanel();

        // Create components for kerf width and grain
        JLabel kerfLabel = new JLabel("Kerf Width: ");
        JTextField kerfField = new JTextField(10);
        JLabel grainLabel = new JLabel("Grain: ");
        JComboBox<String> grainComboBox = new JComboBox<>(new String[]{"On", "Off"});

        // Add components to the bottomLeftPanel
        bottomLeftPanel.setLayout(new BorderLayout());
        JPanel kerfGrainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kerfGrainPanel.add(kerfLabel);
        kerfGrainPanel.add(kerfField);
        kerfGrainPanel.add(grainLabel);
        kerfGrainPanel.add(grainComboBox);
        bottomLeftPanel.add(kerfGrainPanel, BorderLayout.SOUTH);

        // Create the right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // Create buttons for the right panel
        JButton leftArrowButton = new JButton("←");
        JButton rightArrowButton = new JButton("→");

        // Create a panel for the buttons and add them to the right panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(leftArrowButton);
        buttonPanel.add(rightArrowButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the preferred size of the left panel to make it wider
        leftPanel.setPreferredSize(new Dimension(400, 600));

        // Customize the panels with background colors
        topLeftPanel.setBackground(Color.RED);
        bottomLeftPanel.setBackground(Color.BLUE);
        rightPanel.setBackground(Color.GREEN);

        // Add the left and right panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add the top and bottom halves to the left panel
        leftPanel.add(topLeftPanel);
        leftPanel.add(bottomLeftPanel);

        // Add the main panel to the frame
        frame.add(mainPanel);

        frame.setVisible(true);
    }
}
