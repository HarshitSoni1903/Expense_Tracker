package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import my.beans.GroupHeadBean;

public class GroupHeadDB {
	public static int save(GroupHeadBean bean) throws SQLIntegrityConstraintViolationException{
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into group_head(group_name,group_id,group_head,active) values(?,?,?,?)");
			ps.setString(1, bean.getGroupName());
			ps.setString(2, bean.getGroupId());
			ps.setString(3, bean.getGroupHead());
			ps.setBoolean(4, bean.getActive());
			status = ps.executeUpdate();
		} catch(SQLIntegrityConstraintViolationException ex){
			throw ex;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean check(String group_id,boolean active){
		boolean status = false;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_head where group_id=? and active=?");
			ps.setString(1, group_id);
			ps.setBoolean(2, active);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean check(String group_name,String group_head){
		boolean status = false;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_head where group_name=? and group_head=?");
			ps.setString(1, group_name);
			ps.setString(2, group_head);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static String getGroupName(String group_id){
		String group_name = null;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select group_name from group_head where group_id=?");
			ps.setString(1, group_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				group_name = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group_name;
	}
	
	public static int getGroupsCount(boolean active){
		int count = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select count(*) from group_head where active=?");
			ps.setBoolean(1, active);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static List<GroupHeadBean> getActiveGroups(){
		List<GroupHeadBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_head where active=?");
			ps.setBoolean(1, true);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				GroupHeadBean bean = new GroupHeadBean();
				bean.setGroupName(rs.getString(1));
				bean.setGroupId(rs.getString(2));
				bean.setGroupHead(rs.getString(3));
				bean.setActive(true);
				list.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<GroupHeadBean> getPendingGroups(){
		List<GroupHeadBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_head where active=?");
			ps.setBoolean(1, false);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				GroupHeadBean bean = new GroupHeadBean();
				bean.setGroupName(rs.getString(1));
				bean.setGroupId(rs.getString(2));
				bean.setGroupHead(rs.getString(3));
				bean.setActive(false);
				list.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static int delete(String group_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("delete from group_head where group_id=?");
			ps.setString(1, group_id);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int update(String group_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("update group_head set active=? where group_id=?");
			ps.setBoolean(1, true);
			ps.setString(2, group_id);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<GroupHeadBean> getGroups(String group_head){
		List<GroupHeadBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_head where group_head=? and active=?");
			ps.setString(1, group_head);
			ps.setBoolean(2, true);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				GroupHeadBean bean = new GroupHeadBean();
				bean.setGroupName(rs.getString(1));
				bean.setGroupId(rs.getString(2));
				bean.setGroupHead(rs.getString(3));
				bean.setActive(true);
				list.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static String getGroupHead(String group_id){
		String group_head = null;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select group_head from group_head where group_id=?");
			ps.setString(1, group_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				group_head = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group_head;
	}
}
