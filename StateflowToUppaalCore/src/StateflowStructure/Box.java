package StateflowStructure;

public class Box extends StateflowStructure{

	private State boxstate;
	
	public Box(String ID, State boxstate) {
		super(ID);
		this.setBoxstate(boxstate);
	}

	public State getBoxstate() {
		return boxstate;
	}

	public void setBoxstate(State boxstate) {
		this.boxstate = boxstate;
	}	

}
