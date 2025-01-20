package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {

	public static HashMap<String,Attributes> symbolTableMap;

	public static class Attributes{
		private LangType type;
		private char register;
		
		public Attributes(LangType type) {
			this.type = type;
		}
		
		public Attributes(LangType type, char register) {
			this.type = type;
			this.register = register;
		}
		
		public LangType getType() {
			return this.type;
		}
		
		public char getRegister() {
			return this.register;
		}
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
		StringBuilder string = new StringBuilder("");
		for(String key : symbolTableMap.keySet()) {
			string.append(key + ": " + symbolTableMap.get(key) + " | ");	
		}
		return string.toString();
	}

	public static int size() {
		return symbolTableMap.size();
	}

}
