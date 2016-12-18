package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * This application runs a restaurant billing management system providing functions including:
 * 	1. Place new order for different tables.
 * 	2. Modify the latest order of each table.
 * 	3. View history order list and search the list based on table number, dates and times intervals and ordered food.
 * 	4. There is a manager account, which gives you several additional functions: 
 * 		a. Edit menu.
 * 		b. Edit staff list.
 * 		c. View staff activity logs.
 * 		d. Export the search result of history orders as a CSV file.
 * 		e. Import a CSV file containing orders into the database.
 * 
 * 	This app is developed in JAVAFX and using SQLite as database, all informations are stored in the database file: orders.db.
 * 
 * 	Exported file is at the root folder of the app with the name: Export Orders.csv.
 * 	
 * 	An external library: sqlite-jdbc-3.15.1.jar is used to support the SQLite. 
 *  The library was download from: https://bitbucket.org/xerial/sqlite-jdbc/downloads
 * 
 *  The class MaskField is a costomised TextField class, used in Search.fxml and StaffSearch.fxml for formatted time input.
 *  This class is created by vas7n [12 Sep 2015], Accessed from: https://github.com/vas7n/VAMaskField on 10 Dec 2016.
 * 
 * @author      Mingxuan Mei
 * @version     1.0
 * @since       2016-12-16         
 */

public class Main extends Application {
	static Stage thestage;
	
	/**
	 * <code>start</code> Initialise the first screen with Login.fxml, provides two ways to login to the app: 
	 * General staff login and Manager login, they will lead you to different screens.
	 * 
	 * @param  primaryStage This is the Stage (window) which runs the app, the whole app will be running on the same Stage to provide a continuous experience.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			thestage = primaryStage;
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/Login.fxml"));
			Parent root = (Parent)myLoader.load(); 
			
//			MainController controller = (MainController) myLoader.getController();
//			controller.setPrevStage(thestage);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			thestage.setScene(scene);
			thestage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <code>main</code> The main method of the whole app.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
