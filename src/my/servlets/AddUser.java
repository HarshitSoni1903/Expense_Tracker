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

import my.beans.UserBean;
import my.db.UserDB;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
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
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = null;
		int status = 0;
		if(br != null)
			str = br.readLine();
		try {
			JSONObject job = new JSONObject(str);
			String name = job.getString("name");
			String email = job.getString("email");
			String password = job.getString("password");
			long mobile_no = job.getLong("number");
			UserBean bean = new UserBean(name,email,password,mobile_no);
			status = UserDB.save(bean);
			if(status != 0)
				response.getWriter().write("Success");
			else
				response.getWriter().write("Fail");
		} catch(SQLIntegrityConstraintViolationException ex){
			response.getWriter().write("available");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}