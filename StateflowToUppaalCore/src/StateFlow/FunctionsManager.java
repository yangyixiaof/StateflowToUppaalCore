package StateFlow;

import java.util.Map;
import java.util.TreeMap;

import StateflowStructure.StateflowFunction;

public class FunctionsManager {
	
	Map<String, StateflowFunction> sfuncmap = new TreeMap<String, StateflowFunction>();
	
	public void putFunction(String funcid, StateflowFunction func)
	{
		sfuncmap.put(funcid, func);
	}
	
	public StateflowFunction getFunction(String funcid)
	{
		return sfuncmap.get(funcid);
	}
	
}