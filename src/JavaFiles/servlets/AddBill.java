package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import my.beans.BillBean;
import my.beans.BillPollingBean;
import my.beans.GroupMemberBean;
import my.db.BillDB;
import my.db.BillPollingDB;
import my.db.GroupMemberDB;
import my.java.GenID;

/**
 * Servlet implementation class AddBill
 */
@WebServlet("/AddBill")
public class AddBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBill() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = null;
		int status = 0;
		if(br != null)
			str = br.readLine();
		Calendar cal = Calendar.getInstance();
		try{
			JSONObject job = new JSONObject(str);
			String group_id = job.getString("group_id");
			String group_member = job.getString("user_id");
			group_member = (group_member.substring(1, group_member.length()-1));
			int amount = job.getInt("bill_amount");
			String description = job.getString("bill_description");
			String bill_id = GenID.getID();
			if(group_id.equals("personal_income") || group_id.equals("personal_expense")){
				BillBean bean = new BillBean(bill_id,group_id,group_member,description,new java.sql.Date(new java.util.Date().getTime()),amount,cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),true);
				status = BillDB.savePersonal(bean);
				if(status != 0)
					response.getWriter().write("success");
				else
					response.getWriter().write("not success");
			}
			else{
				BillBean billbean = new BillBean(bill_id,group_id,group_member,description,amount,false);
				status = BillDB.save(billbean);
				List<GroupMemberBean> members = GroupMemberDB.getMembers(group_id);
				for(GroupMemberBean bean : members){
					BillPollingBean pollingbean = new BillPollingBean(bill_id,bean.getGroupMember(),group_id,false);
					status = BillPollingDB.save(pollingbean);
				}
				status = BillPollingDB.update(group_member, bill_id);
				if(status != 0)
					response.getWriter().write("success");
				else
					response.getWriter().write("not success");
			}
		} catch (SQLIntegrityConstraintViolationException ex){
			doPost(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
