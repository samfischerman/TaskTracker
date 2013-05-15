package sam.fischer.tasktracker;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Spinner sp1;
	Spinner sp2;
	
	String mObserved;
	String mObserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sp1 = (Spinner) findViewById(R.id.spnObserved);
		sp2 = (Spinner) findViewById(R.id.spnObserver);
	
	    Button BeginSurvey = (Button) findViewById(R.id.btnBeginSurvey);
	    BeginSurvey.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	        	

	    		mObserved = sp1.getSelectedItem().toString();
	    		mObserver = sp2.getSelectedItem().toString(); 
	        	
	        	Intent i = new Intent(getApplicationContext(), SurveyView.class);
	        	i.putExtra("observed",  mObserved);
	        	i.putExtra("observer", mObserver);
	        	startActivity(i);
	        }
	        
	    });
	
	}

}
