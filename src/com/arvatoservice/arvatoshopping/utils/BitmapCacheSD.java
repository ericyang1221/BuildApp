package com.arvatoservice.arvatoshopping.utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.arvatoservice.arvatoshopping.beans.BitmapCacheBean;

public class BitmapCacheSD {
	private final String SD_CACHE_PATH = Environment
			.getExternalStorageDirectory()
			+ Constants.ARVATO_ROOT
			+ Constants.L2_CACHE_PATH;
	private File dir;

	public BitmapCacheSD()
	{
		dir = new File(SD_CACHE_PATH);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}

	public void cache(BitmapCache l1Cache)
	{
		List<Entry<String, BitmapCacheBean>> list = l1Cache.toList();
		if (list != null && !list.isEmpty())
		{
			for (int i = 0; i < list.size(); i++)
			{
				Entry<String, BitmapCacheBean> e = list.get(i);
				String key = (String) e.getKey();
				String path = null;
				try
				{
					path = dir.getAbsolutePath() + "/" + Utils.getMD5(key);
				} catch (NoSuchAlgorithmException e2)
				{
					Log.e("Utils", "URL to MD5 error.");
					e2.printStackTrace();
				}
				if (path != null)
				{
					Bitmap b = e.getValue().getBitmap();
					try
					{
						Utils.saveBitmapAsPng(b, path, false);
					} catch (IOException e1)
					{
						Log.e("Utils", "L1Cache save error.");
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public boolean containsKey(Object key)
	{
		boolean ret = false;
		String[] fileName = dir.list();
		if (fileName != null)
		{
			try
			{
				String keyName = Utils.getMD5((String) key);
				for (int i = 0; i < fileName.length; i++)
				{
					if (fileName[i].equalsIgnoreCase(keyName))
					{
						ret = true;
						break;
					}
				}
			} catch (NoSuchAlgorithmException e)
			{
				Log.e("Utils", "URL to MD5 error.");
				e.printStackTrace();
				ret = false;
			}
		}
		return ret;
	}

	public Bitmap get(String key)
	{
		String path = null;
		try
		{
			path = dir.getAbsolutePath() + "/" + Utils.getMD5(key);
		} catch (NoSuchAlgorithmException e)
		{
			Log.e("Utils", "URL to MD5 error.");
			e.printStackTrace();
		}
		Bitmap bitmap = null;
		if (path != null)
		{
			bitmap = Utils.getBitmapFromSD(path);
		}
		return bitmap;
	}

	public void removeLastByLRU()
	{
		if (getTotalCacheSize() > Constants.CACHE_FOLDER_SIZE_LIMIT_IN_MB)
		{
			File[] files = dir.listFiles();
			List<File> fileList = Arrays.asList(files);
			Collections.sort(fileList, new LastModifiedDesComparator());
			for (int i = 0; i < fileList.size() / 3; i++)
			{
				fileList.get(i).delete();
			}
		}
	}

	/**
	 * Total size in MB.
	 * 
	 * @return
	 */
	private long getTotalCacheSize()
	{
		long totalSize = 0;
		File[] files = dir.listFiles();
		if (files != null)
		{
			for (int i = 0; i < files.length; i++)
			{
				totalSize += files[i].length();
			}
		}
		return totalSize / 1024 / 1024;
	}

	private class LastModifiedDesComparator implements Comparator<File> {
		@Override
		public int compare(File lhs, File rhs)
		{
			return (int) (lhs.lastModified() - rhs.lastModified());
		}
	}

}
