package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import my.beans.BillPollingBean;

public class BillPollingDB {
	public static int save(BillPollingBean bean){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into bill_polling values(?,?,?,?)");
			ps.setString(1, bean.getBillId());
			ps.setString(2, bean.getGroupMember());
			ps.setBoolean(3, bean.getPoll());
			ps.setString(4, bean.getGroupId());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int update(String group_member,String bill_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("update bill_polling set polling=? where group_member=? and bill_id=?");
			ps.setBoolean(1, true);
			ps.setString(2, group_member);
			ps.setString(3, bill_id);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int getPendingBillCount(String group_member,String group_id){
		int count = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select count(*) from bill_polling where group_member=? and polling=? and group_id=?");
			ps.setString(1, group_member);
			ps.setBoolean(2, false);
			ps.setString(3, group_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static boolean getBillPolling(String bill_id,String group_member){
		boolean status = true;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select polling from bill_polling where bill_id=? and group_member=?");
			ps.setString(1, bill_id);
			ps.setString(2, group_member);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				status = rs.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int getMemberCount(String bill_id, boolean poll){
		int count = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select count(*) from bill_polling where bill_id=? and polling=?");
			ps.setString(1, bill_id);
			ps.setBoolean(2, poll);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static int getMemberCount(String bill_id){
		int count = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select count(*) from bill_polling where bill_id=?");
			ps.setString(1, bill_id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static int delete(String bill_id,String group_member){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("delete from bill_polling where bill_id=? and group_member=?");
			ps.setString(1, bill_id);
			ps.setString(2, group_member);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int delete(String bill_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("delete from bill_polling where bill_id=?");
			ps.setString(1, bill_id);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
}
