/**
 * 
 */
/**
 * @author 凯
 *
 */
package search.clothes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shoppingclothes.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class search_shirt extends Activity{

	
	private ListView lv;
	private SimpleAdapter simpleAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview);
		
		lv = (ListView)findViewById(R.id.shirt_listView);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0;i<6;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			/*String shirt1 = "0x7f020010".substring(2);
			int a = Integer.parseInt(shirt1,16);*/
			//用这种方法
			int resID = getResources().getIdentifier("shirt2", "drawable", "com.example.shoppingclothes"); 
			Drawable image = getResources().getDrawable(resID);
			map.put("shirt_url", resID);
			map.put("shirt_name", "韩式潮流衣物");
			map.put("shirt_price", "30元");
			
			list.add(map);
		}
		
		simpleAdapter = new SimpleAdapter(this, 
				list, 
				R.layout.search_shirt,
				new String[]{"shirt_url","shirt_name","shirt_price"}, 
				new int[]{R.id.shirt_url,R.id.shirt_name,R.id.shirt_price});
		
		 lv.setAdapter(simpleAdapter);
		 
		 //点击listView每一条记录
		 lv.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub   
				   Map<String,String>  map=(Map<String,String>)simpleAdapter.getItem(position);     
				   String name=map.get("shirt_name");     
				   String value=map.get("shirt_price");     
				  System.out.println(name+"......"+value); 
			}
		});
	}
}