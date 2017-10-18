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
 * Servlet implementation class GetGrpDetailsAdmin
 */
@WebServlet("/GetGrpDetails")
public class GetGrpDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGrpDetails() {
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
			List<GroupMemberBean> bean = GroupMemberDB.getMembers(group_id);
			String json = new Gson().toJson(bean);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
