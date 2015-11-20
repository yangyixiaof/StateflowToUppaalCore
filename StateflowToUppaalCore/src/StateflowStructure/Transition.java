package StateflowStructure;

public class Transition extends StateflowStructure{

	private boolean fIsDefault = false;
	private StateflowConnector sourcestate = null;
	private StateflowConnector targetstate = null;
	private String labelString = null;
	private int priority = -1;
	private State container = null;
	private String[] fourparts = new String[4];
	private int pathid = -1;
	
	public Transition(String ID, State paracontainer) {
		super(ID);
		this.setContainer(paracontainer);
		paracontainer.AddChildTranstion(this);
	}

	public boolean isfIsDefault() {
		return fIsDefault;
	}

	public void setfIsDefault(boolean fIsDefault) {
		this.fIsDefault = fIsDefault;
	}

	public StateflowConnector getSourcestate() {
		return sourcestate;
	}

	public void setSourcestate(StateflowConnector sourcestate) {
		this.sourcestate = sourcestate;
	}

	public StateflowConnector getTargetstate() {
		return targetstate;
	}

	public void setTargetstate(StateflowConnector targetstate) {
		this.targetstate = targetstate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getLabelString() {
		return labelString;
	}

	public void setLabelString(String labelString) {
		this.labelString = labelString;
	}

	public State getContainer() {
		return container;
	}

	public void setContainer(State container) {
		this.container = container;
	}

	public String[] getFourparts() {
		return fourparts;
	}

	public void setFourparts(String[] fourparts) {
		this.fourparts = fourparts;
	}

	public boolean HasLabel() {
		if (labelString == null || labelString.equals(""))
		{
			return false;
		}
		return true;
	}

	public int getPathid() {
		return pathid;
	}

	public void setPathid(int pathid) {
		this.pathid = pathid;
	}
	
}
