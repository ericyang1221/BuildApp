package com.arvatoservice.arvatoshopping.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.FloatMath;
import android.util.Log;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.baidu.location.BDLocation;

public class Utils {
	private static Properties props;

	private static Properties loadProperties(Context context) {
		if (props == null) {
			props = new Properties();
		}
		try {
			props.load(context.getResources().openRawResource(R.raw.configure));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	public static String getProperty(Context context, String key) {
		return loadProperties(context).getProperty(key, "");
	}

	public static Bitmap getBitmapFromSD(String path) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opt);
		return bitmap;
	}

	public static long hourToMillis(double hour) {
		return (long) hour * 60 * 60 * 1000;
	}

	public static String getMD5(String val) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] m = md5.digest();
		return getString(m);
	}

	private static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
		}
		return sb.toString();
	}

	public static String saveBitmapAsPng(Bitmap bitmap, String path,
			boolean isOverwrite) throws IOException {
		String ret = null;
		if (bitmap != null) {
			File f = new File(path);
			if (isOverwrite) {
				f.createNewFile();
			} else {
				if (f.exists()) {
					f.setLastModified(Calendar.getInstance().getTimeInMillis());
					return f.getAbsolutePath();
				} else {
					f.createNewFile();
				}
			}
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
			ret = f.getAbsolutePath();
		}
		return ret;
	}

	public static String genCodeFromCouponIdAndSellerId(int cid, int sellerId,
			String couponName, String desc) {
		StringBuffer codeBuffer = new StringBuffer();
		String cidStr = String.valueOf(cid);
		String sellerIdStr = String.valueOf(sellerId);
		codeBuffer.append(0);
		for (int i = 0; i < 6 - sellerIdStr.length(); i++) {
			codeBuffer.append(0);
		}
		codeBuffer.append(sellerIdStr);
		for (int i = 0; i < 6 - cidStr.length(); i++) {
			codeBuffer.append(0);
		}
		codeBuffer.append(cidStr);
		for (int i = 0; i < 27; i++) {
			codeBuffer.append(0);
		}
		StringBuffer ret = new StringBuffer();
		ret.append("http://m.icybercode.com/coupon/");
		ret.append(codeBuffer);
		ret.append(".html?name=");
		ret.append(couponName);
		ret.append("&desc=");
		ret.append(desc);
		return ret.toString();
	}

	public static void shareQrCode(Activity activity, String msg,
			Object backgroundBitmap) {
		String qrBitmapPath = null;
		if (backgroundBitmap != null && backgroundBitmap instanceof Bitmap) {
			Bitmap b = (Bitmap) backgroundBitmap;
			qrBitmapPath = saveArvatoUrlBitmap(activity, b);
			Uri qrBitmapPathUri = Uri.parse("file://" + qrBitmapPath);
			Log.d("TRACK", qrBitmapPathUri.toString());
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/*");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT, msg);
			intent.putExtra(Intent.EXTRA_STREAM, qrBitmapPathUri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(Intent.createChooser(intent,
					activity.getTitle()));
		} else {
			Toast.makeText(activity, activity.getString(R.string.cannot_share),
					Toast.LENGTH_SHORT).show();
		}
	}

	public static String saveArvatoUrlBitmap(Activity activity, Bitmap b) {
		String path = null;
		if (b != null) {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// sd card available
				try {
					path = saveBitmapAsJpg(b,
							Environment.getExternalStorageDirectory()
									+ Constants.ARVATO_ROOT
									+ Constants.CACHE_PATH + "/" + "tmp_img",
							70);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("SaveArvatoUrlBitmap", e.toString());
				}
			} else {
				// sd card unavailable
				try {
					path = Images.Media.insertImage(
							activity.getContentResolver(), b, "title", null);
				} catch (Exception e) {
					Log.e("Utils",
							"Media insert image error.Perhaps no sdcard.");
				}
			}
		}
		if (path != null) {
			Log.d("TRACK", path);
		}
		return path;
	}

	public static String saveBitmapAsJpg(Bitmap bitmap, String path, int quailty)
			throws IOException {
		String ret = null;
		if (bitmap != null) {
			File f = new File(path);
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, quailty, fOut);
			fOut.flush();
			fOut.close();
			ret = f.getAbsolutePath();
		}
		return ret;
	}

	public static BDLocation getLocation(Context context) {
		Activity activity = (Activity) context;
		MyApplication myApp = (MyApplication) activity.getApplication();
		BDLocation location = myApp.getLastKnowLocation();
		return location;
	}

	public static double getDistanceFromXtoY(double lat_a, double lng_a,
			double lat_b, double lng_b) {
		double pk = (double) (180 / 3.14169);

		double a1 = lat_a / pk;
		double a2 = lng_a / pk;
		double b1 = lat_b / pk;
		double b2 = lng_b / pk;

		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		double t3 = Math.sin(a1) * Math.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
	}

	public static double gps2m(float lat_a, float lng_a, float lat_b,
			float lng_b) {
		float pk = (float) (180 / 3.14169);

		float a1 = lat_a / pk;
		float a2 = lng_a / pk;
		float b1 = lat_b / pk;
		float b2 = lng_b / pk;

		float t1 = FloatMath.cos(a1) * FloatMath.cos(a2) * FloatMath.cos(b1)
				* FloatMath.cos(b2);
		float t2 = FloatMath.cos(a1) * FloatMath.sin(a2) * FloatMath.cos(b1)
				* FloatMath.sin(b2);
		float t3 = FloatMath.sin(a1) * FloatMath.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
	}

	@SuppressWarnings("deprecation")
	public static void dismissDialogIfExist(Activity activity, int dialogId) {
		try {
			activity.dismissDialog(dialogId);
		} catch (Exception e) {
			Log.i("No dialog", "Dialog:" + dialogId + " missing.");
		}
	}

	public static void showToastMessage(Activity activity, String msg) {
		Toast.makeText(activity.getApplicationContext(), msg,
				Toast.LENGTH_SHORT).show();
	}

	public static String getPushTime(long receivedTime) {
		String ret = null;
		long now = System.currentTimeMillis();
		Calendar todayBegin = Calendar.getInstance();
		todayBegin.set(Calendar.HOUR_OF_DAY, 0);
		System.out.println(todayBegin);
		long todayBeginInMillis = todayBegin.getTimeInMillis();
		long delta = now - receivedTime;
		if (delta < 3600000) {
			// one hour ago
			int min = (int) (delta / 1000 / 60);
			ret = String.valueOf(min) + " mins ago";
		} else if (receivedTime >= todayBeginInMillis) {
			// today
			Calendar rt = Calendar.getInstance();
			rt.setTimeInMillis(receivedTime);
			int hh = rt.get(Calendar.HOUR_OF_DAY);
			int mm = rt.get(Calendar.MINUTE);
			ret = "Today " + (hh >= 10 ? hh : ("0" + hh)) + ":"
					+ (mm >= 10 ? mm : ("0" + mm));
		} else {
			// yy-mm-dd hh-mi-ss
			Calendar rt = Calendar.getInstance();
			rt.setTimeInMillis(receivedTime);
			int hh = rt.get(Calendar.HOUR_OF_DAY);
			int mm = rt.get(Calendar.MINUTE);
			ret = rt.get(Calendar.YEAR) + "-" + rt.get(Calendar.MONTH) + "-"
					+ rt.get(Calendar.DAY_OF_MONTH) + " "
					+ (hh >= 10 ? hh : ("0" + hh)) + ":"
					+ (mm >= 10 ? mm : ("0" + mm));
		}
		return ret;
	}

	public static boolean isFirstStart(Context context) {
		return context.getSharedPreferences("APP_INFO", Context.MODE_PRIVATE)
				.getBoolean("isFirstStart", true);
	}

	public static void setFirstStartCompleted(Context context) {
		context.getSharedPreferences("APP_INFO", Context.MODE_PRIVATE).edit()
				.putBoolean("isFirstStart", false).commit();
	}
}
