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
 * Servlet implementation class PersonalStatement
 */
@WebServlet("/PersonalStatement")
public class PersonalStatement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonalStatement() {
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
		List<BillBean> list;
		try{
			JSONObject in = new JSONObject(str);
			String group_member = in.getString("user_id");
			group_member = (group_member.substring(1, group_member.length()-1));
			String query_type = in.getString("query_type");
			int days = in.getInt("days");
			Date start_date = DateCal.getStartDate(days);
			if(query_type.equals("personal_income"))
				list = BillDB.getMemberIncomeList("personal_income", group_member,start_date);
			else
				list = BillDB.getMemberExpenseList(group_member, start_date);
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
