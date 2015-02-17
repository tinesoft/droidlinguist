package com.tinesoft.droidlinguist.server.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom {@link RuntimeException} thrown when an error occurred during the
 * parsing of the translated strings xml file sent by the user.
 * 
 * @author Tine Kondo
 *
 */
public class TranslatedStringsBuilderException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> errors = new ArrayList<>();

	public TranslatedStringsBuilderException()
	{
	}

	public TranslatedStringsBuilderException(List<String> errors)
	{
		this.errors = errors;
	}

	public void setErrors(List<String> errors)
	{
		this.errors = errors;
	}

	public List<String> getErrors()
	{
		return errors;
	}

}
