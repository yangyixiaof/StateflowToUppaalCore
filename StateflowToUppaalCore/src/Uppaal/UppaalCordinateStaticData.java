package Uppaal;

import java.util.ArrayList;

public class UppaalCordinateStaticData {
	
	//Controller center point.
	public final static int controllercenterx = 240;
	public final static int controllercentery = 240;
	
	//active nail.
	//deactive nail.
	
	//deactive nail.
	public final static int deactiverightnailx = 200;
	public final static int deactiverightnaily = 290;
	public final static int deactiveleftnailx = 190;
	public final static int deactiveleftnaily = 260;
	public final static int deactiveleftnailxgap = -24;
	public final static int deactiveleftnailygap = 32;
	public final static int deactiverightnailxgap = -40;
	public final static int deactiverightnailygap = 10;
	
	//during nail.
	public final static int duringleftnailx = 205;
	public final static int duringleftnaily = 205;
	public final static int duringleftnailxgap = -40;
	public final static int duringleftnailygap = -25;
	public final static int duringrightnailx = 275;
	public final static int duringrightnaily = 205;
	public final static int duringrightnailxgap = 40;
	public final static int duringrightnailygap = -25;
	
	//active nail
	public final static int activerightnailx = 290;
	public final static int activerightnaily = 260;
	public final static int activeleftnailx = 280;
	public final static int activeleftnaily = 290;
	public final static int activerightnailxgap = 40;
	public final static int activerightnailygap = 10;
	public final static int activeleftnailxgap = 24;
	public final static int activeleftnailygap = 32;
	
	//deact controller branch.
	public final static int deactivebranchx = 0;
	public final static int deactivebranchy = 210;
	//act controller branch.
	public final static int activebranchx = 880;
	public final static int activebranchy = 210;

	//allcontroller nail.
	public final static int halfxlength = 80;
	public final static int halfylength = 50;
	
	//Common automaton center point.
	public final static int commoncenterx = 80;
	public final static int commoncentery = 80;
	
	public final static int cyclediameter = 150;
	
	public final static int twolinegap = 50;
	
	public final static int iniimdtimes = 2;
	
	public final static int belowxgap = 25;
	public final static int belowygap = 20;
	
	public final static int transitionguardxgap = -15;
	public final static int transitionguardygap = 50;
	
	public final static int transitionactionybelowguard = 15;
	
	public final static int controlleractionzoom = 5;
	
	public UppaalCordinateStaticData() {
	}
	
	private static int controller_cycle = 0;
	
	public static void ResetControllerSelfCycle()
	{
		controller_cycle = 0;
	}
	
	public static ArrayList<UppaalNail> NextControllerActiveCoordinate()
	{
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		nails.add(new UppaalNail(UppaalCordinateStaticData.activeleftnailx + controller_cycle*UppaalCordinateStaticData.activeleftnailxgap,
				UppaalCordinateStaticData.activeleftnaily + controller_cycle*UppaalCordinateStaticData.activeleftnailygap));
		nails.add(new UppaalNail(UppaalCordinateStaticData.activerightnailx + controller_cycle*UppaalCordinateStaticData.activerightnailxgap,
				UppaalCordinateStaticData.activerightnaily + controller_cycle*UppaalCordinateStaticData.activerightnailygap));
		controller_cycle++;
		return nails;
	}
	
	public static ArrayList<UppaalNail> NextControllerDuringCoordinate()
	{
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		nails.add(new UppaalNail(UppaalCordinateStaticData.duringleftnailx + controller_cycle*UppaalCordinateStaticData.duringleftnailxgap,
				UppaalCordinateStaticData.duringleftnaily + controller_cycle*UppaalCordinateStaticData.duringleftnailygap));
		nails.add(new UppaalNail(UppaalCordinateStaticData.duringrightnailx + controller_cycle*UppaalCordinateStaticData.duringrightnailxgap,
				UppaalCordinateStaticData.duringrightnaily + controller_cycle*UppaalCordinateStaticData.duringrightnailygap));
		controller_cycle++;
		return nails;
	}
	
	public static ArrayList<UppaalNail> NextControllerDeActiveCoordinate()
	{
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		nails.add(new UppaalNail(UppaalCordinateStaticData.deactiveleftnailx + controller_cycle*UppaalCordinateStaticData.deactiveleftnailxgap,
				UppaalCordinateStaticData.deactiveleftnaily + controller_cycle*UppaalCordinateStaticData.deactiveleftnailygap));
		nails.add(new UppaalNail(UppaalCordinateStaticData.deactiverightnailx + controller_cycle*UppaalCordinateStaticData.deactiverightnailxgap,
				UppaalCordinateStaticData.deactiverightnaily + controller_cycle*UppaalCordinateStaticData.deactiverightnailygap));
		controller_cycle++;
		return nails;
	}
	
	public static ArrayList<UppaalNail> NextAllControllerLowerCoordinate()
	{
		ArrayList<UppaalNail> nails = new ArrayList<UppaalNail>();
		nails.add(new UppaalNail(UppaalCordinateStaticData.controllercenterx - (int)((controller_cycle+0.6)*UppaalCordinateStaticData.halfxlength),
				UppaalCordinateStaticData.controllercentery + (int)((controller_cycle+0.6)*UppaalCordinateStaticData.halfylength)));
		nails.add(new UppaalNail(UppaalCordinateStaticData.controllercenterx + (int)((controller_cycle+0.6)*UppaalCordinateStaticData.halfxlength),
				UppaalCordinateStaticData.controllercentery + (int)((controller_cycle+0.6)*UppaalCordinateStaticData.halfylength)));
		controller_cycle++;
		return nails;
	}

	public static int getCycle() {
		return controller_cycle;
	}

	public static void setCycle(int cycle) {
		UppaalCordinateStaticData.controller_cycle = cycle;
	}
}