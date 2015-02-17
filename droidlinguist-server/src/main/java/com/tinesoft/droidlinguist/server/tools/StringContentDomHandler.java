package com.tinesoft.droidlinguist.server.tools;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Custom {@link DomHandler} to get the content of a <b>&lt;string&gt;</b>
 * element as string.
 * 
 * @author Tine Kondo
 *
 */
public class StringContentDomHandler implements DomHandler<String, StreamResult>
{

	private static final String STRING_START_TAG = "<string>";
	private static final String STRING_END_TAG = "</string>";

	private StringWriter xmlWriter = new StringWriter();

	public StreamResult createUnmarshaller(ValidationEventHandler errorHandler)
	{
		xmlWriter.getBuffer().setLength(0);
		return new StreamResult(xmlWriter);
	}

	public String getElement(StreamResult rt)
	{
		String xml = rt.getWriter().toString();
		int beginIndex = xml.indexOf(STRING_START_TAG) + STRING_START_TAG.length();
		int endIndex = xml.indexOf(STRING_END_TAG);
		return xml.substring(beginIndex, endIndex);
	}

	public Source marshal(String n, ValidationEventHandler errorHandler)
	{
		try
		{
			String xml = STRING_START_TAG + n.trim() + STRING_END_TAG;
			StringReader xmlReader = new StringReader(xml);
			return new StreamSource(xmlReader);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}