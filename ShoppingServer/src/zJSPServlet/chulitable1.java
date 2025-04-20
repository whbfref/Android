/**
 * 
 */
/**
 * @author ¿­
 *
 */
package zJSPServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.LoginServlet.kConnDB;

public class chulitable1 extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8587427395952691454L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("gb2312");
		resp.setContentType("gb2312");
		
		kConnDB conn = new kConnDB();
		String huojiaid = req.getParameter("huojiaid");
		String yifuid = req.getParameter("yifuid");
		String kucunliang = req.getParameter("kucunliang");
		
		String sqlquery = "select * from Store";
		ResultSet rs = conn.doQuery(sqlquery);
		
		try {
			while(rs.next()){
				String aa = rs.getString("storeId");
				String bb = rs.getString("ClothesId");
				if(huojiaid.equals(aa)&&yifuid.equals(bb)){
					String sql = "update Store set ClothesSave='"+kucunliang+"'where storeId='"+huojiaid+"'and ClothesId='"+yifuid+"'";
					String sql1 = "update Clothes set ClothesSave='"+kucunliang+"'where ClothesId='"+yifuid+"'";
					conn.doUpdate(sql);
					conn.doUpdate(sql1);
					String url = "xiugaikucun.jsp";
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, resp);
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.closeConnection();
	}
}