package my.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.beans.BillBean;

public class BillDB {
	public static int save(BillBean bean) throws SQLIntegrityConstraintViolationException{
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into bill_detail(bill_id,group_id,group_member,description,amount,accepted) values(?,?,?,?,?,?)");
			ps.setString(1, bean.getBillId());
			ps.setString(2, bean.getGroupId());
			ps.setString(3, bean.getGroupMember());
			ps.setString(4, bean.getDescription());
			ps.setInt(5, bean.getAmount());
			ps.setBoolean(6, bean.getAccepted());
			status = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException ex){
			throw ex;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int savePersonal(BillBean bean) throws SQLIntegrityConstraintViolationException{
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("insert into bill_detail values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, bean.getGroupMember());
			ps.setString(2, bean.getGroupId());
			ps.setString(3, bean.getDescription());
			ps.setInt(4, bean.getAmount());
			ps.setBoolean(5, bean.getAccepted());
			ps.setString(6, bean.getBillId());
			ps.setDate(7, bean.getDateAdded());
			ps.setInt(8, bean.getMonthAdded());
			ps.setInt(9, bean.getYearAdded());
			status = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException ex){
			throw ex;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<String> getBillId(String group_id,boolean accepted){
		List<String> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select bill_id from bill_detail where group_id=? and accepted=?");
			ps.setString(1, group_id);
			ps.setBoolean(2, accepted);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				list.add(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static BillBean getBill(String bill_id){
		BillBean bean = new BillBean();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from bill_detail where bill_id=?");
			ps.setString(1, bill_id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			bean.setBillId(bill_id);
			bean.setGroupId(rs.getString(2));
			bean.setGroupMember(rs.getString(1));
			bean.setDescription(rs.getString(3));
			bean.setAmount(rs.getInt(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public static List<BillBean> getAllBill(String group_id,Date start_date){
		List<BillBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from bill_detail where group_id=? and accepted=? and date_added>=? order by date_added desc");
			ps.setString(1, group_id);
			ps.setBoolean(2, true);
			ps.setDate(3, start_date);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BillBean bean = new BillBean();
				bean.setGroupMember(rs.getString(1));
				bean.setGroupId(group_id);
				bean.setDescription(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setAccepted(rs.getBoolean(5));
				bean.setBillId(rs.getString(6));
				bean.setDateAdded(rs.getDate(7));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int update(BillBean bean){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("update bill_detail set date_added=?, accepted=?, month_added=?, year_added=? where bill_id=?");
			ps.setDate(1, bean.getDateAdded());
			ps.setBoolean(2, bean.getAccepted());
			ps.setInt(3, bean.getMonthAdded());
			ps.setInt(4, bean.getYearAdded());
			ps.setString(5, bean.getBillId());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int delete(String bill_id){
		int status = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("delete from bill_detail where bill_id=?");
			ps.setString(1, bill_id);
			status = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int getMonthGroupExpense(String group_id){
		int group_expense = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_id=? and month_added=? and year_added=? and accepted=?");
			ps.setString(1, group_id);
			ps.setInt(2, month);
			ps.setInt(3, year);
			ps.setBoolean(4, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				group_expense = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return group_expense;
	}
	
	public static int getTotalGroupExpenseBeforeMonth(String group_id){
		int group_expense = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_id=? and month_added<=? and year_added=? and accepted=?");
			ps.setString(1, group_id);
			ps.setInt(2, month);
			ps.setInt(3, year);
			ps.setBoolean(4, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				group_expense = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return group_expense;
	}
	
	public static int getMemberPaidMonth(String group_id,String group_member){
		int memberpaidthismonth = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_id=? and group_member=? and month_added=? and year_added=? and accepted=?");
			ps.setString(1, group_id);
			ps.setString(2, group_member);
			ps.setInt(3, month);
			ps.setInt(4, year);
			ps.setBoolean(5, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				memberpaidthismonth = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return memberpaidthismonth;
	}
	
	public static int getMemberPaidBeforeMonth(String group_id,String group_member){
		int memberpaidthismonth = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_id=? and group_member=? and month_added<=? and year_added=? and accepted=?");
			ps.setString(1, group_id);
			ps.setString(2, group_member);
			ps.setInt(3, month);
			ps.setInt(4, year);
			ps.setBoolean(5, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				memberpaidthismonth = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return memberpaidthismonth;
	}
	
	public static int getMemberIncome(String group_id,String group_member){
		int memberincome = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_id=? and group_member=? and accepted=?");
			ps.setString(1, group_id);
			ps.setString(2, group_member);
			ps.setBoolean(3, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				memberincome = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return memberincome;
	}
	
	public static List<BillBean> getMemberIncomeList(String group_id,String group_member,Date start_date){
		List<BillBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from bill_detail where group_id=? and group_member=? and date_added>=? and accepted=? order by date_added desc");
			ps.setString(1, group_id);
			ps.setString(2, group_member);
			ps.setDate(3, start_date);
			ps.setBoolean(4, true);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BillBean bean = new BillBean();
				bean.setBillId(rs.getString(6));
				bean.setDateAdded(rs.getDate(7));
				bean.setDescription(rs.getString(3));
				bean.setGroupId(group_id);
				bean.setAmount(rs.getInt(4));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<BillBean> getMemberExpenseList(String group_member,Date start_date){
		List<BillBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from bill_detail where not group_id=? and group_member=? and date_added>=? and accepted=? order by date_added desc");
			ps.setString(1, "personal_income");
			ps.setString(2, group_member);
			ps.setDate(3, start_date);
			ps.setBoolean(4, true);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BillBean bean = new BillBean();
				bean.setBillId(rs.getString(6));
				bean.setDateAdded(rs.getDate(7));
				bean.setDescription(rs.getString(3));
				bean.setGroupId(rs.getString(2));
				bean.setAmount(rs.getInt(4));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int getMemberIncomeThisMonth(String group_id,String group_member){
		int memberincomethismonth = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_id=? and group_member=? and month_added=? and year_added=? and accepted=?");
			ps.setString(1, group_id);
			ps.setString(2, group_member);
			ps.setInt(3, month);
			ps.setInt(4, year);
			ps.setBoolean(5, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				memberincomethismonth = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return memberincomethismonth;
	}
	
	public static int getMemberExpense(String group_member){
		int memberexpense = 0;
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_member=? and not group_id=? and accepted=?");
			ps.setString(1, group_member);
			ps.setString(2, "personal_income");
			ps.setBoolean(3, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				memberexpense = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return memberexpense;
	}
	
	public static int getMemberExpenseThisMonth(String group_member){
		int memberexpensethismonth = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select sum(amount) from bill_detail where group_member=? and month_added=? and year_added=? and not group_id=? and accepted=?");
			ps.setString(1, group_member);
			ps.setInt(2, month);
			ps.setInt(3, year);
			ps.setString(4, "personal_income");
			ps.setBoolean(5, true);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				memberexpensethismonth = rs.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return memberexpensethismonth;
	}
}