package sam.fischer.tasktracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.text.format.Time;
import android.view.View;
import android.content.Intent;

public class SurveyView extends Activity
{	
	String svObserved;
	String svObserver;
	
	String svLocation;
	String svActivity;
	
	String svStarttime;
	String svEndtime;
	
	String test;
	
	Spinner spActivity;
	Spinner spEvent;
	
	FileInputStream myInput;
	FileOutputStream myOutput;
	
	/*TextView tvObserved;
	TextView tvObserver;
	TextView tvStartTime;
	TextView tvEndtime;
	TextView tvActivity;
	TextView tvLocation;*/
	
	EditText mEdit;
	
	Time ST  = new Time(Time.getCurrentTimezone());
	Time ET  = new Time(Time.getCurrentTimezone());
	
	ArrayList<ActivityEntry> aL = new ArrayList<ActivityEntry>();
	
	ActivityEntry a1;
	int index;
	
	int switcher = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
        	
        	svObserved = extras.getString("observed");
        	svObserver = extras.getString("observer");
        	
        }
              
        mEdit = (EditText) findViewById(R.id.etLocation);
        spEvent = (Spinner) findViewById(R.id.spnAct);
        
        /* TESTING
        
        tvObserved =(TextView)findViewById(R.id.tvObserved);
        tvObserved.setText(svObserved);
        
        tvObserver =(TextView)findViewById(R.id.tvObserver);
        tvObserver.setText(svObserver);
        
        tvStartTime =(TextView)findViewById(R.id.tvStartTime); 
        tvEndtime =(TextView)findViewById(R.id.tvEndTime); 
        
        tvActivity =(TextView)findViewById(R.id.tvActivity); 
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        
        END TESTING*/

        Button next = (Button) findViewById(R.id.back);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        
	    Button ViewList = (Button) findViewById(R.id.btnViewActivities);
	    ViewList.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            Intent myIntent = new Intent(view.getContext(), TaskList.class);
	            startActivityForResult(myIntent, 0);
	        }
	        
	    });
        
        spActivity = (Spinner) findViewById(R.id.spnAllActivities);
		ArrayAdapter<ActivityEntry> adapter = new ArrayAdapter<ActivityEntry>(this,
				android.R.layout.simple_spinner_item, aL);
		spActivity.setAdapter(adapter);
		
        final Button startActivity = (Button) findViewById(R.id.btnStartActivity);
        final Button freezeActivity = (Button) findViewById(R.id.btnFreezeActivity);
        
        final Button publish = (Button) findViewById(R.id.btnPublish);
        publish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	try {
            		 File newFile = new File("/storage/sdcard1/TaskTracker_db");
            		InputStream input = new FileInputStream(
            		"/data/data/sam.fischer.tasktracker/databases/TaskTracker");

            		        OutputStream output = new FileOutputStream(newFile);
            		        byte[] buffer = new byte[1024];
            		        int length;
            		        while ((length = input.read(buffer)) > 0) {
            		            output.write(buffer, 0, length);
            		        }
            		        output.flush();
            		        output.close();
            		        input.close();

                        	Toast.makeText(getBaseContext(),  "Success", Toast.LENGTH_SHORT).show();

            		    } catch (FileNotFoundException e1) {
            		        // TODO Auto-generated catch block
                        	Toast.makeText(getBaseContext(),  "Error", Toast.LENGTH_SHORT).show();
            		        e1.printStackTrace();
            		    } catch (IOException e) {
            		        // TODO Auto-generated catch block
                        	Toast.makeText(getBaseContext(),  "Error", Toast.LENGTH_SHORT).show();
            		        e.printStackTrace();
            		    }
            	
            }
        });
        
        
        
        startActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	if (switcher == 0)
            	{
            		ST.setToNow();
            	
            		//startActivity.setEnabled(false);
            		freezeActivity.setEnabled(true);
            		switcher = 1;
            		
                	Toast.makeText(getBaseContext(),  "start time recorded", Toast.LENGTH_SHORT).show();
            	}
            	else 
            	{
                	ET.setToNow();
                	
                	svActivity = spEvent.getSelectedItem().toString();
                	svLocation = mEdit.getText().toString();
                	svStarttime = ST.format("%k:%M:%S");
                	svEndtime = ET.format("%k:%M:%S");
                	
                	aL.add(new ActivityEntry(svLocation, svActivity, svStarttime, svEndtime));
                	
                	Toast.makeText(getBaseContext(),  "end time recorded -- activity created", Toast.LENGTH_SHORT).show();
                	
                	saveActivity();

                	ST.setToNow();
            		
            		switcher = 1;
            		
            		Toast.makeText(getBaseContext(),  "start time recorded", Toast.LENGTH_SHORT).show();
            	}
            	
            }

        });
        
        freezeActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	ET.setToNow();
            	
            	svActivity = spEvent.getSelectedItem().toString();
            	svLocation = mEdit.getText().toString();
            	svStarttime = ST.format("%k:%M:%S");
            	svEndtime = ET.format("%k:%M:%S");
            	
            	aL.add(new ActivityEntry(svLocation, svActivity, svStarttime, svEndtime));
            	
            	saveActivity();
            	
            	/* TESTING
            	
            	tvStartTime.setText(aL.get(aL.size()-1).getStarttimeS());
            	tvEndtime.setText(aL.get(aL.size()-1).getEndTimeS());
            	
            	tvActivity.setText(aL.get(aL.size()-1).getEvent());
            	tvLocation.setText(aL.get(aL.size()-1).getLocation());
            	
            	END TESTING */ 
            	
            	freezeActivity.setEnabled(false);
            	
            	switcher = 0;
            	
            	Toast.makeText(getBaseContext(),  "end time recorded -- activity created", Toast.LENGTH_SHORT).show();

            }

        });
        
        /*spActivity.setOnItemSelectedListener(new OnItemSelectedListener()
        {
        	 public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        	 {
            	Toast.makeText(getBaseContext(),  "You have selected item",// : " + a1.toString(), 
                            Toast.LENGTH_SHORT).show();
            }
 
            public void onNothingSelected(AdapterView<?> parent) 
            {
            	//another interface callback
            }
        });*/
    }
    
	private void saveActivity() 
	{
	        DatabaseConnector dbConnector = new DatabaseConnector(this);
	        
	        dbConnector.insertActivity(
	        	  svActivity,
	        	  svLocation,
    			  svStarttime,
    			  svEndtime,
    			  svObserved,
    			  svObserver
    			  );
		
	}
}