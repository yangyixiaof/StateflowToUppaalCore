package StateflowStructure;

import java.util.ArrayList;

public abstract class StateflowConnector extends StateflowStructure{

	ArrayList<Transition> intransition = new ArrayList<Transition>();//no include defaulttransitions
	ArrayList<Transition> outtransition = new ArrayList<Transition>();//no include defaulttransitions
	int posx;
	int posy;
	String mFullName = null;
	
	private StateflowConnector parent = null;
	
	public StateflowConnector(String ID,int x,int y) {
		super(ID);
		this.setPosx(x);
		this.setPosy(y);
	}
	
	public abstract String GetFullName();
	
	public void addOutTransition(int exeorder, Transition trs)
	{
		getOuttransition().add(trs);
		/*int len = getOuttransition().size();
		if (exeorder >= len)
		{
			int itimes = exeorder-len+1;
			for (int i=0;i<itimes;i++)
			{
				getOuttransition().add(null);
			}
		}
		getOuttransition().set(exeorder, trs);*/
	}

	public void addInTransition(Transition tr) {
		getIntransition().add(tr);
	}

	public ArrayList<Transition> getOuttransition() {
		return outtransition;
	}

	public void setOuttransition(ArrayList<Transition> outtransition) {
		this.outtransition = outtransition;
	}

	public ArrayList<Transition> getIntransition() {
		return intransition;
	}

	public void setIntransition(ArrayList<Transition> intransition) {
		this.intransition = intransition;
	}

	public StateflowConnector getParent() {
		return parent;
	}
	
	public boolean HasParent() {
		return parent != null;
	}

	public void setParent(StateflowConnector parent) {
		this.parent = parent;
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

}
