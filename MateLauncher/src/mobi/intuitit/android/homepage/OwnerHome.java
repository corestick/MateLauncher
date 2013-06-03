package mobi.intuitit.android.homepage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobi.intuitit.android.mate.launcher.LauncherProvider;
import mobi.intuitit.android.mate.launcher.R;
import mobi.intuitit.android.mate.launcher.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
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
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

//import org.omg.CORBA.portable.CustomValue;

public class OwnerHome extends Activity implements OnScrollListener,
		OnClickListener {

	final String serverUrl = "http://kimsunghyuntest2.appspot.com/simpleservletapp";

	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath();

	// private static final int TEXT_DIALOG = 0;

	private Uri mImageCaptureUri;
	private ImageView btnProfile;
	private TextView profileMsg;
	private EditText editText;
	private String data;
	private Gallery gallery; // 런처 이미지 보여주기

	private ScrollAdapter mAdapter;
	private ListView mListView;
	private LayoutInflater mInflater;
	private ArrayList<String> mRowList;
	private boolean mLockListView;
	public int msgCnt = 20;

	private TextView tv_Recommend;
	private TextView tv_Download;
	private TextView tv_Visit;

	public int count_Recommend = 0;
	public int count_Download = 0;
	public int count_Visit = 0;

	public Button uploadButton;
	public Button likeButton;

	public String phoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_owner_home);
		likeButton = (Button) findViewById(R.id.owner_like_it);
		uploadButton = (Button) findViewById(R.id.owner_download);
		likeButton.setOnClickListener(this);
		uploadButton.setOnClickListener(this);

		// mRowList = new ArrayList<String>();
		// mLockListView = true;

		// mAdapter = new ScrollAdapter(this, R.layout.row, mRowList);
		// mListView = (ListView) findViewById(R.id.owner_listView);
		//
		// mInflater = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		 mListView.addFooterView(mInflater.inflate(R.layout.footer, null));
		//
		// mListView.setOnScrollListener((OnScrollListener) this);
		// mListView.setAdapter(mAdapter);

		btnProfile = (ImageView) findViewById(R.id.owner_profile);
		// btnProfile.setImageURI(Uri.fromFile(new File(sdcard+"screen1.jpg")));
		profileMsg = (TextView) findViewById(R.id.owner_state);

		// 추천, 다운, 방문 텍스트뷰
		tv_Recommend = (TextView) findViewById(R.id.owner_recommend);
		tv_Download = (TextView) findViewById(R.id.owner_down);
		tv_Visit = (TextView) findViewById(R.id.owner_visit);

		// 추천, 다운, 방문 셋
		tv_Recommend = setRecommend(count_Recommend);
		tv_Download = setDownload(count_Download);
		tv_Visit = setVisit(count_Visit);

		// 런처 홈스크린 보여주는 부분
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		gallery.setAdapter(new OwnerImageAdapter(this));

		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				CustomDialog dialog = new CustomDialog(OwnerHome.this, position);
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
		//
		// // Guest 홈화면으로 가기 위한 임시
		// btnDown.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// get_DB();
		// remove_DB();
		// insert_DB();
		// }
		// });

		// profileMsg.setOnClickListener(new View.OnClickListener() {
		// @SuppressWarnings("deprecation")
		// public void onClick(View v) {
		// Context mContext = OwnerHome.this;
		// AlertDialog.Builder builder;
		// AlertDialog dialog;
		// LayoutInflater inflater = (LayoutInflater) mContext
		// .getSystemService(LAYOUT_INFLATER_SERVICE);
		// View layout = inflater.inflate(R.layout.profilemsg,
		// (ViewGroup) findViewById(R.id.setTextDialog));
		//
		// editText = (EditText) layout.findViewById(R.id.editProfile);
		//
		// builder = new AlertDialog.Builder(mContext);
		// builder.setView(layout);
		// dialog = builder.create();
		// dialog.setTitle("프로필 변경");
		//
		// dialog.setButton("확인", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(final DialogInterface dialog,
		// final int which) {
		// // TODO Auto-generated method stub
		// data = editText.getText().toString();
		// profileMsg.setText(data);
		// dialog.dismiss();
		// }
		// });
		// dialog.setButton2("취소", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(final DialogInterface dialog,
		// final int which) {
		// dialog.dismiss();
		// }
		// });
		// dialog.show();
		// }
		// });

		btnProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doTakePhotoAction();
					}
				};

				DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doTakeAlbumAction();
					}
				};

				DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};

				new AlertDialog.Builder(OwnerHome.this).setTitle("업로드할 이미지 선택")
						.setPositiveButton("사진촬영", cameraListener)
						.setNeutralButton("앨범선택", albumListener)
						.setNegativeButton("취소", cancelListener).show();

			}
		});

	}	

	// 추천, 다운, 방문 수 셋
	private TextView setRecommend(int n) {
		tv_Recommend.setText("" + n);
		return tv_Recommend;
	}

	private TextView setDownload(int n) {
		tv_Download.setText("" + n);
		return tv_Download;
	}

	private TextView setVisit(int n) {
		tv_Visit.setText("" + n);
		return tv_Visit;
	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);

		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	private void doTakeAlbumAction() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA: {

			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			if (thumbnail != null) {
				ImageView profile = (ImageView) findViewById(R.id.owner_profile);
				profile.setImageBitmap(thumbnail);
			}
			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			mImageCaptureUri = data.getData();
		}

		case PICK_FROM_CAMERA: {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 90);
			intent.putExtra("outputY", 90);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			startActivityForResult(intent, CROP_FROM_CAMERA);

			break;
		}
		}
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
				addItems(5);
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

	public class CustomDialog extends Dialog implements
			android.view.View.OnClickListener {
		ImageView iv;

		public CustomDialog(Context context, int position) {
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

	// DB 삭제와 화면지우기
	public void remove_DB() {
		LauncherProvider lp = new LauncherProvider();
		lp.delete_table();
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

	public void insert_DB() {
		String AUTHORITY = "mobi.intuitit.android.mate.launcher.settings";
		String TABLE_FAVORITES = "favorites";
		String PARAMETER_NOTIFY = "notify";
		final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://"
				+ AUTHORITY + "/" + TABLE_FAVORITES + "?" + PARAMETER_NOTIFY
				+ "=false");
		final ContentValues values = new ContentValues();
		final ContentResolver cr = getContentResolver();

		remove_DB(); // DB 지우고, 화면 View 삭제

		String str = JSONfunctions.getJSONfromURL(serverUrl,"+821027480952");
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
					if (json.getString("intent").equals("null")==false)
						values.put("intent", json.getString("intent"));
					else {
						String intent = null;
						values.put("intent", intent);
					}
					if (json.getString("contacts").equals("null")==false)
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

	public void get_DB() {
		//핸드폰 번호 읽어오기
		TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		phoneNum = telManager.getLine1Number();
		
		Log.e("PhoneNum", phoneNum);
		
		ContentResolver contentResolver = getContentResolver();
		String AUTHORITY = "mobi.intuitit.android.mate.launcher.settings";
		String TABLE_FAVORITES = "favorites";
		String PARAMETER_NOTIFY = "notify";

		Uri uri = Uri.parse("content://" + AUTHORITY + "/" + TABLE_FAVORITES
				+ "?" + PARAMETER_NOTIFY + "=true");
		final Cursor c = contentResolver.query(uri, null, null, null, null);

		final int intentIndex = c.getColumnIndexOrThrow("intent");
		final int containerIndex = c.getColumnIndexOrThrow("container");
		final int itemTypeIndex = c.getColumnIndexOrThrow("itemType");
		final int screenIndex = c.getColumnIndexOrThrow("screen");
		final int cellXIndex = c.getColumnIndexOrThrow("cellX");
		final int cellYIndex = c.getColumnIndexOrThrow("cellY");
		final int mobjectType = c.getColumnIndexOrThrow("mobjectType");
		final int mobjectIcon = c.getColumnIndex("mobjectIcon");
		final int contactsIndex = c.getColumnIndex("contacts");

		while (c.moveToNext()) {

			String intentDescription = c.getString(intentIndex);
			int container = c.getInt(containerIndex);
			int itemType = c.getInt(itemTypeIndex);
			int screen = c.getInt(screenIndex);
			int cellX = c.getInt(cellXIndex);
			int cellY = c.getInt(cellYIndex);
			int MobjectType = c.getInt(mobjectType);
			int MobjectIcon = c.getInt(mobjectIcon);
			String contacts = c.getString(contactsIndex);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", phoneNum);
			if (intentDescription == null)
				map.put("intent", "null");
			else
				map.put("intent", intentDescription);
			if (contacts == null)
				map.put("contacts", "null");
			else
				map.put("contacts", contacts);
			map.put("container", container);
			map.put("itemType", itemType);
			map.put("screen", screen);
			map.put("cellX", cellX);
			map.put("cellY", cellY);
			map.put("MobjectType", MobjectType);
			map.put("MobjectIcon", MobjectIcon);

			map.put("wall", "null");

			JSONfunctions.postSONfromURL(serverUrl, map);
		}
		c.close();

		for (int i = 0; i < HomeMain.ChildCount; i++) {
			int wIdx = SharedPreference.getIntSharedPreference(this, i + "|w");
			int fIdx = SharedPreference.getIntSharedPreference(this, i + "|f");
			Log.e("Sh-Wall", String.valueOf(wIdx));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", phoneNum);
			map.put("wall", i + "-" + wIdx);
			map.put("floor", i + "-" + fIdx);
			JSONfunctions.postSONfromURL(serverUrl, map);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(uploadButton)) {
			Log.e("upload", "upload");
			get_DB();
		} else if (v.equals(likeButton)) {
			Log.e("download", "download");
			insert_DB();
		}
	}

}