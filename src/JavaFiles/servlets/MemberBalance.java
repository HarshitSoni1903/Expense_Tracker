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

import com.google.gson.Gson;

import my.db.BillDB;

/**
 * Servlet implementation class MemberBalance
 */
@WebServlet("/MemberBalance")
public class MemberBalance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberBalance() {
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
			JSONObject in = new JSONObject(str);
			JSONObject out = new JSONObject();
			String group_member = in.getString("group_member");
			group_member = (group_member.substring(1, group_member.length()-1));
			float totalincome = BillDB.getMemberIncome("personal_income", group_member);
			float thismonthincome = BillDB.getMemberIncomeThisMonth("personal_income", group_member);
			float incomebeforemonth = totalincome-thismonthincome;
			float totalexpense = BillDB.getMemberExpense(group_member);
			float thismonthexpense = BillDB.getMemberExpenseThisMonth(group_member);
			float expensebeforemonth = totalexpense-thismonthexpense;
			float previousbalance = incomebeforemonth-expensebeforemonth;
			float balance = totalincome-totalexpense;
			out.put("thismonthincome", thismonthincome);
			out.put("thismonthexpense", thismonthexpense);
			out.put("previousbalance", previousbalance);
			out.put("balance", balance);
			String json = new Gson().toJson(out);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
