package visitor;


import ast.LangType;
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

	private TypeDescriptor resultType; // mantiene il risultato della visita
	private String errorMsg = "";

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
			case TYINT -> resultType = new TypeDescriptor(DescEnumType.SUCCESS);
			case TYFLOAT -> resultType = new TypeDescriptor(DescEnumType.FLOAT);
			}
		} else {
			errorMsg += "Variable '" + id + "' has not been properly declared\n";
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

	@Override
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
			// TODO: COSA DEVO FARE QUANDO LEFT E RIGHT NON MATCHANO?
		}
	}

	@Override
	public void visit(NodeDecl node) {
		String id = node.getId().getName();
		Attributes entry = new Attributes(node.getType());
		
		
		
		
	}

	@Override
	public void visit(NodeAssign node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		
		if(resultType.getType() != DescEnumType.ERROR) {
			resultType = new TypeDescriptor(DescEnumType.SUCCESS);
		}

	}

}
