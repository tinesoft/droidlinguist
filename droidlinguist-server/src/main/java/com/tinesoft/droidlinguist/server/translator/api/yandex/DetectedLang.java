package com.tinesoft.droidlinguist.server.translator.api.yandex;

public class DetectedLang
{
	private String code;
	private String lang;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getLang()
	{
		return lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	@Override
	public String toString()
	{
		return "DetectedLang [code=" + code + ", lang=" + lang + "]";
	}

}
