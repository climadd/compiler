package parser;

import scanner.LexicalException;

public class SyntacticException extends Exception {

	private static final long serialVersionUID = 1L;

	public SyntacticException(String msg) {
		super(msg);
	}
	
	//PER WRAPPARE LE LEXICAL EXC NEL CODICE DI PARSING :)
	public interface SyntaxErrorWrapper <T> {
		  T execute() throws LexicalException;
	}
}
