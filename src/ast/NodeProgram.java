package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 * AST's node that holds every declaration and statement in it
 */
public class NodeProgram extends NodeAST{

	private ArrayList<NodeDecSt> decSts;
	
	/**
	 * constructor
	 * @param decSts
	 */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	//getter
	public ArrayList<NodeDecSt> getDecSts(){
		return this.decSts;
	}
	
	/**
	 * toString method
	 * @returns a string that represents the node
	 */
	public String toString() {
		return("<NodeProgram: " + this.decSts.toString() + ">");
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
