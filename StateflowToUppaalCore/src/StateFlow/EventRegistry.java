package StateFlow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import StateflowStructure.Event;
import StateflowStructure.State;
import StateflowStructure.StateflowConnector;
import TransferLogic.RunEnvironment;

public class EventRegistry {

	/*static int NSecEvtId = 5;
	static int NMSecEvtId = 6;
	static int NUSecEvtId = 7;*/
	int NMaxEvtId = 40;
	//NMaxEvtId is the driven event.
	Map<String, Event> emap = new TreeMap<String, Event>();
	Map<String, Integer> tmap = new TreeMap<String, Integer>();
	{
		tmap.put("sec", NMaxEvtId);
		//tmap.put("msec", NMSecEvtId);
		//tmap.put("usec", NUSecEvtId);
	}

	public State GetEvtContainerByFullName(String evt) {
		return emap.get(evt).getContainer();
	}

	public void RegistryEvent(Event evt) {
		State stat = evt.getContainer();
		String evtfullname = stat.GetFullName() + "_" + evt.getName();
		emap.put(evtfullname, evt);
	}

	// evt must be fullname.
	public int GetEvtId(String evtfullname) {
		if (!tmap.containsKey(evtfullname)) {
			NMaxEvtId++;
			tmap.put(evtfullname, NMaxEvtId);
			Event evt = emap.get(evtfullname);
			
			//testing
			/*if (evt == null)
			{
				System.err.println("evt null,and evt fullname is : "+evtfullname);
				System.exit(1);
			}*/
			
			evt.setId(NMaxEvtId);
		}
		return tmap.get(evtfullname);
	}

	// result[0] = event id;
	// result[1] = event dest id;
	// result[2] = event info;
	// evt can be not fullname.
	public int[] GetEvtId(String evt, State parentstate) {
		if (StateflowReservedKeywords.IsReservedKeyword(evt))
		{
			System.err.println(evt + "should not invoke this function.signature is 'public static int[] GetEvtId(String evt, State parentstate)'");
			System.exit(1);
		}
		
		int[] result = new int[3];
		State dest = null;
		String parentfullname = parentstate.GetFullName();
		String oneevt = null;
		String deststate = null;
		if (evt.startsWith("send(")) {
			String hs = evt.substring("send(".length());
			hs = hs.substring(0, hs.lastIndexOf(')'));
			String[] ts = hs.split(",");
			oneevt = ts[0];
			deststate = ts[1].replaceAll("\\.", "_");
			//testing
			/*if (deststate.equals("_"))
			{
				System.err.println(evt);
			}*/
		} else {
			oneevt = evt;
		}
		oneevt = oneevt.replaceAll("\\.", "_");
		Set<String> keys = emap.keySet();
		Event finalet = null;
		for (String evtfullname : keys) {
			Event et = emap.get(evtfullname);
			if (evtfullname.endsWith(oneevt)
					&& parentfullname.startsWith(et.getContainer()
							.GetFullName())) {
				finalet = et;
				break;
			}
		}
		if (finalet == null) {
			// testing code
			ArrayList<Event> evts = GetAllEventsAsArrayList();
			String allevtinfo = "";
			for (Event et : evts) {
				allevtinfo += et.getName()+";";
			}
			System.err.println(parentstate.GetFullName()+";Serious error:Unfound event.oneevt:" + oneevt
					+ ";evt:" + evt + ";allevtinfo:" + allevtinfo);
			new Exception().printStackTrace();
			System.exit(1);
		}
		dest = finalet.getContainer();
		if (deststate != null) {
			if (deststate.equals("")) {
				System.err.println("Serious error,wrong send(XX,XX) syntax.");
				System.exit(1);
			}
			dest = RunEnvironment.getmStateManager().getStateByLastStateName(deststate, parentfullname);
			//The event is dispatch to the dest's parent actually.
		}
		StateflowConnector destparent = dest.getParent();
		result[1] = RunEnvironment.getmStateManager().GetStateId((State)destparent);
		result[2] = RunEnvironment.getmStateManager().GetStateId((State)dest);
		if (destparent == null)
		{
			result[1] = RunEnvironment.getmStateManager().GetStateId((State)dest);
			result[2] = -1;
		}
		String evtfullname = finalet.getContainer().GetFullName() + "_"
				+ finalet.getName();
		if (!tmap.containsKey(evtfullname)) {
			NMaxEvtId++;
			tmap.put(evtfullname, NMaxEvtId);
			finalet.setId(NMaxEvtId);
		}
		result[0] = tmap.get(evtfullname);
		return result;
	}

	public ArrayList<Event> GetAllEventsAsArrayList() {
		return new ArrayList<Event>(emap.values());
	}
	
	public Map<String, Integer> GetAllEventsNameIdMap()
	{
		return tmap;
	}

}