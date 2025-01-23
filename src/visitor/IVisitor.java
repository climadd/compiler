package visitor;

import ast.*;

/**
 * Interface to be implemented by every visitor class. Accomodates project expansion.
 */
public interface IVisitor {

	public void visit(NodeProgram node);
	
	public void visit(NodeId node);
	
	public void visit(NodeConst node);
	
	public void visit(NodeDeref node);
	
	public void visit(NodeBinOp node);
	
	public void visit(NodeDecl node);
	
	public void visit(NodeAssign node);
	
	public void visit(NodePrint node);
	
}
