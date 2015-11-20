package TransferLogic;

import java.io.File;

import CommonLibrary.FilePathParser;
import StateFlow.StateflowController;
import Uppaal.UppaalController;

public class MainFunc {
	
	public MainFunc() {
	}
	
	public void TransferStateflowToUppaal(String[] args) throws Exception
	{
		if (args.length < 1)
		{
			throw new Exception("Which StateFlow File To Translate?");
		}
		String sourcefile = args[0];
		String sfile = FilePathParser.getFile(sourcefile);
		if (!sfile.endsWith(".slx"))
		{
			throw new Exception("Only slx file supportted.");
		}
		String targetfile = "";
		if (args.length >= 2)
		{
			targetfile = args[1];
			if (!targetfile.endsWith(".xml"))
			{
				targetfile += (targetfile.endsWith("/") || targetfile.endsWith("\\") ? "" : File.separator) + sfile.replace(".slx", ".xml");
			}
		}
		else
		{
			targetfile = sourcefile.replace(".slx", ".xml");
		}
		RunEnvironment.InitializeAllEnvironments();
		StateflowController sfc = new StateflowController(sourcefile);
		UppaalController upc = new UppaalController(targetfile);
		TransferController tc = new TransferController(sfc,upc);
		tc.TransferStateflowToUppaal();
	}
	
	public static void main(String[] args)
	{
		MainFunc mf = new MainFunc();
		try {
			mf.TransferStateflowToUppaal(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred:" + e.getMessage());
		}
	}
	
}