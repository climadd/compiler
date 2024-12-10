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
	private HashMap<Character, TokenType> operatorMap;
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

		this.operatorMap = new HashMap<Character, TokenType>();
		operatorMap.put('+', TokenType.PLUS);
		operatorMap.put('-', TokenType.MINUS);
		operatorMap.put('*', TokenType.TIMES);
		operatorMap.put('/', TokenType.DIVIDE);
		operatorMap.put(';', TokenType.SEMI);
		operatorMap.put('=', TokenType.OP_ASSIGN);

		this.keyWordsMap = new HashMap<String, TokenType>();
		keyWordsMap.put("print", TokenType.PRINT);
		keyWordsMap.put("int", TokenType.TYINT);
		keyWordsMap.put("float", TokenType.TYFLOAT);
	}

	/*
	  caratteri che non sono riconosciuti
	  errori nel riconoscimento di un numero:
		i) intero che inizia per zero
		ii) floating point troppo lungo oppure
		iii) numero seguito da lettera
	  errori nel riconoscimento di un identificatore 
		iv) identificatore seguito da numero.
	 */

	// NEXT TOKEN
	// utilizza peekchar per stabilire in che parte di codice entrare. Richiama la funzione e ritorna il contenuto delle funzioni
	public Token nextToken() throws LexicalException {	//ritorna (consumando) il prossimo token sullo stream 
		try {	
			char nextChar = peekChar();  //LA READ LA EFFETTUERò NEI METODI, OGNUNO LA GESTISCE IN AUTONOMIA

			//blocco dello skipChar
			while(skipChars.contains(nextChar)) {
				if(nextChar == EOF) {
					return scanEOF();
				}
				if(nextChar == '\n') {		
					this.line++;
				}			
				readChar();	
				nextChar = peekChar();
			}		

			//blocco operatori - scanOperator()
			if(operatorMap.containsKey(nextChar)) {
				return scanOperator(nextChar);
			}

			//blocco lettere - scanId()
			else if(letters.contains(nextChar)) {
				return scanId(nextChar);
			}

			//blocco numeri - scanNumber()
			else if(numbers.contains(nextChar)) {
				return scanNumber(nextChar);
			}			
			else {
				throw new LexicalException("Invalid Character! '" + nextChar + "' at line " + line);
			} 
		} catch (IOException e) {
			throw new LexicalException("Exception!");
		}
	}

	//SCAN NUMBER	FINITA 
	//(TODO: IMPLEMENTA LIMITE DECIMALI, IMPLEMENTA LETTURA POST ECCEZIONE)

	//ES: 89.999999999 VA TOKENIZZATO A 5 CIFRE DECIMALI? OPPURE LANCIATA EXCEPTION 

	private Token scanNumber(char nextChar) throws IOException, LexicalException {

		StringBuilder numString = new StringBuilder();
		nextChar = readChar(); //valore di nextChar non cambia dal peeked ma avanzo

		boolean decimalFlag = false;
		//		int decimalCount = 5;	//TODO: da implementare, max cifre oltre la virgola = 5

		while(numbers.contains(nextChar) || nextChar == '.') {
			//gestione decimal point
			if(nextChar == '.') {
				if (decimalFlag) {						
					// FIN DOVE CONSUMO L'INPUT?????? = risposta sul forum
					while(numbers.contains(nextChar)) {
						nextChar= readChar();
					}
					throw new LexicalException("Invalid Number! multiple '.' at line " + line);
				}
				decimalFlag = true;
			}

			//check su cifra piu significativa = 0
			char predict = peekChar(); //variabile di controllo
			if (!(numString.length() == 0 && nextChar == '0' && predict != '.')) {
				numString.append(nextChar);
			}			
			nextChar = readChar();
			predict = peekChar();
		}
		if(!decimalFlag) { 
			if(numString.isEmpty()) numString.append(0);	//l'unico caso in cui la stringa è vuota è 00000
			Token token = new Token(TokenType.INT, line, numString.toString());
			return token;
		}
		else {
			Token token = new Token(TokenType.FLOAT, line, numString.toString());
			return token;
		}		 
	}




	//SCANID
	// che legge tutte le lettere minuscole e ritorna un Token ID o
	// il Token associato Parola Chiave (per generare i Token per le
	// parole chiave usate l'HaskMap di corrispondenza
	//Se riconosce una KeyWord ritorna il token KEYWORD

	private Token scanId(char nextChar) throws IOException, LexicalException {

		StringBuilder idString = new StringBuilder(nextChar);	

		while(letters.contains(peekChar())) {
			nextChar = readChar();
			idString.append(nextChar);	
		} 
		//TODO: basandosi sul fatto che print2 è eccezione lessicale
		if(numbers.contains(nextChar)) {
			throw new LexicalException("Id contiene numeri");
		}
		if(keyWordsMap.containsKey(idString.toString())) {
			Token token = new Token(keyWordsMap.get(idString.toString()) , line);
			return token;
		}
		else {
			Token token = new Token(TokenType.ID, line, idString.toString());
			return token;
		}
	}



	//SCAN OPERATOR		FINITA
	//legge i caratteri che fanno parte di operatori e delimitatori

	private Token scanOperator(char nextChar) throws IOException {
		Token token;
		//check per caratteri che sono necessariamente singoli
		if (nextChar == '=' || nextChar == ';') {
			token = new Token(operatorMap.get(nextChar), line);
			nextChar = readChar();	//valore non cambia dal precedente (peeked), ma avanzo il buffer
			return token;
		}
		else {
			StringBuilder multiCharOp = new StringBuilder();
			nextChar = readChar(); //valore di nextChar non cambia dal peeked ma avanzo
			multiCharOp.append(nextChar);

			//check se compositi
			if(peekChar() == '=') {
				nextChar = readChar();
				multiCharOp.append(nextChar);
				token = new Token(TokenType.OP_ASSIGN, line, multiCharOp.toString());
			}
			else {
				// se il carattere non era composito
				token = new Token(operatorMap.get(nextChar), line);
			}
		}
		return token;
	}

	//SCAN EOF		FINITA
	private Token scanEOF() {
		Token token = new Token(TokenType.EOF, line);
		return token;
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