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
import typeChecking.TypeDescriptor;

public class TypeCheckingVisitor implements IVisitor{

	private TypeDescriptor resultType; // mantiene il risultato della visita

	public TypeCheckingVisitor() {
		super();
		SymbolTable.init();
	}
	
	public TypeDescriptor getResultType() {
		return resultType;
	}
	
	@Override
	public void visit(NodeProgram node) {
		ArrayList<NodeDecSt> list =node.getDecSts();
		
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
		node.getLeft().accept(this);
		TypeDescriptor leftTD = resultType;
		node.getRight().accept(this);
		TypeDescriptor rightTD = resultType;
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
