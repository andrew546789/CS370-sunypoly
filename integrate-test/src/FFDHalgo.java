import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.abs;

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

    public static ArrayList<Box2> setBoxesLevels(ArrayList<Box2> boxes, float boardWidth, float boardLength) {
        float [] runningWidths = new float[boxes.size()];
        //float [] runningLengths = new float[boxes.size()];
        float runningLengths = boxes.get(0).getLength();
        int i, level = 0;

        // Get ordered boxes
        boxes = simpleBoxSort(boxes);

        // Add the first box to the running width and length
        runningWidths[level] += boxes.get(0).getWidth();
        //runningLengths[level] += boxes.get(0).getLength();

        // Go through every box
        for(i = 0; i < boxes.size(); i++) {
            // Go through every level
            for (int j = 0; j < runningWidths.length; j++) {
                // Find correct level when current widths of the level + the box are less than or equal to the board width
                if ((runningWidths[j] + boxes.get(i).getWidth()) <= boardWidth) {
                    // Set level if the box can fit
                    level = j;

                    // Always set the level of the box
                    boxes.get(i).setLevel(level);

                    // Always add to the running width
                    runningWidths[level] += boxes.get(i).getWidth();

                    break;
                }
            }
        }

        for(level = 0; i < boxes.size(); level++) {

        }

        return boxes;
    }

    public static ArrayList<Box2> setBoxesPositions(ArrayList<Box2> boxes) {
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
        float boardWidth = 100;
        float boardLength = 200;
        ArrayList<Box2> sqrs = new ArrayList<>();

        // Add the board as the first object
        //sqrs.add(new Box2(50, 50, 0, 0));

        sqrs.add(new Box2(60, 30, 0, 0));
        sqrs.add(new Box2(20, 90, 0, 0));
        sqrs.add(new Box2(50, 10, 0, 0));
        sqrs.add(new Box2(70, 60, 0, 0));
        sqrs.add(new Box2(10, 30, 0, 0));
        sqrs.add(new Box2(60, 30, 0, 0));
        sqrs.add(new Box2(20, 90, 0, 0));
        sqrs.add(new Box2(50, 20, 0, 0));
        sqrs.add(new Box2(70, 60, 0, 0));
        sqrs.add(new Box2(10, 30, 0, 0));

/*
        //create 5 boxes with random values
        for (i = 0; i < 5; i++) {
            sqrs.add(new Box2(rand.nextInt(10), rand.nextInt(10), 0, 0));
        }
*/

        // Print boxes values
        printBoxes(sqrs, boardWidth);

        System.out.println("---------------------");

        setBoxesLevels(sqrs, boardWidth, boardLength);
        setBoxesPositions(sqrs);

        printBoxes(sqrs, boardWidth);
    }
}