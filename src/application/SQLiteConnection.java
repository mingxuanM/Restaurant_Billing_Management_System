package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* This class contains all methods that communicate with the database and a connection method.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16
*/

public class SQLiteConnection {
	
	/**
	 * <code>Connect</code> This method establishes the connection with the database <code>orders.db</code>. 
	 * All following methods use this connection to communicate with the database.
	 * The external library: sqlite-jdbc-3.15.1.jar is used here to support the connection.
	 * The library was download from: https://bitbucket.org/xerial/sqlite-jdbc/downloads
	 * 
	 * @return Connection This returns the connection with the database <code>orders.db</code>.
	 *  
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static Connection Connect(){
		
		try {
			Class.forName("org.sqlite.JDBC");  
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:orders.db");
	        return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
	}
	
	/**
	 * <code>MainMenu</code> This method retrieves current menu in database.
	 * 
	 * @return ResultSet This returns items in the <code>menu</code> table of database.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet MainMenu() {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select name, type, price from menu ORDER BY type ASC;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			//preparedStatement.setString(1, "main");
			resultSet = preparedStatement.executeQuery();
			
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				//preparedStatement.close();
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>MainMenu</code> This method retrieves all staff accounts in database.
	 * 
	 * @return ResultSet This returns all staff accounts in the <code>staffs</code> table of database.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet GetStaffList() {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from staffs ORDER BY FirstName;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			//preparedStatement.setString(1, "main");
			resultSet = preparedStatement.executeQuery();
			
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				//preparedStatement.close();
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>MainMenu</code> This method retrieves all accounts' activities recorded in database.
	 * 
	 * @return ResultSet This returns all recorded activities in the <code>activityLog</code> table of database.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet GetActivityList() {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from activityLog ORDER BY time;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			//preparedStatement.setString(1, "main");
			resultSet = preparedStatement.executeQuery();
			
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>getPrice</code> This method searches and retrieves the price of input item in the menu.
	 * 
	 * @param  name This is the name of the item to be searched.
	 * @return double This returns the price of searched item.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static double getPrice(String name) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select price from menu where name = ?;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			Double price = resultSet.getDouble("price");
			return price;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
        
	}
	
	/**
	 * <code>GetCurrentOrder</code> This method searches and retrieves the latest order of selected table.
	 * 
	 * @param  i This is the number of the table to be searched.
	 * @return ResultSet This returns the items in the latest order of selected table.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet GetCurrentOrder(int i) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT time FROM orders WHERE tableNo = ? ORDER BY time DESC LIMIT 1;";
		String query2 = "SELECT * FROM orders WHERE tableNo = ? AND time = ?;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setInt(1, i);
			resultSet = preparedStatement.executeQuery();
			String latestTime = resultSet.getString("time");
			
			resultSet.close();
			preparedStatement.close();
			Connect().close();
			
			preparedStatement = Connect().prepareStatement(query2);
			preparedStatement.setInt(1, i);
			preparedStatement.setString(2, latestTime);
			resultSet = preparedStatement.executeQuery();
			
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}

	/**
	 * <code>GetCurrentOrder</code> This method retrieves the time of all orders in the database.
	 * 
	 * @return ResultSet This returns the time of all orders in the database.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet allOrders() {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT DISTINCT time FROM orders ORDER BY time DESC;";
		
		try {
			
			preparedStatement = Connect().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
	
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>searchItem</code> This method retrieves the time of the orders containing selected item in the database.
	 * 
	 * @param  item This is the select item which needed to be searched.
	 * @return ResultSet This returns the time of the orders containing selected item.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet searchItem(String item) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT DISTINCT time FROM orders WHERE item = ? ORDER BY time DESC;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setString(1, item);
			resultSet = preparedStatement.executeQuery();
	
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>searchTable</code> This method retrieves the time of the orders of selected table in the database.
	 * 
	 * @param  table This is the select table which needed to be searched.
	 * @return ResultSet This returns the time of the orders of selected table.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet searchTable(int table) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT DISTINCT time FROM orders WHERE tableNo = ? ORDER BY time DESC;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setInt(1, table);
			resultSet = preparedStatement.executeQuery();
	
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>searchTableItem</code> This method retrieves the time of the orders of selected table containing selected item in the database.
	 * 
	 * @param  table This is the select table which needed to be searched.
	 * @param  item This is the select item which needed to be searched.
	 * 
	 * @return ResultSet This returns the time of the orders of selected table containing selected item.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet searchTableItem(int table, String item) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT DISTINCT time FROM orders WHERE tableNo = ? AND item = ? ORDER BY time DESC;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setInt(1, table);
			preparedStatement.setString(2, item);
			resultSet = preparedStatement.executeQuery();
	
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>finalSearch</code> This method retrieves orders at the time in searched result from previous search actions.
	 * 
	 * @param  time This is the order time in searched result from previous search actions.
	 * 
	 * @return ResultSet This returns the items in the order at specified time.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static ResultSet finalSearch(String time) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM orders WHERE time = ? ORDER BY time DESC;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setString(1, time);
			resultSet = preparedStatement.executeQuery();
	
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>removeCurrentOrder</code> This method removes the latest order of selected table to prepare for <code>modify</code> order.
	 * 
	 * @param  table This is the select table which needs to remove the latest order.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static void removeCurrentOrder(int table) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement2 = null;

		String query = "SELECT time FROM orders WHERE tableNo = ? ORDER BY time DESC LIMIT 1;";
		String query2 = "DELETE FROM orders WHERE tableNo = ? AND time = ?;";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setInt(1, table);
			resultSet = preparedStatement.executeQuery();
			String latestTime = resultSet.getString("time");
			
			resultSet.close();
			preparedStatement.close();
			Connect().close();
			
			preparedStatement2 = Connect().prepareStatement(query2);
			preparedStatement2.setInt(1, table);
			preparedStatement2.setString(2, latestTime);
			preparedStatement2.executeUpdate();
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				preparedStatement2.close();
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>GetStaffName</code> This method retrieves the first name and last name of the account with specified username.
	 * 
	 * @param  username This is the username used to login to current session.
	 * 
	 * @return String This returns the first name and last name of the specified username.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static String GetStaffName(String username) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select FirstName, LastName from staffs where Username = ?";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				
				String name = resultSet.getString("FirstName")+" "+resultSet.getString("LastName");
				
				return name;
			} else {
				
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				Connect().close();
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>StaffLogin</code> This method checks if the provided staff login information is correct.
	 * 
	 * @param  username This is the username used to login to current session.
	 * @param  password This is the password used to login to current session.
	 * 
	 * @return Boolean This returns true if the username and password combination is correct and returns false if incorrect.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public Boolean StaffLogin(String username, String password) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet1 = null;
		String query = "select Password from staffs where Username = ?";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet1 = preparedStatement.executeQuery();
			
			if (resultSet1.next()) {
				
				String realPass = resultSet1.getString("Password");
				
				return (realPass.equals(password) ? true : false);
			} else {
				
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				resultSet1.close();
				preparedStatement.close();
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>AdmiLogin</code> This method checks if the provided manager login information is correct.
	 * 
	 * @param  username This is the username used to login to current session.
	 * @param  password This is the password used to login to current session.
	 * 
	 * @return Boolean This returns true if the username and password combination is correct and returns false if incorrect.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public Boolean AdmiLogin(String username, String password) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select Password from manager where Username = ?";
		
		try {
			preparedStatement = Connect().prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				
				String realPass = resultSet.getString("Password");
				
				return (realPass.equals(password) ? true : false);
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
	}
	
	/**
	 * <code>write</code> This method <code>executeUpdate</code> with the input string as query.
	 * 
	 * @param  query This is the string that needed to be executed.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16         
	 */
	
	public static void write(String query) {
		PreparedStatement preparedStatement1 = null;
		
		try {
			preparedStatement1 = Connect().prepareStatement(query);
			preparedStatement1.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				preparedStatement1.close();
				Connect().close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
