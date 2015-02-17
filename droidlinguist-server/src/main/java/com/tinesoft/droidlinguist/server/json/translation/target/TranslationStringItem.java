package com.tinesoft.droidlinguist.server.json.translation.target;

import java.io.Serializable;
import java.util.List;

/**
 * Translation string item that either represents a <b>&lt;string&gt;</b>,
 * <b>&lt;string-array&gt;</b> or <b>&lt;plurals&gt;</b> element in the
 * translated <b>strings.xml</b> file.
 * 
 * @author Tine Kondo
 *
 */
public class TranslationStringItem implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private String comment;
	private boolean translatable;
	private List<TranslationStringItemValue> values;

	public TranslationStringItem()
	{
	}

	public TranslationStringItem(String name, String type, String comment, boolean translatable, List<TranslationStringItemValue> values)
	{
		super();
		this.name = name;
		this.type = type;
		this.comment = comment;
		this.translatable = translatable;
		this.values = values;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

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

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public List<TranslationStringItemValue> getValues()
	{
		return values;
	}

	public void setValues(List<TranslationStringItemValue> values)
	{
		this.values = values;
	}

	@Override
	public String toString()
	{
		return "TranslationFileItem [name=" + name + ", type=" + type + ", comment=" + comment + ", values=" + values + "]";
	}

}
