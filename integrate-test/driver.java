package conversion;

class driver
{

	public static void main(String[] args) 
	{	
		int row = 4, i=0;
		float[] cut = {2,10,1,3,10,1,3,5,1,4,4,1};
		float[] stock = {10,10,3};
		
		func meth = new func();
    	int num = meth.totalcut(cut, row);
    	float[] result = meth.cry(stock, cut, row);
    	
    	for(i=0;i<(num*4);i++)
    	{
            System.out.println(i+1 + ": " + result[i]);
    	}
    	
	}
}