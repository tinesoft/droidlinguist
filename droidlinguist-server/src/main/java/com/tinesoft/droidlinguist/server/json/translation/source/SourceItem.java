package com.tinesoft.droidlinguist.server.json.translation.source;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Marker interface for a {@link StringItem}, {@link StringArrayItem} or
 * {@link PluralItem}.
 * 
 * @author Tine Kondo
 *
 */
public interface SourceItem extends Serializable
{

	@JsonIgnore
	List<String> getValueAsText();

	void setType(String type);

	void setName(String name);

	String getType();

	String getName();

	boolean isTranslatable();

	void setTranslatable(boolean translatable);

}
