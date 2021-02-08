package io.openshift.client;

public class ClientException extends Exception {
	private static final long serialVersionUID = -2220963755720490857L;

	public ClientException(Throwable throwable) {
        super(throwable);
    }
	
	public ClientException(final String message) {
        super(message);
    }


}
