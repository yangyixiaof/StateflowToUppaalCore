package TransferLogic;

import java.util.ArrayList;

import CommonLibrary.ModifyActionHelper;
import CommonLibrary.TransitionLabelParser;
import StateflowStructure.State;
import StateflowStructure.Transition;

public class ActionLabelGlobalModifier {
	
	public static void ModifyAllThreeActionsInStates()
	{
		ArrayList<State> allstates = RunEnvironment.getmStateManager().GetAllStatesAsArrayList();
		int i=0;
		int len=allstates.size();
		for (i=0;i<len;i++)
		{
			State state = allstates.get(i);
			String entry = state.getEntry();
			if (entry != null && !entry.equals(""))
			{
				state.setEntry(ModifyActionHelper.ModifyOneAction(entry, state));
			}
			String during = state.getDuring();
			if (during != null && !during.equals(""))
			{
				state.setDuring(ModifyActionHelper.ModifyOneAction(during, state));
			}
			String exit = state.getExit();
			if (exit != null && !exit.equals(""))
			{
				state.setExit(ModifyActionHelper.ModifyOneAction(exit, state));
			}
		}
	}
	
	//Iterate StateManager.
	public static void ExtractAllFourPartsAndModify()
	{
		ArrayList<State> allstates = RunEnvironment.getmStateManager().GetAllStatesAsArrayList();
		int i=0;
		int len=allstates.size();
		for (i=0;i<len;i++)
		{
			State state = allstates.get(i);
			ArrayList<Transition> trans = state.getAllChildTransitions();
			for (Transition tran : trans)
			{
				String label = tran.getLabelString();
				String[] fourparts = TransitionLabelParser.ExtractPartsInTransitionWithModify(label, tran.getContainer());
				tran.setFourparts(fourparts);
			}
		}
	}
	
}