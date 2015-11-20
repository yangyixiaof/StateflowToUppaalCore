package Uppaal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import CommonLibrary.HTMLEscapeHelper;
import CommonLibrary.SimpleParserHelper;
import PositionLibrary.StatesLinesPositionLibrary;
import StateflowStructure.Data;
import StateflowStructure.Event;
import StateflowStructure.State;
import TransferLogic.RunEnvironment;

public class UppaalController {

	String fTargetFile = "";
	
	Map<String, Integer> mTwoStatesTransitionCount = null;
	Map<String, Integer> mTwoStatesTransitionAverageCount = new TreeMap<String, Integer>();
	
	public int DecreaseTransitionCount(UppaalState source, UppaalState target, UppaalTransition transition)
	{
		String uaname = source.getUa().getFullname();
		String key = uaname+"#"+source.getID()+"#"+target.getID()+"#"+transition.getTypeoftransition();
		Integer count = mTwoStatesTransitionCount.get(key);
		if (count == null)
		{
			count = 0;
		}
		int val = count;
		count--;
		mTwoStatesTransitionCount.put(key,count);
		return val;
	}
	
	public int GetAveCount(UppaalState source, UppaalState target, UppaalTransition transition) {
		String uaname = source.getUa().getFullname();
		String key = uaname+"#"+source.getID()+"#"+target.getID()+"#"+transition.getTypeoftransition();
		Integer avecount = mTwoStatesTransitionAverageCount.get(key);
		if (avecount == null)
		{
			avecount = 0;
		}
		return avecount;
	}
	
	private void ComputeAverage()
	{
		Set<String> keys = mTwoStatesTransitionCount.keySet();
		for (String key : keys)
		{
			int count = mTwoStatesTransitionCount.get(key);
			mTwoStatesTransitionAverageCount.put(key, count/2);
		}
	}

	public UppaalController(String targetfile) {
		fTargetFile = targetfile;
	}
	
	private void WriteFileContentToBufferWriter(String filename,BufferedWriter bw)
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(UppaalController.class.getClassLoader().getResourceAsStream("Uppaal/"+filename)));
			String line = null;
			while ((line = br.readLine()) != null)
			{
				bw.write(line);
				bw.newLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WriteUppaalContent(UppaalModel uppaalModel) throws Exception {
		mTwoStatesTransitionCount = StatesLinesPositionLibrary.GetTransitionCountCopy();
		ComputeAverage();
		File f = null;
		if (fTargetFile == null || fTargetFile.equals("")) {
			fTargetFile = "GTmpTargetUppaal.xml";
		}
		f = new File(fTargetFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		// first all general declaration.
		int i = 0;
		ArrayList<UppaalAutomaton> uas = uppaalModel.getUppaalAutomatons();
		int len = uas.size();

		bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		bw.write("<!DOCTYPE nta PUBLIC '-//Uppaal Team//DTD Flat System 1.1//EN' 'http://www.it.uu.se/research/group/darts/uppaal/flat-1_2.dtd'>\n");
		bw.write("<nta>\n");
		bw.write("<declaration>// Place global declarations here.\n");

		WriteFileContentToBufferWriter("AllDeclarationVariable",bw);
		
		bw.write("\n// history state used both by runner and controller.\n");
		for (i = 0; i < len; i++) {
			UppaalAutomaton ua = uas.get(i);
			if (ua.isCommon())
			{
				bw.write("int m" + ua.getFullname() + "HistoryState = -1;\n");
			}
		}
		bw.write("\n// state child active used both by runner and controller.\n");
		for (i = 0; i < len; i++) {
			UppaalAutomaton ua = uas.get(i);
			if (ua.isCommon())
			{
				bw.write("bool m" + ua.getFullname() + "ChildActive = false;\n");
			}
		}
		bw.write("\n// state childs if exist a valid path used both by runner and controller.\n");
		for (i = 0; i < len; i++) {
			UppaalAutomaton ua = uas.get(i);
			if (ua.isCommon())
			{
				bw.write("bool m" + ua.getFullname() + "ExistPath = false;\n");
			}
		}
		bw.write("\n// state common automaton if lose control.\n");
		for (i = 0; i < len; i++) {
			UppaalAutomaton ua = uas.get(i);
			if (ua.isCommon())
			{
				bw.write("bool m" + ua.getFullname() + "LoseControl = false;\n");
			}
		}
		
		bw.write("\n\n// ---------------------Print state id.e.g.Iterate StateManager.---------------------\n");
		ArrayList<State> statearrays = RunEnvironment.getmStateManager().GetAllStatesAsArrayList();
		int statelen = statearrays.size();
		for (i = 0; i < statelen; i++) {
			State state = statearrays.get(i);
			bw.write("//"+state.GetFullName()+"'s id="+RunEnvironment.getmStateManager().GetStateId(state)+"\n");
		}
		
		bw.write("\n\n// ---------------------Print modified data.e.g.Iterate StateManager.---------------------\n");
		bw.write("// this is just a overview.\n");
		for (i = 0; i < statelen; i++) {
			State stat = statearrays.get(i);
			if (stat instanceof State)
			{
				ArrayList<Data> datas = stat.GetDatasAsArrayList();
				String stfullname = stat.GetFullName();
				int dlen=datas.size();
				for (int j=0;j<dlen;j++)
				{
					Data data = datas.get(j);
					String dataname = stfullname + "_" + data.getName();
					String sizes = data.getSizes();
					boolean onlyone = true;
					int length = 1;
					if (!(sizes == null || sizes.equals("")))
					{
						if (SimpleParserHelper.IsNumber(sizes))
						{
							length = Integer.parseInt(sizes);
							if (length <= 0)
							{
								length = 1;
							}
						}
						else
						{
							if (sizes.startsWith("[") && sizes.endsWith("]"))
							{
								String realsizes = sizes.substring(1, sizes.length()-1);
								String[] ss = realsizes.split(" ");
								int x = 1;
								int zlen = ss.length;
								for (int z=0;z<zlen;z++)
								{
									int zv = Integer.parseInt(ss[z]);
									if (zv > 1)
									{
										if (x == 1)
										{
											x = zv;
										}
										else
										{
											System.err.println("Serious Error:two dimension or larger which we don't support.");
											System.exit(1);
										}
									}
								}
								length = x;
							}
							else
							{
								System.err.println("Unrecongnized sizes structure.dataname:"+dataname+";sizes:"+sizes);
								System.exit(1);
							}
						}
						if (length > 1)
						{
							onlyone = false;
						}
					}
					bw.write("//"+data.getDatatypestr()+" "+dataname + (!onlyone ? "["+length+"]" : "") + (onlyone && data.HasInitValue() ? (" = "+data.getInivalue()) : "")+";\n");
				}
			}
		}
		
		bw.write("\n\n// ---------------------Print modified event.e.g.Iterate EventRegistry.---------------------\n");
		bw.write("//value -1 means unused.\n");
		ArrayList<Event> eventarrays = RunEnvironment.getmEventRegistry().GetAllEventsAsArrayList();
		int evtlen = eventarrays.size();
		for (i=0;i<evtlen;i++)
		{
			Event evt = eventarrays.get(i);
			bw.write("//"+evt.getName()+"'s id="+evt.getId()+"\n");
		}
		bw.write("\n");
		
		bw.write("// ---------------------Print only used event.e.g.Iterate EventRegistry.---------------------\n");
		bw.write("//unappeared event means unused.\n");
		Map<String, Integer> eventmap = RunEnvironment.getmEventRegistry().GetAllEventsNameIdMap();
		Set<String> keys = eventmap.keySet();
		for (String key : keys)
		{
			bw.write("//"+key+"'s id="+eventmap.get(key)+"\n");
		}
		bw.write("\n");
		
		PrintDeclaration(bw,
				RunEnvironment.getmUppaalDeclarationManager().GetUppaalOverAllDeclaration());

		bw.write("\n");
		bw.write("\n");
		
		WriteFileContentToBufferWriter("AllDeclarationFunction",bw);
		bw.write("</declaration>\n");
		
		bw.write("\n");

		for (i = 0; i < len; i++) {
			UppaalAutomaton ua = uas.get(i);
			bw.write("<template>\n");
			bw.write("<name>" + ua.getName() + "</name>\n");
			bw.write("<declaration>// Place local declarations here.\n");
			PrintDeclaration(bw, ua.getUppaalDeclaration());
			if (ua.isController())
			{
				String activestatus = "mStatusDeActive";
				/*if (ua.isChart())
				{
					activestatus = "mStatusActive";
				}*/
				bw.write("int mRunStatus = "+activestatus+";\n");
			}
			//if chart , the initial status is active.
			switch (ua.getType()) {
			case UppaalAutomaton.CONTROLLERUA:
				if (!(ua.getSSID().equals("0")) && (!ua.isHasChilds() && ua.isHasThreeActions()))
				{
					bw.write("bool JHV()\n"
							+ "{\n"
							+  "    return false;\n"
							+ "}\n"
							+ "\n"
							+ "int GHS()\n"
							+ "{\n"
							+ "    return -1;\n"
							+ "}\n"
							+ "\n"
							+ "bool IsLoseControl()\n"
							+ "{\n"
							+ "    return false;\n"
							+ "}\n");
				}
				if (!(ua.getSSID().equals("0")) && (ua.isHasChilds()))
				{
					bw.write("bool JHV()\n" + "{\n" + "    return m"
							+ ua.getFullname()
							+ "HistoryState != -1;\n"
							+ "}\n"
							+ "\n"
							+ "int GHS()\n"
							+ "{\n"
							+ "    return m"
							+ ua.getFullname()
							+ "HistoryState;\n"
							+ "}\n"
							+ "\n"
							+ "bool IsLoseControl()\n"
							+ "{\n"
							+ "    return m"
							+ ua.getFullname()
							+ "LoseControl;\n"
							+ "}\n"
							+ "\n");
				}
				else
				{
					bw.write("\n");
				}
				WriteFileContentToBufferWriter("ControllerDeclarationFunction",bw);
				break;
			case UppaalAutomaton.COMMONUA:
				WriteFileContentToBufferWriter("CommonDeclarationFunction",bw);
				break;
			}
			bw.write("</declaration>\n");
			// draw automaton begin
			DrawUppaalStates(bw, ua);
			DrawUppaalTransition(bw, ua);
			// draw automaton end
			bw.write("</template>\n");
		}
		bw.write("<system>// Place template instantiations here.\n");
		StringBuffer pcs = new StringBuffer("");
		StringBuffer pss = new StringBuffer("system ");
		for (i = 0; i < len; i++) {
			UppaalAutomaton ua = uas.get(i);
			pcs.append("Process_" + ua.getName() + " = " + ua.getName() + "();\n");
			if (i == len - 1) {
				pss.append("Process_" + ua.getName() + ";\n");
			} else {
				pss.append("Process_" + ua.getName() + ",");
			}
		}
		bw.write(pcs.toString());
		bw.write(pss.toString());
		// List one or more processes to be composed into a system.
		bw.write("</system>\n");
		bw.write("<queries>\n");
		bw.write("</queries>\n");
		bw.write("</nta>\n");
		bw.flush();
		bw.close();
	}

	private void DrawUppaalTransition(BufferedWriter bw, UppaalAutomaton ua)
			throws Exception {
		ArrayList<UppaalTransition> uts = ua.getUts();
		int ui = 0;
		int ulen = uts.size();
		double  minirate = 1;
		for (ui = 0; ui < ulen; ui++) {
			UppaalTransition ut = uts.get(ui);
			UppaalState source = ut.getSourcestate();
			UppaalState target = ut.getTargetstate();
			bw.write("<transition>\n");
			bw.write("    <source ref=\"" + source.getID() + "\"/>\n");
			bw.write("    <target ref=\"" + target.getID() + "\"/>\n");
			ArrayList<UppaalNail> nails = ut.getNails();
			int sx = 0;
			int sy = 0;
			int count = DecreaseTransitionCount(source, target, ut);
			int avecount = GetAveCount(source, target, ut);
			if (avecount == 0)
			{
				avecount = -1;
			}
			if (nails != null && nails.size() > 0)
			{
				UppaalNail in = nails.get((0+nails.size()/2));
				sx = in.getX();
				sy = in.getY();
			}
			else
			{
				sx = (source.getX()+target.getX())/2;
				sy = (source.getY()+target.getY())/2;
			}
			double gapt = count-avecount;
			if (gapt >= 0)
			{
				gapt++;
			}
			if (ut.getTypeoftransition() == UppaalTransition.DeactiveTransition)
			{
				gapt += 3;
			}
			if (ut.getTypeoftransition() == UppaalTransition.ActiveTransition)
			{
				gapt += 3;
			}
			if (ut.getTypeoftransition() == UppaalTransition.DuringTransition)
			{
				gapt = -2;
			}
			if (ua.getName().equals("all_controller"))
			{
				gapt = 0;
			}
			if (ut.hasGuard()) {
				bw.write("    <label kind=\"guard\" x=\""+(sx+(int)((gapt/minirate*1.0)*UppaalCordinateStaticData.transitionguardxgap))+"\" y=\""+(sy+(int)((gapt/minirate*1.0)*UppaalCordinateStaticData.transitionguardygap))+"\">"
						+ HTMLEscapeHelper.CommonCharToHTMLEscape(ut.getGuard())
						+ "</label>\n");
			}
			if (ut.hasUpdate()) {
				bw.write("    <label kind=\"assignment\" x=\""+(sx+(int)((gapt/minirate*1.0)*UppaalCordinateStaticData.transitionguardxgap))+"\" y=\""+(sy+(int)((gapt/minirate*1.0)*UppaalCordinateStaticData.transitionguardygap)+UppaalCordinateStaticData.transitionactionybelowguard)+"\">"
						+ HTMLEscapeHelper.CommonCharToHTMLEscape(ut
								.getUpdate()) + "</label>\n");
			}
			if (nails != null)
			{
				int len = nails.size();
				for (int i=0;i<len;i++)
				{
					UppaalNail un = nails.get(i);
					bw.write("    <nail x=\""+un.getX()+"\" y=\""+un.getY()+"\"/>\n");
				}
			}
			bw.write("</transition>\n");
		}
	}

	private void DrawUppaalStates(BufferedWriter bw, UppaalAutomaton ua)
			throws Exception {
		ArrayList<UppaalState> uss = ua.getUss();
		int ui = 0;
		int ulen = uss.size();
		UppaalState inistate = null;
		for (ui = 0; ui < ulen; ui++) {
			UppaalState us = uss.get(ui);
			bw.write("<location id=\"" + us.getID() + "\" x = \""+us.getX()+"\" y = \""+us.getY()+"\">\n");
			if (us.isInitial()) {
				inistate = us;
			}
			if (us.getUppaalname() != null && !us.getUppaalname().equals("")) {
				String usname = us.getUppaalname();
				if (SimpleParserHelper.IsNumber(us.getUppaalname()))
				{
					usname = "SSID" + usname;
				}
				bw.write("    <name x=\""+(us.getX()-10)+"\" y=\""+(us.getY()-15)+"\">"+usname+"</name>\n");
			}
			if (us.isUrgent()) {
				bw.write("    <urgent/>\n");
			}
			bw.write("</location>\n");
		}
		try {
			bw.write("<init ref=\"" + inistate.getID() + "\"/>\n");
		} catch (Exception e) {
			System.out.println(ua.getFullname());
			System.out.println(ua.isChart());
			System.out.println(ua.getType());
			System.exit(1);
		}
	}

	private void PrintDeclaration(BufferedWriter bw, UppaalDeclaration ud)
			throws Exception {
		Map<String, String> vars = ud.getVariables();
		Set<String> sets = vars.keySet();
		for (String varname : sets) {
			String type = vars.get(varname);
			if (type.equals(UppaalDeclaration.IntArrayDeclare)) {
				bw.write("int " + varname + "[MaxArraySize];\n");
			} else {
				if (type.startsWith("ST:")) {
					String[] typeaini = type.split(":");
					bw.write(typeaini[1] + " " + varname + " = " + typeaini[2]
							+ ";\n");
				} else {
					if (type.startsWith("STA:")) {
						String[] typeaini = type.split(":");
						bw.write(typeaini[2] + " " + varname + "["
								+ typeaini[1] + "];\n");
					} else {
						if (type.startsWith("ArraySIZE:"))
						{
							String tmp = type.substring("ArraySIZE:".length());
							String[] tmps = tmp.split(":");
							bw.write(tmps[0] + " " + varname + "[" + tmps[1] + "];\n");
						}
						else
						{
							String[] typeandinival = type.split(":");
							bw.write(typeandinival[0] + " " + varname + (typeandinival.length > 1 ? " = "+typeandinival[1] : "") + ";\n");
						}
					}
				}
			}
		}
		Map<String, String> funcs = ud.getFunctions();
		Set<String> funcset = funcs.keySet();
		for (String funckey : funcset)
		{
			String funcval = funcs.get(funckey);
			bw.write(funcval);
		}
	}

}