package TransferLogic;

import StateFlow.DataManager;
import StateFlow.EventRegistry;
import StateFlow.FunctionsManager;
import StateFlow.StateManager;
import Uppaal.UppaalDeclarationManager;
import Uppaal.UppaalIDAllocator;

public class RunEnvironment {
	
	private static UppaalIDAllocator mUppaalIDAllocator = null;
	private static UppaalDeclarationManager mUppaalDeclarationManager = null;
	private static EventTimeStatistic mEventTimeStatistic = null;
	private static DataManager mDataManager = null;
	private static EventRegistry mEventRegistry = null;
	private static FunctionsManager mFunctionsManager = null;
	private static StateManager mStateManager = null;
	
	public static void InitializeAllEnvironments()
	{
		setmUppaalIDAllocator(new UppaalIDAllocator());
		setmUppaalDeclarationManager(new UppaalDeclarationManager());
		setmEventTimeStatistic(new EventTimeStatistic());
		setmDataManager(new DataManager());
		setmFunctionsManager(new FunctionsManager());
		setmEventRegistry(new EventRegistry());
		setmStateManager(new StateManager());
	}

	public static UppaalIDAllocator getmUppaalIDAllocator() {
		return mUppaalIDAllocator;
	}

	public static void setmUppaalIDAllocator(UppaalIDAllocator mUppaalIDAllocator) {
		RunEnvironment.mUppaalIDAllocator = mUppaalIDAllocator;
	}

	public static UppaalDeclarationManager getmUppaalDeclarationManager() {
		return mUppaalDeclarationManager;
	}

	public static void setmUppaalDeclarationManager(
			UppaalDeclarationManager mUppaalDeclarationManager) {
		RunEnvironment.mUppaalDeclarationManager = mUppaalDeclarationManager;
	}

	public static EventTimeStatistic getmEventTimeStatistic() {
		return mEventTimeStatistic;
	}

	public static void setmEventTimeStatistic(EventTimeStatistic mEventTimeStatistic) {
		RunEnvironment.mEventTimeStatistic = mEventTimeStatistic;
	}

	public static DataManager getmDataManager() {
		return mDataManager;
	}

	public static void setmDataManager(DataManager mDataManager) {
		RunEnvironment.mDataManager = mDataManager;
	}

	public static FunctionsManager getmFunctionsManager() {
		return mFunctionsManager;
	}

	public static void setmFunctionsManager(FunctionsManager mFunctionsManager) {
		RunEnvironment.mFunctionsManager = mFunctionsManager;
	}

	public static EventRegistry getmEventRegistry() {
		return mEventRegistry;
	}

	public static void setmEventRegistry(EventRegistry mEventRegistry) {
		RunEnvironment.mEventRegistry = mEventRegistry;
	}

	public static StateManager getmStateManager() {
		return mStateManager;
	}

	public static void setmStateManager(StateManager mStateManager) {
		RunEnvironment.mStateManager = mStateManager;
	}
	
}
