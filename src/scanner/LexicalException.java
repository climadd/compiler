package scanner;

/**
 * Represents the exception that occurs during lexical analysis, 
 * thrown when Scanner's methods encounter invalid input and 
 * can't be tokenized properly.
 */
public class LexicalException extends Exception {

	private static final long serialVersionUID = 1L;

    /**
     * Constructor of LexicalException.
     *
     * @param msg information about the lexical error.
     */
	public LexicalException(String msg) {
		super(msg);
	}
}
