package typeChecking;

public class TypeDescriptor {

	/*
  		una classe TypeDescriptor che deve
	 *se non ci sono errori di tipo* dire se il tipo è intero, float, oppure void 
		(che usiamo per dichiarazioni e istruzioni corrette)

		se ci sono errori contenere un "i" messaggio di errore
	 */

	private DescEnumType type;
	private String msg;

	//constructors
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
