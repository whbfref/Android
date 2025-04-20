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
	 * 覆盖在主页的一个View
	 * */
	private View view;
	/**
	 * 滚动显示和隐藏menu时，手指滑动需要达到的速度。
	 */
	public static final int SNAP_VELOCITY = 200;

	/**
	 * 屏幕宽度值。
	 */
	private int screenWidth;
	/**
	 * menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
	 */
	private int leftEdge;

	/**
	 * menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。
	 */
	private int rightEdge = 0;

	/**
	 * menu完全显示时，留给content的宽度值。
	 */
	private int menuPadding = 80;

	/**
	 * 主内容的布局。
	 */
	private View content;

	/**
	 * menu的布局。
	 */
	private View menu;

	/**
	 * menu布局的参数，通过此参数来更改leftMargin的值。
	 */
	private LinearLayout.LayoutParams menuParams;

	/**
	 * 记录手指按下时的横坐标。
	 */
	private float xDown;

	/**
	 * 记录手指移动时的横坐标。
	 */
	private float xMove;

	/**
	 * 记录手机抬起时的横坐标。
	 */
	private float xUp;

	/**
	 * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
	 */
	private boolean isMenuVisible;

	/**
	 * 用于计算手指滑动的速度。
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
			// 焦点滚动，添加图片
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
	                
	        //搜索衣服
	        search_shirt.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						//按下设置背景
						search_shirt.setBackgroundResource(R.drawable.searchshirt1);
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,search.clothes.search_shirts.class);
						MainActivity.this.startActivity(intent);
					}
					else if(event.getAction()== MotionEvent.ACTION_UP){
						//还原
						search_shirt.setBackgroundResource(R.drawable.searchshirt);
					}
					return false;
				}
			});
	}

	/**
	 * 初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。
	 */
	private void initValues() {
		WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = window.getDefaultDisplay().getWidth();
		content = findViewById(R.id.content);
		menu = findViewById(R.id.menu);
		menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
		// 将menu的宽度设置为屏幕宽度减去menuPadding
		menuParams.width = screenWidth - menuPadding;
		// 左边缘的值赋值为menu宽度的负数
		leftEdge = -menuParams.width;
		// menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
		menuParams.leftMargin = leftEdge;
		// 将content的宽度设置为屏幕宽度
		content.getLayoutParams().width = screenWidth;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时，记录按下时的横坐标
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
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
			// 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
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
	 * 可以点击主页组件
	 */
	private void isContentSetOnclick() {
		view.setVisibility(View.GONE);
		view.setClickable(false);
	}

	/**
	 * 不可以点击主页组件
	 */
	private void isViewSetOnclick() {
		view.setVisibility(View.VISIBLE);
		view.setClickable(true);
	}

	/**
	 * 在主页
	 * 
	 * 如果手指抬起坐标还在远点
	 * 
	 * 就默认要点击组件
	 */
	private boolean wantOnclickToContent() {
		return xUp - xDown == 0 && isMenuVisible == false;
	}

	/**
	 * 在菜单页
	 * 
	 * 如果手指抬起坐标还在远点
	 * 
	 * 就默认要显示主页
	 */
	private boolean wantToShowContent1() {
		return xUp - xDown == 0 && isMenuVisible == true;
	}

	/**
	 * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
	 * 
	 * @return 当前手势想显示content返回true，否则返回false。
	 */
	private boolean wantToShowContent() {
		return xUp - xDown < 0 && isMenuVisible;
	}

	/**
	 * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
	 * 
	 * @return 当前手势想显示menu返回true，否则返回false。
	 */
	private boolean wantToShowMenu() {
		return xUp - xDown > 0 && !isMenuVisible;
	}

	/**
	 * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
	 * 就认为应该滚动将menu展示出来。
	 * 
	 * @return 如果应该滚动将menu展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToMenu() {
		return xUp - xDown > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
	 * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
	 * 
	 * @return 如果应该滚动将content展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToContent() {
		return xDown - xUp + menuPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 将屏幕滚动到menu界面，滚动速度设定为30.
	 */
	private void scrollToMenu() {
		new ScrollTask().execute(30);
	}

	/**
	 * 将屏幕滚动到content界面，滚动速度设定为-30.
	 */
	private void scrollToContent() {
		new ScrollTask().execute(-30);
	}

	/**
	 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
	 * 
	 * @param event
	 *            content界面的滑动事件
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 获取手指在content界面滑动的速度。
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = menuParams.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
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
				// 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
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
	 * 使当前线程睡眠指定的毫秒数。
	 * 
	 * @param millis
	 *            指定当前线程睡眠多久，以毫秒为单位
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}