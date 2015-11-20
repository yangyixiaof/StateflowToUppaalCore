package StateFlow;

public class StateflowReservedKeywords {
	
	public final static String[] reservedwords = {"sec","msec","usec"};
	
	public static boolean IsReservedKeyword(String identity)
	{
		int i=0;
		int len=reservedwords.length;
		for (i=0;i<len;i++)
		{
			if (identity.equals(reservedwords[i]))
			{
				return true;
			}
		}
		return false;
	}
	
}