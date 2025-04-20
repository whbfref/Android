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
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ����Ϣ��
		setContentView(R.layout.shoppinglogin);
	
		username = (EditText)findViewById(R.id.Shopping_username);
		password = (EditText)findViewById(R.id.Shopping_password);
		LoginButton = (Button)findViewById(R.id.loginButton);
		mMainFrameTask = new MainFrameTask(this);
		MemoryPass = (CheckBox)findViewById(R.id.MemoryPass);
		//���ü�ס����
		MemoryPass.setChecked(true);
		//SharedPreferences��username��password��¼����   ÿ�ν�ȥ���ʱ  ��ʼ���ж�ȡ����  ����login_name��login_password��
	    SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
	    String name_str=remdname.getString("name", "");
	    String pass_str=remdname.getString("pass", "");
	    username.setText(name_str);
	    password.setText(pass_str);

	    //��ס����
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
	    
		//��½
		LoginButton.setOnTouchListener(new OnTouchListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					LoginButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
					
					user = username.getText().toString();
					pass = password.getText().toString();
					
					//���˺�������뾲ֵ̬����
					UrlValues.username = user;
					UrlValues.password = pass;
					//����û�������
					if(MemoryPass.isChecked()){
						 SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
					     SharedPreferences.Editor edit=remdname.edit();
					     edit.putString("name", user);
					     edit.putString("pass", pass);
					     edit.commit();
					     
				     if (user.equals("")) {
				    	 Toast.makeText(getApplicationContext(), "�������˺�",Toast.LENGTH_SHORT).show();
				     } else if (pass.equals("")) {
				    	 Toast.makeText(getApplicationContext(), "����������",Toast.LENGTH_SHORT).show();
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
	
	//���̳߳��з��ʲ���֤��½
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
			//����Post����
			HttpPost httpPost = new HttpPost(URL);
			// ��������
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("username", user.toString()));
			params1.add(new BasicNameValuePair("password", pass.toString()));
			
			try {
				// ���ύ���ݽ��б���
				httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
				HttpResponse httpResponse = client.execute(httpPost);
				//��ȡ��Ӧ״̬��
				int state = httpResponse.getStatusLine().getStatusCode();
				//200��Ӧ�ɹ�
				if(state==200){
					// �����ֽ��������Ͱ�װ�İ�����
					byte[] data = new byte[2048];
					// �ȰѴӷ������������ת�����ֽ�����
					data = EntityUtils.toByteArray(httpResponse.getEntity());
					// �ٴ����ֽ���������������
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					// ���ֽ��������ݰ�װ��
					DataInputStream dis = new DataInputStream(bais);
					// ���ֽ������е����ݻ�ԭ��ԭ���ĸ�����������
					result = new String(dis.readUTF(dis));
					//Log.i("������������Ϣ:", result);
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
			//��ʼ������
			new MainFrameTask(null).execute();
		}
		
		protected void onPostExecute(Object result) {
			
			if(result.toString().equals("success")){
				//�رս�����
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "��½�ɹ�", 1).show();
				Intent intent = new Intent();
				intent.setClass(ShoppingLogin.this, MainFocusActivity.class);
				ShoppingLogin.this.startActivity(intent);
				//����selectLR Activity
				UrlValues.finishState = 1;
				finish();
			}
			else{
				stopProgressDialog();
				Toast.makeText(getApplicationContext(), "��½ʧ��", 1).show();
			}
		}
		
		protected void onCancelled() {
			super.onCancelled();
		}
		
	}
	
/**������ProgressBar���Զ���
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
	    	progressDialog.setMessage("���ڵ�½��...");
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