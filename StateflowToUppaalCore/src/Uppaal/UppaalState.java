package Uppaal;

import java.util.ArrayList;

import TransferLogic.RunEnvironment;

public class UppaalState extends UppaalStructure
{
	private int ID = -1;
	private boolean isInitial = false;
	private boolean isUrgent = false;
	private boolean isCommitted = false;
	private ArrayList<UppaalTransition> uts = null;
	private int x;
	private int y;
	private boolean hastoini = false;
	private String uppaalname = null;
	private boolean newlycreated = false;
	private boolean setdeactivationfire = false;
	private UppaalAutomaton ua = null;
	
	public UppaalState(String name,String uppaalname,boolean isInitial,boolean isUrgent,boolean isCommitted,int id,UppaalAutomaton ua,int x,int y) {
		super(name);
		this.setUppaalname(uppaalname);
		uts = new ArrayList<UppaalTransition>();
		this.setInitial(isInitial);
		this.setUrgent(isUrgent);
		this.setCommitted(isCommitted);
		if (id == -1)
		{
			id = RunEnvironment.getmUppaalIDAllocator().AllocateID(this);
		}
		if (isInitial)
		{
			id = 0;
		}
		this.setID(id);
		this.setUa(ua);
		ua.AddUppaalState(this);
		setX(x);
		setY(y);
	}
	
	public void AddTransition(UppaalTransition ut)
	{
		uts.add(ut);
	}

	public boolean isInitial() {
		return isInitial;
	}

	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}

	public boolean isUrgent() {
		return isUrgent;
	}

	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public boolean isCommitted() {
		return isCommitted;
	}

	public void setCommitted(boolean isCommitted) {
		this.isCommitted = isCommitted;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ArrayList<UppaalTransition> getUts() {
		return uts;
	}

	public void setUts(ArrayList<UppaalTransition> uts) {
		this.uts = uts;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isHastoini() {
		return hastoini;
	}

	public void setHastoini(boolean hastoini) {
		this.hastoini = hastoini;
	}

	public String getUppaalname() {
		return uppaalname;
	}

	public void setUppaalname(String uppaalname) {
		this.uppaalname = uppaalname;
	}

	public boolean isNewlycreated() {
		return newlycreated;
	}

	public void setNewlycreated(boolean newlycreated) {
		this.newlycreated = newlycreated;
	}

	public boolean isSetdeactivationfire() {
		return setdeactivationfire;
	}

	public void setSetdeactivationfire(boolean setdeactivationfire) {
		this.setdeactivationfire = setdeactivationfire;
	}

	public UppaalAutomaton getUa() {
		return ua;
	}

	public void setUa(UppaalAutomaton ua) {
		this.ua = ua;
	}
}
