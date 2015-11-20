package Uppaal;

import java.util.ArrayList;

public class UppaalModel {
	
	private ArrayList<UppaalAutomaton> uppaalAutomatons = new ArrayList<UppaalAutomaton>();

	public void AddUA(UppaalAutomaton ua)
	{
		getUppaalAutomatons().add(ua);
	}
	
	public void AddUAs(ArrayList<UppaalAutomaton> tmpres) {
		getUppaalAutomatons().addAll(tmpres);
	}

	public ArrayList<UppaalAutomaton> getUppaalAutomatons() {
		return uppaalAutomatons;
	}

	public void setUppaalAutomatons(ArrayList<UppaalAutomaton> uppaalAutomatons) {
		this.uppaalAutomatons = uppaalAutomatons;
	}

}
