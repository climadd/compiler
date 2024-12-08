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

	Scanner testErroriID;
	Scanner testErroriNumbers;
	Scanner testEOF;
	Scanner testFLOAT;
	Scanner testGenerale;
	Scanner testId;
	Scanner testIdKeyWords;
	Scanner testINT;
	Scanner testKeyWords;
	Scanner testOperators;

	String path;

	@BeforeEach
	public void config() {

		try {
			path = "./src/test/data/testScanner/";

			testEOF = new Scanner(path + "testEOF.txt");	//finito
			testOperators = new Scanner(path + "testOperators.txt");	//finito
			
			testINT = new Scanner(path + "testINT.txt");	//finito
			testFLOAT = new Scanner(path + "testFLOAT.txt");	
			testErroriNumbers = new Scanner(path + "erroriNumbers.txt");

			testId = new Scanner(path + "testId.txt");	//finito
			testKeyWords = new Scanner(path + "testKeywords.txt");	//finito
			testIdKeyWords = new Scanner(path + "testIdKeyWords.txt");	//finito
			testErroriID = new Scanner(path + "erroriID.txt");
	
			testGenerale = new Scanner(path + "testGenerale.txt");
		} catch (FileNotFoundException e) {
			// Auto-generated catch block
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
				
				
				//TODO: senti la prof su accettazione prossimi 2 output
				nextString = testFLOAT.nextToken().toString(); //dovrà lanciare eccezione
//				assertEquals(null, nextString);
				nextString = testFLOAT.nextToken().toString(); //dovrà lanciare eccezione
//				assertEquals(null, nextString);
				
				nextString = testFLOAT.nextToken().toString();
				assertEquals("<FLOAT, r:5, 89.999999>", nextString); //da cambiare 5 cifre
				
				nextString = testFLOAT.nextToken().toString();
				assertEquals("<FLOAT, r:7, 6.123>",nextString);
				
				assertThrows(LexicalException.class, () -> {
					testFLOAT.nextToken();
				});
				
				//ANDRA' RIMOSSO DOPO LA GESTIONE DEI DECIMALI DOPO IL DOPPIO '.'
				nextString = testFLOAT.nextToken().toString();
				//ORA STAMPA <INT, R:8, 13>, IL DECIMALE NON CONSUMATO DEL TOKEN INVALIDO PRECEDENTE
				
				nextString = testFLOAT.nextToken().toString();
				assertEquals("<EOF, r:10>",nextString);
				
	}
	
	@Test
	public void testErroriNumbers() throws LexicalException{
		String nextString = testErroriNumbers.nextToken().toString();
		assertEquals("<INT, r:1, 0>", nextString);
		
		//TODO: il successivo dovrebbe darmi stato di errore
		nextString = testErroriNumbers.nextToken().toString();	
		System.out.println(nextString);
//		assertThrows(LexicalException.class, () -> {
//			testErroriNumbers.nextToken();
//		});
		nextString = testErroriNumbers.nextToken().toString();
		System.out.println(nextString);
	}
	
	@Test
	public void testID() throws LexicalException{
		
		String nextString = testId.nextToken().toString();
		assertEquals("<ID, r:1, jskjdsfhkjdshkf>", nextString);
		
		nextString = testId.nextToken().toString();
		assertEquals("<ID, r:2, printl>", nextString);
		
		nextString = testId.nextToken().toString();
		assertEquals("<ID, r:4, ffloat>", nextString);
		
		nextString = testId.nextToken().toString();
		assertEquals("<ID, r:6, hhhjj>", nextString);
		
		nextString = testId.nextToken().toString();
		assertEquals("<EOF, r:7>",nextString);
	}
	
	@Test
	public void testKeyWord() throws LexicalException{
		
		String nextString = testKeyWords.nextToken().toString();
		assertEquals("<PRINT, r:2>", nextString);
		
		nextString = testKeyWords.nextToken().toString();
		assertEquals("<FLOAT, r:2>", nextString);
		
		nextString = testKeyWords.nextToken().toString();
		assertEquals("<INT, r:5>", nextString);
		
		nextString = testKeyWords.nextToken().toString();
		assertEquals("<EOF, r:5>", nextString);
	}
	
	@Test
	public void testIdKeywords() throws LexicalException{
		
		String nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<INT, r:1>", nextString);
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<ID, r:1, inta>", nextString);
		
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<FLOAT, r:2>", nextString);
		
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<PRINT, r:3>", nextString);
		
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<ID, r:4, nome>", nextString);
		
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<ID, r:5, intnome>", nextString);
		
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<INT, r:6>", nextString);
		nextString = testIdKeyWords.nextToken().toString();
		assertEquals("<ID, r:6, nome>", nextString);
	}

	public void testErroriID() throws LexicalException{
		
	}
	
	@Test
	public void testGenerale() throws LexicalException{
		String nextString = testGenerale.nextToken().toString();
		assertEquals("<INT, r:1>", nextString);
		nextString = testGenerale.nextToken().toString();
		assertEquals("<ID, r:1, temp>", nextString);
		nextString = testGenerale.nextToken().toString();
		assertEquals("<SEMI, r:1>", nextString);
		
		nextString = testGenerale.nextToken().toString();
		assertEquals("<ID, r:2, temp>", nextString);	
		nextString = testGenerale.nextToken().toString();
		assertEquals("<OP_ASSIGN, r:2, +=>", nextString);
		nextString = testGenerale.nextToken().toString();
		assertEquals("<FLOAT, r:2, 5.>", nextString);
		nextString = testGenerale.nextToken().toString();
		assertEquals("<SEMI, r:2>", nextString);
		
		
	}
	
	//	@Test
	//	public void testReadChar() {
	//		//TODO: LATER ALLIGATER
	//	}
	//
	//	@Test
	//	public void testPeekChar() {
	//
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
