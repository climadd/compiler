package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ast.LangOper;
import ast.LangType;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;

//testing the correct generation of AST nodes
public class TestAST {

	String path = "./src/test/data/testAST/";


	@Test
	public void testAST0() {

	}

	@Test
	public void testAST1() {

	}

	@Test
	public void testAST2() {

	}

	@Test
	public void testAST3() {

	}

	@Test
	public void testTestoAST() throws FileNotFoundException, SyntacticException, LexicalException {
		Scanner scanner = new Scanner("./src/test/data/testAST/testo.txt");
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parse();


		NodeId tempId = new NodeId("temp");
		NodeDecl tempDecl = new NodeDecl(tempId, LangType.TYINT, null);

		NodeBinOp tempAssignExpr = new NodeBinOp(
				LangOper.PLUS,
				new NodeDeref(tempId),
				new NodeConst("7", LangType.TYINT)
				);
		NodeStm tempAssign = new NodeAssign(tempId, tempAssignExpr);

		NodeId temp1Id = new NodeId("temp");
		NodeDecl temp1Decl = new NodeDecl(temp1Id, LangType.TYFLOAT, null);

		NodeBinOp mulExpr = new NodeBinOp(
				LangOper.TIMES,
				new NodeConst("7", LangType.TYINT),
				new NodeConst("5", LangType.TYINT)
				);
		NodeBinOp plusExpr = new NodeBinOp(
				LangOper.PLUS,
				new NodeConst("3", LangType.TYINT),
				mulExpr
				);
		NodeBinOp divExpr = new NodeBinOp(
				LangOper.DIVIDE,
				new NodeConst("6", LangType.TYINT),
				new NodeConst("4", LangType.TYINT)
				);
		NodeBinOp finalExpr = new NodeBinOp(
				LangOper.MINUS,
				plusExpr,
				divExpr
				);
		NodeStm temp1Assign = new NodeAssign(temp1Id, finalExpr);

		ArrayList<NodeDecSt> statements = new ArrayList<>();
		statements.add(tempDecl);
		statements.add(tempAssign);
		statements.add(temp1Decl);
		statements.add(temp1Assign);

		NodeProgram expectedAST = new NodeProgram(statements);

		assertEquals(expectedAST.toString(), nodeProgram.toString());  
	}



	//	   @Test
	//	    void testParserComplexAST() throws IOException, SyntacticException, LexicalException {
	//	        // Configurazione dello scanner con un input simulato
	//	        Scanner scanner = new Scanner(path + "/testAST.txt");
	//	        Parser parser = new Parser(scanner);
	//	        NodeProgram nodeProgram = parser.parse();
	//
	//	        // Costruzione manuale dell'AST atteso
	//	        NodeId tempId = new NodeId("temp");
	//
	//	        NodeExpr value3 = new NodeConst("3", LangType.TYINT);
	//	        NodeExpr value5 = new NodeConst("5", LangType.TYINT);
	//	        NodeExpr value7 = new NodeConst("7", LangType.TYINT);
	//	        NodeExpr value8_3 = new NodeConst("8.3", LangType.TYFLOAT);
	//
	//	        NodeExpr timesExpr = new NodeBinOp(LangOper.TIMES, value5, value7);
	//	        NodeExpr divideExpr = new NodeBinOp(LangOper.DIVIDE, timesExpr, value8_3);
	//	        NodeExpr plusExpr = new NodeBinOp(LangOper.PLUS, value3, divideExpr);
	//
	//	        NodeExpr derefTemp = new NodeDeref(tempId);
	//	        NodeExpr minusExpr = new NodeBinOp(LangOper.MINUS, plusExpr, derefTemp);
	//
	//	        NodeDecl nodeDecl = new NodeDecl(tempId, LangType.TYINT, minusExpr);
	//	        ArrayList<NodeDecSt> declarations = new ArrayList<>();
	//	        declarations.add(nodeDecl);
	//
	//	        NodeProgram expectedAST = new NodeProgram(declarations);
	//
	//	        assertEquals(expectedAST.toString(), nodeProgram.toString());
	//	    }
	//
	//	    @Test
	//	    void testParserASTPrintAndAssign() throws IOException, SyntacticException, LexicalException {
	//	        // Configura lo scanner con input simulato
	//	        Scanner scanner = new Scanner(INPUT_DIR + "/testAST2.txt");
	//	        Parser parser = new Parser(scanner);
	//	        NodeProgram nodeProgram = parser.parse();
	//	    
	//	        // Costruzione manuale dell'AST atteso
	//	        NodeId aId = new NodeId("a");
	//	    
	//	        // Nodo per il comando "print a;"
	//	        NodeStm printStmt = new NodePrint(aId);
	//	    
	//	        // Nodo per l'assegnazione "a = 5 + 7 * 3;"
	//	        NodeExpr value5 = new NodeConst("5", LangType.TYINT);
	//	        NodeExpr value7 = new NodeConst("7", LangType.TYINT);
	//	        NodeExpr value3 = new NodeConst("3", LangType.TYINT);
	//	    
	//	        NodeExpr timesExpr = new NodeBinOp(LangOper.TIMES, value7, value3);
	//	        NodeExpr plusExpr = new NodeBinOp(LangOper.PLUS, value5, timesExpr);
	//	    
	//	        NodeStm assignStmt = new NodeAssign(aId, plusExpr);
	//	    
	//	        // Costruzione del programma
	//	        ArrayList<NodeDecSt> statements = new ArrayList<>();
	//	        statements.add(printStmt);
	//	        statements.add(assignStmt);
	//	    
	//	        NodeProgram expectedAST = new NodeProgram(statements);
	//	    
	//	        assertEquals(expectedAST.toString(), nodeProgram.toString());
	//	    }

}
