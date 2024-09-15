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
	private int riga;
	private PushbackReader buffer;
	private String log;	//non capisco bene a cosa serva
	
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
	

	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		
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
				Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9','.')
				);
		
		this.charTypeMap = new HashMap<Character, TokenType>();
		charTypeMap.put('+', TokenType.PLUS);
		charTypeMap.put('-', TokenType.MINUS);
		charTypeMap.put('*', TokenType.TIMES);
		charTypeMap.put('/', TokenType.DIVIDE);
		charTypeMap.put(';', TokenType.SEMI);
		charTypeMap.put('/', TokenType.OP_ASSIGN);

		this.keyWordsMap = new HashMap<String, TokenType>();
		keyWordsMap.put("print", TokenType.PRINT);
		keyWordsMap.put("int", TokenType.INT);
		keyWordsMap.put("float", TokenType.FLOAT);
		
	}
	
	//METODO DEL GRAFO A STATI DELLE SLIDES
	public Token nextToken() throws LexicalException  {	//ritorna (consumando) il prossimo token sullo stream 
		Token token;
		try {	
			char nextChar = peekChar(); 	// nextChar contiene il prossimo carattere dell'input (non consumato).
			
			//blocco dello skipchar
			while(skipChars.contains(nextChar)) {
				if(nextChar == '\n') {
					riga++;
				}
				else if(nextChar == EOF) {
					token = new Token(TokenType.EOF, riga);
					return token;
				}
				readChar();
			}
			
			//blocco lettere - scanId()
			if(letters.contains(nextChar)) {
				return scanId(nextChar);
			}

			// blocco numeri - scanNumber()
			else if(numbers.contains(nextChar)) {
				return scanNumber(nextChar);
			}
			//blocco operatori/delimitatori
			else if(charTypeMap.containsKey(nextChar)) {
				token = new Token(charTypeMap.get(nextChar), riga);
				return token;
			}
			
		} catch (IOException e) {
			throw new LexicalException("Exception!");
		}
		// TODO: CONTROLLO DELLA LEGALITà FINE STRINGA
		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata. 	
		throw new RuntimeException("Yet to implement");
	}
	
	
	// che legge sia un intero che un float e ritorna il Token INUM o FNUM
	// i caratteri che leggete devono essere accumulati in una stringa
	// che verra' assegnata al campo valore del Token
	
	 private Token scanNumber(char nextChar) throws IOException {
		 String numString= "";
		 Token token;
		 boolean floatFlag = false;
		 
		 while(numbers.contains(nextChar)) {
			 if(!floatFlag && numString != "") floatFlag = true; //il float deve avere 1 "." e non deve essere in prima posizione
			 Character c = readChar();
			 numString.concat(c.toString());		
		 	}
		 if(!floatFlag) { 
			 token = new Token(TokenType.INT, riga, numString);
		 }
		 else {
			 token = new Token(TokenType.FLOAT, riga, numString);
		 }
		 return token;
	 }

	 
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HaskMap di corrispondenza
	 private Token scanId(char nextChar) throws IOException {
		 String lettString = "";
		 Token token;
		 while(letters.contains(nextChar)) {
			 Character c = readChar();
			 lettString.concat(c.toString());
		 } 
		 token = new Token(TokenType.ID, riga, lettString);
		 return token;
	 }

	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
