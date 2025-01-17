package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr{

	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	//costruttore
	public  NodeBinOp (LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	//getters
	public LangOper getOp() {
		return this.op;
	}
	
	public NodeExpr getLeft() {
		return this.left;
	}
	
	public NodeExpr getRight() {
		return this.right;
	}
	
	//toString
	public String toString() {
		return("NodeBinOp <" + ">");
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
