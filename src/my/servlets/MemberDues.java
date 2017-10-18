package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import my.db.BillDB;
import my.db.GroupMemberDB;

/**
 * Servlet implementation class MemberDues
 */
@WebServlet("/MemberDues")
public class MemberDues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDues() {
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
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		if(br != null)
			str = br.readLine();
		try{
			JSONObject in = new JSONObject(str);
			JSONObject out = new JSONObject();
			String group_id = in.getString("group_id");
			String group_member = in.getString("group_member");
			group_member = (group_member.substring(1, group_member.length()-1));
			float totalgroupexpensebeforemonth = BillDB.getTotalGroupExpenseBeforeMonth(group_id);
			float groupmembercount = GroupMemberDB.getMembersCount(group_id, true);
			float groupexpensethismonth = BillDB.getMonthGroupExpense(group_id);
			float perperson = groupexpensethismonth/groupmembercount;
			float paidthismonth = BillDB.getMemberPaidMonth(group_id, group_member);
			float paidbeforemonth = BillDB.getMemberPaidBeforeMonth(group_id, group_member);
			float previousbalance = totalgroupexpensebeforemonth-paidbeforemonth;
			float due = (perperson-paidthismonth)+previousbalance;
			out.put("group_expense", groupexpensethismonth);
			out.put("perperson", perperson);
			out.put("paid", paidthismonth);
			out.put("previuosbalance", previousbalance);
			out.put("due", due);
			String json = new Gson().toJson(out);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
