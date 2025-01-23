package symbolTable;

/**
 * Represents the exception that occours whenever the number of available registers
 * for variable declarations has already been reached. 
 */
public class RegistersException extends Exception{

	private static final long serialVersionUID = 1L;

	public RegistersException(String msg) {
		super(msg);
	}
}
