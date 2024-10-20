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
	public void testFLOAT() throws LexicalException{

		String nextToken = testFLOAT.nextToken().toString();
		assertEquals("<FLOAT, r:1, 98.8095>", nextToken);
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
