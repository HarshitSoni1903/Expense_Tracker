package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import my.beans.BillBean;
import my.db.BillDB;
import my.java.DateCal;

/**
 * Servlet implementation class GroupStatement
 */
@WebServlet("/GroupStatement")
public class GroupStatement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupStatement() {
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
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		if(br != null)
			str = br.readLine();
		try{
			JSONObject job = new JSONObject(str);
			String group_id = job.getString("group_id");
			int days = job.getInt("days");
			Date start_date = DateCal.getStartDate(days);
			List<BillBean> list = BillDB.getAllBill(group_id,start_date);
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
