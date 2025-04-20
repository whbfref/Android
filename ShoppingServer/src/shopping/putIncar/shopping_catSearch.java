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
		
		//��ȡ���ݹ�����username
		String username = request.getParameter("username");
		//��������
		kConnDB conn = new kConnDB();
		//����sql���,ͨ��username��ȡ��װ�����Ϣ
		String sql = "select Clothes.ClothesId ,ClothesName, ClothesPrice ,ClothesSave, ClothesUrl ,ClothesBuy from Clothes,ShoppingCar where Clothes.ClothesId=ShoppingCar.ClothesId and username='"+username+"'";
		//��ѯ
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
				//�����������������
				DataOutputStream dos = new DataOutputStream(response.getOutputStream());
				//�������ݶ������������
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
