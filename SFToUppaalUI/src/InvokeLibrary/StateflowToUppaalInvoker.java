package InvokeLibrary;

import TransferLogic.MainFunc;

public class StateflowToUppaalInvoker {
	public static void InvokeStateflowToUppaalLogic(String[] args) {
		MainFunc mf = new MainFunc();
		try {
			mf.TransferStateflowToUppaal(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred:" + e.getMessage());
		}
	}
}
