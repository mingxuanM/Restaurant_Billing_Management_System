package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/**
* This class is the controller of the home screens after login screen: <code>AdmiIn.fxml</code> and <code>StaffIn.fxml</code> for manager and staff respectively,
* give access to all functions of the app.
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16          
*/

public class MainController implements Initializable{
	
	private int table1, table2, table3, table4, table5;
	
	ObservableList<Order> result;
	
	
	
	@FXML
	private Label newOrder_Label;
	
	@FXML
	private Label table;
	
	@FXML
	private Label modifyTable;
	
	@FXML
	private SplitPane newOrder;
	
	@FXML
	private SplitPane historyOrder;
	
	@FXML
	private Label historyOrder_Label;
	
	@FXML
	private TableView<Menu> menuTable;
	
	@FXML
	private TableView<Menu> currentTable; 

	/**
	 * <code>initialize</code> Initialise the screens with 5 variables, one for each table, 
	 * to achieve the "click table once for new order, click again for modify order" function.
	 * 
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		table1=1;
		table2=1; 
		table3=1; 
		table4=1;
		table5=1;
		
	}
	
	/**
	 * <code>logout</code> Log current user out and switch the screen to Login.fxml.
	 * Record this activity into database.
	 * 
	 * @param event Listener of the <code>logout</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void logout (ActionEvent event) throws IOException{
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml")); 
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.thestage.setScene(scene);
		
		
		String query = new String("INSERT INTO activityLog (time,name,activity)VALUES ('"+GetTime.now()+"','"+LoginController.staffName+"','logout')");
		
		SQLiteConnection.write(query);
	};
	
	/**
	 * <code>newOrder1</code> This method provides the "click table once for new order, click again for modify order" function for table 1.
	 * The <code>newOrder</code> and <code>historyOrder</code>(Latest Order) pages are overlapping in a StackPane and are send to front when needed.
	 * 
	 * Before the <code>newOrder</code> page is sent to front, the TableView <code>menuTable</code> is populated with current menu with several editable TableColumn.
	 * Before the <code>historyOrder</code> page is sent to front, the TableView <code>currentTable</code> is populated with the latest order and not-ordered items in the menu .
	 * 
	 * @param e Listener of the <code>Table1</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	public void newOrder1 (ActionEvent e) throws SQLException{
		
		menuTable.getColumns().clear();
		
		TableColumn<Menu, String> newOrderList = new TableColumn<>("Name");
		TableColumn<Menu, String> newOrderType = new TableColumn<>("Type");
		TableColumn<Menu, Double> OrderPrice = new TableColumn<>("Price");
		TableColumn<Menu, Integer> newOrderQuantity = new TableColumn<>("Quantity");
		TableColumn<Menu, String> newOrderSpecialReq = new TableColumn<>("Special Request");
		TableColumn<Menu, String> newOrderComment = new TableColumn<>("Comment");
		//menu.menuGenerater();
		ResultSet mainResultSet = SQLiteConnection.MainMenu();
		ObservableList<Menu> items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Menu name = new Menu(mainResultSet.getString("name"), mainResultSet.getString("type"), mainResultSet.getDouble("price"), 0, "", "");
				items.add(name);
			}
			
			menuTable.setItems(items);
			newOrderList.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
			newOrderType.setCellValueFactory(new PropertyValueFactory<Menu, String>("type"));
			OrderPrice.setCellValueFactory(new PropertyValueFactory<Menu, Double>("price"));
			//newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setCellFactory(TextFieldTableCell.<Menu, Integer>forTableColumn(new IntegerStringConverter()) );
			newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, Integer> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setquantity(t.getNewValue());
		                }
		            }
		        );
			
			newOrderSpecialReq.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderSpecialReq.setCellValueFactory(new PropertyValueFactory<Menu, String>("specialReq"));
			newOrderSpecialReq.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setspecialReq(t.getNewValue());
		                }
		            }
		        );
			
			newOrderComment.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderComment.setCellValueFactory(new PropertyValueFactory<Menu, String>("comments"));
			newOrderComment.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setcomments(t.getNewValue());
		                }
		            }
		        );
			
		
			menuTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if (table1==1){
			newOrder_Label.setText("New Order for Table");
			table.setText("1");
			newOrder.toFront();
			table1=0;
			table2=1; 
			table3=1;
			table4=1; 
			table5=1;
		}else{
			currentTable.getColumns().clear();
			
			ResultSet menuResultSet = SQLiteConnection.MainMenu();
			
			ResultSet currentResultSet = SQLiteConnection.GetCurrentOrder(Integer.parseInt(table.getText()));
			ObservableList<Menu> currentItems =FXCollections.observableArrayList();
			ArrayList<String> currentNames = new ArrayList<String>();
			
			double price = 0.0;
			
			try {
				while(currentResultSet.next()){
					Menu name = new Menu(currentResultSet.getString("item"), currentResultSet.getString("type"), SQLiteConnection.getPrice(currentResultSet.getString("item")), currentResultSet.getInt("amount"), currentResultSet.getString("specialRequests"), currentResultSet.getString("comments"));
					currentItems.add(name);
					currentNames.add(currentResultSet.getString("item"));
					price = price + SQLiteConnection.getPrice(currentResultSet.getString("item"))*currentResultSet.getInt("amount");
				}
				
				while (menuResultSet.next()){
					if (currentNames.contains(menuResultSet.getString("name"))){
						//Do nothing.
					} else {
						Menu name = new Menu(menuResultSet.getString("name"), menuResultSet.getString("type"), menuResultSet.getDouble("price"), 0, "", "");
						currentItems.add(name);
					}
				}
				
				Menu orderPrice = new Menu("Current Bill:", "", price, 0, "", "");
				currentItems.add(orderPrice);
				
				
				currentTable.setItems(currentItems);
				currentTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			historyOrder_Label.setText("Latest Order of Table");
			modifyTable.setText("1");
			historyOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=1; 
			table5=1;
		}
	};
	
	/**
	 * <code>newOrder2</code> Provides the same function as <code>newOrder1</code> for table 2.
	 * 
	 * @param e Listener of the <code>Table2</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	public void newOrder2 (ActionEvent e) {
		
		menuTable.getColumns().clear();
		
		TableColumn<Menu, String> newOrderList = new TableColumn<>("Name");
		TableColumn<Menu, String> newOrderType = new TableColumn<>("Type");
		TableColumn<Menu, Double> OrderPrice = new TableColumn<>("Price");
		TableColumn<Menu, Integer> newOrderQuantity = new TableColumn<>("Quantity");
		TableColumn<Menu, String> newOrderSpecialReq = new TableColumn<>("Special Request");
		TableColumn<Menu, String> newOrderComment = new TableColumn<>("Comment");
		//menu.menuGenerater();
		ResultSet mainResultSet = SQLiteConnection.MainMenu();
		ObservableList<Menu> items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Menu name = new Menu(mainResultSet.getString("name"), mainResultSet.getString("type"), mainResultSet.getDouble("price"), 0, "", "");
				items.add(name);
			}
			
			menuTable.setItems(items);
			newOrderList.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
			newOrderType.setCellValueFactory(new PropertyValueFactory<Menu, String>("type"));
			OrderPrice.setCellValueFactory(new PropertyValueFactory<Menu, Double>("price"));
			//newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setCellFactory(TextFieldTableCell.<Menu, Integer>forTableColumn(new IntegerStringConverter()) );
			newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, Integer> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setquantity(t.getNewValue());
		                }
		            }
		        );
			
			newOrderSpecialReq.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderSpecialReq.setCellValueFactory(new PropertyValueFactory<Menu, String>("specialReq"));
			newOrderSpecialReq.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setspecialReq(t.getNewValue());
		                }
		            }
		        );
			
			newOrderComment.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderComment.setCellValueFactory(new PropertyValueFactory<Menu, String>("comments"));
			newOrderComment.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setcomments(t.getNewValue());
		                }
		            }
		        );
			
		
			menuTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (table2==1){
			newOrder_Label.setText("New Order for Table");
			table.setText("2");
			newOrder.toFront();
			table1=1;
			table2=0; 
			table3=1; 
			table4=1; 
			table5=1;
		}else{
			currentTable.getColumns().clear();
			
			ResultSet menuResultSet = SQLiteConnection.MainMenu();
			
			ResultSet currentResultSet = SQLiteConnection.GetCurrentOrder(Integer.parseInt(table.getText()));
			ObservableList<Menu> currentItems =FXCollections.observableArrayList();
			ArrayList<String> currentNames = new ArrayList<String>();
			Double price=0.0;
			try {
				
				while(currentResultSet.next()){
					Menu name = new Menu(currentResultSet.getString("item"), currentResultSet.getString("type"), SQLiteConnection.getPrice(currentResultSet.getString("item")), currentResultSet.getInt("amount"), currentResultSet.getString("specialRequests"), currentResultSet.getString("comments"));
					currentItems.add(name);
					currentNames.add(currentResultSet.getString("item"));
					price = price + SQLiteConnection.getPrice(currentResultSet.getString("item"))*currentResultSet.getInt("amount");
				}
				
				while (menuResultSet.next()){
					if (currentNames.contains(menuResultSet.getString("name"))){
						//Do nothing.
					} else {
						Menu name = new Menu(menuResultSet.getString("name"), menuResultSet.getString("type"), menuResultSet.getDouble("price"), 0, "", "");
						currentItems.add(name);
					}
				}
				
				Menu orderPrice = new Menu("Current Bill:", "", price, 0, "", "");
				currentItems.add(orderPrice);
				
				currentTable.setItems(currentItems);
				currentTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			historyOrder_Label.setText("Latest Order of Table");
			modifyTable.setText("2");
			historyOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=1; 
			table5=1;
		}
	};
	
	/**
	 * <code>newOrder3</code> Provides the same function as <code>newOrder1</code> for table 3.
	 * 
	 * @param e Listener of the <code>Table3</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	public void newOrder3 (ActionEvent e) {
		menuTable.getColumns().clear();
		
		TableColumn<Menu, String> newOrderList = new TableColumn<>("Name");
		TableColumn<Menu, String> newOrderType = new TableColumn<>("Type");
		TableColumn<Menu, Double> OrderPrice = new TableColumn<>("Price");
		TableColumn<Menu, Integer> newOrderQuantity = new TableColumn<>("Quantity");
		TableColumn<Menu, String> newOrderSpecialReq = new TableColumn<>("Special Request");
		TableColumn<Menu, String> newOrderComment = new TableColumn<>("Comment");
		//menu.menuGenerater();
		ResultSet mainResultSet = SQLiteConnection.MainMenu();
		ObservableList<Menu> items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Menu name = new Menu(mainResultSet.getString("name"), mainResultSet.getString("type"), mainResultSet.getDouble("price"), 0, "", "");
				items.add(name);
			}
			
			menuTable.setItems(items);
			newOrderList.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
			newOrderType.setCellValueFactory(new PropertyValueFactory<Menu, String>("type"));
			OrderPrice.setCellValueFactory(new PropertyValueFactory<Menu, Double>("price"));
			//newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setCellFactory(TextFieldTableCell.<Menu, Integer>forTableColumn(new IntegerStringConverter()) );
			newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, Integer> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setquantity(t.getNewValue());
		                }
		            }
		        );
			
			newOrderSpecialReq.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderSpecialReq.setCellValueFactory(new PropertyValueFactory<Menu, String>("specialReq"));
			newOrderSpecialReq.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setspecialReq(t.getNewValue());
		                }
		            }
		        );
			
			newOrderComment.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderComment.setCellValueFactory(new PropertyValueFactory<Menu, String>("comments"));
			newOrderComment.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setcomments(t.getNewValue());
		                }
		            }
		        );
			
		
			menuTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (table3==1){
			newOrder_Label.setText("New Order for Table");
			table.setText("3");
			newOrder.toFront();
			table1=1;
			table2=1; 
			table3=0; 
			table4=1; 
			table5=1;
		}else{
			currentTable.getColumns().clear();
			
			ResultSet menuResultSet = SQLiteConnection.MainMenu();
			
			ResultSet currentResultSet = SQLiteConnection.GetCurrentOrder(Integer.parseInt(table.getText()));
			ObservableList<Menu> currentItems =FXCollections.observableArrayList();
			ArrayList<String> currentNames = new ArrayList<String>();
			Double price=0.0;
			try {
				while(currentResultSet.next()){
					Menu name = new Menu(currentResultSet.getString("item"), currentResultSet.getString("type"), SQLiteConnection.getPrice(currentResultSet.getString("item")), currentResultSet.getInt("amount"), currentResultSet.getString("specialRequests"), currentResultSet.getString("comments"));
					currentItems.add(name);
					currentNames.add(currentResultSet.getString("item"));
					price = price + SQLiteConnection.getPrice(currentResultSet.getString("item"))*currentResultSet.getInt("amount");

				}
				
				while (menuResultSet.next()){
					if (currentNames.contains(menuResultSet.getString("name"))){
						//Do nothing.
					} else {
						Menu name = new Menu(menuResultSet.getString("name"), menuResultSet.getString("type"), menuResultSet.getDouble("price"), 0, "", "");
						currentItems.add(name);
					}
				}
				
				Menu orderPrice = new Menu("Current Bill:", "", price, 0, "", "");
				currentItems.add(orderPrice);
				currentTable.setItems(currentItems);
				currentTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			historyOrder_Label.setText("Latest Order of Table");
			modifyTable.setText("3");
			historyOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=1; 
			table5=1;
		}
	};
	
	/**
	 * <code>newOrder4</code> Provides the same function as <code>newOrder1</code> for table 4.
	 * 
	 * @param e Listener of the <code>Table4</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	public void newOrder4 (ActionEvent e) {
		menuTable.getColumns().clear();
		
		TableColumn<Menu, String> newOrderList = new TableColumn<>("Name");
		TableColumn<Menu, String> newOrderType = new TableColumn<>("Type");
		TableColumn<Menu, Double> OrderPrice = new TableColumn<>("Price");
		TableColumn<Menu, Integer> newOrderQuantity = new TableColumn<>("Quantity");
		TableColumn<Menu, String> newOrderSpecialReq = new TableColumn<>("Special Request");
		TableColumn<Menu, String> newOrderComment = new TableColumn<>("Comment");
		//menu.menuGenerater();
		ResultSet mainResultSet = SQLiteConnection.MainMenu();
		ObservableList<Menu> items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Menu name = new Menu(mainResultSet.getString("name"), mainResultSet.getString("type"), mainResultSet.getDouble("price"), 0, "", "");
				items.add(name);
			}
			
			menuTable.setItems(items);
			newOrderList.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
			newOrderType.setCellValueFactory(new PropertyValueFactory<Menu, String>("type"));
			OrderPrice.setCellValueFactory(new PropertyValueFactory<Menu, Double>("price"));
			//newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setCellFactory(TextFieldTableCell.<Menu, Integer>forTableColumn(new IntegerStringConverter()) );
			newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, Integer> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setquantity(t.getNewValue());
		                }
		            }
		        );
			
			newOrderSpecialReq.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderSpecialReq.setCellValueFactory(new PropertyValueFactory<Menu, String>("specialReq"));
			newOrderSpecialReq.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setspecialReq(t.getNewValue());
		                }
		            }
		        );
			
			newOrderComment.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderComment.setCellValueFactory(new PropertyValueFactory<Menu, String>("comments"));
			newOrderComment.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setcomments(t.getNewValue());
		                }
		            }
		        );
			
		
			menuTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (table4==1){
			newOrder_Label.setText("New Order for Table");
			table.setText("4");
			newOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=0; 
			table5=1;
		}else{
			currentTable.getColumns().clear();
			
			ResultSet menuResultSet = SQLiteConnection.MainMenu();
			
			ResultSet currentResultSet = SQLiteConnection.GetCurrentOrder(Integer.parseInt(table.getText()));
			ObservableList<Menu> currentItems =FXCollections.observableArrayList();
			ArrayList<String> currentNames = new ArrayList<String>();
			Double price=0.0;
			try {
				while(currentResultSet.next()){
					Menu name = new Menu(currentResultSet.getString("item"), currentResultSet.getString("type"), SQLiteConnection.getPrice(currentResultSet.getString("item")), currentResultSet.getInt("amount"), currentResultSet.getString("specialRequests"), currentResultSet.getString("comments"));
					currentItems.add(name);
					currentNames.add(currentResultSet.getString("item"));
					price = price + SQLiteConnection.getPrice(currentResultSet.getString("item"))*currentResultSet.getInt("amount");

				}
				
				while (menuResultSet.next()){
					if (currentNames.contains(menuResultSet.getString("name"))){
						//Do nothing.
					} else {
						Menu name = new Menu(menuResultSet.getString("name"), menuResultSet.getString("type"), menuResultSet.getDouble("price"), 0, "", "");
						currentItems.add(name);
					}
				}
				Menu orderPrice = new Menu("Current Bill:", "", price, 0, "", "");
				currentItems.add(orderPrice);
				currentTable.setItems(currentItems);
				currentTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			historyOrder_Label.setText("Latest Order of Table");
			modifyTable.setText("4");
			historyOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=1; 
			table5=1;
		}
	};
	
	/**
	 * <code>newOrder5</code> Provides the same function as <code>newOrder1</code> for table 5.
	 * 
	 * @param e Listener of the <code>Table5</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	public void newOrder5 (ActionEvent e) {
		menuTable.getColumns().clear();
		
		TableColumn<Menu, String> newOrderList = new TableColumn<>("Name");
		TableColumn<Menu, String> newOrderType = new TableColumn<>("Type");
		TableColumn<Menu, Double> OrderPrice = new TableColumn<>("Price");
		TableColumn<Menu, Integer> newOrderQuantity = new TableColumn<>("Quantity");
		TableColumn<Menu, String> newOrderSpecialReq = new TableColumn<>("Special Request");
		TableColumn<Menu, String> newOrderComment = new TableColumn<>("Comment");
		//menu.menuGenerater();
		ResultSet mainResultSet = SQLiteConnection.MainMenu();
		ObservableList<Menu> items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Menu name = new Menu(mainResultSet.getString("name"), mainResultSet.getString("type"), mainResultSet.getDouble("price"), 0, "", "");
				items.add(name);
			}
			
			menuTable.setItems(items);
			newOrderList.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
			newOrderType.setCellValueFactory(new PropertyValueFactory<Menu, String>("type"));
			OrderPrice.setCellValueFactory(new PropertyValueFactory<Menu, Double>("price"));
			//newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setCellFactory(TextFieldTableCell.<Menu, Integer>forTableColumn(new IntegerStringConverter()) );
			newOrderQuantity.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("quantity"));
			newOrderQuantity.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, Integer> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setquantity(t.getNewValue());
		                }
		            }
		        );
			
			newOrderSpecialReq.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderSpecialReq.setCellValueFactory(new PropertyValueFactory<Menu, String>("specialReq"));
			newOrderSpecialReq.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setspecialReq(t.getNewValue());
		                }
		            }
		        );
			
			newOrderComment.setCellFactory(TextFieldTableCell.forTableColumn());
			newOrderComment.setCellValueFactory(new PropertyValueFactory<Menu, String>("comments"));
			newOrderComment.setOnEditCommit(
		            new EventHandler<CellEditEvent<Menu, String>>() {
		                @Override
		                public void handle(CellEditEvent<Menu, String> t) {
		                    ((Menu) t.getTableView().getItems().get(
		                            t.getTablePosition().getRow())
		                            ).setcomments(t.getNewValue());
		                }
		            }
		        );
			
		
			menuTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (table5==1){
			newOrder_Label.setText("Latest Order of Table");
			table.setText("5");
			newOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=1; 
			table5=0;
		}else{
			currentTable.getColumns().clear();
			
			ResultSet menuResultSet = SQLiteConnection.MainMenu();
			
			ResultSet currentResultSet = SQLiteConnection.GetCurrentOrder(Integer.parseInt(table.getText()));
			ObservableList<Menu> currentItems =FXCollections.observableArrayList();
			ArrayList<String> currentNames = new ArrayList<String>();
			Double price =0.0;
			try {
				while(currentResultSet.next()){
					Menu name = new Menu(currentResultSet.getString("item"), currentResultSet.getString("type"), SQLiteConnection.getPrice(currentResultSet.getString("item")), currentResultSet.getInt("amount"), currentResultSet.getString("specialRequests"), currentResultSet.getString("comments"));
					currentItems.add(name);
					currentNames.add(currentResultSet.getString("item"));
					price = price + SQLiteConnection.getPrice(currentResultSet.getString("item"))*currentResultSet.getInt("amount");

				}
				
				while (menuResultSet.next()){
					if (currentNames.contains(menuResultSet.getString("name"))){
						//Do nothing.
					} else {
						Menu name = new Menu(menuResultSet.getString("name"), menuResultSet.getString("type"), menuResultSet.getDouble("price"), 0, "", "");
						currentItems.add(name);
					}
				}
				Menu orderPrice = new Menu("Current Bill:", "", price, 0, "", "");
				currentItems.add(orderPrice);
				currentTable.setItems(currentItems);
				currentTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice, newOrderQuantity, newOrderSpecialReq, newOrderComment);
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			historyOrder_Label.setText("Latest Order of Table 5");
			modifyTable.setText("5");
			historyOrder.toFront();
			table1=1;
			table2=1; 
			table3=1; 
			table4=1; 
			table5=1;
		}
	};
	
	/**
	 * <code>orderList</code> Switch the screen to <code>Search.fxml</code> (Search order list, export and import SCV files) for manager.
	 * 
	 * @param e Listener of the <code>Order List</code> button when Manager logged in.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void orderList (ActionEvent e){
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/Search.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	};
	
	/**
	 * <code>staffList</code> Switch the screen to <code>EditStaff.fxml</code> (Add, Remove or Modify staff accounts) for manager.
	 * 
	 * @param e Listener of the <code>Staff List</code> button when Manager logged in.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void staffList (ActionEvent e){
		
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
		
	};
	
	/**
	 * <code>staffOrderList</code> Switch the screen to <code>StaffSearch.fxml</code> (Search order list) for general staffs.
	 * 
	 * @param e Listener of the <code>Order List</code> button when staffs logged in.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */

	public void staffOrderList (ActionEvent e){
		
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/StaffSearch.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	};
		
	/**
	 * <code>editMenu</code> Switch the screen to <code>EditMenu.fxml</code> (Add, Remove or Modify items in current menu) for manager.
	 * 
	 * @param e Listener of the <code>Order List</code> button when Manager logged in.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void editMenu(ActionEvent e){
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/EditMenu.fxml"));
			Scene scene3 = new Scene(root);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.thestage.setScene(scene3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * <code>submit</code> Submit current order into the database.
	 * Record this activity into database.
	 * @param e Listener of the <code>Submit</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void submit(ActionEvent e) {
		
		int i = 0;
		
		for (Menu item : menuTable.getItems()){ 
			if (item.getquantity() > 0){
				i++;
				int tableNo = Integer.parseInt(table.getText());
				String orderItem = new String(item.getname());
				String orderType = new String(item.gettype());
				int amount = item.getquantity();
				String specialReq = new String(item.getspecialReq());
				String comments = new String(item.getcomments());
				String time = new String(GetTime.now());
				
				String query = new String("INSERT INTO orders (time,tableNo,item,amount,specialRequests,comments,type)VALUES ("+"'"+time+"',"+tableNo+",'"+orderItem+"',"+amount+",'"+specialReq+"','"+comments+"','"+orderType+"'"+")");
				SQLiteConnection.write(query);
				System.out.println("SQLog:"+query);
			}
		}
		
		if(i>0){
			newOrder_Label.setText("Submitted for table");
			String query = new String("INSERT INTO activityLog (time,name,activity)VALUES ('"+GetTime.now()+"','"+LoginController.staffName+"','Submit order')");
			SQLiteConnection.write(query);
		}else{
			newOrder_Label.setText("Empty Order for table");
		}
		
	}
	
	/**
	 * <code>modify</code> Modify the latest order and write the changes into the database.
	 * Record this activity into database.
	 * @param e Listener of the <code>Modify</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public void modify (ActionEvent e) throws NumberFormatException{
		
		SQLiteConnection.removeCurrentOrder(Integer.parseInt(table.getText()));
		
		for (Menu item : currentTable.getItems()){ 
			if (item.getquantity() > 0){
				int tableNo = Integer.parseInt(table.getText());
				String orderItem = new String(item.getname());
				String orderType = new String(item.gettype());
				int amount = item.getquantity();
				String specialReq = new String(item.getspecialReq());
				String comments = new String(item.getcomments());
				String time = new String(GetTime.now());
				
				String query = new String("INSERT INTO orders (time,tableNo,item,amount,specialRequests,comments,type)VALUES ("+"'"+time+"',"+tableNo+",'"+orderItem+"',"+amount+",'"+specialReq+"','"+comments+"','"+orderType+"'"+")");
				SQLiteConnection.write(query);
				System.out.println("SQLog:"+query);
			}
		}
		
		String query = new String("INSERT INTO activityLog (time,name,activity)VALUES ('"+GetTime.now()+"','"+LoginController.staffName+"','Modify order')");
		SQLiteConnection.write(query);
		
		historyOrder_Label.setText("Modified for table");
		
	}

	
	
}

