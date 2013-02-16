package ut.ee.utilities;

public class Utilities {
	
	public static final String RELIABILITY_TABLE = "tblReliability";
	

	public static final String F_EXPERIMENT = "experiment";
	public static final int LATENCY = 1;
	public static final int BATTERY = 2;
	public static final int BANDWIDTH = 3;
	public static final int RELIABILITY = 4;

	public static final String F_ID = "id";
	public static final String F_SERVER_ID = "server_id";
	public static final String F_SERVER_TIMESTAMP = "server_timestamp";
	public static final String F_DEVICE_TIMESTAMP = "device_timestamp";
	public static final String F_MESSAGE = "message_text";
	public static final String F_BATCH = "batchid";
	public static final String F_BATTERY = "battery";
	public static final String F_BYTES_SENT = "bytes";
	public static final String F_PACKAGES_SENT = "packages";
	public static final String F_BYTES_RECEIVED = "bytes";
	public static final String F_PACKAGES_RECEIVED = "packages";

	public static final String COMA = ",";
	public static final String SINGLE_QUOTE = "'";
	public static final String END_STATEMENT = ");";

	public static final String INSERT_RELIABILITY = "insert into "
			+ RELIABILITY_TABLE + " (" + F_SERVER_TIMESTAMP
			+ "," + F_DEVICE_TIMESTAMP + "," + F_MESSAGE + ") values(";

	public static String CREATE_RELIABILITY_TABLE = "create table "
			+ RELIABILITY_TABLE + " (" + F_ID
			+ " integer primary key autoincrement, " + F_SERVER_TIMESTAMP
			+ " text, " + F_DEVICE_TIMESTAMP + " text, " + F_MESSAGE
			+ " text); ";


}
