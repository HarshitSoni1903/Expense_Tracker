package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import my.beans.GroupHeadBean;
import my.db.GroupHeadDB;
import my.java.GenID;

/**
 * Servlet implementation class GroupRequest
 */
@WebServlet("/CreateGroup")
public class CreateGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGroup() {
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
			String group_name = job.getString("group_name");
			String group_head = job.getString("userid");
			group_head = (group_head.substring(1, group_head.length()-1));
			if(GroupHeadDB.check(group_name, group_head)){
				response.getWriter().write("You have already created group with this name!");
			}
			else{
				String group_id = GenID.getID();
				GroupHeadBean bean = new GroupHeadBean(group_name,group_id,group_head,false);
				status = GroupHeadDB.save(bean);
				if(status != 0)
					response.getWriter().write("Request sent successfully!");
				else
					response.getWriter().write("Server error! Please try again later.");
			}
		} catch(SQLIntegrityConstraintViolationException ex){
			doPost(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
