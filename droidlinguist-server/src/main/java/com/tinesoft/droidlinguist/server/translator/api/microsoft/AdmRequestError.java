package com.tinesoft.droidlinguist.server.translator.api.microsoft;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdmRequestError
{
	private String error;
	private String errorDescription;

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	@JsonProperty("error_description")
	public String getErrorDescription()
	{
		return errorDescription;
	}

	@JsonProperty("error_description")
	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString()
	{
		return "AdmRequestError [error=" + error + ", errorDescription=" + errorDescription + "]";
	}

}
