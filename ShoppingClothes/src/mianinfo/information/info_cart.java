/**
 * 
 */
/**
 * @author 凯
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

	//访问购物车Url
	private String Url = UrlValues.clothes_carSearch;
	//结算按钮
	private Button info_shoppingcart_buy = null;
	//总合计
	private TextView info_shoppingcart_price = null;
	//购物车listView
	private ListView info_shoppingcart_list;
	//获取返回输入流list
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
		
		//结算按钮
		info_shoppingcart_buy.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				//新建一个询问对话框
				new AlertDialog.Builder(info_cart.this).
				setMessage("是否结算所选物品").
				setCancelable(false).
				setNegativeButton("否", null).
				setPositiveButton("是", new DialogInterface.OnClickListener() {
					
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
								Toast.makeText(info_cart.this, "请选择物品", 1).show();
							}
							info_shoppingcart_price.setText("0");
						}
						else{
							Toast.makeText(info_cart.this, "购物车是空的", 1).show();
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
			//获取用户账号
			String username = UrlValues.username;
			
			HttpPost httpPost = new HttpPost(Url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			list.add(new BasicNameValuePair("username", username.toString()));
			try {
				//设置编码
				httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()== 200){
					//创建一个字节数组
					byte[] buff = new byte[2048];
					//获取响应内容
					buff = EntityUtils.toByteArray(httpResponse.getEntity());
					//创建字节流输入对象
					ByteArrayInputStream bais = new ByteArrayInputStream(buff);
					//创建传输对象的输入流对象
					ObjectInputStream ois = new ObjectInputStream(bais);
					//获取对象
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
						//将衣服的名字转换为对应drawable的int值
						int resID = getResources().getIdentifier(ClothesUrl, "drawable", "com.example.shoppingclothes");
						//将数据储存在另一个map中
						mapCart.put("info_EditBye", "1");
						mapCart.put("info_checkBox", false);
						mapCart.put("ClothesId", ClothesId);
						mapCart.put("info_imageView1", resID);
						mapCart.put("info_ClothesName", ClothesName);
						mapCart.put("info_Price", ClothesPrice);
						mapCart.put("info_buy", ClothesBuy);
						mapCart.put("ClothesSave", ClothesSave);
						mapCart.put("info_all",jisuanPrice(ClothesPrice, ClothesBuy));
					
						//将mapCart添加到listCart
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
				//将listCart显示出来
				info_shoppingcart_list_View(listCart);
			}
		}		
	}
	
	//将list显示出来
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
						//获取当前行的Map
						final Map<String,Object> map = (Map<String, Object>) simpleAdapter.getItem(position);
						if(convertView==null){
							convertView = View.inflate(info_cart.this, R.layout.info_shoppingcart, null);
						}
						//编辑按钮
						final Button info_edit = (Button)convertView.findViewById(R.id.info_edit);
						//垃圾箱删除按钮
						final Button info_rubbish = (Button)convertView.findViewById(R.id.info_rubbish);
						//数量编辑按钮
						final Button info_EditBye = (Button)convertView.findViewById(R.id.info_EditBye);
						//单价
						final TextView info_Price = (TextView)convertView.findViewById(R.id.info_Price);
						//每个list服装数量
						final TextView info_buy = (TextView)convertView.findViewById(R.id.info_buy);
						//每个list总价格
						final TextView info_all = (TextView)convertView.findViewById(R.id.info_all);
						//每个list的选择框
						final CheckBox info_checkBox = (CheckBox)convertView.findViewById(R.id.info_checkBox);
						
						//为选择框设置监听
						info_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								if(isChecked){
									//将布尔值记录在listCart中
									map.put("info_checkBox", info_checkBox.isChecked());
									listCart.set(mposition,map);
									//计算全部价格并且计算
									info_shoppingcart_price.setText(jisuanAllprice(listCart).toString());
									
								}
								else{
									//将布尔值记录在listCart中
									map.put("info_checkBox",info_checkBox.isChecked());
									listCart.set(mposition, map);
									//计算全部价格并且计算
									info_shoppingcart_price.setText(jisuanAllprice(listCart).toString());
								}
							}
						});
						
						//为垃圾箱设置监听
						info_rubbish.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								new AlertDialog.Builder(info_cart.this).setMessage("是否删除").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
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
						
						//数量编辑按钮
						info_EditBye.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								final EditText eText = new EditText(info_cart.this);
								eText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
								
								AlertDialog.Builder builder = new AlertDialog.Builder(info_cart.this);
								builder.setMessage("输入数量").setIcon(R.drawable.input_normal).setView(eText).setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										//修改对应Map的修改数量
										map.put("info_EditBye", eText.getText().toString());
										listCart.set(mposition, map);
										//显示在数量按钮上
										info_EditBye.setText(eText.getText().toString());
									}
								});
								 AlertDialog alert = builder.create();
						         alert.show();
							}
						});
						
						//为编辑设置监听
						info_edit.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(info_edit.getText().equals("编辑")){
									info_edit.setText("完成");
									info_rubbish.setVisibility(View.VISIBLE);
									info_EditBye.setVisibility(View.VISIBLE);  
								}
								else if(info_edit.getText().equals("完成")){
									//正则表达
									Pattern pattern = Pattern.compile("[0-9]*"); 
									//判断用户输入的是否是数字
									if( pattern.matcher(info_EditBye.getText().toString()).matches()){
										info_edit.setText("编辑");
										info_rubbish.setVisibility(View.INVISIBLE);
										info_EditBye.setVisibility(View.INVISIBLE);
										//设置数量
										info_buy.setText(info_EditBye.getText().toString());
										//计算价格并且显示
										info_all.setText(jisuanPrice(info_Price.getText().toString(), info_buy.getText().toString()));
										
										//无意碰到这行代码。。
										map.put("info_buy", info_buy.getText().toString());
										map.put("info_all", info_all.getText().toString());
										listCart.set(mposition,map);
										
										//访问修改购物车数据库
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
										Toast.makeText(getApplicationContext(), "请输入数字", 1).show();
									}
								}
							}
						});
						return super.getView(position, convertView, parent);
					}
			
		};
		info_shoppingcart_list.setAdapter(simpleAdapter);	
	}
	
	//计算勾中物品的总价格
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
	
	//计算单个list中的总价
	protected String jisuanPrice(String price, String amount) {
		int all = (Integer.parseInt(price))*(Integer.parseInt(amount));
		
		return String.valueOf(all);
	}
}