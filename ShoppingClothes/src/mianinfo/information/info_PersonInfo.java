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

import background.focus.Main_informatin;

import com.example.shoppingclothes.R;

import Values.bean.UrlValues;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class info_PersonInfo extends Activity{
	private String Url = UrlValues.getInfo_Url;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private TextView infomatin_zhanghaoText = null;
	
	private Button infomatin_nameButton = null;
	private TextView infomatin_nameText = null;
	
	private Button infomatin_emailButton = null;
	private TextView infomatin_emailText = null;
	
	private Button infomatin_telButton = null;
	private TextView infomatin_telText= null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.infomation);
		
		infomatin_zhanghaoText = (TextView)findViewById(R.id.infomatin_zhanghaoText);
		
		infomatin_nameButton = (Button)findViewById(R.id.infomatin_nameButton);
		infomatin_nameText = (TextView)findViewById(R.id.infomatin_nameText);
		
		infomatin_emailButton = (Button)findViewById(R.id.infomatin_emailButton);
		infomatin_emailText = (TextView)findViewById(R.id.infomatin_emailText);
		
		infomatin_telButton = (Button)findViewById(R.id.infomatin_telButton);
		infomatin_telText = (TextView)findViewById(R.id.infomatin_telText);
		
		new AT().execute(UrlValues.username);
		
		// 修改姓名
		infomatin_nameButton.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					infomatin_nameButton.setBackgroundColor(Color.parseColor("#F0F0F0"));
					Intent intent = new Intent();
					intent.putExtra("key", infomatin_nameText.getText().toString());
					intent.putExtra("setAction", "setName");
					intent.setClass(info_PersonInfo.this, info_EditInfomation.class);
					info_PersonInfo.this.startActivity(intent);
					finish();
				}
				if(event.getAction() == MotionEvent.ACTION_UP){
					infomatin_nameButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}				
				return false;
			}
		});
		
		// 修改email
		infomatin_emailButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					infomatin_emailButton.setBackgroundColor(Color.parseColor("#F0F0F0"));
					Intent intent = new Intent();
					intent.putExtra("key", infomatin_emailText.getText().toString());
					intent.putExtra("setAction", "setemail");
					intent.setClass(info_PersonInfo.this,info_EditInfomation.class);
					info_PersonInfo.this.startActivity(intent);
					finish();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					infomatin_emailButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
				return false;
			}
		});
		

		// 修改tel
		infomatin_telButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					infomatin_telButton.setBackgroundColor(Color.parseColor("#F0F0F0"));
					Intent intent = new Intent();
					intent.putExtra("key", infomatin_telText.getText().toString());
					intent.putExtra("setAction", "settel");
					intent.setClass(info_PersonInfo.this,info_EditInfomation.class);
					info_PersonInfo.this.startActivity(intent);
					finish();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					infomatin_telButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
				return false;
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
			
			HttpPost httpPost = new HttpPost(Url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("username", UrlValues.username));
			list.add(new BasicNameValuePair("action", "get"));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					byte[] buff = new byte[2048];
					buff = EntityUtils.toByteArray(httpResponse.getEntity());
					ByteArrayInputStream bais = new ByteArrayInputStream(buff);
					ObjectInputStream ois = new ObjectInputStream(bais);
					map = (Map<String, Object>) ois.readObject();
					ois.close();
					bais.close();
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
				//将获取的信息放到界面中 
				infomatin_zhanghaoText.setText(UrlValues.username);
				infomatin_nameText.setText((String)map.get("name"));
				infomatin_emailText.setText((String)map.get("email"));
				infomatin_telText.setText((String)map.get("tel"));
			}
			else{
				Toast.makeText(getApplicationContext(), "获取信息失败", 1).show();
			}
		}
	}
}
