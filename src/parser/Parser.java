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

	//TODO: tutti gli errori lessicali vanno catchati

	//MATCH
	//necessario per capire in che branch delle regole grammaticali proseguire

	private Token match(TokenType type) throws LexicalException, SyntacticException {
		Token tk =scanner.peekToken();

		if(type.equals(tk.getType())) {
			System.out.println(tk);		 // per debug
			return scanner.nextToken();
		}
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
		default -> throw new SyntacticException("Issue in parsePrga riga " + token.getLine());
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
			return nodeDecSt;
		}
		default -> throw new SyntacticException("Issue in parseDSs a riga " + token.getLine());
		}
	}

	//4	
	private NodeDecl parseDcl() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		switch(token.getType()) {
		case TYFLOAT, TYINT ->{
			LangType type = parseTy();
			Token tk = match(TokenType.ID);
			NodeExpr nodeInit = parseDclP();
			NodeId nodeId = new NodeId(tk.getValue());

			return new NodeDecl(nodeId, type, nodeInit);
		}
		default -> throw new SyntacticException("Issue in parseDcl a riga " + token.getLine());
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
		default -> throw new SyntacticException("Issue in parseDclPa riga " + token.getLine());
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
				default -> throw new SyntacticException("Issue in composite operator switch in parseStm a riga " + token.getLine());
				}
				NodeBinOp nodeBinOp = new NodeBinOp(type, nodeDeref, parseExp());
				match(TokenType.SEMI);
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
		default -> throw new SyntacticException("Issue in parseDclP a riga " + token.getLine());
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
		default -> throw new SyntacticException("Issue in parseExp a riga " + token.getLine());
		}
	}
	//10.11.12
	private NodeExpr parseExpP(NodeExpr nodeExpr) throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		NodeExpr exp;
		NodeExpr expP;

		switch(token.getType()) {
		case PLUS ->{
			match(TokenType.PLUS);
			exp = parseTr();
			expP = parseExpP(exp);
			return new NodeBinOp(LangOper.PLUS, nodeExpr, expP);
		}
		case MINUS ->{
			match(TokenType.MINUS);
			exp = parseTr();
			expP = parseExpP(exp);
			return new NodeBinOp(LangOper.MINUS, nodeExpr, expP);
		}
		case SEMI ->{
			return nodeExpr;
		}
		default -> throw new SyntacticException("Issue in parseExpP a riga " + token.getLine());
		}
	}
	//13
	private NodeExpr parseTr() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		NodeExpr exp1;
		NodeExpr exp2;

		switch(token.getType()) {
		case ID, FLOAT, INT ->{
			exp1 = parseVal();
			exp2 = parseTrP(exp1);
			return exp2;
		}
		default -> throw new SyntacticException("Issue in parseTr a riga " + token.getLine());
		}
	}
	//14.15.16
	private NodeExpr parseTrP(NodeExpr nodeExpr) throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		NodeExpr exp;
		NodeExpr expP;

		switch(token.getType()) {
		case TIMES ->{
			match(TokenType.TIMES);
			exp = parseVal();
			expP = parseTrP(exp);
			return new NodeBinOp(LangOper.TIMES, nodeExpr, expP);
		}
		case DIVIDE ->{
			match(TokenType.DIVIDE);
			exp = parseVal();
			expP = parseTrP(exp);
			return new NodeBinOp(LangOper.DIVIDE, nodeExpr, expP);
		}
		case MINUS, PLUS, SEMI ->{
			return nodeExpr;
		}
		default -> throw new SyntacticException("Issue in parseTrP a riga " + token.getLine());
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
		default -> throw new SyntacticException("Issue in parseTy a riga " + token.getLine());
		}
	}

	//19.20.21
	private NodeExpr parseVal() throws SyntacticException, LexicalException {
		Token token;
		token = this.scanner.peekToken();

		String value;
		String id;

		switch(token.getType()) {
		case INT ->{
			value = match(TokenType.INT).getValue();
			return new NodeConst(value, LangType.TYINT);
		}
		case FLOAT ->{
			value = match(TokenType.FLOAT).getValue();
			return new NodeConst(value, LangType.TYFLOAT);	
		}
		case ID ->{
			id = match(TokenType.ID).getValue();
			return new NodeDeref(new NodeId(id));
		}
		default -> throw new SyntacticException("Issue in parseVal a riga " + token.getLine());
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
		default ->throw new SyntacticException("Issue in parseOp a riga " + token.getLine());
		}
	}


}
