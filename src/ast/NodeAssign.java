package ast;

import visitor.IVisitor;

/**
 * AST's NodeAssign
 */
public class NodeAssign extends NodeStm{

	private NodeId id;
	private NodeExpr expr;
	
	/**
	 * constructor 
	 * @param id
	 * @param expr
	 */
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	/**
	 * getter
	 * @returns id field
	 */
	public NodeId getId() {
		return this.id;
	}
	
	/**
	 * getter
	 * @returns expr field
	 */
	public NodeExpr getExpr() {
		return this.expr;
	}
	
	/**
	 * toString method
	 * @returns a string that represents the node
	 */
	public String toString() {
		return("<NodeAssign: " + this.id.toString() + "," + this.expr.toString() + ">");
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
