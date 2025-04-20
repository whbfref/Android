/**
 * 
 */
/**
 * @author ��
 *
 */
package background.focus;

import java.util.HashMap;
import java.util.Map;

import search.clothes.clothes_search;
import shopping.clothes.ShoppingLogin;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import search.clothes.*;
import com.example.shoppingclothes.R;

public class MainFocusActivity extends Activity {
	private Gallery gallery;
	private FocusAdapter adapter;
	private ImageView focusPointImage;
	private long firstTime = 0;
	
	private Button search_Clothes = null;
	private Button search_shirt = null;
	private Button main_caidan = null;
	private Button search_children = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		//titlebarΪ�Լ��������Ĳ���
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_main);  //titlebarΪ�Լ��������Ĳ���
        
		search_Clothes = (Button)findViewById(R.id.search_Clothes);
        search_shirt = (Button)findViewById(R.id.search_shirt);
        main_caidan = (Button)findViewById(R.id.shopping_main_caidan);
        search_children = (Button)findViewById(R.id.search_children);
        
        gallery=(Gallery)findViewById(R.id.focusGallery);
        focusPointImage=(ImageView)findViewById(R.id.focusPointImage);
        adapter = new FocusAdapter(this);
        gallery.setAdapter(adapter);
        
        //����ͼƬ
        gallery.setOnItemSelectedListener(new OnItemSelectedListener(){
			@SuppressWarnings({ "rawtypes", "unused" })
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Map map = adapter.getItem(position);
				switch(position){
					case 0:
						focusPointImage.setBackgroundResource(R.drawable.focus_point_1);
						break;
					case 1:
						focusPointImage.setBackgroundResource(R.drawable.focus_point_2);
						break;
					case 2:
						focusPointImage.setBackgroundResource(R.drawable.focus_point_3);
						break;
					case 3:
						focusPointImage.setBackgroundResource(R.drawable.focus_point_4);
						break;
					case 4:
						focusPointImage.setBackgroundResource(R.drawable.focus_point_5);
						break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {			
			}
        });       
        //������������ͼƬ 
        Map<String, Object> map1 = new  HashMap<String,Object>();
        map1.put("image", getResources().getDrawable(R.drawable.focus_background1));
      
        
        Map<String, Object> map2 = new  HashMap<String,Object>();
        map2.put("image", getResources().getDrawable(R.drawable.focus_background2));
     
        Map<String, Object> map3 = new  HashMap<String,Object>();
        map3.put("image", getResources().getDrawable(R.drawable.focus_background3));
        
        Map<String, Object> map4 = new  HashMap<String,Object>();
        map4.put("image", getResources().getDrawable(R.drawable.focus_background4));
       
        Map<String, Object> map5 = new  HashMap<String,Object>();
        map5.put("image", getResources().getDrawable(R.drawable.focus_background5));
                  
        adapter.addObject(map1);
        adapter.addObject(map2);
        adapter.addObject(map3);
        adapter.addObject(map4);
        adapter.addObject(map5);
        
        //��ͯ����
        search_children.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainFocusActivity.this, search_children.class);
				MainFocusActivity.this.startActivity(intent);
			}
		});
        
        //������ť
        search_Clothes.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainFocusActivity.this, clothes_search.class);
				MainFocusActivity.this.startActivity(intent);
			}
		});
                
        //�����·�
        search_shirt.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					//�������ñ���
					search_shirt.setBackgroundResource(R.drawable.searchshirt1);
					Intent intent = new Intent();
					intent.setClass(MainFocusActivity.this,search.clothes.search_shirts.class);
					MainFocusActivity.this.startActivity(intent);
				}
				else if(event.getAction()== MotionEvent.ACTION_UP){
					//��ԭ
					search_shirt.setBackgroundResource(R.drawable.searchshirt);
				}
				return false;
			}
		});
        
        //���ϲ˵�����ť����
        main_caidan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainFocusActivity.this, Main_informatin.class);
				MainFocusActivity.this.startActivity(intent);
			}
		});
    }
     //�������˳�����
    public boolean onKeyUp(int keyCode, KeyEvent event) {  
	       // TODO Auto-generated method stub  
	       switch(keyCode)  
	       {  
	       case KeyEvent.KEYCODE_BACK:  
	            long secondTime = System.currentTimeMillis();   
	             if (secondTime - firstTime > 2000) {                                         //������ΰ���ʱ��������2�룬���˳�  
	                 Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();   
	                 firstTime = secondTime;//����firstTime  
	                 return true;   
	             } else {                                                    //���ΰ���С��2��ʱ���˳�Ӧ��  
	            	finish();
	             }   
	           break;  
	       }  
	     return super.onKeyUp(keyCode, event);  
	   }
}