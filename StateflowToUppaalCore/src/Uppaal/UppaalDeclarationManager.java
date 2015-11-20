package Uppaal;

public class UppaalDeclarationManager {

	UppaalDeclaration mUppaalDeclaration = null;

	public UppaalDeclaration GetUppaalOverAllDeclaration() {
		if (mUppaalDeclaration == null) {
			mUppaalDeclaration = new UppaalDeclaration("OverallDeclaration");
		}
		return mUppaalDeclaration;
	}

	public UppaalDeclaration GetUppaalAutomatonDeclaration(
			String declarationname) {
		UppaalDeclaration uc = new UppaalDeclaration(declarationname);
		return uc;
	}

}