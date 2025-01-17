package ast;

import visitor.IVisitor;

public class NodeConst extends NodeExpr{

	private String value;
	private LangType type;
	
	public NodeConst(String value, LangType type) {
		this.value = value;
		this.type = type;
	}
	
	public String toString() {
		return("NodeConst <" + ">");
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
