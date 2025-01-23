package ast;

import visitor.IVisitor;

/**
 * AST's node for binary operations
 */
public class NodeBinOp extends NodeExpr{

	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	/**
	 * constructors
	 * @param operator
	 * @param left term
	 * @param right term
	 */
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
	
	/**
	 * setter for op field of NodeBinOp objects
	 * @param type 
	 */
	public void setOp(LangOper type) {
		this.op = type;
	}
	
	/**
	 * toString method
	 * @returns a string that represents the node
	 */
	public String toString() {
		return("<NodeBinOp: "  + this.left.toString() + "," + this.op.toString() + "," + this.right.toString() +  ">");
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
