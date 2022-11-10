package it.trionica.web.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ApplicationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ApplicationException(Exception e){
		//super("Errore applicativo interno");
		super(e.getMessage());
		log.error("Errore applicativo interno:"+ e.getMessage());
		StackTraceElement[] steArray= e.getStackTrace();
		this.setStackTrace(steArray);
	}
}
