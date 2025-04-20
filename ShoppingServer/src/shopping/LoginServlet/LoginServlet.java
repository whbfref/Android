/**
 * 
 */
/**
 * @author 凯
 *
 */
package shopping.LoginServlet;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 314719472293387358L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//登陆成功的返回reslut
		//request.setCharacterEncoding("UTF_8");--GBK
		
		String result = "";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		int flag = UserDAO.isLogin(username, password);
		
		try{
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			
			if(flag==1){
				result = "success";
				dos.writeUTF(result);
				dos.close();		
			}
			else{
				result = "fault";
				dos.writeUTF(result);
				dos.close();		
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}