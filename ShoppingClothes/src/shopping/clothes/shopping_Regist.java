package shopping.clothes;

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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.example.shoppingclothes.R;
import com.example.shoppingclothes.R.id;
import Values.bean.UrlValues;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


public class shopping_Regist extends Activity{
	
	public static final String URL = UrlValues.Regist_URL;
	
	private RadioGroup zhuce_sex = null;
	private RadioButton zhuce_nan = null;
	private RadioButton zhuce_nv = null;
	
	private Button reset = null;
	private Button zhuce = null;
	
	private EditText zhuce_realName = null;
	private EditText zhuce_Id = null;
	private EditText zhuce_Email = null;
	private EditText zhuce_zhanghao = null;
	private EditText zhuce_mima = null;

	private String zhanghao = "";
	private String mima = "";
	private String realName = "";
	private String Id = "";
	private String Email = "";
	private String Sex = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shopping_zhuce);
		
		//按钮注册
		reset = (Button)findViewById(R.id.zhuce_reset);
		zhuce = (Button)findViewById(R.id.zhuce_zhuce);
		//编辑文本注册
		zhuce_zhanghao = (EditText)findViewById(R.id.zhuce_zhanghao);
		zhuce_mima = (EditText)findViewById(R.id.zhuce_mima);
		zhuce_realName = (EditText)findViewById(R.id.realName);
		zhuce_Id = (EditText)findViewById(R.id.zhuce_Id);
		zhuce_Email = (EditText)findViewById(R.id.zhuce_Email);
		//RadioGroup
		zhuce_sex = (RadioGroup)findViewById(R.id.zhuce_sex);
		//RadioButton
		zhuce_nan = (RadioButton)findViewById(R.id.zhuce_nan);
		zhuce_nv = (RadioButton)findViewById(R.id.zhuce_nv);
		
		//重置按钮监听
		reset.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				zhuce_zhanghao.setText("");
				zhuce_mima.setText("");
				zhuce_realName.setText("");
				zhuce_Id.setText("");
				zhuce_Email.setText("");
			}
		});
		//注册按钮监听
		zhuce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhanghao = zhuce_zhanghao.getText().toString();
				mima = zhuce_mima.getText().toString();
				realName = zhuce_realName.getText().toString();
				Id = zhuce_Id.getText().toString();
				Email = zhuce_Email.getText().toString();
				Sex = zhuce_nan.getText().toString();
								
				if (!zhanghao.equals("") && !mima.equals("") && !realName.equals("")&& !Id.equals("")&& !Email.equals("")&& !Sex.equals("")) {
			    	 showDialog("确认注册?");
			     }
				else{
					Toast.makeText(getApplicationContext(), "请输入完整",Toast.LENGTH_SHORT).show();
				}
			}
		});	
		//性别单选按钮组监听
		zhuce_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == zhuce_nan.getId()){
					Sex = zhuce_nan.getText().toString();							
				}
				else if(checkedId == zhuce_nv.getId()){
					Sex = zhuce_nv.getText().toString();
				}
			}
		});
	}
	private void showDialog(String msg){
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		         builder.setMessage(msg).setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				}).setPositiveButton("确定",
		                 new DialogInterface.OnClickListener() {
		                     @SuppressWarnings("unchecked")
							@Override
		                     public void onClick(DialogInterface dialog, int id) {
		    			    	 new AT().execute(zhanghao, mima);
		                     }
		                 });
		         AlertDialog alert = builder.create();
		         alert.show();
	}
	
	@SuppressLint("ShowToast")
	@SuppressWarnings("rawtypes")
	class AT extends AsyncTask{
		
		String result = "";

		@SuppressWarnings("static-access")
		protected Object doInBackground(Object... params) {
			
			//建立Post请求
			HttpPost httpPost = new HttpPost(URL);
			// 创建参数
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("zhanghao", zhanghao.toString()));
			params1.add(new BasicNameValuePair("mima", mima.toString()));		
			params1.add(new BasicNameValuePair("realName", realName));
			params1.add(new BasicNameValuePair("Id", Id));
			params1.add(new BasicNameValuePair("Email", Email));
			params1.add(new BasicNameValuePair("Sex", Sex));
			
			try {
				// 对提交数据进行编码
				httpPost.setEntity(new UrlEncodedFormEntity(params1,"GBK"));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				//获取响应状态码
				int state = httpResponse.getStatusLine().getStatusCode();
				//200响应成功
				if(state==200){
					// 利用字节数组流和包装的绑定数据
					byte[] data = new byte[2048];
					// 先把从服务端来的数据转化成字节数组
					data = EntityUtils.toByteArray(httpResponse.getEntity());
					// 再创建字节数组输入流对象
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					// 绑定字节流和数据包装流
					DataInputStream dis = new DataInputStream(bais);
					// 将字节数组中的数据还原成原来的各种数据类型
					result = new String(dis.readUTF(dis));
					//Log.i("服务器返回信息:", result);
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
			return result;
		}

		protected void onPreExecute() {
		}

		protected void onPostExecute(Object result) {
			
			if(result.equals("registsuccess")){
				Toast.makeText(getApplicationContext(), "注册成功!", 1).show();
				Intent intent = new Intent();
				intent.setClass(shopping_Regist.this, ShoppingLogin.class);
				shopping_Regist.this.startActivity(intent);
				finish();
			}
			else if(result.equals("registdefault")){
				Toast.makeText(getApplicationContext(), "用户已存在!", 1).show();
				zhuce_zhanghao.setText("");
				zhuce_mima.setText("");
			}
		}
		
	}
}