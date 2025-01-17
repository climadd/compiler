package ast;

import visitor.IVisitor;

public class NodePrint extends NodeStm{

	private NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public String toString() {
		return("NodePrint <" + ">");
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
