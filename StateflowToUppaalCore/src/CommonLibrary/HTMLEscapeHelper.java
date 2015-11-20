package CommonLibrary;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HTMLEscapeHelper {
	
	private static Map<String, String> CommonCharToHTMLEscapeMap = new TreeMap<String, String>();
	static {
		getCommonCharToHTMLEscapeMap().put("&&", "&amp;&amp;");
		getCommonCharToHTMLEscapeMap().put("<", "&lt;");
		getCommonCharToHTMLEscapeMap().put(">", "&gt;");
	}
	
	private static Map<String, String> HTMLEscapeToCommonCharMap = new TreeMap<String, String>();
	static {
		getHTMLEscapeToCommonCharMap().put("&amp;&amp;", "&&");
		getHTMLEscapeToCommonCharMap().put("&lt;", "<");
		getHTMLEscapeToCommonCharMap().put("&gt;", ">");
	}
	
	private static String IterateMapKeyAndReplaceByValue(String cnt, Map<String, String> tmap)
	{
		Set<String> keysets = tmap.keySet();
		String result = cnt;
		for (String key : keysets)
		{
			String value = tmap.get(key);
			result = result.replaceAll(key, value);
		}
		return result;
	}
	
	public static String CommonCharToHTMLEscape(String commonstr)
	{
		return IterateMapKeyAndReplaceByValue(commonstr, getCommonCharToHTMLEscapeMap());
	}
	
	public static String HTMLEscapeToCommonChar(String escapestr)
	{
		return IterateMapKeyAndReplaceByValue(escapestr, getHTMLEscapeToCommonCharMap());
	}

	public static Map<String, String> getCommonCharToHTMLEscapeMap() {
		return CommonCharToHTMLEscapeMap;
	}

	public static void setCommonCharToHTMLEscapeMap(
			Map<String, String> commonCharToHTMLEscapeMap) {
		CommonCharToHTMLEscapeMap = commonCharToHTMLEscapeMap;
	}

	public static Map<String, String> getHTMLEscapeToCommonCharMap() {
		return HTMLEscapeToCommonCharMap;
	}

	public static void setHTMLEscapeToCommonCharMap(
			Map<String, String> hTMLEscapeToCommonCharMap) {
		HTMLEscapeToCommonCharMap = hTMLEscapeToCommonCharMap;
	}
	
}