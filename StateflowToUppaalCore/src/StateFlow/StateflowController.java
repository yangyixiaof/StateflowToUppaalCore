package StateFlow;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import StateflowStructure.Box;
import StateflowStructure.Chart;
import StateflowStructure.Data;
import StateflowStructure.Event;
import StateflowStructure.GraphicalFunction;
import StateflowStructure.Junction;
import StateflowStructure.MatlabFunction;
import StateflowStructure.SimulinkModel;
import StateflowStructure.State;
import StateflowStructure.StateflowConnector;
import StateflowStructure.Transition;
import StateflowStructure.TruthTable;
import TransferLogic.RunEnvironment;

public class StateflowController {

	String fSourceFile = "";
	public static final int StateType = 1;
	public static final int JunctionType = 2;
	private Map<String,Integer> metainfo = new TreeMap<String, Integer>();

	public StateflowController(String sourcefile) {
		fSourceFile = sourcefile;
	}
	
	private Element SearchForPropertyMatchFromList(String attrname, String attrvalue, List<Element> properties)
	{
		//testing
		//System.err.println("prop size:"+properties.size());
		for (Element e : properties)
		{
			//testing
			//System.err.println("propname:"+e.getName()+",propvalue:"+e.getValue());
			List<Attribute> attrs = e.getAttributes();
			for (Attribute attr : attrs)
			{
				//testing
				//System.err.println("name:"+attr.getName()+",value:"+attr.getValue());
				if (attr.getName().equals(attrname) && attr.getValue().equals(attrvalue))
				{
					return e;
				}
			}
		}
		return null;
	}
	
	private void ParseFunction(Element sts, State stat, State state, String SSID)
	{
		Element truth_table = sts.getChild("truthTable");
		if (truth_table != null && SearchForPropertyMatchFromList("Name", "isTruthTable", (truth_table.getChildren("P"))).getValue().equals("1"))
		{
			Element predicateArray = SearchForPropertyMatchFromList("PropName", "predicateArray", truth_table.getChildren("Array"));
			String[] dimesion = predicateArray.getAttribute("Dimension").getValue().split("*");
			TruthTable tt = new TruthTable(SSID,stat);
			int rownum = Integer.parseInt(dimesion[0]);
			int colnum = Integer.parseInt(dimesion[1])-1;
			tt.InitialPredicateArray(rownum, colnum);
			List<Element> cells = predicateArray.getChildren("Cell");
			Iterator<Element> itr = cells.iterator();
			for (int s = 0;s < rownum;s++)
			{
				itr.next();
			}
			int col = 0;
			while (itr.hasNext())
			{
				for (int s = 0;s < rownum;s++)
				{
					String str = itr.next().getValue();
					tt.SetPredicateArray(s, col, str);
				}
				col++;
			}
			Element actionArray = SearchForPropertyMatchFromList("PropName", "predicateArray", truth_table.getChildren("Array"));
			String[] adimesion = actionArray.getAttribute("Dimension").getValue().split("*");
			int arownum = Integer.parseInt(adimesion[0]);
			int acolnum = Integer.parseInt(adimesion[1])-1;
			tt.InitialActionArray(arownum, acolnum);
			List<Element> acells = actionArray.getChildren("Cell");
			Iterator<Element> aitr = acells.iterator();
			for (int s = 0;s < arownum;s++)
			{
				aitr.next();
			}
			int acol = 0;
			while (aitr.hasNext())
			{
				for (int s = 0;s < arownum;s++)
				{
					String str = aitr.next().getValue();
					tt.SetActionArray(s, acol, str);
				}
				acol++;
			}
			state.addStateflowFunction(SSID, tt);
			RunEnvironment.getmFunctionsManager().putFunction(SSID, tt);
		}
		else
		{
			Element script = SearchForPropertyMatchFromList("Name", "script", sts.getChild("eml").getChildren("P"));
			if (script != null)
			{
				String scriptstr = script.getValue();
				MatlabFunction mf = new MatlabFunction(SSID,stat);
				mf.setScriptstr(scriptstr);
				state.addStateflowFunction(SSID, mf);
				RunEnvironment.getmFunctionsManager().putFunction(SSID, mf);
			}
			else
			{
				GraphicalFunction gf = new GraphicalFunction(SSID,stat);
				state.addStateflowFunction(SSID, gf);
				RunEnvironment.getmFunctionsManager().putFunction(SSID, gf);
			}
		}
	}
	
	private void ParseDataList(List<Element> datalist, State state)
	{
		for (Element data : datalist)
		{
			Element scopeele = SearchForPropertyMatchFromList("Name", "scope", data.getChildren("P"));
			String scopeinfo = scopeele.getValue();
			switch (scopeinfo) {
			case "FUNCTION_INPUT_DATA":
			case "FUNCTION_OUTPUT_DATA":
			case "LOCAL_DATA":
			case "INPUT_DATA":
				if (mNowParsingSimulinkModel.getmTimeGapForBackUp() != 0)
				{
					mNowParsingSimulinkModel.setmTimeGap(0);
				}
			case "OUTPUT_DATA":
			case "CONSTANT_DATA":
				String datatypestr = SearchForPropertyMatchFromList("Name", "primitive", data.getChild("props").getChild("type").getChildren("P")).getValue();
				String name = data.getAttributeValue("name");
				String sizes = null;
				try {
					sizes = data.getChild("props").getChild("array").getChild("P").getValue();
				} catch (Exception e) {
					//System.err.println("warn:"+name+" has informal format.");
					//do nothing.
				}
				Element inivalelement = SearchForPropertyMatchFromList("Name", "initialValue", data.getChild("props").getChildren("P"));
				String inival = inivalelement==null?null:inivalelement.getValue();
				if (datatypestr.equals("Inherit: From definition in chart"))
				{
					datatypestr = "NoType";
				}
				Data dat = new Data(name, scopeinfo, sizes, datatypestr, inival);
				RunEnvironment.getmDataManager().putData(name, dat);
				state.putData(name, dat);
				break;
			default:
				System.err.println("For now,Can not support data storage memory data type and paramater datatype.");
				System.exit(1);
				break;
			}
		}
	}
	
	private void ParseEventList(List<Element> eventlist, State state) {
		for (Element event : eventlist)
		{
			String scopeinfo = SearchForPropertyMatchFromList("Name", "scope", event.getChildren("P")).getValue();
			switch (scopeinfo) {
			case "INPUT_EVENT":
			case "LOCAL_EVENT":
			case "OUTPUT_EVENT":
				String name = SearchForPropertyMatchFromList("Name", "name", event.getChildren("P")).getValue();
				String trigger = SearchForPropertyMatchFromList("Name", "trigger", event.getChildren("P")).getValue();
				Event evt = new Event(name, scopeinfo, trigger,state);
				RunEnvironment.getmEventRegistry().RegistryEvent(evt);
				state.putEvent(name, evt);
				break;
			default:
				System.err.println("For now,Can not support data storage memory data type and paramater datatype.");
				System.exit(1);
				break;
			}
		}
	}
	
	private void ParseTransition(List<Element> transitions, State state)
	{
		for (Element trs : transitions)
		{
			String tSSID = trs.getAttributeValue("SSID");
			String labelstr = "";
			try {
				labelstr = SearchForPropertyMatchFromList("Name", "labelString", (trs.getChildren("P"))).getValue();
			} catch (Exception e) {
				//System.err.println("warn:label:"+tSSID+" has informal format.");
			}
			Transition tr = new Transition(tSSID, state);
			tr.setLabelString(labelstr);
			Element src = trs.getChild("src");
			Element temp = null;
			String srcSSID = (temp=SearchForPropertyMatchFromList("Name", "SSID", (src.getChildren("P")))) != null ? temp.getValue() : null;
			Element dst = trs.getChild("dst");
			String dstSSID = null;
			try {
				dstSSID = SearchForPropertyMatchFromList("Name", "SSID", (dst.getChildren("P"))).getValue();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("There exists one trsnsition that has no target.");
			}
			if (dstSSID == null)
			{
				System.err.println("dstSSID == null????");
				System.exit(1);
			}
			Element exeorderele = SearchForPropertyMatchFromList("Name", "executionOrder", (trs.getChildren("P")));
			int exeorder = 1;
			if (exeorderele != null)
			{
				exeorder = Integer.parseInt(exeorderele.getValue());
			}
			tr.setPriority(exeorder);
			if (srcSSID != null)
			{
				StateflowConnector srcstate = RunEnvironment.getmStateManager().getStateOrJunction(srcSSID);
				if (srcstate == null)
				{
					switch (metainfo.get(srcSSID)) {
					case StateType:
						srcstate = new State(srcSSID,srcSSID,-1,-1);
						RunEnvironment.getmStateManager().putState(srcSSID, (State)srcstate);
						break;
					case JunctionType:
						srcstate = new Junction(srcSSID,-1,-1);
						RunEnvironment.getmStateManager().putJunction(srcSSID, (Junction)srcstate);
						break;
					default:
						System.err.println("Serious error : unreconginize metainfo.");
						System.exit(1);
						break;
					}
				}
				srcstate = RunEnvironment.getmStateManager().getStateOrJunction(srcSSID);
				srcstate.addOutTransition(exeorder, tr);
				tr.setSourcestate(srcstate);
				tr.setfIsDefault(false);
			}
			else
			{
				tr.setfIsDefault(true);
				state.addDefaultTransition(tr);
			}
			assert dstSSID != null : "A transition must have at least one Dst State or junction.";
			if (dstSSID != null)
			{
				StateflowConnector dststate = RunEnvironment.getmStateManager().getStateOrJunction(dstSSID);
				if (dststate == null)
				{
					switch (metainfo.get(dstSSID)) {
					case StateType:
						dststate = new State(dstSSID,dstSSID,-1,-1);
						RunEnvironment.getmStateManager().putState(dstSSID, (State)dststate);
						break;
					case JunctionType:
						dststate = new Junction(srcSSID,-1,-1);
						RunEnvironment.getmStateManager().putJunction(dstSSID, (Junction)dststate);
						break;
					default:
						System.err.println("Serious error : unreconginize metainfo.");
						System.exit(1);
						break;
					}
				}
				tr.setTargetstate(dststate);
				dststate.addInTransition(tr);
			}
		}
	}
	
	private void ParseJunction(List<Element> junctions, State state) {
		for (Element junc : junctions)
		{
			String SSID = junc.getAttributeValue("SSID");
			String junctype = SearchForPropertyMatchFromList("Name", "type", junc.getChildren("P")).getValue();
			if (junctype.equals("HISTORY_JUNCTION"))
			{
				state.setfHistory(true);
				return;
			}
			if (!junctype.equals("CONNECTIVE_JUNCTION"))
			{
				System.err.println("Unrecongnized junction type:"+junctype);
				System.exit(1);					
			}
			Junction juncstate = RunEnvironment.getmStateManager().getJunction(SSID);
			int[] pos = GetPositionInfo(junc);
			if (juncstate == null)
			{
				juncstate = new Junction(SSID,pos[0],pos[1]);
			}
			else
			{
				juncstate.setPosx(pos[0]);
				juncstate.setPosy(pos[1]);
			}
			juncstate.setType(junctype);
			juncstate.setParent(state);
			RunEnvironment.getmStateManager().putJunction(SSID, juncstate);
			state.putJunction(SSID, juncstate);
		}
	}
	
	private int[] GetPositionInfo(Element sts)
	{
		String positionstr = SearchForPropertyMatchFromList("Name", "position", (sts.getChildren("P"))).getValue().trim();
		int leftpos = positionstr.indexOf('[')+1;
		int rightpos = positionstr.lastIndexOf(']');
		String posstr = positionstr.substring(leftpos, rightpos);
		String[] poss = posstr.split(" ");
		int[] result = new int[2];
		result[0] = (int)Double.parseDouble(poss[0]);
		result[1] = (int)Double.parseDouble(poss[1]);
		return result;
		
	}
	
	private void ParseOneState(Element onelevelstateinfo, State state)
	{
		if (onelevelstateinfo == null)
		{
			return;
		}
		//recurisive parse state.
		List<Element> states = onelevelstateinfo.getChildren("state");
		for (Element sts : states)
		{
			String SSID = sts.getAttribute("SSID").getValue();
			String labelstr = SearchForPropertyMatchFromList("Name", "labelString", (sts.getChildren("P"))).getValue();
			int[] pos = GetPositionInfo(sts);
			State stat = (State)RunEnvironment.getmStateManager().getStateOrJunction(SSID);
			
			if (stat == null)
			{
				stat = new State(SSID,SSID,pos[0],pos[1]);
				RunEnvironment.getmStateManager().putState(SSID, (State)stat);
			}
			else
			{
				stat.setPosx(pos[0]);
				stat.setPosy(pos[1]);
			}
			stat.setfLabelString(labelstr);
			
			Element priorityele = SearchForPropertyMatchFromList("Name", "executionOrder", (sts.getChildren("P")));
			if (priorityele != null)
			{
				stat.setPriority(Integer.parseInt(priorityele.getValue()));
			}
			String typestr = SearchForPropertyMatchFromList("Name", "type", (sts.getChildren("P"))).getValue();
			String childdecompstr = SearchForPropertyMatchFromList("Name", "decomposition", (sts.getChildren("P"))).getValue();
			switch (typestr) {
			case "FUNC_STATE":
				ParseFunction(sts, stat, state, SSID);
				break;
			case "GROUP_STATE":
				RunEnvironment.getmStateManager().putBox(SSID, new Box(SSID,stat));
			default:
				stat.setfChildDecomposition(GetDecompsition(childdecompstr));
				break;
			}
			//还有entry during exit等未处理
			state.putChildState(SSID,stat);
			stat.setParent(state);
			Element stschild = sts.getChild("Children");
			ParseOneState(stschild, stat);
		}
		//Parse junction.
		ParseJunction(onelevelstateinfo.getChildren("junction"), state);
		//Parse data.
		ParseDataList(onelevelstateinfo.getChildren("data"), state);
		//Parse event.
		ParseEventList(onelevelstateinfo.getChildren("event"), state);
		//Parse transition.
		ParseTransition(onelevelstateinfo.getChildren("transition"), state);
	}
	
	private boolean GetDecompsition(String childdecompstr)
	{
		switch (childdecompstr) {
		case "CLUSTER_CHART":
		case "CLUSTER_STATE":
			return State.ChildsSerial;
		case "SET_CHART":
		case "SET_STATE":
			return State.ChildsParallel;
		default:
			System.err.println("decomposition unreconginizable:"+childdecompstr+".");
			System.exit(1);
			break;
		}
		return false;
	}
	
	private void ExtactMetaInfoOfJunctionAndState(Element onelevelstateinfo)
	{
		if (onelevelstateinfo == null)
		{
			return;
		}
		//recurisive parse state.
		//extra junction meta info
		List<Element> junctions = onelevelstateinfo.getChildren("junction");
		for (Element junc : junctions)
		{
			String SSID = junc.getAttributeValue("SSID");
			metainfo.put(SSID, JunctionType);
		}
		List<Element> states = onelevelstateinfo.getChildren("state");
		//extract state meta info
		for (Element sts : states)
		{
			String SSID = sts.getAttribute("SSID").getValue();
			metainfo.put(SSID, StateType);
			//recursive process.
			Element stschild = sts.getChild("Children");
			ExtactMetaInfoOfJunctionAndState(stschild);
		}
	}

	private void ParseOneChart(Element chart, Chart sscht)
	{
		List<Element> cproperty = chart.getChildren("P");
		Element e = SearchForPropertyMatchFromList("Name", "name", cproperty);
		Element decomp = SearchForPropertyMatchFromList("Name", "decomposition", cproperty);
		sscht.setfChildDecomposition(GetDecompsition(decomp.getValue()));
		sscht.setName(e.getValue());
		sscht.setfLabelString(e.getValue());
		sscht.setSSID(RunEnvironment.getmStateManager().GetChartSSId(sscht)+"");
		sscht.setID(RunEnvironment.getmStateManager().GetChartSSId(sscht)+"");
		Element child = chart.getChild("Children");
		metainfo.clear();
		ExtactMetaInfoOfJunctionAndState(child);
		ParseOneState(child, sscht);
		RunEnvironment.getmStateManager().putState(sscht.getSSID(), sscht);
	}
	
	private void ExtractRunInfo(List<Element> modellist, SimulinkModel result)
	{
		for (Element model : modellist)
		{
			//should be only one model.
			List<Element> conflist = model.getChildren("ConfigurationSet");
			for (Element conf : conflist)
			{
				Element pritycontainer = conf.getChildren("Array").get(0).getChildren("Object").get(0).getChildren("Array").get(0).getChildren("Object").get(0);
				Element fixstep = SearchForPropertyMatchFromList("Name", "FixedStep", (pritycontainer.getChildren("P")));
				Element steptype = SearchForPropertyMatchFromList("Name", "Solver", (pritycontainer.getChildren("P")));
				Element starttimeele = SearchForPropertyMatchFromList("Name", "StartTime", (pritycontainer.getChildren("P")));
				Element stoptimeele = SearchForPropertyMatchFromList("Name", "StopTime", (pritycontainer.getChildren("P")));
				if (fixstep == null || steptype == null || !steptype.getValue().equals("FixedStepDiscrete"))
				{
					System.err.println("Unsupportted simulate type.Only support FixedStep and Discrete execytion type.");
					System.exit(1);
				}
				double timegapforbackup = 0;
				int timegap = -1;
				try {
					timegap = Integer.parseInt(fixstep.getValue());
				} catch (Exception e) {
					timegapforbackup = Double.parseDouble(fixstep.getValue());
					System.err.println("Warning:Only support integer simulation step.Double or float is not supportted.The simulation step time gap:"+steptype.getValue()+";error parse content:"+fixstep.getValue()+" is not supportted.");
					//System.exit(1);
				}
				int starttime = 0;
				int stoptime = 0;
				if (starttimeele == null || stoptimeele == null)
				{
					System.out.println("Serious error:no starttime and stoptime set???");
					System.exit(1);
				}
				starttime = (int) Double.parseDouble(starttimeele.getValue());
				stoptime = (int) Double.parseDouble(stoptimeele.getValue());
				result.setmTimeGap(timegap);
				result.setmStartTime(starttime);
				result.setmStopTime(stoptime);
				result.setmTimeGapForBackUp(timegapforbackup);
			}
		}
	}
	
	SimulinkModel mNowParsingSimulinkModel = null;

	public SimulinkModel ReadStateflowContent() throws Exception {
		//read release
		Document document = StateFlowReader.ReadDocument(fSourceFile);
		
		Element root = document.getRootElement();
		SimulinkModel result = new SimulinkModel("1");
		mNowParsingSimulinkModel = result;
		List<Element> modellist = root.getChildren("Model");
		ExtractRunInfo(modellist, result);
		List<Element> list = root.getChildren("Stateflow");
		//应该只有一个stateflow
		for (Element stateflow : list) {
			List<Element> maclist = (List<Element>) stateflow.getChildren("machine");
			for (Element machine : maclist)
			{
				//可能有几个machine?????????应该只有一个吧?????????????
				//<P Name="isLibrary">0</P>这个有用吗?????????
				List<Element> machinechildlist = (List<Element>) machine.getChildren("Children");
				for (Element macchild : machinechildlist)
				{
					//machine的Children应该也只有一个。????????????
					List<Element> chartlist = (List<Element>) macchild.getChildren("chart");
					for (Element chart : chartlist)
					{
						//machine的Children的chart应该也只有一个。????????????
						assert chart.getAttribute("id").getName().equals("id");
						String ID = chart.getAttribute("id").getValue();
						Chart cht = new Chart(ID,-1,-1);
						result.addStateflowChart(cht);
						ParseOneChart(chart, cht);
					}
				}
			}
		}
		if (mNowParsingSimulinkModel.getmTimeGap() == -1)
		{
			System.err.println("Serious error:We only support integer simulation time gap,not double type.now run time gap is:"+mNowParsingSimulinkModel.getmTimeGapForBackUp());
			System.exit(1);
		}
		RunEnvironment.getmStateManager().IntializeAllFullPath();
		
		//release resource
		StateFlowReader.ReleaseResource();
		return result;
	}

	public Map<String,Integer> getMetainfo() {
		return metainfo;
	}

	public void setMetainfo(Map<String,Integer> metainfo) {
		this.metainfo = metainfo;
	}

}