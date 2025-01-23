package token;

/**
 * enumeration used to differentiate token types
 */
public enum TokenType {
	
	INT,	//	0 | [1-9]([0-9]*) 
	FLOAT,	//	[0-9]+.([0-9]{0,5})
	ID,	//	[a-z]+ 
	TYINT,	// int
	TYFLOAT,	// float
	PRINT,	// print
	ASSIGN,	// =
	OP_ASSIGN,	// += | -= | *= | /=
	PLUS,	// +
	MINUS,	// -
	TIMES,	// *
	DIVIDE,	// /
	SEMI,	// ;
	EOF;
}
