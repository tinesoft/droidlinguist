package com.tinesoft.droidlinguist.server.json.translation.target;

import java.io.Serializable;
import java.util.List;

/**
 * Translation string item value, that either represents the inner text of a
 * <b>&lt;string&gt;</b> or a <b>&lt;item &gt;</b> element in the
 * <b>strings.xml</b> file.
 * 
 * @author Tine Kondo
 *
 */
public class TranslationStringItemValue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// can be null (for 'string' and 'string-array' items )
	private String quantity;

	private String text;
	private String state;
	private List<String> errors;

	public TranslationStringItemValue()
	{
	}

	public TranslationStringItemValue(String text, String state)
	{
		this(null, text, state, null);
	}

	public TranslationStringItemValue(String quantity, String text, String state)
	{
		this(quantity, text, state, null);
	}

	public TranslationStringItemValue(String quantity, String text, String state, List<String> errors)
	{
		super();
		this.quantity = quantity;
		this.text = text;
		this.state = state;
		this.errors = errors;
	}

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

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	public void setErrors(List<String> errors)
	{
		this.errors = errors;
	}

	@Override
	public String toString()
	{
		return "TranslationFileItemValue [quantity=" + quantity + ", text=" + text + ", state=" + state + "]";
	}

}
