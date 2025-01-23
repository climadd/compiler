package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import ast.NodeProgram;
import scanner.Scanner;
import parser.Parser;
import parser.SyntacticException;
import visitor.*;


public class TestCodeGeneration {

	private String path = "./src/test/data/testCodeGeneration/";
	
	@Test
	public void testAssign() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner(path + "1_assign.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		CodeGenerationVisitor codeGenerationVisitor = new CodeGenerationVisitor();
		node.accept(codeGenerationVisitor);
			
		assertEquals("1 6 / sa la p P", codeGenerationVisitor.getList().toString());	
	}
	
	@Test
	public void testDivisions() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner(path + "2_divsioni.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		
		// i need to typecheck before code generation because of the DIVFLOAT handling
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		CodeGenerationVisitor codeGenerationVisitor = new CodeGenerationVisitor();
		node.accept(codeGenerationVisitor);
			
		assertEquals("0 sa la 1 + sa 6 sb 1.0 6 5k / 0k la lb / + sc la p P lb p P lc p P", codeGenerationVisitor.getList().toString());	
	}
	
	@Test
	public void testGeneral() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner(path + "3_generale.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		
		// i need to typecheck before code generation because of the DIVFLOAT handling
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		CodeGenerationVisitor codeGenerationVisitor = new CodeGenerationVisitor();
		node.accept(codeGenerationVisitor);
			
		assertEquals("5 3 + sa la 0.5 + sb la p P lb 4 5k / 0k sb lb p P lb 1 - sc lc lb * sc lc p P", codeGenerationVisitor.getList().toString());	
	}
	
	@Test
	public void testOutOfRegisters() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner(path + "4_registriFiniti.txt");
		Parser parse = new Parser(scanner);
		NodeProgram node = parse.parse();
		
		// i need to typecheck before code generation because of the DIVFLOAT handling
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		CodeGenerationVisitor codeGenerationVisitor = new CodeGenerationVisitor();
		node.accept(codeGenerationVisitor);
			
		assertEquals("No registers left for further declarations!", codeGenerationVisitor.getLog());	
	}
	
}
