package com.tinesoft.droidlinguist.server.translator.api.yandex;

import java.util.List;
import java.util.Map;

public class SupportedLangs
{
	private List<String> dirs;
	private Map<String, String> langs;

	public List<String> getDirs()
	{
		return dirs;
	}

	public void setDirs(List<String> dirs)
	{
		this.dirs = dirs;
	}

	public Map<String, String> getLangs()
	{
		return langs;
	}

	public void setLangs(Map<String, String> langs)
	{
		this.langs = langs;
	}

	@Override
	public String toString()
	{
		return "SupportedLangs [dirs=" + dirs + ", langs=" + langs + "]";
	}

}
