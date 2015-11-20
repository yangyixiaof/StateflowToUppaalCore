package SFLanguageParser;

import StateflowStructure.State;

public class FunctionNameModifier extends GrammarTreeModifier {
	
	State mContainerState = null;

	public FunctionNameModifier(State statepara) {
		this.mContainerState = statepara;
	}
	
	@Override
	public String[] Modifiy(Object obj) {
		String funcname = (String)obj;
		State tmpstate = mContainerState;
		String[] result = new String[3];
		switch (funcname) {
		case "int32":
		case "int16":
		case "int8":
		case "uint32":
		case "uint16":
		case "uint8":
			result[0]="";
			result[1]="";
			result[2]="";
			break;
		default:
			while (tmpstate != null && !tmpstate.ContainsData(funcname))// && !tmpstate.ContainsFunction(funcname)
			{
				tmpstate = (State) tmpstate.getParent();
			}
			if (tmpstate != null)
			{
				result[0]=funcname;
				result[1]="[";
				result[2]="]";
			}
			else
			{
				result[0]=funcname;
				result[1]="(";
				result[2]=")";
			}
			break;
		}
		return result;
	}

}