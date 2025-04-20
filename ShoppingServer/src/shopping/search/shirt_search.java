/**
 * 
 */
/**
 * @author ¿­
 *
 */
package shopping.search;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import shopping.LoginServlet.kConnDB;

public class shirt_search extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5056061823867652700L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		kConnDB conn = new kConnDB();
		String sql = "select ClothesId,ClothesName,ClothesSave,ClothesUrl,ClothesPrice from Clothes where ClothesId like '0%'";
		
		ResultSet rs = conn.doQuery(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		try {
			while(rs.next()){
				Map<String,Object> map =new HashMap<String,Object>();
				map.put("ClothesId", rs.getString("ClothesId"));
				map.put("ClothesName", rs.getString("ClothesName"));
				map.put("ClothesSave", rs.getString("ClothesSave"));
				map.put("ClothesUrl", rs.getString("ClothesUrl"));
				map.put("ClothesPrice", rs.getString("ClothesPrice"));	
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