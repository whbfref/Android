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
	
	//连接数据库
	public static Connection getConnection(){
		
		Connection conn = null;
		
		try{
			//注册驱动
			Class.forName(dbDriver);
			//通过账号密码连接数据库
			conn=DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(conn==null){
			System.err.println("数据库连接失败!");
		}
		return conn;
	}
	
	//执行语句
	public boolean doexe(String sql){
		
		boolean b = false;
		try
		{	
			//连接数据库
			conn=kConnDB.getConnection();
			//创建语句
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//执行语句
			b=stmt.execute(sql);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return b;
	}
	
	
	//执行查询
	public ResultSet doQuery(String sql)
	{
		try
		{
			//连接数据库
			conn=kConnDB.getConnection();
			//创建语句
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//执行语句
			rs=stmt.executeQuery(sql);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	//执行增删改
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
	
	// 关闭数据库
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
	