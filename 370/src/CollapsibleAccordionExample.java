import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.lightGray);

        JToggleButton toggleButton = new JToggleButton("Toggle Accordion");
        JPanel accordionPanel = new JPanel();
        accordionPanel.setLayout(new BorderLayout());
        JLabel accordionLabel = new JLabel("Accordion Content");
        accordionPanel.add(accordionLabel);

        JComboBox<String> grainComboBox = new JComboBox<>(new String[]{"On", "Off"});
        JTextField kerfSizeField = new JTextField();

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    String grainInput = (String) grainComboBox.getSelectedItem();
                    String kerfSizeInput = kerfSizeField.getText();
                    accordionLabel.setText("Grain: " + grainInput + ", Kerf Size: " + kerfSizeInput);
                    accordionPanel.setVisible(true);
                } else {
                    accordionLabel.setText("Accordion Content");
                    accordionPanel.setVisible(false);
                }
            }
        });

        accordionPanel.setVisible(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Grain:"));
        inputPanel.add(grainComboBox);
        inputPanel.add(new JLabel("Kerf Size:"));
        inputPanel.add(kerfSizeField);

        leftContentPanel.add(new JLabel("Left Panel Content"), BorderLayout.NORTH);
        leftContentPanel.add(toggleButton, BorderLayout.CENTER);
        leftContentPanel.add(inputPanel, BorderLayout.SOUTH);

        leftPanel.add(leftContentPanel, BorderLayout.WEST);
        leftPanel.add(rightPanel, BorderLayout.CENTER);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.setVisible(true);
    }
}


