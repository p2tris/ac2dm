package ut.ee.com;

import ut.ee.db.DBManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver {

	private static final String TAG = "BatteryLife";
	DBManager db;
	Context mContext;


	public BatteryReceiver( ) {
		// 		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
		Log.i(TAG, "level: "	 + level + "; scale: " + scale);
		int percent = (level * 100) / scale;
		final String text = String.valueOf(percent) + "%";
	}

}
