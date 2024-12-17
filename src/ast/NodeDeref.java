package ast;

public class NodeDeref {

	private NodeId id;
	
	public NodeDeref(NodeId id) {
		this.id = id;
	}
	
	public String toString() {
		return("NodeDeref <" + ">");
	}
}
