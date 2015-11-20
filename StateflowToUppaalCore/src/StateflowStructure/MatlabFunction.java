package StateflowStructure;

public class MatlabFunction extends StateflowFunction{

	State state;
	private String scriptstr;
	
	public MatlabFunction(String ID, State state) {
		super(ID);
		this.state = state;
	}

	public String getScriptstr() {
		return scriptstr;
	}

	public void setScriptstr(String scriptstr) {
		this.scriptstr = scriptstr;
	}
	
}
