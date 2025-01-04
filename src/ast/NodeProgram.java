package ast;

import java.util.ArrayList;

public class NodeProgram extends NodeAST{

	private ArrayList<NodeDecSt> decSts;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	public String toString() {
		return("NodeProgram <" + ">");
	}
}
