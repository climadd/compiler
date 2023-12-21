package token;

public class Token {

	private int riga;	//riga del codice in cui si trova il token
	private TokenType tipo;	//il tipo del token
	private String val;	//solo per identificatori, numeri, keyword, operatori

	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	public Token(TokenType tipo, int riga) {
		this.riga = riga;
		this.tipo = tipo;
	}

    // Getters per i campi
	public int getRiga() {
		return riga;
	}

	public TokenType getTipo() {
		return tipo;
	}

	public String getVal() {
		return val;
	}

	public String toString() {
		if(val==null) {
			return "<" + tipo + ", r: " + riga + ">";
		}
		else {
			return "<" + tipo + ", r: " + riga + ", " + val + ">";
		}
	}
	/*esempio produzione stringa token
	<INT,r:1><ID,r:1,tempa><SEMI,r:1>
	<ID,r:2,tempa><OP_ASSIGN,r:2,=><INUM,r:2,5><SEMI,r:2>
	<FLOAT,r:3><ID,r:3,tempb><OP_ASSIGN,r:3,=><ID,r:3,tempa><DIVIDE,r:3><FNUM,r:3,3.2><SEMI,r:2>
	<ID,r:4,tempb><OP_ASSIGN,r:4,+=><INUM,r:4,7><SEMI,r:4>
	<PRINT,r:5><ID,r:5,tempb><SEMI,r:5><EOF,r:5>
	*/

}
