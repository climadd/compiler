package symbolTable;

import java.util.HashMap;

import ast.LangType;

/**
 * A symbol table responsible to hold data about declared variables
 * 
 * @author Lorenzo Mirto Bertoldo (github.com/climadd)
 *
 */
public class SymbolTable {

	public static HashMap<String,Attributes> symbolTableMap;

	/**
	 * Represents the attributes of a SymbolTable entry
	 */
	public static class Attributes{
		private LangType type;
		private Character register;

		/**
		 * Constructors
		 * 
		 * @param type
		 * @param register
		 */
		public Attributes(LangType type, Character register) {
			this.type = type;
			this.register = register;
		}

		public Attributes(LangType type) {
			this(type, null);
		}

		public Attributes(Character register) {
			this(null, register);
		}



		/**
		 * Getters
		 */
		public LangType getType() {
			return this.type;
		}

		public char getRegister() {
			return this.register;
		}

		/**
		 * Setter
		 * 
		 * @param register
		 */
		public void setRegister(char register) {
			this.register = register;
		}
	}

	/**
	 * Initializes the symbol table as an empty map
	 */
	public static void init() {
		symbolTableMap = new HashMap<String, Attributes>();
	}

	/**
     * Inserts a variable and its attributes into the symbol table.
     * 
     * @param id the identifier of the variable
     * @param entry the Attributes object containing type and register information
     * @return true if the variable was added successfully, false if it already exists
     */
	public static boolean enter(String id, Attributes entry) {
		if(!symbolTableMap.containsKey(id)) {
			symbolTableMap.put(id, entry);
			return true;
		}
		else return false;	
	}

    /**
     * Retrieves the attributes of a variable if it exists in the symbol table.
     * 
     * @param id the identifier of the variable
     * @return Attributes object containing type and register information, or {@code null} if not found
     */
	public static Attributes lookup(String id) {
		if(symbolTableMap.containsKey(id)) {
			return symbolTableMap.get(id);
		}
		else return null;
	}

	/**
	 * provides a string of the whole contents of the symbol table
	 * @returns a String
	 */
	public static String toStr() {
		StringBuilder string = new StringBuilder("");
		for(String key : symbolTableMap.keySet()) {
			string.append(key + ": " + symbolTableMap.get(key) + " | ");	
		}
		return string.toString();
	}

	/**
	 * returns the number of entries in the map
	 * @return an int number
	 */
	public static int size() {
		return symbolTableMap.size();
	}

}
