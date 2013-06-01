package mobi.intuitit.android.homepage;
import java.io.File;
import java.util.ArrayList;

import mobi.intuitit.android.mate.launcher.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GuestHome extends Activity implements OnScrollListener {

	private static final int TEXT_DIALOG = 0;
	
	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	
	private ScrollAdapter mAdapter;
	private ListView mListView;
	private LayoutInflater mInflater;
	private ArrayList<String> mRowList;
	private boolean mLockListView;
	public int msgCnt = 20;
	
	//임시 사진, 남길말, 이름
	public String tempState = "하이요";
	public int tempProfile = R.drawable.sms;
	// 사진, 남길말, 이름
	public TextView m_State;
	public TextView m_Name;
	public ImageView m_Profile;

//	private ImageView btnRecommend;
//	private ImageView btnDown;
//	private ImageView btnWrite;
	
	private TextView tv_Recommend;
	private TextView tv_Download;
	private TextView tv_Visit;
	
	public int count_Recommend = 0;
	public int count_Download = 0;
	public int count_Visit = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_guest_home);

//		mRowList = new ArrayList<String>();
//		mLockListView = true;
//
//		mAdapter = new ScrollAdapter(this, R.layout.row, mRowList);
//		mListView = (ListView) findViewById(R.id.guest_listView);

//		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		mListView.addFooterView(mInflater.inflate(R.layout.footer, null));
//
//		mListView.setOnScrollListener((OnScrollListener) this);
//		mListView.setAdapter(mAdapter);

//		btnRecommend = (ImageView) findViewById(R.id.btnRecommend);
//		btnDown = (ImageView) findViewById(R.id.btnDown);
//		btnWrite = (ImageView) findViewById(R.id.btnWirte);
		// 추천, 다운, 방문 텍스트뷰
		tv_Recommend = (TextView) findViewById(R.id.guest_recommend);
		tv_Download = (TextView) findViewById(R.id.guest_down);
		tv_Visit = (TextView) findViewById(R.id.guest_visit);
		// 남길말, 사진
		m_State = (TextView)findViewById(R.id.guest_state);
		m_Profile = (ImageView)findViewById(R.id.guest_profile);
		// 추천, 다운, 방문 셋
		tv_Recommend = setRecommend(count_Recommend);
		tv_Download = setDownload(count_Download);
		tv_Visit = setVisit(count_Visit);
		// 남길말, 사진 
		m_State = setState(tempState);
		m_Profile = setProfile();
		

		Gallery gallery = (Gallery) findViewById(R.id.gallery_guest);
		gallery.setAdapter(new GuestImageAdapter(this));

//		addItems(10);		

		// 방명록 작성 버튼
//		btnWrite.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				final Dialog dia = new Dialog(GuestHome.this);
//				dia.setContentView(R.layout.visitorbook);
//				dia.show();
//
//				Button visit_ok = (Button) dia.findViewById(R.id.ok);
//				Button visit_cancle = (Button) dia.findViewById(R.id.cancle);
//				EditText visitbook = (EditText) dia
//						.findViewById(R.id.editText1);
//
//				visit_ok.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Toast.makeText(GuestHome.this, "방명록",
//								Toast.LENGTH_SHORT).show();
//						dia.dismiss();
//					}
//				});
//				visit_cancle.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						dia.dismiss();
//					}
//				});
//			}
//		});
//
//		// 추천
//		btnRecommend.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(GuestHome.this, "추천", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		// 다운로드
//		btnDown.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(GuestHome.this, "다운로드", Toast.LENGTH_SHORT)
//						.show();
//			}
//		});
	}

	public TextView setRecommend(int n){
		tv_Recommend.setText("추천 : " + n);	
		return tv_Recommend;	
	}
	public TextView setDownload(int n){
		tv_Download.setText("다운 : " + n);	
		return tv_Download;	
	}
	public TextView setVisit(int n){
		tv_Visit.setText("방문 : " + n);	
		return tv_Visit;	
	}	
	// 남길말, 이름, 사진 설정
	public TextView setState(String state){
		m_State.setText(state);
		return m_State;
	}
	public ImageView setProfile(){		
		Uri uri = Uri.fromFile(new File(sdcard+"/Test/tayeon.jpg")); 
		Log.e("na", uri.toString());
		m_Profile.setImageURI(uri);			
//		m_Profile.setScaleType(ImageView.ScaleType.FIT_XY);
//		m_Profile.setLayoutParams(new Gallery.LayoutParams(300, 400));	
		return m_Profile;
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
		// 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
		int count = totalItemCount - visibleItemCount;

		if (msgCnt != 0) {
			if (firstVisibleItem >= count && totalItemCount != 0
					&& mLockListView == false) {
//				addItems(5);
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	private void addItems(final int size) {
		// 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
		mLockListView = true;

		Runnable run = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < size; i++) {
					mRowList.add("안녕 " + msgCnt);
					msgCnt--;
				}

				// 모든 데이터를 로드하여 적용하였다면 어댑터에 알리고
				// 리스트뷰의 락을 해제합니다.
				mAdapter.notifyDataSetChanged();
				if (msgCnt != 0)
					mLockListView = false;
				else
					mLockListView = true;
			}
		};

		// 속도의 딜레이를 구현하기 위한 꼼수
		Handler handler = new Handler();
		handler.postDelayed(run, 5000);
	}
}