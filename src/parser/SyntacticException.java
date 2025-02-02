package parser;

import scanner.LexicalException;

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
	
	//INTERFACE FOR LEXICAL ERROR HANDLING IN PARSER
    /**
     * An interface needed to wrap methods that may throw a LexicalException.
     * <p>
     * This interface allows scanner methods to be wrapped and executed with consistent error
     * handling.
     *
     * @param <T> the type of the result returned by the wrapped method.
     */
	public interface LexicalErrorWrapper <T> {
		
	   /**
         * Executes the wrapped method, which may throw a LexicalException.
         *
         * @return the result of the executed method.
         * @throws LexicalException if a lexical error occurs.
         */
		  T execute() throws LexicalException;
		  
	}
}
