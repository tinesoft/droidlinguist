package com.tinesoft.droidlinguist.server.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tinesoft.droidlinguist.server.json.ErrorResource;

/**
 * Custom {@link ResponseEntityExceptionHandler} for the application.
 * 
 * @author Tine Kondo
 *
 */
@ControllerAdvice
public class DroidLinguistExceptionHandler extends ResponseEntityExceptionHandler
{

	@ExceptionHandler({ SourceStringsParserException.class, TranslatedStringsBuilderException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request)
	{
		if (e instanceof SourceStringsParserException)
			return handleSourceStringsParserException((SourceStringsParserException) e, request);
		else if (e instanceof TranslatedStringsBuilderException)
			return handleTranslatedStringsBuilderException((TranslatedStringsBuilderException) e, request);

		return super.handleException(e, request);
	}

	private ResponseEntity<Object> handleSourceStringsParserException(SourceStringsParserException sspe, WebRequest request)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorResource error = new ErrorResource("Your source xml file is not valid", HttpStatus.UNPROCESSABLE_ENTITY.value(), sspe.getErrors());

		return handleExceptionInternal(sspe, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	private ResponseEntity<Object> handleTranslatedStringsBuilderException(TranslatedStringsBuilderException tsbe, WebRequest request)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorResource error = new ErrorResource("Your translation is not valid", HttpStatus.UNPROCESSABLE_ENTITY.value(), tsbe.getErrors());

		return handleExceptionInternal(tsbe, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

}
