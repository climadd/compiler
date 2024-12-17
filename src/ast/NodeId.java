package ast;

public class NodeId extends NodeAST{

	private String name;
	
	public NodeId(String name) {
		this.name = name;
	}
	
	public String toString() {
		return("NodeId <name : " + name + ">");
	}
}
