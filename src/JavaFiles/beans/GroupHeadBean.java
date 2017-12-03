package my.beans;

public class GroupHeadBean {
	private String group_name,group_id,group_head;
	private boolean active;
	
	public GroupHeadBean(){}
	
	public GroupHeadBean(String group_name,String group_id,String group_head,boolean active){
		this.group_name = group_name;
		this.group_id = group_id;
		this.group_head = group_head;
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
	
	public String getGroupHead(){
		return group_head;
	}
	
	public void setGroupHead(String group_head){
		this.group_head = group_head;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
}
