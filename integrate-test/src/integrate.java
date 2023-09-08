
public class integrate 
{
	public native void test();
	{
		System.loadLibrary("cs370");
	}
	
	public static void main(String[] args)
	{
		new integrate().test();
	}
}
