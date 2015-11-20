package SFLanguageParser;

import StateFlow.StateflowReservedKeywords;
import StateflowStructure.State;

public class IdentifierModifier extends GrammarTreeModifier {
	
	State mContainerState = null;
	
	public IdentifierModifier(State cstate) {
		mContainerState = cstate;
	}

	@Override
	public Object Modifiy(Object obj) {
		String idnt = (String)obj;
		State tmpstate = mContainerState;
		while (tmpstate != null && !tmpstate.ContainsData(idnt) && !tmpstate.ContainsEvent(idnt))
		{
			tmpstate = (State) tmpstate.getParent();
		}
		if (tmpstate != null)
		{
			if (tmpstate.ContainsEvent(idnt))
			{
				return idnt;
			}
			if (tmpstate.ContainsData(idnt))
			{
				return tmpstate.getDataName(idnt);
			}
		}
		if (StateflowReservedKeywords.IsReservedKeyword(idnt))
		{
			return idnt;
		}
		System.err.println("Serious error : Undeclared parameter:"+idnt+".");
		System.exit(1);
		return null;
	}
	
}