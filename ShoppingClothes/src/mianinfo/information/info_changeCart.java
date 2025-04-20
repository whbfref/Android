package mianinfo.information;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
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

import android.os.AsyncTask;
import android.util.Log;
import Values.bean.UrlValues;

public class info_changeCart {
	//修改购物车Url
	private String urlChange = UrlValues.clothes_changeCart;
	
	private String clothesId;
	private String clothesbye;
	
	/**1、dex = orderBuy 表示购买并且在数据库中生成订单
	 * 2、dex = Change 表示修改数量
	 * 3、dex = delete 表示删除购物车的内容
	 * */
	private String dex;
	
	//修改购物车数据库
	public void ChangeCartDataBase(String ClothesId, String info_buy, String Dex) throws ClientProtocolException, IOException{
		clothesId = ClothesId;
		clothesbye = info_buy;
		dex = Dex;
		new asyncTask().execute(clothesId,clothesbye);
	}
	
	
	public class asyncTask extends AsyncTask<Object, Object, Object>{

		String result = "";
		
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressWarnings("static-access")
		protected Object doInBackground(Object... params) {

			HttpPost httpPost = new HttpPost(urlChange);
			
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("ClothesId",clothesId));
			list.add(new BasicNameValuePair("ClothesBuy",clothesbye));
			list.add(new BasicNameValuePair("dex", dex));
			if(dex.equals("orderBuy")){
				//生成订单
				list.add(new BasicNameValuePair("username", UrlValues.username));
				list.add(new BasicNameValuePair("orderId", UrlValues.DateOrderId()));
			}
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					byte[] buff = new byte[2048];
					buff = EntityUtils.toByteArray(httpResponse.getEntity());
					
					ByteArrayInputStream bais = new ByteArrayInputStream(buff);
					
					DataInputStream dis = new DataInputStream(bais);
					
					result = (String)dis.readUTF(dis);
					dis.close();
					bais.close();
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

		@Override
		protected void onPostExecute(Object result) {
			if(result.equals("success")){
				Log.v("LogCk", "result");
			}
			else if(result.equals("defeat")){
				Log.v("LogCk", "defeat");
			}
		}	
	}
}
