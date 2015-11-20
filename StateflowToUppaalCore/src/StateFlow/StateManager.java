package StateFlow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import StateflowStructure.Box;
import StateflowStructure.Chart;
import StateflowStructure.Junction;
import StateflowStructure.State;
import StateflowStructure.StateflowConnector;

public class StateManager {
	
	Map<String, State> allstates = new TreeMap<String, State>();
	Map<String, State> fullnameindexstates = null;
	Map<String, String> allfullpathssids = new TreeMap<String, String>();
	Map<String, Junction> alljunctions = new TreeMap<String, Junction>();
	Map<String, Box> boxstates = new TreeMap<String, Box>();
	
	int NMaxStateId = 1;
	Map<String,Integer> chartmap = new TreeMap<String,Integer>();
	int mChartSSID = 0;
	Map<String,Integer> tmap = new TreeMap<String,Integer>();
	
	public int GetChartSSId(Chart chart)
	{
		String chartfullname = chart.getName();
		Integer SSID = chartmap.get(chartfullname);
		int issid = -1;
		if (SSID == null)
		{
			issid = --mChartSSID;
		}
		else
		{
			issid = SSID;
		}
		chartmap.put(chartfullname, issid);
		return issid;
	}
	
	public int GetStateId(StateflowConnector stat)
	{
		if (stat instanceof Chart)
		{
			String chart = GetChartSSId((Chart)stat)+"_chart";
			if (tmap.containsKey(chart))
			{
				return tmap.get(chart);
			}
			return 1;
		}
		if (stat == null)
		{
			return -1;
		}
		String state = stat.getID();
		if (stat instanceof State)
		{
			state += "_state";
		}
		if (stat instanceof Junction)
		{
			state += "_junction";
		}
		if (tmap.containsKey(state))
		{
			return tmap.get(state);
		}
		else
		{
			NMaxStateId++;
			tmap.put(state, NMaxStateId);
			return NMaxStateId;
		}
	}

	public void putFullPathAndSSID(String fullpath, String SSID)
	{
		allfullpathssids.put(fullpath, SSID);
	}
	
	public String getFullPathAndSSID(String fullpath)
	{
		return allfullpathssids.get(fullpath);
	}
	
	public void putState(String SSID, State state)
	{
		allstates.put(SSID, state);
	}
	
	public StateflowConnector getStateOrJunction(String SSID)
	{
		StateflowConnector sf = allstates.get(SSID);
		if (sf == null)
		{
			sf = alljunctions.get(SSID);
		}
		return sf;
	}
	
	public void putJunction(String SSID, Junction junc)
	{
		alljunctions.put(SSID, junc);
	}
	
	public Junction getJunction(String SSID)
	{
		return alljunctions.get(SSID);
	}
	
	public void putBox(String SSID, Box box)
	{
		boxstates.put(SSID, box);
	}
	
	public Box getBox(String SSID)
	{
		return boxstates.get(SSID);
	}
	
	/*private static boolean MatchPath(State state,String[] paths, int i, State[] targetstate)
	{
		boolean result = false;
		int len = paths.length;
		if (i == len)
		{
			return true;
		}
		Map<String, State> css = state.getStates();
		Set<String> keyset = css.keySet();
		for (String key : keyset)
		{
			State chdstate = css.get(key);
			boolean tmpres = MatchPath(chdstate, paths, i+1, targetstate);
			if (i == len-1 && tmpres)
			{
				targetstate[0] = chdstate;
			}
			result |= tmpres;
			if (result)
			{
				return true;
			}
		}
		return result;
	}*/

	public State SearchForState(String statepath) {
		String path = statepath.replaceAll("\\.", "_").trim();
		Set<String> keyset = allfullpathssids.keySet();
		for (String key : keyset)
		{
			String ts = allfullpathssids.get(key);
			if (key.endsWith(path))
			{
				return allstates.get(ts);
			}
		}
		return null;
	}

	public void IntializeAllFullPath() {
		Set<String> keyset = allstates.keySet();
		for (String key : keyset)
		{
			State stat = allstates.get(key);
			String SSID = stat.getSSID();
			String fullpath = stat.GetFullName();
			String fullpathssid = getFullPathAndSSID(fullpath);
			if (fullpathssid == null)
			{
				putFullPathAndSSID(fullpath, SSID);
			}
		}
	}
	
	public ArrayList<State> GetAllStatesAsArrayList()
	{
		return new ArrayList<State>(allstates.values());
	}
	
	public ArrayList<Junction> GetAllJunctionsAsArrayList()
	{
		return new ArrayList<Junction>(alljunctions.values());
	}
	
	private int PrefixCount(String one, String two)
	{
		int count = 0;
		int len = Math.min(one.length(), two.length());
		for (int i=0;i<len;i++)
		{
			if (one.charAt(i) == two.charAt(i))
			{
				count++;
			}
			else
			{
				break;
			}
		}
		return count;
	}

	public State getStateByLastStateName(String deststate, String parentfullname) {
		Set<String> fullpaths = allfullpathssids.keySet();
		ArrayList<String> candidates = new ArrayList<String>();
		for (String fullpath : fullpaths)
		{
			if (fullpath.endsWith(deststate))
			{
				candidates.add(fullpath);
			}
		}
		int len = candidates.size();
		int max = 0;
		int maxindex = -1;
		for (int i=0;i<len;i++)
		{
			int count = PrefixCount(candidates.get(i), parentfullname);
			if (max < count)
			{
				max = count;
				maxindex = i;
			}
		}
		if (maxindex == -1)
		{
			System.err.println("deststate : "+deststate);
			System.err.println("Serious error : no such state in send(XX,XX)'s second parameter.");
			System.exit(1);
		}
		return allstates.get(allfullpathssids.get(candidates.get(maxindex)));
	}
	
	private void GeneratefullnameIndexStatesMap()
	{
		Set<String> keys = allstates.keySet();
		for (String key : keys)
		{
			State state = allstates.get(key);
			String sfullname = state.GetFullName();
			fullnameindexstates.put(sfullname, state);
		}
	}

	public State getStateByFullStateName(String statefullname) {
		if (fullnameindexstates == null)
		{
			fullnameindexstates = new TreeMap<String, State>();
			GeneratefullnameIndexStatesMap();			
		}
		return fullnameindexstates.get(statefullname);
	}
	
}