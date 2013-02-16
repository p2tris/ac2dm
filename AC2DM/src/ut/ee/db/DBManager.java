package ut.ee.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ut.ee.utilities.Utilities;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBManager {
	

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static String DATABASE_PATH = "/Android/";
	private static String DATABASE_NAME = "DBPNS";
	private static int DATABASE_VERSION = 1;

	private static String TABLE = "motion";

	private static String CREATE_TABLE = "";

	
	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE);
			onCreate(db);
		}

	}

	public void Reset() {
		mDbHelper.onUpgrade(this.mDb, 1, 1);
	}

	public DBManager(Context ctx) {
		mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);
	}

	public DBManager(Context ctx, int table) {
		mCtx = ctx;		
		CREATE_TABLE = Utilities.CREATE_RELIABILITY_TABLE;
		mDbHelper = new DatabaseHelper(mCtx);
	}

	public DBManager open() throws SQLException {
		mDb = mDbHelper.getReadableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public void createEntryForReliability(
			String serverTimeStamp, String deviceTimeStamp, String message) {
		String statement = Utilities.INSERT_RELIABILITY;
		statement = statement + Utilities.SINGLE_QUOTE + serverTimeStamp
				+ Utilities.SINGLE_QUOTE + Utilities.COMA
				+ Utilities.SINGLE_QUOTE + deviceTimeStamp
				+ Utilities.SINGLE_QUOTE + Utilities.COMA
				+ Utilities.SINGLE_QUOTE + message + Utilities.SINGLE_QUOTE
				+ Utilities.END_STATEMENT;
		mDb.execSQL(statement);
	}

	public void createAccelerometerEntry(String times, double tmi, double tmf) {
		// //TODO
		mDb.execSQL("insert into " + TABLE + "(tmi,tmf,times) VALUES (" + tmi
				+ "," + tmf + ",'" + times + "');");
	}

	// Copy the Database from its default location
	public void copyDataBase() throws IOException {

		InputStream myInput = new FileInputStream(mCtx.getDatabasePath(
				DATABASE_NAME).getAbsolutePath());
		
    	String outFileName = "/sdcard/MHData";
     	OutputStream myOutput = new FileOutputStream(outFileName);
     	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
     	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    	
		File borrar = new File(mCtx.getDatabasePath(DATABASE_NAME)
				.getAbsolutePath());
		borrar.delete();

	}

}