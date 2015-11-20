package StateflowStructure;

import java.util.Comparator;

public class TransitionSorter implements Comparator<Transition>{

	@Override
	public int compare(Transition arg0, Transition arg1) {
		if (arg0.getPriority() < arg1.getPriority())
		{
			return -1;
		}
		if (arg0.getPriority() > arg1.getPriority())
		{
			return 1;
		}
		return 0;
	}

}
