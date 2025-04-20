package mianinfo.information;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.example.shoppingclothes.R;
import Values.bean.UrlValues;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class info_EditInfomation extends Activity{
	
	private String URL = UrlValues.getInfo_Url;
	private String setAction = "";

	private EditText edit_infomation = null;
	private Button clear = null;
	private TextView edit_infomation_text = null;
	private Button edit_infomation_ok = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_infomation);

		edit_infomation = (EditText)findViewById(R.id.edit_infomation);
		clear = (Button)findViewById(R.id.edit_infomation_clear);
		edit_infomation_text = (TextView)findViewById(R.id.edit_infomation_text);
		edit_infomation_ok = (Button)findViewById(R.id.edit_infomation_ok);
		//获取传递过来的key值
		getKey();	
		
		
		//完成按钮监听
		edit_infomation_ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				new AT().execute(UrlValues.username);
			}
		});
		//清除编辑框
		clear.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				edit_infomation.setText("");
				edit_infomation_text.setText("0");
			}
		});
		//为编辑框设置监听
		edit_infomation.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				edit_infomation_text.setText(String.valueOf(edit_infomation.getText().length()));
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {			
			}		
			public void afterTextChanged(Editable s) {				
			}
		});
	}

	//获取值
	private void getKey() {
		Intent intent = getIntent();
		String key = intent.getStringExtra("key");
		setAction = intent.getStringExtra("setAction");
		edit_infomation.setText(key);
		edit_infomation.setSelection(edit_infomation.getText().length());
		edit_infomation_text.setText(String.valueOf(key.length()));
	}
	
	//线程池
	@SuppressLint("ShowToast")
	class AT extends AsyncTask<Object, Object, Object>{

		private String result = "";
		
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@SuppressWarnings("static-access")
		protected Object doInBackground(Object... params) {
			
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("username", UrlValues.username));
			list.add(new BasicNameValuePair("value", edit_infomation.getText().toString()));
			list.add(new BasicNameValuePair("action", "set"));
			list.add(new BasicNameValuePair("setAction", setAction));
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list,"GBK"));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					byte[] buff = new byte[2048];
					buff = EntityUtils.toByteArray(httpResponse.getEntity());
					ByteArrayInputStream bais = new ByteArrayInputStream(buff);
					DataInputStream dis = new DataInputStream(bais);
					result = dis.readUTF(dis);
					dis.close();
					bais.close();
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
			}	
			return result;
		}
		
		protected void onPostExecute(Object result) {
			
			if(result.equals("success")){
				Intent intent = new Intent();
				intent.setClass(info_EditInfomation.this, info_PersonInfo.class);
				info_EditInfomation.this.startActivity(intent);
				finish();
			} else{
				Toast.makeText(getApplicationContext(), "修改失败", 1).show();
				}
		}
	}
}
