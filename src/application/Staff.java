package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
* This class creates the type <code>Staff</code> for <code>TableView staffTable</code> in edit staff account screen: <code>EditStaff.fxml</code>.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16        
*/

public class Staff {
	
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty username;
	private StringProperty password;
	
	public Staff(String firstName, String lastName, String username, String password){
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		
	}
	
	public String getfirstName(){
		return firstName.get();
	}
	
	public String getlastName(){
		return lastName.get();
	}
	
	public String getusername(){
		return username.get();
	}
	
	public String getpassword(){
		return password.get();
	}
	
	
	public void setfirstName(String firstName) {
		this.firstName.set(firstName);
    }
	
	public void setlastName(String lastName) {
		this.lastName.set(lastName);
    }
	
	public void setusername(String username) {
		this.username.set(username);
    }
	
	public void setpassword(String password) {
		this.password.set(password);
    }
	
	
	public StringProperty firstNameProperty() {
        return firstName;
    }
	
	public StringProperty lastNameProperty() {
        return lastName;
    }
	
	public StringProperty usernameProperty() {
        return username;
    }
	
	public StringProperty passwordProperty() {
        return password;
    }

}
