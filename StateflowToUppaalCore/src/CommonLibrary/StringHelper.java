package CommonLibrary;

public class StringHelper {
	
	public static boolean IsNullOrEmpty(String str)
	{
		if (str == null || str.equals(""))
		{
			return true;
		}
		return false;
	}
	
}