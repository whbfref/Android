/**
 * 
 */
/**
 * @author 凯
 *
 */
package shopping.putIncar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.LoginServlet.kConnDB;

public class shopping_car extends HttpServlet{

	private	kConnDB conn = null;
	private ResultSet rs = null;
	private String username = "";
	private String shirt_id = "";
	private String result = "";
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		username = request.getParameter("username");
		shirt_id = request.getParameter("shirt_id");
			
		conn = new kConnDB();
		String sql = "select * from ShoppingCar";
		rs = conn.doQuery(sql);
		
		result = chaxun();
			
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());
		dos.writeUTF(result);
		dos.close();
	}
	
	public String chaxun(){
			try {
				while(rs.next()){
					String aa = rs.getString("username");
					String bb = rs.getString("ClothesId");
					
					if(aa.equals(username)&& bb.equals(shirt_id)){
						conn.closeConnection();
						return "物品存在";
					}
				}	
				//若物品不存在
				String sql = "insert into ShoppingCar values('"+username+"','"+shirt_id+"','1')";
				conn = new kConnDB();
				int i = conn.doUpdate(sql);
				if(i>0){
					conn.closeConnection();
					return "加入成功";
				}
				else{
					conn.closeConnection();
					return "加入失败";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
		
	}
	
}