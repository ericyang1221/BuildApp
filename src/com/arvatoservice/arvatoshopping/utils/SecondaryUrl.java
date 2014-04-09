package com.arvatoservice.arvatoshopping.utils;

public class SecondaryUrl {
	private final String EXPR = "=\\?";
	private final String EQUAL = "=";
	private String url;
	private String[] paraArr;

	public SecondaryUrl(String url, String[] paraArr, int mode)
	{
		this.url = url;
		this.paraArr = paraArr;
		addPara(mode);
	}

	private void addPara(int mode)
	{
		if (url != null && paraArr != null)
		{
			String equal = EQUAL;
			switch (mode)
			{
			case Constants.SECONDARYURL_MODE_FILENAME:
				equal = "";
				break;
			case Constants.SECONDARYURL_MODE_PARA:
				equal = EQUAL;
				break;
			}
			for (int i = 0; i < paraArr.length; i++)
			{
				String paraWithEqual = equal + paraArr[i];
				url = url.replaceFirst(EXPR, paraWithEqual);
			}
			if (url.indexOf(EXPR) != -1)
			{
				throw new IllegalArgumentException(
						"Parameter didn't match the url.");
			}
		}
	}

	@Override
	public String toString()
	{
		return url;
	}
}
