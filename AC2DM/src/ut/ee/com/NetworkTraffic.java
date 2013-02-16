package ut.ee.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.net.TrafficStats;

public class NetworkTraffic {

	// TrafficStats stats = new TrafficStats();
	Context mContext;
	HashMap<String, Long> bytesReceived = new HashMap<String, Long>();
	HashMap<String, Long> packagesReceived = new HashMap<String, Long>();
	HashMap<String, Long> bytesSent = new HashMap<String, Long>();
	HashMap<String, Long> packagesSent = new HashMap<String, Long>();

	public NetworkTraffic(Context context) {
		mContext = context;
	}

	public long getBytesReceived() {
		return TrafficStats.getTotalRxBytes();
	}

	public long getPackagesReceived() {
		return TrafficStats.getTotalRxPackets();
	}

	public long getBytesSent() {
		return TrafficStats.getTotalTxBytes();
	}

	public long getPackagesSent() {
		return TrafficStats.getTotalTxPackets();
	}

	public void measure(String event) {
		packagesReceived.put(event, new Long(getPackagesReceived()));
		packagesSent.put(event, new Long(getPackagesSent()));
		bytesReceived.put(event, new Long(getBytesReceived()));
		bytesSent.put(event, new Long(getBytesSent()));
	}

	public String getMeasures() {
		String cvs = "Packages Received\n";
		Iterator it = packagesReceived.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			cvs = cvs + pairs.getKey() + " = " + pairs.getValue()+"\n";
			//it.remove(); // avoids a ConcurrentModificationException
		}

		it = packagesSent.entrySet().iterator();
		cvs = cvs+"\nPackages Sent\n";
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			cvs = cvs + pairs.getKey() + "=" + pairs.getValue()+"\n";
			//it.remove(); // avoids a ConcurrentModificationException
		}
		cvs = cvs+"\nBytes Received\n";
		it = bytesReceived.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			cvs = cvs + pairs.getKey() + "=" + pairs.getValue()+"\n";
			//it.remove(); // avoids a ConcurrentModificationException
		}

		cvs = cvs+"\nBytes Sent\n";
		it = bytesSent.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			//it.remove(); // avoids a ConcurrentModificationException
			cvs = cvs + pairs.getKey() + "=" + pairs.getValue()+"\n";
		}
		return cvs;
	}

}
