package CommonLibrary;

public class SimpleParserHelper {
	
	public static boolean IsEventDispatch(String cnt)
	{
		String pattern = "[a-zA-Z_][a-zA-Z0-9_]+";
		return (cnt.matches(pattern)) || cnt.startsWith("send(");
	}
	
	public static boolean IsNumber(String cnt)
	{
		String pattern = "[-]?[0-9]+";
		return cnt.matches(pattern);
	}
	
}
