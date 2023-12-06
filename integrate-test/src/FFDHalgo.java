import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
class Stock {
    private float stockWidth, stockLength;

    public void setStockWidth(float stockWidth) { this.stockWidth = stockWidth; }
    public void setStockLength(float stockLength) { this.stockLength = stockLength; }
    public float getStockWidth() { return stockWidth; }
    public float getStockLength() { return stockLength; }

    public Stock(float stockWidth, float stockLength) {
        setStockWidth(stockWidth);
        setStockLength(stockLength);
    }
}

class Box2 {
    private float width, length, posx, posy;
    private int level, ID, grain, stock;

    public void setWidth(float width) { this.width = width; }
    public void setLength(float length) {this.length = length; }
    public void setPosx(float posx) { this.posx = posx; }
    public void setPosy(float posy) { this.posy = posy; }
    public void setLevel(int level) { this.level = level; }
    public void setID(int ID) { this.ID = ID; }
    public void setGrain(int grain) { this.grain = grain; }
    public void setStock(int stock) { this.stock = stock; }

    public float getWidth() { return width; }
    public float getLength() {return length; }
    public float getPosx() { return posx; }
    public float getPosy() { return posy; }
    public int getLevel() { return level; }
    public int getID(){ return ID; }
    public int getGrain() { return grain; }
    public int getStock() { return stock; }

    public Box2(float width, float length, int ID, int grain) {
        setWidth(width);
        setLength(length);
        setID(ID);
        setGrain(grain);
        setLevel(-1);
    }
}

class FFDH {
    public static ArrayList<Box2> simpleBoxSort(ArrayList<Box2> boxes, int graindir) {
        boolean sorted = false;
        int i = 0;
        float tempWidth = 0;
        boolean graincare = true;

        // Grain direction nightmare
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

    public static ArrayList<Box2> killCringeBoxes(ArrayList<Box2> boxes, ArrayList<Stock> stocks) {
        int i, j = 0;
        boolean canFit = true;

        // Go through every box
        for(i = 0; i < boxes.size(); i++) {
            // Go through every stock
            for(j = 0; j < stocks.size(); j++) {
                // If the width or the length of the box is longer than the board, don't render
                if (boxes.get(i).getWidth() > stocks.get(j).getStockWidth() || boxes.get(i).getLength() > stocks.get(j).getStockLength()) {
                    canFit = false;
                } else {
                    canFit = true;
                    break;
                }
            }

            if(!canFit) {
                // Set the width and length to 0 ;; non-renderable
                boxes.get(i).setWidth(0);
                boxes.get(i).setLength(0);
            }
        }

        return boxes;
    }

    public static ArrayList<Box2> setBoxesLevels(ArrayList<Box2> boxes, ArrayList<Stock> stocks, int graindir) {
        int i, t, j = 0;
        float [] currentLengthPerStock = new float[stocks.size()];
        float [][] levelWidths = new float[stocks.size()][boxes.size()];

        // "kill" boxes that cannot be rendered on any stock
        killCringeBoxes(boxes, stocks);

        // Get ordered boxes
        boxes = simpleBoxSort(boxes, graindir);

        // Initialize variables
        currentLengthPerStock[0] = boxes.get(0).getLength();
        levelWidths[0][0] = boxes.get(0).getWidth();
        boxes.get(0).setLevel(0);

        float testTallest = boxes.get(0).getLength();

        // Go through every box
        for(i = 1; i < boxes.size(); i++) {
            // Go through every stock
            stock:
            for(t = 0; t < stocks.size(); t++) {
                // Go through every level
                for(j = 0; j < boxes.size(); j++) {
                    // Find correct level when current widths of the level + the box are less than or equal to the board width
                    if((levelWidths[t][j] + boxes.get(i).getWidth() <= stocks.get(t).getStockWidth())
                            && (currentLengthPerStock[t] + boxes.get(i).getLength() <= stocks.get(t).getStockLength()))
                    {
                        // Always set the level and stock of the box
                        boxes.get(i).setLevel(j);
                        boxes.get(i).setStock(t);

                        // Always add to the running width
                        levelWidths[t][j] += boxes.get(i).getWidth();

                        // new level
                        if(boxes.get(i).getLevel() != boxes.get(i - 1).getLevel()) {
                            currentLengthPerStock[0] += boxes.get(i).getLength();
                        }

                        break stock;
                    }
                }
            }

            // Delete boxes that cannot be rendered anywhere
            if(boxes.get(i).getLevel() == -1) {
                boxes.get(i).setLength(0);
                boxes.get(i).setWidth(0);
            }
        }

        return boxes;
    }

    public static ArrayList<Box2> orderBoxesStocksLevels(ArrayList<Box2> boxes, ArrayList<Stock> stocks) {
        Collections.sort(boxes, new Comparator<Box2>() {
            @Override
            public int compare(Box2 obj1, Box2 obj2) {
                // Compare based on stock first
                int stockComparison = Integer.compare(obj1.getStock(), obj2.getStock());

                // If stock is the same, compare based on level
                if (stockComparison == 0) {
                    return Integer.compare(obj1.getLevel(), obj2.getLevel());
                }

                return stockComparison;
            }
        });

        return boxes;
    }

    public static ArrayList<Box2> setBoxesPositions(ArrayList<Box2> boxes, ArrayList<Stock> stocks) {
        int i, j = 0;
        float tempTallestPerStock [] = new float [stocks.size()];

        // Get order boxes based on their stocks then their level
        orderBoxesStocksLevels(boxes, stocks);

        // Initialize temp tallest per stock
        tempTallestPerStock[0] = boxes.get(0).getLength();

        // Go through every box
        for(i = 1; i < boxes.size(); i++) {
            // Go through every stock of every box
            for(j = 0; j < stocks.size(); j++) {
                // If the current stock is the same as the box's stock
                if(j == boxes.get(i).getStock()) {
                    if(boxes.get(i).getStock() != boxes.get(i - 1).getStock()) {
                        boxes.get(i).setPosx(0);
                        boxes.get(i).setPosy(0);
                        tempTallestPerStock[j] = boxes.get(i).getLength();
                    } else if(boxes.get(i).getLevel() == boxes.get(i - 1).getLevel()) {
                        // Set the x pos to the previous box's width and the y pos to the previous box's y pos if they're on the same level
                        boxes.get(i).setPosx(boxes.get(i - 1).getWidth() + boxes.get(i - 1).getPosx());
                        boxes.get(i).setPosy(boxes.get(i - 1).getPosy());
                    } else { //if(tempTallest + boxes.get(i).getLength() <= stocks.get(boxes.get(i).getStock()).getStockLength()) {
                        // If new level: reset x pos to 0, set the y pos to the temp tallest, set temp tallest to current length and pos y
                        boxes.get(i).setPosx(0);
                        boxes.get(i).setPosy(tempTallestPerStock[j]);
                        tempTallestPerStock[j] = boxes.get(i).getLength() + boxes.get(i).getPosy();

                    }
                }
            }
        }

        printBoxes(boxes);

        return boxes;
    }

    public static void printBoxes(ArrayList<Box2> boxes) {
        int i;

        // Iterate through every box to print their respective values
        for(i = 0; i < boxes.size(); i++) {
            System.out.println(boxes.get(i).getID() + ": " + boxes.get(i).getWidth() + "w, " + boxes.get(i).getLength() +
                    "l, " + boxes.get(i).getPosx() + "x, " + boxes.get(i).getPosy() + "y, lvl: " +
                    boxes.get(i).getLevel() + ", stock: " + boxes.get(i).getStock());
        }

        return;
    }

    public static void main(String[] args) {
        ArrayList<Box2> boxes = new ArrayList<>();
        ArrayList<Stock> stocks = new ArrayList<>();

        stocks.add(new Stock(10, 15));
        stocks.add(new Stock(10, 100));

        boxes.add(new Box2(2, 2, 0, 0));
        boxes.add(new Box2(1, (float)1.5, 1, 0));
        boxes.add(new Box2(4, 1, 2, 0));
        boxes.add(new Box2(4, 1, 3, 0));
        boxes.add(new Box2((float)0.5, (float)0.5, 4, 0));
        boxes.add(new Box2(5, 5, 5, 0));
        //boxes.add(new Box2(11, 11, 6, 0));
        boxes.add(new Box2(5, 7, 7, 0));
        boxes.add(new Box2(7, 5, 8, 0));
        boxes.add(new Box2(4, 2, 9, 0));
        boxes.add(new Box2(4, 3, 10, 0));

        setBoxesLevels(boxes, stocks, 0);
        setBoxesPositions(boxes, stocks);
        printBoxes(boxes);
    }
}