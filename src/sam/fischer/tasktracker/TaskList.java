package sam.fischer.tasktracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class TaskList extends ListActivity {

	 public static final String ROW_ID = "row_id";
	 private ListView conListView;
	 private CursorAdapter conAdapter;
	   
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conListView=getListView();
        conListView.setOnItemClickListener(viewConListener);
        
        // map each name to a TextView
        String[] from = new String[] { "activity" };
        int[] to = new int[] { R.id.taskTextView };
        conAdapter = new SimpleCursorAdapter(TaskList.this, R.layout.task_list, null, from, to);
        setListAdapter(conAdapter); // set adapter
    }
    
    @Override
    protected void onResume() 
    {
       super.onResume();  
       new GetActivies().execute((Object[]) null);
     } 
    
    
    @Override
    protected void onStop() 
    {
       Cursor cursor = conAdapter.getCursor();
       
       if (cursor != null) 
          cursor.deactivate();
       
       conAdapter.changeCursor(null);
       super.onStop();
    }    
    
  
    private class GetActivies extends AsyncTask<Object, Object, Cursor> 
    {
       DatabaseConnector dbConnector = new DatabaseConnector(TaskList.this);

       @Override
       protected Cursor doInBackground(Object... params)
       {
          dbConnector.open();
          return dbConnector.getAllActivities(); 
       } 
       
       @Override
       protected void onPostExecute(Cursor result)
       {
          conAdapter.changeCursor(result); // set the adapter's Cursor
          dbConnector.close();
       } 
    } 
       
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.task_menu, menu);
       return true;
    }   
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
	      switch (item.getItemId())
	      {
	    	case R.id.addTaskItem:
	    		Intent addItem =
	    			new Intent(this, AddEditTask.class);
	    		 startActivity(addItem);
	            return true;
	            
	         case R.id.deleteAllItems:
		            deleteAllActivities();
		            return true;
		            
		         default:
		            return super.onOptionsItemSelected(item);
		     } 
    }
    
	   private void deleteAllActivities()
	   { 
		  AlertDialog.Builder alert = new AlertDialog.Builder(TaskList.this);

	      alert.setTitle(R.string.confirmTitle); 
	      alert.setMessage(R.string.confirmAllMessage); 

	      alert.setPositiveButton(R.string.delete_btn,
	         new DialogInterface.OnClickListener()
	         {
	            public void onClick(DialogInterface dialog, int button)
	            {
	               final DatabaseConnector dbConnector = 
	                  new DatabaseConnector(TaskList.this);

	               AsyncTask<Long, Object, Object> deleteTask =
	                  new AsyncTask<Long, Object, Object>()
	                  {
	                     @Override
	                     protected Object doInBackground(Long... params)
	                     {
	                        dbConnector.deleteAllActivities(); 
	                        return null;
	                     } 
	                     
	                     @Override
	                     protected void onPostExecute(Object result)
	                     {
	                        finish(); 
	                     }
	                  };

	               deleteTask.execute();               
	            }
	         }
	      );
	      
	      alert.setNegativeButton(R.string.cancel_btn, null).show();
	   }
	
	   
    
    OnItemClickListener viewConListener = new OnItemClickListener() 
    {    
       public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
       {         
          Intent viewCon = new Intent(TaskList.this, ViewTask.class);
          viewCon.putExtra(ROW_ID, arg3);
          startActivity(viewCon);
       }
    };    
    
}
