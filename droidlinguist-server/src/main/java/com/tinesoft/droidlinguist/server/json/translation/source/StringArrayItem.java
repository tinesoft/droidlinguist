package com.tinesoft.droidlinguist.server.json.translation.source;

import java.util.List;

/**
 * Item that represents a source string of type <b>&lt;string-array&gt;</b> in
 * the <b>strings.xml</b> file.
 * 
 * @author Tine Kondo
 *
 */
public final class StringArrayItem implements SourceItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private Boolean translatable;
	private List<String> values;

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

	public List<String> getValues()
	{
		return values;
	}

	public void setValue(List<String> values)
	{
		this.values = values;
	}

	@Override
	public List<String> getValueAsText()
	{
		return values;
	}
}