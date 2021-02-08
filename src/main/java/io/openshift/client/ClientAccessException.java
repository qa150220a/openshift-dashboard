package io.openshift.client;

public class ClientAccessException extends ClientException {
	private static final long serialVersionUID = 3041598961584996743L;

	public ClientAccessException(Throwable throwable) {
        super(throwable);
    }
	
	public ClientAccessException(final String message) {
        super(message);
    }
}
