package visitor;

import ast.LangOper;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import typeChecking.DescEnumType;
import typeChecking.TypeDescriptor;

/**
 * The class implements the IVisitor and defines its visit methods in order to traverse the AST's
 * nodes to validate the type compatibility of the instructions.
 * 
 * @author Lorenzo Mirto Bertoldo (github.com/climadd)
 */
public class TypeCheckingVisitor implements IVisitor{

	private TypeDescriptor resultType; //keeps the result of the last visited node
	private String errorMsg = ""; //accumulates error messages

	/**
	 * Initializes symbol table while constructing the TypeCheckingVisitor
	 */
	public TypeCheckingVisitor() {
		super();
		SymbolTable.init();
	}

	public TypeDescriptor getResultType() {
		return resultType;
	}

	//VISIT METHODS	
	/**
	 * Visits a program node and validates all declarations/statements.
	 * 
	 * @param node the program node
	 */
	@Override
	public void visit(NodeProgram node) {
		for (NodeDecSt decSt : node.getDecSts()) {
			decSt.accept(this);
		}
	}

	/**
	 * Visits an identifier node and retrieves its type from the symbol table.
	 * 
	 * @param node the identifier node
	 */
	@Override	//fatto
	public void visit(NodeId node) {
		String id = node.getName();
		Attributes type = SymbolTable.lookup(id);
		if(type != null) {
			switch(type.getType()) {
			case TYINT -> resultType = new TypeDescriptor(DescEnumType.INT);
			case TYFLOAT -> resultType = new TypeDescriptor(DescEnumType.FLOAT);
			}
		} else {
			errorMsg += "Variable '" + id + "' has not been properly declared.\n";
			resultType = new TypeDescriptor(DescEnumType.ERROR, errorMsg);
			return;
		}
	}



	/**
	 * Visits a constant node and sets its type based on the constant type.
	 * 
	 * @param node the constant node
	 */
	@Override	//fatto
	public void visit(NodeConst node) {

		switch(node.getType()) {
		case TYINT -> resultType = new TypeDescriptor(DescEnumType.INT);
		case TYFLOAT -> resultType = new TypeDescriptor(DescEnumType.FLOAT);
		}
	}

    /**
     * Visits a dereference node and validates the identifier it refers to.
     * 
     * @param node the dereference node
     */
	@Override	//fatto
	public void visit(NodeDeref node) {
		node.getId().accept(this);

	}

    /**
     * Visits a binary operation node, checks operand types, and sets the result type.
     * Handles implicit float division if needed.
     * 
     * @param node the binary operation node
     */
	@Override	//fatto
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		TypeDescriptor leftTD = resultType;
		node.getRight().accept(this);
		TypeDescriptor rightTD = resultType;

		if(leftTD.getType() == DescEnumType.ERROR) {
			resultType = leftTD;
			return;
		}
		if(rightTD.getType() == DescEnumType.ERROR) {
			resultType = rightTD;
			return;
		}
		if(leftTD.getType() == DescEnumType.INT && rightTD.getType() == DescEnumType.INT ) {
			resultType = new TypeDescriptor(DescEnumType.INT);
		}
		else {
			resultType = new TypeDescriptor(DescEnumType.FLOAT);

			//changing the node generated from the parsing in order to handle the FLOAT divisions
			if (node.getOp() == LangOper.DIVIDE) {
				node.setOp(LangOper.DIVFLOAT);
			}
		}
	}

    /**
     * Visits a declaration node, validates its type, and checks for initialization consistency.
     * 
     * @param node the declaration node
     */
	@Override //fatto
	public void visit(NodeDecl node) {
		String id = node.getId().getName();
		TypeDescriptor typeToCompare;

		switch(node.getType()) {
		case TYINT -> typeToCompare = new TypeDescriptor(DescEnumType.INT);
		case TYFLOAT -> typeToCompare = new TypeDescriptor(DescEnumType.FLOAT);
		default -> {
			new TypeDescriptor(DescEnumType.ERROR);
			return;
		}
		}
		Attributes entry = new Attributes(node.getType());
		if (SymbolTable.enter(id, entry)) {
			if(node.getInit() == null) {
				resultType = new TypeDescriptor(DescEnumType.VOID);
				return;
			}
			else {
				node.getInit().accept(this);
				TypeDescriptor paramether = resultType;

				if(typeToCompare.isCompatible(paramether)) {
					resultType = new TypeDescriptor(DescEnumType.VOID);
				}
				else resultType = new TypeDescriptor(DescEnumType.ERROR);
			}
		}
		else {
			errorMsg += "Variable '" + node.getId().getName() + "' has already been declared.\n";
			resultType = new TypeDescriptor(DescEnumType.ERROR, errorMsg);
			return;
		}
	}

    /**
     * Visits an assignment node and validates compatibility between identifier and expression types.
     * 
     * @param node the assignment node
     */
	@Override	//fatto
	public void visit(NodeAssign node) {
		node.getId().accept(this);
		TypeDescriptor id = resultType;
		node.getExpr().accept(this);
		TypeDescriptor expr = resultType;

		if (id.getType() == DescEnumType.ERROR) {
			resultType = id;
			return;
		}
		if (expr.getType() == DescEnumType.ERROR) {
			resultType = expr;
			return;
		}
		else {
			if (id.isCompatible(expr)){
				resultType = new TypeDescriptor(DescEnumType.VOID);
			}
			else {
				errorMsg += "Unable to assign a float value to '" + node.getId().getName() + "' variable./n";
				resultType = new TypeDescriptor(DescEnumType.ERROR, errorMsg);
				return;
			}
		}
	}

    /**
     * Visits a print node and ensures the identifier being printed has a valid type.
     * 
     * @param node the print node
     */
	@Override	//fatto
	public void visit(NodePrint node) {
		node.getId().accept(this);	
		if(resultType.getType() != DescEnumType.ERROR) {
			resultType = new TypeDescriptor(DescEnumType.VOID);
		}
	}
}
