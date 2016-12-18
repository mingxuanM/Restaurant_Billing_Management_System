package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
* This class is the controller of the order list search screen for manager: <code>Search.fxml</code> and for staff: <code>StaffSearch.fxml</code>
* Can search the orders based on table number, dates and times intervals and ordered food.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16          
*/

public class SearchController implements Initializable{
	
	ObservableList<Order> result;
	
	@FXML
	private ComboBox<Integer> searchTable;
	
	@FXML
	private ComboBox<String> searchItem;
	
	@FXML
	private TableView<Order> searchResult;
	
	@FXML
	private TextField fromTime;
	
	@FXML
	private TextField toTime;
	
	@FXML
	private Label timeSearch;
	
	@FXML
	private Button loadCombo;
	
	@FXML
	private Button exportResult;
	
	@FXML
	private Button importOrders;
	
	/**
	 * <code>initialize</code> Initialise the screens with <code>ComboBox searchTable</code> and <code>ComboBox searchItem</code>, 
	 * populate the <code>ComboBox searchTable</code> with numbers from 0 to 5;
	 * populate the <code>ComboBox searchItem</code> with name of items in the menu.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		searchTable.getItems().addAll(0,1,2,3,4,5);
		searchTable.setValue(0);
		
		ResultSet menu = SQLiteConnection.MainMenu();
		ArrayList<String> menuItems = new ArrayList<String>();
		menuItems.add("0");
		
			try {
				while(menu.next()){
					menuItems.add(menu.getString("name"));
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		searchItem.getItems().addAll(menuItems);
		searchItem.setValue("0");
	}
	
	/**
	 * <code>search</code> This method will take provided conditions based on any combination of table number, dates and times intervals or ordered food to find the matched orders 
	 * and populate these orders into the <code>TableView searchResult</code>.
	 * 
	 * @param e Listener of the <code>Search</code> button of Search.fxml and StaffSearch.fxml.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	@SuppressWarnings("unchecked")
	public void search (ActionEvent e){
		
		searchResult.getColumns().clear();
		
//		if(searchTable.getValue()==null){
//			loadCombo.fire();
//		}
		
		int tableNo = searchTable.getValue();
		String containItem = new String(searchItem.getValue());
		String fTime = new String(fromTime.getText());
		String tTime = new String(toTime.getText());
		ResultSet result1 = null;
		ResultSet result2 = null;
		ArrayList<String> searched = new ArrayList<String>();
		
		if (!(containItem.equals("0")) && tableNo == 0){
			result1=SQLiteConnection.searchItem(containItem);
		} else if ((containItem.equals("0")) && tableNo != 0){
			result1=SQLiteConnection.searchTable(tableNo);
		} else if (!(containItem.equals("0")) && tableNo != 0){
			result1=SQLiteConnection.searchTableItem(tableNo, containItem);
		} else if ((containItem.equals("0")) && tableNo == 0){
			result1=SQLiteConnection.allOrders();
		}

		if ((fTime.matches(".*\\d+.*"))&&(tTime.matches(".*\\d+.*"))){
			try {
				while(result1.next()){
					if(StringTo.time(result1.getString("time")).after(StringTo.time(fTime)) && StringTo.time(result1.getString("time")).before(StringTo.time(tTime))){
						searched.add(result1.getString("time"));
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (!(fTime.matches(".*\\d+.*")) && (tTime.matches(".*\\d+.*"))){
			timeSearch.setText("Please give full intervals (in format: yyyy-MM-dd HH:mm:ss)");
		} else if ((fTime.matches(".*\\d+.*")) && !(tTime.matches(".*\\d+.*"))){
			timeSearch.setText("Please give full intervals (in format: yyyy-MM-dd HH:mm:ss)");
		} else if (!(fTime.matches(".*\\d+.*")) && !(tTime.matches(".*\\d+.*"))){
			try {
				while(result1.next()){
					
						searched.add(result1.getString("time"));
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		result =FXCollections.observableArrayList();
		
		for (String item : searched){
			result2=SQLiteConnection.finalSearch(item);
			try {
				while(result2.next()){
					Order order = new Order(result2.getInt("tableNo"), result2.getString("time"), result2.getString("item"), result2.getString("type"), SQLiteConnection.getPrice(result2.getString("item")), result2.getInt("amount"), result2.getString("specialRequests"), result2.getString("comments"));
					result.add(order);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		searchResult.setItems(result);
		
		TableColumn<Order, Integer> tableNumber = new TableColumn<>("Table");
		TableColumn<Order, String> time = new TableColumn<>("Time");
		TableColumn<Order, String> name = new TableColumn<>("Item");
		TableColumn<Order, String> type = new TableColumn<>("Type");
		TableColumn<Order, Double> price = new TableColumn<>("Price");
		TableColumn<Order, Integer> quantity = new TableColumn<>("Quantity");
		TableColumn<Order, String> specialReq = new TableColumn<>("Special Request");
		TableColumn<Order, String> comments = new TableColumn<>("Comment");

		tableNumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("table"));
		time.setCellValueFactory(new PropertyValueFactory<Order, String>("time"));
		name.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
		type.setCellValueFactory(new PropertyValueFactory<Order, String>("type"));
		price.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
		quantity.setCellValueFactory(new PropertyValueFactory<Order, Integer>("quantity"));
		specialReq.setCellValueFactory(new PropertyValueFactory<Order, String>("specialReq"));
		comments.setCellValueFactory(new PropertyValueFactory<Order, String>("comments"));

		
		searchResult.getColumns().addAll(tableNumber, time, name, type, price, quantity, specialReq, comments);
		
		
	}
	
	/**
	 * <code>export</code> This method will export the search result into a SCV file at the root folder of the app with the name: Export Orders.csv.
	 * 
	 * @param e Listener of the <code>Export Result</code> button of Search.fxml.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void export(ActionEvent e) throws Exception {
		
		
	    Writer writer = null;
	    File file = new File("Export Orders.csv.");
	    writer = new BufferedWriter(new FileWriter(file));
	    try {
	    	writer.write("Table,Time,Item,Type,Price,Quantity,Special Request,Comment \n");
	        
	        for (Order item : result) {

	            String text = item.gettable() + "," + item.gettime() + "," + item.getname() + "," + item.gettype() + "," + item.getprice() + "," + item.getquantity() + "," + item.getspecialReq() + "," + item.getcomments() + "\n";

	            writer.write(text);
	        }
	    } catch (FileNotFoundException ex) {
	    	file.createNewFile();
	    	
	    	for (Order item : result) {

	            String text = item.gettable() + "," + item.gettime() + "," + item.getname() + "," + item.gettype() + "," + item.getprice() + "," + item.getquantity() + "," + item.getspecialReq() + "," + item.getcomments() + "\n";

	            writer.write(text);
	        }
	    	
	        ex.printStackTrace();
	    }
	    finally {
	    	exportResult.setText("Result Exported");
	    	writer.flush();
	        writer.close();
	    } 
	}
	
	/**
	 * <code>inport</code> This method will open a file chooser to choose a CSV file and import the containing orders into the app database. 
	 * 
	 * @param e Listener of the <code>Import Orders</code> button of Search.fxml.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void inport(ActionEvent e) throws Exception {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.thestage);
		
		//File file = new File("Inport Orders.csv");
		
		int i = 1;
		Scanner s = new Scanner(file);
		while (s.hasNextLine()){
			if (i>1){
			String[] order = s.nextLine().split(",");
			int tableNo = Integer.parseInt(order[0]);
			String time = order[1];
			String orderItem = order[2];
			String orderType = order[3];
			int amount = Integer.parseInt(order[5]);
			
			String specialReq = null;
			if (order[6]==null){
				specialReq = "";
			} else if (order[6]!=null) {
				specialReq = order[6];
			}
			
			String comments = null;
			if (order[7]==null){
				comments = "";
			} else if (order[7]!=null) {
				comments = order[7];
			}
			
			String query = new String("INSERT INTO orders (time,tableNo,item,amount,specialRequests,comments,type)VALUES ("+"'"+time+"',"+tableNo+",'"+orderItem+"',"+amount+",'"+specialReq+"','"+comments+"','"+orderType+"'"+")");
			System.out.println("SQLog:"+query);
			SQLiteConnection.write(query);
			
			} else {
				s.nextLine();
			}
			
			i++;
		}
		s.close();
		
		importOrders.setText("Orders Imported");
		
		
	}
	
	/**
	 * <code>backToAdmi</code> switch the screen back to AdmiIn.fxml.
	 * 
	 * @param e Listener of the <code>Back</code> button of Search.fxml.
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
	 * <code>backToStaff</code> switch the screen back to StaffIn.fxml.
	 * 
	 * @param e Listener of the <code>Back</code> button of StaffSearch.fxml.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void backToStaff (ActionEvent e){
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/StaffIn.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
}
