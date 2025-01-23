package ast;

import visitor.IVisitor;

/**
 *AST's Deref node
 */
public class NodeDeref extends NodeExpr{

	private NodeId id;
	
	/**
	 * constructor
	 * @param id
	 */
	public NodeDeref(NodeId id) {
		this.id = id;
	}
	
	//getter
	public NodeId getId() {
		return this.id;
	}
	
	/**
	 * toString method
	 * @returns a string that represents the node
	 */
	public String toString() {
		return("<NodeDeref: " + this.id.toString() + ">");
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
