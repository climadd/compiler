package ast;

import visitor.IVisitor;

public abstract class NodeAST {

	public NodeAST() {
	}
	
	public String toString() {
		return("<NodeAST>");
	}
	
	public abstract void accept(IVisitor visitor);

}
