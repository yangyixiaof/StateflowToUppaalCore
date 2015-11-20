package StateFlow;

import java.util.Map;
import java.util.TreeMap;

import StateflowStructure.Data;

public class DataManager {
	
	Map<String, Data> datamap = new TreeMap<String, Data>();
	
	public void putData(String name, Data data)
	{
		datamap.put(name, data);
	}
	
	public Data getData(String name)
	{
		return datamap.get(name);
	}
	
}