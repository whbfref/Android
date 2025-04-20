package shopping.putIncar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.LoginServlet.kConnDB;

public class shopping_catSearch extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6195392843092498304L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取传递过来的username
		String username = request.getParameter("username");
		//建立连接
		kConnDB conn = new kConnDB();
		//创建sql语句,通过username获取服装相关信息
		String sql = "select Clothes.ClothesId ,ClothesName, ClothesPrice ,ClothesSave, ClothesUrl ,ClothesBuy from Clothes,ShoppingCar where Clothes.ClothesId=ShoppingCar.ClothesId and username='"+username+"'";
		//查询
		ResultSet rs = conn.doQuery(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ClothesId", rs.getString("ClothesId"));
				map.put("ClothesName", rs.getString("ClothesName"));
				map.put("ClothesSave", rs.getString("ClothesSave"));
				map.put("ClothesUrl", rs.getString("ClothesUrl"));
				map.put("ClothesPrice", rs.getString("ClothesPrice"));	
				map.put("ClothesBuy", rs.getString("ClothesBuy"));
				list.add(map);
			}
				//创建数据输出流对象
				DataOutputStream dos = new DataOutputStream(response.getOutputStream());
				//创建传递对象输出流对象
				ObjectOutputStream oos = new ObjectOutputStream(dos);
				oos.writeObject(list);
				oos.close();
				conn.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
