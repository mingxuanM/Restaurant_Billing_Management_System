package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
* This class creates the type <code>Menu</code> for <code>TableView menuTable</code>, <code>TableView currentTable</code> in home screen of manager:<code>AdmiIn.fxml</code> and home screen of staff:<code>StaffIn.fxml</code>; 
* and for <code>TableView editMenuTable</code> in edit menu screen: <code>EditMenu.fxml</code>.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16        
*/

public class Menu {
	
	private StringProperty name;
	private StringProperty type;
	private DoubleProperty price;
	private IntegerProperty quantity;
	private StringProperty specialReq;
	private StringProperty comments;
	
	
	public Menu(String name, String type, Double price, int quantity, String specialReq, String comments){
		this.name = new SimpleStringProperty(name);
		this.type = new SimpleStringProperty(type);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.price = new SimpleDoubleProperty(price);
		this.specialReq = new SimpleStringProperty(specialReq);
		this.comments = new SimpleStringProperty(comments);

	}
	
	public String getname(){
		return name.get();
	}
	
	public String gettype(){
		return type.get();
	}
	
	public String getspecialReq(){
		return specialReq.get();
	}
	
	public String getcomments(){
		return comments.get();
	}
	
	public Integer getquantity(){
		return quantity.get();
	}
	
	public Double getprice(){
		return price.get();
	}
	
	public void setname(String name) {
		this.name.set(name);
    }
	
	public void settype(String type) {
		this.type.set(type);
    }
	
	public void setspecialReq(String specialReq) {
		this.specialReq.set(specialReq);
    }
	
	public void setcomments(String comments) {
		this.comments.set(comments);
    }
	
	public void setquantity(Integer quantity) {
		this.quantity.set(quantity);
    }
	
	public void setprice(Double price) {
		this.price.set(price);
    }
	
	public StringProperty nameProperty() {
        return name;
    }
	
	public StringProperty typeProperty() {
        return type;
    }
	
	public StringProperty specialReqProperty() {
        return specialReq;
    }
	
	public StringProperty commentsProperty() {
        return comments;
    }
	
	public IntegerProperty quantityProperty() {
        return quantity;
    }
	
	public DoubleProperty priceProperty() {
        return price;
    }

}
