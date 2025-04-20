/**
 * 
 */
/**
 * @author 凯
 *
 */
package Values.bean;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UrlValues{
	//IP地址
	public static final String IP = "10.0.2.2";
	//验证登陆
	public static final String Login_URL = "http://"+IP+":8080/ShoppingServer/LoginServlet";
	//注册
	public static final String Regist_URL = "http://"+IP+":8080/ShoppingServer/RegistServlet";
	//查询衣服的表
	public static final String ShirtSearch_URL = "http://"+IP+":8080/ShoppingServer/ShirtSearchServlet";
	//向购物车中添加
	public static final String clothes_putIncar = "http://"+IP+":8080/ShoppingServer/shopping_car";
	//访问购物车
	public static final String clothes_carSearch = "http://"+IP+":8080/ShoppingServer/shopping_catSearch";
	//修改购物车
	public static final String clothes_changeCart ="http://"+IP+":8080/ShoppingServer/shopping_changeCart";
	//访问我的订单
	public static final String shopping_readOrder = "http://"+IP+":8080/ShoppingServer/shopping_readOrder";
	//选择搜索
	public static final String search_clothes = "http://"+IP+":8080/ShoppingServer/search_clothes";
	//儿童新装搜索
	public static final String search_chilren = "http://"+IP+":8080/ShoppingServer/search_chilren";
	//getInfo
	public static final String getInfo_Url = "http://"+IP+":8080/ShoppingServer/getInfo";

	
	public static String username ;
	public static String password ;
	
	//结束Activity标识
	public static int finishState =0;
	
	public UrlValues(){
		
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String DateOrderId(){
		 Date date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssssss");
		 String s = sdf.format(date);
		 return s;
	}
}