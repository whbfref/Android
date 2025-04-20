package mianinfo.information;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import Values.bean.UrlValues;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.shoppingclothes.R;

public class info_order extends Activity{
	//访问订单URL
	private String UrlOrder = UrlValues.shopping_readOrder;
	//listView
	private ListView info_order_listView = null;
	//创建一个总的List
	private List<Map<String, Object>> listOrder = new ArrayList<Map<String,Object>>();
	//创建一个简单适配器
	SimpleAdapter simpleAdapter;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info_shoppingorder_listview);
		
		info_order_listView = (ListView)findViewById(R.id.info_order_listView);
		
		new asyncTaskOrder().execute(UrlValues.username);
	}
	
	
	//线程池访问
	public class asyncTaskOrder extends AsyncTask<Object, Object, Object>{

		String result = "";
		
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@SuppressWarnings("unchecked")
		protected Object doInBackground(Object... params) {
			String username = UrlValues.username;
			
			HttpPost httpPost = new HttpPost(UrlOrder);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("username", username));
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					byte[] buff = new byte[2048];
					buff = EntityUtils.toByteArray(httpResponse.getEntity());
					ByteArrayInputStream bais = new ByteArrayInputStream(buff);
					ObjectInputStream ois = new ObjectInputStream(bais);
					//获取服务端传来的list
					List<Map<String, Object>> listReturn = new ArrayList<Map<String,Object>>();
					listReturn = (List<Map<String, Object>>) ois.readObject();
					ois.close();
					bais.close();
					
					for(int i=0;i<listReturn.size();i++){
						Map<String, Object> mapOrder = new HashMap<String, Object>();
						Map<String, Object> mapReturn = new HashMap<String, Object>();
						mapReturn = listReturn.get(i);
						
						//取数据
						String ClothesId = (String) mapReturn.get("ClothesId");
						String ClothesUrl = (String) mapReturn.get("ClothesUrl");
						String ClothesName = (String) mapReturn.get("ClothesName");
						String ClothesPrice = (String) mapReturn.get("ClothesPrice");
						String ClothesBuy = (String)mapReturn.get("ClothesBuy");
						String orderId = (String)mapReturn.get("orderId");
						//将衣服名字转化为int值
						int resId = getResources().getIdentifier(ClothesUrl, "drawable", "com.example.shoppingclothes");
						//将数据存储在list的map中
						mapOrder.put("info_shoppingOrder_order", orderId);
						mapOrder.put("info_shoppingOrder_ImageView1", resId);
						mapOrder.put("info_shoppingOrder_name", ClothesName);
						mapOrder.put("info_shoppingOrder_price", ClothesPrice);
						mapOrder.put("info_shoppingOrder_buy", ClothesBuy);
						mapOrder.put("info_shoppingOrder_all", String.valueOf((Integer.parseInt(ClothesPrice))*(Integer.parseInt(ClothesBuy))));
						
						//将数据存储
						listOrder.add(mapOrder);
					}
					
					result = "success";
					return result;
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		@SuppressLint("ShowToast")
		protected void onPostExecute(Object result) {
			if(result.equals("success")){
				info_shoppingorder_list_View(listOrder);
			}
			else{
				Toast.makeText(getApplicationContext(), "连接错误", 1).show();
			}
		}
	}
	
	//将list显示出来
	public void info_shoppingorder_list_View(List<Map<String, Object>> list){
	
		simpleAdapter = new SimpleAdapter(this,
				list, 
				R.layout.info_shoppingorder, 
				new String[]{"info_shoppingOrder_order","info_shoppingOrder_ImageView1","info_shoppingOrder_name","info_shoppingOrder_price","info_shoppingOrder_buy","info_shoppingOrder_all"},
				new int[]{R.id.info_shoppingOrder_order,R.id.info_shoppingOrder_ImageView1,R.id.info_shoppingOrder_name,R.id.info_shoppingOrder_price,R.id.info_shoppingOrder_buy,R.id.info_shoppingOrder_all});
		
		info_order_listView.setAdapter(simpleAdapter);
	}
}
