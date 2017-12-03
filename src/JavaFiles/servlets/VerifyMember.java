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

import my.db.GroupMemberDB;

/**
 * Servlet implementation class VerifyMember
 */
@WebServlet("/VerifyMember")
public class VerifyMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyMember() {
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
		int status = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = null;
		if(br != null)
			str = br.readLine();
		try {
			JSONObject job = new JSONObject(str);
			String group_id = job.getString("group_id");
			String group_member = job.getString("user_id");
			String action = job.getString("action");
			if(action.equals("n")){
				status = GroupMemberDB.removeMemberGroup(group_member, group_id);
				if(status != 0)
					response.getWriter().write("Success");
				else
					response.getWriter().write("not success");
			}
			else if(action.equals("y")){
				status = GroupMemberDB.update(group_member, group_id);
				if(status != 0)
					response.getWriter().write("Success");
				else
					response.getWriter().write("not success");
			}
			else
				response.getWriter().write("not success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
