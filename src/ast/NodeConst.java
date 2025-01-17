package ast;

import visitor.IVisitor;

public class NodeConst extends NodeExpr{

	private String value;
	private LangType type;
	
	//constructor
	public NodeConst(String value, LangType type) {
		this.value = value;
		this.type = type;
	}
	
	//getter
	public String getValue() {
		return this.value;
	}
	
	public LangType getType() {
		return this.type;
	}
	
	//toString
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
