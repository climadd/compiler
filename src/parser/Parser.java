package parser;

import java.util.ArrayList;

import ast.*;
import parser.SyntacticException.LexicalErrorWrapper;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;


/**
 * A parser for constructing an Abstract Syntax Tree (AST) from a sequence of tokens.
 * The class implements a parser based on the grammar rules of the language. (src/resources/ParseTable.png)
 * It validates the syntactic structure of the input while constructing Nodes in the process.
 * 
 * @author Lorenzo Mirto Bertoldo (github.com/climadd)
 */
public class Parser {

	private Scanner scanner;

	/**
	 * Constructor of a Parser object for the given file.
	 *
	 * @param the Scanner object used to tokenize the input.
	 */
	public Parser (Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Wraps every Scanner-defined method in order to handle SyntacticExceptions separately.
	 * in this case .peekToken() and .nextToken()
	 *
	 * @param action<T> which represent the method that's gonna be called
	 * @return the result of executing the scanner action.
	 * @throws SyntacticException if such exception is thrown during execution.
	 */
	private <T> T wrapScanner(LexicalErrorWrapper<T> action) throws SyntacticException {
		try {
			return action.execute();
		} catch (LexicalException e) {
			throw new SyntacticException("Lexical error occurred: " + e.getMessage());
		}
	}	


	//MATCH  
	/**
	 * Matches the next token in the input stream, with its expected TokenType.
	 * If the next token matches the expected type, it gets consumed and is returned.
	 *
	 * @param type the expected TokenType.
	 * @return the correctly matched Token.
	 * @throws SyntacticException if the next token does not match the expected type.
	 */
	private Token match(TokenType type) throws  SyntacticException {
		Token tk = wrapScanner(scanner::peekToken);

		if(type.equals(tk.getType())) {
			//			System.out.println(tk);		 // per debug
			return wrapScanner(scanner::nextToken);
		}
		else 
			throw new SyntacticException("TokenType Mismatch! peeked:" + tk.getType().toString() + "  parameter: " + type.toString() + " at line " + tk.getLine());
	}


	//PARSING METHODS
	/**
	 * Entry point for the parsing process. It parses the entire input directing the code execution 
	 * through the Grammar Rules of the Parse Table (src/../resources/ParseTable.png).
	 *
	 * @return the root node of the AST: ParsePrg.
	 * @throws SyntacticException if a syntactic error occurs during parsing.
	 */
	public NodeProgram parse() throws  SyntacticException {
		return this.parsePrg();
	}

	/**
	 * RULE #0
	 * <p>Parses a program ({@code Prg -> DSs EOF}).</p>
	 *
	 * @return a NodeProgram representing the program.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeProgram parsePrg() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);	
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

	/**
	 * RULE#1 #2 #3
	 * <p>Parses a sequence of declarations and statements.</p>
	 *   <li>{@code DSs -> Dcl DSs}</li>
	 *   <li>{@code DSs -> Stm DSs}</li>
	 *   <li>{@code DSs -> Epsilon}</li>
	 *
	 * @return a list of NodeDecSt representing the declarations and statements.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException{
		Token token;
		token = wrapScanner(this.scanner::peekToken);

		NodeDecl nodeDecl;
		ArrayList<NodeDecSt> nodeDecSt = new ArrayList<NodeDecSt>();
		NodeStm nodeStm;

		switch(token.getType()) {
		case TYFLOAT, TYINT ->{		//DSs -> Dcl DSs
			nodeDecl = parseDcl();
			nodeDecSt = parseDSs();
			nodeDecSt.add(0, nodeDecl);
		}
		case ID, PRINT ->{
			nodeStm = parseStm();
			nodeDecSt = parseDSs();
			nodeDecSt.add(0,nodeStm);
		}
		case EOF -> {
			//dont parse further
		}
		default -> throw new SyntacticException("Issue in parseDSs a riga " + token.getLine());
		}
		return nodeDecSt;
	}

	/**
	 * RULE#4
	 * <p>Parses a declaration {@code Dcl -> Ty id DclP}</p>

	 *
	 * @return a NodeDecl representing the declaration.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeDecl parseDcl() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

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

	/**
	 * RULE#5 #6
	 * <p>Parses an optional initializer for declaration.
	 *   <li>{@code DclP -> ;}</li>
	 *   <li>{@code DclP -> = Exp ;}</li>
	 *
	 * @return a list of NodeDecSt representing the declarations and statements.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeExpr parseDclP() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);
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

	/**
	 * RULE #7 #8
	 * <p>Parses a statement.
	 *   <li>{@code Stm -> id Op Exp ;}</li>
	 *   <li>{@code Stm -> print id ;}</li>
	 *
	 * @return a NodeStm representing the statement.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeStm parseStm() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

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

	/**
	 * RULE #9
	 * <p>Parses an expression ({@code Exp}).
	 * <li>{@code Exp -> Tr ExpP}</li>
	 *
	 * @return a NodeExpr representing the expression.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeExpr parseExp() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

		switch(token.getType()) {
		case ID, FLOAT, INT ->{
			NodeExpr left = parseTr();
			NodeExpr full = parseExpP(left);
			return full;
		}				
		default -> throw new SyntacticException("Issue in parseExp a riga " + token.getLine());
		}
	}

	/**
	 * RULE #10 #11 #12
	 * Parses a continuation of an expression.
	 *   <li>{@code ExpP -> + Tr ExpP}</li>
	 *   <li>{@code ExpP -> - Tr ExpP}</li>
	 *   <li>{@code ExpP -> Epsilon}</li>
	 *
	 * @param nodeExpr the left-hand side of the expression.
	 * @return a  NodeExpr representing the full expression.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeExpr parseExpP(NodeExpr nodeExpr) throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

		NodeExpr exp;
		NodeExpr expP;

		switch(token.getType()) {
		case PLUS->{
			match(TokenType.PLUS);
			exp = parseTr();
			expP = parseExpP(new NodeBinOp(LangOper.PLUS, nodeExpr, exp));
			return expP;
		}
		case MINUS ->{
			match(TokenType.MINUS);
			exp = parseTr();
			expP = parseExpP(new NodeBinOp(LangOper.MINUS, nodeExpr, exp));
			return expP;
		}
		case SEMI ->{
			return nodeExpr;
		}
		default -> throw new SyntacticException("Issue in parseExpP a riga " + token.getLine());
		}
	}

	/**
	 * RULE #13
	 * Parses a term.
	 *   <li>{@code Tr -> Val TrP}</li>
	 *
	 * @return a NodeExpr representing the term.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeExpr parseTr() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

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

	/**
	 * RULE #14 #15 #16
	 * Parses a continuation of a term.
	 * 	 <li>{@code TrP -> * Val TrP}</li>
	 *   <li>{@code TrP -> / Val TrP}</li>
	 *   <li>{@code TrP -> Epsilon}</li>
	 *
	 * @param nodeExpr the left-hand side of the term.
	 * @return a NodeExpr representing the full term.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeExpr parseTrP(NodeExpr nodeExpr) throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

		NodeExpr exp;
		NodeExpr expP;

		switch(token.getType()) {
		case TIMES ->{
			match(TokenType.TIMES);
			exp = parseVal();
			expP = parseTrP(new NodeBinOp(LangOper.TIMES, nodeExpr, exp));
			return expP;
		}
		case DIVIDE ->{
			match(TokenType.DIVIDE);
			exp = parseVal();
			expP = parseTrP(new NodeBinOp(LangOper.DIVIDE, nodeExpr, exp));
			return expP;
		}
		case MINUS, PLUS, SEMI ->{
			return nodeExpr;
		}
		default -> throw new SyntacticException("Issue in parseTrP a riga " + token.getLine());
		}
	}

	/**
	 * RULE #17 #18
	 * Parses a type.

	 *   <li>{@code Ty -> float}</li>
	 *   <li>{@code Ty -> int}</li>
	 *
	 * @return the LangType representing the type.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private LangType parseTy() throws SyntacticException{
		Token token;
		token = wrapScanner(this.scanner::peekToken);

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

	/**
	 * RULE #19 #20 #21
	 * Parses a value.
	 *   <li>{@code Val -> int}</li>
	 *   <li>{@code Val -> float}</li>
	 *   <li>{@code Val -> id}</li>
	 *
	 * @return a NodeExpr representing the value.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private NodeExpr parseVal() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);

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

	/**
	 * RULE #22 #23
	 * Parses an operator.
	 *   <li>{@code Op -> =}</li>
	 *   <li>{@code Op -> += | -= | *= | /=}</li>

	 *
	 * @return a Token representing the operator.
	 * @throws SyntacticException if a syntactic error occurs.
	 */
	private Token parseOp() throws SyntacticException {
		Token token;
		token = wrapScanner(this.scanner::peekToken);
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
