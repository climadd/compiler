package symbolTable;

import java.util.ArrayList;

public class Registers {

	public static ArrayList<Character> characters;

	/**
	 * ArrayList of 26 Characters initialization method.
	 */
	public static void init() {
		characters = new ArrayList<Character>();
		for(char c = 'a'; c <= 'z'; c++) {
			characters.add(c);
		}
	}
	
	/**
	 * Removes a register from the available set if possible, otherwise throws an exception
	 * 
	 * @returns the char that was removed from the available pool
	 * @throws RegistersException
	 */
	public static char newRegister() throws RegistersException {
		if(!characters.isEmpty()) {
			return characters.remove(0);
		}
		else throw new RegistersException("No more registers left for further initialization instances!");
	}


}
