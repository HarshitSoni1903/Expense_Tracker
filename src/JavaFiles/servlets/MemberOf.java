package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import my.beans.GroupMemberBean;
import my.db.GroupMemberDB;

/**
 * Servlet implementation class MemberOf
 */
@WebServlet("/MemberOf")
public class MemberOf extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberOf() {
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
			String group_member = job.getString("userid");
			group_member = (group_member.substring(1, group_member.length()-1));
			List<GroupMemberBean> bean = GroupMemberDB.getGroups(group_member);
			String json = new Gson().toJson(bean);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
