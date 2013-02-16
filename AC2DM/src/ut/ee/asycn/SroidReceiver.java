package ut.ee.asycn;

import java.io.IOException;
import java.util.HashMap;

import ut.ee.db.DBManager;
import ut.ee.utilities.ParserForJSON;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SroidReceiver extends BroadcastReceiver {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final int HELLO_ID = 1;

	public static Activity activity;

	public void notification(Context context, Intent intent) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = "Sroid Notification";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		// Context context = activity.getApplicationContext();
		CharSequence contentTitle = "Sroid Notification";
		CharSequence contentText = "Mobile Host Notification";
		notification.defaults |= Notification.DEFAULT_SOUND;
		//notification.defaults |= Notification.DEFAULT_VIBRATE;

		Intent notificationIntent = new Intent(context, AC2DMActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		mNotificationManager.notify(HELLO_ID, notification);

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("ut.ee.mh.intent.action.SROID_START")) {
			handleRegistration(context, intent);
			notification(context, intent);
			String message = intent.getExtras().getString("message");
			if (message.length() <= 0) {
				message = "{\"current_time_millis\":\"1.330902216436E12\",\"mesage\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
			}
			ParserForJSON parse = new ParserForJSON();
			HashMap<String, String> values = parse.parse(message);
			AC2DMActivity.manager.open();
			AC2DMActivity.manager.createEntryForReliability(
					(String) values.get("current_time_millis"),
					String.valueOf(System.currentTimeMillis()),
					(String) values.get("message"));
			AC2DMActivity.manager.close();
			((AC2DMActivity) activity).setCounter();
		}
	}

	private void handleRegistration(Context context, Intent intent) {
		
	}

}
