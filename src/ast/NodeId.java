package ast;

import visitor.IVisitor;

public class NodeId extends NodeAST{

	private String name;
	
	//constructor
	public NodeId(String name) {
		this.name = name;
	}
	
	//getter
	public String getName() {
		return this.name;
	}
	
	//toString
	public String toString() {
		return("NodeId <name : " + name + ">");
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
