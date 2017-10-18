package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDB {
	public static int save(String name,String email,String message){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into message_request values(?,?,?)");
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, message);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
}
