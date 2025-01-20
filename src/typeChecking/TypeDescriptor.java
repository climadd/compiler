package typeChecking;

public class TypeDescriptor {

	/*
  		una classe TypeDescriptor che deve
		*se non ci sono errori di tipo* dire se il tipo è intero, float, oppure void 
		(che usiamo per dichiarazioni e istruzioni corrette)
		
		se ci sono errori contenere un "i" messaggio di errore
		sarà anche necessario definire un "metodo per la compatibilità" da usare
		nell’analisi dell’assegnamento e delle dichiarazioni con inizializzazione.
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
	
	public boolean isCompatible(TypeDescriptor candidate) {
		//TODO: CAPISCI COSA BISOGNA CONFRONTARE
		if(candidate.getType() == candidate.getType()) {
			return true;
		}
		else return false;
	}
}
