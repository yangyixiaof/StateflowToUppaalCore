package StateflowStructure;

public class Chart extends State{
	
	private String name = null;
	
	public Chart(String ID,int x,int y) {
		super(ID,x,y);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
