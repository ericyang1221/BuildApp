package com.arvatoservice.arvatoshopping.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.arvatoservice.arvatoshopping.MyApplication;

/**
 * 
 * @author Eric
 * 
 */
public class HttpRequestHelper {
	private MyApplication myApp;

	public HttpRequestHelper(Activity activity)
	{
		this.myApp = (MyApplication) activity.getApplication();
	}

	public String sendRequestAndReturnString(String url)
	{
		System.out.println(url);
		StringBuffer response = new StringBuffer();
		// HttpClient client = new DefaultHttpClient();
		HttpClient client = myApp.getClient();
		if (client == null)
		{
			client = new DefaultHttpClient();
			Log.d("HttpRequestHelper", "DefaultHttpClient");
		}
		try
		{
			HttpResponse hr = client.execute(new HttpGet(url));
			HttpEntity entity = hr.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			String buff = null;
			while ((buff = br.readLine()) != null)
			{
				response.append(buff);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			// client.getConnectionManager().shutdown();
		}
		return response.toString();
	}

	public JSONObject sendRequestAndReturnJson(String url)
	{
		JSONObject jsonObject = null;
		try
		{
			jsonObject = new JSONObject(sendRequestAndReturnString(url).trim());
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return jsonObject;
	}

	public JSONObject sendFileAndReturnJson(String url,
			Map<String, String> params, Map<String, File> files)
	{
		JSONObject jsonObject = null;
		try
		{
			jsonObject = new JSONObject(postFileAndReturnString(url, params,
					files));
		} catch (JSONException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return jsonObject;
	}

	private String postFileAndReturnString(String url,
			Map<String, String> params, Map<String, File> files)
			throws IOException
	{
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // ������ʱ��
		conn.setDoInput(true);// ��������
		conn.setDoOutput(true);// �������
		conn.setUseCaches(false); // ������ʹ�û���
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// ������ƴ�ı����͵Ĳ���
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet())
		{
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());

		// String content = "type=" + URLEncoder.encode(type, "utf-8");
		// outStream.writeBytes(content);

		outStream.write(sb.toString().getBytes());
		// �����ļ����
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet())
			{
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1)
				{
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

		// ��������־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// �õ���Ӧ��
		int res = conn.getResponseCode();
		String ret = "";
		if (res == 200)
		{
			InputStream in = conn.getInputStream();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null)
			{
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			ret = new String(temp.toString().getBytes(), "utf-8");
		}
		System.out.println(ret);
		outStream.close();
		conn.disconnect();
		return ret;
	}
}
