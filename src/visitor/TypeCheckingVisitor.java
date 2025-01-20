package visitor;

import java.util.ArrayList;

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

	@Override
	public void visit(NodeProgram node) {
		for (NodeDecSt decSt : node.getDecSts()) {
			decSt.accept(this);
		}

	}

	@Override
	public void visit(NodeId node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NodeConst node) {
		// TODO Auto-generated method stub

	}

	@Override
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
		// TODO Auto-generated method stub

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
