package TransferLogic;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import CommonLibrary.RegularExpression;
import StateflowStructure.Junction;
import StateflowStructure.MockedState;
import StateflowStructure.SimulinkModel;
import StateflowStructure.State;
import StateflowStructure.StateflowConnector;
import StateflowStructure.Transition;
import Uppaal.UppaalDeclaration;

public class EventTimeStatistic {
	//translate time concern condition including ini state. 
	
	//State ssid to eventfullname map
	//local map record which state need to update the time concern events.
	//Map<String, ArrayList<String>> localparentevtmap = new TreeMap<String, ArrayList<String>>();
	//which evt counts should the child state compute.
	//local map record which state's out use the time concern events count only id stored.
	Map<String, Integer> localevtmap = new TreeMap<String, Integer>();
	Map<String, Boolean> mVisitRecord = new TreeMap<String, Boolean>();
	Map<String, Boolean> mLevelVisitRecord = new TreeMap<String, Boolean>();
	//global map record all event which could be used.
	//Map<String, Boolean> globalevtmap = new TreeMap<String, Boolean>();
	public static final String inipostfix = "InitialEventCounter";
	public static final String commonpostfix = "EventCounter";
	public static final String clearfuncpostfix = "Cleaner";
	public static final String incpostfix = "Inc";
	public static final String idtranslatepostfix = "IdTranslate";
	public static final String ifincidxpostfix = "IfIncIdx";
	public static final String ifincpostfix = "IfIncWFunc";
	public static final String whichinvokepostfix = "WhichInvokeForStateIdAndEvtId";
	public static final String incarray = "mIfIncArray";
	public static final String incarraysize = "mIfIncArrayLength";
	public static final String onetofalse = "SetOneIfIncToFalse";
	public static final String alltotrue = "SetAllIfIncToTrue";
	
	SimulinkModel mSimulink = null;
	
	int mPhase = 0;
	
	public EventTimeStatistic() {
	}
	
	public void SetSimulink(SimulinkModel simulink)
	{
		mSimulink = simulink;
	}
	
	public void SetPhase(int phase)
	{
		mPhase = phase;
	}
	
	//testing function
	/*private static void PrintAllLocalEvtMap()
	{
		Set<String> keys = localevtmap.keySet();
		System.err.println("Local Evt Test Begin.");
		for (String key : keys)
		{
			System.err.println(key + ":" + localevtmap.get(key));
		}
		System.err.println("Local Evt Test End.");
	}*/
	
	public static String GetClearFuncNameByState(State state)
	{
		return GetEvtCountArrayName(state, (State)state.getParent())+clearfuncpostfix;
	}
	
	private static String GetClearFuncName(String arrayfullname)
	{
		return arrayfullname+clearfuncpostfix;
	}
	
	private static String GetIfIncArrayIndxFuncName()
	{
		return ifincidxpostfix;
	}
	
	private static String GetIfIncFuncName()
	{
		return ifincpostfix;
	}
	
	public static String GetWhichFuncToIncFuncName()
	{
		return whichinvokepostfix;
	}

	private static String GetIncFuncName(String arrayfullname) {
		return arrayfullname+incpostfix;
	}
	
	private static String GetIdPosTranslateName(String arrayfullname) {
		return arrayfullname+idtranslatepostfix;
	}
	
	private static String GetEvtCountArrayName(State source, State parent)
	{
		String result = "";
		if (source == null)
		{
			if (parent == null)
			{
				System.err.println("Parent has outter transitions that has time concern operations?.");
				System.exit(1);
			}
			else
			{
				result+=parent.GetFullName()+inipostfix;
			}
		}
		else
		{
			result+=source.GetFullName()+commonpostfix;
		}
		return result;
	}
	
	private static String GetIfIncArrayName()
	{
		return incarray;
	}
	
	private static String GetIfIncArraySize() {
		return incarraysize;
	}	

	public static String GetSetOneToFalse() {
		return onetofalse;
	}

	public static String GetSetAllToTrue() {
		return alltotrue;
	}
	
	public void CountAndReplaceEvent(State chart, UppaalDeclaration overallud)
	{
		StatisticCountEvent(chart);
		
		//testing
		//PrintAllLocalEvtMap();
		FillOverAllDeclarationWithDeclAndCleanOperation(overallud);
		FillOverAllIncOperation(overallud);
	}

	private String GetRawFullName(String arrayname)
	{
		/*if (arrayname.charAt(0) != 'm')
		{
			System.err.println("Wrong event count array name.Intern error!;and the name is : "+arrayname);
			System.exit(1);	
		}
		arrayname = arrayname.substring(1);*/
		int pos = arrayname.indexOf(inipostfix);
		if (pos == -1)
		{
			pos = arrayname.indexOf(commonpostfix);
			if (pos == -1)
			{
				System.err.println("Wrong event count array name.Intern error!;and the name is : "+arrayname);
				System.exit(1);
			}
		}
		String statefullname = arrayname.substring(0, pos);
		return statefullname;
	}
	
	public boolean HasCountEvent(State state)
	{
		String arrayname = GetEvtCountArrayName(state, (State)state.getParent());
		return localevtmap.containsKey(arrayname);
	}
	
	private void FillOverAllDeclarationWithDeclAndCleanOperation(
			UppaalDeclaration overallud) {
		Set<String> keys = localevtmap.keySet();
		for (String key : keys)
		{
			int count = localevtmap.get(key);
			String funcname = GetClearFuncName(key);
			if (!overallud.ContainsFunction(funcname))
			{
				overallud.AddFunction(funcname, "\nvoid "+funcname+"()\n{\n    int i="+(count-1)+";\n    while(i&gt;=0){\n        "+key+"[i]=0;\n        i--;\n    }\n}\n");
			}
			if (!overallud.ContainsFunction(key))
			{
				overallud.AddVariable(key, "STA:"+count+":int");
			}
		}
	}
	
	private void FillOverAllIncOperation(UppaalDeclaration overallud)
	{
		Set<String> keys = localevtmap.keySet();
		int size = keys.size();
		String ifincidxname = GetIfIncArrayIndxFuncName();
		String whichinvokename = GetWhichFuncToIncFuncName();
		String ifincname = GetIfIncFuncName();
		String setonetofalse = GetSetOneToFalse();
		String setalltotrue = GetSetAllToTrue();
		if (!overallud.ContainsFunction(ifincidxname))
		{
			overallud.AddVariable(GetIfIncArrayName(), "STA:"+size+":bool");
			overallud.AddVariable(GetIfIncArraySize(), "ST:int:"+size);
			overallud.AddFunction(setonetofalse, "\nvoid "+setonetofalse+"(int stateid)\n{\n"+Gensetinctofalseatstatefunc("    ")+"}\n");
			overallud.AddFunction(setalltotrue, "\nvoid "+setalltotrue+"()\n{\n"+Gensetallinctotruefunc("    ")+"}\n");
			overallud.AddFunction(ifincidxname, "\nint "+ifincidxname+"(int stateid)\n{\n"+Genifincidxfunc("    ")+"}\n");
			overallud.AddFunction(whichinvokename, "\nvoid "+whichinvokename+"(int stateid)\n{\n"+Genwhichinvokefunc("    ")+"}\n");
			overallud.AddFunction(ifincname, "\nbool "+ifincname+"(int stateid)\n{\n"+Genifincfunc("    ")+"}\n");
		}
		for (String key : keys)
		{
			String idposfuncname = GetIdPosTranslateName(key);
			String funcname = GetIncFuncName(key);
			if (!overallud.ContainsFunction(funcname))
			{
				overallud.AddFunction(idposfuncname, "\nint "+idposfuncname+"(int evtid)\n{\n"+Genidposfunc("    ",key)+"}\n");
				overallud.AddFunction(funcname, "\nvoid "+funcname+"()\n{\n"+Genposincfunc("    ",key,idposfuncname)+"}\n");
			}
		}
	}

	private String Genifincfunc(String prefixtab) {
		String ifincidx = GetIfIncArrayIndxFuncName();
		StringBuffer sb = new StringBuffer("");
		sb.append(prefixtab+"int pos = "+ifincidx+"(stateid);\n");
		sb.append(prefixtab+"if (pos == -1){return false;}\n");
		sb.append(prefixtab+"return "+GetIfIncArrayName()+"[pos];\n"); 
		return sb.toString();
	}
	
	private String Gensetinctofalseatstatefunc(String prefixtab) {
		String ifincidx = GetIfIncArrayIndxFuncName();
		StringBuffer sb = new StringBuffer("");
		sb.append(prefixtab+"int pos = "+ifincidx+"(stateid);\n");
		sb.append(prefixtab+"if (pos == -1){return;}\n");
		sb.append(prefixtab+GetIfIncArrayName()+"[pos] = false;\n");//ifincidx 
		return sb.toString();
	}
	
	private String Gensetallinctotruefunc(String prefixtab) {
		StringBuffer sb = new StringBuffer("");
		sb.append(prefixtab+"int i = "+GetIfIncArraySize()+"-1;\n");
		sb.append(prefixtab+"while(i&gt;=0){\n");
		sb.append(prefixtab+prefixtab+GetIfIncArrayName()+"[i]=true;\n");//ifincidx
		sb.append(prefixtab+prefixtab+"i--;\n");//ifincidx
		sb.append(prefixtab+"}\n");
		return sb.toString();
	}

	private String Genwhichinvokefunc(String prefixtab) {
		StringBuffer sb = new StringBuffer("");
		Set<String> keyset = localevtmap.keySet();
		sb.append(prefixtab+"if (stateid == -1){return;}\n");
		for (String key : keyset)
		{
			String arrayfullname = key;
			String statefullname = GetRawFullName(arrayfullname);
			State state = RunEnvironment.getmStateManager().getStateByFullStateName(statefullname);
			if (arrayfullname.endsWith(inipostfix))
			{
				state = state.GetmMockedInitialState();
			}
			int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
			String evtinc = state.GetFullName() + EventTimeStatistic.commonpostfix
					+ EventTimeStatistic.incpostfix + "()";
			sb.append(prefixtab+"if (stateid == "+nowstateid+" &amp;&amp; "+GetIfIncFuncName()+"("+nowstateid+")"+"){"+evtinc+";}\n");
		}
		return sb.toString();
	}

	private String Genifincidxfunc(String prefixtab) {
		StringBuffer sb = new StringBuffer("");
		Set<String> keyset = localevtmap.keySet();
		int idx = 0;
		for (String key : keyset)
		{
			String arrayfullname = key;
			String statefullname = GetRawFullName(arrayfullname);
			State state = RunEnvironment.getmStateManager().getStateByFullStateName(statefullname);
			if (arrayfullname.endsWith(inipostfix))
			{
				state = state.GetmMockedInitialState();
			}
			int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
			sb.append(prefixtab+"if (stateid == "+nowstateid+"){return "+idx+";}\n");
			idx++;
		}
		sb.append(prefixtab+"return -1;\n");
		return sb.toString();
	}

	private String Genposincfunc(String prefixtab, String arrayfullname, String idposfuncname)
	{
		StringBuffer sb = new StringBuffer("");
		sb.append(prefixtab+"int evtid = -1;\n");
		sb.append(prefixtab+"int evtpos = -1;\n");
		sb.append(prefixtab+"if ((mExecutionStack[mExecutionStackTop].mEvent &gt;= vValidEventBase)){evtid = mExecutionStack[mExecutionStackTop].mEvent;}\n");
		sb.append(prefixtab+"if ((evtid == -1) &amp;&amp; (mExecutionStack[mExecutionStackTop].mInfo &gt;= vValidEventBase)){evtid = mExecutionStack[mExecutionStackTop].mInfo;}\n");
		sb.append(prefixtab+"if (evtid == -1){return;}\n");
		sb.append(prefixtab+"evtpos = "+idposfuncname+"(evtid);\n");
		sb.append(prefixtab+"if (evtpos == -1){return;}\n");
		sb.append(prefixtab+arrayfullname+"[evtpos]++;\n");
		return sb.toString();
	}
	
	private String Genidposfunc(String prefixtab, String arrayfullname)
	{
		StringBuffer sb = new StringBuffer("");
		String statefullname = GetRawFullName(arrayfullname);
		State state = RunEnvironment.getmStateManager().getStateByFullStateName(statefullname);
		if (arrayfullname.endsWith(inipostfix))
		{
			state = state.GetmMockedInitialState();
		}
		Map<String, Integer> ces = state.getmCountEvent();
		Set<String> keys = ces.keySet();
		for (String key : keys)
		{
			int val = ces.get(key);
			int id = RunEnvironment.getmEventRegistry().GetEvtId(key);
			sb.append(prefixtab+"if (evtid == "+id+"){return "+val+";}\n");
		}
		sb.append(prefixtab+"return -1;\n");
		return sb.toString();
	}

	private void StatisticCountEvent(State chart)
	{
		mVisitRecord.clear();
		mLevelVisitRecord.clear();
		CountState(chart);
		IterateAndFillTheMapInField(chart);
	}
	
	private void IterateAndFillTheMapInField(State state) {
		if (state.HasCountEvent())
		{
			localevtmap.put(state.GetFullName()+commonpostfix, state.GetEvtCount());
		}
		if (!state.hasChildStates())
		{
			return;
		}
		if (state.isfChildDecomposition() == State.ChildsSerial)
		{
			MockedState ms = state.GetmMockedInitialState();
			if (ms != null && ms.HasCountEvent())
			{
				localevtmap.put(state.GetFullName()+inipostfix, ms.GetEvtCount());
			}
		}
		ArrayList<State> chstates = state.getOrderedStates();
		int i=0;
		int len = chstates.size();
		for (i=0;i<len;i++)
		{
			State chstate = chstates.get(i);
			IterateAndFillTheMapInField(chstate);
		}
	}
	
	//testing code
	/*private static void PrintStateFullNameInList(ArrayList<State> states)
	{
		int i=0;
		int len=states.size();
		System.err.println("StateFullNameBegin");
		for (i=0;i<len;i++)
		{
			State stat = states.get(i);
			System.err.println(stat.GetFullName());
		}
		System.err.println("StateFullNameEnd");
	}*/
	
	private void ReplaceArrayListWithAnotherArrayList(ArrayList<State> replacedlist, ArrayList<State> withlist)
	{
		replacedlist.clear();
		int len = withlist.size();
		for (int i=0;i<len;i++)
		{
			replacedlist.add(withlist.get(i));
		}
	}
	
	private void CountState(State parentstate)
	{
		if (mVisitRecord.get(parentstate.GetFullName())!=null && mVisitRecord.get(parentstate.GetFullName()))
		{
			return;
		}
		
		mVisitRecord.put(parentstate.GetFullName(), true);
		mLevelVisitRecord.clear();
		ArrayList<State> endedstate = new ArrayList<State>();
		do
		{
			//testing
			//PrintStateFullNameInList(endedstate);
			if (endedstate.size() == 0)
			{
				if (parentstate.hasChildStates())
				{
					if (parentstate.isfChildDecomposition() == State.ChildsSerial)
					{
						IterateUntilFirstTouchedState(parentstate.GetmMockedInitialState(), parentstate.GetmMockedInitialState(), endedstate);						
					}
					else
					{
						//do nothing.
					}
				}
			}
			else
			{
				ArrayList<State> tempendedstate = new ArrayList<State>();
				int i=0;
				int len=endedstate.size();
				for (i=0;i<len;i++)
				{
					State tsourcestate = endedstate.get(i);
					if (!mLevelVisitRecord.containsKey(tsourcestate.GetFullName()))
					{
						mLevelVisitRecord.put(tsourcestate.GetFullName(), true);
						IterateUntilFirstTouchedState(tsourcestate, tsourcestate, tempendedstate);
					}
				}
				ReplaceArrayListWithAnotherArrayList(endedstate,tempendedstate);
			}
		}
		while(!endedstate.isEmpty());		
		
		//CountState(parentstate, endedstate);
		/*if (parentstate.isfChildDecomposition() == State.ChildsSerial)
		{
			int i=0;
			int len=onelevelstates.size();
			for (i=0;i<len;i++)
			{
				State state = endedstate.get(i);
				CountState(state, null);
			}
		}
		else
		{
			if (parentstate.hasChildStates())
			{*/
		ArrayList<State> chstates = parentstate.getOrderedStates();
		int i=0;
		int len=chstates.size();
		for (i=0;i<len;i++)
		{
			State state = chstates.get(i);
			CountState(state);
		}
		/*	}
		}*/
	}
	
	private ArrayList<String> GetAllCountEvent(ArrayList<ArrayList<String>> countevts)
	{
		ArrayList<String> setlist = new ArrayList<String>();
		int i=0;
		int len = countevts.size();
		for (i=0;i<len;i++)
		{
			setlist.add(countevts.get(i).get(2));
		}
		return setlist;
	}
	
	private void IterateUntilFirstTouchedState(State source, StateflowConnector nowstate, ArrayList<State> endedstate)
	{
		ArrayList<Transition> trans = nowstate.getOuttransition();
		if (trans == null || trans.size() == 0)
		{
			return;
		}
		int i=0;
		int len=trans.size();
		for (i=0;i<len;i++)
		{
			Transition tran = trans.get(i);

			//testing
			/*State target = (State) tran.getTargetstate();
			System.err.println(tlabel);
			if (source.GetFullName().endsWith("ere") && target.GetFullName().endsWith("xcxc"))
			{
				//System.err.println("mockstate's parent : "+source.getParent().GetFullName()+";outtrans's len is : "+nowstate.getOuttransition().size());
				System.err.println(tlabel);
			}*/
			//testing
			//System.err.println("content:"+tran.getLabelString());
			String[] fourparts = tran.getFourparts();
			String cond = fourparts[1];
			
			if (cond != null && !cond.equals(""))
			{
				ArrayList<String> timecns = RegularExpression.GetAllTimeConcern(cond);
				ArrayList<ArrayList<String>> timeparts = RegularExpression.ExtractThreePartsInEveryString(timecns);
				source.AddCountEventMap(GetAllCountEvent(timeparts));
				Map<String, Integer> countevt = source.getmCountEvent();
				int j=0;
				int jlen=timecns.size();
				for (j=0;j<jlen;j++)
				{
					String onecns = timecns.get(j);
					String modifiedcnt = ModifyStateflowTimeCountJudgeToUppaalJudge(timeparts.get(j), countevt, GetEvtCountArrayName((State)tran.getSourcestate(), tran.getContainer()));
					String reponecns = onecns.replace("(", "\\(");
					reponecns = reponecns.replace(")", "\\)");
					fourparts[1] = fourparts[1].replaceAll(reponecns, modifiedcnt);
					
					//testing
					/*System.err.println(onecns);
					System.err.println(modifiedcnt);
					System.err.println(fourparts[1]);*/
					
				}
				tran.setFourparts(fourparts);
			}
			StateflowConnector sfc = tran.getTargetstate();
			if (sfc instanceof Junction)
			{
				IterateUntilFirstTouchedState(source, sfc, endedstate);
			}
			else
			{
				endedstate.add((State)sfc);
			}
		}
	}

	private String ModifyStateflowTimeCountJudgeToUppaalJudge(
			ArrayList<String> timeparts, Map<String, Integer> countevt, String evtcountname) {
		String operation = "";
		String extraop = "";
		if (timeparts.get(2).equals("sec"))
		{
			extraop = "/"+mSimulink.getmTimeGap();
		}
		switch (timeparts.get(0)) {
		case "after":
			operation = "&gt;=("+timeparts.get(1)+extraop+")";
			break;
		case "before":
			operation = "&lt;=("+timeparts.get(1)+extraop+")";
			break;
		case "every":
			operation = "%("+timeparts.get(1)+extraop+")==0";
			break;
		case "at":
			operation = "==("+timeparts.get(1)+extraop+")";
			break;
		default:
			System.err.println("Unsupportted time concern operation,the only four operation we supportted is after/before/every/at.");
			System.exit(1);
			break;
		}
		String result = evtcountname+"["+countevt.get(timeparts.get(2))+"]"+operation;
		return result;
	}
	
}