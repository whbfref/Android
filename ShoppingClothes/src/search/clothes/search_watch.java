package search.clothes;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mianinfo.information.info_cart;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.shoppingclothes.R;

import Values.bean.StrictModeBean;
import Values.bean.UrlValues;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class search_watch extends Activity{

	private Button shirt_car = null;
	private Button shirt_buy = null;
	private Button shopping_cart = null;
	
	private ImageView image = null;
	private TextView price = null;
	private TextView name = null;
	private TextView save = null; 
	
	//衣服信息
	String shirt_id= null;
	String shirt_urlName = null;
	String shirt_name = null;
	String shirt_price = null;
	String shirt_save = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shirt_watch);
		//titlebar为自己标题栏的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar为自己标题栏的布局
		//解决android4.0访问网络问题
		StrictModeBean.StrictM();
		//显示传递的数据
		Intent intent = getIntent();
		getData(intent);
		
		shirt_car = (Button)findViewById(R.id.Thing_car);
		shirt_buy = (Button)findViewById(R.id.Thing_buy);
		shopping_cart = (Button)findViewById(R.id.shopping_cart);
		
		//查看购物车
		shopping_cart.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					shopping_cart.setBackgroundResource(R.drawable.shopping_cart1);
					Intent intent = new Intent();
					intent.setClass(search_watch.this, info_cart.class);
					search_watch.this.startActivity(intent);
				}
				else if(event.getAction() == MotionEvent.ACTION_UP){
					shopping_cart.setBackgroundResource(R.drawable.shopping_cart);
				}
				return false;
			}
		});
		
		//加入购物车
		shirt_car.setOnTouchListener(new OnTouchListener() {	
			public boolean onTouch(View v, MotionEvent event) {
				//按下
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					//设置颜色
					shirt_car.setBackgroundColor(Color.parseColor("#FFDAB9"));
					//询问对话框
					shirt_car_showDialog("是否加入购物车?");			
				}
				//抬起
				else if(event.getAction()==MotionEvent.ACTION_UP){
					shirt_car.setBackgroundColor(Color.parseColor("#FF8C69"));
				}
				return false;
			}
		});
		//直接购买
	}
	
	//购物车询问 Dialog
	private void shirt_car_showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setNegativeButton("取消", null).setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
			                     @SuppressWarnings({ "static-access" })
								public void onClick(DialogInterface dialog, int id) {
			                    	 //访问后台加入数据库购物车
			                    	 HttpPost httpPost = new HttpPost(UrlValues.clothes_putIncar);
			                    	 List<NameValuePair> list_cart = new ArrayList<NameValuePair>();
			                    	 list_cart.add(new BasicNameValuePair("username", UrlValues.username));
			                    	 list_cart.add(new BasicNameValuePair("shirt_id", shirt_id));
			                    	 
			                    	 try{
			                    		 //设置编码
			                    		httpPost.setEntity(new UrlEncodedFormEntity(list_cart,HTTP.UTF_8));
			                    		//响应请求
			                    		HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
			                    		
			                    		if(httpResponse.getStatusLine().getStatusCode()==200){
			                    			byte[] buff = new byte[2048];
			                    			buff = EntityUtils.toByteArray(httpResponse.getEntity());
			                    			ByteArrayInputStream bais = new ByteArrayInputStream(buff);
			                    			DataInputStream dis = new DataInputStream(bais);
			                    			//取出服务端返回的值
			                    			String result =new String(dis.readUTF(dis));
			                    			//处理返回的result
			                    			chuli(result);
			                    			dis.close();
			                    			bais.close();
			                    		}
			                    	 }catch (UnsupportedEncodingException e) {
			             				e.printStackTrace();
			             			} catch (ClientProtocolException e) {
			             				e.printStackTrace();
			             			} catch (IOException e) {
			             				e.printStackTrace();
			             			}		
			                     }
			                 });
			         AlertDialog alert = builder.create();
			         alert.show();
	}
	
	//获取传递数据
	public void getData(Intent intent){
		
			image = (ImageView)findViewById(R.id.shirt_watch_imageView);
			price = (TextView)findViewById(R.id.shirt_watch_price);
			name = (TextView)findViewById(R.id.shirt_watch_name);
			save = (TextView)findViewById(R.id.shirt_watch_save);
			
			shirt_id = intent.getStringExtra("shirt_id");
			shirt_urlName = intent.getStringExtra("shirt_urlName");
			shirt_name = intent.getStringExtra("shirt_name");
			shirt_price = intent.getStringExtra("shirt_price");
			shirt_save = intent.getStringExtra("shirt_save");
			
			int resID = getResources().getIdentifier(shirt_urlName, "drawable", "com.example.shoppingclothes");
			
			name.setText(shirt_name);
			price.setText(shirt_price);
			save.setText(shirt_save);
			image.setImageResource(resID);
		}
	
	//处理返回的result
	@SuppressLint("ShowToast")
	public void chuli(String result){
		
		if(result.equals("物品存在")){
			Toast.makeText(getApplicationContext(), "物品已存在", 1).show();
		}
		else if(result.equals("加入成功")){
			Toast.makeText(getApplicationContext(), "加入购物车成功", 1).show();
		}
		else if(result.equals("加入失败")){
			Toast.makeText(getApplicationContext(), "加入购物车失败", 1).show();
			
		}
		else{
			Toast.makeText(getApplicationContext(), "网络错误", 1).show();
		}	
	}
}	