package CommonLibrary;

import SFLanguageParser.ParseSFLanguage;
import StateflowStructure.State;

public class ModifyActionHelper {
	
	public static String ModifyOneAction(String actionorcondition, State container)
	{
		boolean colhandle = false;
		String hstr = actionorcondition;
		if (!hstr.endsWith(";")) {
			colhandle = true;
			hstr += ";";
		}
		String tmpcnt = ParseSFLanguage.TransferFunctionContent(hstr,
				container);
		if (colhandle) {
			if (!tmpcnt.endsWith(";")) {
				System.err
						.println("Serious error : parsed content is not end with ';'."+"    wrong cnt is : "+tmpcnt+"........Initial cnt is : "+hstr);
			}
			tmpcnt = tmpcnt.substring(0, tmpcnt.length() - 1);
		}
		return tmpcnt;
	}
	
}
