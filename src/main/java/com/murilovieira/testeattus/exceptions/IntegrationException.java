package com.murilovieira.testeattus.exceptions;

public class IntegrationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public IntegrationException(String mensagem) {
		super(mensagem);
	}
	
}
