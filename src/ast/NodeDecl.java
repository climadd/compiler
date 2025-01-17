package ast;

import visitor.IVisitor;

public class NodeDecl extends NodeDecSt{

	private NodeId id;
	private LangType type;
	private NodeExpr init;
	
	//constructor
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		this.id = id;
		this.type = type;
		this.init = init;
	}
	
	//getters
	public NodeId getId() {
		return this.id;
	}
	
	public LangType getType() {
		return this.type;
	}
	
	public NodeExpr getInit() {
		return this.init;
	}
	
	//toString
	public String toString() {
		return("NodeDecl <" + ">");
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
