package StateflowStructure;

public class Junction extends StateflowConnector{
	
	private String type = null;

	public Junction(String ID,int x,int y) {
		super(ID,x,y);
		this.mFullName = ID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String GetFullName() {
		if (mFullName == null) {
			String prefix = "";
			if (HasParent()) {
				prefix = ((State) getParent()).GetFullName() + "_";
			}
			mFullName = prefix + this.ID;
		}
		return mFullName;
	}

}