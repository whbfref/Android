/**
 * 
 */
/**
 * @author 凯
 *
 */
package infomation;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.LoginServlet.kConnDB;

public class getInfo extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4042216853120943748L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		//获取信息
		if(action.equals("get")){
			String username = request.getParameter("username");
			
			kConnDB conn = new kConnDB();			
			String sql = "select * from Admin where username='"+username+"'";	
			ResultSet rs = conn.doQuery(sql);
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				while(rs.next()){
					map.put("name",rs.getString("name"));
					map.put("sex",rs.getString("sex"));
					map.put("email",rs.getString("email"));
					map.put("IdCard",rs.getString("IdCard"));
					map.put("tel",rs.getString("tel"));
					map.put("adress",rs.getString("adress"));
				}
				
				ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
				oos.writeObject(map);
				oos.close();
				conn.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//设置信息
		else if(action.equals("set")){
			request.setCharacterEncoding("GBK");
			String setAction = request.getParameter("setAction");
			String value = request.getParameter("value");
			String username = request.getParameter("username");
			
			kConnDB conn = new kConnDB();
			//设置姓名
			if(setAction.equals("setName")){
				String sql = "update Admin set name='"+value+"' where username='"+username+"'";
				if(conn.doUpdate(sql) != 0){
					DataOutputStream dos = new DataOutputStream(response.getOutputStream());
					dos.writeUTF("success");
					dos.close();
					conn.closeConnection();
				}
			}
			//设置邮箱
			else if(setAction.equals("setemail")){
				String sql = "update Admin set email='"+value+"' where username='"+username+"'";
				if(conn.doUpdate(sql) != 0){
					DataOutputStream dos = new DataOutputStream(response.getOutputStream());
					dos.writeUTF("success");
					dos.close();
					conn.closeConnection();
				}
			}
			//设置电话
			else if(setAction.equals("settel")){
				String sql = "update Admin set tel='"+value+"' where username='"+username+"'";
				if(conn.doUpdate(sql) != 0){
					DataOutputStream dos = new DataOutputStream(response.getOutputStream());
					dos.writeUTF("success");
					dos.close();
					conn.closeConnection();
				}
			}
		}
	}
}