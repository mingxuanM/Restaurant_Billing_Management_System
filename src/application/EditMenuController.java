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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
* This class is the controller of the edit menu screen: <code>EditMenu.fxml</code>.
* Provides add, remove and replace method to edit the menu.
* 
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16          
*/

public class EditMenuController implements Initializable{
	
	@FXML
	private TextField addName;
	
	@FXML
	private TextField addPrice;
	
	@FXML
	private ComboBox<String> addType;
	
	@FXML
	private TableView<Menu> editMenuTable;
	
	@FXML
	private Label status;
	
	ObservableList<Menu> items;

	/**
	 * <code>initialize</code> Initialise the screens with <code>TableView editMenuTable</code>, populate the TableView with current menu in the database.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addType.getItems().addAll("main", "starter", "drink");
		
		editMenuTable.getColumns().clear();
		
		TableColumn<Menu, String> newOrderList = new TableColumn<>("Name");
		TableColumn<Menu, String> newOrderType = new TableColumn<>("Type");
		TableColumn<Menu, Double> OrderPrice = new TableColumn<>("Price");
		
		ResultSet mainResultSet = SQLiteConnection.MainMenu();
		items =FXCollections.observableArrayList();
	
		try {
			while(mainResultSet.next()){
				Menu name = new Menu(mainResultSet.getString("name"), mainResultSet.getString("type"), mainResultSet.getDouble("price"), 0, "", "");
				items.add(name);
			}
			
			editMenuTable.setItems(items);
			newOrderList.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
			newOrderType.setCellValueFactory(new PropertyValueFactory<Menu, String>("type"));
			OrderPrice.setCellValueFactory(new PropertyValueFactory<Menu, Double>("price"));
			
			editMenuTable.getColumns().addAll(newOrderList, newOrderType, OrderPrice);
			
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
	 * <code>add</code> add the given item with content in all specified fields into the database and the <code>TableView editMenuTable</code>.
	 * 
	 * @param e Listener of the <code>Add</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void add (ActionEvent e){
		
		if(addName.getText().isEmpty() || addPrice.getText().isEmpty() || addType.getValue().isEmpty() || !isDouble(addPrice.getText())){
			status.setText("Please add qualified information to all fields.");
		} else {
			items.add(new Menu(addName.getText(), addType.getValue(), Double.parseDouble(addPrice.getText()), 0, null, null));
			String query = new String("INSERT INTO menu (name,type,price)VALUES ('"+addName.getText()+"','"+addType.getValue()+"',"+Double.parseDouble(addPrice.getText())+")");
			SQLiteConnection.write(query);
		}
	}
	
	/**
	 * <code>replace</code> Replace the selected item with content in all specified fields and write the changes into the database.
	 * 
	 * @param e Listener of the <code>Replace</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void replace (ActionEvent e){
		if(editMenuTable.getSelectionModel().isEmpty() || (addName.getText().isEmpty() && addPrice.getText().isEmpty() && addType.getValue().isEmpty()) || !isDouble(addPrice.getText())){
			status.setText("Please add qualified information to the fields and select one row in the Table to be replaced.");
		} else {
			String strName = items.get(editMenuTable.getSelectionModel().getSelectedIndex()).getname();
			String strType = items.get(editMenuTable.getSelectionModel().getSelectedIndex()).gettype();
			Double strPrice = items.get(editMenuTable.getSelectionModel().getSelectedIndex()).getprice();
			
			String rePlacedName = strName;
			
			if (!addName.getText().isEmpty()){
				strName=addName.getText();
			}
			
			if (!addPrice.getText().isEmpty()){
				strPrice=Double.parseDouble(addPrice.getText());
			}
			
			if (!addType.getValue().isEmpty()){
				strType=addType.getValue();
			}

			items.set(editMenuTable.getSelectionModel().getSelectedIndex(), new Menu(strName, strType, strPrice, 0, null, null));
			String query = new String("UPDATE menu SET name = '"+strName+"',type = '"+strType+"',price = "+strPrice+" WHERE name = '"+rePlacedName+"';");
			SQLiteConnection.write(query);
		}
	}
	
	/**
	 * <code>remove</code> Remove the selected item from <code>TableView editMenuTable</code> and database.
	 * 
	 * @param e Listener of the <code>Remove</code> button.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public void remove (ActionEvent e){
		if(editMenuTable.getSelectionModel().isEmpty()){
			status.setText("Please select one row in the Table to be removed.");
		} else {
			String removedName = items.get(editMenuTable.getSelectionModel().getSelectedIndex()).getname();
			items.remove(editMenuTable.getSelectionModel().getSelectedIndex());
			String query = new String("DELETE FROM menu WHERE name = '"+removedName+"';");
			SQLiteConnection.write(query);
		}
	}
	
	/**
	 * <code>isDouble</code> This method check if the input price is a double number.
	 * 
	 * @param str The input string is the text get from <code>TextField addPrice</code>.
	 * 
	 * @return Boolean This returns the boolean value of whether the input string can be parsed into a double.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	

}
