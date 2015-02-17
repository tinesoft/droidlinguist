package com.tinesoft.droidlinguist.server.json;

import java.util.List;

/**
 * 
 * @author Tine Kondo
 *
 */
public class ErrorResource
{
	private String message;
	private int statusCode;
	private List<String> errors;

	public ErrorResource()
	{
	}

	public ErrorResource(String message, int statusCode, List<String> errors)
	{
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.errors = errors;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	public void setErrors(List<String> errors)
	{
		this.errors = errors;
	}

}