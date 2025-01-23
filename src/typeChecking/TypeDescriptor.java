package typeChecking;

/**
 * The TypeDescriptor class represents the type information for nodes during type checking.
 * 
 * <p>If no type errors are present, it specifies whether the type is {@code int}, {@code float}, 
 * or {@code void}.</p>
 * 
 * <p>If type errors exist, it contains an error message describing the issue.</p>
 */
public class TypeDescriptor {

	private DescEnumType type;
	private String msg;

	/**
	 * constructor
	 * @param type
	 */
	public TypeDescriptor(DescEnumType type) {
		this.type = type;
	}

	public TypeDescriptor(DescEnumType type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	//getters
	public DescEnumType getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

	/**  
	 * Method for Assignments (or a Declarations with assignment) whenever DescEnumTypes don't match. 
	 * The logic is the following<p>
	 * 
	 * <li>this.object FLOAT (paramether INT) -> true
	 * <li>this.object INT (paramether FLOAT) -> false
	 *
	 *@param candidate
	 *@returns true if the type is compatible
	 */
	public boolean isCompatible(TypeDescriptor candidate) {	
		if(candidate.getType() == DescEnumType.INT && this.getType() == DescEnumType.FLOAT)  {
			return true;
		}
		else return false;
	}
}
