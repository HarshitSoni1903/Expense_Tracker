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

import my.beans.MemberCountBean;
import my.db.GroupMemberDB;

/**
 * Servlet implementation class GetMembersCount
 */
@WebServlet("/GetMembersCount")
public class GetMembersCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMembersCount() {
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
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			if(br != null)
				str = br.readLine();
			JSONObject job = new JSONObject(str);
			String group_id = job.getString("group_id");
			int active_members = GroupMemberDB.getMembersCount(group_id, true);
			int pending_members = GroupMemberDB.getMembersCount(group_id, false);
			MemberCountBean bean = new MemberCountBean();
			bean.setActiveMembers(active_members);
			bean.setPendingMembers(pending_members);
			String json = new Gson().toJson(bean);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
