package ast;

import symbolTable.SymbolTable.Attributes;
import visitor.IVisitor;

/**
 * AST's nodes for identifiers
 */
public class NodeId extends NodeAST{

	private String name;
	private Attributes attributes;
	
	/**
	 * constructor
	 * @param name
	 */
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
	
	//setter 
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * toString method
	 * @returns a string that represents the node
	 */
	public String toString() {
		return("<NodeId : " + this.name + ">");
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
