/**
 * 
 */
/**
 * @author 凯
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

public class jsp_loginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5445692627370933142L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("gb2312");
		PrintWriter ps = resp.getWriter();
		resp.setContentType("gb2312");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//将用户输入的信息注册到JavaBean中
		AdminBean adBean = new AdminBean(username, password); 
		
		kConnDB conn = new kConnDB();
		String sql = "select * from manager";
		ResultSet rs = conn.doQuery(sql);
		try {
			while(rs.next()){
				if(username.equals(rs.getString("adusername"))&&password.equals(rs.getString("adpass"))){
					String url = "login1-1.jsp";
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, resp);
				}
				else{
					String url = "login1-error.jsp";
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, resp);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}