package PositionLibrary;

import java.util.ArrayList;

import Uppaal.UppaalCordinateStaticData;
import Uppaal.UppaalNail;

public class LinePositionLibrary {
	
	static int Lines = 0;
	static int smx = -1;
	static double xgap = -1;
	static int smy = -1;
	static double ygap = -1;
	
	protected static void InitializeLineSeperateWithoutPredict(int startx, int starty,
			int stopx, int stopy)
	{
		double length = Math.sqrt((startx - stopx) * (startx - stopx)
				+ (starty - stopy) * (starty - stopy));
		ygap = (stopx - startx) / length * UppaalCordinateStaticData.twolinegap;
		xgap = (starty - stopy) / length * UppaalCordinateStaticData.twolinegap;
		smx = (startx + stopx) / 2 - (int) (xgap);
		smy = (starty + stopy) / 2 - (int) (ygap);
	}
	
	protected static ArrayList<UppaalNail> LineNextWithoutPredict(int i) {
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		i-=1;
		nails.add(new UppaalNail(smx + (int) (i*xgap), smy + (int) (i*ygap)));
		return nails;
	}

	protected static void InitializeLineSeperate(int lines, int startx, int starty,
			int stopx, int stopy) {
		Lines = lines;
		double length = Math.sqrt((startx - stopx) * (startx - stopx)
				+ (starty - stopy) * (starty - stopy));
		ygap = (stopx - startx) / length * UppaalCordinateStaticData.twolinegap;
		xgap = (stopy - starty) / length * UppaalCordinateStaticData.twolinegap;
		smx = (startx + stopx) / 2 - (int) (xgap * lines / 2.0);
		smy = (starty + stopy) / 2 - (int) (ygap * lines / 2.0);
	}

	protected static ArrayList<UppaalNail> LineNext() {
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		if (Lines-- > 0) {
			nails.add(new UppaalNail(smx + (int) (Lines*xgap), smy + (int) (Lines*ygap)));
		}
		else
		{
			System.err.println("Wrong predict transition num between states.");
			System.exit(1);
		}
		return nails;
	}

}