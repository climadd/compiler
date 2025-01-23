package visitor;

import java.util.ArrayList;

import ast.*;
import symbolTable.Registers;
import symbolTable.RegistersException;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

public class CodeGenerationVisitor implements IVisitor{

	//TODO: cambia dcCode in stringbuilder
	private String dcCode; //storage for the current visit's code, resetted each visit
	private ArrayList<String> listDcCode; //contains each dcCode
	private String log;

	//constructor
	public  CodeGenerationVisitor() {
		super();
		SymbolTable.init();
		Registers.init();
		dcCode = "";
		listDcCode = new ArrayList<String>();
		log = "";	
	}

	//getters
	public String getCode() {
		return this.dcCode;
	}

	public String getList() {
		return String.join("", listDcCode).strip();
	}

	public String getLog() {
		return this.log;
	}


	//VISIT METHODS
	/**
	 * Visits the ArrayList of NodeDecSts present in NodeProgram as long as log is empty.
	 */
	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt decSt : node.getDecSts()) {
			if (log.isBlank()) {
				decSt.accept(this);
				listDcCode.add(dcCode);
				dcCode = "";
			}
			else {
				listDcCode.clear();
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void visit(NodeId node) {
		String id = node.getName();
		System.out.println(node.getAttributes());
		dcCode = String.valueOf(SymbolTable.lookup(id).getRegister());
	}

	/**
	 * Generates code to push the constant onto the stack.
	 */
	@Override
	public void visit(NodeConst node) {
		String value = node.getValue();

		dcCode = value;
	}

	/**
	 * Generates code to load onto the stack the register associated with the identifier.
	 */
	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);

		dcCode = "l" + dcCode;
	}

	/**
	 * Generates code for the expression on the left, then for the expression on the right, and 
	 * then for the operation. With the necessary modifications since it involves float division.
	 */
	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		String leftDc = dcCode;
		node.getRight().accept(this);
		String rightDc = dcCode;
		String op = "";	
		switch(node.getOp()) {
		case PLUS -> op = " +";
		case DIVIDE -> op = " /";
		case MINUS -> op = " -";	
		case TIMES -> op = " *";
		case DIVFLOAT -> op = " 5k / 0k ";
		}

		dcCode = leftDc + " "  + rightDc + op;
	}

	/**
	 * Generates a new register to associate with the attribute of the declared identifier 
	 * and assigns it to the identifier in the symbol table. 
	 * If there is an initialization, it must generate code as for an assignment.
	 */
	@Override
	public void visit(NodeDecl node) {
		//PRENDO NOME ID
		String id = node.getId().getName();
		
		//GUARDO NELLA SYMBLETABLE E CON LOOKUP MI FACCIO RESTITUIRE
		//L'OGG ATTRIBUTES CON INFO RELATIVE
		Attributes attribute;
		
		//PROVO A ALLOCARE UN REGISTRO
		char register;
		try {
			register = Registers.newRegister(); 
		} catch (RegistersException e) {
			log += e.getMessage();
			return;
		}
		
		//ASSOCIO IL REGISTRO ALLA VARIABILE
		node.getId().getAttributes().setRegister(register); 

		//LA DICHIARAZIONE CONTINE UNA INIZIALIZZAZ?
		if (node.getInit() != null) {
			
			//SE Sì INSERISCILA NEL dcCode
			node.getInit().accept(this);
			String init = dcCode;
			node.getId().accept(this);
			id = dcCode;

			dcCode = init + " s" + id;
		}
	}

	/**
	 * Generates code for the expression on the right side of the assignment. Stores the top 
	 * of the stack in the register associated with the identifier on the left.
	 */
	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		String exp = dcCode;
		node.getId().accept(this);
		String id = dcCode;

		dcCode = exp + " s" + id;

	}

	/**
	 * Generates code to load onto the stack the register associated with the identifier, 
	 * followed by code to print it and then remove it from the stack.
	 */
	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);

		dcCode = "l" + dcCode + " p P";

	}

}
