package PositionLibrary;

import Uppaal.UppaalState;

public class StatesPointsPositionLibrary {
	
	public static final int xleastgap = 250;
	public static final int yleastgap = 250;
	
	public static final int defaultpointygap = 40;
	
	static double xgap = 0;
	static double ygap = 0;
	
	static int nstartx = 0;
	static int nstarty = 0;
	
	static UppaalState nstartus = null;
	static UppaalState nstopus = null;
	
	public static void TwoStateflowStatesPointsInitialize(UppaalState startus, UppaalState stopus, int calen)
	{
		//calen : splitted actions number.
		nstartus = startus;
		nstopus = stopus;
		int startx = startus.getX();
		int starty = startus.getY();
		int stopx = stopus.getX();
		int stopy = stopus.getY();
		nstartx = startx;
		nstarty = starty;
		xgap = ((stopx - startx) / ((calen + 1) * 1.0));
		ygap = ((stopy - starty) / ((calen + 1) * 1.0));
		double max = Math.max(Math.abs(xgap), Math.abs(ygap));
		if (max < StatesPointsPositionLibrary.xleastgap)
		{
			double times = StatesPointsPositionLibrary.xleastgap/(max);
			xgap = xgap * times;
			ygap = ygap * times;
		}
	}
	
	public static int NextStatePositionx()
	{
		nstartx += xgap;
		return nstartx;
	}
	
	public static int NextStatePositiony()
	{
		nstarty += ygap;
		return nstarty;
	}
	
	public static void FinalizeStatePosition()
	{
		if (nstopus.isNewlycreated())
		{
			nstopus.setX(NextStatePositionx());
			nstopus.setY(NextStatePositiony());
		}
	}
	
	public static int[] GetLeastGapxy(int lx, int ly)
	{
		int[] result = new int[2];
		int max = Math.max(Math.abs(lx), Math.abs(ly));
		if (max == 0)
		{
			max = 50;
		}
		if (max < StatesPointsPositionLibrary.xleastgap)
		{
			double times = StatesPointsPositionLibrary.xleastgap/(max);
			result[0] = (int)(lx * times);
			result[1] = (int)(ly * times);
		}
		return result;
	}
	
}