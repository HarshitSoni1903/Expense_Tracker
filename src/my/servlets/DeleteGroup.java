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

import my.db.GroupHeadDB;

/**
 * Servlet implementation class DeleteGroup
 */
@WebServlet("/DeleteGroup")
public class DeleteGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGroup() {
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
			status = GroupHeadDB.delete(group_id);
			if(status != 0)
				response.getWriter().write("deleted");
			else
				response.getWriter().write("not deleted");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
