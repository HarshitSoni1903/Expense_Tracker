package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import my.beans.BillBean;
import my.db.BillDB;
import my.db.BillPollingDB;
import my.db.GroupMemberDB;

/**
 * Servlet implementation class VerifyBill
 */
@WebServlet("/VerifyBill")
public class VerifyBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyBill() {
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
		String str = null;
		int status = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		if(br != null)
			str = br.readLine();
		try{
			JSONObject job = new JSONObject(str);
			String bill_id = job.getString("bill_id");
			String group_id = job.getString("group_id");
			String group_member = job.getString("userid");
			group_member = (group_member.substring(1, group_member.length()-1));
			String poll = job.getString("poll");
			float groupmembercount = GroupMemberDB.getMembersCount(group_id, true);
			if(poll.equals("y")){
				status = BillPollingDB.update(group_member, bill_id);
				float billpollingmembercount = BillPollingDB.getMemberCount(bill_id, true);
				float criteria = billpollingmembercount/groupmembercount;
				if(criteria >= 0.5){
					Calendar cal = Calendar.getInstance();
					BillBean bean =new BillBean(bill_id,new java.sql.Date(new java.util.Date().getTime()),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),true);
					status = BillDB.update(bean);
					status = BillPollingDB.delete(bill_id);
				}
			}
			else{
				status = BillPollingDB.delete(bill_id, group_member);
				float billpollingmembercount = BillPollingDB.getMemberCount(bill_id);
				float criteria = billpollingmembercount/groupmembercount;
				if(criteria <= 0.5){
					status = BillDB.delete(bill_id);
				}
			}
			if(status != 0)
				response.getWriter().write("success");
			else
				response.getWriter().write("not success");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
