package search.clothes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

import com.example.shoppingclothes.R;

import Values.bean.UrlValues;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class clothes_search extends Activity{
	//����������url
	private String SearchUrl = UrlValues.search_clothes;
	//��ѡ��
	private RadioGroup Sex = null;
	private RadioGroup Color = null;
	private RadioGroup Size = null;
	//��¼ѡ�� 
	private String sexString = "";
	private String colorString = "";
	private String sizeString = "";
	
	//Sex
	private RadioButton Sex_male = null;
	private RadioButton Sex_female = null;
	//Color
	private RadioButton Color_black = null;
	private RadioButton Color_blue = null;
	private RadioButton Color_red = null;
	private RadioButton Color_white = null;
	private RadioButton Color_grey = null;
	//Size
	private RadioButton Size_M = null;
	private RadioButton Size_L = null;
	private RadioButton Size_XL = null;
	
	private Button ok = null;
	
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

	private ListView listView ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_search_listview);
		//��ѡ��ע��
		Sex = (RadioGroup)findViewById(R.id.main_search_listSex);
		Color = (RadioGroup)findViewById(R.id.main_search_listColor);
		Size = (RadioGroup)findViewById(R.id.main_search_listSize);
		//Sex
		Sex_male = (RadioButton)findViewById(R.id.main_search_Sex_male);
		Sex_female = (RadioButton)findViewById(R.id.main_search_Sex_female);
		//Color
		Color_black = (RadioButton)findViewById(R.id.main_search_Color_black);
		Color_blue = (RadioButton)findViewById(R.id.main_search_Color_blue);
		Color_red = (RadioButton)findViewById(R.id.main_search_Color_red);
		Color_grey = (RadioButton)findViewById(R.id.main_search_Color_grey);
		Color_white = (RadioButton)findViewById(R.id.main_search_Color_white);
		//Size
		Size_M = (RadioButton)findViewById(R.id.main_search_Size_M);
		Size_L = (RadioButton)findViewById(R.id.main_search_Size_L);
		Size_XL = (RadioButton)findViewById(R.id.main_search_Size_XL);
		//ȷ����ťע��
		ok = (Button)findViewById(R.id.main_search_ok);
		//listViewע��
		listView = (ListView)findViewById(R.id.main_search_listView);
		
		//�Ա������
		Sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == Sex_male.getId()){
					sexString = Sex_male.getText().toString();
				}
				else if(checkedId == Sex_female.getId()){
					sexString = Sex_female.getText().toString();
				}
			}
		});
		//��ɫ������
		Color.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == Color_black.getId()){
					colorString = Color_black.getText().toString();
				}
				else if(checkedId == Color_blue.getId()){
					colorString = Color_blue.getText().toString();
				}
				else if(checkedId == Color_red.getId()){
					colorString = Color_red.getText().toString();
				}
				else if(checkedId == Color_white.getId()){
					colorString = Color_white.getText().toString();
				}
				else if(checkedId == Color_grey.getId()){
					colorString = Color_grey.getText().toString();
				}
			}
		});
		//�ߴ������
		Size.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == Size_M.getId()){
					sizeString = Size_M.getText().toString();
				}
				else if(checkedId == Size_L.getId()){
					sizeString = Size_L.getText().toString();
				}
				else if(checkedId == Size_XL.getId()){
					sizeString = Size_XL.getText().toString();
				}
			}
		});
		//ȷ�ϰ�ť����
		ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(sexString!="" &&colorString!="" &&sizeString!=""){
					new AT().execute(UrlValues.username);
				}
				else{
					Toast.makeText(getApplicationContext(), "��ѡ������", 1).show();
				}
			}
		});
		
		 //���listViewÿһ����¼
        listView.setOnItemClickListener(new OnItemClickListener() {
        	
			@SuppressWarnings("unchecked")
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

				   intent.setClass(clothes_search.this,search_watch.class);
				   clothes_search.this.startActivity(intent);
				   
			}
			
		});
	}
	
	class AT extends AsyncTask<Object, Object, Object>{
		
		String result = "";
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressWarnings("unchecked")
		protected Object doInBackground(Object... params) {

			HttpPost httpPost = new HttpPost(SearchUrl);
			
			List<NameValuePair> listpost = new ArrayList<NameValuePair>();
			listpost.add(new BasicNameValuePair("sex", sexString));
			listpost.add(new BasicNameValuePair("color", colorString));
			listpost.add(new BasicNameValuePair("size",sizeString));
	
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(listpost,"GBK"));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					// �����ֽ��������Ͱ�װ�İ�����
					byte[] data = new byte[2048];
					// �ȰѴӷ������������ת�����ֽ�����
					data = EntityUtils.toByteArray(httpResponse.getEntity());
					// �ٴ����ֽ���������������
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					// ���ֽ��������ݰ�װ��
					ObjectInputStream ois = new ObjectInputStream(bais);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					list = (List<Map<String, Object>>) ois.readObject();
					ois.close();
					bais.close();
					
					if(list.size()==0){
						result = "null";
						return result;
					}
					else{	
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
	
							list1.add(map1);
						}
						result = "success";
						return result;
					}
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
				//��list��ʾ����
				list_View(list1);
				//��list����Ա��´β�ѯ
				list1 = new ArrayList<Map<String,Object>>();
			}
			else if(result.equals("null")){
				Toast.makeText(getApplicationContext(), "û�в�ѯ���", 1).show();
			}
			else{
				Toast.makeText(getApplicationContext(), "��ѯʧ��", 1).show();
			}
		}
		
	}
	//�� list�е�������ʾ����
	public void list_View(List<Map<String, Object>> list){
		simpleAdapter = new SimpleAdapter(this, 
				list, 
				R.layout.search_shirt,
				new String[]{"shirt_url","shirt_name","shirt_price","shirt_save"}, 
				new int[]{R.id.shirt_url,R.id.shirt_name,R.id.shirt_price,R.id.shirt_save});
		
		listView.setAdapter(simpleAdapter);
	}	
}
