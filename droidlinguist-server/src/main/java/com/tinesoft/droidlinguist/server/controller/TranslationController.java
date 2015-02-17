package com.tinesoft.droidlinguist.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinesoft.droidlinguist.server.json.translation.target.TranslationResource;
import com.tinesoft.droidlinguist.server.service.TranslationService;

@Controller
@RequestMapping(value = "/api/translation")
public class TranslationController
{
	private static final Logger LOG = LoggerFactory.getLogger(TranslationController.class);

	@Autowired
	private TranslationService translationService;

	@RequestMapping(value = "/download", //
	method = RequestMethod.POST, //
	consumes = { MediaType.APPLICATION_JSON_VALUE }, //
	produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public void download(@RequestBody TranslationResource translation, HttpServletResponse response)
	{
		LOG.info("new translation request...");
		translationService.buildTranslation(translation, response);

	}

}
