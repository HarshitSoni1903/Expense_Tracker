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
 * Servlet implementation class GetGroupHead
 */
@WebServlet("/GetGroupHead")
public class GetGroupHead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGroupHead() {
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
			String group_head = GroupHeadDB.getGroupHead(group_id);
			response.getWriter().write(group_head);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
