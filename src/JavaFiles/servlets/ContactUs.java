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

import my.db.ContactDB;

/**
 * Servlet implementation class ContactUs
 */
@WebServlet("/ContactUs")
public class ContactUs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactUs() {
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
		int status = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		if(br != null)
			str = br.readLine();
		try{
			JSONObject job = new JSONObject(str);
			String name = job.getString("name");
			String email = job.getString("email");
			String message = job.getString("message");
			status = ContactDB.save(name, email, message);
			if(status != 0)
				response.getWriter().write("success");
			else
				response.getWriter().write("not success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
