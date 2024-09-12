package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashSet;

import exceptions.LexicalException;

import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;
	private String log;

	HashSet skpChars = new HashSet();	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione

	// letters: insieme lettere e inizializzazione
	// digits: cifre e inizializzazione

	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il
	// TokenType corrispondente

	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il
	// TokenType  corrispondente

	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		// inizializzare campi che non hanno inizializzazione
	}

	public Token nextToken()  {

		// nextChar contiene il prossimo carattere dell'input (non consumato).
		char nextChar = peekChar();//Catturate l'eccezione IOException e
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
