package com.tinesoft.droidlinguist.server.json;


public class Language
{
	private String code;
	private String name;
	private String nativeName;

	public Language()
	{
	}

	public Language(String code, String name, String nativeName)
	{
		this.code = code;
		this.name = name;
		this.nativeName = nativeName;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNativeName()
	{
		return nativeName;
	}

	public void setNativeName(String nativeName)
	{
		this.nativeName = nativeName;
	}

}
