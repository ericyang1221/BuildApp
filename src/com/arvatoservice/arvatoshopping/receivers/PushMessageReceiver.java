package com.arvatoservice.arvatoshopping.receivers;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.arvatoservice.arvatoshopping.MainActivity;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.ShowActivity;
import com.arvatoservice.arvatoshopping.beans.PushMsgBean;
import com.arvatoservice.arvatoshopping.db.PushMsgDBHelper;
import com.arvatoservice.arvatoshopping.utils.BPushUtils;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.baidu.android.pushservice.PushConstants;

/**
 * Push消息处理receiver
 */
public class PushMessageReceiver extends BroadcastReceiver {
	/** TAG to Log */
	public static final String TAG = PushMessageReceiver.class.getSimpleName();
	private AlertDialog.Builder builder;

	/**
	 * 
	 * 
	 * @param context
	 *            Context
	 * @param intent
	 *            接收的intent
	 */
	@Override
	public void onReceive(final Context context, Intent intent)
	{
		Log.d(TAG, ">>> Receive intent: \r\n" + intent);
		long when = System.currentTimeMillis();
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE))
		{
			// 获取消息内容
			String content = intent.getExtras().getString(
					PushConstants.EXTRA_PUSH_MESSAGE_STRING);
			System.out.println("msg=" + content);
			int type = -1;
			String imgUrl = null;
			String title = null;
			String msg = null;
			int rid = Constants.PUSH_MSG_NO_RID;
			try
			{
				JSONObject contentJson = new JSONObject(content);
				if (contentJson.has("msg"))
				{
					msg = contentJson.getString("msg");
				}
				if (contentJson.has("type"))
				{
					type = contentJson.getInt("type");
				}
				if (contentJson.has("img"))
				{
					imgUrl = contentJson.getString("img");
				}
				if (contentJson.has("rid"))
				{
					rid = contentJson.getInt("rid");
				}
				if (contentJson.has("title"))
				{
					title = contentJson.getString("title");
				}
			} catch (Exception e)
			{
				Log.e(TAG, "parse message as json exception " + e);
				return;
			}

			if (type == Constants.PUSH_MSG_TYPE_INFO)
			{
				if (title == null)
				{
					title = context.getResources().getString(
							R.string.push_msg_type_info);
				}
			}
			else if (type == Constants.PUSH_MSG_TYPE_PRODUCT)
			{
				if (title == null)
				{
					title = context.getResources().getString(
							R.string.push_msg_type_product);
				}
			}
			else if (type == Constants.PUSH_MSG_TYPE_COUPON)
			{
				if (title == null)
				{
					title = context.getResources().getString(
							R.string.push_msg_type_coupon);
				}
			}
			else
			{
				return;
			}

			if (msg == null)
			{
				return;
			}

			PushMsgDBHelper pmdbHelper = new PushMsgDBHelper(context);
			PushMsgBean pmBean = new PushMsgBean(type, when, title, msg, rid,
					imgUrl);
			pmdbHelper.insert(pmBean);
			sendNotification(context, title, msg, when);

			// // 用户在此自定义处理消息,以下代码为demo界面展示用
			// Intent responseIntent = null;
			// responseIntent = new Intent(BPushUtils.ACTION_MESSAGE);
			// responseIntent.putExtra(BPushUtils.EXTRA_MESSAGE, message);
			// // responseIntent.setClass(context, PushDemoActivity.class);
			// responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(responseIntent);

		}
		else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE))
		{
			// 处理绑定等方法的返回数据
			// PushManager.startWork()的返回值通过PushConstants.METHOD_BIND得到

			// 获取方法
			final String method = intent
					.getStringExtra(PushConstants.EXTRA_METHOD);
			// 方法返回错误码。若绑定返回错误（非0），则应用将不能正常接收消息。
			// 绑定失败的原因有多种，如网络原因，或access token过期。
			// 请不要在出错时进行简单的startWork调用，这有可能导致死循环。
			// 可以通过限制重试次数，或者在其他时机重新调用来解决。
			final int errorCode = intent
					.getIntExtra(PushConstants.EXTRA_ERROR_CODE,
							PushConstants.ERROR_SUCCESS);
			// 返回内容
			final String content = new String(
					intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));

			// 用户在此自定义处理消息,以下代码为demo界面展示用
			Log.d(TAG, "onMessage: method : " + method);
			Log.d(TAG, "onMessage: result : " + errorCode);
			Log.d(TAG, "onMessage: content : " + content);

			Intent responseIntent = null;
			responseIntent = new Intent(BPushUtils.ACTION_RESPONSE);
			responseIntent.putExtra(BPushUtils.RESPONSE_METHOD, method);
			responseIntent.putExtra(BPushUtils.RESPONSE_ERRCODE, errorCode);
			responseIntent.putExtra(BPushUtils.RESPONSE_CONTENT, content);
			responseIntent.setClass(context, ShowActivity.class);
			responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(responseIntent);
			// 可选。通知用户点击事件处理
		}
		else if (intent.getAction().equals(
				PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK))
		{
			// Log.d(TAG, "intent=" + intent.toUri(0));
			//
			// Intent aIntent = new Intent();
			// aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// aIntent.setClass(context, CustomActivity.class);
			// String title = intent
			// .getStringExtra(PushConstants.EXTRA_NOTIFICATION_TITLE);
			// aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_TITLE, title);
			// String content = intent
			// .getStringExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT);
			// aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT,
			// content);
			//
			// context.startActivity(aIntent);
		}
	}

	@SuppressWarnings("deprecation")
	private void sendNotification(Context ctx, String title, String message,
			long when)
	{
		// Get the notification manager
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nm = (NotificationManager) ctx.getSystemService(ns);

		// Create Notification Object
		int icon = R.drawable.ic_launcher;
		Notification notification = new Notification(icon, message, when);
		notification.ledARGB = Color.BLUE;
		notification.ledOffMS = 3000;
		notification.ledOnMS = 3000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS
				| Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE;
		// Set ContentView using setLatestEvenInfo
		Intent intent = new Intent(ctx, MainActivity.class);
		intent.putExtra(Constants.PUSH_INTENT_DIRECTION_KEY,
				Constants.PUSH_INTENT_DIRECTION_BOTTOM);
		PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, 0);
		notification.setLatestEventInfo(ctx, title, message, pi);
		// Send notification
		// The first argument is a unique id for this notification.
		// This id allows you to cancel the notification later
		nm.notify(Constants.BAIDU_PUSH_NOTIFICATION_ID, notification);
	}
}
