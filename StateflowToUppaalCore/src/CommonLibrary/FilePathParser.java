package CommonLibrary;

public class FilePathParser {
	
	public static int getLastPathIndex(String path)
	{
		int lastpathidx = path.lastIndexOf('/');
		if (lastpathidx == -1)
		{
			lastpathidx = path.lastIndexOf('\\');
		}
		return lastpathidx;
	}
	
	public static String getPath(String fullpath)
	{
		try {
			return fullpath.substring(0, getLastPathIndex(fullpath));
		} catch (Exception e) {
		}
		return "";
	}
	
	public static String getFile(String fullpath)
	{
		return fullpath.substring(getLastPathIndex(fullpath)+1, fullpath.length());
	}
	
	public static String trimSuffix(String path)
	{
		int ldx = path.lastIndexOf('.');
		if (ldx == -1)
		{
			ldx = path.length();
		}
		return path.substring(0, ldx);
	}
	
}