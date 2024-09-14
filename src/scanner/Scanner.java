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
	private String log;
	
	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	private HashSet<Character> skpChars;	
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
		
		this.skpChars = new HashSet<Character>();
		skpChars.add(' ');
		skpChars.add('\n');
		skpChars.add('\t');
		skpChars.add('\r');
		skpChars.add(EOF);
		
		this.letters = new HashSet<Character>(
			    Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
				);
				
		this.numbers = new HashSet<Character>(
				Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9')
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
	public Token nextToken() throws LexicalException  {		//ritorna (consumando) il prossimo token sullo stream 
		Token token;
		try {	
			
			char nextChar = peekChar(); 	// nextChar contiene il prossimo carattere dell'input (non consumato).
			while(skpChars.contains(nextChar)) {
				if(nextChar == EOF) {
					token = new Token(TokenType.EOF, riga);
					return token;
				}
				else if(nextChar == '\n') {
					riga++;
				}
			}
			while
			
			
			
		} catch (IOException e) {
			throw new LexicalException("Exception!");
		}
		//Catturate l'eccezione IOException e
		       // ritornate una LexicalException che la contiene
		
			
		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF


		// Se nextChar e' in letters
		// return scanId()
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HaskMap di corrispondenza

		// Se nextChar e' o in operators oppure 
		// ritorna il Token associato con l'operatore o il delimitatore

		// Se nextChar e' in numbers
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata. 

		throw new RuntimeException("TODO da implementare"); //sostituzione al return
	}

	 private Token scanNumber() {
		 
		 throw new RuntimeException("TODO da implementare");
	 }

	 private Token scanId() {
		 
		 throw new RuntimeException("TODO da implementare");
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
