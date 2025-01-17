package ast;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr{

	private NodeId id;
	
	//constructor
	public NodeDeref(NodeId id) {
		this.id = id;
	}
	
	//getter
	public NodeId getId() {
		return this.id;
	}
	
	//toString
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
