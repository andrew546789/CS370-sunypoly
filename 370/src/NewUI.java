import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;//thing for making float rectangles
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Array;
import java.util.*;
import static java.lang.Math.abs;

public class NewUI {
    private JFrame frame;
    private JPanel inputPanel; // Main panel for user inputs
    private JPanel buttonPanel; // Panel for buttons
    private JPanel displayPanel; // Panel for displaying rectangles
    private JButton addButton;
    private JButton drawButton;
    private JButton eraseButton;
    private List<RectangleInputPanel> rectangleInputPanels = new ArrayList<>();
    int rows=0;//yellow
    ArrayList<Float> stock = new ArrayList<Float>();
    ArrayList<Box2> BOX = new ArrayList<Box2>();
    public NewUI() {
        // Create the main frame
        frame = new JFrame("Cut List Helper");
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
        //set height,width,quantity arrays to null
        rows=0;
        stock.clear();
        BOX.clear();
        for (RectangleInputPanel inputPanel : rectangleInputPanels) {//look through all the data
            inputPanel.feedRectangle(g);
            rows++;
        }
        for (RectangleInputPanel inputPanel : rectangleInputPanels) {
            inputPanel.drawRectangle(g);//l8r this will only get called once ideally
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
            if(number==1){
                setBorder(BorderFactory.createTitledBorder("Stock"));
            }else {
                setBorder(BorderFactory.createTitledBorder("Part " + (number - 1)));
            }
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
        public void drawRectangle(Graphics g) {//currently gets called once per row, but once algorithm is in we might use it differently
            String heightStr = heightField.getText();
            String widthStr = widthField.getText();
            String quantityStr = quantityField.getText();

            try {
                FFDH.setBoxesLevels(BOX, stock.get(0));
                FFDH.setBoxesPositions(BOX);

                Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, stock.get(0), stock.get(1));
                Graphics2D g2d = (Graphics2D)g.create();
                g2d.draw(rect);

                for(int i=0;i<BOX.size();i++) {
                    g.drawRect((int) BOX.get(i).getPosx(), (int) BOX.get(i).getPosy(), (int) BOX.get(i).getWidth(), (int) BOX.get(i).getLength());
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        }
        public void feedRectangle(Graphics g) {
            String heightStr = heightField.getText();
            String widthStr = widthField.getText();
            String quantityStr = quantityField.getText();
            try {

                if(rows==0) {
                    stock.add(Float.parseFloat(heightStr));
                    stock.add(Float.parseFloat(widthStr));
                    stock.add(Float.parseFloat(quantityStr));
                }else{
                    for(int i=0;i<Integer.parseInt(quantityStr);i++) {
                        Box2 a = new Box2(Float.parseFloat(widthStr), Float.parseFloat(heightStr), 0, 0);
                        BOX.add(a);
                    }
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        }
    }
}
//algorithm goes below
class Box2 {
    private float width, length, posx, posy;
    private int level;

    public void setWidth(float width) { this.width = width; }
    public void setLength(float length) {this.length = length; }
    public void setPosx(float posx) { this.posx = posx; }
    public void setPosy(float posy) { this.posy = posy; }
    public void setLevel(int level) { this.level = level; }
    public float getWidth() { return width; }
    public float getLength() {return length; }
    public float getPosx() { return posx; }
    public float getPosy() { return posy; }
    public int getLevel() { return level; }

    public Box2(float width, float length, float posx, float posy) {
        setWidth(width);
        setLength(length);
        setPosx(posx);
        setPosy(posy);
    }
}

class FFDH {
    public static ArrayList<Box2> simpleBoxSort(ArrayList<Box2> boxes) {
        boolean sorted = false;
        int i = 0;

        // Iterate through every box to sort them
        while(!sorted) {
            sorted = true;

            for(i = 0; i < boxes.size() - 1; i++) {
                // If the length of the current box is smaller than the length of the next box
                if(boxes.get(i + 1).getLength() > boxes.get(i).getLength()) {
                    Box2 tempBox = boxes.get(i + 1);
                    boxes.set(i + 1, boxes.get(i));
                    boxes.set(i, tempBox);
                    sorted = false;
                }
            }
        }

        return boxes;
    }

    public static ArrayList<Box2> setBoxesLevels(ArrayList<Box2> boxes, float boardWidth) {
        float [] runningWidths = new float[boxes.size()];
        int i, level = 0;

        // Get ordered boxes
        boxes = simpleBoxSort(boxes);

        // Add the first box to the running width
        runningWidths[level] += boxes.get(0).getWidth();

        // Go through every box
        for(i = 1; i < boxes.size(); i++) {
            if((runningWidths[level] + boxes.get(i).getWidth()) > boardWidth) {
                // Increase the level if the current widths of the level of the box are bigger than the boardwidth
                level += 1;
            }
            // Always add to the running width
            runningWidths[level] += boxes.get(i).getWidth();
            // Always set the level of the box
            boxes.get(i).setLevel(level);
        }

        return boxes;
    }

    public static ArrayList<Box2> setBoxesPositions(ArrayList<Box2> boxes) {
        int i = 0;
        float tempTallest = boxes.get(0).getLength();

        // Go through every box
        for(i = 1; i < boxes.size(); i++) {
            if(boxes.get(i).getLevel() == boxes.get(i - 1).getLevel()) {
                // Set the x pos to the previous box's width and the y pos to the previous box's y pos if they're on the same level
                boxes.get(i).setPosx(boxes.get(i - 1).getWidth() + boxes.get(i - 1).getPosx());
                boxes.get(i).setPosy(boxes.get(i - 1).getPosy());
            } else {
                // If new level: reset x pos to 0, set the y pos to the temp tallest, set temp tallest to current length and pos y
                boxes.get(i).setPosx(0);
                boxes.get(i).setPosy(tempTallest);
                tempTallest = boxes.get(i).getLength() + boxes.get(i).getPosy();
            }
        }

        return boxes;
    }

    public static void printBoxes(ArrayList<Box2> boxes, float boardWidth) {
        int i;

        // Iterate through every box to print their respective values
        for(i = 0; i < boxes.size(); i++) {
            System.out.println(boxes.get(i).getWidth() + "w, " + boxes.get(i).getLength() +
                    "l: " + boxes.get(i).getPosx() + "x, " + boxes.get(i).getPosy() + "y: " + boxes.get(i).getLevel());
        }

        return;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int i = 0;
        float boardWidth = 1000;
        ArrayList<Box2> sqrs = new ArrayList<>();

        // Add the board as the first object
        //sqrs.add(new Box2(50, 50, 0, 0));

        sqrs.add(new Box2(60, 3, 0, 0));
        sqrs.add(new Box2(30, 9, 0, 0));
        sqrs.add(new Box2(50, 1, 0, 0));
        sqrs.add(new Box2(70, 6, 0, 0));
        sqrs.add(new Box2(10, 3, 0, 0));

/*
        //create 5 boxes with random values
        for (i = 0; i < 5; i++) {
            sqrs.add(new Box2(rand.nextInt(10), rand.nextInt(10), 0, 0));
        }
*/

        // Print boxes values
        printBoxes(sqrs, boardWidth);

        System.out.println("---------------------");

        setBoxesLevels(sqrs, boardWidth);
        setBoxesPositions(sqrs);

        printBoxes(sqrs, boardWidth);
    }
}