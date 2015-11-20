package CommonLibrary;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
	public RegularExpression() {
	}
	
	public static void main(String[] args) {
		GetTimeConcernType("after", "x==1 && after(1,1) && y==5 && after(3,sec) && koolos");
	}
	
	public static ArrayList<String> GetAllTimeConcern(String expression)
	{
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(GetTimeConcernType("after", expression));
		result.addAll(GetTimeConcernType("before", expression));
		result.addAll(GetTimeConcernType("at", expression));
		result.addAll(GetTimeConcernType("every", expression));
		return result;
	}
	
	public static ArrayList<String> GetTimeConcernType(String type, String expression)
	{
		String regStr = "("+type+"\\([^,]*,[^;]*\\))";
		return GetGeneralContents(regStr, expression);
	}
	
	private static ArrayList<String> GetGeneralContents(String regStr, String expression)
	{
		Pattern pattern = Pattern.compile(regStr);
		Matcher matcher = pattern.matcher(expression);
		ArrayList<String> arrlist = new ArrayList<String>();
		while (matcher.find()) {
			// 此时拿到的group1，就是在上面定好的组，对应了括号里面的内容
			int mgrpcount = matcher.groupCount();
			int i=0;
			for (i=0;i<mgrpcount;i++)
			{
				arrlist.add(matcher.group(i+1));
			}
		}
		return arrlist;
	}
	
	private static boolean CheckTimeConcernType(String type)
	{
		boolean result = false;
		switch (type) {
		case "after":
		case "before":
		case "at":
		case "every":
			result = true;
			break;
		default:
			System.err.println("Unreconginized timed concern type,the four time concern operation we support is : after,before,at,every");
			System.exit(1);
			break;
		}
		return result;
	}
	
	public static ArrayList<ArrayList<String>> ExtractThreePartsInEveryString(ArrayList<String> strs)
	{
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		int i=0;
		int len=strs.size();
		for (i=0;i<len;i++)
		{
			String onestr = strs.get(i);
			ArrayList<String> tparts = GetGeneralContents("([^(]*)\\(([^(,]*),([^),]*)\\)", onestr);
			tparts.set(0, tparts.get(0).trim());
			tparts.set(1, tparts.get(1).trim());
			tparts.set(2, tparts.get(2).trim());
			//testing
			if (tparts.size() != 3 || !CheckTimeConcernType(tparts.get(0)))
			{
				System.err.println("not right grammar of four type time concern operation:after/before/at/every(int,event)");
				System.exit(1);
			}
			result.add(tparts);
		}
		return result;
	}
	
	public static String[] GetTransitionFourParts(String expression)
	{
		String regStr = "([^(\\[/{})]+)?(\\[[^]{}]*\\])?(\\{[^}]*\\})?(/\\{[^}]*\\})?";
		return GetContent(regStr, expression);
	}
	
	private static String[] GetContent(String reglr, String expression)
	{
		String[] result = new String[4];
		Pattern pattern = Pattern.compile(reglr);
		Matcher matcher = pattern.matcher(expression);
		if (matcher.find()) {
			// 此时拿到的group1，就是在上面定好的组，对应了括号里面的内容
			result[0] = matcher.group(1);
			result[1] = matcher.group(2);
			result[2] = matcher.group(3);
			result[3] = matcher.group(4);
		}
		return result;
	}
	
}