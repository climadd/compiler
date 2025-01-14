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
	//necessario per capire in che branch delle regole grammaticali proseguire

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
			nodeExpr = parseExp();
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

		NodeId nodeId;

		switch(token.getType()) {
		case ID -> {
			nodeId = new NodeId(match(TokenType.ID).getValue());
			Token op = parseOp();
			//dal parseOp ricavo il tipo di assegnamento per capire
			if(op.getType()== TokenType.OP_ASSIGN) {
				//x +=3 deve essere costruito sull'AST come x = x + 3
				NodeDeref nodeDeref = new NodeDeref(nodeId);
				LangOper type;
				switch(op.getValue()) {
				case "+=" -> {
					type = LangOper.PLUS;
				}
				case "-=" -> {
					type = LangOper.MINUS;
				}
				case "*=" -> {
					type = LangOper.TIMES;
				}
				case "/=" -> {
					type = LangOper.DIVIDE;
				}
				default -> throw new SyntacticException("Issue in ");
				}
				NodeBinOp nodeBinOp = new NodeBinOp(type, nodeDeref, parseExp());
				return new NodeAssign(nodeId, nodeBinOp);
			}
			//sono nel caso base operatore non  composito ->(parseExp gestisce tutto)
			NodeExpr nodeExpr = parseExp();
			match(TokenType.SEMI);
			NodeAssign nodeAssign = new NodeAssign(nodeId, nodeExpr);
			return nodeAssign;
		}	
		case PRINT ->{
			match(TokenType.PRINT);
			nodeId = new NodeId(match(TokenType.ID).getValue());
			match(TokenType.SEMI);
			NodePrint nodePrint = new NodePrint(nodeId);
			return nodePrint;
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
			NodeExpr left = parseTr();
			NodeExpr full = parseExpP(left);
			return full;
		}				
		default -> throw new SyntacticException("Issue in parseExp");
		}
	}
	//10.11.12
	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		NodeExpr exp;
		NodeExpr expP;
		
		switch(token.getType()) {
		case PLUS ->{
			match(TokenType.PLUS);
			exp = parseTr();
			expP = parseExpP(exp);
			return new NodeBinOp(LangOper.PLUS, left, expP);
		}
		case MINUS ->{
			match(TokenType.MINUS);
			exp = parseTr();
			expP = parseExpP(exp);
			return new NodeBinOp(LangOper.MINUS, left, expP);
		}
		case SEMI ->{
			return left;
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
	private Token parseOp() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();
		Token op;

		switch(token.getType()) {
		case ASSIGN ->{
			op = match(TokenType.ASSIGN);
			return op;
		}
		case OP_ASSIGN ->{
			op = match(TokenType.OP_ASSIGN);
			return op;
		}
		default ->throw new SyntacticException("Issue in parseOp");
		}
	}


}
