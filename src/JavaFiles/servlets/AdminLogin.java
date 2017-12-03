package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import my.db.DBCon;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
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
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String str = null;
			if(br != null)
				str = br.readLine();
			JSONObject job = new JSONObject(str);
			String userid = job.getString("email");
			String password = job.getString("password");
			try(Connection con = DBCon.getCon()){
				PreparedStatement ps = con.prepareStatement("select * from admin where userid=? and password=?");
				ps.setString(1, userid);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					Cookie ck = new Cookie("admin","admin");
					response.addCookie(ck);
					response.getWriter().write("verified");
				}
				else
					response.getWriter().write("fail");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
