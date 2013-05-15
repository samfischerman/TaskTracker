package sam.fischer.tasktracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseConnector {
	
	private static final String DB_NAME = "TaskTracker";
	private SQLiteDatabase database;
	private DatabaseOpenHelper dbOpenHelper;
	   
	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseOpenHelper(context, DB_NAME, null, 1);
	}
	
	   public void open() throws SQLException 
	   {
	      //open database in reading/writing mode
	      database = dbOpenHelper.getWritableDatabase();
	   } 

	   public void close() 
	   {
	      if (database != null)
	         database.close();
	   }	   
	   
	   		   public void insertActivity(String activity, String location, String starttime, String endtime, String observed, String observer) 
			   {
			      ContentValues newCon = new ContentValues();
			      newCon.put("activity", activity);
			      newCon.put("location", location);
			      newCon.put("starttime", starttime);
			      newCon.put("endtime", endtime);
			      newCon.put("observed", observed);
			      newCon.put("observer", observer);

			      open();
			      database.insert("task", null, newCon);
			      close();
			   }

			
			   public void updateActivity(long id, String activity, String location, String starttime, String endtime, String observed, String observer) 
			   {
			      ContentValues editCon = new ContentValues();
			      editCon.put("activity", activity);
			      editCon.put("location", location);
			      editCon.put("starttime", starttime);
			      editCon.put("endtime", endtime);
			      editCon.put("observed", observed);
			      editCon.put("observer", observer);


			      open();
			      database.update("task", editCon, "_id=" + id, null);
			      close();
			   }

			  
			   public Cursor getAllActivities() 
			   {
			      return database.query("task", new String[] {"_id", "activity"}, 
			         null, null, null, null, "starttime");
			   }

			   public Cursor getOneActivity(long id) 
			   {
			      return database.query("task", null, "_id=" + id, null, null, null, null);
			   }
			   
			   public void deleteActivity(long id) 
			   {
			      open(); 
			      database.delete("task", "_id=" + id, null);
			      close();
			   }
			   
			   public void deleteAllActivities() 
			   {
			      open(); 
			      database.delete("task", "_id>-1", null);
			      close();
			   }
}
