package sam.fischer.tasktracker;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditTask extends Activity {
	
	 private long rowID; 
	 private EditText activityEt;
	 private EditText locationEt;
	 private EditText starttimeEt;
	 private EditText endtimeEt;
	 private EditText observedEt;
	 private EditText observerEt;
	   
	   @Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	      super.onCreate(savedInstanceState); 
	      setContentView(R.layout.add_task);

	      activityEt = (EditText) findViewById(R.id.activityEdit);
	      locationEt = (EditText) findViewById(R.id.locationEdit);
	      starttimeEt = (EditText) findViewById(R.id.starttimeEdit);
	      endtimeEt = (EditText) findViewById(R.id.endtimeEdit);
	      observedEt = (EditText) findViewById(R.id.observedEdit);
	      observerEt = (EditText) findViewById(R.id.observerEdit);
	      
	      Bundle extras = getIntent().getExtras(); 
	      
	      if (extras != null)
	      {
	         rowID = extras.getLong("row_id");
	         activityEt.setText(extras.getString("activity"));  
	         locationEt.setText(extras.getString("location"));  
	         starttimeEt.setText(extras.getString("starttime"));
	         endtimeEt.setText(extras.getString("endtime")); 
	         observedEt.setText(extras.getString("observed")); 
	         observerEt.setText(extras.getString("observer")); 
	         
	      }
	      
	      Button saveButton =(Button) findViewById(R.id.saveBtn);
	      saveButton.setOnClickListener(new OnClickListener() {
			
	    	  public void onClick(View v) 
	          {
	             if (activityEt.getText().length() != 0)
	             {
	                AsyncTask<Object, Object, Object> saveActivityTask = 
	                   new AsyncTask<Object, Object, Object>() 
	                   {
	                      @Override
	                      protected Object doInBackground(Object... params) 
	                      {
	                         saveActivity();
	                         return null;
	                      }
	          
	                      @Override
	                      protected void onPostExecute(Object result) 
	                      {
	                         finish();
	                      }
	                   }; 
	                  
	                saveActivityTask.execute((Object[]) null); 
	             }
	             
	             else
	             {
	                AlertDialog.Builder alert = new AlertDialog.Builder(AddEditTask.this);
	                alert.setTitle(R.string.errorTitle); 
	                alert.setMessage(R.string.errorMessage);
	                alert.setPositiveButton(R.string.errorButton, null); 
	                alert.show();
	             }
	          } 
		 });
	   }
	   
	   private void saveActivity() 
	   {
	      DatabaseConnector dbConnector = new DatabaseConnector(this);

	      if (getIntent().getExtras() == null)
	      {
	    	  dbConnector.insertActivity(
	    			  activityEt.getText().toString(),
	    			  locationEt.getText().toString(),
	    			  starttimeEt.getText().toString(),
	    			  endtimeEt.getText().toString(),
	    			  observedEt.getText().toString(),
	    			  observerEt.getText().toString() 
	    			  );
	      }
	      else
	      {
	         dbConnector.updateActivity(rowID,
	    			  activityEt.getText().toString(),
	    			  locationEt.getText().toString(),
	    			  starttimeEt.getText().toString(),
	    			  endtimeEt.getText().toString(),
	    			  observedEt.getText().toString(),
	    			  observerEt.getText().toString()
	    			  );
	      }
	   }
	}
