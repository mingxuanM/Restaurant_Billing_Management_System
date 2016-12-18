package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
* This class creates the type <code>Activity</code> for <code>TableView activityTable</code> in activity log screen: <code>ActivityLog.fxml</code>.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16        
*/

public class Activity {
	
	private StringProperty time;
	private StringProperty name;
	private StringProperty activity;
	
	public Activity(String time, String name, String activity){
		this.time = new SimpleStringProperty(time);
		this.name = new SimpleStringProperty(name);
		this.activity = new SimpleStringProperty(activity);
		
	}
	
	public String gettime(){
		return time.get();
	}
	
	public String getname(){
		return name.get();
	}
	
	public String getactivity(){
		return activity.get();
	}
	
	public void settime(String time) {
		this.time.set(time);
    }
	
	public void setname(String name) {
		this.name.set(name);
    }
	
	public void setactivity(String activity) {
		this.activity.set(activity);
    }
	
	public StringProperty timeProperty() {
        return time;
    }
	
	public StringProperty nameProperty() {
        return name;
    }
	
	public StringProperty activityProperty() {
        return activity;
    }
	
}
