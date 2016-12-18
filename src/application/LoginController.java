package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
* This class is the controller of the first login screens: <code>Login.fxml</code>.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16          
*/

public class LoginController implements Initializable{
	
	static String staffName;
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private Label lblStatus1;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	@FXML
	private TextField AdmiUsername;
	
	@FXML
	private TextField AdmiPassword;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * <code>stafflogin</code> Check the given user name and password combination with the record in database, if right, log the staff in and switch the screen to <code>StaffIn.fxml</code>.
	 * Record this activity into database.
	 * @param e Listener of the <code>Login</code> button for staff login.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void stafflogin (ActionEvent e) throws IOException {
		
		SQLiteConnection stafflogin = new SQLiteConnection();
		
		String in_username = txtUsername.getText();
		
		String tryPassword = txtPassword.getText();
		
		if (stafflogin.StaffLogin(in_username, tryPassword)){
			
			staffName = SQLiteConnection.GetStaffName(in_username);
			String time = GetTime.now();
			String query = new String("INSERT INTO activityLog (time,name,activity)VALUES ('"+time+"','"+staffName+"','login')");
			
			SQLiteConnection.write(query);
			
			lblStatus.setText("Login Successful");

			Parent root = FXMLLoader.load(getClass().getResource("/application/StaffIn.fxml"));
			
			Scene scene2 = new Scene(root);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene2);
			//inLabel.setText(in_username+" logged in");
			
			
		
		} else {
			lblStatus.setText("Wrong Username & Password Combination");
		}
	};
	
	/**
	 * <code>admilogin</code> Check the given user name and password combination with the record in database, if right, log the manager in and switch the screen to <code>AdmiIn.fxml</code>.
	 * Record this activity into database.
	 * @param e Listener of the <code>Login</code> button for manager login.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void admilogin (ActionEvent e) throws IOException {
		
		SQLiteConnection admilogin = new SQLiteConnection();
		
		String in_username = AdmiUsername.getText();
		
		String tryPassword = AdmiPassword.getText();
		
		if (admilogin.AdmiLogin(in_username, tryPassword)){
			
			lblStatus1.setText("Login Successful");
			
			staffName = "Manager";
			String time = GetTime.now();
			String query = new String("INSERT INTO activityLog (time,name,activity)VALUES ('"+time+"','"+staffName+"','login')");
			
			SQLiteConnection.write(query);

			//Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/AdmiIn.fxml"));
			//prevStage.close();
			//prevStage=primaryStage;
			Scene scene2 = new Scene(root);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene2);
			//primaryStage.show();
			//inLabel.setText(in_username+" logged in");
		
		} else {
			lblStatus1.setText("Wrong Username & Password Combination");
		}
	};

}
