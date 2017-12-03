package my.beans;

import java.sql.Date;

public class BillBean {
	private String bill_id,group_id,group_member,description;
	private Date date_added;
	private int amount,month_added,year_added;
	private boolean accepted;
	
	public BillBean(){}
	
	public BillBean(String bill_id,String group_id,String group_member,String description,Date date_added,int amount,int month_added,int year_added,boolean accepted){
		this.bill_id = bill_id;
		this.group_id = group_id;
		this.group_member = group_member;
		this.description = description;
		this.date_added = date_added;
		this.amount = amount;
		this.month_added = month_added;
		this.year_added = year_added;
		this.accepted = accepted;
	}
	
	public BillBean(String bill_id,Date date_added,int month_added,int year_added,boolean accepted){
		this.bill_id = bill_id;
		this.date_added = date_added;
		this.month_added = month_added;
		this.year_added = year_added;
		this.accepted = accepted;
	}
	
	public BillBean(String bill_id,String group_id,String group_member,String description,int amount,boolean accepted){
		this.bill_id = bill_id;
		this.group_id = group_id;
		this.group_member = group_member;
		this.description = description;
		this.amount = amount;
		this.accepted = accepted;
	}
	
	public String getBillId(){
		return bill_id;
	}
	
	public void setBillId(String bill_id){
		this.bill_id = bill_id;
	}
	
	public String getGroupId(){
		return group_id;
	}
	
	public void setGroupId(String group_id){
		this.group_id = group_id;
	}
	
	public String getGroupMember(){
		return group_member;
	}
	
	public void setGroupMember(String group_member){
		this.group_member = group_member;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public Date getDateAdded(){
		return date_added;
	}
	
	public void setDateAdded(Date date_added){
		this.date_added = date_added;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getMonthAdded(){
		return month_added;
	}
	
	public void setMonthAdded(int month_added){
		this.month_added = month_added;
	}
	
	public int getYearAdded(){
		return year_added;
	}
	
	public void setYearAdded(int year_added){
		this.year_added = year_added;
	}
	
	public boolean getAccepted(){
		return accepted;
	}
	
	public void setAccepted(boolean accepted){
		this.accepted = accepted;
	}
}