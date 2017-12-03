package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import my.beans.UserBean;

public class UserDB {
	public static int save(UserBean bean) throws SQLIntegrityConstraintViolationException{
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into user_detail values(?,?,?,?)");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getEmail());
			ps.setString(3, bean.getPassword());
			ps.setLong(4, bean.getMobileNo());
			status = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException ex){
			throw ex;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static String getUserName(String userid){
		String user_name = null;
		try(Connection con = DBCon.getCon()){ 
			PreparedStatement ps = con.prepareStatement("select name from user_detail where email=?");
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			user_name = rs.getString(1);
		} catch (SQLException e){
			e.printStackTrace();
		}
		return user_name;
	}
	
	public static List<UserBean> getUsers(){
		List<UserBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from user_detail");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				UserBean bean = new UserBean();
				bean.setName(rs.getString(1));
				bean.setEmail(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setMobileNo(rs.getLong(4));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}