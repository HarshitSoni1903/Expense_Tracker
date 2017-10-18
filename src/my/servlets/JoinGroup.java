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

import my.beans.GroupMemberBean;
import my.db.GroupHeadDB;
import my.db.GroupMemberDB;

/**
 * Servlet implementation class JoinGroup
 */
@WebServlet("/JoinGroup")
public class JoinGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinGroup() {
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
			String group_member = job.getString("userid");
			group_member = (group_member.substring(1, group_member.length()-1));
			if(GroupHeadDB.check(group_id,true)){
				if(GroupMemberDB.check(group_id,group_member)){
					response.getWriter().write("Request already sent !");
				}
				else{
					String group_name = GroupHeadDB.getGroupName(group_id);
					GroupMemberBean bean = new GroupMemberBean(group_name,group_id,group_member,false);
					status = GroupMemberDB.save(bean);
					if(status != 0){
						response.getWriter().write("Request Sent successfully!");
					}
					else{
						response.getWriter().write("Server Error! Please try again later.");
					}
				}
			}
			else{
				response.getWriter().write("Invalid group ID!");
			}
		}catch(Exception e){
				e.printStackTrace();
		}
	}

}