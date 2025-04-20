package zJSPServlet;

import java.sql.ResultSet;

import shopping.LoginServlet.kConnDB;

public class AdminBean{
	private String username;
	private String password;
	private String fanw1;
	private String fanw2;
	
	public AdminBean(){}
	
	public AdminBean(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setFanw1(String fanwei1){
		this.fanw1 = fanwei1;
	}
	public String getFanw1(){
		return fanw1;
	}
	
	public void setFanw2(String fanwei2){
		this.fanw2 = fanwei2;
	}
	public String getFanw2(){
		return fanw2;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	
	public ResultSet query(String sql){
		
		kConnDB connDB = new kConnDB();
		ResultSet rs = connDB.doQuery(sql);
		return rs;
	}
	
	public void update(String sql){
		
		kConnDB connDB = new kConnDB();
		connDB.doUpdate(sql);
		connDB.closeConnection();
	}
	
	public boolean doexe(String sql){
		boolean b = false;
		kConnDB connDB = new kConnDB();
		b = connDB.doexe(sql);
		return b;
	}
}