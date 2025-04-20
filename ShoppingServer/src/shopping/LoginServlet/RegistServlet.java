package shopping.LoginServlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegistServlet extends HttpServlet{

	private static final long serialVersionUID = 314719472293387358L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("GBK");
		
		String zhanghao = request.getParameter("zhanghao");
		String mima = request.getParameter("mima");
		String realName = request.getParameter("realName");
		String Sex = request.getParameter("Sex");
		String Email = request.getParameter("Email");
		String Id = request.getParameter("Id");
		
		kConnDB conn = new kConnDB();
		String sql = "insert into Admin values('"+zhanghao+"','"+mima+"','"+realName+"','"+Sex+"','"+Email+"','"+Id+"','','')";
		
		int i = conn.doUpdate(sql);
		
		if(i!=0){
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			String result = "registsuccess";
			dos.writeUTF(result);
			dos.close();
			conn.closeConnection();
		}
		else{
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			String result = "registdefault";
			dos.writeUTF(result);
			dos.close();
			conn.closeConnection();
		}
	}
	
}