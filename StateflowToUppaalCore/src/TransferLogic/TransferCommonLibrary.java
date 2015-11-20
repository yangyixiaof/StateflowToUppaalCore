package TransferLogic;

import java.util.ArrayList;

import PositionLibrary.StatesLinesPositionLibrary;
import StateflowStructure.Chart;
import StateflowStructure.Data;
import StateflowStructure.State;
import StateflowStructure.StateflowConnector;
import Uppaal.UppaalAutomaton;
import Uppaal.UppaalState;
import Uppaal.UppaalTransition;

public class TransferCommonLibrary {

	public static String GetInitialNameOfCompState(StateflowConnector state) {
		return state.getID() + "_initial";
	}

	public static String GetCommonParallelTransitionName(StateflowConnector state,
			int idx) {
		return state.getID() + "_spt_" + (idx);
	}
	
	public static String GetCommonAutomatonCommunicateFunctionName(int parentid, int childstatid)
	{
		return "CommonAutomatonCommunicateWithControllerAndConditional("+parentid+","+childstatid+")";
	}

	public static String CompDataType(Data data) {
		String result = "";
		String size = data.getSizes();
		int arrsize = -1;
		if (size == null || size.equals(""))
		{
			arrsize = 1;
		}
		else
		{
			int pi = size.indexOf("[");
			int pl = size.indexOf("]");
			if (pl == -1) {
				pl = size.length();
			}
			String sizecnt = size.substring(pi + 1, pl);
			String[] sizes = sizecnt.split(",");
			if (sizes == null || sizes.length == 1) {
				sizes = sizecnt.split(" ");
			}
			if (sizes.length >= 3) {
				System.err
						.println("Serious error : Three or over dimesion matrix unsupported.");
				System.exit(1);
			}
			if (sizes.length == 1) {
				arrsize = Integer.parseInt(sizes[0]);
			} else {
				if (!sizes[0].equals("1") && !sizes[1].equals("1")) {
					System.err.println("Serious error : matrix unsupported.");
					System.exit(1);
				}
				arrsize = Integer.parseInt(sizes[0]);
				if (arrsize == 1) {
					arrsize = Integer.parseInt(sizes[1]);
				}
			}
		}
		if (arrsize <= 1) {
			result = "ST:" + data.getDatatypestr() + ":" + data.getInivalue();
		} else {
			result = "STA:" + arrsize + ":" + data.getDatatypestr();
		}
		return result;
	}

	public static void AddAutomatonArrayToArrayList(
			ArrayList<UppaalAutomaton> result, UppaalAutomaton[] uas) {
		int len = uas.length;
		int i = 0;
		for (i = 0; i < len; i++) {
			result.add(uas[i]);
		}
	}
	
	public static String GetIfDispatchedToConditionalOnceVariable()
	{
		return "mTryFindPath";
	}
	
	public static String GetFunctionNameOfClearPathSelectArrayInConditionalAutomaton(String parentfullname) {
		return "Clear" + parentfullname + "PathSelect()";
	}
	
	public static String GetFunctionNameOfClearPathSelectArrayInConditionalAutomatonAtDepth(String parentfullname, int depth) {
		return "Clear" + parentfullname + "PathSelectAtDepth(" + depth + ")";
	}

	/*public static String GetClearPathJudgeResultByParentFullName(String parentfullname) {
		return "Clear" + parentfullname + "JudgePathResult()";
	}*/

	public static String GetStateControllerName(StateflowConnector state) {
		return state.getID() + "_controller";
	}


	public static void GenerateOnlyBackDeirectTransition(State parentstate,
			UppaalState ini, UppaalState oneus, UppaalAutomaton ua) {
		int parentid = RunEnvironment.getmStateManager().GetStateId(parentstate);
		int type = ua.getType();
		new UppaalTransition(ini.getName() + "_" + oneus.getName() + "td1",
				oneus, ini, "", "JVT2I(" + parentid + "," + type + ")", "",
				"DelEvtIfOnlyBack()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, oneus, 0));
	}

	public static UppaalState CreateTwoWayUppaalState(String sname, String meaningname, int id,
			State parentstate, UppaalState ini, UppaalAutomaton ua, int x, int y) {
		UppaalState result = ua.GetUppaalState(sname, meaningname, false, true, false, id,
				x, y);
		if (!result.isHastoini()) {
			GenerateTwoWayDeirectTransition(parentstate, ini, result, ua);
			result.setHastoini(true);
		}
		return result;
	}
	
	private static void GenerateTwoWayDeirectTransition(State parentstate,
			UppaalState ini, UppaalState oneus, UppaalAutomaton ua) {
		int parentid = RunEnvironment.getmStateManager().GetStateId(parentstate);
		int type = ua.getType();
		new UppaalTransition(ini.getName() + "_" + oneus.getName() + "td0",
				ini, oneus, "", "JVI2T(" + parentid + "," + oneus.getID() + ","
						+ type + ")", "", "PopOneEvent()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, oneus, 0));
		new UppaalTransition(ini.getName() + "_" + oneus.getName() + "td1",
				oneus, ini, "", "JVT2I(" + parentid + "," + type + ")", "",
				"DelEvtIfOnlyBack()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, oneus, 0));
	}

	public static UppaalState CreateOnlyBackUppaalState(String sname, String meaningname, int id,
			State parentstate, UppaalState ini, UppaalAutomaton ua, int x, int y) {
		UppaalState result = ua.GetUppaalState(sname, meaningname, false, true, false, id,
				x, y);
		if (!result.isHastoini()) {
			GenerateOnlyBackDeirectTransition(parentstate, ini, result, ua);
			result.setHastoini(true);
		}
		return result;
	}

	public static UppaalState CreateRawUppaalState(String sname, String meaningname, boolean isIni,
			int id, UppaalAutomaton ua, int x, int y) {
		UppaalState result = ua.GetUppaalState(sname, meaningname, isIni, true, false, id,
				x, y);
		return result;
	}
	
	public static String GetParentFullName(StateflowConnector state) {
		String result = null;
		if (state.HasParent()) {
			result = ((State) state.getParent()).GetFullName();
		} else {
			System.err
					.println("Serious error,no parent.How to get parent full name without parent?");
			System.exit(1);
		}
		return result;
	}
	
	public static int GetParentId(StateflowConnector state) {
		int parentid = -1;
		if (state.HasParent()) {
			parentid = RunEnvironment.getmStateManager().GetStateId((State) state.getParent());
		} else {
			if (!(state instanceof Chart)) {
				System.err.println("Serious error,no parent state id:"
						+ state.getID()
						+ ". How to get parent id without parent?");
				System.exit(1);
			}
		}
		return parentid;
	}
	
	public static String GenerateEntryTransitionPrefix(State state) {
		return state.getID() + "_controller_entry";
	}

	public static String GenerateDuringTransitionPrefix(State state) {
		return state.getID() + "_controller_during";
	}

	public static String GenerateExitTransitionPrefix(State state) {
		return state.getID() + "_controller_exit";
	}
	
	public static String GetCondAutomatonResourceArrayName()
	{
		return "mSourceRecordArray";
	}
	
	public static String GetStateName(StateflowConnector beginstate, State parentstate) {
		return beginstate == null ? parentstate.GetFullName() + "Initial"
				: beginstate.GetFullName();
	}
	
}