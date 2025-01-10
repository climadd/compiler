package parser;

import java.util.ArrayList;

import ast.*;
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
	public NodeProgram parse() throws SyntacticException, LexicalException {
		return this.parsePrg(); 
	}

	//0
	private NodeProgram parsePrg() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();
		ArrayList<NodeDecSt> nodeDecSt;
		
		switch(token.getType()) {
		case TYFLOAT, TYINT, ID, PRINT, EOF ->{		//Prg -> DSs $		
			nodeDecSt = parseDSs();
			match(TokenType.EOF);
			return new NodeProgram(nodeDecSt);

		}
		default -> throw new SyntacticException("Issue in parsePrg");
		}
	}

	//1.2.3
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();
		
		NodeDecl nodeDecl;
		ArrayList<NodeDecSt> nodeDecSt = new ArrayList<NodeDecSt>();
		NodeStm nodeStm;
		
		switch(token.getType()) {
		case TYFLOAT, TYINT ->{		//DSs -> Dcl DSs
			nodeDecl = parseDcl();
			nodeDecSt = parseDSs();
			nodeDecSt.add(0, nodeDecl);
			return nodeDecSt;
		}
		case ID, PRINT ->{
			nodeStm = parseStm();
			nodeDecSt = parseDSs();
			nodeDecSt.add(0,nodeStm);
			return nodeDecSt;
		}
		case EOF -> {
			return new ArrayList<NodeDecSt>();
		}
		default -> throw new SyntacticException("Issue in parseDSs");
		}
	}

	//4	
	private NodeDecl parseDcl() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();
		
		switch(token.getType()) {
		case TYFLOAT, TYINT ->{
			LangType type = parseTy();
			match(TokenType.ID);
			parseDclP();
			NodeId nodeId = new NodeId(match(TokenType.ID).getValue());
			NodeExpr nodeInit = parseDclP();
			return new NodeDecl(nodeId, type, nodeInit);
		}
		default -> throw new SyntacticException("Issue in parseDcl");
		}
	}

	//5. 6
	private NodeExpr parseDclP() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();
		NodeExpr nodeExpr;
		
		switch(token.getType()) {
		case SEMI ->{
			match(TokenType.SEMI);
			return null;
		}
		case ASSIGN ->{
			match(TokenType.ASSIGN);
			nodeExpr = parseExp(); //ogni volta che faccio un parse restituisco un nodo
			match(TokenType.SEMI);		 
			return nodeExpr;
		}
		default -> throw new SyntacticException("Issue in parseDclP");
		}
	}
	
	//7.8
	private NodeStm parseStm() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		NodeStm nodeStm;
		NodeId nodeId;
		
		switch(token.getType()) {
		case PRINT ->{
			match(TokenType.PRINT);
			nodeId = new NodeId(match(TokenType.ID).getValue());
			match(TokenType.SEMI);
			NodePrint nodePrint = new NodePrint(nodeId);
			NodeAssign nodeAssign = new NodeAssign(nodeId, nodeExpr);
			return nodeAssign;
		}
		case ID ->{
			nodeId = new NodeId(match(TokenType.ID).getValue());
			parseOp();
			parseExp();
			match(TokenType.SEMI);
			
			return nodeStm;
		}
		default -> throw new SyntacticException("Issue in parseDclP");
		}
	}
	//9
	private NodeExpr parseExp() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case ID, FLOAT, INT ->{
			parseTr();
			parseExpP();
			return null;
		}				
		default -> throw new SyntacticException("Issue in parseExp");
		}
	}
	//10.11.12
	private NodeExpr parseExpP() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case PLUS ->{
			match(TokenType.PLUS);
			parseTr();
			parseExpP();
			return null;
		}
		case MINUS ->{
			match(TokenType.MINUS);
			parseTr();
			parseExpP();
			return null;
		}
		case SEMI ->{
			return null;
		}
		default -> throw new SyntacticException("Issue in parseExpP");
		}
	}
	//13
	private NodeExpr parseTr() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case ID, FLOAT, INT ->{
			parseVal();
			parseTrP();
			return null;
		}
		default -> throw new SyntacticException("Issue in parseTr");
		}
	}
	//14.15.16
	private NodeExpr parseTrP() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TIMES ->{
			match(TokenType.TIMES);
			parseVal();
			parseTrP();
			return null;
		}
		case DIVIDE ->{
			match(TokenType.DIVIDE);
			parseVal();
			parseTrP();
			return null;
		}
		case MINUS, PLUS, SEMI ->{
			return null;
		}
		default -> throw new SyntacticException("Issue in parseTrP");
		}
	}

	//17.18
	private LangType parseTy() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TYFLOAT ->{
			match(TokenType.TYFLOAT);
			return LangType.TYFLOAT;
		}
		case TYINT ->{
			match(TokenType.TYINT);
			return LangType.TYINT;
		}
		default -> throw new SyntacticException("Issue in parseTy");
		}
	}
	
//19.20.21
	private NodeExpr parseVal() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case INT ->{
			match(TokenType.INT);
			return null;
		}
		case FLOAT ->{
			match(TokenType.FLOAT);
			return null;	
		}
		case ID ->{
			match(TokenType.ID);
			return null;
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
