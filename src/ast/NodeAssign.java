package ast;

public class NodeAssign {

	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	public String toString() {
		return("NodeAssign <" + ">");
	}
}
