package CommonLibrary;

import StateflowStructure.State;

public class TransitionLabelParser {
	
	public static String[] ExtractPartsInTransitionWithoutModify(String transitionLabel,
			State container) {
		String[] strs = RegularExpression
				.GetTransitionFourParts(transitionLabel);
		for (int i = 1; i < 4; i++) {
			if (strs[i] != null && !strs[i].equals("")) {
				switch (i) {
				case 1:
					strs[i] = strs[i].trim();
					strs[i] = strs[i].substring(1, strs[i].length() - 1);
					break;
				case 2:
					strs[i] = strs[i].trim();
					strs[i] = strs[i].substring(1, strs[i].length() - 1);
					break;
				case 3:
					strs[i] = strs[i].trim();
					int bracpos = strs[i].indexOf('{') + 1;
					strs[i] = strs[i].substring(bracpos, strs[i].length() - 1);
					break;
				}
			}
		}
		return strs;
	}
	
	public static String[] ExtractPartsInTransitionWithModify(String transitionLabel,
			State container) {
		String[] strs = RegularExpression
				.GetTransitionFourParts(transitionLabel);
		for (int i = 1; i < 4; i++) {
			if (strs[i] != null && !strs[i].equals("")) {
				switch (i) {
				case 1:
					strs[i] = strs[i].trim();
					strs[i] = strs[i].substring(1, strs[i].length() - 1);
					break;
				case 2:
					strs[i] = strs[i].trim();
					strs[i] = strs[i].substring(1, strs[i].length() - 1);
					break;
				case 3:
					strs[i] = strs[i].trim();
					int bracpos = strs[i].indexOf('{') + 1;
					strs[i] = strs[i].substring(bracpos, strs[i].length() - 1);
					break;
				}
				strs[i] = ModifyActionHelper.ModifyOneAction(strs[i], container);
			}
		}
		return strs;
	}
	
}