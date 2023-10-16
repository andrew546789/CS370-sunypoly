import javax.swing.*;
import java.awt.*;

public class CollapsibleAccordionExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Collapsible Accordion Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel leftContentPanel = new JPanel();
        leftContentPanel.setLayout(new BorderLayout());

       // JToggleButton toggleButton = new JToggleButton("Toggle Accordion");
        JPanel accordionPanel = new JPanel();
        accordionPanel.setLayout(new BorderLayout());

        JComboBox<String> grainComboBox = new JComboBox<>(new String[]{"On", "Off"});
        JTextField kerfSizeField = new JTextField();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Grain:"));
        inputPanel.add(grainComboBox);
        inputPanel.add(new JLabel("Kerf Size:"));
        inputPanel.add(kerfSizeField);

        leftContentPanel.add(new JLabel("Left Panel Content"), BorderLayout.NORTH);
       // leftContentPanel.add(toggleButton, BorderLayout.CENTER);
        leftContentPanel.add(inputPanel, BorderLayout.SOUTH);

        leftPanel.add(leftContentPanel, BorderLayout.WEST);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.setVisible(true);
    }
}
