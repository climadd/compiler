package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {

	public static HashMap<String,Attributes> symbolTable;

	public static class Attributes{
		private LangType type;
	}

	public static void init() {
		// .....
	}

	public static boolean enter(String id, Attributes entry) {
		// ......
		throw new RuntimeException();	//togli quando aggiungi il valore di ritorno
	}
	
	public static Attributes lookup(String id) {
		//......
		throw new RuntimeException();	//togli quando aggiungi il valore di ritorno
	}
	
	public static String toStr() {
		// ......
		throw new RuntimeException();	//togli quando aggiungi il valore di ritorno
	}
	
	public static int size() {
		// ......
		throw new RuntimeException();	//togli quando aggiungi il valore di ritorno
	}
	
}
