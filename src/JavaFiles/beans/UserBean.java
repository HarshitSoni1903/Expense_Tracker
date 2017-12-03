package my.beans;

public class UserBean {
	private String name,email,password;
	private long mobile_no;
	
	public UserBean(){}
	
	public UserBean(String name,String email,String password,long mobile_no){
		this.name = name;
		this.email = email;
		this.password = password;
		this.mobile_no = mobile_no;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public long getMobileNo(){
		return mobile_no;
	}
	
	public void setMobileNo(long mobile_no){
		this.mobile_no = mobile_no;
	}
}