package my.beans;

public class BillPollingBean {
	private String bill_id,group_member,group_id;
	private boolean poll;
	
	public BillPollingBean(){}
	
	public BillPollingBean(String bill_id,String group_member,String group_id,boolean poll){
		this.bill_id = bill_id;
		this.group_member = group_member;
		this.poll = poll;
		this.group_id = group_id;
	}
	
	public String getGroupId(){
		return group_id;
	}
	
	public void setGroupId(String group_id){
		this.group_id = group_id;
	}
	
	public String getBillId(){
		return bill_id;
	}
	
	public void setBillId(String bill_id){
		this.bill_id = bill_id;
	}
	
	public String getGroupMember(){
		return group_member;
	}
	
	public void setGroupMember(String group_member){
		this.group_member = group_member;
	}
	
	public boolean getPoll(){
		return poll;
	}
	
	public void setPoll(boolean poll){
		this.poll = poll;
	}
}
