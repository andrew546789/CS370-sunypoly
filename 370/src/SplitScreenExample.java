import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SplitScreenExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Split Screen Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JPanel topLeftPanel = new JPanel();
        JPanel bottomLeftPanel = new JPanel();

        JTextField kerfField = new JTextField(10);
        JButton bottomLeftButton = new JButton("Bottom-Left Button");

        bottomLeftPanel.setLayout(new BorderLayout());
        JPanel kerfGrainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kerfGrainPanel.add(kerfField);
        bottomLeftPanel.add(kerfGrainPanel, BorderLayout.SOUTH);
        bottomLeftPanel.add(bottomLeftButton, BorderLayout.NORTH);

        JButton topLeftButton = new JButton("Top-Left Button");
        JTextField topLeftField1 = new JTextField(10);
        JTextField topLeftField2 = new JTextField(10);

        JPanel topLeftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftButtonPanel.add(topLeftButton);
        topLeftPanel.add(topLeftButtonPanel);
        topLeftPanel.add(topLeftField1);
        topLeftPanel.add(topLeftField2);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JButton leftArrowButton = new JButton("←");
        JButton rightArrowButton = new JButton("→");
        JButton addPageButton = new JButton("Add Page");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(leftArrowButton);
        buttonPanel.add(rightArrowButton);
        buttonPanel.add(addPageButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        leftPanel.setPreferredSize(new Dimension(400, 600));

        topLeftPanel.setBackground(Color.RED);
        bottomLeftPanel.setBackground(Color.BLUE);
        rightPanel.setBackground(Color.GREEN);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        leftPanel.add(topLeftPanel);
        leftPanel.add(bottomLeftPanel);

        // Create a list to store content for the right panel
        List<String> rightPanelContent = new ArrayList<>();
        rightPanelContent.add("Page 1 Content");
        int currentPage = 0;  // Current page index

        // Set initial content for the right panel
        JLabel rightPanelLabel = new JLabel(rightPanelContent.get(currentPage));
        rightPanel.add(rightPanelLabel, BorderLayout.CENTER);

        // Add ActionListener for the left arrow button
        leftArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    //currentPage--;
                    rightPanelLabel.setText(rightPanelContent.get(currentPage));
                }
            }
        });

        // Add ActionListener for the right arrow button
        rightArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage < rightPanelContent.size() - 1) {
                    //currentPage++;
                    rightPanelLabel.setText(rightPanelContent.get(currentPage));
                }
            }
        });

        // Add ActionListener for the "Add Page" button
        addPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanelContent.add("Page " + (rightPanelContent.size() + 1) + " Content");
            }
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
