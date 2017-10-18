package my.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon {
	public static Connection getCon(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");  
			con = DriverManager.getConnection("jdbc:mysql://au-cdbr-sl-syd-01.cleardb.net:3306/ibmx_cb0b9ab23f329f2","b9539a6f8ca59d","85dadd73");
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return con;
	}
}
