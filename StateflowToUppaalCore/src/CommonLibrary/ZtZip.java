package CommonLibrary;


import java.io.File;

import org.zeroturnaround.zip.ZipUtil;

public class ZtZip {
	
	public static void UnpackFile(String sourcepath,String targetpath)
	{
		ZipUtil.unpack(new File(sourcepath), new File(targetpath));
	}
	
}
