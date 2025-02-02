package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import ast.*;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

public class TestParser {

	private String 	path = "./src/test/data/testParser/";

	@Test
	public void testParser() throws SyntacticException, FileNotFoundException {
		Scanner scanPrimo = new Scanner(path + "primo.txt");
		Parser parsePrimo = new Parser(scanPrimo);

		Scanner scanGenerale = new Scanner (path + "testGenerale.txt");
		Parser parseGenerale = new Parser(scanGenerale);

		parsePrimo.parse();
		parseGenerale.parse();	
	}

	@Test
	public void  testParserCorretto() throws FileNotFoundException {
		Scanner scanCorretto1 = new Scanner(path + "testParserCorretto1.txt");
		Parser parseCorretto1 = new Parser(scanCorretto1);
		assertDoesNotThrow(() -> 
		parseCorretto1.parse()		
				);

		Scanner scanCorretto2 = new Scanner(path + "testParserCorretto2.txt");
		Parser parseCorretto2 = new Parser(scanCorretto2);
		assertDoesNotThrow(() -> 
		parseCorretto2.parse()		
				);
	}

	@Test
	public void testParserEccezione() throws FileNotFoundException, SyntacticException {

		Scanner scanExc0 = new Scanner(path + "testParserEcc_0.txt");
		Parser parseExc0 = new Parser(scanExc0);
		assertThrows(parser.SyntacticException.class, () -> 
		parseExc0.parse()
				);

		Scanner scanExc1 = new Scanner(path + "testParserEcc_1.txt");
		Parser parseExc1 = new Parser(scanExc1);
		assertThrows(SyntacticException.class,() ->
		parseExc1.parse()
				);

		Scanner scanExc2 = new Scanner(path + "testParserEcc_2.txt");
		Parser parseExc2 = new Parser(scanExc2);
		assertThrows(SyntacticException.class,() ->
		parseExc2.parse()
				);

		Scanner scanExc3 = new Scanner(path + "testParserEcc_3.txt");
		Parser parseExc3 = new Parser(scanExc3);
		assertThrows(SyntacticException.class,() ->
		parseExc3.parse()
				);

		Scanner scanExc4 = new Scanner(path + "testParserEcc_4.txt");
		Parser parseExc4 = new Parser(scanExc4);
		assertThrows(SyntacticException.class,() ->
		parseExc4.parse()
				);

		Scanner scanExc5 = new Scanner(path + "testParserEcc_5.txt");
		Parser parseExc5 = new Parser(scanExc5);
		assertThrows(SyntacticException.class,() ->
		parseExc5.parse()
				);

		Scanner scanExc6 = new Scanner(path + "testParserEcc_6.txt");
		Parser parseExc6 = new Parser(scanExc6);
		assertThrows(SyntacticException.class,() ->
		parseExc6.parse()
				);

		Scanner scanExc7 = new Scanner(path + "testParserEcc_7.txt");
		Parser parseExc7 = new Parser(scanExc7);
		assertThrows(SyntacticException.class,() ->
		parseExc7.parse()
				);
	}

	@Test
	public void testSoloFiles() throws FileNotFoundException {
		Scanner scanSoloDich = new Scanner(path + "testSoloDich.txt");
		Parser parseSoloDich = new Parser(scanSoloDich);
		assertDoesNotThrow(() -> 
		parseSoloDich.parse()		
				);

		Scanner scanSoloDichPrint = new Scanner(path + "testSoloDichPrint.txt");
		Parser parseSoloDichPrint = new Parser(scanSoloDichPrint);
		assertDoesNotThrow(() -> 
		parseSoloDichPrint.parse()		
				);
	}

	
	//AST generation Tests
	@Test
	public void testParserASTGeneration() throws SyntacticException, FileNotFoundException {

		Scanner scanner = new Scanner(path + "/testAST.txt");
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parse();

		//Explicit construction of expected AST		
		NodeId nodeId = new NodeId("catamarano");
		NodeExpr value4 = new NodeConst("4", LangType.TYINT);
		NodeExpr value5 = new NodeConst("5", LangType.TYINT);
		NodeExpr value6 = new NodeConst("6", LangType.TYINT);
		NodeExpr value7_8 = new NodeConst("7.8", LangType.TYFLOAT);

		NodeExpr timesExpr = new NodeBinOp(LangOper.TIMES, value5, value6);
		NodeExpr divideExpr = new NodeBinOp(LangOper.DIVIDE, timesExpr, value7_8);
		NodeExpr plusExpr = new NodeBinOp(LangOper.PLUS, value4, divideExpr);
		NodeExpr nodeDeref = new NodeDeref(nodeId);
		NodeExpr minusExpr = new NodeBinOp(LangOper.MINUS, plusExpr, nodeDeref);

		NodeDecl nodeDecl = new NodeDecl(nodeId, LangType.TYINT, minusExpr);
		ArrayList<NodeDecSt> declarations = new ArrayList<>();
		declarations.add(nodeDecl);

		NodeProgram explicitlyConstructedAST = new NodeProgram(declarations);

		assertEquals(explicitlyConstructedAST.toString(), nodeProgram.toString());
	}
	
	@Test
	public void testParserASTGenerationPlus() throws SyntacticException, FileNotFoundException {

		Scanner scanner = new Scanner(path + "/testAST2.txt");
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parse();

		//Explicit construction of expected AST
		NodeId accelId = new NodeId("accelerator");
		NodeStm printStmt = new NodePrint(accelId); 
		
		NodeExpr value51 = new NodeConst("51", LangType.TYINT);
		NodeExpr value40 = new NodeConst("40", LangType.TYINT);
		NodeExpr value00 = new NodeConst("00", LangType.TYINT);
		
		NodeExpr timesExpr = new NodeBinOp(LangOper.TIMES, value40, value00);
		NodeExpr plusExpr = new NodeBinOp(LangOper.PLUS, value51, timesExpr);  
		NodeStm assignStmt = new NodeAssign(accelId, plusExpr);
		ArrayList<NodeDecSt> statements = new ArrayList<>();
		statements.add(printStmt);
		statements.add(assignStmt);

		NodeProgram explicitlyConstructedAST = new NodeProgram(statements);

		assertEquals(explicitlyConstructedAST.toString(), nodeProgram.toString());
	}
}
