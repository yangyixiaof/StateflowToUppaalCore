package Uppaal;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import StateflowStructure.Junction;
import StateflowStructure.State;
import TransferLogic.RunEnvironment;

public class UppaalAutomaton extends UppaalStructure{
	
	public static final int CONDITIONALUA = 0;
	public static final int COMMONUA = 1;
	public static final int CONTROLLERUA = 2;
	public static final int CONTROLLERACTIONUA = 3;
	
	private boolean hasChilds = false;
	private boolean hasThreeActions = false;
	private boolean isChart = false;
	private int type = -1;
	private String SSID = null;
	private String fullname = null;
	private UppaalDeclaration ud = null;
	private Map<String,UppaalState> usmap = new TreeMap<String, UppaalState>();
	private ArrayList<UppaalState> uss = null;
	private ArrayList<UppaalTransition> uts = null;
	
	public UppaalAutomaton(String name,boolean isChart,boolean hasChilds,boolean hasThreeActions)
	{
		super(name);
		this.isChart = isChart;
		this.setType(2);
		this.setFullname(name);
		setUppaalDeclaration(RunEnvironment.getmUppaalDeclarationManager().GetUppaalAutomatonDeclaration(name+"_declaration"));
		setUss(new ArrayList<UppaalState>());
		setUts(new ArrayList<UppaalTransition>());
		setSSID("0");
		setHasChilds(hasChilds);
		setHasThreeActions(hasThreeActions);
	}
	
	private void setSSID(String ssid) {
		SSID = ssid;
	}
	
	public UppaalAutomaton(Junction junction)
	{
		super("junc_"+junction.getID());
		this.isChart = false;
		String SSID = junction.getID();
		String fullname = junction.GetFullName();
		this.setFullname(fullname);
		this.setType(CONTROLLERUA);
		String name = "junc_"+junction.getID();
		setUppaalDeclaration(RunEnvironment.getmUppaalDeclarationManager().GetUppaalAutomatonDeclaration(name+"_declaration"));
		setUss(new ArrayList<UppaalState>());
		setUts(new ArrayList<UppaalTransition>());
		setSSID(SSID);
		setHasChilds(false);
		setHasThreeActions(false);
	}

	public UppaalAutomaton(State state,int type,boolean isChart,boolean hasChilds,boolean hasThreeActions) {
		super(state.GetFullName() + (type==0?"_conditional":type==1?"":type==2?"_controller":"_ctrlaction"));
		this.isChart = isChart;
		String SSID = state.getSSID();
		String fullname = state.GetFullName();
		this.setFullname(fullname);
		this.setType(type);
		String name = fullname + (type==0?"_conditional":type==1?"":type==2?"_controller":"_ctrlaction");
		setUppaalDeclaration(RunEnvironment.getmUppaalDeclarationManager().GetUppaalAutomatonDeclaration(name+"_declaration"));
		setUss(new ArrayList<UppaalState>());
		setUts(new ArrayList<UppaalTransition>());
		setSSID(SSID);
		setHasChilds(hasChilds);
		setHasThreeActions(hasThreeActions);
	}

	public void AddUppaalTransiton(UppaalTransition ut)
	{
		uts.add(ut);
	}
	
	public void AddUppaalState(UppaalState initialus) {
		getUss().add(initialus);
	}

	public ArrayList<UppaalState> getUss() {
		return uss;
	}

	public void setUss(ArrayList<UppaalState> uss) {
		this.uss = uss;
	}

	public UppaalDeclaration getUppaalDeclaration() {
		return ud;
	}

	public void setUppaalDeclaration(UppaalDeclaration ud) {
		this.ud = ud;
	}

	public ArrayList<UppaalTransition> getUts() {
		return uts;
	}

	public void setUts(ArrayList<UppaalTransition> uts) {
		this.uts = uts;
	}

	public String getSSID() {
		return SSID;
	}

	public void setID(String iD) {
		SSID = iD;
	}
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UppaalState GetUppaalState(String sname, String meaningname, boolean isInitial, boolean isUrgent,
			boolean isCommitted, int id, int x, int y) {
		UppaalState result = usmap.get(sname);
		if (result == null)
		{
			result = new UppaalState(sname, meaningname, isInitial, isUrgent, false, id, this, x, y);
			result.setNewlycreated(true);
			usmap.put(sname, result);
		}
		else
		{
			result.setNewlycreated(false);
		}
		return result;
	}

	public Map<String,UppaalState> getUsmap() {
		return usmap;
	}

	public void setUsmap(Map<String,UppaalState> usmap) {
		this.usmap = usmap;
	}

	public boolean isChart() {
		return isChart;
	}

	public void setChart(boolean isChart) {
		this.isChart = isChart;
	}
	
	public boolean isCommon()
	{
		return type == COMMONUA;
	}
	
	public boolean isController()
	{
		return type == CONTROLLERUA;
	}
	
	public boolean isControllerAction()
	{
		return type == CONTROLLERACTIONUA;
	}

	public boolean isHasChilds() {
		return hasChilds;
	}

	public void setHasChilds(boolean hasChilds) {
		this.hasChilds = hasChilds;
	}

	public boolean isHasThreeActions() {
		return hasThreeActions;
	}

	public void setHasThreeActions(boolean hasThreeActions) {
		this.hasThreeActions = hasThreeActions;
	}
	
}