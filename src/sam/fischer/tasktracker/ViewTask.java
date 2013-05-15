package sam.fischer.tasktracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewTask extends Activity {
	
	   private long rowID;
	   private TextView activityTv;
	   private TextView locationTv;
	   private TextView starttimeTv; 
	   private TextView endtimeTv; 
	   private TextView observedTv; 
	   private TextView observerTv; 
	   
	   @Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.view_task);
	      
	      setUpViews();
	      Bundle extras = getIntent().getExtras();
	      rowID = extras.getLong(TaskList.ROW_ID); 
	   }
	   
	   private void setUpViews() {
		   activityTv = (TextView) findViewById(R.id.activityText);
		   locationTv = (TextView) findViewById(R.id.locationText);
		   starttimeTv = (TextView) findViewById(R.id.starttimeText);
		   endtimeTv = (TextView) findViewById(R.id.endtimeText);
		   observedTv = (TextView) findViewById(R.id.observedText);
		   observerTv = (TextView) findViewById(R.id.observerText);
	   }

	   @Override
	   protected void onResume()
	   {
	      super.onResume();
	      new LoadContacts().execute(rowID);
	   } 
	   
	   private class LoadContacts extends AsyncTask<Long, Object, Cursor> 
	   {
	      DatabaseConnector dbConnector = new DatabaseConnector(ViewTask.this);
	      
	      @Override
	      protected Cursor doInBackground(Long... params)
	      {
	         dbConnector.open();
	         return dbConnector.getOneActivity(params[0]);
	      } 

	      @Override
	      protected void onPostExecute(Cursor result)
	      {
	         super.onPostExecute(result);
	   
	         result.moveToFirst();
	         // get the column index for each data item
	         int activityIndex = result.getColumnIndex("activity");
	         int locationIndex = result.getColumnIndex("location");
	         int starttimeIndex = result.getColumnIndex("starttime");
	         int endtimeIndex = result.getColumnIndex("endtime");
	         int observedIndex = result.getColumnIndex("observed");
	         int observerIndex = result.getColumnIndex("observer");
	   
	         activityTv.setText(result.getString(activityIndex));
	         locationTv.setText(result.getString(locationIndex));
	         starttimeTv.setText(result.getString(starttimeIndex));
	         endtimeTv.setText(result.getString(endtimeIndex));
	         observedTv.setText(result.getString(observedIndex));
	         observerTv.setText(result.getString(observerIndex));
	   
	         result.close();
	         dbConnector.close();
	      }
	   } 
	   
	   
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) 
	   {
	      super.onCreateOptionsMenu(menu);
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.view_task_menu, menu);
	      return true;
	   }
	   
	   @Override
	   public boolean onOptionsItemSelected(MenuItem item) 
	   {
	      switch (item.getItemId())
	      {
	         case R.id.editItem:
	            Intent addEditContact =
	               new Intent(this, AddEditTask.class);
	            
	            addEditContact.putExtra(TaskList.ROW_ID, rowID);
	            addEditContact.putExtra("activity", activityTv.getText());
	            addEditContact.putExtra("location", locationTv.getText());
	            addEditContact.putExtra("starttime", starttimeTv.getText());
	            addEditContact.putExtra("endtime", endtimeTv.getText());
	            addEditContact.putExtra("observed", observedTv.getText());
	            addEditContact.putExtra("observer", observerTv.getText());
	            startActivity(addEditContact); 
	            return true;
	            
	         case R.id.deleteItem:
	            deleteContact();
	            return true;
	            
	         default:
	            return super.onOptionsItemSelected(item);
	      } 
	   }
	   
	   private void deleteContact()
	   {
	     
	      AlertDialog.Builder alert = new AlertDialog.Builder(ViewTask.this);

	      alert.setTitle(R.string.confirmTitle); 
	      alert.setMessage(R.string.confirmMessage); 

	      alert.setPositiveButton(R.string.delete_btn,
	         new DialogInterface.OnClickListener()
	         {
	            public void onClick(DialogInterface dialog, int button)
	            {
	               final DatabaseConnector dbConnector = 
	                  new DatabaseConnector(ViewTask.this);

	               AsyncTask<Long, Object, Object> deleteTask =
	                  new AsyncTask<Long, Object, Object>()
	                  {
	                     @Override
	                     protected Object doInBackground(Long... params)
	                     {
	                        dbConnector.deleteActivity(params[0]); 
	                        return null;
	                     } 
	                     
	                     @Override
	                     protected void onPostExecute(Object result)
	                     {
	                        finish(); 
	                     }
	                  };

	               deleteTask.execute(new Long[] { rowID });               
	            }
	         }
	      );
	      
	      alert.setNegativeButton(R.string.cancel_btn, null).show();
	   }
	}

