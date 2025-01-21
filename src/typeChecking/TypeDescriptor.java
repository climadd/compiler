package typeChecking;

public class TypeDescriptor {

	/*
  		una classe TypeDescriptor che deve
	 *se non ci sono errori di tipo* dire se il tipo è intero, float, oppure void 
		(che usiamo per dichiarazioni e istruzioni corrette)

		se ci sono errori contenere un "i" messaggio di errore
	 */


	private DescEnumType type;
	private String msg;

	//constructors
	public TypeDescriptor(DescEnumType type) {
		this.type = type;
	}

	public TypeDescriptor(DescEnumType type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	//getters
	public DescEnumType getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

	/*  
	 un "metodo per la compatibilità" da usare dell’analisi dell’assegnamento e delle dich con inizializ.
	 es: nell' ASSEGNAMENTO e Dichiarazione (CON ASSEGNAMENTO) posso assegnare un int a float
	 ma NON posso assegnare un float a variabile int
	*/
	public boolean isCompatible(TypeDescriptor candidate) {
		
		if(candidate.getType() == candidate.getType()) {
			return true;
		}
		else return false;
	}
}
