package com.tinesoft.droidlinguist.server.json.translation.source;

import java.util.Arrays;
import java.util.List;

/**
 * Item that represents a source string of type <b>&lt;string&gt;</b> in the
 * <b>strings.xml</b> file.
 * 
 * @author Tine Kondo
 *
 */
public class StringItem implements SourceItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private boolean translatable;
	private String value;

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isTranslatable()
	{
		return translatable;
	}

	public void setTranslatable(boolean translatable)
	{
		this.translatable = translatable;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public List<String> getValueAsText()
	{
		return Arrays.asList(value);
	}
}