package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

public class TestTypeChecking {

	private String path = "./src/test/data/testTypeChecking/";

	@Test
	public void testDicRipetute() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "1_dicRipetute.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals("Variable 'a' has already been declared.\n", typeCheckingVisitor.getResultType().getMsg());
	}

	@Test
	public void testiDNonDeclared() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "2_idNonDec.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals("Variable 'b' has not been properly declared.\n", typeCheckingVisitor.getResultType().getMsg());	
	}

	@Test
	public void testidNonDec() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "3_idNonDec");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals("Variable 'c' has not been properly declared.\n", typeCheckingVisitor.getResultType().getMsg());
	}
	
	@Test
	public void testTipoNonCompatibile() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "4_tipoNonCompatibile.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals("Unable to assign a float value to 'b' variable./n", typeCheckingVisitor.getResultType().getMsg());
	}
	
	@Test
	public void testCorretto1() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "5_corretto.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals(null, typeCheckingVisitor.getResultType().getMsg());
	}
	
	@Test
	public void testCorretto2() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "6_corretto.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals(null, typeCheckingVisitor.getResultType().getMsg());
	}
	
	@Test
	public void testCorretto3() throws FileNotFoundException, SyntacticException{
		Scanner scanner = new Scanner(path + "7_corretto.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);

		assertEquals(null, typeCheckingVisitor.getResultType().getMsg());
	}
}
