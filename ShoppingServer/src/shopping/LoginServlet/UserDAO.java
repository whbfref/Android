package shopping.LoginServlet;
import java.sql.ResultSet;

public class UserDAO {

	public static int isLogin(String username, String password) {

		try {
			kConnDB conn = new kConnDB();
			String sql = "select * from Admin";
			ResultSet rs = conn.doQuery(sql);
			
			while (rs.next()) {
				//获取账号
				String aa = rs.getString("username");
				//获取密码
				String bb = rs.getString("password");
				if (username.equals(aa)) {
					// 输出检查,登陆成功
					if (password.equals(bb)) {
						conn.closeConnection();
						return 1;
					} 
					//密码错误
					else {
						conn.closeConnection();
						return 2;
					}
				}
				//用户名不存在
				if (aa.equals("None")) {
					conn.closeConnection();
					return 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
