package parser;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {

	private Scanner scanner;
	
	public Parser (Scanner scanner) {
		this.scanner = scanner;
	}
	
	private Token match(TokenType type) throws LexicalException, SyntacticException {
		
		Token tk =scanner.peekToken();
		if(type.equals(tk.getType())) return scanner.nextToken();
		else throw new SyntacticException("Syntactic Exc!");
	}
}
