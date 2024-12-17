package ast;

public class NodeDecl extends NodeDecStm{

	private NodeId id;
	private LangType type;
	private NodeExpr init;
	
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		this.id = id;
		this.type = type;
		this.init = init;
	}
	
	public String toString() {
		return("NodeDecl <" + ">");
	}
}
