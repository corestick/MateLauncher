package mobi.intuitit.android.homepage;

import java.io.File;
import java.util.ArrayList;

import mobi.intuitit.android.mate.launcher.LauncherProvider;
import mobi.intuitit.android.mate.launcher.R;
import mobi.intuitit.android.mate.launcher.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class GuestHome extends Activity implements OnScrollListener,
		OnClickListener {

	public Intent mIntent = new Intent();
	
	final String serverUrl = "http://kimsunghyuntest2.appspot.com/simpleservletapp";

	private static final int TEXT_DIALOG = 0;

	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath();

	private ScrollAdapter mAdapter;
	private ListView mListView;
	private LayoutInflater mInflater;
	private ArrayList<String> mRowList;
	private boolean mLockListView;
	public int msgCnt = 20;

	// ���渻, ���� ���޹޴� ����
	public String m_state;
	public int m_profile;
	
	// ����, ���渻, �̸�
	public TextView tv_State;
	public TextView tv_Name;
	public ImageView tv_Profile;
	// private ImageView btnRecommend;
	// private ImageView btnDown;
	// private ImageView btnWrite;

	private TextView tv_Recommend;
	private TextView tv_Download;
	private TextView tv_Visit;

	public int count_Recommend = 0;
	public int count_Download = 0;
	public int count_Visit = 0;

	public Button downButton;
	public Button likeButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest_home);
		
		mIntent = getIntent();
		m_state = mIntent.getStringExtra("state");
		m_profile = mIntent.getIntExtra("profile", 0);

		downButton = (Button) findViewById(R.id.guest_download);
		likeButton = (Button) findViewById(R.id.guest_like_it);
		downButton.setOnClickListener(this);
		likeButton.setOnClickListener(this);

		// mRowList = new ArrayList<String>();
		// mLockListView = true;
		//
		// mAdapter = new ScrollAdapter(this, R.layout.row, mRowList);
		// mListView = (ListView) findViewById(R.id.guest_listView);

		// mInflater = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// mListView.addFooterView(mInflater.inflate(R.layout.footer, null));
		//
		// mListView.setOnScrollListener((OnScrollListener) this);
		// mListView.setAdapter(mAdapter);

		// btnRecommend = (ImageView) findViewById(R.id.btnRecommend);
		// btnDown = (ImageView) findViewById(R.id.btnDown);
		// btnWrite = (ImageView) findViewById(R.id.btnWirte);
		// ��õ, �ٿ�, �湮 �ؽ�Ʈ��
		tv_State = (TextView) findViewById(R.id.guest_state);
		tv_Profile = (ImageView) findViewById(R.id.guest_profile);
		tv_Recommend = (TextView) findViewById(R.id.guest_recommend);
		tv_Download = (TextView) findViewById(R.id.guest_down);
		tv_Visit = (TextView) findViewById(R.id.guest_visit);
		// ���渻, ����
		tv_State = setState(m_state);
		tv_Profile = setProfile(m_profile);
		// ��õ, �ٿ�, �湮 ��
		tv_Recommend = setRecommend(count_Recommend);
		tv_Download = setDownload(count_Download);
		tv_Visit = setVisit(count_Visit);
		

		Gallery gallery = (Gallery) findViewById(R.id.gallery_guest);
		gallery.setAdapter(new GuestImageAdapter(this));
		gallery.setSelection(1);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Guest_Dialog dialog = new Guest_Dialog(GuestHome.this, position);
				dialog.setCancelable(true);
				android.view.WindowManager.LayoutParams params = dialog
						.getWindow().getAttributes();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.MATCH_PARENT;
				dialog.getWindow().setAttributes(params);
				dialog.show();
			}
		});

		// addItems(10);

		// ���� �ۼ� ��ư
		// btnWrite.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// final Dialog dia = new Dialog(GuestHome.this);
		// dia.setContentView(R.layout.visitorbook);
		// dia.show();
		//
		// Button visit_ok = (Button) dia.findViewById(R.id.ok);
		// Button visit_cancle = (Button) dia.findViewById(R.id.cancle);
		// EditText visitbook = (EditText) dia
		// .findViewById(R.id.editText1);
		//
		// visit_ok.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(GuestHome.this, "����",
		// Toast.LENGTH_SHORT).show();
		// dia.dismiss();
		// }
		// });
		// visit_cancle.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// dia.dismiss();
		// }
		// });
		// }
		// });
		//
		// // ��õ
		// btnRecommend.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(GuestHome.this, "��õ", Toast.LENGTH_SHORT).show();
		// }
		// });
		//
		// // �ٿ�ε�
		// btnDown.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(GuestHome.this, "�ٿ�ε�", Toast.LENGTH_SHORT)
		// .show();
		// }
		// });
	}

	class Guest_Dialog extends Dialog implements
			android.view.View.OnClickListener {
		ImageView iv;

		public Guest_Dialog(Context context, int position) {
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_screen);

			iv = (ImageView) findViewById(R.id.dialog_imageview);
			Uri uri = Uri.fromFile(new File(sdcard
					+ "/MateLauncher/Owner/screen" + position + ".jpg"));
			iv.setImageURI(uri);
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			iv.setOnClickListener(this);
		}

		public void onClick(View view) {
			if (view == iv) {
				dismiss();
			}
		}
	}

	public TextView setRecommend(int n) {
		tv_Recommend.setText("" + n);
		return tv_Recommend;
	}

	public TextView setDownload(int n) {
		tv_Download.setText("" + n);
		return tv_Download;
	}

	public TextView setVisit(int n) {
		tv_Visit.setText("" + n);
		return tv_Visit;
	}

	// ���渻, �̸�, ���� ����
	public TextView setState(String state) {
		tv_State.setText(state);
		return tv_State;
	}
	public ImageView setProfile(int profile) {
		tv_Profile.setImageResource(profile);
		return tv_Profile;
	}

	public ImageView setProfile() {
		Uri uri = Uri.fromFile(new File(sdcard + "/Test/tayeon.jpg"));
		Log.e("na", uri.toString());
		tv_Profile.setImageURI(uri);
		// m_Profile.setScaleType(ImageView.ScaleType.FIT_XY);
		// m_Profile.setLayoutParams(new Gallery.LayoutParams(300, 400));
		return tv_Profile;
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
				// addItems(5);
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

	public String[] getJSONString(String str) {
		int bracketCnt = 0;
		int arrCnt = 0;
		int tmpCnt = 0;
		String[] strArr = new String[20];
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '{') {
				bracketCnt++;
			}
			if (str.charAt(i) == '}') {
				bracketCnt--;
				if (bracketCnt == 0) {
					strArr[arrCnt] = str.substring(tmpCnt, i + 1);
					arrCnt++;
					tmpCnt = i + 1;
				}
			}
		}
		return strArr;
	}

	// DB ������ ȭ�������
	public void remove_DB() {
		LauncherProvider lp = new LauncherProvider();
		lp.delete_table();
	}

	public void insert_DB() {
		String AUTHORITY = "mobi.intuitit.android.mate.launcher.settings";
		String TABLE_FAVORITES = "favorites";
		String PARAMETER_NOTIFY = "notify";
		final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://"
				+ AUTHORITY + "/" + TABLE_FAVORITES + "?" + PARAMETER_NOTIFY
				+ "=false");
		final ContentValues values = new ContentValues();
		final ContentResolver cr = getContentResolver();

		remove_DB(); // DB �����, ȭ�� View ����

		String str = JSONfunctions.getJSONfromURL(serverUrl, "123");
		String[] jsonArr = getJSONString(str);
		try {
			for (int i = 0; i < jsonArr.length; i++) {
				if (jsonArr[i] == null) {
					break;
				}
				JSONObject json = new JSONObject(jsonArr[i]);
				if (json.getString("wall").equals("null")) {
					values.put("container", json.getString("container"));
					values.put("screen", json.getString("screen"));
					values.put("cellX", json.getString("cellX"));
					values.put("cellY", json.getString("cellY"));
					values.put("mobjectType", json.getString("MobjectType"));
					values.put("mobjectIcon", json.getString("MobjectIcon"));
					values.put("itemType", json.getString("itemType"));
					if (json.getString("intent").equals("null") == false)
						values.put("intent", json.getString("intent"));
					else {
						String intent = null;
						values.put("intent", intent);
					}
					if (json.getString("contacts").equals("null") == false)
						values.put("contacts", json.getString("contacts"));
					else {
						String contacts = null;
						values.put("contacts", contacts);
					}
					cr.insert(CONTENT_URI_NO_NOTIFICATION, values);
				} else {
					String wall = json.getString("wall");
					String floor = json.getString("floor");
					String[] wall_arry = wall.split("-");
					String[] floor_arry = floor.split("-");
					SharedPreference.putSharedPreference(this,
							Integer.parseInt(wall_arry[0]) + "|w",
							Integer.parseInt(wall_arry[1]));
					SharedPreference.putSharedPreference(this,
							Integer.parseInt(floor_arry[0]) + "|f",
							Integer.parseInt(floor_arry[0]));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(downButton)) {
			insert_DB();
		} else if (v.equals(likeButton)) {
			count_Recommend++;
		}

	}
}