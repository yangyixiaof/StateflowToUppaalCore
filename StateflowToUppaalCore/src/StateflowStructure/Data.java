package StateflowStructure;

public class Data {
	
	private String scopestr;
	private String sizes;
	private String datatypestr;
	private String inivalue;
	private String name;
	
	public Data(String name, String scopestr, String sizes, String datatypestr, String inivalue) {
		this.setName(name);
		this.setScopestr(scopestr);
		this.setSizes(sizes);
		switch (datatypestr) {
		case "SF_BOOLEAN_TYPE":
			this.setDatatypestr("bool");
			if (inivalue == null || inivalue.equals(""))
			{
				inivalue = "false";
			}
			break;
		case "SF_INT32_TYPE":
			this.setDatatypestr("int");
			if (inivalue == null || inivalue.equals(""))
			{
				inivalue = "-1";
			}
			break;
		default:
			System.err.println("Error,Unspportted type:"+datatypestr);
			System.exit(1);
			break;
		}
		this.setInivalue(inivalue);
	}

	public String getScopestr() {
		return scopestr;
	}

	public void setScopestr(String scopestr) {
		this.scopestr = scopestr;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public String getDatatypestr() {
		return datatypestr;
	}

	public void setDatatypestr(String datatypestr) {
		this.datatypestr = datatypestr;
	}

	public String getInivalue() {
		return inivalue;
	}

	public void setInivalue(String inivalue) {
		this.inivalue = inivalue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean HasInitValue()
	{
		return (inivalue != null && !inivalue.equals(""));
	}
	
}