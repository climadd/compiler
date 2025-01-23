package token;

/**
 * Contains the informations about the Token as a structure and its handling methods
 */
public class Token {

	private TokenType type;	//enum
	private int line;	//riga del codice in cui si trova il token
	private String value;	// OPZIONALE: per identificatori e numeri contiene la string matchata
	
	/**
	 * constructor
	 * @param type
	 * @param line
	 * @param value
	 */
	public Token(TokenType type, int line, String value) {
		this.type = type;
		this.line = line;
		this.value = value;
	}
	
	public Token(TokenType type, int line) {
		this(type, line, null);
	}

    // Getters per i campi
	public int getLine() {
		return this.line;
	}
	
	public TokenType getType() {
		return this.type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	/**
	 * toString method
	 * @returns a string that represents the data of a token
	 */
	public String toString() {
		if(this.value != null)
			return("<" + this.type + ", r:" + this.line + ", " + this.value + ">");
		else 
			return ("<" + this.type + ", r:" + this.line + ">");
	}
	
	/*	example output toString()
	 * 
	 * 	<TYINT, r:1><ID, r:1, tempa><SEMI ,r:1>
		<ID, r:2, tempa><OP_ASSIGN, r:2, =><INT, r:2, 5><SEMI, r:2>
		<TYFLOAT, r:3><ID, r:3, tempb><OP_ASSIGN, r:3, =><ID, r:3, tempa><DIVIDE, r:3><FLOAT, r:3, 3.2><SEMI, r:2>
		<ID, r:4, tempb><OP_ASSIGN, r:4, +=><INT, r:4, 7><SEMI, r:4>
		<PRINT, r:5><ID, r:5, tempb><SEMI, r:5><EOF, r:5>

     
     */

}
