## Restaurant_Billing_Management_System - COMPGC01 Coursework

Youtube video address: https://youtu.be/w5pjWKsDLh0

Manager test account:username: ``1234567``, password: ``1234567``;
Staff test account:username: ``1``, password: ``1``;

---

1. This application runs a restaurant billing management system providing functions including:  
 	1). Place new order for different tables.  
  	2). Modify the latest order of each table.  
  	3). View history order list and search the list based on table number, dates and times intervals and ordered food.  
  	4). There is a manager account, which gives you several additional functions:  
  		&emsp;a. Edit menu.  
 		&emsp;b. Edit staff list.  
 		&emsp;c. View staff activity logs.  
 		&emsp;d. Export the search result of history orders as a CSV file at the root folder of the app with the name: 'Export Orders.csv'.  
  		&emsp;e. Import a CSV file containing orders into the database.  
2. This app is developed in JAVAFX and using SQLite as database, all informations are stored in the database file: 'orders.db'.  
The database has 5 tables:  
	1). activityLog: Stores all recorded activities of all accounts with name, time and type of activity.  
	2). manager: Stores the manager account including manager's first name, last name, username and password.  
	3). staffs: Stores all staffs' account including staffs' first name, last name, username and password.  
	4). menu: Stores current menu, each item has a name, type and price.  
	5). orders: Stores all orders, each order is stored as a list of items in the order with same 'time' and 'tableNo' with their own 'special request' and 'comment'.  
	 
3. An external library: sqlite-jdbc-3.15.1.jar is used to support the SQLite. 
The library was download from: https://bitbucket.org/xerial/sqlite-jdbc/downloads
  
4. The class MaskField is a costomised TextField class, used in Search.fxml and StaffSearch.fxml for formatted time input.
This class is created by 'vas7n' [12 Sep 2015], Accessed from: https://github.com/vas7n/VAMaskField on 10 Dec 2016.
