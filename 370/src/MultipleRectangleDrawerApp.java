import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MultipleRectangleDrawerApp {
    private JFrame frame;
    private JPanel inputPanel;
    private JPanel displayPanel;
    private JButton addButton;
    private JButton drawButton;
    private JButton eraseButton; // New button for erasing rectangles
    private List<RectangleInputPanel> rectangleInputPanels = new ArrayList<>();

    public MultipleRectangleDrawerApp() {
        frame = new JFrame("Multiple Rectangle Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        displayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRectangles(g);
            }
        };

        addButton = new JButton("Add Rectangle Input");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRectangleInputPanel();
            }
        });

        drawButton = new JButton("Draw Rectangles");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.repaint();
            }
        });

        eraseButton = new JButton("Erase Rectangles"); // Create an "Erase Rectangles" button
        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRectangles();
            }
        });

        inputPanel.add(addButton);
        inputPanel.add(drawButton);
        inputPanel.add(eraseButton); // Add the "Erase Rectangles" button

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
                new MultipleRectangleDrawerApp();
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

            // Set the preferred size for height and quantity fields
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
class funcc
{
    public static int totalcut(float[] cut, int row)
    {
        int num = 0;
        for (int i = 0; i < row * 3; i++)
        {
            if (i % 3 == 2) {
                num += cut[i];
            }
        }
        return num;
    }

    public static float[] cry(float[] stock, float[] cut, int row)
    {
        int tempj = 0;
        int num = totalcut(cut, row);
        Box[] obj = new Box[num];
        int temps = num * 4;
        float[] out = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < row * 3; i++)
        {
            if (i % 3 == 0)
            {
                int j;
                for (j = 0; j < cut[i + 2]; j++)
                {
                    obj[j + tempj] = new Box(cut[i], cut[i + 1]);
                }
                tempj += j;
            }
        }
        obj[0].posx = 0;
        obj[0].posy = 0;
        float tempx = 0;

        for (int i = 1; i < num; i++)
        {
            if ((obj[i].height + obj[i - 1].height + obj[i - 1].posy) <= stock[1])
            {
                obj[i].posy = obj[i - 1].height + obj[i - 1].posy;
                obj[i].posx = tempx;
            } else if ((obj[i].width + obj[i - 1].width + obj[i - 1].posx) <= stock[0])
            {
                obj[i].posx = obj[i - 1].width + obj[i - 1].posx;
                obj[i].posy = 0;
                tempx = obj[i].posx;
            } else
            {
                System.out.println("cant fit");
                break;
            }
        }



        for(int i=0;i<num;i++)
        {
            out[4*i]=obj[i].width;
            out[4*i+1]=obj[i].height;
            out[4*i+2]=obj[i].posx;
            out[4*i+3]=obj[i].posy;
        }


        return out;
    }
}

class Boxx {
    float width, height, posx, posy;

    public Boxx(float width, float height) {
        this.width = width;
        this.height = height;
    }
}