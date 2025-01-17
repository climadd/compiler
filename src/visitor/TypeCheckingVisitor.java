package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;

public class TypeCheckingVisitor implements IVisitor{

	private TypeDescriptor resType; // mantiene il risultato della visita

	@Override
	public void visit(NodeProgram node) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeBinOp node) {
//		node.getLeft().accept(this);
//		TypeDescriptor leftTD = resType;
//		node.getRight().accept(this);
//		TypeDescriptor rightTD = resType;
//		if ( ......... ) / /controlli opportuni su leftTD e rightTD
//		......................
//		resType = ..... // assegna il TypeDescriptor appropriato
		
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
		// TODO Auto-generated method stub
		
	}

}
