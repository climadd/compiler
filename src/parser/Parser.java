package parser;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {

	private Scanner scanner;

	//costruttore
	public Parser (Scanner scanner) {
		this.scanner = scanner;
	}


	//MATCH
	private Token match(TokenType type) throws LexicalException, SyntacticException {
		Token tk =scanner.peekToken();

		if(type.equals(tk.getType())) 
			return scanner.nextToken();
		else 
			throw new SyntacticException("TokenType Mismatch! peeked:" + tk.getType().toString() + "  parameter: " + type.toString() + " at line " + tk.getLine());
	}


	//METODI PER PARSING
	public void parse() throws SyntacticException, LexicalException {
		this.parsePrg(); 
	}

	//0
	private void parsePrg() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();
		switch(token.getType()) {
		case TYFLOAT, TYINT, ID, PRINT, EOF ->{		//Prg -> DSs $
			parseDSs();
			match(TokenType.EOF);
			return;

		}
		default -> throw new SyntacticException("Issue in parsePrg");
		}
	}

	//1.2.3
	private void parseDSs() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TYFLOAT, TYINT ->{		//DSs -> Dcl DSs
			parseDcl();
			parseDSs();
			return;
		}
		case ID, PRINT ->{
			parseStm();
			parseDSs();
			return;
		}
		case EOF -> {
			return;
		}
		default -> throw new SyntacticException("Issue in parseDSs");
		}
	}

	//4	
	private void parseDcl() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TYFLOAT, TYINT ->{
			parseTy();
			match(TokenType.ID);
			parseDclP();
			return;
		}
		default -> throw new SyntacticException("Issue in parseDcl");
		}

	}

	//5. 6
	private void parseDclP() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case SEMI ->{
			match(TokenType.SEMI);
			return;
		}
		case ASSIGN ->{
			match(TokenType.ASSIGN);
			parseExp();
			match(TokenType.SEMI);
			return;
		}
		default -> throw new SyntacticException("Issue in parseDclP");
		}
	}
	//8.7
	private void parseStm() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case PRINT ->{
			match(TokenType.PRINT);
			match(TokenType.ID);
			match(TokenType.SEMI);
			return;
		}
		case ID ->{
			match(TokenType.ID);
			parseOp();
			parseExp();
			match(TokenType.SEMI);
			return;
		}
		default -> throw new SyntacticException("Issue in parseDclP");
		}
	}
	//9
	private void parseExp() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case ID, FLOAT, INT ->{
			parseTr();
			parseExpP();
			return;
		}				
		default -> throw new SyntacticException("Issue in parseExp");
		}
	}
	//10.11.12
	private void parseExpP() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case PLUS ->{
			match(TokenType.PLUS);
			parseTr();
			parseExpP();
			return;
		}
		case MINUS ->{
			match(TokenType.MINUS);
			parseTr();
			parseExpP();
			return;
		}
		case SEMI ->{
			return;
		}
		default -> throw new SyntacticException("Issue in parseExpP");
		}
	}
	//13
	private void parseTr() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case ID, FLOAT, INT ->{
			parseVal();
			parseTrP();
			return;
		}
		default -> throw new SyntacticException("Issue in parseTr");
		}
	}
	//14.15.16
	private void parseTrP() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TIMES ->{
			match(TokenType.TIMES);
			parseVal();
			parseTrP();
			return;
		}
		case DIVIDE ->{
			match(TokenType.DIVIDE);
			parseVal();
			parseTrP();
			return;
		}
		case MINUS, PLUS, SEMI ->{
			return;
		}
		default -> throw new SyntacticException("Issue in parseTrP");
		}
	}

	//17.18
	private void parseTy() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TYFLOAT ->{
			match(TokenType.TYFLOAT);
			return;
		}
		case TYINT ->{
			match(TokenType.TYINT);
			return;
		}
		default -> throw new SyntacticException("Issue in parseTy");
		}
	}
	
//19.20.21
	private void parseVal() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case INT ->{
			match(TokenType.INT);
			return;
		}
		case FLOAT ->{
			match(TokenType.FLOAT);
			return;	
		}
		case ID ->{
			match(TokenType.ID);
			return;
		}
		default -> throw new SyntacticException("Issue in parseVal");
		}
	}
	
//22.23
	private void parseOp() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case ASSIGN ->{
			match(TokenType.ASSIGN);
			return;
		}
		case OP_ASSIGN ->{
			match(TokenType.OP_ASSIGN);
			return;
		}
		default ->throw new SyntacticException("Issue in parseOp");
		}
	}


}
