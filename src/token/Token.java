package token;

public class Token {

	private TokenType tipo;	//enum
	private int riga;	//riga del codice in cui si trova il token
	private String val;	// OPZIONALE: per identificatori e numeri contiene la string matchata
	
	public Token(TokenType type, int line, String value) {
		this.tipo = type;
		this.riga = line;
		this.val = value;
	}
	
	public Token(TokenType type, int line) {
		this(type, line, null);
	}

    // Getters per i campi
	public int getLine() {
		return this.riga;
	}
	
	public TokenType getType() {
		return this.tipo;
	}
	
	public String getValue() {
		return this.val;
	}
	
    
	public String toString() {
		if(this.val != null)
			return("<" + tipo + ", r:" + riga + ", " + val + ">");
		else 
			return ("<" + tipo + ", r:" + riga + ">");
	}

	//nota: dopo ogni virgola preferisco mettere uno spazio
	
	/*	esempio output toString()
	 * 
	 * 	<TYINT,r:1><ID,r:1,tempa><SEMI,r:1>
		<ID,r:2,tempa><OP_ASSIGN,r:2,=><INT,r:2,5><SEMI,r:2>
		<TYFLOAT,r:3><ID,r:3,tempb><OP_ASSIGN,r:3,=><ID,r:3,tempa><DIVIDE,r:3><FLOAT,r:3,3.2><SEMI,r:2>
		<ID,r:4,tempb><OP_ASSIGN,r:4,+=><INT,r:4,7><SEMI,r:4>
		<PRINT,r:5><ID,r:5,tempb><SEMI,r:5><EOF,r:5>

     
     */

}
