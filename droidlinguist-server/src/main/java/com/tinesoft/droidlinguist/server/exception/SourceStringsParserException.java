package com.tinesoft.droidlinguist.server.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom {@link RuntimeException} thrown when an error occurred during the
 * parsing of the source strings xml file uploaded by the user.
 * 
 * @author Tine Kondo
 *
 */
public class SourceStringsParserException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> errors = new ArrayList<>();

	public SourceStringsParserException()
	{
	}

	public SourceStringsParserException(List<String> errors)
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
