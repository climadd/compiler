package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import token.*;


public class Scanner {
	final char EOF = (char) -1;
	private int riga;
	private PushbackReader buffer;
	private String log;

	private ArrayList<Character> skip = new ArrayList<>(Arrays.asList(' ', '\n','\t','\r',EOF));
	// skip: insieme caratteri di skip
	private ArrayList<Character> letters = new ArrayList<>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
	// letters: insieme caratteri di lettere
	private ArrayList<Character> digits = new ArrayList<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9'));
	// digits:insieme di cifre

	HashMap<Character, TokenType> charMap = new HashMap<>(){{
		put('+',TokenType.PLUS);
		put('-',TokenType.MINUS);
		put('*',TokenType.TIMES);
		put('/',TokenType.DIVIDE);
		put('=',TokenType.OP_ASSIGN);
		put(';',TokenType.SEMI);
	}};
	//Hashmap operatori
	HashMap<String, TokenType> keyMap = new HashMap<>(){{
		put("print",TokenType.PRINT);
		put("int",TokenType.TYINT);
		put("float",TokenType.TYFLOAT);
	}};
	//Hashmap parole chiave

	public Scanner(String fileName) throws IOException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
	}
	//costruttore che crea la PushbackReader di nome buffer


	public Token nextToken() throws IOException {

		// nextChar contiene il prossimo carattere dell'input (non consumato).
		char nextChar = peekChar(); //Catturate l'eccezione IOException e
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


	}

	// private Token scanNumber()

	// private Token scanId()

	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}

