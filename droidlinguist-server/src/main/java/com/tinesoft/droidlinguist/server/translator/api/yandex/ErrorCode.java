package com.tinesoft.droidlinguist.server.translator.api.yandex;

/**
 * Enumeration of error codes returned by the Yandex Translator API.
 * 
 * @author Tine Kondo
 *
 */
public enum ErrorCode
{
	ERR_OK(200, "Operation completed successfully."), //
	ERR_KEY_INVALID(401, "Invalid API key."), //
	ERR_KEY_BLOCKED(402, "This API key has been blocked."), //
	ERR_DAILY_REQ_LIMIT_EXCEEDED(403, "You have reached the daily limit for requests (including calls of the detect method)."), //
	ERR_DAILY_CHAR_LIMIT_EXCEEDED(404, "You have reached the daily limit for the volume of translated text (including calls of the detect method)."), //
	ERR_TEXT_TOO_LONG(413, "The text size exceeds the maximum."), //
	ERR_UNPROCESSABLE_TEXT(422, "The text could not be translated."), //
	ERR_LANG_NOT_SUPPORTED(501, "The specified translation direction is not supported.");//

	private final int value;
	private final String description;

	private ErrorCode(int value, String description)
	{
		this.value = value;
		this.description = description;
	}

	public int getValue()
	{
		return value;
	}

	public String getDescription()
	{
		return description;
	}

}
