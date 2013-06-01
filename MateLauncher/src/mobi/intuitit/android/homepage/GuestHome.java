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
	
	//�ӽ� ����, ���渻, �̸�
	public String tempState = "���̿�";
	public int tempProfile = R.drawable.sms;
	// ����, ���渻, �̸�
	public TextView m_State;
	public TextView m_Name;
	public ImageView m_Profile;

	private ImageView btnRecommend;
	private ImageView btnDown;
	private ImageView btnWrite;
	
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

		mRowList = new ArrayList<String>();
		mLockListView = true;

		mAdapter = new ScrollAdapter(this, R.layout.row, mRowList);
		mListView = (ListView) findViewById(R.id.guest_listView);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mListView.addFooterView(mInflater.inflate(R.layout.footer, null));

		mListView.setOnScrollListener((OnScrollListener) this);
		mListView.setAdapter(mAdapter);

		btnRecommend = (ImageView) findViewById(R.id.btnRecommend);
		btnDown = (ImageView) findViewById(R.id.btnDown);
		btnWrite = (ImageView) findViewById(R.id.btnWirte);
		// ��õ, �ٿ�, �湮 �ؽ�Ʈ��
		tv_Recommend = (TextView) findViewById(R.id.guest_recommend);
		tv_Download = (TextView) findViewById(R.id.guest_down);
		tv_Visit = (TextView) findViewById(R.id.guest_visit);
		// ���渻, ����
		m_State = (TextView)findViewById(R.id.guest_state);
		m_Profile = (ImageView)findViewById(R.id.guest_profile);
		// ��õ, �ٿ�, �湮 ��
		tv_Recommend = setRecommend(count_Recommend);
		tv_Download = setDownload(count_Download);
		tv_Visit = setVisit(count_Visit);
		// ���渻, ���� 
		m_State = setState(tempState);
		m_Profile = setProfile();
		

		Gallery gallery = (Gallery) findViewById(R.id.gallery_guest);
		gallery.setAdapter(new GuestImageAdapter(this));

		addItems(10);		

		// ����� �ۼ� ��ư
		btnWrite.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog dia = new Dialog(GuestHome.this);
				dia.setContentView(R.layout.visitorbook);
				dia.show();

				Button visit_ok = (Button) dia.findViewById(R.id.ok);
				Button visit_cancle = (Button) dia.findViewById(R.id.cancle);
				EditText visitbook = (EditText) dia
						.findViewById(R.id.editText1);

				visit_ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(GuestHome.this, "�����",
								Toast.LENGTH_SHORT).show();
						dia.dismiss();
					}
				});
				visit_cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dia.dismiss();
					}
				});
			}
		});

		// ��õ
		btnRecommend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(GuestHome.this, "��õ", Toast.LENGTH_SHORT).show();
			}
		});

		// �ٿ�ε�
		btnDown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(GuestHome.this, "�ٿ�ε�", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public TextView setRecommend(int n){
		tv_Recommend.setText("��õ : " + n);	
		return tv_Recommend;	
	}
	public TextView setDownload(int n){
		tv_Download.setText("�ٿ� : " + n);	
		return tv_Download;	
	}
	public TextView setVisit(int n){
		tv_Visit.setText("�湮 : " + n);	
		return tv_Visit;	
	}	
	// ���渻, �̸�, ���� ����
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
		// ���� ���� ó���� ���̴� ����ȣ�� �������� ����ȣ�� ���Ѱ���
		// ��ü�� ���ڿ� ���������� ���� �Ʒ��� ��ũ�� �Ǿ��ٰ� �����մϴ�.
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
		// �������� �߰��ϴ� ���� �ߺ� ��û�� �����ϱ� ���� ���� �ɾ�Ӵϴ�.
		mLockListView = true;

		Runnable run = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < size; i++) {
					mRowList.add("�ȳ� " + msgCnt);
					msgCnt--;
				}

				// ��� �����͸� �ε��Ͽ� �����Ͽ��ٸ� ����Ϳ� �˸���
				// ����Ʈ���� ���� �����մϴ�.
				mAdapter.notifyDataSetChanged();
				if (msgCnt != 0)
					mLockListView = false;
				else
					mLockListView = true;
			}
		};

		// �ӵ��� �����̸� �����ϱ� ���� �ļ�
		Handler handler = new Handler();
		handler.postDelayed(run, 5000);
	}
}