package TransferLogic;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import CommonLibrary.SimpleParserHelper;
import PositionLibrary.StatesLinesPositionLibrary;
import PositionLibrary.StatesPointsPositionLibrary;
import StateFlow.StateflowController;
import StateflowStructure.Chart;
import StateflowStructure.Data;
import StateflowStructure.Junction;
import StateflowStructure.SimulinkModel;
import StateflowStructure.State;
import StateflowStructure.StateflowConnector;
import StateflowStructure.Transition;
import StateflowStructure.TransitionSorter;
import Uppaal.UppaalAutomaton;
import Uppaal.UppaalController;
import Uppaal.UppaalCordinateStaticData;
import Uppaal.UppaalDeclaration;
import Uppaal.UppaalModel;
import Uppaal.UppaalState;
import Uppaal.UppaalTransition;

public class TransferController {

	StateflowController fStateflowController = null;
	UppaalController fUppaalController = null;
	SimulinkModel simulinkModel;
	UppaalModel uppaalModel;

	public static final int Entry = 100;
	public static final int During = 101;
	public static final int Exit = 102;
	public static final int Conditional = 103;

	// Local global variavle.
	static int mNowChartId = -1;

	Map<String, Boolean> mVisitedStates = new TreeMap<String, Boolean>();
	Map<String, Boolean> mTempVisitedStates = new TreeMap<String, Boolean>();

	public TransferController(StateflowController sfc, UppaalController upc) {
		fStateflowController = sfc;
		fUppaalController = upc;
	}

	public void TransferStateflowToUppaal() throws Exception {
		simulinkModel = fStateflowController.ReadStateflowContent();
		System.out.println("Read stateflow model successfully.");
		ControllerAndActionTransferLogic.setSimulinkModel(simulinkModel);
		RunEnvironment.getmEventTimeStatistic().SetSimulink(simulinkModel);
		uppaalModel = RealTransfer(simulinkModel);
		fUppaalController.WriteUppaalContent(uppaalModel);
		System.out.println("Write Uppaal model into xml successfully.");
		System.out.println("\nThe whole process ended successfully.");
	}

	private UppaalModel RealTransfer(SimulinkModel simulinkModel) {
		mVisitedStates.clear();
		UppaalModel result = new UppaalModel();
		UppaalAutomaton ua = new UppaalAutomaton("all_controller", false,
				false, false);
		// generate all_controller which is responsible for the disable event
		// clear.
		ControllerAndActionTransferLogic.GenerateDeleteInactiveEventLogic(ua);
		result.AddUA(ua);
		// one level by one level
		ArrayList<Chart> charts = simulinkModel.getStateflowcharts();
		UppaalDeclaration overallud = RunEnvironment.getmUppaalDeclarationManager()
				.GetUppaalOverAllDeclaration();
		int i = 0;
		int len = charts.size();
		for (i = 0; i < len; i++) {
			Chart chart = charts.get(i);
			// extract all four parts and modify function name and identity.
			ActionLabelGlobalModifier.ModifyAllThreeActionsInStates();
			
			System.out.println("Parse Stateflow language succesfully.");
			
			ActionLabelGlobalModifier.ExtractAllFourPartsAndModify();
			
			System.out.println("Transfer Stateflow language into Uppaal successfully.");
			
			RunEnvironment.getmEventTimeStatistic().CountAndReplaceEvent(chart, overallud);
			
			System.out.println("Transfer Stateflow time related operation into Uppaal successfully.");
			
			mNowChartId = RunEnvironment.getmStateManager().GetStateId(chart);
			
			ArrayList<UppaalAutomaton> tmpres = TransferOneChart(chart);
			
			System.out.println("Transfer Stateflow diagram into Uppaal Automaton diagram successfully.");
			
			// TODO this may be modified, when mutiple charts are about
			// supported.
			ControllerAndActionTransferLogic
					.GenerateOneChartEventDrivenAutomaton(ua, chart);
			
			System.out.println("Generate system driven automaton successfully.");
			
			result.AddUAs(tmpres);
		}
		return result;
	}

	private ArrayList<UppaalAutomaton> TransferOneChart(Chart cht) {
		ArrayList<UppaalAutomaton> result = new ArrayList<UppaalAutomaton>();
		State chstate = (State) cht;
		if (!chstate.hasChildStates()) {
			return null;
		}
		// Give all junctions a controller automaton;
		TransferAllJunctions(RunEnvironment.getmStateManager().GetAllJunctionsAsArrayList(), result);

		TransferOneState(chstate, result);
		return result;
	}

	private void TransferAllJunctions(ArrayList<Junction> allJunctions,
			ArrayList<UppaalAutomaton> result) {
		int len = allJunctions.size();
		for (int i = 0; i < len; i++) {
			result.add(ControllerAndActionTransferLogic
					.GenerateSimplifiedJunctionController(allJunctions.get(i)));
		}
	}

	private void TransferOneState(State state, ArrayList<UppaalAutomaton> result) {
		// Handle data declaration
		boolean isChart = false;
		boolean hasChilds = false;
		if (state instanceof Chart) {
			isChart = true;
		}
		if (state.hasChildStates()) {
			hasChilds = true;
		}
		Map<String, Data> datas = state.getDatas();
		Set<String> keys = datas.keySet();
		UppaalDeclaration overall = RunEnvironment.getmUppaalDeclarationManager()
				.GetUppaalOverAllDeclaration();
		for (String key : keys) {
			Data data = datas.get(key);
			overall.AddVariable(state.getDataName(key),
					TransferCommonLibrary.CompDataType(data));
		}
		if ((!state.hasChildStates()) && (!state.HasThreeActions())) {
			result.add(ControllerAndActionTransferLogic
					.GenerateSimplifiedStateControllerForNothingHaveState(state));
			return;
		}
		UppaalAutomaton[] uas = ControllerAndActionTransferLogic
				.GenerateCompStateController(state);
		TransferCommonLibrary.AddAutomatonArrayToArrayList(result, uas);
		if (!state.hasChildStates()) {
			return;
		}
		UppaalAutomaton ua = new UppaalAutomaton(state,
				UppaalAutomaton.COMMONUA, isChart, hasChilds,
				state.HasThreeActions());
		ua.getUppaalDeclaration()
				.AddVariable(
						TransferCommonLibrary
								.GetIfDispatchedToConditionalOnceVariable(),
						"ST:bool:false");
		result.add(ua);

		Map<String, State> states = state.getStates();
		keys = states.keySet();
		for (String key : keys) {
			State childstate = states.get(key);
			// LabelString如果含有entry或者during或者exit的话，需要进一步处理
			TransferOneState(childstate, result);
		}
		int nowstateid = RunEnvironment.getmStateManager().GetStateId(state);
		if (state.isfChildDecomposition() == State.ChildsParallel) {
			UppaalState ini = TransferCommonLibrary.CreateRawUppaalState(
					TransferCommonLibrary.GetInitialNameOfCompState(state),
					state.getfLabelName() + "_parallel_initial", true, -1, ua,
					UppaalCordinateStaticData.commoncenterx,
					UppaalCordinateStaticData.commoncentery);
			// state is all parallel,then do nothing,just delete the event send
			// to me.
			new UppaalTransition(
					TransferCommonLibrary.GetCommonParallelTransitionName(
							state, 0), ini, ini, "", "JVSE2M(" + nowstateid
							+ ",aCommonAutomaton)", "", "PopOneEvent()", ua,
					StatesLinesPositionLibrary
							.MakeChoiceBetweenAboveTwoFunction(ini, ini, 0));
		} else {
			UppaalState ini = TransferCommonLibrary.CreateRawUppaalState(
					"State_" + state.getID() + "_initial",
					state.getfLabelName() + "_serial_initial", true, -1, ua,
					UppaalCordinateStaticData.commoncenterx,
					UppaalCordinateStaticData.commoncentery);
			UppaalAutomaton ua_cond = new UppaalAutomaton(state,
					UppaalAutomaton.CONDITIONALUA, isChart, hasChilds,
					state.HasThreeActions());
			result.add(ua_cond);
			UppaalState ini_cond = TransferCommonLibrary.CreateRawUppaalState(
					"State_" + state.getID() + "_initial",
					state.getfLabelName() + "_ser_cond_initial", true, -1,
					ua_cond, UppaalCordinateStaticData.commoncenterx,
					UppaalCordinateStaticData.commoncentery);
			// state is sequence,begin generate the true process.
			// BeginGenerate(state, null, null, initialus, initialus, null,
			// null, uppaaldeclaration, ua, null, initialus);
			mMaxPathCount = 0;
			mMaxDepthCount = 0;

			mTempVisitedStates.clear();
			ArrayList<State> childs = state.getOrderedStates();

			ArrayList<State> needtogenerate = childs;
			ArrayList<State> temp = new ArrayList<State>();
			UppaalState startus = ini;
			UppaalState startus_cond = ini_cond;
			State beginstate = null;
			do {
				BeginGenerate(state, beginstate, ini, startus, ini_cond,
						startus_cond, ua, ua_cond, 0);
				int i = 0;
				int len = needtogenerate.size();
				for (i = 0; i < len; i++) {
					State childstat = needtogenerate.get(i);
					if (!mTempVisitedStates.containsKey(TransferCommonLibrary
							.GetStateName(childstat, state))) {
						temp.add(childstat);
					}
				}
				needtogenerate = temp;
				temp = new ArrayList<State>();

				if (needtogenerate.size() > 0) {
					beginstate = needtogenerate.get(0);
					int lx = beginstate.getPosx() - inix;
					int ly = beginstate.getPosy() - iniy;
					int[] xygap = StatesPointsPositionLibrary.GetLeastGapxy(lx,
							ly);
					int snrex = xygap[0] + ini.getX();
					int snrey = xygap[1] + ini.getY();
					startus = TransferCommonLibrary.CreateTwoWayUppaalState(
							"State_" + beginstate.getID() + "unode",
							beginstate.getfLabelName(),
							RunEnvironment.getmStateManager().GetStateId(beginstate), state, ini,
							ua, snrex, snrey);
					startus_cond = TransferCommonLibrary
							.CreateTwoWayUppaalState(
									"State_" + beginstate.getID() + "unode",
									beginstate.getfLabelName(),
									RunEnvironment.getmStateManager().GetStateId(beginstate), state,
									ini_cond, ua_cond, snrex, snrey);
				} else {
					break;
				}
			} while (true);

			// overall declaration
			UppaalDeclaration uoveralldecl = RunEnvironment.getmUppaalDeclarationManager()
					.GetUppaalOverAllDeclaration();
			String parentfullname = state.GetFullName();
			// clear child path array.

			// conditional declaration
			if (mMaxDepthCount > 0) {
				uoveralldecl.AddVariable("m" + parentfullname
						+ "PathSelectLength", "const int:" + mMaxDepthCount);
				uoveralldecl.AddVariable("m" + parentfullname + "PathSelect",
						"ArraySIZE:int:" + mMaxDepthCount);
				uoveralldecl.AddFunction(parentfullname + "PathClear",
						"\nvoid Clear" + parentfullname + "PathSelect()\n"
								+ "{\n" + "    int i = m" + parentfullname
								+ "PathSelectLength-1;\n"
								+ "    while (i &gt;= 0)\n" + "    {\n"
								+ "        m" + parentfullname
								+ "PathSelect[i] = 1;\n" + "        i--;\n"
								+ "    }\n" + "}\n");
				uoveralldecl.AddFunction(parentfullname + "PathClearAtDepth",
						"\nvoid Clear" + parentfullname
								+ "PathSelectAtDepth(int depth)\n" + "{\n"
								+ "    m" + parentfullname
								+ "PathSelect[depth] = 1;\n" + "}\n");

				uoveralldecl.AddVariable("m" + parentfullname + "ChildPath",
						"ArraySIZE:int:" + mMaxDepthCount);
				/*
				 * uoveralldecl .AddFunction( parentfullname +
				 * "PathClearForJudgePath", "\nvoid Clear" + parentfullname +
				 * "JudgePathResult()\n{\n    int i = " + (mMaxPathCount - 1) +
				 * ";\n    while (i &gt;=0)\n    {\n        m" + parentfullname
				 * + "ChildPath[i] = false;\n        i--;\n    }\n}\n");
				 */
			}

			// which source to back.
			UppaalDeclaration uacond_decl = ua_cond.getUppaalDeclaration();
			uacond_decl.AddVariable("mSourceRecordLength", "ST:const int:"
					+ mMaxDepthCount);
			uacond_decl.AddVariable("mSourceRecordArray", "ArraySIZE:int:"
					+ mMaxDepthCount);
		}
	}

	int mMaxPathCount = 0;
	int mMaxDepthCount = 0;

	int inix = -1;
	int iniy = -1;

	private int[] GetSourceStatePosition(StateflowConnector sfc,
			StateflowConnector tfc) {
		int[] result = new int[2];
		if (sfc == null) {
			result[0] = tfc.getPosx();
			result[1] = tfc.getPosy()
					- StatesPointsPositionLibrary.defaultpointygap;
			inix = result[0];
			iniy = result[1];
		} else {
			result[0] = sfc.getPosx();
			result[1] = sfc.getPosy();
		}
		return result;
	}

	// only for common automaton.
	private void GenerateCommunicatationWithControllerAndConditionalAutomatonOnlyInStateOrIni(
			State parentstate, StateflowConnector nowstate,
			UppaalState stateorinius, String parentfullname, int parentid,
			UppaalAutomaton commonua) {
		int nowstatid = stateorinius.getID();
		String judge = "(!m" + parentfullname + "ExistPath)&amp;&amp;JVSE2M("
				+ parentid + ",aCommonAutomaton)";
		String communicatefuncname = TransferCommonLibrary
				.GetCommonAutomatonCommunicateFunctionName(parentid, nowstatid);
		String clearpathselect = TransferCommonLibrary
				.GetFunctionNameOfClearPathSelectArrayInConditionalAutomaton(parentfullname);
		String fupdate = communicatefuncname + "," + clearpathselect;
		if (nowstate != null
				&& (nowstate.getOuttransition() == null || nowstate
						.getOuttransition().size() == 0)) {
			fupdate = "ReturnToController()";
		}
		new UppaalTransition("", stateorinius, stateorinius, "", judge, "",
				fupdate, commonua,
				StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(
						stateorinius, stateorinius, 0));
	}

	private void BeginGenerate(State parentstate,
			StateflowConnector beginstate, UppaalState ini,
			UppaalState startus, UppaalState ini_cond,
			UppaalState startus_cond, UppaalAutomaton ua,
			UppaalAutomaton ua_cond, int depthcount) {
		mVisitedStates.put(
				TransferCommonLibrary.GetStateName(beginstate, parentstate),
				true);
		mTempVisitedStates.put(
				TransferCommonLibrary.GetStateName(beginstate, parentstate),
				true);
		// There seems no need to get the uppaal declaration.
		// UppaalDeclaration udecl = ua.getUppaalDeclaration();
		// UppaalDeclaration udecl_cond = ua_cond.getUppaalDeclaration();
		// get local uppaal automaton's declarations.

		int parentid = RunEnvironment.getmStateManager().GetStateId(parentstate);
		String parentfullname = parentstate.GetFullName();
		String pathselectarray = "m" + parentfullname + "PathSelect";
		ArrayList<Transition> trans = null;
		trans = (beginstate == null ? parentstate.getDefaulttransitions()
				: beginstate.getOuttransition());

		trans.sort(new TransitionSorter());
		// get transitions and sort it by priority.

		// begin iterate all outgoing transition of a state.
		int len = trans.size();
		// generate Runner Automaton.
		// for non junction state,generate self cycle to judge the ordered path.
		// but now , it is useless.
		/*
		 * String stateactivejudge = "";//ini doesn't need active judge. if
		 * (beginstate instanceof State) { stateactivejudge = "&&(m" +
		 * ua.getFullname() + "ChildActive)"; }
		 */
		int startx = startus.getX();
		int starty = startus.getY();
		if (beginstate == null || beginstate instanceof State) {
			GenerateCommunicatationWithControllerAndConditionalAutomatonOnlyInStateOrIni(
					parentstate, beginstate, startus, parentfullname, parentid,
					ua);
		}

		// trace back for junction.
		// only Junction has back action
		String sendtoconditional = "JVSE2M(" + parentid
				+ ",aConditionalAutomaton)";
		// trace back for state and ini.
		// if no path find, only state and ini need to back to common ua.
		if ((beginstate == null || beginstate instanceof State)) {
			String backadditionalaction = "m" + parentfullname
					+ "ExistPath=false,MSEAutoType(aCommonAutomaton)";
			// JPathMatch
			String condition = sendtoconditional + "&&" + pathselectarray + "["
					+ depthcount + "]==" + (len + 1);

			new UppaalTransition((beginstate == null ? "ini"
					: beginstate.getID())
					+ "_conditional_back_self_state", startus_cond,
					startus_cond, "", condition, "", backadditionalaction,
					ua_cond,
					StatesLinesPositionLibrary
							.MakeChoiceBetweenAboveTwoFunction(startus_cond,
									startus_cond, 0));
		}

		// generate delete direct_to_state event transition.
		if (beginstate == null) {
			new UppaalTransition(
					"ini_cond_deltoitdirect_transition",
					ini_cond,
					ini_cond,
					"",
					"JVSEF2M("
							+ parentid
							+ ",eTransToStateDirectly,aConditionalAutomaton)&amp;&amp;(JVSEDestPos(0))",
					"", "PopOneEvent()", ua_cond, StatesLinesPositionLibrary
							.MakeChoiceBetweenAboveTwoFunction(ini_cond,
									ini_cond, 0));
		}

		if (len > 0) {
			// calculate depth count.
			if (mMaxDepthCount < depthcount + 1) {
				mMaxDepthCount = depthcount + 1;
			}
		}

		for (int i = 0; i < len; i++) {
			Transition transition = trans.get(i);
			mMaxPathCount++;
			transition.setPathid(mMaxPathCount - 1);
			String[] cparts = transition.getFourparts();
			StateflowConnector sourcestate = transition.getSourcestate();
			StateflowConnector targetstate = transition.getTargetstate();

			boolean hasmorelevel = (targetstate instanceof Junction)
					&& targetstate.getOuttransition() != null
					&& targetstate.getOuttransition().size() > 0;

			// for every state,generate common transition reguard less of
			// conditional action.
			int[] srcpos = GetSourceStatePosition(sourcestate, targetstate);
			int lx = targetstate.getPosx() - srcpos[0];
			int ly = targetstate.getPosy() - srcpos[1];
			int[] xygap = StatesPointsPositionLibrary.GetLeastGapxy(lx, ly);
			int stopx = xygap[0] + startx;
			int stopy = xygap[1] + starty;
			UppaalState stopus = TransferCommonLibrary.CreateTwoWayUppaalState(
					targetstate.getID(), targetstate.GetFullName(),
					RunEnvironment.getmStateManager().GetStateId(targetstate), parentstate, ini, ua,
					stopx, stopy);
			GenerateOneRunnerTransition(cparts[3], startus, stopus, ua,
					parentstate, ini, transition, depthcount);

			// The code below all do conditional operation except the forward
			// operation.
			// generate Conditional Automaton.
			// generate conditional transition.All jedge and the bool array
			// reset.
			UppaalState stop_condus = TransferCommonLibrary
					.CreateTwoWayUppaalState(targetstate.getID() + "_cond",
							targetstate.GetFullName(),
							RunEnvironment.getmStateManager().GetStateId(targetstate), parentstate,
							ini_cond, ua_cond, stopx, stopy);
			// First generate back transition.

			// initialize position.
			int cdtlen = 3;
			if (cparts[2] != null && !cparts[2].equals("")) {
				// translate conditional action to automaton.
				String[] cdactions = cparts[2].split(";");
				cdtlen += cdactions.length;
			}
			StatesPointsPositionLibrary.TwoStateflowStatesPointsInitialize(
					startus_cond, stop_condus, cdtlen);

			String intrinsicJudge = "";
			if (cparts[0] != null && !cparts[0].equals("")) {
				intrinsicJudge += "JVSEF2M(" + parentid + ","
						+ RunEnvironment.getmEventRegistry().GetEvtId(cparts[0], parentstate)[0]
						+ ",aConditionalAutomaton)";
			}
			if (cparts[1] != null && !cparts[1].equals("")) {
				intrinsicJudge += (intrinsicJudge.equals("") ? "" : "&&");
				intrinsicJudge += ("(" + cparts[1] + ")");
			}
			// the code above is the just translate the transition judge into
			// intrinsicJudge String Variable.
			String commonJudge = "JVSE2M("
					+ parentid
					+ ",aConditionalAutomaton)&amp;&amp;(!JVSEF(eTransToStateDirectly))";
			// the code above is used for conditional automaton for self
			// execution permission.
			String imdjudgename = (sourcestate == null ? "ini" : sourcestate
					.getID())
					+ "_imd_judge_priority_"
					+ targetstate.getID()
					+ "_trans_" + transition.getID();
			UppaalState imdjudgeforpriority = TransferCommonLibrary
					.CreateRawUppaalState(imdjudgename, null, false, -1,
							ua_cond,
							StatesPointsPositionLibrary.NextStatePositionx(),
							StatesPointsPositionLibrary.NextStatePositionx());

			UppaalState pseudo_startus_cond = imdjudgeforpriority;
			// mocked pseudo_startus_cond which means before real cond operation
			// we need to do path selection based on priorioty.

			// generate if path hold proceed or no hold back logic.
			// this if blocked is not right,all nodes needs the logic below.
			// if (sourcestate != null && sourcestate instanceof Junction) {

			/*
			 * String clearpathaction = ""; if (sourcestate == null ||
			 * sourcestate instanceof State) { clearpathaction =
			 * ","+TransferCommonLibrary
			 * .GetClearPathJudgeResultByParentFullName(parentfullname); }
			 */
			new UppaalTransition((sourcestate == null ? "ini"
					: sourcestate.getID())
					+ "_imd_judge_trans_" + targetstate.getID(), startus_cond,
					imdjudgeforpriority, "", "m" + parentfullname
							+ "PathSelect[" + depthcount + "]==" + (i + 1)
							+ "&amp;&amp;" + commonJudge, "",
					(hasmorelevel ? TransferCommonLibrary
							.GetCondAutomatonResourceArrayName()
							+ "["
							+ (depthcount + 1)
							+ "]="
							+ (transition.getPathid()) + "," : "")
							+ "m"
							+ parentfullname
							+ "PathSelect["
							+ depthcount
							+ "]++", ua_cond,
					StatesLinesPositionLibrary
							.MakeChoiceBetweenAboveTwoFunction(startus_cond,
									imdjudgeforpriority, 0));
			// this code above showed that only the transition current priority
			// matched could proceed.
			// this code only decide which transition can be executed.No do the
			// real judgement.

			new UppaalTransition((sourcestate == null ? ""
					: sourcestate.getID())
					+ "_imd_judge_false_trans_" + targetstate.getID(),
					imdjudgeforpriority, startus_cond, "", commonJudge
							+ "&&!("
							+ (intrinsicJudge.equals("") ? true
									: intrinsicJudge) + ")", "", "m"
							+ parentfullname + "ChildPath[" + depthcount
							+ "]=-1", ua_cond,
					StatesLinesPositionLibrary
							.MakeChoiceBetweenAboveTwoFunction(
									imdjudgeforpriority, startus_cond, 0));
			// if the real judgement fails ,back to start_condus.

			// at last, must add this over operation.
			String overaction = "m" + parentfullname
					+ "ExistPath=true,MSEAutoType(aCommonAutomaton)";
			// do conditional action.
			// Every transition must do this action at very first.
			String judgesetaction = "m" + parentfullname + "ChildPath["
					+ depthcount + "]=" + (transition.getPathid());
			if (cparts[2] != null && !cparts[2].equals("")) {
				// translate conditional action to automaton.
				String[] cdactions = cparts[2].split(";");
				UppaalState prestate = pseudo_startus_cond;// this replace by
															// pseudo startus
															// considering the
															// priority
															// mechanism.
				int cdlen = cdactions.length;
				for (int cl = 0; cl < cdlen; cl++) {
					cdactions[cl] = cdactions[cl].trim();
				}
				for (int k = 0; k < cdlen; k++) {
					UppaalState ttstate = stop_condus;
					if (k < cdlen - 1) {
						ttstate = TransferCommonLibrary
								.CreateOnlyBackUppaalState(
										(sourcestate == null ? "sourceini"
												: sourcestate.getID())
												+ "_imd"
												+ k
												+ targetstate.getID()
												+ "_cond_" + transition.getID(),
										null, -1, parentstate, ini_cond,
										ua_cond, StatesPointsPositionLibrary
												.NextStatePositionx(),
										StatesPointsPositionLibrary
												.NextStatePositiony());
					} else {
						StatesPointsPositionLibrary.FinalizeStatePosition();
					}
					String fguard = commonJudge;
					String transitionjudgeaction = "";
					if (k == 0) {
						transitionjudgeaction = judgesetaction;

						fguard += (intrinsicJudge.equals("") ? "" : "&&")
								+ intrinsicJudge;
					}
					String fupdate = transitionjudgeaction
							+ (transitionjudgeaction.equals("") ? "" : ",");
					if (SimpleParserHelper.IsEventDispatch(cdactions[k])) {
						int[] eventresultpair = RunEnvironment.getmEventRegistry().GetEvtId(
								cdactions[k], parentstate);
						fupdate += "DispatchFuncEvent(" + eventresultpair[0]
								+ "," + eventresultpair[1] + ","
								+ eventresultpair[2] + ")";
					} else {
						fupdate += cdactions[k];
					}
					if (k == cdlen - 1 && targetstate instanceof State) {
						// Before transfer to the state , do
						// MSEAutoType(aCommonAutomaton) action.
						fupdate += ((fupdate.equals("") ? "" : ",") + overaction);
					}
					new UppaalTransition(startus_cond.getID() + "_trans" + k
							+ "_" + stop_condus.getID(), prestate, ttstate, "",
							fguard, "", fupdate, ua_cond,
							StatesLinesPositionLibrary
									.MakeChoiceBetweenAboveTwoFunction(
											prestate, ttstate, 0));
					prestate = ttstate;
					// iteratively set end become start.
				}
				StatesLinesPositionLibrary.IncreaseCount(startus_cond,
						stop_condus, 2, 0);
			} else {
				// conditional action is null just transfer from startus to
				// stopus directly.
				String fupdate = judgesetaction;
				if (targetstate instanceof State) {
					fupdate += "," + overaction;
				}
				new UppaalTransition(startus_cond.getID() + "_trans0_"
						+ stop_condus.getID(), pseudo_startus_cond,
						stop_condus, "", commonJudge
								+ (intrinsicJudge.equals("") ? "" : "&&")
								+ intrinsicJudge, "", fupdate, ua_cond,
						StatesLinesPositionLibrary
								.MakeChoiceBetweenAboveTwoFunction(
										pseudo_startus_cond, stop_condus, 0));
			}

			if ((targetstate != null && targetstate instanceof Junction)) {
				// generate target back trace to this start state.
				// only junction do this.
				String clearpathselectatdepth = "";
				if (hasmorelevel) {
					clearpathselectatdepth = TransferCommonLibrary
							.GetFunctionNameOfClearPathSelectArrayInConditionalAutomatonAtDepth(
									parentfullname, depthcount + 1);
				}
				String backtowherejudge = TransferCommonLibrary
						.GetCondAutomatonResourceArrayName()
						+ "["
						+ (depthcount + 1) + "]==" + (transition.getPathid());
				new UppaalTransition(
						(targetstate == null ? "ini" : targetstate.getID())
								+ "_conditional_back_" + transition.getID(),
						stop_condus,
						startus_cond,
						"",
						sendtoconditional
								+ "&amp;&amp;"
								+ backtowherejudge
								+ (hasmorelevel ? "&amp;&amp;"
										+ pathselectarray
										+ "["
										+ (depthcount + 1)
										+ "]=="
										+ (targetstate.getOuttransition()
												.size() + 1) : ""),
						"",
						"m"
								+ parentfullname
								+ "ChildPath["
								+ (depthcount)
								+ "] = -1"
								+ (clearpathselectatdepth.equals("") ? "" : ",")
								+ clearpathselectatdepth, ua_cond,
						StatesLinesPositionLibrary
								.MakeChoiceBetweenAboveTwoFunction(
										startus_cond, stop_condus, 0));
			}

			// consider the state that don't belong to parent state more.
			// recursive iterate the target state.
			if (targetstate.getParent() == parentstate) {
				int newdepthcount = depthcount + 1;
				if (targetstate instanceof State) {
					newdepthcount = 0;
				}
				if (mVisitedStates.get(TransferCommonLibrary.GetStateName(
						targetstate, parentstate)) == null) {
					BeginGenerate(parentstate, targetstate, ini, stopus,
							ini_cond, stop_condus, ua, ua_cond, newdepthcount);
				}
			}
		}
	}

	private void GenerateOneRunnerTransition(String caction,
			UppaalState startus, UppaalState stopus, UppaalAutomaton ua,
			State parentstate, UppaalState ini, Transition transition,
			int depthcount) {
		// eDeActivationOver is useful
		// eActivationOver is useless.
		int parentid = RunEnvironment.getmStateManager().GetStateId(parentstate);
		String parentfullname = parentstate.GetFullName();
		int transitionid = transition.getPathid();

		StateflowConnector deactstate = null;
		StateflowConnector actstate = null;
		String deaction = "";
		String actaction = "";

		/*
		 * StateflowConnector sourcestate = transition.getSourcestate();
		 * StateflowConnector targetstate = transition.getTargetstate();
		 */

		if (transition != null) {
			deactstate = transition.getSourcestate();
			actstate = transition.getTargetstate();
			deaction = GenerateDeactivation(deactstate, actstate);
			actaction = GenerateActivation(deactstate,
					transition.getContainer(), actstate);
		}
		StateflowConnector sourcestat = transition.getSourcestate();
		StateflowConnector targetstat = transition.getTargetstate();
		// if transition is not cross bound.
		if (sourcestat instanceof Junction
				|| sourcestat == null
				|| (!((State) sourcestat).hasChildStates() && !((State) sourcestat)
						.HasThreeActions())) {
			if ((sourcestat == null && isDecedent(parentstate, targetstat))
					|| (isDecedent(sourcestat.getParent(), targetstat))) {
				deaction = "";
			}
		}
		if (targetstat instanceof Junction
				|| targetstat == null
				|| (!((State) targetstat).hasChildStates() && !((State) targetstat)
						.HasThreeActions())) {
			if ((sourcestat == null && isDecedent(targetstat.getParent(),
					parentstate))
					|| (isDecedent(targetstat.getParent(), sourcestat))) {
				actaction = "";
			}
		}

		if (targetstat instanceof State && ((State) targetstat).HasCountEvent()) {
			actaction += (actaction.equals("") ? "" : ",")
					+ EventTimeStatistic
							.GetClearFuncNameByState((State) targetstat) + "()"
					+ "," + EventTimeStatistic.GetSetOneToFalse() + "("
					+ RunEnvironment.getmStateManager().GetStateId(targetstat) + ")";
		}
		// the code above search for transition's source state and target state.
		// find the state container both the source and target.
		// active is stop at the state container both the source and target.
		// deactive stop at run time at smallest active.

		// must do at first:
		String invalidexistpath = "m" + parentfullname + "ExistPath = false";

		String whichpath = "(m" + parentfullname + "ChildPath[" + depthcount
				+ "] == " + transitionid + ")&amp;&amp;";

		String existpath = "(m" + parentfullname + "ExistPath)&amp;&amp;";
		if (sourcestat instanceof Junction) {
			// junction doesn't need mFullNameExistPath judge, because if there
			// is junction and runned , there must be a valid path.
			existpath = "";
		}
		// deactivation fire;
		// ini state don't need to fire deactivation.
		// Attention : the ini state also don't need to judge the deactivation.
		if (!startus.isInitial()) {
			// self cycle to fire deactivation;
			new UppaalTransition(
					startus.getID() + "_self_activejudge_firedeactive",
					startus,
					startus,
					"",
					whichpath + existpath + "JVSE2M(" + parentid
							+ ",aCommonAutomaton)",
					"",
					"DispatchEvent(eDeActivationOver," + parentid
							+ ",-1,aCommonAutomaton)"
							+ (deaction.equals("") ? "" : ",") + deaction + ","
							+ invalidexistpath,
					ua,
					StatesLinesPositionLibrary
							.MakeChoiceBetweenAboveTwoFunction(startus, startus, 0));
		}

		int catlen = 2;
		String[] cactions = null;
		// statepoints position initialize.
		if (caction != null && !caction.equals("")) {
			cactions = caction.split(";");
			int cdlen = cactions.length;
			catlen += cactions.length;
			for (int cl = 0; cl < cdlen; cl++) {
				cactions[cl] = cactions[cl].trim();
			}
		}
		StatesPointsPositionLibrary.TwoStateflowStatesPointsInitialize(startus,
				stopus, catlen);

		boolean parentparentchildparallel = false;
		if (parentstate.getParent() != null
				&& ((State) parentstate.getParent()).isfChildDecomposition() == State.ChildsParallel) {
			parentparentchildparallel = true;
		}
		boolean istargetstate = false;
		if (targetstat != null && targetstat instanceof State) {
			istargetstate = true;
		}
		String eventconsumedintransition = "CommonActionEventConsumed("
				+ parentparentchildparallel + "," + istargetstate + ")";

		UppaalState imdus = stopus;
		String actparentfullname = TransferCommonLibrary
				.GetParentFullName(actstate);
		String utimatemustdoaction = "m" + parentfullname
				+ "LoseControl=false,m" + actparentfullname + "HistoryState="
				+ RunEnvironment.getmStateManager().GetStateId(actstate) + ",mTryFindPath = false,"
				+ eventconsumedintransition + (actaction.equals("") ? "" : ",")
				+ actaction;

		if (caction != null && !caction.equals("")) {
			// Common Transition start by deactivation judge or mSSIDExistPath
			// only for ini.
			boolean needJOver = false;
			// starty += 5;
			int calen = cactions.length;
			for (int q = 0; q < calen; q++) {
				String oneact = cactions[q];
				if (SimpleParserHelper.IsEventDispatch(oneact)) {
					needJOver = true;
				}
			}
			// the code above iterate all nodes to judge if need JudgeOver.

			String judgeactionoverevent = "";
			String JOver = "";

			// begore imdus is mainly judge, after imdus is mainly action.
			imdus = TransferCommonLibrary.CreateRawUppaalState("preimd"
					+ stopus.getID() + "_" + transition.getID(), null, false,
					-1, ua, StatesPointsPositionLibrary.NextStatePositionx(),
					StatesPointsPositionLibrary.NextStatePositiony());

			if (!needJOver) {
				String ptract = caction.replaceAll(";", ",");
				if (ptract.endsWith(",")) {
					ptract = ptract.substring(0, ptract.length() - 1);
				}
				new UppaalTransition(
						startus.getID() + "_trans_activation_last", imdus,
						stopus, "", "", "", ptract + "," + utimatemustdoaction,
						ua,
						StatesLinesPositionLibrary
								.MakeChoiceBetweenAboveTwoFunction(imdus,
										stopus, 0));
			} else {
				JOver = "JVSEF2M(" + parentid + ",eJudgeActionOver,"
						+ ua.getType() + ")";
				judgeactionoverevent = "DispatchEvent(eJudgeActionOver,"
						+ parentid + ",-1,aCommonAutomaton)";

				// new
				// UppaalTransition(imdus.getID()+"_runnertrans_"+stopus.getID(),
				// imdus, stopus, "", "", "", "", ua);
				String onetransaction = "";
				UppaalState imdsrcstate = imdus;
				UppaalState imdtgtstate = null;
				boolean needJudgeOverForSeq = false;
				UppaalState runnertransactionoverstate = null;
				// not set yet, will be set in the following code.
				for (int q = 0; q < calen; q++) {
					String oneact = cactions[q];
					if (SimpleParserHelper.IsEventDispatch(oneact)) {
						int[] eventresultpair = RunEnvironment.getmEventRegistry().GetEvtId(oneact,
								transition.getContainer());
						onetransaction = judgeactionoverevent
								+ (judgeactionoverevent == null
										|| judgeactionoverevent.equals("") ? ""
										: ",") + "DispatchFuncEvent("
								+ eventresultpair[0] + "," + eventresultpair[1]
								+ "," + eventresultpair[2] + ")";
					} else {
						onetransaction = oneact;
					}
					if (q < calen - 1) {
						imdtgtstate = TransferCommonLibrary
								.CreateRawUppaalState(
										startus.getID() + "_imdactionevt" + q
												+ transition.getID(), null,
										false, -1, ua,
										StatesPointsPositionLibrary
												.NextStatePositionx(),
										StatesPointsPositionLibrary
												.NextStatePositiony());
					} else {
						runnertransactionoverstate = TransferCommonLibrary
								.CreateRawUppaalState(startus.getID()
										+ "_action_over" + transition.getID(),
										null, false, -1, ua,
										StatesPointsPositionLibrary
												.NextStatePositionx(),
										StatesPointsPositionLibrary
												.NextStatePositiony());
						imdtgtstate = runnertransactionoverstate;
					}
					String Judge = "";
					String extrapop = "";
					if (needJudgeOverForSeq) {
						Judge = JOver;
						extrapop = "PopOneEvent()";
						// this extra action above pop up eJudgeActionOver
						// event.
					}
					new UppaalTransition(
							startus.getID() + "_common_actionevt" + q,
							imdsrcstate,
							imdtgtstate,
							"",
							Judge,
							"",
							extrapop
									+ ((onetransaction == null || onetransaction
											.equals(""))
											|| (extrapop == null || extrapop
													.equals("")) ? "" : ",")
									+ onetransaction, ua,
							StatesLinesPositionLibrary
									.MakeChoiceBetweenAboveTwoFunction(
											imdtgtstate, imdsrcstate, 0));
					imdsrcstate = imdtgtstate;
					if (SimpleParserHelper.IsEventDispatch(oneact)) {
						needJudgeOverForSeq = true;
					} else {
						needJudgeOverForSeq = false;
					}
				}
				StatesPointsPositionLibrary.FinalizeStatePosition();
				new UppaalTransition(
						startus.getID() + "_trans_activation_last",
						runnertransactionoverstate, stopus, "",
						needJudgeOverForSeq ? JOver : "", "",
						needJudgeOverForSeq ? "m" + actparentfullname
								+ "HistoryState="
								+ RunEnvironment.getmStateManager().GetStateId(actstate)
								+ ",PopOneEvent()," // this pop up is to pop up
													// eJudgeActionOver event.
								// pop up the event driven the common ua to run.
								// record mStackTopEventIdRecord and will use in
								// actaction(active).
								+ utimatemustdoaction : utimatemustdoaction,
						ua,
						StatesLinesPositionLibrary
								.MakeChoiceBetweenAboveTwoFunction(stopus,
										runnertransactionoverstate, 0));
				// this if needJudgeOverForSeq , first popup JudgeActionOver
				// event , next
				// popup the real driven run event then dispatch activation
				// event.
			}
		}

		// these code below is startus to imdus.
		String pjudge = whichpath;
		// judge deactivation-over event is on the stack.
		// ini state don't need to judge event.
		// this default is for ini state.
		String commonjudge = existpath + "JVSE2M(" + parentid
				+ ",aCommonAutomaton)";
		// "&amp;&amp;JVSEF2M(" + parentid +
		// ",eDeActivationOver,aCommonAutomaton)";
		// String commonjudge = "(m"+parentfullname+"ValidPath)";Just wrong name
		// for valid path and exist path.
		String directaction = "";
		if (caction == null || caction.equals("")) {
			directaction = utimatemustdoaction;
		}
		String existpathop = invalidexistpath;
		String additionalact = "";
		if (!startus.isInitial()) {
			commonjudge = "JVSEF2M(" + parentid
					+ ",eDeActivationOver,aCommonAutomaton)";
			additionalact = "PopOneEvent()";// Pop up deactivation over event.
			existpathop = "";
		}
		String setlosecontrol = "m" + parentfullname + "LoseControl=true";
		if (caction == null || caction.equals("")) {
			setlosecontrol = "";
		}
		String semanytodefaultinvalid = existpathop
				+ (existpathop.equals("") ? "" : ",") + "m" + parentfullname
				+ "HistoryState=-1";
		String additionalfirst = additionalact
				+ ((semanytodefaultinvalid.equals("") || additionalact
						.equals("")) ? "" : ",") + semanytodefaultinvalid;
		String existpathandsetlosecontrol = additionalfirst
				+ (additionalfirst.equals("") || setlosecontrol.equals("") ? ""
						: ",") + setlosecontrol;
		String directandadditionalaction = existpathandsetlosecontrol
				+ ((directaction.equals("") || existpathandsetlosecontrol
						.equals("")) ? "" : ",") + directaction;
		new UppaalTransition(startus.getID() + "_runnertrans_" + imdus.getID(),
				startus, imdus, "", pjudge + commonjudge, "",
				directandadditionalaction, ua,
				StatesLinesPositionLibrary.MakeChoiceBetweenAboveTwoFunction(
						startus, imdus, 0));
	}

	/*
	 * private String TrimLastCharacterIfComma(String result) { if
	 * (result.charAt(result.length()-1) == ',') { result = result.substring(0,
	 * result.length()-1); } return result; }
	 */

	private String GenerateActivation(StateflowConnector deactstate,
			StateflowConnector deactparent, StateflowConnector actstate) {
		StateflowConnector sf = FindActiveFore(deactstate, deactparent,
				actstate);
		String result = "";
		if (sf instanceof State) {
			result = "Active(" + RunEnvironment.getmStateManager().GetStateId(actstate) + ","
					+ (RunEnvironment.getmStateManager().GetStateId(sf)) + ")";
		}
		return result;
	}

	private String GenerateDeactivation(StateflowConnector deactstate,
			StateflowConnector actstate) {
		if (deactstate == null) {
			return "";
		}
		StateflowConnector sf = FindDeactiveFore(deactstate, actstate);
		String result = "";
		if (sf instanceof State) {
			result = "Deactive(" + RunEnvironment.getmStateManager().GetStateId(deactstate) + ","
					+ RunEnvironment.getmStateManager().GetStateId(sf.getParent()) + ")";
		}
		return result;
	}

	private StateflowConnector FindDeactiveFore(StateflowConnector deactive,
			StateflowConnector active) {
		StateflowConnector pre = deactive;
		if (isDecedent(pre, active)) {
			return pre;
		}
		while (!isDecedent(pre.getParent(), active)) {
			pre = pre.getParent();
		}
		return pre;
	}

	private StateflowConnector FindActiveFore(StateflowConnector deactive,
			StateflowConnector deactparent, StateflowConnector active) {
		if (deactive == null) {
			deactive = deactparent;
		}
		StateflowConnector pre = active;
		if (isDecedent(pre, deactive)) {
			return pre;
		}
		while (!isDecedent(pre.getParent(), deactive)) {
			pre = pre.getParent();
		}
		return pre;
	}

	private boolean isDecedent(StateflowConnector ancestor,
			StateflowConnector offspring) {
		StateflowConnector sfc = offspring;
		do {
			if (ancestor == sfc) {
				return true;
			}
			sfc = sfc.getParent();
		} while ((sfc) != null);
		return false;
	}

	// Map<String, Boolean> CAgeneration = new TreeMap<String, Boolean>();

}