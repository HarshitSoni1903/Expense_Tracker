package my.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import my.db.BillPollingDB;

/**
 * Servlet implementation class GetPendingBills
 */
@WebServlet("/GetPendingBills")
public class GetPendingBills extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPendingBills() {
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
			String group_member = job.getString("userid");
			group_member = (group_member.substring(1, group_member.length()-1));
			List<String> inactivebillid = BillDB.getBillId(group_id,false);
			List<BillBean> inactivebillpolling = new ArrayList<>();
			for(String billid : inactivebillid){
				boolean status = BillPollingDB.getBillPolling(billid, group_member);
				if(!status){
					BillBean bean = BillDB.getBill(billid);
					inactivebillpolling.add(bean);
				}
			}
			String json = new Gson().toJson(inactivebillpolling);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
