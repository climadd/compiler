package typeChecking;

/**
 * Enumeration for the types of TypeDescriptor objects
 * Void can be interpreted as "ok", "success" for operations that are neither int nor float
 */
public enum DescEnumType {

	INT,
	FLOAT,
	VOID,
	ERROR
}
