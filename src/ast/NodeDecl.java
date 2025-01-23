package ast;

import visitor.IVisitor;

/**
 * AST's node structure for declarations
 */
public class NodeDecl extends NodeDecSt{

	private NodeId id;
	private LangType type;
	private NodeExpr init;

	/**
	 * constructor
	 * @param id
	 * @param type
	 * @param init
	 */
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

	/**
	 * toString method. since the initialization field is optional StringBuilder is used for its logic
	 * @returns a string that represents the node
	 */
	public String toString() {
		StringBuilder rString = new StringBuilder( "<NodeDecl: " + getId().toString() + "," + getType().toString());
		if(this.init != null) {
			rString.append("," + this.init.toString());
		}
		rString.append(">");

		return(rString.toString());
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
