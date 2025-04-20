package shopping.LoginServlet;

import java.sql.*;

public class kConnDB{
	
	public Connection conn=null;
	public Statement stmt=null;
	public ResultSet rs=null;
	
	private static String dbDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String dbUrl="jdbc:sqlserver://localhost:1433;DatabaseName=ShoppingClothes";
	private static String dbUser="sa";
	private static String dbPwd="123456";
	
	//�������ݿ�
	public static Connection getConnection(){
		
		Connection conn = null;
		
		try{
			//ע������
			Class.forName(dbDriver);
			//ͨ���˺������������ݿ�
			conn=DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(conn==null){
			System.err.println("���ݿ�����ʧ��!");
		}
		return conn;
	}
	
	//ִ�����
	public boolean doexe(String sql){
		
		boolean b = false;
		try
		{	
			//�������ݿ�
			conn=kConnDB.getConnection();
			//�������
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//ִ�����
			b=stmt.execute(sql);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return b;
	}
	
	
	//ִ�в�ѯ
	public ResultSet doQuery(String sql)
	{
		try
		{
			//�������ݿ�
			conn=kConnDB.getConnection();
			//�������
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//ִ�����
			rs=stmt.executeQuery(sql);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	//ִ����ɾ��
	public int doUpdate(String sql)
	{
		int result=0;
		try
		{
			conn=kConnDB.getConnection();
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			result=stmt.executeUpdate(sql);
		}
		
		catch(SQLException e)
		{
			result=0;
			e.printStackTrace();
		}
		return result;
	}
	
	// �ر����ݿ�
	public void closeConnection() {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
	