package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import my.beans.GroupMemberBean;

public class GroupMemberDB {
	public static int save(GroupMemberBean bean){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into group_member(group_name,group_id,group_member,active) values(?,?,?,?)");
			ps.setString(1, bean.getGroupName());
			ps.setString(2, bean.getGroupId());
			ps.setString(3, bean.getGroupMember());
			ps.setBoolean(4, bean.getActive());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean check(String group_id,String group_member){
		boolean status = false;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_member where group_id=? and group_member=?");
			ps.setString(1, group_id);
			ps.setString(2, group_member);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int addGroupHead(String group_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_head where group_id=?");
			ps.setString(1, group_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				GroupMemberBean bean = new GroupMemberBean();
				bean.setGroupName(rs.getString(1));
				bean.setGroupId(group_id);
				bean.setGroupMember(rs.getString(3));
				bean.setActive(true);
				status = save(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<GroupMemberBean> getMembers(String group_id){
		List<GroupMemberBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_member where group_id=?");
			ps.setString(1, group_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				GroupMemberBean bean = new GroupMemberBean();
				bean.setGroupName(rs.getString(1));
				bean.setGroupId(rs.getString(2));
				bean.setGroupMember(rs.getString(3));
				bean.setActive(rs.getBoolean(4));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int delete(String group_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("delete from group_member where group_id=?");
			ps.setString(1, group_id);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int removeMemberGroup(String group_member,String group_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("delete from group_member where group_member=? and group_id=?");
			ps.setString(1, group_member);
			ps.setString(2, group_id);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<GroupMemberBean> getGroups(String userid){
		List<GroupMemberBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from group_member where group_member=? and active=?");
			ps.setString(1, userid);
			ps.setBoolean(2, true);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				GroupMemberBean bean = new GroupMemberBean();
				bean.setGroupName(rs.getString(1));
				bean.setGroupId(rs.getString(2));
				bean.setGroupMember(userid);
				bean.setActive(true);
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int getMembersCount(String group_id,boolean active){
		int count = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select count(*) from group_member where group_id=? and active=?");
			ps.setString(1, group_id);
			ps.setBoolean(2, active);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static int update(String group_member,String group_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("update group_member set active=? where group_id=? and group_member=?");
			ps.setBoolean(1, true);
			ps.setString(2, group_id);
			ps.setString(3, group_member);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
}
