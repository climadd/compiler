package ast;

import visitor.IVisitor;

/**
 * abstact class parent of every other node in the AST
 */
public abstract class NodeAST {

	public NodeAST() {
	}
	
	public abstract void accept(IVisitor visitor);

}
