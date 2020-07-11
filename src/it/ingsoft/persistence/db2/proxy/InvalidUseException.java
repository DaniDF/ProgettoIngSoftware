package it.ingsoft.persistence.db2.proxy;

public class InvalidUseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidUseException()
	{
		super();
	}
	
	public InvalidUseException(String message)
	{
		super(message);
	}
	
	public InvalidUseException(Throwable cause)
	{
		super(cause);
	}
	
	public InvalidUseException(String message, Throwable cause)
	{
		super(message,cause);
	}

}
