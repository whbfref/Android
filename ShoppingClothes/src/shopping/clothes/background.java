/**
 * 
 */
/**
 * @author 凯
 *
 */
package shopping.clothes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.shoppingclothes.R;


public class background extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		setContentView(R.layout.background);
		
		//启动线程池
		new BackTask(null).execute();
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	class BackTask extends AsyncTask<Integer, String, Integer>{
		public BackTask(background mainFrame){
		}

		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected Integer doInBackground(Integer... params) {	
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
			
		@Override
		protected void onPostExecute(Integer result) {

			Intent intent = new Intent();
			intent.setClass(background.this, selectLR.class);
			background.this.startActivity(intent);	
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
}