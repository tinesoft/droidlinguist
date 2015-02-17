package com.tinesoft.droidlinguist.server.json.translation.source;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tinesoft.droidlinguist.server.json.translation.target.TranslationFile;

public class SourceStringsResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String message;
	private List<SourceItem> strings;
	private Map<String, TranslationFile> files; // translation files by target
												// language

	public SourceStringsResource(int statusCode, List<SourceItem> strings, Map<String, TranslationFile> files) {
		super();
		this.statusCode = statusCode;
		this.strings = strings;
		this.files = files;
	}

	public SourceStringsResource(int statusCode, String message, List<SourceItem> strings,
			Map<String, TranslationFile> files) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.strings = strings;
		this.files = files;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<SourceItem> getStrings() {
		return strings;
	}

	public void setStrings(List<SourceItem> strings) {
		this.strings = strings;
	}

	/**
	 * Gets translation files by target languages
	 * 
	 * @return
	 */
	public Map<String, TranslationFile> getFiles() {
		return files;
	}

	public void setFiles(Map<String, TranslationFile> files) {
		this.files = files;
	}

}
