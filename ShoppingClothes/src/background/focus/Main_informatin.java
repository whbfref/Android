package background.focus;

import com.example.shoppingclothes.R;
import com.example.shoppingclothes.R.layout;

import mianinfo.information.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

public class Main_informatin extends Activity{
	//���ﳵ
	private Button info_cart = null;
	//�ҵĶ���
	private Button info_order = null;
	//������Ϣ
	private Button info_infomation = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_caidan_info);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_info);
			
		info_cart = (Button)findViewById(R.id.info_cart);
		info_order = (Button)findViewById(R.id.info_order);
		info_infomation = (Button)findViewById(R.id.info_infomation);
			
		//�鿴�ҵĹ��ﳵ
		info_cart.setOnTouchListener(new OnTouchListener() {
				
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()== MotionEvent.ACTION_DOWN){
					info_cart.setBackgroundColor(Color.parseColor("#F0F0F0"));
					Intent intent = new Intent();
					intent.setClass(Main_informatin.this,info_cart.class);
					Main_informatin.this.startActivity(intent);
				}
				else if(event.getAction()== MotionEvent.ACTION_UP){
					info_cart.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
				return false;
			}
		});
		
		//�鿴�ҵĶ���
		info_order.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()== MotionEvent.ACTION_DOWN){
					info_order.setBackgroundColor(Color.parseColor("#F0F0F0"));
					Intent intent = new Intent();
					intent.setClass(Main_informatin.this,info_order.class);
					Main_informatin.this.startActivity(intent);
				}
				else if(event.getAction()== MotionEvent.ACTION_UP){
					info_order.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
				
				return false;
			}
		});
		
		//�鿴������Ϣ
		info_infomation.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					info_infomation.setBackgroundColor(Color.parseColor("#F0F0F0"));
					Intent intent = new Intent();
					intent.setClass(Main_informatin.this, info_PersonInfo.class);
					Main_informatin.this.startActivity(intent);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){
					info_infomation.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
				return false;
			}
		});
	}
}
