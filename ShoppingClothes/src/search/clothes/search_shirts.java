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

import Values.bean.StrictModeBean;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Contacts.Intents.Insert;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class search_shirts extends Activity{
	
	private TextView listView_textID = null;
	private ListView lv;
	private SimpleAdapter simpleAdapter;
	private static final String Shirt_URL = Values.bean.UrlValues.ShirtSearch_URL;
	
	List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
	@SuppressWarnings({ "unchecked" })
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview);		
		StrictModeBean.StrictM();
        
        listView_textID = (TextView)findViewById(R.id.listView_textID);
        lv = (ListView)findViewById(R.id.shirt_listView);
        
        listView_textID.setText("��ʽ��������");
        try {
        	HttpPost httpPost = new HttpPost(Shirt_URL);
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				// �����ֽ��������Ͱ�װ�İ�����
				byte[] data = new byte[2048];
				// �ȰѴӷ������������ת�����ֽ�����
				data = EntityUtils.toByteArray(httpResponse.getEntity());
				// �ٴ����ֽ���������������
				ByteArrayInputStream bais = new ByteArrayInputStream(data);
				// ���ֽ��������ݰ�װ��
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
					//���·�������ת��Ϊ��Ӧdrawable��intֵ
					int resID = getResources().getIdentifier(ClothesUrl, "drawable", "com.example.shoppingclothes");
					//�����ݴ�������һ��map��
					map1.put("ClothesId", ClothesId);
					map1.put("shirt_urlName", ClothesUrl);
					map1.put("shirt_url",resID);
					map1.put("shirt_name", ClothesName);
					map1.put("shirt_price",ClothesPrice+"Ԫ");
					map1.put("shirt_save","���:"+ClothesSave);
					//System.out.println(ClothesUrl+"....."+ClothesName+"....."+ClothesPrice+"....."+ClothesSave);
					list1.add(map1);
				}
				
				//��list��ʾ����
				list_View(list1);
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
        
        //���listViewÿһ����¼
        lv.setOnItemClickListener(new OnItemClickListener() {
        	
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) { 
				   Map<String,String> map=(Map<String,String>)simpleAdapter.getItem(position);
				   //ȡ��Map���ݲ������ݴ��ݵ���һ��Activity
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

				   intent.setClass(search_shirts.this,search_watch.class);
				   search_shirts.this.startActivity(intent);
				   
			}
			
		});
        //���list�ı���ɫ
        lv.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					lv.setBackgroundColor(Color.parseColor("#EBEBEB"));
				}
				else if(event.getAction()==MotionEvent.ACTION_UP){
					lv.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
				return false;
			}
		});
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