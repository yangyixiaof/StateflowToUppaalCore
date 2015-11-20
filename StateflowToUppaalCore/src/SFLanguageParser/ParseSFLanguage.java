package SFLanguageParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import StateflowStructure.State;

public class ParseSFLanguage {
	
	public static final String uppaalseperate = ",";

	private static Map<String, Object> ParseSF(String action)
			throws IOException {
		ExprParser strParser = new ExprParser(new CommonTokenStream(
				new ExprLexer(new ANTLRInputStream(new ByteArrayInputStream(
						action.getBytes())))));
		ExprRealVisitor treeBuilder = new ExprRealVisitor();
		return treeBuilder.visitDo_action(strParser.do_action());
	}

	public static String TransferFunctionContent(String funccnt, State state) {
		Map<String, Object> transmap = null;
		try {
			transmap = ParseSF(funccnt);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("parse funccnt run into error.");
			System.exit(1);
		}
		GrammarTreeModifier identygtm = new IdentifierModifier(state);
		GrammarTreeModifier funcgtm = new FunctionNameModifier(state);
		Map<String, GrammarTreeModifier> gtmmap = new TreeMap<String, GrammarTreeModifier>();
		gtmmap.put("Identifier", identygtm);
		gtmmap.put("FunctionCall", funcgtm);
		return IterateAndPrintMap(transmap, gtmmap, "\t");
	}

	@SuppressWarnings("unchecked")
	public static String IterateAndPrintMap(Map<String, Object> transmap,
			Map<String, GrammarTreeModifier> gtmmap, String prefix) {
		StringBuilder buffer = new StringBuilder();
		// get the parameter of the map node tree
		List<Map<String, Object>> arg = (List<Map<String, Object>>) transmap
				.get("arg-list");
		// test the value of the map node tree and recursion
		if (transmap.get("value").equals("if")) {
			List<Map<String, Object>> args = (List<Map<String, Object>>) transmap
					.get("arg-list");
			buffer.append(prefix + "if (");
			buffer.append(IterateAndPrintMap(args.get(0), gtmmap, prefix+"\t"));
			buffer.append(prefix + ")\n");
			buffer.append(prefix + "{\n");
			buffer.append(IterateAndPrintMap(args.get(1), gtmmap, prefix+"\t"));
			buffer.append(prefix + "}\n");
			buffer.append(prefix + "else\n");
			buffer.append(prefix + "{\n");
			buffer.append(IterateAndPrintMap(args.get(2), gtmmap, prefix+"\t"));
			buffer.append(prefix + "}\n");
			// action statement, all action will be parsed to a statement with
			// node value do
		} else if (transmap.get("value").equals("do")) {
			// action not null
			if (arg.size() > 0) {
				for (Map<String, Object> node : (List<Map<String, Object>>) transmap
						.get("arg-list")) {
					if (node.get("value").equals("if")) {
						buffer.append(
								IterateAndPrintMap(node, gtmmap, prefix) + "\n");
					} else {
						buffer.append(
								IterateAndPrintMap(node, gtmmap, prefix) + ";\n");
					}
				}
				// null action
			} else {
			}
			// print operator, the operator has two arguments
			// print parameter, the parameter has no argument
		} else {
			if (transmap.get("tag").equals("FunctionCall"))
			{
				GrammarTreeModifier gtm = gtmmap.get("FunctionCall");
				String appendstr = transmap.get("value").toString();
				String fleftsp = "(";
				String frightsp = ")";
				String[] func;
				if (gtm != null)
				{
					func = (String[])gtm.Modifiy(appendstr);
					appendstr = func[0];
					fleftsp = func[1];
					frightsp = func[2];
				}
				buffer.append(appendstr);
				buffer.append(fleftsp);
				
				boolean ifdirectlyoutput = appendstr.equals("send");
				for (int i=0;arg!=null&&i<arg.size();i++)
				{
					String cosperate = ",";
					if (i == arg.size()-1)
					{
						cosperate = "";
					}
					Map<String, Object> node1 = ((List<Map<String, Object>>) transmap
							.get("arg-list")).get(i);
					if (ifdirectlyoutput)
					{
						if (node1.get("value").equals("."))
						{
							buffer.append(DotOpNode(node1)+cosperate);
						}
						else
						{
							buffer.append(node1.get("value").toString()+cosperate);
						}
					}
					else
					{
						buffer.append(IterateAndPrintMap(node1, gtmmap, prefix)+cosperate);
					}
				}
				buffer.append(frightsp);
			}
			else
			{
				if (arg.size() > 0) {
					if (arg.size() == 1)
					{
						buffer.append((String) (transmap.get("value")));
						Map<String, Object> node1 = ((List<Map<String, Object>>) transmap
								.get("arg-list")).get(0);
						buffer.append(IterateAndPrintMap(node1, gtmmap, prefix));
					}
					else
					{
						Map<String, Object> node1 = ((List<Map<String, Object>>) transmap
								.get("arg-list")).get(0);
						buffer.append(IterateAndPrintMap(node1, gtmmap, prefix));
						int size = arg.size();
						for (int i=1;i<size;i++)
						{
							buffer.append((String) (transmap.get("value")));
							Map<String, Object> node2 = ((List<Map<String, Object>>) transmap
									.get("arg-list")).get(i);
							buffer.append(IterateAndPrintMap(node2, gtmmap, ""));
						}
					}
				} else {
					if (transmap.get("tag").equals("Identifier"))
					{
						GrammarTreeModifier gtm = gtmmap.get("Identifier");
						String appendstr = transmap.get("value").toString();
						if (gtm != null)
						{
							appendstr = (String)gtm.Modifiy(appendstr);
						}
						buffer.append(appendstr);
					}
					else
					{
						buffer.append(transmap.get("value").toString());
					}
				}
			}
		}
		return buffer.toString().trim();
	}
	
	@SuppressWarnings("unchecked")
	private static String DotOpNode(Map<String, Object> node)
	{
		StringBuffer sb = new StringBuffer("");
		List<Map<String, Object>> dotarglist = (List<Map<String, Object>>) node.get("arg-list");
		int len = dotarglist.size();
		for (int i=0;i<len;i++)
		{
			sb.append(dotarglist.get(i).get("value")+"_");
		}
		if (sb.length()>0)
		{
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}

}