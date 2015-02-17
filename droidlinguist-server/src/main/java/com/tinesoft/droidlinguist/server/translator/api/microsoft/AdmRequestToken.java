package com.tinesoft.droidlinguist.server.translator.api.microsoft;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Azure Data Market (ADM) Request Token Object.
 * 
 * @author Tine Kondo
 * @see <a
 *      href="https://msdn.microsoft.com/en-us/library/hh454950.aspx">Obtaining
 *      an Access Token on MSDN</a>
 */
public class AdmRequestToken implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";

	private String clientId;
	private String clientSecret;
	private String scope;
	private String grantType;

	public AdmRequestToken()
	{
	}

	public AdmRequestToken(String clientId, String clientSecret)
	{
		this(clientId, clientSecret, "http://api.microsofttranslator.com", "client_credentials");
	}

	public AdmRequestToken(String clientId, String clientSecret, String scope, String grantType)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.grantType = grantType;
	}

	/**
	 * Required. The client ID that you specified when you registered your
	 * application with Azure DataMarket.
	 * 
	 * @return the client id
	 */
	@JsonProperty(CLIENT_ID)
	public String getClientId()
	{
		return clientId;
	}

	@JsonProperty(CLIENT_ID)
	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	/**
	 * Required. The client secret value that you obtained when you registered
	 * your application with Azure DataMarket.
	 * 
	 * @return the client secret
	 */
	@JsonProperty(CLIENT_SECRET)
	public String getClientSecret()
	{
		return clientSecret;
	}

	@JsonProperty(CLIENT_SECRET)
	public void setClientSecret(String clientSecret)
	{
		this.clientSecret = clientSecret;
	}

	/**
	 * Required. Use "client_credentials" as the grant_type value for the
	 * Microsoft Translator API.
	 * 
	 * @return the grant type
	 */
	@JsonProperty(GRANT_TYPE)
	public String getGrantType()
	{
		return grantType;
	}

	@JsonProperty(GRANT_TYPE)
	public void setGrantType(String grantType)
	{
		this.grantType = grantType;
	}

	/**
	 * Required. Use the URL <a
	 * href="http://api.microsofttranslator.com">http://
	 * api.microsofttranslator.com</a> as the scope value for the Microsoft
	 * Translator API.
	 * 
	 * @return the scope
	 */
	public String getScope()
	{
		return scope;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}

}
