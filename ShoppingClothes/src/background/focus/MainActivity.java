package background.focus;

import java.util.HashMap;
import java.util.Map;

import com.example.shoppingclothes.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity implements OnTouchListener{

	/**
	 * ��������ҳ��һ��View
	 * */
	private View view;
	/**
	 * ������ʾ������menuʱ����ָ������Ҫ�ﵽ���ٶȡ�
	 */
	public static final int SNAP_VELOCITY = 200;

	/**
	 * ��Ļ���ֵ��
	 */
	private int screenWidth;
	/**
	 * menu�����Ի����������Ե��ֵ��menu���ֵĿ��������marginLeft�����ֵ֮�󣬲����ټ��١�
	 */
	private int leftEdge;

	/**
	 * menu�����Ի��������ұ�Ե��ֵ��Ϊ0����marginLeft����0֮�󣬲������ӡ�
	 */
	private int rightEdge = 0;

	/**
	 * menu��ȫ��ʾʱ������content�Ŀ��ֵ��
	 */
	private int menuPadding = 80;

	/**
	 * �����ݵĲ��֡�
	 */
	private View content;

	/**
	 * menu�Ĳ��֡�
	 */
	private View menu;

	/**
	 * menu���ֵĲ�����ͨ���˲���������leftMargin��ֵ��
	 */
	private LinearLayout.LayoutParams menuParams;

	/**
	 * ��¼��ָ����ʱ�ĺ����ꡣ
	 */
	private float xDown;

	/**
	 * ��¼��ָ�ƶ�ʱ�ĺ����ꡣ
	 */
	private float xMove;

	/**
	 * ��¼�ֻ�̧��ʱ�ĺ����ꡣ
	 */
	private float xUp;

	/**
	 * menu��ǰ����ʾ�������ء�ֻ����ȫ��ʾ������menuʱ�Ż���Ĵ�ֵ�����������д�ֵ��Ч��
	 */
	private boolean isMenuVisible;

	/**
	 * ���ڼ�����ָ�������ٶȡ�
	 */
	private VelocityTracker mVelocityTracker;
	
	
	private Gallery gallery;
	private FocusAdapter adapter;
	private ImageView focusPointImage;
	private long firstTime = 0;
	
	private Button search_Clothes = null;
	private Button search_shirt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		View view121 = findViewById(R.id.linearLayout);
		initValues();
		content.setOnTouchListener(this);
		view = findViewById(R.id.view);
		isViewSetOnclick();
		view.setOnTouchListener(this);
		
		search_shirt = (Button)findViewById(R.id.search_shirt);
	        
		gallery=(Gallery)findViewById(R.id.focusGallery);
		focusPointImage=(ImageView)findViewById(R.id.focusPointImage);
		adapter = new FocusAdapter(this);
		gallery.setAdapter(adapter);
	        
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
			// ������������ͼƬ
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("image",
					getResources().getDrawable(R.drawable.focus_background1));
	
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("image",
					getResources().getDrawable(R.drawable.focus_background2));
	
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("image",
					getResources().getDrawable(R.drawable.focus_background3));
	
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("image",
					getResources().getDrawable(R.drawable.focus_background4));
	
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("image",
					getResources().getDrawable(R.drawable.focus_background5));
	
			adapter.addObject(map1);
			adapter.addObject(map2);
			adapter.addObject(map3);
			adapter.addObject(map4);
			adapter.addObject(map5);
	                
	        //�����·�
	        search_shirt.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						//�������ñ���
						search_shirt.setBackgroundResource(R.drawable.searchshirt1);
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,search.clothes.search_shirts.class);
						MainActivity.this.startActivity(intent);
					}
					else if(event.getAction()== MotionEvent.ACTION_UP){
						//��ԭ
						search_shirt.setBackgroundResource(R.drawable.searchshirt);
					}
					return false;
				}
			});
	}

	/**
	 * ��ʼ��һЩ�ؼ������ݡ�������ȡ��Ļ�Ŀ�ȣ���content�����������ÿ�ȣ���menu�����������ÿ�Ⱥ�ƫ�ƾ���ȡ�
	 */
	private void initValues() {
		WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = window.getDefaultDisplay().getWidth();
		content = findViewById(R.id.content);
		menu = findViewById(R.id.menu);
		menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
		// ��menu�Ŀ������Ϊ��Ļ��ȼ�ȥmenuPadding
		menuParams.width = screenWidth - menuPadding;
		// ���Ե��ֵ��ֵΪmenu��ȵĸ���
		leftEdge = -menuParams.width;
		// menu��leftMargin����Ϊ���Ե��ֵ��������ʼ��ʱmenu�ͱ�Ϊ���ɼ�
		menuParams.leftMargin = leftEdge;
		// ��content�Ŀ������Ϊ��Ļ���
		content.getLayoutParams().width = screenWidth;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ��ָ����ʱ����¼����ʱ�ĺ�����
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			// ��ָ�ƶ�ʱ���ԱȰ���ʱ�ĺ����꣬������ƶ��ľ��룬������menu��leftMarginֵ���Ӷ���ʾ������menu
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown);
			if (isMenuVisible) {
				menuParams.leftMargin = distanceX;
			} else {
				menuParams.leftMargin = leftEdge + distanceX;
			}
			if (menuParams.leftMargin < leftEdge) {
				menuParams.leftMargin = leftEdge;
			} else if (menuParams.leftMargin > rightEdge) {
				menuParams.leftMargin = rightEdge;
			}
			menu.setLayoutParams(menuParams);
			break;
		case MotionEvent.ACTION_UP:
			// ��ָ̧��ʱ�������жϵ�ǰ���Ƶ���ͼ���Ӷ������ǹ�����menu���棬���ǹ�����content����
			xUp = event.getRawX();
			if (wantToShowMenu()) {
				isViewSetOnclick();
				if (shouldScrollToMenu()) {
					scrollToMenu();
				} else {
					scrollToContent();
				}
			} else if (wantToShowContent()) {
				isViewSetOnclick();
				if (shouldScrollToContent()) {
					scrollToContent();
				} else {
					scrollToMenu();
				}
			} else if (wantToShowContent1()) {
				isViewSetOnclick();
				scrollToContent();
			} else if (wantOnclickToContent()) {
				isContentSetOnclick();
			}
			recycleVelocityTracker();
			break;
		}
		return true;
	}

	/**
	 * ���Ե����ҳ���
	 */
	private void isContentSetOnclick() {
		view.setVisibility(View.GONE);
		view.setClickable(false);
	}

	/**
	 * �����Ե����ҳ���
	 */
	private void isViewSetOnclick() {
		view.setVisibility(View.VISIBLE);
		view.setClickable(true);
	}

	/**
	 * ����ҳ
	 * 
	 * �����ָ̧�����껹��Զ��
	 * 
	 * ��Ĭ��Ҫ������
	 */
	private boolean wantOnclickToContent() {
		return xUp - xDown == 0 && isMenuVisible == false;
	}

	/**
	 * �ڲ˵�ҳ
	 * 
	 * �����ָ̧�����껹��Զ��
	 * 
	 * ��Ĭ��Ҫ��ʾ��ҳ
	 */
	private boolean wantToShowContent1() {
		return xUp - xDown == 0 && isMenuVisible == true;
	}

	/**
	 * �жϵ�ǰ���Ƶ���ͼ�ǲ�������ʾcontent�������ָ�ƶ��ľ����Ǹ������ҵ�ǰmenu�ǿɼ��ģ�����Ϊ��ǰ��������Ҫ��ʾcontent��
	 * 
	 * @return ��ǰ��������ʾcontent����true�����򷵻�false��
	 */
	private boolean wantToShowContent() {
		return xUp - xDown < 0 && isMenuVisible;
	}

	/**
	 * �жϵ�ǰ���Ƶ���ͼ�ǲ�������ʾmenu�������ָ�ƶ��ľ������������ҵ�ǰmenu�ǲ��ɼ��ģ�����Ϊ��ǰ��������Ҫ��ʾmenu��
	 * 
	 * @return ��ǰ��������ʾmenu����true�����򷵻�false��
	 */
	private boolean wantToShowMenu() {
		return xUp - xDown > 0 && !isMenuVisible;
	}

	/**
	 * �ж��Ƿ�Ӧ�ù�����menuչʾ�����������ָ�ƶ����������Ļ��1/2��������ָ�ƶ��ٶȴ���SNAP_VELOCITY��
	 * ����ΪӦ�ù�����menuչʾ������
	 * 
	 * @return ���Ӧ�ù�����menuչʾ��������true�����򷵻�false��
	 */
	private boolean shouldScrollToMenu() {
		return xUp - xDown > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * �ж��Ƿ�Ӧ�ù�����contentչʾ�����������ָ�ƶ��������menuPadding������Ļ��1/2��
	 * ������ָ�ƶ��ٶȴ���SNAP_VELOCITY�� ����ΪӦ�ù�����contentչʾ������
	 * 
	 * @return ���Ӧ�ù�����contentչʾ��������true�����򷵻�false��
	 */
	private boolean shouldScrollToContent() {
		return xDown - xUp + menuPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * ����Ļ������menu���棬�����ٶ��趨Ϊ30.
	 */
	private void scrollToMenu() {
		new ScrollTask().execute(30);
	}

	/**
	 * ����Ļ������content���棬�����ٶ��趨Ϊ-30.
	 */
	private void scrollToContent() {
		new ScrollTask().execute(-30);
	}

	/**
	 * ����VelocityTracker���󣬲�������content����Ļ����¼����뵽VelocityTracker���С�
	 * 
	 * @param event
	 *            content����Ļ����¼�
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * ��ȡ��ָ��content���滬�����ٶȡ�
	 * 
	 * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ��
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	/**
	 * ����VelocityTracker����
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = menuParams.leftMargin;
			// ���ݴ�����ٶ����������棬������������߽���ұ߽�ʱ������ѭ����
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > rightEdge) {
					leftMargin = rightEdge;
					break;
				}
				if (leftMargin < leftEdge) {
					leftMargin = leftEdge;
					break;
				}
				publishProgress(leftMargin);
				// Ϊ��Ҫ�й���Ч��������ÿ��ѭ��ʹ�߳�˯��20���룬�������۲��ܹ���������������
				sleep(20);
			}
			if (speed[0] > 0) {
				isMenuVisible = true;
			} else {
				isMenuVisible = false;
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			menuParams.leftMargin = leftMargin[0];
			menu.setLayoutParams(menuParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			menuParams.leftMargin = leftMargin;
			menu.setLayoutParams(menuParams);
		}
	}

	/**
	 * ʹ��ǰ�߳�˯��ָ���ĺ�������
	 * 
	 * @param millis
	 *            ָ����ǰ�߳�˯�߶�ã��Ժ���Ϊ��λ
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}