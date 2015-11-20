package StateFlow;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import CommonLibrary.FileDeleter;
import CommonLibrary.FilePathParser;
import CommonLibrary.ZtZip;

public class StateFlowReader {
	
	static String targetuncompress = null;
	
	public static Document ReadDocument(String sourcefile) throws Exception
	{
		targetuncompress = FilePathParser.trimSuffix(sourcefile);
		ZtZip.UnpackFile(sourcefile, targetuncompress);
		String ssfile = targetuncompress + File.separator + "simulink"
				+ File.separator + "blockdiagram.xml";
		SAXBuilder builder = new SAXBuilder();
		InputStream file = new FileInputStream(ssfile);
		return builder.build(file);
	}
	
	public static void ReleaseResource()
	{
		FileDeleter.DeleteFolder(targetuncompress);
	}
	
}
