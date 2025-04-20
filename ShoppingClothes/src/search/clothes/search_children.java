package search.clothes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.shoppingclothes.R;

import Values.bean.UrlValues;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class search_children extends Activity{

	private String URL = UrlValues.search_chilren;
	private TextView listViewText = null;
	private SimpleAdapter simpleAdapter;
	private ListView lv;
	
	List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview);
		
		lv = (ListView)findViewById(R.id.shirt_listView);
		listViewText = (TextView)findViewById(R.id.listView_textID);
		listViewText.setText("儿童新装");
		new AT().execute(UrlValues.username);
		
        
        //点击listView每一条记录
        lv.setOnItemClickListener(new OnItemClickListener() {
        	
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) { 
				   Map<String,String> map=(Map<String,String>)simpleAdapter.getItem(position);
				   //取出Map数据并将数据传递到另一个Activity
				   String shirt_id = map.get("ClothesId");
				   String shirt_watch_imageView = map.get("shirt_urlName");
				   String shirt_name = (String)map.get("shirt_name");
				   String shirt_price = (String)map.get("shirt_price").substring(0,map.get("shirt_price").length()-1);
				   String shirt_save = (String)map.get("shirt_save").substring(3);
				   
				   Intent intent = new Intent();
				   
				   intent.putExtra("shirt_id", shirt_id);
				   intent.putExtra("shirt_urlName", shirt_watch_imageView);
				   intent.putExtra("shirt_name", shirt_name);
				   intent.putExtra("shirt_price", shirt_price);
				   intent.putExtra("shirt_save", shirt_save);

				   intent.setClass(search_children.this,search_watch.class);
				   search_children.this.startActivity(intent);
				   
			}
			
		});
	}
	
	//线程池
	class AT extends AsyncTask<Object, Object, Object>{

		private String result = "";
		
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressWarnings("unchecked")
		protected Object doInBackground(Object... params) {
			try {
	        	HttpPost httpPost = new HttpPost(URL);
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					// 利用字节数组流和包装的绑定数据
					byte[] data = new byte[2048];
					// 先把从服务端来的数据转化成字节数组
					data = EntityUtils.toByteArray(httpResponse.getEntity());
					// 再创建字节数组输入流对象
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					// 绑定字节流和数据包装流
					ObjectInputStream ois = new ObjectInputStream(bais);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					list=(List<Map<String, Object>>) ois.readObject();
					ois.close();
					bais.close();
								
					for(int i=0; i<list.size(); i++){
						Map<String,Object> map =new HashMap<String,Object>();
						Map<String,Object> map1 =new HashMap<String,Object>();
						map = list.get(i);
						String ClothesId = (String) map.get("ClothesId");
						String ClothesUrl = (String) map.get("ClothesUrl");
						String ClothesName = (String) map.get("ClothesName");
						String ClothesPrice = (String) map.get("ClothesPrice");
						String ClothesSave= (String) map.get("ClothesSave");
						//将衣服的名字转换为对应drawable的int值
						int resID = getResources().getIdentifier(ClothesUrl, "drawable", "com.example.shoppingclothes");
						//将数据储存在另一个map中
						map1.put("ClothesId", ClothesId);
						map1.put("shirt_urlName", ClothesUrl);
						map1.put("shirt_url",resID);
						map1.put("shirt_name", ClothesName);
						map1.put("shirt_price",ClothesPrice+"元");
						map1.put("shirt_save","库存:"+ClothesSave);
						result = "success";
						//System.out.println(ClothesUrl+"....."+ClothesName+"....."+ClothesPrice+"....."+ClothesSave);
						list1.add(map1);
					}
					return result;
				}
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
		
		protected void onPostExecute(Object result) {
			if(result.equals("success")){
				//将list显示出来
				list_View(list1);
			}
			else{
				Toast.makeText(getApplicationContext(), "连接失败", 1).show();
			}
		}
	}
	
	public void list_View(List<Map<String, Object>> list){
		simpleAdapter = new SimpleAdapter(this, 
				list, 
				R.layout.search_shirt,
				new String[]{"shirt_url","shirt_name","shirt_price","shirt_save"}, 
				new int[]{R.id.shirt_url,R.id.shirt_name,R.id.shirt_price,R.id.shirt_save});
		
		lv.setAdapter(simpleAdapter);
	}
}
