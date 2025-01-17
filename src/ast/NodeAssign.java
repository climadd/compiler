package ast;

import visitor.IVisitor;

public class NodeAssign extends NodeStm{

	private NodeId id;
	private NodeExpr expr;
	
	//constructor
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	//getters
	public NodeId getId() {
		return this.id;
	}
	
	public NodeExpr getExpr() {
		return this.expr;
	}
	
	//toString
	public String toString() {
		return("NodeAssign <" + ">");
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
