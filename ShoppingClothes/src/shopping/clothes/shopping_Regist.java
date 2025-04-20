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
		
		//��ťע��
		reset = (Button)findViewById(R.id.zhuce_reset);
		zhuce = (Button)findViewById(R.id.zhuce_zhuce);
		//�༭�ı�ע��
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
		
		//���ð�ť����
		reset.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				zhuce_zhanghao.setText("");
				zhuce_mima.setText("");
				zhuce_realName.setText("");
				zhuce_Id.setText("");
				zhuce_Email.setText("");
			}
		});
		//ע�ᰴť����
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
			    	 showDialog("ȷ��ע��?");
			     }
				else{
					Toast.makeText(getApplicationContext(), "����������",Toast.LENGTH_SHORT).show();
				}
			}
		});	
		//�Ա�ѡ��ť�����
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
		         builder.setMessage(msg).setCancelable(false).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				}).setPositiveButton("ȷ��",
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
			
			//����Post����
			HttpPost httpPost = new HttpPost(URL);
			// ��������
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("zhanghao", zhanghao.toString()));
			params1.add(new BasicNameValuePair("mima", mima.toString()));		
			params1.add(new BasicNameValuePair("realName", realName));
			params1.add(new BasicNameValuePair("Id", Id));
			params1.add(new BasicNameValuePair("Email", Email));
			params1.add(new BasicNameValuePair("Sex", Sex));
			
			try {
				// ���ύ���ݽ��б���
				httpPost.setEntity(new UrlEncodedFormEntity(params1,"GBK"));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
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
				Toast.makeText(getApplicationContext(), "ע��ɹ�!", 1).show();
				Intent intent = new Intent();
				intent.setClass(shopping_Regist.this, ShoppingLogin.class);
				shopping_Regist.this.startActivity(intent);
				finish();
			}
			else if(result.equals("registdefault")){
				Toast.makeText(getApplicationContext(), "�û��Ѵ���!", 1).show();
				zhuce_zhanghao.setText("");
				zhuce_mima.setText("");
			}
		}
		
	}
}