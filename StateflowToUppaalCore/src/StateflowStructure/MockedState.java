package StateflowStructure;

public class MockedState extends State{

	public MockedState(String ID, int x, int y, State parentstate) {
		super(ID, x, y);
		setParent(parentstate);
		setfLabelString("Initial");
	}
	
}
