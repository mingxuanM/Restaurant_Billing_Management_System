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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
* This class is the controller of the activity log screen: <code>ActivityLog.fxml</code> for manager.
* Allows the manager to view records of staffs and manager activities.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16         
*/

public class ActivityController implements Initializable{
	
	@FXML
	private TableView<Activity> activityTable;
	
	ObservableList<Activity> items;

	/**
	 * <code>initialize</code> Initialise the screens with <code>TableView activityTable</code>, populate the TableView with all recorded activities in the database.
	 * 
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		activityTable.getColumns().clear();
		
		TableColumn<Activity, String> colTime = new TableColumn<>("Time");
		TableColumn<Activity, String> colName = new TableColumn<>("Name");
		TableColumn<Activity, String> colActivity = new TableColumn<>("Activity");
		
		
		ResultSet mainResultSet = SQLiteConnection.GetActivityList();
		items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Activity log = new Activity(mainResultSet.getString("time"), mainResultSet.getString("name"), mainResultSet.getString("activity"));
				items.add(log);
			}
			
			activityTable.setItems(items);
			colTime.setCellValueFactory(new PropertyValueFactory<Activity, String>("time"));
			colName.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
			colActivity.setCellValueFactory(new PropertyValueFactory<Activity, String>("activity"));
			
			activityTable.getColumns().addAll(colTime, colName, colActivity);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * <code>back</code> Switch the screen back to <code>EditStaff.fxml</code> for manager.
	 * 
	 * @param e Listener of the <code>Back</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void back (ActionEvent e){
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/EditStaff.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}


}
