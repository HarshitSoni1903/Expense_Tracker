package my.beans;

public class MemberCountBean {
	private int active_members,pending_members;
	
	public int getActiveMembers(){
		return active_members;
	}
	
	public void setActiveMembers(int active_members){
		this.active_members = active_members;
	}
	
	public int getPendingMembers(){
		return pending_members;
	}
	
	public void setPendingMembers(int pending_members){
		this.pending_members = pending_members;
	}
}
