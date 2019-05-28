package fr.insee.bar.exception;

@SuppressWarnings("serial")
public class BarClientException extends RuntimeException {
	public BarClientException(String id) {
		super(String.format("Le client %s n'existe pas.", id));
	}
}
