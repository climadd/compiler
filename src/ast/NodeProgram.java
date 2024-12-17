package ast;

import java.util.ArrayList;

public class NodeProgram extends NodeAST{

	private ArrayList<NodeDecStm> decSts;
	
	public NodeProgram(ArrayList<NodeDecStm> decSts) {
		this.decSts = decSts;
	}
	
	public String toString() {
		return("NodeProgram <" + ">");
	}
}
