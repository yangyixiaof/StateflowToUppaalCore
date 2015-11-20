package Uppaal;

import java.util.Map;
import java.util.TreeMap;

public class UppaalDeclaration extends UppaalStructure{
	
	public static final String IntArrayDeclare = "IntArray";
	
	Map<String,String> variables = new TreeMap<String, String>();
	Map<String,String> functions = new TreeMap<String, String>();
		
	protected UppaalDeclaration(String name) {
		super(name);
	}
	
	public boolean ContainsFunction(String funcname)
	{
		return functions.containsKey(funcname);
	}
	
	public boolean ContainsVariable(String varname)
	{
		return variables.containsKey(varname);
	}
	
	public void AddVariable(String varname,String vartype)
	{
		variables.put(varname, vartype);
	}
	
	public void AddFunction(String funcname,String funccontent)
	{
		functions.put(funcname, funccontent);
	}

	public Map<String,String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String,String> variables) {
		this.variables = variables;
	}

	public Map<String,String> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String,String> functions) {
		this.functions = functions;
	}

}