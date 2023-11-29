package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;


import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int riga; 			//incrementero' ogni volta che leggo un \n
	private PushbackReader buffer;
	private String log;


	public Scanner(String fileName) throws FileNotFoundException {


	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	ArrayList<Character> skip = new ArrayList<>();
	skip.add(' ');
	skip.add('\n');
	skip.add('\t');
	skip.add('\r');
	skip.add(EOF);
	 System.out.println("Array 1:" + skip);

	// letters: insieme lettere e inizializzazione
	ArrayList<Character> letters = new ArrayList<>(26);
	letters.add('a');
	letters.add('b');
	letters.add('c');
	letters.add('d');
	letters.add('e');
	letters.add('f');
	letters.add('g');
	letters.add('h');
	letters.add('i');
	letters.add('j');
	letters.add('k');
	letters.add('l');
	letters.add('m');
	letters.add('n');
	letters.add('o');
	letters.add('p');
	letters.add('q');
	letters.add('r');
	letters.add('s');
	letters.add('t');
	letters.add('u');
	letters.add('v');
	letters.add('w');
	letters.add('x');
	letters.add('y');
	letters.add('z');

	// digits: cifre e inizializzazione
	ArrayList<Character> digits = new ArrayList<>();
	digits.add('0');
	digits.add('1');
	digits.add('2');
	digits.add('3');
	digits.add('4');
	digits.add('5');
	digits.add('6');
	digits.add('7');
	digits.add('8');
	digits.add('9');

	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il TokenType corrispondente
	HashMap<Character, TokenType> opMap= new HashMap<Character, TokenType>();
	opMap.put('+',TokenType.PLUS);
	opMap.put('-',TokenType.MINUS);
	opMap.put('*',TokenType.TIMES);
	opMap.put('/',TokenType.DIVIDE);
	opMap.put('=',TokenType.SEMI);
	opMap.put(';',TokenType.OP_ASSIGN);

	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente
	HashMap<String, TokenType> keyMap= new HashMap<String, TokenType>();
	keyMap.put("print",TokenType.PRINT);
	keyMap.put("int",TokenType.TYINT);
	keyMap.put("float",TokenType.TYFLOAT);


		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		// inizializzare campi che non hanno inizializzazione
	}

	public Token nextToken()  {

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
