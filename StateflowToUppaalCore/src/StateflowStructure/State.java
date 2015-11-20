package StateflowStructure;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import CommonLibrary.ArrayHelper;

public class State extends StateflowConnector {

	public static final boolean ChildsSerial = false;
	public static final boolean ChildsParallel = true;

	//static String prefix = null;
	String SSID = null;
	String fLabelString = null;
	String fLabelName = null;
	boolean fChildDecomposition = ChildsSerial;
	private boolean fHistory = false;

	String entry = "";
	String during = "";
	String exit = "";

	int priority = -1;

	ArrayList<Transition> defaulttransitions = new ArrayList<Transition>();
	ArrayList<Transition> alltransitions = new ArrayList<Transition>();

	Map<String, Box> boxes = new TreeMap<String, Box>();
	Map<String, StateflowFunction> functions = new TreeMap<String, StateflowFunction>();
	Map<String, State> states = new TreeMap<String, State>();
	Map<String, Junction> junctions = new TreeMap<String, Junction>();

	Map<String, Data> datas = new TreeMap<String, Data>();
	Map<String, Event> events = new TreeMap<String, Event>();
	
	MockedState mMockedInitialState = null;
	
	int mCountMax = -1;
	Map<String,Integer> mCountEvent = new TreeMap<String,Integer>();

	public State(String ID,int x,int y) {
		super(ID,x,y);
	}

	public State(String ID, String SSID,int x,int y) {
		super(ID,x,y);
		this.SSID = SSID;
	}
	
	public boolean HasCountEvent()
	{
		return mCountMax >= 0;
	}
	
	public int GetEvtCount()
	{
		return mCountMax+1;
	}

	public String getfLabelName() {
		return fLabelName;
	}

	public void setfLabelString(String fLabelString) {
		this.fLabelString = fLabelString;
		String[] flss = fLabelString.split("\n");
		fLabelName = flss[0];
		int len = flss.length;
		for (int i=1;i<len;i++)
		{
			String fs = flss[i];
			String[] typeandaction = fs.split(":");
			switch (typeandaction[0]) {
			case "entry":
				entry = typeandaction[1];
				break;
			case "during":
				during = typeandaction[1];
				break;
			case "exit":
				exit = typeandaction[1];
				break;
			default:
				System.err.println("unrecoginized type,.please check three actions you write.");
				System.exit(1);
				break;
			}
		}
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public void putChildState(String SSID, State tempstate) {
		getStates().put(SSID, tempstate);
	}

	public State getChildState(String SSID) {
		return getStates().get(SSID);
	}

	public State getState(String SSID) {
		State srcsts = getStates().get(SSID);
		if (srcsts == null) {
			if (SSID.equals(this.SSID)) {
				srcsts = this;
			}
		}
		return srcsts;
	}

	public boolean isfChildDecomposition() {
		return fChildDecomposition;
	}

	public void setfChildDecomposition(boolean fChildDecomposition) {
		this.fChildDecomposition = fChildDecomposition;
	}

	public void addStateflowFunction(String SSID, StateflowFunction tt) {
		functions.put(SSID, tt);
	}

	public void putData(String name, Data data) {
		datas.put(name, data);
	}

	public Data getData(String name) {
		return datas.get(name);
	}

	public void putEvent(String name, Event evt) {
		events.put(name, evt);
	}

	public Event getEvent(String name) {
		return events.get(name);
	}

	public void putJunction(String SSID, Junction junc) {
		getJunctions().put(SSID, junc);
	}

	public Junction getJunction(String SSID) {
		return getJunctions().get(SSID);
	}

	public boolean hasChildStates() {
		return getStates().size() > 0 || getJunctions().size() > 0;
	}

	public Map<String, State> getStates() {
		return states;
	}

	public void setStates(Map<String, State> states) {
		this.states = states;
	}

	public ArrayList<Transition> getDefaulttransitions() {
		return defaulttransitions;
	}

	public void setDefaulttransitions(ArrayList<Transition> defaulttransitions) {
		this.defaulttransitions = defaulttransitions;
	}

	public void addDefaultTransition(Transition tr) {
		this.defaulttransitions.add(tr);
	}

	public int getPriority() {
		return priority;
	}

	public boolean HasThreeActions() {
		if ((HasEntry()) || (HasDuring()) || (HasExit())) {
			return true;
		}
		return false;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean HasEntry() {
		if (getEntry() != null && !getEntry().equals("")) {
			return true;
		}
		return false;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public boolean HasDuring() {
		if (getDuring() != null && !getDuring().equals("")) {
			return true;
		}
		return false;
	}

	public String getDuring() {
		return during;
	}

	public void setDuring(String during) {
		this.during = during;
	}

	public boolean HasExit() {
		if (getExit() != null && !getExit().equals("")) {
			return true;
		}
		return false;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	ArrayList<State> mOrderedStates = null;

	public ArrayList<State> getOrderedStates() {
		if (mOrderedStates == null) {
			mOrderedStates = new ArrayList<State>();
			Set<String> keys = states.keySet();
			for (String key : keys) {
				State ks = states.get(key);
				if (isfChildDecomposition() == ChildsParallel)
				{
					int priority = ks.getPriority();
					ArrayHelper.SetArray(mOrderedStates, priority-1, ks);
				}
				else
				{
					ArrayHelper.AddArray(mOrderedStates, ks);
				}
			}
		}
		return mOrderedStates;
	}
	
	public State getFirstPriorityChildState() {
		ArrayList<State> orderedstates = getOrderedStates();
		return orderedstates.get(0);
	}

	public State getLastPriorityChildState() {
		ArrayList<State> orderedstates = getOrderedStates();
		int len = orderedstates.size();
		return orderedstates.get(len - 1);
	}

	public State getNextHigherPriorityChildState(State state) {
		// higher means value slower.
		int priority = state.getPriority();
		priority--;
		ArrayList<State> orderedstates = getOrderedStates();
		if (priority < 1 || orderedstates.get(priority-1) == null) {
			return null;
		}
		return orderedstates.get(priority-1);
	}

	//only invoked valid by parallel child decomposition.
	public State getNextSlowerPriorityChildState(State state) {
		if (isfChildDecomposition() == ChildsSerial)
		{
			return null;
		}
		// slower means value higher.
		int priority = state.getPriority();
		priority++;
		ArrayList<State> orderedstates = getOrderedStates();
		int len = orderedstates.size();
		if (priority > len || orderedstates.get(priority-1) == null) {
			return null;
		}
		return orderedstates.get(priority-1);
	}

	public boolean isfHistory() {
		return fHistory;
	}

	public void setfHistory(boolean fHistory) {
		this.fHistory = fHistory;
	}

	public boolean ContainsData(String idnt) {
		if (datas.containsKey(idnt)) {
			return true;
		}
		return false;
	}

	public String getDataName(String idnt) {
		assert datas.containsKey(idnt) : "Serious error:this state doesn't contain data "
				+ idnt + " .";
		/*if (prefix == null) {
			State tmpstate = (State) this.getParent();
			ArrayList<String> slist = new ArrayList<String>();
			slist.add(getfLabelName());
			while (tmpstate != null) {
				tmpstate = (State) tmpstate.getParent();
				slist.add(tmpstate.getfLabelName());
			}
			StringBuffer tmpprefix = new StringBuffer("");
			int i = 0;
			int len = slist.size();
			for (i = len - 1; i >= 0; i--) {
				tmpprefix.append(slist.get(i));
			}
			prefix = tmpprefix.toString();
		}*/
		return GetFullName() + "_" + idnt;
	}

	public Map<String, Data> getDatas() {
		return datas;
	}

	public boolean ContainsEvent(String idnt) {
		if (events.containsKey(idnt)) {
			return true;
		}
		return false;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public ArrayList<Data> GetDatasAsArrayList()
	{
		return new ArrayList<Data>(datas.values());
	}
	
	public void AddCountEventMap(ArrayList<String> countEventSet)
	{
		for (String key : countEventSet)
		{
			if (!mCountEvent.containsKey(key))
			{
				mCountMax++;
				mCountEvent.put(key, mCountMax);
			}
		}
	}

	public Map<String,Integer> getmCountEvent() {
		return mCountEvent;
	}

	public void setmCountEvent(Map<String,Integer> mCountEvent) {
		this.mCountEvent = mCountEvent;
	}

	public MockedState GetmMockedInitialState() {
		if (mMockedInitialState == null)
		{
			mMockedInitialState = new MockedState("-1", -1, -1, this);
			mMockedInitialState.addOutTransitionAll(defaulttransitions);
		}
		return mMockedInitialState;
	}

	protected void addOutTransitionAll(ArrayList<Transition> defaulttransitions2) {
		int i=0;
		int len=defaulttransitions2.size();
		for (i=0;i<len;i++)
		{
			Transition tr = defaulttransitions2.get(i);
			int priority = tr.getPriority();
			this.addOutTransition(priority, tr);
		}
	}

	public ArrayList<Transition> getAllChildTransitions() {
		return alltransitions;
	}
	
	public void AddChildTranstion(Transition transition)
	{
		alltransitions.add(transition);
	}
	
	public String GetFullName() {
		if (mFullName == null) {
			String prefix = "";
			if (HasParent()) {
				prefix = ((State) getParent()).GetFullName() + "_";
			}
			mFullName = prefix + getfLabelName();
		}
		return mFullName;
	}

	public Map<String, Junction> getJunctions() {
		return junctions;
	}

	public void setJunctions(Map<String, Junction> junctions) {
		this.junctions = junctions;
	}
	
}