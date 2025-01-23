package ast;

import symbolTable.SymbolTable.Attributes;
import visitor.IVisitor;

public class NodeId extends NodeAST{

	private String name;
	private Attributes attributes;
	//constructor
	public NodeId(String name) {
		this.name = name;
	}
	
	//getter
	public String getName() {
		return this.name;
	}
	public Attributes getAttributes() {
		return this.attributes;
	}
	
	//TODO: setter 
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
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
