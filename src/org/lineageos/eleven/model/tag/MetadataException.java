package org.lineageos.eleven.model.tag;

public class MetadataException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MetadataException(String message) {
		super(message);
	}
	
	public MetadataException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MetadataException(Throwable cause) {
		super(cause);
	}

}
