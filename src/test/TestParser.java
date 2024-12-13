package test;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;

public class TestParser {

	private String 	path = "./src/test/data/testParser/";

	
	Scanner scanPrimo;
	Parser parsePrimo;
	
	Scanner scanGeneral;
	Parser parseGeneral;

//	@BeforeEach
//	public void config() {
//		try {
//			scanPrimo = new Scanner(path + "primo.txt");
//			parsePrimo = new Parser(scanPrimo);
//			
//			System.out.println("percorso completo è '" + path  + "primo.txt'");
//			
//			
//		} catch (FileNotFoundException e) {
//			System.out.println("File non trovato!");
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testParser() throws SyntacticException, LexicalException, FileNotFoundException {
		scanPrimo = new Scanner(path + "primo.txt");
		parsePrimo = new Parser(scanPrimo);
		
		scanGeneral = new Scanner ("./src/test/data/testScanner/testGenerale.txt");
		parseGeneral = new Parser(scanGeneral);

		parsePrimo.parse();
		parseGeneral.parse();
		
	}

}
