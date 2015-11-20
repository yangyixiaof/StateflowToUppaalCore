package StateflowStructure;

public class TruthTable extends StateflowFunction
{
	
	State state;
	String[][] predicateArray = null;
	String[][] actionArray = null;

	public TruthTable(String ID, State state) {
		super(ID);
		this.state = state;
	}
	
	public void InitialPredicateArray(int rownum, int colnum)
	{
		predicateArray = new String[rownum][colnum];
	}
	
	public void SetPredicateArray(int r,int c,String value)
	{
		predicateArray[r][c] = value;
	}
	
	public void InitialActionArray(int rownum, int colnum)
	{
		actionArray = new String[rownum][colnum];
	}
	
	public void SetActionArray(int r,int c,String value)
	{
		actionArray[r][c] = value;
	}

}
