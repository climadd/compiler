package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import scanner.Scanner;
import scanner.LexicalException;
import token.Token;
import token.TokenType;

class TestScanner {

	//Scanner erroriID
	//Scanner erroriNumbers
	Scanner testEOF;
	Scanner testFLOAT;
	Scanner testGenerale;
	Scanner testId;
	Scanner testIdKeyWords;
	Scanner testINT;
	Scanner testKeywords;
	Scanner testOperators;

	String path;

	@BeforeEach
	public void config() {

		try {
			path = "./src/test/data/testScanner/";

			testEOF = new Scanner(path + "testEOF.txt");
			testFLOAT = new Scanner(path + "testFLOAT.txt");
			testGenerale = new Scanner(path + "testGenerale.txt");
			testId = new Scanner(path + "testId.txt");
			testIdKeyWords = new Scanner(path + "testIdKeyWords.txt");
			testINT = new Scanner(path + "testINT.txt");
			testKeywords = new Scanner(path + "testKeywords.txt");
			testOperators = new Scanner(path + "testOperators.txt");	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Timeout(3)
	public void testEOF() throws LexicalException{	

		//nextToken
		Token output = testEOF.nextToken();
		assertEquals(output.getType(), TokenType.EOF);
		assertEquals(output.getLine(), 4);

		//toString()
		String nextToken = testEOF.nextToken().toString();	
		assertEquals("<EOF, r:4>", nextToken);

	}

	@Test
	public void testOperators() throws LexicalException {

		String nextString = testOperators.nextToken().toString();
		assertEquals("<PLUS, r:1>",nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:1, /=>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<MINUS, r:2>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<TIMES, r:2>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<DIVIDE, r:3>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:5, +=>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:6>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:6, -=>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<MINUS, r:8>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:8>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:8, *=>", nextString);

		nextString = testOperators.nextToken().toString();
		assertEquals("<SEMI, r:10>", nextString);
	}

	@Test
	public void testINT() throws LexicalException{

		
				String nextString = testINT.nextToken().toString();
				assertEquals("<INT, r:2, 698>", nextString);
				
				nextString = testINT.nextToken().toString();
				assertEquals("<INT, r:4, 560099>", nextString);
				
				nextString = testINT.nextToken().toString();
				assertEquals("<INT, r:5, 1234>", nextString);
		
	}

	@Test
	public void testFLOAT() throws LexicalException{
		
				String nextString = testFLOAT.nextToken().toString();
				assertEquals("<FLOAT, r:1, 98.8095>", nextString);
				
				testFLOAT.nextToken(); //dovr� lanciare eccezione
				testFLOAT.nextToken(); //dovr� lanciare eccezione
				
				nextString = testFLOAT.nextToken().toString();
				assertEquals("<FLOAT, r:5, 89.99999>", nextString);
				
	}
	
	@Test
	public void testID() throws LexicalException{
		
		String nextString = testId.nextToken().toString();
		assertEquals("<ID, r:1, jskjdsfhkjdshkf>", nextString);
		
	}
	
	@Test
	public void testKeyWord() throws LexicalException{
		
		
	}

	//	@Test
	//	public void testReadChar() {
	//		//TODO: LATER ALLIGATER
	//	}
	//
	//	@Test
	//	public void testPeekChar() {
	//		//TODO: LATER
	//	}
	//
	//	@Test
	//	public void testScanNumber() {
	//
	//	}
	//
	//	@Test
	//	public void testScanId(){
	//
	//	}
	//
	//	@Test
	//	public void testScanOperator() {
	//
	//	}
	//
	//
	//
	//	@Test
	//	public void testNextToken() {
	//
	//	}

}
