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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import Values.bean.UrlValues;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import background.focus.MainFocusActivity;

import com.example.shoppingclothes.R;

@SuppressLint("ShowToast")
public class ShoppingLogin extends Activity{
	
	private MainFrameTask mMainFrameTask = null;
	private CustomProgressDialog progressDialog = null;
	
	private EditText username;
	private EditText password;
	private Button LoginButton = null ;
	private CheckBox MemoryPass = null;

	public String user = "";
	public String pass = "";
	
	public HttpClient client = new DefaultHttpClient();
	public static final String URL = UrlValues.Login_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		setContentView(R.layout.shoppinglogin);
	
		username = (EditText)findViewById(R.id.Shopping_username);
		password = (EditText)findViewById(R.id.Shopping_password);
		LoginButton = (Button)findViewById(R.id.loginButton);
		mMainFrameTask = new MainFrameTask(this);
		MemoryPass = (CheckBox)findViewById(R.id.MemoryPass);
		//设置记住密码
		MemoryPass.setChecked(true);
		//SharedPreferences将username和password记录起来   每次进去软件时  开始从中读取数据  放入login_name，login_password中
	    SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
	    String name_str=remdname.getString("name", "");
	    String pass_str=remdname.getString("pass", "");
	    username.setText(name_str);
	    password.setText(pass_str);

	    //记住密码
	    MemoryPass.setOnCheckedChangeListener(new OnCheckedChangeListener() {		
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				 if(isChecked){
					 SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
				     SharedPreferences.Editor edit=remdname.edit();
				     edit.putString("name", username.getText().toString());
				     edit.putString("pass", password.getText().toString());
				     edit.commit();
				 }
				  if(!isChecked){
				     SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
				     SharedPreferences.Editor edit=remdname.edit();
				     edit.putString("name", "");
				     edit.putString("pass", "");
				     edit.commit();
				  }
			}
		});
	    
		//登陆
		LoginButton.setOnTouchListener(new OnTouchListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					LoginButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
					
					user = username.getText().toString();
					pass = password.getText().toString();
					
					//将账号密码放入静态值类中
					UrlValues.username = user;
					UrlValues.password = pass;
					//检测用户名密码
					if(MemoryPass.isChecked()){
						 SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
					     SharedPreferences.Editor edit=remdname.edit();
					     edit.putString("name", user);
					     edit.putString("pass", pass);
					     edit.commit();
					     
				     if (user.equals("")) {
				    	 Toast.makeText(getApplicationContext(), "请输入账号",Toast.LENGTH_SHORT).show();
				     } else if (pass.equals("")) {
				    	 Toast.makeText(getApplicationContext(), "请输入密码",Toast.LENGTH_SHORT).show();
				     } else if (!user.equals("") && !pass.equals("")) {
				    	 new AT().execute(user, pass);
				     }
					}
				}
				else if(event.getAction()==MotionEvent.ACTION_UP){
					LoginButton.setBackgroundColor(Color.parseColor("#C1C1C1"));
				}			
				return false;
			}
		});
	}
	
	//在线程池中访问并验证登陆
	@SuppressWarnings("rawtypes")
	class AT extends AsyncTask{
		
		String result = "";


		@SuppressWarnings("static-access")
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			
			user = username.getText().toString();
			pass = password.getText().toString();
			
			UrlValues.username = user;
			UrlValues.password = pass;
			//建立Post请求
			HttpPost httpPost = new HttpPost(URL);
			// 创建参数
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("username", user.toString()));
			params1.add(new BasicNameValuePair("password", pass.toString()));
			
			try {
				// 对提交数据进行编码
				httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
				HttpResponse httpResponse = client.execute(httpPost);
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
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}		
			return result;
		}

		protected void onPreExecute() {
			//开始进度条
			new MainFrameTask(null).execute();
		}
		
		protected void onPostExecute(Object result) {
			
			if(result.toString().equals("success")){
				//关闭进度条
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "登陆成功", 1).show();
				Intent intent = new Intent();
				intent.setClass(ShoppingLogin.this, MainFocusActivity.class);
				ShoppingLogin.this.startActivity(intent);
				//结束selectLR Activity
				UrlValues.finishState = 1;
				finish();
			}
			else{
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "登陆失败", 1).show();
			}
		}
		
		protected void onCancelled() {
			super.onCancelled();
		}
		
	}
	
/**以下是ProgressBar的自定义
 * */
	class MainFrameTask extends AsyncTask<Integer, String, Integer>{
		@SuppressWarnings("unused")
		private ShoppingLogin mainFrame = null;
		
		public MainFrameTask(ShoppingLogin mainFrame){
			this.mainFrame = mainFrame;
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
			
		@Override
		protected void onPreExecute() {
			startProgressDialog();
		}

		@Override
		protected void onPostExecute(Integer result) {
			stopProgressDialog();
		}		
	}
	protected void onDestroy() {
		stopProgressDialog();
		
		if (mMainFrameTask != null && !mMainFrameTask.isCancelled()){
			mMainFrameTask.cancel(true);
		}
		
		super.onDestroy();
	}
	private void startProgressDialog(){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(this);
	    	progressDialog.setMessage("正在登陆中...");
		}
		
    	progressDialog.show();
	}
	
	private void stopProgressDialog(){
		if (progressDialog != null){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

		
}