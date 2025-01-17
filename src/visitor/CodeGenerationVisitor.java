package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;

public class CodeGenerationVisitor implements IVisitor{

	private String codiceDc; // mantiene il codice della visita
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeDecl node) {
//		node.getLeft().accept(this);
//		String leftCodice = codiceDc;
//		node.getRight().accept(this);
//		String rightCodice = codiceDc;
//		if ( ......... ) / /controlli di errore
//		codiceDc = .......... //codice dell’espressione binaria
		
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
