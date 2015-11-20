package CommonLibrary;

import java.util.ArrayList;

import StateflowStructure.State;

public class ArrayHelper {
	
	public static void AddArray(ArrayList<State> parray, State obj)
	{
		parray.add(obj);
	}
	
	public static void SetArray(ArrayList<State> parray, int idx, State obj)
	{
		int length = parray.size();
		if (idx > length-1)
		{
			int expand = idx - length + 1;
			for (int i=0;i<expand;i++)
			{
				parray.add(null);
			}
			if (parray.get(idx) != null)
			{
				System.err.println("Same priority,may it be wrong?");
				System.exit(1);
			}
		}
		parray.set(idx, obj);
	}
	
	public static String[] ArrayListToArray(ArrayList<String> arrlist)
	{
		int len = arrlist.size();
		if (len == 0)
		{
			return null;
		}
		String[] result = new String[len];
		int i=0;
		for (i=0;i<len;i++)
		{
			result[i] = arrlist.get(i);
		}
		return result;
	}
	
}