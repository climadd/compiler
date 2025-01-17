package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {

	public static HashMap<String,Attributes> symbolTableMap;

	public static class Attributes{
		private LangType type;


	}


	public static void init() {
		symbolTableMap = new HashMap<String, Attributes>();
	}

	public static boolean enter(String id, Attributes entry) {
		if(!symbolTableMap.containsKey(id)) {
			symbolTableMap.put(id, entry);
			return true;
		}
		else return false;	
	}

	public static Attributes lookup(String id) {
		if(symbolTableMap.containsKey(id)) {
			return symbolTableMap.get(id);
		}
		else return null;
	}

	public static String toStr() {
		String string = "";
		for(String key : symbolTableMap.keySet()) {
			string += key;
		}
		return string;
	}

	public static int size() {
		return symbolTableMap.size();
	}

}
