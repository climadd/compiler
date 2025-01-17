package ast;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr{

	private NodeId id;
	
	public NodeDeref(NodeId id) {
		this.id = id;
	}
	
	public String toString() {
		return("NodeDeref <" + ">");
	}
	
	/**
	 * Accepts a visitor to perform operations on this object as part of the 
	 * Visitor design pattern.
	 *
	 * @param visitor the visitor instance performing operations.
	 */
	public void accept(IVisitor visitor) {
		visitor.visit(this);
		}
}
