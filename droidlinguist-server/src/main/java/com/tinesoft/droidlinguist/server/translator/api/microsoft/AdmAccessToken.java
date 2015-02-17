package com.tinesoft.droidlinguist.server.translator.api.microsoft;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Azure Data Market (ADM) Access Token Object.
 * 
 * @author Tine Kondo
 * @see <a
 *      href="https://msdn.microsoft.com/en-us/library/hh454950.aspx">Obtaining
 *      an Access Token on MSDN</a>
 */
public class AdmAccessToken implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accessToken;
	private String tokenType;
	private int expiresIn;
	private String scope;

	public AdmAccessToken()
	{
	}

	/**
	 * Constructor
	 * 
	 * @param accessToken
	 * @param tokenType
	 * @param expiresIn
	 * @param scope
	 */
	public AdmAccessToken(String accessToken, String tokenType, int expiresIn, String scope)
	{
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.scope = scope;
	}

	/**
	 * The access token that you can use to authenticate you access to the
	 * Microsoft Translator API.
	 * 
	 * @return the access token
	 */
	@JsonProperty("access_token")
	public String getAccessToken()
	{
		return accessToken;
	}

	@JsonProperty("access_token")
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	/**
	 * The format of the access token. Currently, Azure DataMarket returns <br/>
	 * <a href="http://schemas.xmlsoap.org/ws/2009/11/swt-token-profile-1.0">
	 * http://schemas.xmlsoap.org/ws/2009/11/swt-token-profile-1.0</a> , <br/>
	 * which indicates that a Simple Web Token (SWT) token will be returned.
	 * 
	 * @return the token type
	 */
	@JsonProperty("token_type")
	public String getTokenType()
	{
		return tokenType;
	}

	@JsonProperty("token_type")
	public void setTokenType(String tokenType)
	{
		this.tokenType = tokenType;
	}

	/**
	 * The number of seconds for which the access token is valid.
	 * 
	 * @return the expiration time in milliseconds
	 */
	@JsonProperty("expires_in")
	public int getExpiresIn()
	{
		return expiresIn;
	}

	@JsonProperty("expires_in")
	public void setExpiresIn(int expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	/**
	 * The domain for which this token is valid. For the Microsoft Translator
	 * API, the domain is <a
	 * href="http://api.microsofttranslator.com">http://api
	 * .microsofttranslator.com</a>.
	 * 
	 * @return the scope
	 */
	@JsonProperty("scope")
	public String getScope()
	{
		return scope;
	}

	@JsonProperty("scope")
	public void setScope(String scope)
	{
		this.scope = scope;
	}

	@Override
	public String toString()
	{
		return "AdmAccessToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + "]";
	}

}
