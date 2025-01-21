package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {

	public static HashMap<String,Attributes> symbolTableMap;

	public static class Attributes{
		private LangType type;
		private char register;
		
		//Inner Class
		public Attributes(LangType type) {
			this.type = type;
		}
		
		//constructors
		public Attributes(LangType type, char register) {
			this.type = type;
			this.register = register;
		}
		
		//getters
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

	// inserisce il nome con l’informazione sulla dichiarazione 
	public static boolean enter(String id, Attributes entry) {
		if(!symbolTableMap.containsKey(id)) {
			symbolTableMap.put(id, entry);
			return true;
		}
		else return false;	
	}

	// serve a controllare se la variabile di parametro è  presente nella symbol table, 
	// quindi anche inizializzata correttamente
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
