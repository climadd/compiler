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
	//riga incrementero' ogni volta che leggo un \n
	private PushbackReader buffer;
	private String log;
	private Token nextToken;
	//ottiene il prossimo token oppure null se il prossimo token deve essere letto dal file con la nextToken

	public Scanner(String fileName) throws IOException {
		
		// skip: insieme caratteri di skip (include EOF)
		ArrayList<Character> skip = new ArrayList<>(Arrays.asList(' ', '\n','\t','\r',EOF));
		// letters: insieme caratteri di lettere
		ArrayList<Character> letters = new ArrayList<>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
		// digits:insieme di cifre
		ArrayList<Character> digits = new ArrayList<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9'));

		// opMap: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il TokenType corrispondente
		HashMap<Character, TokenType> opMap = new HashMap<Character, TokenType>();
		opMap.put('+',TokenType.PLUS);
		opMap.put('-',TokenType.MINUS);
		opMap.put('*',TokenType.TIMES);
		opMap.put('/',TokenType.DIVIDE);
		opMap.put('=',TokenType.OP_ASSIGN);
		opMap.put(';',TokenType.SEMI);

		// keyMap: mapping fra le stringhe "print", "float", "int" e il TokenType corrispondente
		HashMap<String, TokenType> keyMap = new HashMap<String, TokenType>();
		keyMap.put("print",TokenType.PRINT);
		keyMap.put("int",TokenType.TYINT);
		keyMap.put("float",TokenType.TYFLOAT);


		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		// inizializzare campi che non hanno inizializzazione
	}

	public Token nextToken()  {
			Token t = nextToken;
			nextToken = null;
			return t;
		}


		// nextChar contiene il prossimo carattere dell'input (non consumato).
		char nextChar; //Catturate l'eccezione IOException e ritornate una LexicalException che la contiene

	{
		try {
			nextChar = peekChar();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

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

