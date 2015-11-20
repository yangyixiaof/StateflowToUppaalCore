package PositionLibrary;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Uppaal.UppaalAutomaton;
import Uppaal.UppaalCordinateStaticData;
import Uppaal.UppaalNail;
import Uppaal.UppaalState;

public class StatesLinesPositionLibrary {
	
	static Map<String, Integer> mUppaalStateSelfCycleCount = new TreeMap<String, Integer>();
	static TreeMap<String, Integer> mTwoStateflowUpaalStateTransitionCount = new TreeMap<String, Integer>();
	//use ids, key : source_id#target_id value : transition count.
	
	private static ArrayList<UppaalNail> StateSelfCycleNails(UppaalState startus_cond, int transitiontype)
	{
		String uaname = startus_cond.getUa().getFullname();
		String fullname =uaname+"#"+startus_cond.getUppaalname()+"#"+transitiontype;
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		if (fullname != null && !fullname.equals(""))
		{
			// nail graph begin.
			Integer count = mUppaalStateSelfCycleCount.get(fullname);
			if (count == null)
			{
				count = 0;
			}
			count++;
			mUppaalStateSelfCycleCount.put(fullname,count);
			int scx = startus_cond.getX();
			int scy = startus_cond.getY();
			nails.add(new UppaalNail(scx-count*UppaalCordinateStaticData.belowxgap, scy+count*UppaalCordinateStaticData.belowygap));
			nails.add(new UppaalNail(scx+count*UppaalCordinateStaticData.belowxgap, scy+count*UppaalCordinateStaticData.belowygap));
			// nail graph end.
		}
		return nails;
	}
	
	private static ArrayList<UppaalNail> TwoStateTransitionNails(UppaalState startus_cond, UppaalState stop_condus, int transitiontype)
	{
		int count = IncreaseCount(startus_cond, stop_condus, 1, transitiontype);
		LinePositionLibrary.InitializeLineSeperateWithoutPredict(startus_cond.getX(), startus_cond.getY(), stop_condus.getX(), stop_condus.getY());
		return LinePositionLibrary.LineNextWithoutPredict(count);
	}
	
	public static ArrayList<UppaalNail> MakeChoiceBetweenAboveTwoFunction(UppaalState startus_cond, UppaalState stop_condus, int transitiontype)
	{
		if (startus_cond == stop_condus)
		{
			return StateSelfCycleNails(startus_cond, transitiontype);
		}
		else
		{
			return TwoStateTransitionNails(startus_cond, stop_condus, transitiontype);
		}
	}
	
	public static int IncreaseCount(UppaalState startus, UppaalState stop, int incnum, int transitiontype)
	{
		UppaalAutomaton ua = startus.getUa();
		String uaname = ua.getFullname();
		String key = uaname+"#"+startus+"#"+stop+"#"+transitiontype;
		String key2 = uaname+"#"+stop+"#"+startus+"#"+transitiontype;
		Integer count = mTwoStateflowUpaalStateTransitionCount.get(key);
		if (count == null)
		{
			count = 0;
		}
		count+=incnum;
		mTwoStateflowUpaalStateTransitionCount.put(key,count);
		mTwoStateflowUpaalStateTransitionCount.put(key2,count);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public static TreeMap<String,Integer> GetTransitionCountCopy()
	{
		return (TreeMap<String, Integer>) mTwoStateflowUpaalStateTransitionCount.clone();
	}
	
}