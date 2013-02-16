package ut.ee.asycn;

import java.io.IOException;
import java.util.HashMap;

import ut.ee.com.NetworkTraffic;
import ut.ee.db.DBManager;
import ut.ee.utilities.ParserForJSON;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AC2DMActivity extends Activity {
	private String emailOfSender = "davidpaniagualiv@gmail.com";
	private int batchID = 1;
	private static boolean mIsBound=false;
	public static DBManager manager;
	public static String RESULT = "The database has been saved ";
	public static String MESS= "Message received: ";
	public static int counter = 0;
	private static String BAD_RESULT = "The database has not been initialized";
	public static NetworkTraffic networkTraffic; 
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		networkTraffic  = new NetworkTraffic(this);
		manager = new DBManager(this.getApplicationContext(),0);
		//before sending the registration request
		networkTraffic.measure("1. BEFORE REGISTRATION REQUEST");
		Intent registrationIntent = new Intent(
				"com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		registrationIntent.putExtra("sender", emailOfSender);
		startService(registrationIntent);
		//after sending the registration request
		networkTraffic.measure("2. AFTER REGISTRATION REQUEST");
		
		SroidReceiver.activity= this;
		C2DMBroadCaster.activity= this;
		Intent registrationIntentMH = new Intent("ut.ee.mh.intent.SROID_START");
		registrationIntentMH.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		startService(registrationIntentMH);
		long i = System.currentTimeMillis();
		Button btnSave = (Button)findViewById(R.id.save);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					AC2DMActivity.manager.copyDataBase();
					TextView result = (TextView) findViewById(R.id.result);
					result.setText(RESULT);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					TextView result = (TextView) findViewById(R.id.result);
					result.setText(RESULT);
				}
				networkTraffic.getMeasures();
			}
		});
		
		
		
	}
	
	public int getCounter(){
		return AC2DMActivity.counter;
	}
	
	public void setCounter(){
		AC2DMActivity.counter = AC2DMActivity.counter +1;
		TextView txt = (TextView) findViewById(R.id.txt);
		txt.setText(AC2DMActivity.MESS + String.valueOf(AC2DMActivity.counter));
	}
	
	private LocalService mBoundService;

	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        // This is called when the connection with the service has been
	        // established, giving us the service object we can use to
	        // interact with the service.  Because we have bound to a explicit
	        // service that we know is running in our own process, we can
	        // cast its IBinder to a concrete class and directly access it.
	        mBoundService = ((LocalService.LocalBinder)service).getService();

	        // Tell the user about this for our demo.
	        Toast.makeText(AC2DMActivity.this, "Local service connected",
	                Toast.LENGTH_SHORT).show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	        // This is called when the connection with the service has been
	        // unexpectedly disconnected -- that is, its process crashed.
	        // Because it is running in our same process, we should never
	        // see this happen.
	        mBoundService = null;
	        Toast.makeText(AC2DMActivity.this, "Local Service Disconnected",
	                Toast.LENGTH_SHORT).show();
	    }
	};

	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).
	    bindService(new Intent(AC2DMActivity.this, 
	            LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
	    mIsBound = true;
	}

	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        unbindService(mConnection);
	        mIsBound = false;
	    }
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    doUnbindService();
	}

}