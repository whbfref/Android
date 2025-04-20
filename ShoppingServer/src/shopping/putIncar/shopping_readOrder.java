package shopping.putIncar;

import java.io.IOException;
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

public class shopping_readOrder extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4789710557754423991L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		
		kConnDB conn = new kConnDB();
		
		String sql = "select Clothes.ClothesId, ClothesName ,ClothesPrice ,ClothesUrl, orderId, ClothesBuy  from orderGoods ,Clothes where orderGoods.ClothesId = Clothes.ClothesId and orderGoods.username='"+username+"'";
		
		ResultSet rs = conn.doQuery(sql);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
					map.put("ClothesId", rs.getString("ClothesId"));
					map.put("ClothesName", rs.getString("ClothesName"));
					map.put("ClothesUrl", rs.getString("ClothesUrl"));
					map.put("orderId", rs.getString("orderId"));
					map.put("ClothesPrice", rs.getString("ClothesPrice"));	
					map.put("ClothesBuy", rs.getString("ClothesBuy"));
				list.add(map);
			}
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(list);
			oos.close();		
			conn.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
