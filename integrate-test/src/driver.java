package conversion;

import java.util.ArrayList;
import java.util.Random;

//this just tests shit
class driver
{

	public static void main(String[] args) 
	{	
		func meth = new func();
		int quantity = 6, i=0;
		ArrayList<Box> store = new ArrayList<Box>();
		float[] stock = {10,10,1};
		Random rand = new Random();
		int upb = 4;
		
		
		for(i=0;i<quantity;i++)
		{
			store.add(new Box());
			store.get(i).setwidth(rand.nextInt(upb)+1);
			store.get(i).setheight(rand.nextInt(upb)+1);
			store.get(i).setposx(0.0f);
			store.get(i).setposy(0.0f);
			store.get(i).setrender(true);
		}
		
		
  	ArrayList<Box> result = meth.cry(stock, store, quantity);
  	
  	//System.out.println(stock[0] + "    " + stock[1]);
  	
  	for(i=0;i<quantity;i++)
  	{
          System.out.print(i+1 + " width: " + result.get(i).getwidth() + "  ");
          System.out.print(i+1 + " height: " + result.get(i).getheight() + "  ");
          System.out.print(i+1 + " xcord: " + result.get(i).getposx() + "  ");
          System.out.print(i+1 + " ycord: " + result.get(i).getposy() + "  ");
          System.out.print(i+1 + " can render: " + result.get(i).getrender() + "  ");
          System.out.println("");
  	}
  	
	}
}