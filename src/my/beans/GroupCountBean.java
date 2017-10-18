package my.beans;

public class GroupCountBean {
	private int active_groups,pending_groups;
	
	public int getActiveGroups(){
		return active_groups;
	}
	
	public void setActiveGroups(int active_groups){
		this.active_groups = active_groups;
	}
	
	public int getPendingGroups(){
		return pending_groups;
	}
	
	public void setPendingGroups(int pending_groups){
		this.pending_groups = pending_groups;
	}
}
