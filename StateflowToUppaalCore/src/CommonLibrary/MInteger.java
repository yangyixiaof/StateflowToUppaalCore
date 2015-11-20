package CommonLibrary;

public class MInteger {
	
	private int value;
	
	public MInteger(int val) {
		this.setValue(val);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public void Increase()
	{
		value++;
	}
	
}