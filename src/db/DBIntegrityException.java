package db;

public class DBIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DBIntegrityException(String text) {
		super(text);
	}
	
}
