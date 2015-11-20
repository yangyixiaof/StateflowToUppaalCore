package TransferLogic;

import java.util.ArrayList;

import CommonLibrary.SimpleParserHelper;
import PositionLibrary.StatesLinesPositionLibrary;
import StateflowStructure.Chart;
import StateflowStructure.Junction;
import StateflowStructure.SimulinkModel;
import StateflowStructure.State;
import Uppaal.UppaalAutomaton;
import Uppaal.UppaalCordinateStaticData;
import Uppaal.UppaalDeclaration;
import Uppaal.UppaalNail;
import Uppaal.UppaalState;
import Uppaal.UppaalTransition;

public class ControllerAndActionTransferLogic {
	
	private static SimulinkModel simulinkModel = null;
	
	public static final int entry = 7;
	public static final int during = 6;
	public static final int exit = 5;
	
	public static void GenerateDeleteInactiveEventLogic(UppaalAutomaton ua) {
		UppaalState ini = TransferCommonLibrary.CreateRawUppaalState("all_initial", "all_initial", true, -1, ua,
				UppaalCordinateStaticData.controllercenterx,
				UppaalCordinateStaticData.controllercentery);
		// delete an invalid event.
		ArrayList<UppaalNail> nails1 = new ArrayList<UppaalNail>();
		nails1.add(new UppaalNail(UppaalCordinateStaticData.controllercenterx
				- UppaalCordinateStaticData.halfxlength,
				UppaalCordinateStaticData.controllercentery
						- UppaalCordinateStaticData.halfylength));
		nails1.add(new UppaalNail(UppaalCordinateStaticData.controllercenterx
				+ UppaalCordinateStaticData.halfxlength,
				UppaalCordinateStaticData.controllercentery
						- UppaalCordinateStaticData.halfylength));
		new UppaalTransition(0 + "_evtclear", ini, ini, "",
				"JTopStackInValid()", "", "PopOneEvent()", ua, nails1);
	}

	public static void GenerateOneChartEventDrivenAutomaton(UppaalAutomaton ua,
			Chart chart) {
		UppaalState ini = TransferCommonLibrary.CreateRawUppaalState("all_initial", "all_initial", true, -1, ua,
				UppaalCordinateStaticData.controllercenterx,
				UppaalCordinateStaticData.controllercentery);
		// generate a valid event.
		UppaalCordinateStaticData.ResetControllerSelfCycle();
		UppaalDeclaration ud = ua.getUppaalDeclaration();
		ud.AddVariable("mFirstActivated", "ST:bool:false");
		String guard = "mFirstActivated&&IsStackEmpty()";
		String incguard = "mFirstActivated&&IsStackEmpty()";
		new UppaalTransition(0 + "_evtgenerator", ini, ini, "", "!mFirstActivated", "",
				"mFirstActivated=true,Active(1,1)", ua,
				UppaalCordinateStaticData.NextAllControllerLowerCoordinate());
		if (getSimulinkModel().getmTimeGap() > 0) {
			ud.AddVariable("mDrivenTime", "ST:int:0");
			ud.AddVariable("mTotalTime", "ST:int:0");
			guard += " && mDrivenTime >= "
					+ getSimulinkModel().getmTimeGap()
					+ " && "
					+ "mTotalTime <= "
					+ (getSimulinkModel().getmStopTime() - getSimulinkModel().getmStartTime());
			incguard += " && mDrivenTime < " + getSimulinkModel().getmTimeGap();
			new UppaalTransition(1 + "_evtgenerator_timeinc", ini, ini, "", incguard, "",
					"mTotalTime=mTotalTime+"+getSimulinkModel().getmTimeGap()
							+",mDrivenTime=mDrivenTime+"+getSimulinkModel().getmTimeGap(), ua,
							UppaalCordinateStaticData.NextAllControllerLowerCoordinate());
		}
		String generateevent = "GenerateDrivenEvent("
				+ RunEnvironment.getmStateManager().GetStateId(chart) + ")";
		String setalltotrue = EventTimeStatistic.GetSetAllToTrue() + "()";
		new UppaalTransition(0 + "_evtgenerator", ini, ini, "", guard, "",
						"mDrivenTime=mDrivenTime-"+getSimulinkModel().getmTimeGap()+"," + setalltotrue+","+generateevent, ua,
						UppaalCordinateStaticData.NextAllControllerLowerCoordinate());
	}
	
	public static UppaalAutomaton GenerateSimplifiedJunctionController(
			Junction junction) {
		UppaalAutomaton ua = new UppaalAutomaton(junction);
		int junctionid = RunEnvironment.getmStateManager().GetStateId(junction);
		int parentid = RunEnvironment.getmStateManager().GetStateId(junction.getParent());
		// deactive begin
		// deactive stack on
		// send deactive to its child.
		UppaalState ini = TransferCommonLibrary.CreateRawUppaalState(junction.getID() + "_initial", "junc_"+junction.GetFullName()+"_ctrl_initial",
				true, -1, ua, UppaalCordinateStaticData.controllercenterx,
				UppaalCordinateStaticData.controllercentery);
		new UppaalTransition("simpledeactivehandle", ini, ini, "", "JVSDaO(" + junctionid + ",aControllerAutomaton)", "", "HandleSimpleDeactivation("+parentid +")", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		new UppaalTransition("simpleactivehandleonstack", ini, ini, "", "JVSAO(" + junctionid + ",aControllerAutomaton)", "", "HandleSimpleActivationOnStack("+parentid +","+junctionid+")", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		new UppaalTransition("simpleactivehandleleavestack", ini, ini, "", "JVSAL(" + junctionid + ",aControllerAutomaton)", "", "HandleSimpleActivationLeaveStack("+parentid +","+junctionid+")", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		new UppaalTransition("delete_dispatch_events", ini, ini, "", "JVSE2MForAll("+RunEnvironment.getmStateManager().GetStateId(junction)+",aControllerAutomaton) &amp;&amp; NotDeactiveAndActiveRelatedEvents()", "", "PopOneEvent()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
				return ua;
	}
	
	public static UppaalAutomaton GenerateSimplifiedStateControllerForNothingHaveState(State state)
	{
		UppaalAutomaton ua = new UppaalAutomaton(state,
				UppaalAutomaton.CONTROLLERUA, false, false, false);
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		int parentid = RunEnvironment.getmStateManager().GetStateId(state.getParent());
		// deactive begin
		// deactive stack on
		// send deactive to its child.
		UppaalState ini = TransferCommonLibrary.CreateRawUppaalState(state.getID() + "_initial", state.getfLabelName()+"_ctrl_initial",
				true, -1, ua, UppaalCordinateStaticData.controllercenterx,
				UppaalCordinateStaticData.controllercentery);
		/*GenerateDeActivationIStayOnStackSendToChildOrNotAndChangeToConsumed(
				state, ini, ua);
		// some aspect of deactive.
		GenerateDeActivationConsumedLogic(state, ini, ua);*/
		// deactive end
		new UppaalTransition("simpledeactivehandle", ini, ini, "", "JVSDaO(" + nowstateid + ",aControllerAutomaton)", "", "HandleSimpleDeactivation("+parentid +")", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		new UppaalTransition("simpleactivehandleonstack", ini, ini, "", "JVSAO(" + nowstateid + ",aControllerAutomaton)", "", "HandleSimpleActivationOnStack("+parentid +","+nowstateid+")", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		new UppaalTransition("simpleactivehandleleavestack", ini, ini, "", "JVSAL(" + nowstateid + ",aControllerAutomaton)", "", "HandleSimpleActivationLeaveStack("+parentid +","+nowstateid+")", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		new UppaalTransition("delete_dispatch_events", ini, ini, "", "JVSE2MForAll("+RunEnvironment.getmStateManager().GetStateId(state)+",aControllerAutomaton) &amp;&amp; NotDeactiveAndActiveRelatedEvents()", "", "PopOneEvent()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini,ini,0));
		return ua;
	}

	public static UppaalAutomaton[] GenerateCompStateController(State state) {
		boolean isChart = false;
		UppaalAutomaton[] uas = null;
		if (state instanceof Chart) {
			isChart = true;
		}
		boolean haschilds = false;
		if (state.hasChildStates()) {
			haschilds = true;
		}
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		UppaalAutomaton ua = new UppaalAutomaton(state,
				UppaalAutomaton.CONTROLLERUA, isChart, haschilds, state.HasThreeActions());
		if (state.HasThreeActions()) {
			uas = new UppaalAutomaton[2];
			UppaalAutomaton ctrl_act_ua = new UppaalAutomaton(state,
					UppaalAutomaton.CONTROLLERACTIONUA, isChart, haschilds, state.HasThreeActions());
			uas[1] = ctrl_act_ua;
			UppaalState ctrl_ini = TransferCommonLibrary.CreateRawUppaalState(state.getID()
					+ "_ctrl_initial", state.getfLabelName()+"_ctrl_act_initial", true, -1, ctrl_act_ua,
					UppaalCordinateStaticData.controllercenterx,
					UppaalCordinateStaticData.controllercentery);
			new UppaalTransition(state.getID()+"self_delete", ctrl_ini, ctrl_ini, "", "JVSEF2M("+nowstateid+",eTransToStateDirectly,aControllerActionAutomaton)", "", "PopOneEvent()", ctrl_act_ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ctrl_ini,ctrl_ini,UppaalTransition.DuringTransition));
			// execute the active event.
			// entry action.
			GenerateEntryActionLogic(state, ctrl_ini, ctrl_act_ua);
			// execute deactive event.
			// exit action.
			GenerateExitActionLogic(state, ctrl_ini, ctrl_act_ua);
			// dispatch begin
			// during action
			GenerateDuringActionLogic(state, ctrl_ini, ctrl_act_ua);
		} else {
			uas = new UppaalAutomaton[1];
		}
		//the code above generate the controller action automaton.
		uas[0] = ua;
		UppaalState ini = TransferCommonLibrary.CreateRawUppaalState(state.getID() + "_initial", state.getfLabelName()+"_ctrl_initial",
				true, -1, ua, UppaalCordinateStaticData.controllercenterx,
				UppaalCordinateStaticData.controllercentery);
		// only do entry/during action.
		// There is no stack in entry/during/exit.Luckily.
		// active begin
		// active stack on.
		// send active to its parent.
		GenerateActivationIStayOnStackSendToParentOrNotAndChangeToConsumed(
				state, ini, ua);
		// some aspect of active.
		GenerateActivationConsumedLogic(state, ini, ua);
		// active end

		// deactive begin
		// deactive stack on
		// send deactive to its child.
		GenerateDeActivationIStayOnStackSendToChildOrNotAndChangeToConsumed(
				state, ini, ua);
		// some aspect of deactive.
		GenerateDeActivationConsumedLogic(state, ini, ua);
		// deactive end

		// some aspect of dispatch in active states.
		GenerateEventDispatch(state, ini, ua);
		// dispatch end
		return uas;
	}

	public static void GenerateDuringActionLogic(State state, UppaalState ctrl_ini,
			UppaalAutomaton ctrl_act_ua) {
		// during
		if (state.HasDuring()) {
			int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
			String during = state.getDuring();
			// this judgement need to be refined.
			String commonJudge = "JVSEF2M(" + nowstateid
					+ ",eDuring,aControllerActionAutomaton)";
			GenerateControllerThreeStateOnAction(state, ctrl_ini, ctrl_ini,
					ctrl_ini, during, commonJudge,
					TransferCommonLibrary.GenerateDuringTransitionPrefix(state), "PopOneEvent()",
					ctrl_act_ua, ControllerAndActionTransferLogic.during);
		}
	}

	public static void GenerateExitActionLogic(State state, UppaalState ctrl_ini,
			UppaalAutomaton ctrl_act_ua) {
		// exit
		if (state.HasExit()) {
			int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
			String exitact = state.getExit();
			String commonJudge = "JVSEF2M("+nowstateid+",eDeActivation,aControllerActionAutomaton)";
			//"JVSDaL(" + nowstateid + ",aControllerActionAutomaton)";
			String clearcountevtarray = "";
			if (state.HasCountEvent()) {
				clearcountevtarray = EventTimeStatistic
						.GetClearFuncNameByState(state) + "(),";
			}
			GenerateControllerThreeStateOnAction(state, ctrl_ini, ctrl_ini,
					ctrl_ini, exitact, commonJudge,
					TransferCommonLibrary.GenerateExitTransitionPrefix(state), clearcountevtarray
							+ "PopOneEvent()", ctrl_act_ua, ControllerAndActionTransferLogic.exit);
		}
	}

	public static void GenerateEntryActionLogic(State state, UppaalState ctrl_ini,
			UppaalAutomaton ctrl_act_ua) {
		// Now , the prepare work is over , let us begin execute entry action.
		// entry
		if (state.HasEntry()) {
			int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
			String entryact = state.getEntry();
			String commonJudge = "JVSEF2M("+nowstateid+",eActivation,aControllerActionAutomaton)";
			//"JVSAL(" + nowstateid + ",aControllerActionAutomaton)";
			String clearcountevtarray = "";
			if (state.HasCountEvent()) {
				clearcountevtarray = EventTimeStatistic
						.GetClearFuncNameByState(state) + "(),";
			}
			GenerateControllerThreeStateOnAction(state, ctrl_ini, ctrl_ini,
					ctrl_ini, entryact, commonJudge,
					TransferCommonLibrary.GenerateEntryTransitionPrefix(state), clearcountevtarray
							+ "PopOneEvent()", ctrl_act_ua, ControllerAndActionTransferLogic.entry);
		}
	}

	public static void GenerateEventDispatch(State parentstate, UppaalState ini,
			UppaalAutomaton ua) {
		//most difficult now.
		//add two self cycle.
		//mentioned on manuscript 3.
		
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(parentstate);
		String evtinc = "";
		if (parentstate.isfChildDecomposition() == State.ChildsSerial)
		{
			evtinc = EventTimeStatistic.GetWhichFuncToIncFuncName() + "(GHS())";
		}
		
		//state is a parent state.
		//the above code is right.
		
		
		UppaalCordinateStaticData.ResetControllerSelfCycle();
		
		boolean parentparentparallel = false;
		if (parentstate.getParent()!=null && ((State)parentstate.getParent()).isfChildDecomposition()==State.ChildsParallel)
		{
			parentparentparallel = true;
		}
		
		new UppaalTransition(parentstate.getID() + "_during_trans0", ini, ini, "",
				"JVSE2M("+nowstateid+",aControllerAutomaton) && JVSETrigger()",
				"",
				evtinc+(evtinc.equals("")?"":",")+"FirstRoundEventDispatchHandle("+parentstate.HasDuring()+", "+nowstateid+","+parentparentparallel+","+parentstate.isfChildDecomposition()+",GHS())", ua, UppaalCordinateStaticData.NextControllerDuringCoordinate()).setTypeoftransition(UppaalTransition.DuringTransition);
		
		new UppaalTransition(parentstate.getID() + "_during_trans0", ini, ini, "",
				"JVSE2M("+nowstateid+",aControllerAutomaton) && !JVSETrigger()",
				"",
				"FirstRoundEventDispatchHandle("+parentstate.HasDuring()+", "+nowstateid+","+parentparentparallel+","+parentstate.isfChildDecomposition()+",GHS())", ua, UppaalCordinateStaticData.NextControllerDuringCoordinate()).setTypeoftransition(UppaalTransition.DuringTransition);
		//the code above handles during dispatch in the situation it has child states.
		
		String dispatchselflogicjudge = "JVSEF2M("+nowstateid+",eDispathToSelfLogic,aControllerAutomaton)";
		
		if (parentstate.isfChildDecomposition() == State.ChildsSerial && parentstate.hasChildStates())
		{
			new UppaalTransition(parentstate.getID() + "_during_trans2", ini, ini, "",
					dispatchselflogicjudge, "",  "MNSEDispatchToSelfLogic(JNCRA()," + nowstateid + ","
							+ (parentstate.isfChildDecomposition() == State.ChildsSerial)
							+ "," + parentstate.HasDuring() + ",IsLoseControl())", ua, UppaalCordinateStaticData.NextControllerDuringCoordinate()).setTypeoftransition(UppaalTransition.DuringTransition);
		}
		else
		{
			new UppaalTransition(parentstate.getID() + "_during_trans2", ini, ini, "",
					dispatchselflogicjudge, "", "MNSESelfLogicOver(JNCRA())", ua, UppaalCordinateStaticData.NextControllerDuringCoordinate()).setTypeoftransition(UppaalTransition.DuringTransition);
		}
		//else just dispatch event to child and parallel which has been done in the next section.
		
		
		
		State nextstate = null;
		//get parent state's next order state.
		nextstate = parentstate.getParent() == null ? null : ((State) parentstate
				.getParent()).getNextSlowerPriorityChildState(parentstate);
		String childstateid = "-1";
		if (parentstate.isfChildDecomposition() == State.ChildsParallel) {
			// parallel
			//get first order child in parallel children.
			ArrayList<State> orderedchildstates = parentstate.getOrderedStates();
			childstateid = RunEnvironment.getmStateManager().GetStateId(orderedchildstates.get(0))
					+ "";
		} else {
			// sequence
			//just get the run time record.
			childstateid = "GHS()";
		}
		
		//this judgement is the communication with the common automaton,if the event is not consumed.
		//common automaton will set info to iSelfLogicOver.
		new UppaalTransition(parentstate.getID() + "_during_trans4", ini, ini, "",
				"JNSESelfLogicOver(" + nowstateid + ")", "",
				"MNSEDispatchToChild(" + childstateid+ "," + parentstate.hasChildStates() + ")",
				ua, UppaalCordinateStaticData.NextControllerDuringCoordinate()).setTypeoftransition(UppaalTransition.DuringTransition);
		
		String toparalleljudge = "";
		toparalleljudge = "JVSEF2M("+nowstateid+",eChildOver,aControllerAutomaton)";
		//if the eChildOver event is not dispatched, it no matter affect the following code.
		new UppaalTransition(parentstate.getID() + "_during_trans5", ini, ini, "",
				toparalleljudge, "",
				"MNSEDispatchToParallel(" + (nextstate == null ? -1
						: RunEnvironment.getmStateManager().GetStateId(nextstate)) + ")",
						ua, UppaalCordinateStaticData.NextControllerDuringCoordinate()).setTypeoftransition(UppaalTransition.DuringTransition);
	}

	// deactive send to deactive stop id directly.
	public static void GenerateDeActivationIStayOnStackSendToChildOrNotAndChangeToConsumed(
			State state, UppaalState ini, UppaalAutomaton ua) {
		String statectrl = TransferCommonLibrary.GetStateControllerName(state);
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		UppaalState imd = TransferCommonLibrary.CreateRawUppaalState(statectrl + "_gison2cda_imd", null,
				false, -1, ua, UppaalCordinateStaticData.deactivebranchx,
				UppaalCordinateStaticData.deactivebranchy);
		if (!state.hasChildStates()) {
			new UppaalTransition(state.getID() + "_gison2cda_trans0", ini, imd,
					"", "JVSDaO(" + nowstateid + ",aControllerAutomaton)", "",
					"MSO2L()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
			new UppaalTransition(state.getID() + "_gison2cda_trans1", imd, ini,
					"", "", "", "", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
		} else {
			new UppaalTransition(state.getID() + "_gison2cda_trans0", ini, imd,
					"", "JVSDaO(" + nowstateid + ",aControllerAutomaton)", "",
					"MSO2L()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
			new UppaalTransition(state.getID() + "_gison2cda_trans1", imd, ini,
					"", "!(JNCRA())", "", "", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
			
			//event it is inactive, the event shouldn't pop up.
			//waiting the true deactive logic to run.
			
			String childdeactid = "GHS()";
			if (state.isfChildDecomposition() == State.ChildsParallel)
			{
				childdeactid = ""+RunEnvironment.getmStateManager().GetStateId(state.getLastPriorityChildState());
			}
			new UppaalTransition(
					state.getID() + "_gison2cda_trans2",
					imd,
					ini,
					"",
					"JNCRA()",
					"",
					"MSEAutoDest("+childdeactid+"),MSEAutoInfo(-GetStackTopInfo())",
					ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
		}
	}

	public static void GenerateDeActivationConsumedLogic(State state,
			UppaalState ini, UppaalAutomaton ua) {
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		int parentid = TransferCommonLibrary.GetParentId(state);
		UppaalCordinateStaticData.ResetControllerSelfCycle();
		
		if (state instanceof Chart)
		{
			new UppaalTransition(state.getID() + "_ab_trans1", ini, ini, "",
					"JVSEF2M("+nowstateid+",eDeActivation,aControllerAutomaton)","",
							 "PopOneEvent()", ua,
					UppaalCordinateStaticData.NextControllerDeActiveCoordinate()).setTypeoftransition(UppaalTransition.DeactiveTransition);
		}
		else
		{
			State previoustate = ((State)state.getParent()).getNextHigherPriorityChildState(state);
			boolean hasprevious = previoustate == null ? false : true;
			boolean parentparallel = ((State)state.getParent()).isfChildDecomposition() == State.ChildsParallel;
			boolean childparallel = state.isfChildDecomposition() == State.ChildsParallel;
			int previousid = previoustate == null ? -1 : RunEnvironment.getmStateManager().GetStateId(previoustate);
			new UppaalTransition(state.getID() + "_ab_trans1", ini, ini, "",
					"JVSDaL("+nowstateid+",aControllerAutomaton) && JNCRDA()","", 
							 "HandleInactiveInDeactivation("+parentparallel+","+hasprevious+","+previousid+","+parentid+")", ua,
					UppaalCordinateStaticData.NextControllerDeActiveCoordinate()).setTypeoftransition(UppaalTransition.DeactiveTransition);
			
			new UppaalTransition(state.getID() + "_ab_trans2", ini, ini, "",
					"JVSDaL("+nowstateid+",aControllerAutomaton) && JNCRA()","",
							 "HandleActiveInDeactivation("+state.HasExit()+","+state.hasChildStates()+","+childparallel+"),MNRDA()", ua,
					UppaalCordinateStaticData.NextControllerDeActiveCoordinate()).setTypeoftransition(UppaalTransition.DeactiveTransition);
			
			new UppaalTransition(state.getID() + "_ab_trans3", ini, ini, "",
					"JVSEF2M("+nowstateid+",eExitActionOver,aControllerAutomaton)","",
							 "HandleeExitActionOver("+parentparallel+","+hasprevious+","+previousid+","+parentid+")", ua,
					UppaalCordinateStaticData.NextControllerDeActiveCoordinate()).setTypeoftransition(UppaalTransition.DeactiveTransition);
		}
	}

	public static void GenerateActivationConsumedLogic(State state, UppaalState ini,
			UppaalAutomaton ua) {
		// this function is all together.Due to the sequence logic is better
		// than repeatedly cycled logic.
		// judge whether state is active or during.
		int parentid = TransferCommonLibrary.GetParentId(state);
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		State parent = state.getParent() == null ? null : (State)state.getParent();
		String parentfullname = parent == null ? "" : parent.GetFullName();
		boolean parentparllel = parent!=null && parent.isfChildDecomposition() == State.ChildsParallel ? true : false;
		// UppaalState avtbegin = CreateRawUppaalState(state.getID()+"_cab",
		// false, -1, ua);
		// split to three child transition.
		// if send to this controller, let the common automaton tranfer to the
		// specified active state.
		// first transfer to specified state in common automaton.
		
		UppaalCordinateStaticData.ResetControllerSelfCycle();
		boolean parallel = state.isfChildDecomposition() == State.ChildsParallel;
		
		int firstchildid = (state.hasChildStates() && parallel) ? RunEnvironment.getmStateManager().GetStateId(state.getFirstPriorityChildState()) : -1;
		new UppaalTransition(state.getID() + "_ab_trans1", ini, ini, "",
				"JVSEF2M("+nowstateid+",eDefaultActivation,aControllerAutomaton)", "", 
						 "HandleeDefaultActivation("+(parallel)+","+firstchildid+")", ua,
				UppaalCordinateStaticData.NextControllerActiveCoordinate()).setTypeoftransition(UppaalTransition.ActiveTransition);
		
		boolean resetcommontodest = false;
		if (state.getParent() != null
				&& ((State) state.getParent()).isfChildDecomposition() == State.ChildsSerial) {
			resetcommontodest = true;
		}
		
		boolean haschild = state.hasChildStates();
		boolean hasentry = state.HasEntry();
		new UppaalTransition(state.getID() + "_ab_trans2", ini, ini, "",
				"JVSAL("+nowstateid+",aControllerAutomaton)","", 
						 "HandleeActivation("+parentid+",JNCRA(),"+parallel+","+haschild+","+resetcommontodest+","+hasentry+","+firstchildid+"),"+((parent != null && !parentparllel) ? "(!JNCRA())?m"+parentfullname+"HistoryState="+nowstateid+":mEmptyOp=0," : "")+"MNRA()", ua,
				UppaalCordinateStaticData.NextControllerActiveCoordinate()).setTypeoftransition(UppaalTransition.ActiveTransition);
		
		new UppaalTransition(state.getID() + "_ab_trans3", ini, ini, "",
				"JVSEF2M("+nowstateid+",eActiveChild,aControllerAutomaton)", "", 
						 "HandleeActiveChild("+parentparllel+","+haschild+","+parallel+","+firstchildid+")", ua,
				UppaalCordinateStaticData.NextControllerActiveCoordinate()).setTypeoftransition(UppaalTransition.ActiveTransition);
		
		new UppaalTransition(state.getID() + "_ab_trans4", ini, ini, "",
				"JVSEF2M("+nowstateid+",eActiveParallel,aControllerAutomaton)", "", 
						 "HandleeActiveParallel("+(state.getParent() == null ? -1 : RunEnvironment.getmStateManager().GetStateId(((State)state.getParent()).getNextSlowerPriorityChildState(state)))+")", ua,
				UppaalCordinateStaticData.NextControllerActiveCoordinate()).setTypeoftransition(UppaalTransition.ActiveTransition);
	}
	
	public static void GenerateActivationIStayOnStackSendToParentOrNotAndChangeToConsumed(
			State state, UppaalState ini, UppaalAutomaton ua) {
		//this function is not like deactive, this funtion only dispatch to highest no anything else.
		String statectrl = TransferCommonLibrary.GetStateControllerName(state);
		String sendid = "-1";
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		UppaalState imd = TransferCommonLibrary.CreateRawUppaalState(statectrl + "_gison2c_act_imd", null,
				false, -1, ua, UppaalCordinateStaticData.activebranchx,
				UppaalCordinateStaticData.activebranchy);
		if (state.getParent() != null) {
			sendid = TransferCommonLibrary.GetParentId(state) + "";
		}
		new UppaalTransition(state.getID() + "_gison2c_trans0", ini, imd, "",
				"JVSAO(" + nowstateid + ",aControllerAutomaton)", "",
				"MSO2L()", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
		new UppaalTransition(state.getID() + "_gison2c_trans1", imd, ini, "",
				"JVSAH(aControllerAutomaton)", "", "ActivationStopDispatch(JNCRA())", ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
		new UppaalTransition(state.getID() + "_gison2c_trans2", imd, ini, "",
				"!JVSAH(aControllerAutomaton)", "", "PushOneEvent(eActivation,"
						+ sendid + "," + nowstateid
						+ ",-GetStackTopInfo(),true,aControllerAutomaton,-1,-1)", ua,
						//info just inherit from previous activation.
						StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(ini, imd, 0));
	}
	
	public static void GenerateControllerThreeStateOnAction(State state,
			UppaalState ini, UppaalState startus, UppaalState stopus,
			String caction, String commonJudge, String transIDPfx,
			String lastOperation, UppaalAutomaton ua, int direction) {
		if (caction == null || caction.equals("")) {
			return;
		}
		String[] cas = caction.split(";");
		int len = cas.length;
		int zoom = UppaalCordinateStaticData.controlleractionzoom;
		UppaalState preus = startus;
		int startx = -1;
		int starty = -1;
		int stopx = -1;
		int stopy = -1;
		switch (direction) {
		case ControllerAndActionTransferLogic.entry:
			startx = ini.getX() + UppaalCordinateStaticData.activeleftnailxgap*len*zoom;
			starty = ini.getY() + UppaalCordinateStaticData.activeleftnailygap*len*zoom;
			stopx = ini.getX() + UppaalCordinateStaticData.activerightnailxgap*len*zoom;
			stopy = ini.getY() + UppaalCordinateStaticData.activerightnailxgap*len*zoom;
			break;
		case ControllerAndActionTransferLogic.during:
			startx = ini.getX() + UppaalCordinateStaticData.duringleftnailxgap*len*zoom;
			starty = ini.getY() + UppaalCordinateStaticData.duringleftnailygap*len*zoom;
			stopx = ini.getX() + UppaalCordinateStaticData.duringrightnailxgap*len*zoom;
			stopy = ini.getY() + UppaalCordinateStaticData.duringrightnailxgap*len*zoom;
			break;
		case ControllerAndActionTransferLogic.exit:
			startx = ini.getX() + UppaalCordinateStaticData.deactiveleftnailxgap*len*zoom;
			starty = ini.getY() + UppaalCordinateStaticData.deactiveleftnailygap*len*zoom;
			stopx = ini.getX() + UppaalCordinateStaticData.deactiverightnailxgap*len*zoom;
			stopy = ini.getY() + UppaalCordinateStaticData.deactiverightnailxgap*len*zoom;
			break;
		default:
			System.err.println("more than 3 or below 1 direction?");
			System.exit(1);
			break;
		}
		int xgap = (stopx - startx)/len;
		int ygap = (stopy - starty)/len;
		
		int cx = startx;
		int cy = starty;
		int i = 0;
		for (i = 0; i < len; i++) {
			UppaalState srcus = preus;
			UppaalState tgtus = TransferCommonLibrary.CreateOnlyBackUppaalState(transIDPfx + (i + 1), null,
					-1, state, ini, ua, cx, cy);
			String cs = cas[i];
			String act = cs;
			if (SimpleParserHelper.IsEventDispatch(cs)) {
				int[] eventresultpair = RunEnvironment.getmEventRegistry().GetEvtId(cs, state);
				act = "DispatchFuncEvent(" + eventresultpair[0] + ","
						+ eventresultpair[1] + "," + eventresultpair[2] + ")";
			}
			new UppaalTransition(transIDPfx + (i + 1) + "_t", srcus, tgtus, "",
					commonJudge, "", act, ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(srcus, tgtus, 0));
			preus = tgtus;
			cx += xgap;
			cy += ygap;
		}
		// //at last,transfer the execution chance to controller.
		// String transferpoweract = "MSEAutoType(aControllerAutomaton)";
		new UppaalTransition(transIDPfx + (i + 1) + "_t", preus, stopus, "",
				commonJudge, "", lastOperation, ua, StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(preus, stopus, 0));
	}
	
	public static SimulinkModel getSimulinkModel() {
		return simulinkModel;
	}

	public static void setSimulinkModel(SimulinkModel simulinkModel) {
		ControllerAndActionTransferLogic.simulinkModel = simulinkModel;
	}
	
}