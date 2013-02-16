package ut.ee.asycn;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import android.widget.TextView;
import ut.ee.utilities.*;

public class C2DMBroadCaster extends BroadcastReceiver {

	private static final int HELLO_ID = 1;
	public static String registrationID = "";
	public static Activity activity;
	DBManager manager;
	int counter = 0;

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(
				"com.google.android.c2dm.intent.REGISTRATION")) {
			
			handleRegistration(context, intent);
			// after registration
			((AC2DMActivity) activity).networkTraffic.measure("3. AFTER RECEIVING REGISTRATION");

		} else if (intent.getAction().equals(
				"com.google.android.c2dm.intent.RECEIVE")) {
			counter = counter +1;
			((AC2DMActivity) activity).networkTraffic.measure("4."+String.valueOf(((AC2DMActivity)activity).getCounter())+" AFTER RECEIVING MESSAGE");
			handleMessage(context, intent);
			
		}

	}

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

	private void handleMessage(Context context, Intent intent) {
		String title = intent.getExtras().getString(Utilities.C2DM_TITLE);
		String message = intent.getExtras().getString(Utilities.C2DM_MESSAGE);
		// if (Utilities.C2DM_MESSAGE_SYNC.equals(message)) {
		if (message != null) {
			notification(context, intent);
			// String message = intent.getExtras().getString("message");
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
		String registration = intent.getStringExtra("registration_id");
		if (intent.getStringExtra("error") != null) {
			// Registration failed, should try again later.
			String error = intent.getStringExtra("error");
		} else if (intent.getStringExtra("unregistered") != null) {
			// unregistration done, new messages from the authorized sender will
			// be rejected
		} else if (registration != null) {
			registrationID = registration;
			// Send the registration ID to the 3rd party site that is sending
			// the messages.
			// This should be done in a separate thread.
			// When done, remember that all registration is done.

		}
	}

}
