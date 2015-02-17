package com.tinesoft.droidlinguist.server.tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.tinesoft.droidlinguist.server.exception.TranslatedStringsBuilderException;
import com.tinesoft.droidlinguist.server.json.translation.source.StringItemType;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationFile;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationResource;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationState;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationStringItem;
import com.tinesoft.droidlinguist.server.json.translation.target.TranslationStringItemValue;

/**
 * Component that builds the translated 'strings.xml' file for each target
 * language, and packages them into a single zip that will be streamed out in
 * the response to client.
 * 
 * @author Tine Kondo
 *
 */
@Component
public class TranslatedStringsBuilder
{
	private static final Logger LOG = LoggerFactory.getLogger(TranslatedStringsBuilder.class);

	private static final String XMLNS_XLIFF = " xmlns:xliff=\"urn:oasis:names:tc:xliff:document:1.2\"";
	private static final String XMLNS_XLIFF_MARKER = "XMLNS_XLIFF";

	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	private static final String RESOURCES_TAG_START = "<resources " + XMLNS_XLIFF_MARKER + ">\n";
	private static final String RESOURCES_TAG_END = "</resources>\n";

	private static final String STRING_TAG_START = "\t<string name=\"%s\">";
	private static final String STRING_TAG_END = "</string>\n";

	private static final String STRING_ARRAY_TAG_START = "\t<string-array name=\"%s\">\n";
	private static final String STRING_ARRAY_TAG_END = "</string-array>\n";

	private static final String ITEM_QUANTITY_TAG_START = "\t\t<item quantity=\"%s\">";
	private static final String ITEM_TAG_START = "\t\t<item>";
	private static final String ITEM_TAG_END = "</item>\n";

	private static final String PLURALS_TAG_START = "\t<plurals name=\"%s\">\n";
	private static final String PLURALS_TAG_END = "</plurals>\n";

	public void build(TranslationResource translation, HttpServletResponse response)
	{
		LOG.info("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		LOG.info(" >>> Building translated strings...");

		if (translation == null)
		{
			LOG.error(" !!! Translation object is null");
			throw new TranslatedStringsBuilderException(Arrays.asList("Invalid translation"));
		}

		// 1. validating parameters
		LOG.debug(" 1. Validating parameters...");

		String name = StringUtils.isNotBlank(translation.getName()) ? sanitizeFileName(translation.getName()) : "strings.xml";
		String sourceLang = translation.getSourceLang();
		String state = translation.getState();
		Map<String, TranslationFile> files = translation.getFiles();

		if (!TranslationState.COMPLETED.equals(TranslationState.getbyCode(state)))
		{
			LOG.warn(" !!! Translation is not in 'COMPLETED'. State was '{}'", state);
			// throw new
			// TranslatedStringsBuilderException(Arrays.asList("Incomplete translation"));
		}

		if (files == null || files.isEmpty())
		{
			LOG.warn(" !!! Translation is not valid. Target files list is empty");
			// throw new
			// TranslatedStringsBuilderException(Arrays.asList("Incomplete translation"));
		}

		// 2 .preparing the response headers
		LOG.debug(" 2. Preparing the response headers...");

		String filename = FilenameUtils.getBaseName(name) + ".zip";
		response.setContentType("application/zip");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
		response.setHeader(HttpHeaders.CONTENT_ENCODING, "utf-8");

		// 3. creating and streaming the zip files with all translated xml files
		// in the response
		LOG.debug(" 3. creating and streaming the zip files with all translated xml files in the response...");

		try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());)
		{
			for (Map.Entry<String, TranslationFile> e : files.entrySet())
			{
				String fileName = FilenameUtils.getBaseName(name) + "_" + e.getValue().getTargetLang() + ".xml";
				addTranslationFileToZip(fileName, e.getValue(), zos);
			}

			zos.finish();
			zos.flush();

		}
		catch (IOException e)
		{
			LOG.error(" !!! Translation output zip could not be created", e);
			throw new TranslatedStringsBuilderException(Arrays.asList("Failed to create zip file for translated strings"));
		}

		LOG.info(" <<< Done building translated strings.");

	}

	public static String buildTranslationFileContent(TranslationFile file)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(XML_HEADER);
		sb.append(RESOURCES_TAG_START);

		boolean useXLIFF = false;
		for (TranslationStringItem tsi : file.getStrings())
		{

			StringItemType type = StringItemType.getByName(tsi.getType());

			if (StringUtils.isNotBlank(tsi.getComment()))
				sb.append("\t<!-- ").append(tsi.getComment()).append("-->\n");

			switch (type)
			{
				case STRING:
					if (!tsi.getValues().isEmpty())
					{
						sb.append(String.format(STRING_TAG_START, tsi.getName()));
						sb.append(tsi.getValues().get(0).getText());
						sb.append(STRING_TAG_END);
					}
					// TODO add warning tsi.getValues().isEmpty()?

					break;
				case STRING_ARRAY:
					if (!tsi.getValues().isEmpty())
					{
						sb.append(String.format(STRING_ARRAY_TAG_START, tsi.getName()));
						for (TranslationStringItemValue tsiv : tsi.getValues())
						{
							sb.append(ITEM_TAG_START);
							sb.append(tsiv.getText());
							sb.append(ITEM_TAG_END);

						}
						sb.append(STRING_ARRAY_TAG_END);
					}
					// TODO add warning tsi.getValues().isEmpty()?

					break;
				case PLURALS:
					useXLIFF = true;
					if (!tsi.getValues().isEmpty())
					{
						sb.append(String.format(PLURALS_TAG_START, tsi.getName()));
						for (TranslationStringItemValue tsiv : tsi.getValues())
						{
							sb.append(String.format(ITEM_QUANTITY_TAG_START, tsiv.getQuantity()));
							sb.append(tsiv.getText());
							sb.append(ITEM_TAG_END);

						}
						sb.append(PLURALS_TAG_END);
					}
					// TODO add warning tsi.getValues().isEmpty()?

					break;
			}
		}

		sb.append(RESOURCES_TAG_END);

		// add the xmlns:xliff if needed
		return sb.toString().replace(XMLNS_XLIFF_MARKER, useXLIFF ? XMLNS_XLIFF : "");
	}

	private void addTranslationFileToZip(String name, TranslationFile file, ZipOutputStream zos) throws IOException
	{
		LOG.debug(" \t> Adding file '{}' to output zip", name);

		String fileContent = buildTranslationFileContent(file);
		ZipEntry entry = new ZipEntry(name);
		zos.putNextEntry(entry);
		zos.write(fileContent.getBytes("utf-8"));
		zos.closeEntry();

		LOG.debug(" \t< File '{}' successfully added to output zip", name);

	}

	/**
	 * Sanitizes a filename from certain chars.<br />
	 * 
	 * This method enforces the <code>forceSingleExtension</code> property and
	 * then replaces all occurrences of \, /, |, :, ?, *, &quot;, &lt;, &gt;,
	 * control chars by _ (underscore).
	 * 
	 * @param filename
	 *            a potentially 'malicious' filename
	 * @return sanitized filename
	 */
	public static String sanitizeFileName(final String filename)
	{

		return filename != null ? filename.replaceAll("\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_") : null;
	}
}
