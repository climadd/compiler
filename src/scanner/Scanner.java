package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int line;
	private PushbackReader buffer;

	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	private HashSet<Character> skipChars;
	// letters: insieme lettere e inizializzazione
	private HashSet<Character> letters;
	// digits: cifre e inizializzazione
	private HashSet<Character> numbers;
	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', '=', ';' e il TokenType corrispondente
	private HashMap<Character, TokenType> charTypeMap;
	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente
	private HashMap<String, TokenType> keyWordsMap;


	//costruttore
	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		line = 1;

		this.skipChars = new HashSet<Character>();
		skipChars.add(' ');
		skipChars.add('\n');
		skipChars.add('\t');
		skipChars.add('\r');
		skipChars.add(EOF);

		this.letters = new HashSet<Character>(
				Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
				);

		this.numbers = new HashSet<Character>(
				Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
				);

		this.charTypeMap = new HashMap<Character, TokenType>();
		charTypeMap.put('+', TokenType.PLUS);
		charTypeMap.put('-', TokenType.MINUS);
		charTypeMap.put('*', TokenType.TIMES);
		charTypeMap.put('/', TokenType.DIVIDE);
		charTypeMap.put(';', TokenType.SEMI);
		charTypeMap.put('=', TokenType.OP_ASSIGN);

		this.keyWordsMap = new HashMap<String, TokenType>();
		keyWordsMap.put("print", TokenType.PRINT);
		keyWordsMap.put("int", TokenType.INT);
		keyWordsMap.put("float", TokenType.FLOAT);

	}


	// NEXT TOKEN
	// utilizza peekchar per stabilire in che parte di codice entrare. Richiama la funzione e ritorna il contenuto delle funzioni

	public Token nextToken() throws LexicalException  {	//ritorna (consumando) il prossimo token sullo stream 
		Token token;
		try {	
			char nextChar = peekChar(); 	// nextChar contiene il prossimo carattere dell'input (non consumato).

			//blocco dello skipchar
			while(skipChars.contains(nextChar)) {
				if(nextChar == EOF) {
					token = new Token(TokenType.EOF, line);
					return token;
				}
				else if(nextChar == '\n') {
					line++;
				}
				nextChar = readChar();
			}		
			//blocco lettere - scanId()
			if(letters.contains(nextChar)) {
				return scanId(nextChar);
			}
			//blocco numeri - scanNumber()
			else if(numbers.contains(nextChar)) {
				return scanNumber(nextChar);
			}			
			//blocco parole chiave - scanOperatior()
			else if(charTypeMap.containsKey(nextChar)) {
				return scanOperator(nextChar);
			}

			else {
				throw new LexicalException("Invalid Character! '" + nextChar + "' at line " + line);
			} 
		} catch (IOException e) {
			throw new LexicalException("Exception!");
		}
	}



	//SCAN NUMBER
	// che legge sia un intero che un float e ritorna il Token INUM o FNUM
	// i caratteri che leggete devono essere accumulati in una stringa
	// che verra' assegnata al campo valore del Token
	private Token scanNumber(char nextChar) throws IOException, LexicalException {
		String numString= "";
		Token token;
		boolean floatFlag = false;

		while(numbers.contains(nextChar) || nextChar == '.') {
			if(nextChar == '.') {
				if (floatFlag) {
					throw new LexicalException("Invalid Number! multiple '.' at line " + line);
				}
				floatFlag = true;
			}

			nextChar = readChar();
			numString += nextChar;		 
		}

		if(!floatFlag) { 
			token = new Token(TokenType.INT, line, numString);
		}
		else {
			token = new Token(TokenType.FLOAT, line, numString);
		}		 
		return token;
	}




	//SCANID
	// che legge tutte le lettere minuscole e ritorna un Token ID o
	// il Token associato Parola Chiave (per generare i Token per le
	// parole chiave usate l'HaskMap di corrispondenza
	//Se riconosce una KeyWord ritorna il token KEYWORD
	private Token scanId(char nextChar) throws IOException {
		String lettString = "";
		Token token;
		while(letters.contains(nextChar)) {
			Character c = readChar();		//consuma input
			lettString += c;
		} 

		if(keyWordsMap.containsKey(lettString)) {
			token = new Token(keyWordsMap.get(lettString) , line, lettString);
			return token;
		}
		else {
			token = new Token(TokenType.ID, line, lettString);
			return token;
		}
	}




	//SCAN OPERATOR
	//legge i caratteri che fanno parte di operatori e delimitatori

	private Token scanOperator(char nextChar) throws IOException {
		Token token;
		if (nextChar == '=' || nextChar == ';') {
			token = new Token(charTypeMap.get(readChar()), line);
			return token;
		}
		else {
			String multiCharOp = "";
			multiCharOp += readChar();
			char predict = peekChar();	//variabile di sostegno per l'assegnamento con operatore
			if (predict == '=') {
				multiCharOp += predict;
				token = new Token (TokenType.OP_ASSIGN, line, multiCharOp);
				readChar();
			}
			else {
				token = new Token (charTypeMap.get(nextChar), line);
			}
			return token;
		}	

	}



	/**
	 *  reads a character in the buffer by consuming it
	 * @return The next character in the buffer.
	 * @throws IOException
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 *  reads a character in the buffer without consuming it
	 * @return The next character in the buffer.
	 * @throws IOException
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
