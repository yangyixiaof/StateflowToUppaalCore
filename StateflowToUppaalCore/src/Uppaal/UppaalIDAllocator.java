package Uppaal;

import java.util.Map;
import java.util.TreeMap;

public class UppaalIDAllocator {
	
	private int AllocId = 100000;
	private Map<String,Integer> mIDMaps = new TreeMap<String, Integer>();
	static UppaalIDAllocator mUppaalIDAllocator = null;
	
	public int AllocateID(UppaalState us)
	{
		String key = us.getName();
		if (!mIDMaps.containsKey(key))
		{
			AllocId--;
			mIDMaps.put(key, AllocId);
		}
		return mIDMaps.get(key);
	}
	
}