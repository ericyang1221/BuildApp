package com.arvatoservice.arvatoshopping.beans;

import java.io.Serializable;

/**
 * 
 * @author Eric
 * 
 */
public class ProductDetailVideoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String thumbnail;
	private String video;

	public ProductDetailVideoBean()
	{
	}

	public ProductDetailVideoBean(String thumbnail, String video)
	{
		this.thumbnail = thumbnail;
		this.video = video;
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public String getVideo()
	{
		return video;
	}

	public void setVideo(String video)
	{
		this.video = video;
	}

}
