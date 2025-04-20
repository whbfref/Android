package shopping.clothes;

import java.security.PublicKey;

import Values.bean.UrlValues;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.shoppingclothes.R;



public class selectLR extends Activity{
	

	private Button Select_zhuce = null ;
	private Button Select_Login = null ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		setContentView(R.layout.selectlr);
		//将结束状态设为 0
		UrlValues.finishState=0;
		
		Select_zhuce = (Button)findViewById(R.id.Select_zhuce);
		Select_Login =  (Button)findViewById(R.id.Select_login);
	
		//前往注册
		Select_zhuce.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					Select_zhuce.setTextColor(Color.parseColor("#F2F2F2"));
					Intent intent = new Intent();
					intent.setClass(selectLR.this, shopping_Regist.class);
					selectLR.this.startActivity(intent);
				}
				else if(event.getAction()==MotionEvent.ACTION_UP){
					Select_zhuce.setTextColor(Color.parseColor("#C1C1C1"));
				}
				return false;
			}
		});
		
		//前往登陆
		Select_Login.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					Select_Login.setTextColor(Color.parseColor("#F2F2F2"));
					Intent intent = new Intent();
					intent.setClass(selectLR.this, ShoppingLogin.class);
					selectLR.this.startActivity(intent);
			}
				else if(event.getAction()==MotionEvent.ACTION_UP){
					Select_Login.setTextColor(Color.parseColor("#C1C1C1"));
				}
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(UrlValues.finishState == 1){
			finish();
		}
	}
	
}