package StateflowStructure;

import java.util.ArrayList;

public class SimulinkModel extends SimulinkStructure{
	
	private ArrayList<Chart> stateflowcharts = new ArrayList<Chart>();
	private int mTimeGap = 0;
	private int mStartTime = 0;
	private int mStopTime = 0;
	private double mTimeGapForBackUp = 0;
	
	public SimulinkModel(String ID) {
		super(ID);
	}
	
	public void addStateflowChart(Chart cht)
	{
		getStateflowcharts().add(cht);
	}

	public ArrayList<Chart> getStateflowcharts() {
		return stateflowcharts;
	}

	public void setStateflowcharts(ArrayList<Chart> stateflowcharts) {
		this.stateflowcharts = stateflowcharts;
	}

	public int getmTimeGap() {
		return mTimeGap;
	}

	public void setmTimeGap(int mTimeGap) {
		this.mTimeGap = mTimeGap;
	}

	public int getmStartTime() {
		return mStartTime;
	}

	public void setmStartTime(int mStartTime) {
		this.mStartTime = mStartTime;
	}

	public int getmStopTime() {
		return mStopTime;
	}

	public void setmStopTime(int mStopTime) {
		this.mStopTime = mStopTime;
	}

	public double getmTimeGapForBackUp() {
		return mTimeGapForBackUp;
	}

	public void setmTimeGapForBackUp(double mTimeGapForBackUp) {
		this.mTimeGapForBackUp = mTimeGapForBackUp;
	}
	
}