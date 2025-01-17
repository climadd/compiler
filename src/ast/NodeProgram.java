package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodeProgram extends NodeAST{

	private ArrayList<NodeDecSt> decSts;
	
	//costruttore
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	//getter
	public ArrayList<NodeDecSt> getDecSts(){
		return this.decSts;
	}
	
	//toString
	public String toString() {
		return("NodeProgram <" + ">");
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
