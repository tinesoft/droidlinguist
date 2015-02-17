package com.tinesoft.droidlinguist.server.json.translation.target;

import java.io.Serializable;
import java.util.List;

/**
 * Translation file that represents a translated <b>strings.xml</b> file in a
 * given target language.
 * 
 * @author Tine Kondo
 *
 */
public class TranslationFile implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String targetLang;
	private String state;
	private List<String> errors;
	private List<TranslationStringItem> strings;

	public TranslationFile()
	{
	}

	public TranslationFile(String targetLang, String state, List<TranslationStringItem> strings)
	{
		this(targetLang, state, strings, null);
	}

	public TranslationFile(String targetLang, String state, List<TranslationStringItem> strings, List<String> errors)
	{
		this.targetLang = targetLang;
		this.state = state;
		this.strings = strings;
		this.errors = errors;
	}

	public String getTargetLang()
	{
		return targetLang;
	}

	public void setTargetLang(String targetLang)
	{
		this.targetLang = targetLang;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public List<TranslationStringItem> getStrings()
	{
		return strings;
	}

	public void setStrings(List<TranslationStringItem> strings)
	{
		this.strings = strings;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	public void setErrors(List<String> errors)
	{
		this.errors = errors;
	}

	@Override
	public String toString()
	{
		return "TranslationFile [targetLang=" + targetLang + ", state=" + state + ", strings=" + strings + "]";
	}

}
