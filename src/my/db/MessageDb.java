package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import my.beans.MessageBean;

public class MessageDb {
	public static List<MessageBean> getMessage(){
		List<MessageBean> list = new ArrayList<>();
		try(Connection con = DBCon.getCon()){
			PreparedStatement ps = con.prepareStatement("select * from message_request");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MessageBean bean = new MessageBean();
				bean.setName(rs.getString(1));
				bean.setEmail(rs.getString(2));
				bean.setMessage(rs.getString(3));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
