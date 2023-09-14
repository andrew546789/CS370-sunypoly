package conversion;
import java.util.ArrayList; 


class func 
{
//function to do the algorithm. it reads the cut width and height and seeds them into a 2d grid
//it returns the cut width and height and the cuts x and y coordinates.
    public static ArrayList<Box> cry(float[] stock, Box[] cut, int num) 
    {
        int i = 0;
        // creates an arraylist of the box object
        ArrayList<Box> barr = new ArrayList<Box>();
        
        //populates the arraylist with the user inputs for width and height
        for(i=0;i<num;i++)
        {
        	barr.add(new Box());
            barr.get(i).setwidth(cut[i].getwidth());
            barr.get(i).setheight(cut[i].getheight());
            barr.get(i).setposx(0.0f);
            barr.get(i).setposy(0.0f);
        }
        
        //does the actual seeding by stacking boxes on top of each other and when it gets to the end of the board shifts it to the right.
        //tempx is for x value
        float tempx=0;
        for(i=1;i<num;i++)
        {
        	// if the height of the current cut + the height of the previous cut + the y cord of the previous cut
        	// is equal too or less than the height of the stock  then put it on the grid
        	if(barr.get(i).getheight() + barr.get(i-1).getheight() + barr.get(i-1).getposy() <= stock[1])
        	{
        		barr.get(i).setposy(barr.get(i-1).getheight() + barr.get(i-1).getposy());
        		barr.get(i).setposx(tempx);
        		
        	}
        	//the else if initiates when the boxes cannot fit vertically on the stock
        	
        	// if the width of the current cut + the width of the previous cut + the x cord of the previous cut
        	// is equal too or less than the width of the stock  then put it on the grid
        	else if(barr.get(i).getwidth() + barr.get(i-1).getwidth() + barr.get(i-1).getposx() <= stock[0])
        	{
        		barr.get(i).setposx(barr.get(i-1).getwidth() + barr.get(i-1).getposx());
        		barr.get(i).setposy(0.0f);
        	}
        	
        	//this says that the cuts cannot fit in the grid and will cause an error msg to the user.
        	else
        	{
        		return null;
        	}
        }
        
        return barr;
    }
}

//class box with variable width, height, coordinate x, and coordinate y.
class Box {
    float width, height, posx, posy;

    public Box() 
    {
    }
    
    //a whole bunch of setters
    public void setwidth(float width)
    {
    	this.width=width;
    }
    public void setheight(float height)
    {
    	this.height=height;
    }
    public void setposx(float posx)
    {
    	this.posx=posx;
    }
    public void setposy(float posy)
    {
    	this.posy=posy;
    }
    
    // a whole bunch of getters.
    public float getwidth()
    {
    	return width;
    }
    public float getheight()
    {
    	return height;
    }
    public float getposx()
    {
    	return posx;
    }
    public float getposy()
    {
    	return posy;
    }
}