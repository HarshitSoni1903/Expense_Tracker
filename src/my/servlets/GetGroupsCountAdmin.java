package my.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import my.beans.GroupCountBean;
import my.db.GroupHeadDB;

/**
 * Servlet implementation class GetGroupsAdmin
 */
@WebServlet("/GetGroupsCountAdmin")
public class GetGroupsCountAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGroupsCountAdmin() {
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
		int active_groups = GroupHeadDB.getGroupsCount(true);
		int pending_groups = GroupHeadDB.getGroupsCount(false);
		GroupCountBean bean = new GroupCountBean();
		bean.setActiveGroups(active_groups);
		bean.setPendingGroups(pending_groups);
		String json = new Gson().toJson(bean);
		response.getWriter().write(json);
	}

}
