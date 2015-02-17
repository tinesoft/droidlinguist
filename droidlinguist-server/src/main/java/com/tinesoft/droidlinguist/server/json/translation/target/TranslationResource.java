package com.tinesoft.droidlinguist.server.json.translation.target;

import java.io.Serializable;
import java.util.Map;

/**
 * Translation object.
 * 
 * @author Tine Kondo
 *
 */
public class TranslationResource implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String state;
	private String sourceLang;
	private Map<String, TranslationFile> files;

	public TranslationResource()
	{
	}

	public TranslationResource(String name, String state, String sourceLang, Map<String, TranslationFile> files)
	{
		this.name = name;
		this.state = state;
		this.sourceLang = sourceLang;
		this.files = files;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getSourceLang()
	{
		return sourceLang;
	}

	public void setSourceLang(String sourceLang)
	{
		this.sourceLang = sourceLang;
	}

	public Map<String, TranslationFile> getFiles()
	{
		return files;
	}

	public void setFiles(Map<String, TranslationFile> files)
	{
		this.files = files;
	}

	@Override
	public String toString()
	{
		return "TranslationResource [name=" + name + ", state=" + state + ", sourceLang=" + sourceLang + ", files=" + files + "]";
	}

}
