package conversion;
import java.util.ArrayList;
import java.util.Random;

class func 
{
//function to do the algorithm. it reads the cut width and height and seeds them into a 2d grid
//it returns the cut width and height and the cuts x and y coordinates.
    public static ArrayList<Box> cry(float[] stock, ArrayList<Box> barr, int num) 
    {
    	// initialize func class to call methods
    	func call = new func();
    	
        int i = 0;
        // creates an arraylist of the box object
        //ArrayList<Box> barr = new ArrayList<Box>();
        
        /*
        //populates the arraylist with the user inputs for width and height
        for(i=0;i<num;i++)
        {
        	barr.add(new Box());
            barr.get(i).setwidth(store[i].getwidth());
            barr.get(i).setheight(store[i].getheight());
            barr.get(i).setposx(0.0f);
            barr.get(i).setposy(0.0f);
        }*/
        
        /*
        //does the actual seeding by stacking boxes on top of each other and when it gets to the end of the board shifts it to the right.
        //tempx is for x value
        float tempx=0;
        for(i=1;i<num;i++)
        {
        	// if the height of the current cut + the height of the previous cut + the y cord of the previous cut
        	// is equal too or less than the height of the stock  then put it on the grid
        	if(barr.get(i).getheight() + barr.get(i-1).getheight() + barr.get(i-1).getposy() <= stock[1] && call.checkcoll(i,barr)==true)
        	{
        		barr.get(i).setposy(barr.get(i-1).getheight() + barr.get(i-1).getposy());
        		barr.get(i).setposx(tempx);
        	}
        	//the else if initiates when the boxes cannot fit vertically on the stock
        	
        	// if the width of the current cut + the width of the previous cut + the x cord of the previous cut
        	// is equal too or less than the width of the stock  then put it on the grid
        	else if(barr.get(i).getwidth() + barr.get(i-1).getwidth() + barr.get(i-1).getposx() <= stock[0] && call.checkcoll(i,barr)==true)
        	{
        		barr.get(i).setposx(barr.get(i-1).getwidth() + barr.get(i-1).getposx());
        		barr.get(i).setposy(0.0f);
        	}
        	
        	//this says that the cuts cannot fit in the grid and will cause an error msg to the user.
        	else
        	{
        		return null;
        	}
        }*/
        
        
        //sorting algorithm
        // sort it based on height
        heightsort(barr);
        //set initial value and check for stock height and width
        if(barr.get(num-1).getheight()< stock[1] && barr.get(num-1).getwidth() < stock[0])
        {
        	barr.get(num-1).setposx(0);
        	barr.get(num-1).setposy(0);
        }
        
        //ceiling boolean to tell when i flip
        // first boolean to tell when i initially flip
        boolean ceiling = false;
        boolean first = true;
        
        //starts at the back because i ordered the biggest number that way
        for(i=num-2;i>=0;i--)
        {
        	//System.out.println(i + ":checkcoll2: " + checkcoll2(barr,i,stock));
        	
        	// if it fits shove it in
        	if(barr.get(i).getwidth()+ barr.get(i+1).getwidth() + barr.get(i+1).getposx() <= stock[0] && ceiling==false)
        	{
        		barr.get(i).setposx(barr.get(i+1).getwidth() + barr.get(i+1).getposx());
        		barr.get(i).setposy(barr.get(i+1).getposy());
        	}
        	else
        	{
        		ceiling = true;
        	}
        	
        	
        	if(ceiling==true && first==true)
        	{
        		//i shove it in the ceiling but then do the collision check
        		barr.get(i).setposx(stock[0] - barr.get(i).getwidth());
        		barr.get(i).setposy(stock[1] - barr.get(i).getheight());
        		
            	if(checkcoll2(barr,i,stock)==true)
            	{
            		barr.get(i).setrender(false);
            		first = true;
            		System.out.println(i +":x:" + barr.get(i+1).getposx() + "    y: " + barr.get(i+1).getposy());
            		// set its posx and posy to the previous value so it doesnt get confused
            		barr.get(i).setposx(barr.get(i+1).getposx());
            		barr.get(i).setposy(barr.get(i+1).getposy());
            	}
            	else if(checkcoll2(barr,i,stock)==false)
            	{
            		first = false;
            	}
        	}
        	
    		// if collision check is true make the cut not render
        	if((barr.get(i+1).getposx() - barr.get(i).getwidth() >= 0) && (ceiling==true) && (checkcoll2(barr,i,stock)==false))
        	{
        		barr.get(i).setposx(barr.get(i+1).getposx() - barr.get(i).getwidth());
        		barr.get(i).setposy(stock[1] - barr.get(i).getheight());
        	}
        	else if(ceiling == true && checkcoll(barr,i) == true)
        	{
        		barr.get(i).setrender(false);
        	}
        }
        
        return barr;
    }
    
    //checks collision of the box about to be placed
    public static boolean checkcoll(ArrayList<Box> barr, int x)
	{
    	int n = barr.size()-1;
    	//out is the output variable
    	boolean out = false;
    	//define the new boxes bottom left cords
    	float newbx = barr.get(x+1).getwidth() + barr.get(x+1).getposx();
    	float newby = barr.get(x+1).getposy();
    	//define the boxes top right cords
    	float newtx = newbx + barr.get(x).getwidth();
    	float newty = newby + barr.get(x).getheight();
    	
    	// iterate through all placed boxes checking collision
    	for(int i=n;i>=0;i--)
       	{ 
       		// if the y values of the boxes overlap move on and check if the x overlaps. else no collision
       		// if the x overlaps return true that it collides. else continue iterating
       		// this is a disgusting if statement
       		if((((newty > (barr.get(i).getposy() - 0.001)) && (newty < (barr.get(i).getposy() + barr.get(i).getheight()) + 0.001)) 
       		|| (newtx > (barr.get(i).getposx() - 0.001)) && (newtx < (barr.get(i).getposx() + barr.get(i).getwidth() + 0.001)))
       		&& (((newbx > (barr.get(i).getposx() - 0.001)) && (newbx < (barr.get(i).getposx() + barr.get(i).getwidth() + 0.001)))
       		|| ((newby > (barr.get(i).getposy()- 0.001)) && (newby < (barr.get(i).getposy() + barr.get(i).getheight() + 0.001)))))
       		{
       			return true;
       		}
       		
       		
       	}
		return out;
	}
    public static boolean checkcoll2(ArrayList<Box> barr, int x, float stock[])
   	{
       	int n = barr.size()-1;
       	//out is the output variable
       	boolean out = false;
       	//define the new boxes bottom left cords
       	float newbx = (barr.get(x+1).getposx() - barr.get(x).getwidth());
       	float newby = stock[1] - barr.get(x).getheight();
       	//define the boxes top right cords
       	float newtx = newbx + barr.get(x).getwidth();
       	float newty = newby + barr.get(x).getheight();
       	
       //	System.out.println(x+ " :blx: " + newbx + "   bly: " + newby + "   trx: " + newtx + "   try: " + newty);
       	
       	// iterate through all placed boxes checking collision
       	for(int i=n;i>=0;i--)
       	{ 
       		// if the y values of the boxes overlap move on and check if the x overlaps. else no collision
       		// if the x overlaps return true that it collides. else continue iterating
       		// this is a disgusting if statement
       		if((((newty > (barr.get(i).getposy() - 0.001)) && (newty < (barr.get(i).getposy() + barr.get(i).getheight()) + 0.001)) 
       		|| (newtx > (barr.get(i).getposx() - 0.001)) && (newtx < (barr.get(i).getposx() + barr.get(i).getwidth() + 0.001)))
       		&& (((newbx > (barr.get(i).getposx() - 0.001)) && (newbx < (barr.get(i).getposx() + barr.get(i).getwidth() + 0.001)))
       		|| ((newby > (barr.get(i).getposy()- 0.001)) && (newby < (barr.get(i).getposy() + barr.get(i).getheight() + 0.001)))))
       		{
       			return true;
       		}
       		
       		
       	}
   		return out;
   	}
    
    
    //bubble sort to organize by height
    public static void heightsort(ArrayList<Box> barr)
    {
    	// x is to limit the size
    	int x = barr.size();
    	boolean done;
    	
    	do
    	{
    		// aslong as the if statement in the recursive statement occurs
    		// because of the done boolean it will keep iterating 
    		// each full iteration thru the data sets doesnt need to check the last number 
    		//since the biggest numbers should be slid to the end
    		done = false;
    		for(int i=0;i<x-1;i++)
    		{
    			if(barr.get(i).getheight() > barr.get(i+1).getheight())
    			{
    				Box temp = barr.get(i);
    				barr.set(i, barr.get(i+1));
    				barr.set(i+1,temp);
    				done=true;
    			}
    			
    		}
    		x--;
    	}
    	while(done);
    }
}

//class box with variable width, height, coordinate x, and coordinate y.
class Box {
  float width, height, posx, posy;
  boolean canRender;

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
  public void setrender(boolean render)
  {
  	this.canRender=render;
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
  public boolean getrender()
  {
  	return canRender;
  }
}