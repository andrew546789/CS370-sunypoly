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
    //private JButton mgrainButton;
    //private JButton grainButton;
    private List<RectangleInputPanel> rectangleInputPanels = new ArrayList<>();
    int rows=0;
    ArrayList<Float> stock = new ArrayList<Float>();
    ArrayList<Box2> BOX = new ArrayList<Box2>();
    double scaleFactor;
    // Add the generateColorPalette method
    private Color[] generateColorPalette(int numColors) {
        Color[] palette = new Color[numColors];
        float saturation = .75f;
        float brightness = 1.0f;

        for (int i = 0; i < numColors; i++) {
            float hue = (float) i / numColors;
            palette[i] = Color.getHSBColor(hue, saturation, brightness);
        }

        return palette;
    }

    // Create a private field to store the generated color palette
    private Color[] partColors;
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
        //mgrainButton = new JButton("Grain Matters");
        //grainButton = new JButton("Horizontal");
        addButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        drawButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        eraseButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        //mgrainButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        //grainButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        // Initialize the color palette with 10 colors
        partColors = generateColorPalette(10);
        // Add action listeners to buttons




        //mgrainButton.addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //        addRectangleInputPanel();
        //    }
        //});
        //grainButton.addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //        addRectangleInputPanel();
        //    }
        //});
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRectangleInputPanel();
            }
        });

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {displayPanel.repaint();}
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
        //buttonPanel.add(mgrainButton);

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
        scaleFactor=1;
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


        public RectangleInputPanel(int number) {
            if(number==1){
                setBorder(BorderFactory.createTitledBorder("Stock"));
            }else {
                setBorder(BorderFactory.createTitledBorder("Part " + (number - 1)));
            }
            heightField = new JTextField();
            widthField = new JTextField();
            quantityField = new JTextField();

            add(new JLabel("Height:"));
            add(heightField);
            add(new JLabel("Width:"));
            add(widthField);
            add(new JLabel("Quantity:"));
            add(quantityField);
            //add(grainButton);

            widthField.setPreferredSize(new Dimension(40, 25));
            heightField.setPreferredSize(new Dimension(40, 25));
            quantityField.setPreferredSize(new Dimension(40, 25));
        }

        // Method to draw a rectangle based on user input
        public void drawRectangle(Graphics g) {//currently gets called once per row, but once algorithm is in we might use it differently
            String heightStr = heightField.getText();
            String widthStr = widthField.getText();
            String quantityStr = quantityField.getText();
//
            try {
                scaleFactor = (Math.min(1.0 * (frame.getWidth()-410) / stock.get(0), 1.0 * (frame.getHeight()-50) / stock.get(1))) * 0.9;//set scaling mult based off stock
                FFDH.setBoxesLevels(BOX, stock.get(0), stock.get(1), 2);
                // 2 is grain dir doesnt matter 0 and 1 doesnt matter aslong as the inputs are on the same page.
                FFDH.setBoxesPositions(BOX,stock.get(1));
                Rectangle2D.Double srect = new Rectangle2D.Double(10, 10, stock.get(0)*scaleFactor, stock.get(1)*scaleFactor);
                Graphics2D g2d = (Graphics2D)g.create();
                g2d.draw(srect);
                for(int i=0;i<BOX.size();i++) {
                    Color yes[];
                    yes=generateColorPalette(rows);
                    Rectangle2D.Double prect = new Rectangle2D.Double((BOX.get(i).getPosx()*scaleFactor+10), (BOX.get(i).getPosy()*scaleFactor+10), (BOX.get(i).getWidth()*scaleFactor), (BOX.get(i).getLength()*scaleFactor));
                    g2d.setColor(yes[BOX.get(i).getID()]);
                    g2d.fill(prect);
                    g2d.setColor(Color.black);
                    g2d.draw(prect);
                    if((BOX.get(i).getLength()*scaleFactor)>15&&20<(BOX.get(i).getWidth()*scaleFactor)) {//only put a part display if you can see the part
                        g2d.drawString("Prt" + BOX.get(i).getID(), (int) (BOX.get(i).getPosx() * scaleFactor + 12), (int) (BOX.get(i).getPosy() * scaleFactor + 22));// part label on each part
                    }
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
                    stock.add(Float.parseFloat(widthStr));
                    stock.add(Float.parseFloat(heightStr));
                    stock.add(Float.parseFloat(quantityStr));
                }else{
                    for(int i=0;i<Integer.parseInt(quantityStr);i++) {
                        Box2 a = new Box2(Float.parseFloat(widthStr), Float.parseFloat(heightStr), 0, 0,rows,2);
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
class stock {
	private float swidth, sheight;
	
	public void setswidth(float swidth) { this.swidth = swidth; }
	public void setsheight(float sheight) { this.sheight = sheight; }
	
	public float getswidth() { return swidth; }
	public float getslegnth() { return sheight; }
	
	public stock(float swidth, float sheight) {
		setswidth(swidth);
		setsheight(sheight);
	}
}

class Box2 {
    private float width, length, posx, posy;
    private int level, ID, grain, StockNum;

    public void setWidth(float width) { this.width = width; }
    public void setLength(float length) {this.length = length; }
    public void setPosx(float posx) { this.posx = posx; }
    public void setPosy(float posy) { this.posy = posy; }
    public void setLevel(int level) { this.level = level; }
    public void setgrain(int grain) { this.grain = grain; }
    public void setID(int ID) {this.ID=ID;}
    public void setStockNum(int StockNum) { this.StockNum = StockNum; }
    
    public float getWidth() { return width; }
    public float getLength() {return length; }
    public float getPosx() { return posx; }
    public float getPosy() { return posy; }
    public int getLevel() { return level; }
    public int getID(){return ID;}
    public int getGrain() { return grain;}
    public int getStockNum() { return StockNum;}
    
    public Box2(float width, float length, float posx, float posy, int ID, int grain) {
        setWidth(width);
        setLength(length);
        setPosx(posx);
        setPosy(posy);
        setID(ID);
        setgrain(grain);
        setStockNum(StockNum);
    }
}

class FFDH {
    public static ArrayList<Box2> simpleBoxSort(ArrayList<Box2> boxes, int graindir) {
        boolean sorted = false;
        int i = 0;
        float tempWidth = 0;
        boolean graincare = true;
                
        
        if(graindir != 2) {
        	for(i=0; i < boxes.size() ; i++) {
        		if( boxes.get(i).getGrain() == 2 ) {
        			if(boxes.get(i).getWidth() > boxes.get(i).getLength()) {
        				tempWidth = boxes.get(i).getWidth();
        				boxes.get(i).setWidth(boxes.get(i).getLength());
        				boxes.get(i).setLength(tempWidth);
        			}
        		}
        		else if (graindir != boxes.get(i).getGrain()) {
        			tempWidth = boxes.get(i).getWidth();
        			boxes.get(i).setWidth(boxes.get(i).getLength());
        			boxes.get(i).setLength(tempWidth);
        		}
        		else if(graindir == boxes.get(i).getGrain()) {
        			
        		}
        	}
        }
        else {
        	for(i = 0; i < boxes.size(); i++) {
        		if(boxes.get(i).getWidth() > boxes.get(i).getLength()) {
        			tempWidth = boxes.get(i).getWidth();
        			boxes.get(i).setWidth(boxes.get(i).getLength());
        			boxes.get(i).setLength(tempWidth);
        		}
        	}
        }
        
        	


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
//setboxeslevel for stocks object arraylists
/*
 * public static ArrayList<Box2> setBoxesLevels(ArrayList<Box2> boxes, ArratList<stock> stocks, int graindir) {
        float [] runningWidths = new float[boxes.size()];

        int i, level = 0;

        // Get ordered boxes
        boxes = simpleBoxSort(boxes, graindir);

        // Add the first box to the running width and length
        //runningWidths[level] += boxes.get(0).getWidth();

        // Go through every box
        for(i = 0; i < boxes.size(); i++) {
            // Go through every level
            for (int j = 0; j < runningWidths.length; j++) {
                // Find correct level when current widths of the level + the box are less than or equal to the board width
                if (((runningWidths[j] + boxes.get(i).getWidth()) <= stocks.get(x).getswidth()) && (boxes.get(i).getWidth() <= stocks.get(x).getswidth()) 
                		&& (boxes.get(i).getLength() <= stocks.get(x).getsheight())) {
                    // Set level if the box can fit
                    level = j;

                    // Always set the level of the box
                    boxes.get(i).setLevel(level);

                    // Always add to the running width
                    runningWidths[level] += boxes.get(i).getWidth();
                    break;
                }
                else if((x < stocks.size()-1) && 
                (boxes.get(i).getWidth() > stocks.get(x).getswidth() || boxes.get(i).getLength() > stocks.get(x).getsheight())
                &&(((boxes.get(i).getWidth()) <= stocks.get(x+1).getswidth()) && 
                (boxes.get(i).getWidth() <= stocks.get(x+1).getswidth()) 
                && (boxes.get(i).getLength() <= stocks.get(x+1).getsheight())))
                {
                	level = j;
					x++;
					
                    // Always set the level of the box
                    boxes.get(i).setLevel(level);

                    // Always add to the running width
                    runningWidths[level] += boxes.get(i).getWidth();
                    break;
                }
                else{
                	boxes.get(i).setWidth(0);
                    boxes.get(i).setLength(0);
                }
                	
            }
        }

        return boxes;
    }
 * 
 */
    public static ArrayList<Box2> setBoxesLevels(ArrayList<Box2> boxes, float boardWidth, float boardLength, int graindir) {
        float [] runningWidths = new float[boxes.size()];

        int i, level = 0, x=0;

        // Get ordered boxes
        boxes = simpleBoxSort(boxes, graindir);

        // Add the first box to the running width and length
        //runningWidths[level] += boxes.get(0).getWidth();

        // Go through every box
        for(i = 0; i < boxes.size(); i++) {
            // Go through every level
            for (int j = 0; j < runningWidths.length; j++) {
                // Find correct level when current widths of the level + the box are less than or equal to the board width
                if (((runningWidths[j] + boxes.get(i).getWidth()) <= boardWidth) && (boxes.get(i).getWidth() <= boardWidth) 
                		&& (boxes.get(i).getLength() <= boardLength)) {
                    // Set level if the box can fit
                    level = j;

                    // Always set the level of the box
                    boxes.get(i).setLevel(level);

                    // Always add to the running width
                    runningWidths[level] += boxes.get(i).getWidth();
                    break;
                }
                else if(boxes.get(i).getWidth() > boardWidth || boxes.get(i).getLength() > boardLength) {
                	boxes.get(i).setWidth(0);
                    boxes.get(i).setLength(0);
                }
                	
            }
        }

        return boxes;
    }

// implementation of setboxes position with a stock object arraylist
    /*
     * 
     * public static ArrayList<Box2> setBoxesPositions(ArrayList<Box2> boxes, ArrayList<stock> stocks) {
        int i = 0, x = 0;
        float tempTallest = boxes.get(0).getLength();
        boolean sorted = false;

        // Sort boxes based on their level
        while(!sorted) {
            sorted = true;

            // For every box, except the last one
            for(i = 0; i < boxes.size() - 1; i++) {
                // If the level of the next box is smaller than the level of the current box
                if(boxes.get(i + 1).getLevel() < boxes.get(i).getLevel()) {
                    Box2 tempBox = boxes.get(i);
                    boxes.set(i, boxes.get(i + 1));
                    boxes.set(i + 1, tempBox);
                    sorted = false;
                }
            }
        }

        // Go through every box to find positions
        for(i = 1; i < boxes.size(); i++) {
            if(boxes.get(i).getLevel() == boxes.get(i - 1).getLevel()) {
                // Set the x pos to the previous box's width and the y pos to the previous box's y pos if they're on the same level
                boxes.get(i).setPosx(boxes.get(i - 1).getWidth() + boxes.get(i - 1).getPosx());
                boxes.get(i).setPosy(boxes.get(i - 1).getPosy());
            } else if (tempTallest + boxes.get(i).getLength() <= stocks.get(x).getsheight()) {
                // If new level: reset x pos to 0, set the y pos to the temp tallest, set temp tallest to current length and pos y
                boxes.get(i).setPosx(0);
                boxes.get(i).setPosy(tempTallest);
                tempTallest = boxes.get(i).getLength() + boxes.get(i).getPosy();
            } 
            else if((x < stocks.size()-1) &&) {
            x++;
            i--;
            }
            else {
                boxes.get(i).setWidth(0);
                boxes.get(i).setLength(0);
            }
        }

        return boxes;
    }
     * 
     */
    
    
    public static ArrayList<Box2> setBoxesPositions(ArrayList<Box2> boxes, float boardHeight) {
        int i = 0;
        float tempTallest = boxes.get(0).getLength();
        boolean sorted = false;

        // Sort boxes based on their level
        while(!sorted) {
            sorted = true;

            // For every box, except the last one
            for(i = 0; i < boxes.size() - 1; i++) {
                // If the level of the next box is smaller than the level of the current box
                if(boxes.get(i + 1).getLevel() < boxes.get(i).getLevel()) {
                    Box2 tempBox = boxes.get(i);
                    boxes.set(i, boxes.get(i + 1));
                    boxes.set(i + 1, tempBox);
                    sorted = false;
                }
            }
        }

        // Go through every box to find positions
        for(i = 1; i < boxes.size(); i++) {
            if(boxes.get(i).getLevel() == boxes.get(i - 1).getLevel()) {
                // Set the x pos to the previous box's width and the y pos to the previous box's y pos if they're on the same level
                boxes.get(i).setPosx(boxes.get(i - 1).getWidth() + boxes.get(i - 1).getPosx());
                boxes.get(i).setPosy(boxes.get(i - 1).getPosy());
            } else if (tempTallest + boxes.get(i).getLength() <= boardHeight) {
                // If new level: reset x pos to 0, set the y pos to the temp tallest, set temp tallest to current length and pos y
                boxes.get(i).setPosx(0);
                boxes.get(i).setPosy(tempTallest);
                tempTallest = boxes.get(i).getLength() + boxes.get(i).getPosy();
            } else {
                boxes.get(i).setWidth(0);
                boxes.get(i).setLength(0);
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
        float boardWidth = 100;
        float boardLength = 200;
        ArrayList<Box2> sqrs = new ArrayList<>();
    }
}