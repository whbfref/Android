/**
 * 
 */
/**
 * @author ��
 *
 */
package Values.bean;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UrlValues{
	//IP��ַ
	public static final String IP = "10.0.2.2";
	//��֤��½
	public static final String Login_URL = "http://"+IP+":8080/ShoppingServer/LoginServlet";
	//ע��
	public static final String Regist_URL = "http://"+IP+":8080/ShoppingServer/RegistServlet";
	//��ѯ�·��ı�
	public static final String ShirtSearch_URL = "http://"+IP+":8080/ShoppingServer/ShirtSearchServlet";
	//���ﳵ�����
	public static final String clothes_putIncar = "http://"+IP+":8080/ShoppingServer/shopping_car";
	//���ʹ��ﳵ
	public static final String clothes_carSearch = "http://"+IP+":8080/ShoppingServer/shopping_catSearch";
	//�޸Ĺ��ﳵ
	public static final String clothes_changeCart ="http://"+IP+":8080/ShoppingServer/shopping_changeCart";
	//�����ҵĶ���
	public static final String shopping_readOrder = "http://"+IP+":8080/ShoppingServer/shopping_readOrder";
	//ѡ������
	public static final String search_clothes = "http://"+IP+":8080/ShoppingServer/search_clothes";
	//��ͯ��װ����
	public static final String search_chilren = "http://"+IP+":8080/ShoppingServer/search_chilren";
	//getInfo
	public static final String getInfo_Url = "http://"+IP+":8080/ShoppingServer/getInfo";

	
	public static String username ;
	public static String password ;
	
	//����Activity��ʶ
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