package sam.fischer.tasktracker;

public class ActivityEntry 
{
	
	String aLocation;
	String aEvent;
	
	String StartTime;
	String EndTime;
    
    public ActivityEntry(String location, String event, String starttime, String endtime) 
    {
    
    	aLocation = location;
    	aEvent = event;
    	StartTime = starttime;
    	EndTime = endtime;
    }
    
    public String getLocation()
    {
    	
    	return aLocation;
    	
    }
    
    public String getEvent()
    {
    	
    	return aEvent;
    	
    }
    
    public String getStarttimeS()
    {
    	return StartTime;
    }
    
    public String getEndTimeS()
    {
    	return EndTime;
    }   
    
    @Override
    public String toString() {
        return this.StartTime + " -" + this.EndTime + " -- " + this.aEvent + " - " + this.aLocation + "";
    }
    
}