package shopping.LoginServlet;
import java.sql.ResultSet;

public class UserDAO {

	public static int isLogin(String username, String password) {

		try {
			kConnDB conn = new kConnDB();
			String sql = "select * from Admin";
			ResultSet rs = conn.doQuery(sql);
			
			while (rs.next()) {
				//��ȡ�˺�
				String aa = rs.getString("username");
				//��ȡ����
				String bb = rs.getString("password");
				if (username.equals(aa)) {
					// ������,��½�ɹ�
					if (password.equals(bb)) {
						conn.closeConnection();
						return 1;
					} 
					//�������
					else {
						conn.closeConnection();
						return 2;
					}
				}
				//�û���������
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
