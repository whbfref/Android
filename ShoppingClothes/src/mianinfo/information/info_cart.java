/**
 * 
 */
/**
 * @author ��
 *
 */
package mianinfo.information;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class info_cart extends Activity{

	//���ʹ��ﳵUrl
	private String Url = UrlValues.clothes_carSearch;
	//���㰴ť
	private Button info_shoppingcart_buy = null;
	//�ܺϼ�
	private TextView info_shoppingcart_price = null;
	//���ﳵlistView
	private ListView info_shoppingcart_list;
	//��ȡ����������list
	private List<Map<String, Object>> listCart = new ArrayList<Map<String, Object>>();
	
	private SimpleAdapter simpleAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info_shoppingcart_listview);
		
		info_shoppingcart_buy = (Button)findViewById(R.id.info_shoppingcart_buy);
		info_shoppingcart_price = (TextView)findViewById(R.id.info_shoppingcart_price);
		info_shoppingcart_list = (ListView)findViewById(R.id.info_shoppingcart_list);
		
		info_shoppingcart_price.setText("0");
		new asyncTask().execute(UrlValues.username);
		
		//���㰴ť
		info_shoppingcart_buy.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				//�½�һ��ѯ�ʶԻ���
				new AlertDialog.Builder(info_cart.this).
				setMessage("�Ƿ������ѡ��Ʒ").
				setCancelable(false).
				setNegativeButton("��", null).
				setPositiveButton("��", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						if(listCart.size()!=0){
							int size =listCart.size();
							int i=0;
							for( i=0; i<listCart.size();i++){
								Map<String, Object> map = new HashMap<String, Object>();
								map = listCart.get(i);
								if(map.get("info_checkBox").equals(true)){
									listCart.remove(i);
									i--;
									try {
										info_changeCart cc = new info_changeCart();
										cc.ChangeCartDataBase((String)map.get("ClothesId"),(String)map.get("info_buy"),"orderBuy");
										Thread.sleep(1*1000);
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							info_shoppingcart_list_View(listCart);
							if(i == size){
								Toast.makeText(info_cart.this, "��ѡ����Ʒ", 1).show();
							}
							info_shoppingcart_price.setText("0");
						}
						else{
							Toast.makeText(info_cart.this, "���ﳵ�ǿյ�", 1).show();
						}
					}
				}).create().show();

			}
			
		});
	}
	
	public class asyncTask extends AsyncTask<Object, Object, Object>{

		String result = "";
		
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressWarnings("unchecked")
		protected Object doInBackground(Object... params) {
			//��ȡ�û��˺�
			String username = UrlValues.username;
			
			HttpPost httpPost = new HttpPost(Url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			list.add(new BasicNameValuePair("username", username.toString()));
			try {
				//���ñ���
				httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()== 200){
					//����һ���ֽ�����
					byte[] buff = new byte[2048];
					//��ȡ��Ӧ����
					buff = EntityUtils.toByteArray(httpResponse.getEntity());
					//�����ֽ����������
					ByteArrayInputStream bais = new ByteArrayInputStream(buff);
					//����������������������
					ObjectInputStream ois = new ObjectInputStream(bais);
					//��ȡ����
					List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
					list1 = (List<Map<String, Object>>) ois.readObject();
					ois.close();
					bais.close();
					
					for(int i = 0;i<list1.size();i++){
						Map<String,Object> map =new HashMap<String,Object>();
						Map<String,Object> mapCart =new HashMap<String,Object>();
						map = list1.get(i);
						String ClothesId = (String) map.get("ClothesId");
						String ClothesUrl = (String) map.get("ClothesUrl");
						String ClothesName = (String) map.get("ClothesName");
						String ClothesPrice = (String) map.get("ClothesPrice");
						String ClothesSave= (String) map.get("ClothesSave");
						String ClothesBuy = (String)map.get("ClothesBuy");
						//���·�������ת��Ϊ��Ӧdrawable��intֵ
						int resID = getResources().getIdentifier(ClothesUrl, "drawable", "com.example.shoppingclothes");
						//�����ݴ�������һ��map��
						mapCart.put("info_EditBye", "1");
						mapCart.put("info_checkBox", false);
						mapCart.put("ClothesId", ClothesId);
						mapCart.put("info_imageView1", resID);
						mapCart.put("info_ClothesName", ClothesName);
						mapCart.put("info_Price", ClothesPrice);
						mapCart.put("info_buy", ClothesBuy);
						mapCart.put("ClothesSave", ClothesSave);
						mapCart.put("info_all",jisuanPrice(ClothesPrice, ClothesBuy));
					
						//��mapCart��ӵ�listCart
						listCart.add(mapCart);
					}	
					result = "success";
					return result;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Object result) {
			if(result.equals("success")){
				//��listCart��ʾ����
				info_shoppingcart_list_View(listCart);
			}
		}		
	}
	
	//��list��ʾ����
	public void info_shoppingcart_list_View(List<Map<String, Object>> list){
		
		simpleAdapter = new SimpleAdapter(this, 
				list, 
				R.layout.info_shoppingcart,
				new String[]{"info_imageView1","info_ClothesName","info_Price","info_buy","info_all","info_EditBye"}, 
				new int[]{R.id.info_imageView1,R.id.info_ClothesName,R.id.info_Price,R.id.info_buy,R.id.info_all,R.id.info_EditBye}){

			
					@SuppressWarnings("unchecked")
					public View getView(int position, View convertView,
							ViewGroup parent) {
						final int mposition  = position;
						//��ȡ��ǰ�е�Map
						final Map<String,Object> map = (Map<String, Object>) simpleAdapter.getItem(position);
						if(convertView==null){
							convertView = View.inflate(info_cart.this, R.layout.info_shoppingcart, null);
						}
						//�༭��ť
						final Button info_edit = (Button)convertView.findViewById(R.id.info_edit);
						//������ɾ����ť
						final Button info_rubbish = (Button)convertView.findViewById(R.id.info_rubbish);
						//�����༭��ť
						final Button info_EditBye = (Button)convertView.findViewById(R.id.info_EditBye);
						//����
						final TextView info_Price = (TextView)convertView.findViewById(R.id.info_Price);
						//ÿ��list��װ����
						final TextView info_buy = (TextView)convertView.findViewById(R.id.info_buy);
						//ÿ��list�ܼ۸�
						final TextView info_all = (TextView)convertView.findViewById(R.id.info_all);
						//ÿ��list��ѡ���
						final CheckBox info_checkBox = (CheckBox)convertView.findViewById(R.id.info_checkBox);
						
						//Ϊѡ������ü���
						info_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								if(isChecked){
									//������ֵ��¼��listCart��
									map.put("info_checkBox", info_checkBox.isChecked());
									listCart.set(mposition,map);
									//����ȫ���۸��Ҽ���
									info_shoppingcart_price.setText(jisuanAllprice(listCart).toString());
									
								}
								else{
									//������ֵ��¼��listCart��
									map.put("info_checkBox",info_checkBox.isChecked());
									listCart.set(mposition, map);
									//����ȫ���۸��Ҽ���
									info_shoppingcart_price.setText(jisuanAllprice(listCart).toString());
								}
							}
						});
						
						//Ϊ���������ü���
						info_rubbish.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								new AlertDialog.Builder(info_cart.this).setMessage("�Ƿ�ɾ��").setNegativeButton("ȡ��", null).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										listCart.remove(mposition);
										info_shoppingcart_list_View(listCart);
										
										try {
											info_changeCart cc = new info_changeCart();
											cc.ChangeCartDataBase((String)map.get("ClothesId"),(String)map.get("info_buy"),"delete");
										} catch (ClientProtocolException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}).show();
							}
						});
						
						//�����༭��ť
						info_EditBye.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								final EditText eText = new EditText(info_cart.this);
								eText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
								
								AlertDialog.Builder builder = new AlertDialog.Builder(info_cart.this);
								builder.setMessage("��������").setIcon(R.drawable.input_normal).setView(eText).setNegativeButton("ȡ��", null).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										//�޸Ķ�ӦMap���޸�����
										map.put("info_EditBye", eText.getText().toString());
										listCart.set(mposition, map);
										//��ʾ��������ť��
										info_EditBye.setText(eText.getText().toString());
									}
								});
								 AlertDialog alert = builder.create();
						         alert.show();
							}
						});
						
						//Ϊ�༭���ü���
						info_edit.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(info_edit.getText().equals("�༭")){
									info_edit.setText("���");
									info_rubbish.setVisibility(View.VISIBLE);
									info_EditBye.setVisibility(View.VISIBLE);  
								}
								else if(info_edit.getText().equals("���")){
									//������
									Pattern pattern = Pattern.compile("[0-9]*"); 
									//�ж��û�������Ƿ�������
									if( pattern.matcher(info_EditBye.getText().toString()).matches()){
										info_edit.setText("�༭");
										info_rubbish.setVisibility(View.INVISIBLE);
										info_EditBye.setVisibility(View.INVISIBLE);
										//��������
										info_buy.setText(info_EditBye.getText().toString());
										//����۸�����ʾ
										info_all.setText(jisuanPrice(info_Price.getText().toString(), info_buy.getText().toString()));
										
										//�����������д��롣��
										map.put("info_buy", info_buy.getText().toString());
										map.put("info_all", info_all.getText().toString());
										listCart.set(mposition,map);
										
										//�����޸Ĺ��ﳵ���ݿ�
										try {
											info_changeCart cc = new info_changeCart();
											cc.ChangeCartDataBase((String)map.get("ClothesId"),(String)map.get("info_buy"),"Change");
										} catch (ClientProtocolException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else{
										Toast.makeText(getApplicationContext(), "����������", 1).show();
									}
								}
							}
						});
						return super.getView(position, convertView, parent);
					}
			
		};
		info_shoppingcart_list.setAdapter(simpleAdapter);	
	}
	
	//���㹴����Ʒ���ܼ۸�
	protected String jisuanAllprice(List<Map<String, Object>> list){
		int all= 0;
		for(int position =0;position<list.size();position++){
			Map<String,Object> map = (Map<String, Object>)list.get(position);
			
			if(map.get("info_checkBox").equals(true)){
				all += Integer.parseInt((String) map.get("info_all"));
			}
		}
		
		return String.valueOf(all);		
	}
	
	//���㵥��list�е��ܼ�
	protected String jisuanPrice(String price, String amount) {
		int all = (Integer.parseInt(price))*(Integer.parseInt(amount));
		
		return String.valueOf(all);
	}
}