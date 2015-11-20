package Uppaal;

import java.util.ArrayList;

public class UppaalTransition extends UppaalStructure{
	
	private UppaalState sourcestate;
	private UppaalState targetstate;
	private String select = "";
	private String guard = "";
	private String sync = "";
	private String update = "";
	private ArrayList<UppaalNail> nails = null;
	private int typeoftransition = 0;
	
	public static final int DeactiveTransition = 5;
	public static final int DuringTransition = 6;
	public static final int ActiveTransition = 7;
	
	public UppaalTransition(String name,UppaalState sourcestate,UppaalState targetstate,String select,String guard,String sync,String update,UppaalAutomaton ua,ArrayList<UppaalNail> paranails) {
		super(name);
		this.sourcestate = sourcestate;
		sourcestate.AddTransition(this);
		this.targetstate = targetstate;
		this.select = select;
		this.guard = guard;
		this.sync = sync;
		this.update = update;
		ua.AddUppaalTransiton(this);
		setNails(paranails);
	}
	
	public boolean hasGuard()
	{
		return getGuard() != null && !getGuard().equals("");
	}
	
	public boolean hasUpdate()
	{
		return getUpdate() != null && !getUpdate().equals("");
	}

	public UppaalState getSourcestate() {
		return sourcestate;
	}

	public void setSourcestate(UppaalState sourcestate) {
		this.sourcestate = sourcestate;
	}

	public UppaalState getTargetstate() {
		return targetstate;
	}

	public void setTargetstate(UppaalState targetstate) {
		this.targetstate = targetstate;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getGuard() {
		return guard;
	}

	public void setGuard(String guard) {
		this.guard = guard;
	}

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public ArrayList<UppaalNail> getNails() {
		return nails;
	}

	public void setNails(ArrayList<UppaalNail> nails) {
		this.nails = nails;
	}

	public int getTypeoftransition() {
		return typeoftransition;
	}

	public void setTypeoftransition(int typeoftransition) {
		this.typeoftransition = typeoftransition;
	}
	
}
