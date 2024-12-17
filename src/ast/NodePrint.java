package ast;

public class NodePrint extends NodeStm{

	private NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public String toString() {
		return("NodePrint <" + ">");
	}
}
