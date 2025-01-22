package visitor;

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

public class TypeCheckingVisitor implements IVisitor{

	private TypeDescriptor resultType; //mantiene il risultato dell'ultimo nodo visitato
	private String errorMsg = ""; //accumula messaggi di errore

	//initialization
	public TypeCheckingVisitor() {
		super();
		SymbolTable.init();
	}

	public TypeDescriptor getResultType() {
		return resultType;
	}

	//METODI PER VISITA	
	@Override
	public void visit(NodeProgram node) {
		for (NodeDecSt decSt : node.getDecSts()) {
			decSt.accept(this);
		}
	}

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

	@Override	//fatto
	public void visit(NodeConst node) {

		switch(node.getType()) {
		case TYINT -> resultType = new TypeDescriptor(DescEnumType.INT);
		case TYFLOAT -> resultType = new TypeDescriptor(DescEnumType.FLOAT);
		}
	}

	@Override	//fatto
	public void visit(NodeDeref node) {
		node.getId().accept(this);

	}

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
		if(leftTD.getType() == rightTD.getType()) {
			switch(leftTD.getType()) {
			case INT -> resultType = new TypeDescriptor(DescEnumType.INT);
			case FLOAT -> resultType = new TypeDescriptor(DescEnumType.FLOAT);
			default -> throw new IllegalArgumentException("Unexpected value: " + leftTD.getType());
			}
		}
		else {
			if (leftTD.getType() != rightTD.getType()) {
				resultType = new TypeDescriptor(DescEnumType.FLOAT);
			}
		}
	}

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

	@Override	//fatto
	public void visit(NodePrint node) {
		node.getId().accept(this);	
		if(resultType.getType() != DescEnumType.ERROR) {
			resultType = new TypeDescriptor(DescEnumType.VOID);
		}
	}
}
