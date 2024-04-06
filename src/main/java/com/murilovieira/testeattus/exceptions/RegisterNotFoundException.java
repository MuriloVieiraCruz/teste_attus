package com.murilovieira.testeattus.exceptions;

public class RegisterNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegisterNotFoundException(String mensagem) {
		super(mensagem);
	}

}
