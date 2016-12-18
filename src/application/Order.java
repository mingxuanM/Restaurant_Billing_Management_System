package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
* This class creates the type <code>Order</code> for <code>TableView searchResult</code> in order list search screen: <code>Search.fxml</code> and <code>StaffSearch.fxml</code>.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16        
*/

public class Order {
	private IntegerProperty table;
	private StringProperty time;
	private StringProperty name;
	private StringProperty type;
	private DoubleProperty price;
	private IntegerProperty quantity;
	private StringProperty specialReq;
	private StringProperty comments;
	
	public Order(int table, String time, String name, String type, Double price, int quantity, String specialReq, String comments){
		this.table = new SimpleIntegerProperty(table);
		this.time = new SimpleStringProperty(time);
		this.name = new SimpleStringProperty(name);
		this.type = new SimpleStringProperty(type);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.price = new SimpleDoubleProperty(price);
		this.specialReq = new SimpleStringProperty(specialReq);
		this.comments = new SimpleStringProperty(comments);
	}
	
	public String gettime(){
		return time.get();
	}
	
	public Integer gettable(){
		return table.get();
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
	
	public void settime(String time) {
		this.time.set(time);
    }
	
	public void settable(Integer table) {
		this.table.set(table);
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
	
	public StringProperty timeProperty() {
        return time;
    }
	
	public IntegerProperty tableProperty() {
        return table;
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
