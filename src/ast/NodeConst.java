package ast;

public class NodeConst {

	private String value;
	private LangType type;
	
	public NodeConst(String value, LangType type) {
		this.value = value;
		this.type = type;
	}
	
	public String toString() {
		return("NodeConst <" + ">");
	}
}
