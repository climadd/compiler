package parser;


/**
 * Represents the exception that occurs during syntactic analysis, 
 * thrown when Parser's methods encounter invalid syntax while attempting
 * to parse a stream of tokens according to the rules of the grammar.
 */
public class SyntacticException extends Exception {

	private static final long serialVersionUID = 1L;

    /**
     * Constructor of SyntacticException.
     *
     * @param msg information about the lexical error.
     */
	public SyntacticException(String msg) {
		super(msg);
	}
	
	public SyntacticException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
