package com.tinesoft.droidlinguist.server.json.translation.source;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Item that represents a source string of type <b>&lt;plurals&gt;</b> in the
 * <b>strings.xml</b> file.
 * 
 * @author Tine Kondo
 *
 */
public class PluralItem implements SourceItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String type;
	private boolean translatable;
	private List<Value> values;

	public static class Value
	{
		private String quantity;
		private String text;

		public String getQuantity()
		{
			return quantity;
		}

		public void setQuantity(String quantity)
		{
			this.quantity = quantity;
		}

		public String getText()
		{
			return text;
		}

		public void setText(String text)
		{
			this.text = text;
		}

	}

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

	public List<Value> getValues()
	{
		return values;
	}

	public void setValue(List<Value> values)
	{
		this.values = values;
	}

	@Override
	public List<String> getValueAsText()
	{
		return values.stream().map(v -> v.text).collect(Collectors.toList());
	}
}
