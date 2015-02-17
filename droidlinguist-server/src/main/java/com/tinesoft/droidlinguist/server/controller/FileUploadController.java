package com.tinesoft.droidlinguist.server.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tinesoft.droidlinguist.server.exception.SourceStringsParserException;
import com.tinesoft.droidlinguist.server.json.translation.source.SourceItem;
import com.tinesoft.droidlinguist.server.json.translation.source.SourceStringsResource;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationFile;
import com.tinesoft.droidlinguist.server.service.TranslationService;

@Controller
@RequestMapping(value = "/api")
public class FileUploadController
{

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private TranslationService translationService;

	@RequestMapping(value = "/upload", //
	method = RequestMethod.POST, //
	consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, //
	produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SourceStringsResource upload(@RequestParam("sourceLang") String sourceLang, @RequestParam("targetLangs") String targetLangs,
			@RequestParam("sourceFile") MultipartFile sourceFile, @RequestParam("translator") String translator)
	{

		if (!sourceFile.isEmpty())
		{
			try
			{
				List<SourceItem> strings = translationService.parseSourceFile(sourceFile.getBytes());
				Map<String, TranslationFile> files = translationService.translate(strings, sourceLang, Arrays.asList(targetLangs.split(", ")), translator);

				return new SourceStringsResource(HttpStatus.ACCEPTED.value(), strings, files);

			}
			catch (IOException e)
			{
				LOG.error("Failed to parse uploaded file", e);
				throw new SourceStringsParserException(Arrays.asList("Server could not read your source file. Please review your file and try again"));
			}

		}
		else
		{
			LOG.error("Uploaded source file is empty");
			throw new SourceStringsParserException(Arrays.asList("Uploaded source file is empty! Please, try again with a valid file"));
		}

	}
}
