package my.beans;

public class GroupMemberBean {
	private String group_name,group_id,group_member;
	private boolean active;
	
	public GroupMemberBean(){}
	
	public GroupMemberBean(String group_name,String group_id,String group_member,boolean active){
		this.group_name = group_name;
		this.group_id = group_id;
		this.group_member = group_member;
		this.active = active;
	}
	
	public String getGroupName(){
		return group_name;
	}
	
	public void setGroupName(String group_name){
		this.group_name = group_name;
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
	
	public void setGroupMember(String group_head){
		this.group_member = group_head;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
}
