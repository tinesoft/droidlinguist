package com.tinesoft.droidlinguist.server.translator.api.yandex;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslationResult
{
	private String code;
	private String lang;
	private DetectedLang detectedLang;
	private List<String> texts;

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

	@JsonProperty("text")
	public List<String> getTexts()
	{
		return texts;
	}

	@JsonProperty("text")
	public void setTexts(List<String> texts)
	{
		this.texts = texts;
	}

	@JsonProperty("detected")
	public DetectedLang getDetectedLang()
	{
		return detectedLang;
	}

	@JsonProperty("detected")
	public void setDetectedLang(DetectedLang detectedLang)
	{
		this.detectedLang = detectedLang;
	}

	@Override
	public String toString()
	{
		return "TranslationResult [code=" + code + ", lang=" + lang + ", detectedLang=" + detectedLang + ", texts=" + texts + "]";
	}

}
