package shopping.putIncar;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.LoginServlet.kConnDB;

public class shopping_changeCart extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145037383455714873L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ClothesId = request.getParameter("ClothesId");
		String clothesBuy = request.getParameter("ClothesBuy");
		String dex = request.getParameter("dex");
		
		//�޸����ݿ�
		if(dex.equals("Change")){
			kConnDB conn = new kConnDB();
			String sql = "update ShoppingCar set ClothesBuy='"+clothesBuy+"' where ClothesId='"+ClothesId+"'";
			
			int i = conn.doUpdate(sql);
			
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			if(i>0){
				String reslut = "success";
				dos.writeUTF(reslut);
				dos.close();
				conn.closeConnection();
			}
			else{
				String reslut = "defeat";
				dos.writeUTF(reslut);
				dos.close();
				conn.closeConnection();
			}
		}
		//ɾ�����ݿ��е�����
		else if(dex.equals("delete")){
			kConnDB conn = new kConnDB();
			String sql = "delete from ShoppingCar where ClothesId='"+ClothesId+"'";
			int i = conn.doUpdate(sql);
			
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			if(i>0){
				String reslut = "success";
				dos.writeUTF(reslut);
				dos.close();
				conn.closeConnection();
			}
			else{
				String reslut = "defeat";
				dos.writeUTF(reslut);
				dos.close();
				conn.closeConnection();
			}
		}
		//ɾ�����ݿ��ж�Ӧ�����ݲ������ɶ���
		else if(dex.equals("orderBuy")){
			String orderId = request.getParameter("orderId");
			String username = request.getParameter("username");
			
			kConnDB conn = new kConnDB();
			String sqlOrder = "insert into orderGoods values('"+orderId+"','"+username+"','"+ClothesId+"','"+clothesBuy+"')";
			String sqlDelete = "delete from ShoppingCar where ClothesId='"+ClothesId+"'";
			
			int i = conn.doUpdate(sqlOrder);
			int j = conn.doUpdate(sqlDelete);
			
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			if(i>0 &&j>0){
				String sqlString1  = "update Clothes set ClothesSave=ClothesSave-"+Integer.parseInt(clothesBuy);
				String sqlString2  = "update Store set ClothesSave=ClothesSave-"+Integer.parseInt(clothesBuy);
				
				conn.doUpdate(sqlString1);
				conn.doUpdate(sqlString2);
				
				String reslut = "success";
				dos.writeUTF(reslut);
				dos.close();
				conn.closeConnection();
			}
			else{
				String reslut = "defeat";
				dos.writeUTF(reslut);
				dos.close();
				conn.closeConnection();
			}
		}		
	}
}
