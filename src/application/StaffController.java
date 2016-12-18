package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
* This class is the controller of the edit staff screen: <code>EditStaff.fxml</code>.
* Provides add, remove and replace method to edit the staff accounts.
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16          
*/

public class StaffController implements Initializable{
	
	@FXML
	private TextField staffFirst;
	
	@FXML
	private TextField staffLast;
	
	@FXML
	private TextField staffUsername;
	
	@FXML
	private TextField staffPassword;
	
	@FXML
	private TableView<Staff> staffTable;
	
	@FXML
	private Label status;
	
	ObservableList<Staff> items;

	/**
	 * <code>initialize</code> Initialise the screens with <code>TableView staffTable</code>, populate the TableView with current staff accounts in the database.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		staffTable.getColumns().clear();
		
		TableColumn<Staff, String> colFirstName = new TableColumn<>("First Name");
		TableColumn<Staff, String> colLastName = new TableColumn<>("Last Name");
		TableColumn<Staff, String> colUsername = new TableColumn<>("Username");
		TableColumn<Staff, String> colPassword = new TableColumn<>("Password");
		
		ResultSet mainResultSet = SQLiteConnection.GetStaffList();
		items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Staff name = new Staff(mainResultSet.getString("FirstName"), mainResultSet.getString("LastName"), mainResultSet.getString("Username"), mainResultSet.getString("Password"));
				items.add(name);
			}
			
			staffTable.setItems(items);
			colFirstName.setCellValueFactory(new PropertyValueFactory<Staff, String>("firstName"));
			colLastName.setCellValueFactory(new PropertyValueFactory<Staff, String>("lastName"));
			colUsername.setCellValueFactory(new PropertyValueFactory<Staff, String>("username"));
			colPassword.setCellValueFactory(new PropertyValueFactory<Staff, String>("password"));
			
			staffTable.getColumns().addAll(colFirstName, colLastName, colUsername, colPassword);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * <code>backToAdmi</code> switch the screen back to AdmiIn.fxml.
	 * 
	 * @param e Listener of the <code>back</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void backToAdmi (ActionEvent e){
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/AdmiIn.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
	}
	
	/**
	 * <code>add</code> add the given staff account with content in all specified fields into the database and the <code>TableView staffTable</code>.
	 * 
	 * @param e Listener of the <code>Add</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void add (ActionEvent e){
		
		if(staffFirst.getText().isEmpty() || staffLast.getText().isEmpty() || staffUsername.getText().isEmpty() || staffPassword.getText().isEmpty()){
			status.setText("Please add qualified information to all fields.");
		} else {
			items.add(new Staff(staffFirst.getText(), staffLast.getText(), staffUsername.getText(), staffPassword.getText()));
			String query = new String("INSERT INTO staffs (firstName,lastName,username,password)VALUES ('"+staffFirst.getText()+"','"+staffLast.getText()+"','"+staffUsername.getText()+"','"+staffPassword.getText()+"')");
			SQLiteConnection.write(query);
		}
	}
	
	/**
	 * <code>replace</code> Replace the selected staff account with content in all specified fields and write the changes into the database.
	 * 
	 * @param e Listener of the <code>Replace</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void replace (ActionEvent e){
		if(staffTable.getSelectionModel().isEmpty() || (staffFirst.getText().isEmpty() && staffLast.getText().isEmpty() && staffUsername.getText().isEmpty() && staffPassword.getText().isEmpty())){
			status.setText("Please add qualified information to the fields and select one row in the Table to be replaced.");
		} else {
			String strFirstName = items.get(staffTable.getSelectionModel().getSelectedIndex()).getfirstName();
			String strLastName = items.get(staffTable.getSelectionModel().getSelectedIndex()).getlastName();
			String strUsername = items.get(staffTable.getSelectionModel().getSelectedIndex()).getusername();
			String strPassword = items.get(staffTable.getSelectionModel().getSelectedIndex()).getpassword();
			
			String rePlacedName = strFirstName;
			
			if (!staffFirst.getText().isEmpty()){
				strFirstName=staffFirst.getText();
			}
			
			if (!staffLast.getText().isEmpty()){
				strLastName=staffLast.getText();
			}
			
			if (!staffUsername.getText().isEmpty()){
				strUsername=staffUsername.getText();
			}
			
			if (!staffPassword.getText().isEmpty()){
				strPassword=staffPassword.getText();
			}

			items.set(staffTable.getSelectionModel().getSelectedIndex(), new Staff(strFirstName, strLastName, strUsername, strPassword));
			String query = new String("UPDATE staffs SET FirstName = '"+strFirstName+"',LastName = '"+strLastName+"',Username = '"+strUsername+"',Password = '"+strPassword+"' WHERE FirstName = '"+rePlacedName+"';");
			SQLiteConnection.write(query);
		}
	}
	
	/**
	 * <code>remove</code> Remove the selected staff account from <code>TableView staffTable</code> and database.
	 * 
	 * @param e Listener of the <code>Remove</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void remove (ActionEvent e){
		if(staffTable.getSelectionModel().isEmpty()){
			status.setText("Please select one row in the Table to be removed.");
		} else {
			String removedName = items.get(staffTable.getSelectionModel().getSelectedIndex()).getfirstName();
			items.remove(staffTable.getSelectionModel().getSelectedIndex());
			String query = new String("DELETE FROM staffs WHERE FirstName = '"+removedName+"';");
			SQLiteConnection.write(query);
		}
	}
	
	/**
	 * <code>activityLog</code> Switch the screen to <code>ActivityLog.fxml</code> (allows the manager to view records of staffs and manager activities).
	 * 
	 * @param e Listener of the <code>Staff Activity Log</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */

	public void activityLog (ActionEvent e){
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/ActivityLog.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	

}
