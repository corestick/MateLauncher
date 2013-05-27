package mobi.intuitit.android.mate.launcher;

import static android.util.Log.e;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechBubbleView extends LinearLayout implements
		View.OnClickListener {

	private Launcher mLauncher;
	private TextView mainview;
	private ImageView smsButton;
	private ImageView faceButton;
	private ImageView twiterButton;
	private Button sendButton;
	private EditText edit;

	private Button b; // 전환용 임시버튼

	private ListView listview;
	ArrayAdapter<String> itemAdapter;
	ArrayList<String> setupAppName = new ArrayList<String>();
	ArrayList<String> setupAppPacName = new ArrayList<String>();
	ArrayList<Drawable> setupAppIcon = new ArrayList<Drawable>();

	ArrayList<String> contactlist = new ArrayList<String>();
	Mobject Apptag;

	public SpeechBubbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setLauncher(Launcher launcher) {
		mLauncher = launcher;

	}

	public TextView getMainView() {
		return mainview;
	}

	public void CreateMainView() {
		mainview = new TextView(mLauncher);
		b = new Button(mLauncher);
		b.setText("임시");
		mainview.setText("말풍선");
		mainview.setHeight(100);
		mainview.setTextColor(Color.RED);
		mainview.setBackgroundColor(Color.LTGRAY);
		addView(mainview, 200, 100);
		addView(b, 100, 56);
		b.setOnClickListener(this);
	}

	public void CreateSelectView() {
		smsButton = new ImageView(mLauncher);
		faceButton = new ImageView(mLauncher);
		twiterButton = new ImageView(mLauncher);

		smsButton.setOnClickListener(this);
		faceButton.setOnClickListener(this);

		addView(smsButton);
		addView(faceButton);
		addView(twiterButton);

		LayoutParams param2 = (LayoutParams) smsButton.getLayoutParams();
		param2.width = 60;
		param2.height = 60;

		LayoutParams param3 = (LayoutParams) faceButton.getLayoutParams();
		param3.width = 60;
		param3.height = 60;

		LayoutParams param4 = (LayoutParams) twiterButton.getLayoutParams();
		param4.width = 60;
		param4.height = 60;

		smsButton.setLayoutParams(param2);
		faceButton.setLayoutParams(param3);
		twiterButton.setLayoutParams(param4);

		smsButton.setImageResource(R.drawable.ico_sms);
		faceButton.setImageResource(R.drawable.ico_facebook);
		twiterButton.setImageResource(R.drawable.ico_tweet);

	}

	public void CreateSendView() {
		edit = new EditText(mLauncher);
		sendButton = new Button(mLauncher);

		sendButton.setOnClickListener(this);
		addView(edit);
		addView(sendButton);

		LayoutParams param1 = (LayoutParams) edit.getLayoutParams();
		param1.width = 200;
		param1.height = 110;
		edit.setLayoutParams(param1);

		LayoutParams param2 = (LayoutParams) sendButton.getLayoutParams();
		param2.width = 100;
		param2.height = 70;
		sendButton.setLayoutParams(param2);

		sendButton.setText("전송");

	}

	public void setLocation(int left, int top, int right, int bottom) {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		params.gravity = Gravity.TOP;
		params.setMargins(left, top, right, bottom);
		this.setLayoutParams(params);
	}

	// 설치된 앱 목록 알아오기
	public Mobject selectApp(Object tag) {		
		final long App_id = ((Mobject) tag).id;
		Apptag = (Mobject) tag;
		
		LinearLayout linear = (LinearLayout) findViewById(R.id.speechbubbleview);
		linear.getLayoutParams().height = LayoutParams.FILL_PARENT;
		linear.getLayoutParams().width = LayoutParams.FILL_PARENT;
		linear.requestLayout();

		listview = new ListView(mLauncher);
		linear.addView(listview);

		LayoutParams param = (LayoutParams) listview.getLayoutParams();
		param.width = LayoutParams.FILL_PARENT;
		param.height = LayoutParams.FILL_PARENT;
		listview.setLayoutParams(param);
		listview.setBackgroundColor(Color.DKGRAY);
		MyAdapter myadapter = new MyAdapter();
		listview.setAdapter(myadapter);
		loadApp();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentView, View view,
					int position, long id) {
				Toast.makeText(mLauncher, setupAppPacName.get(position), 30)
						.show();
				final ContentValues values = new ContentValues();
				final ContentResolver cr = mLauncher.getContentResolver();
				ItemInfo itemInfo = new ItemInfo();

				Intent intent = mLauncher.getPackageManager()
						.getLaunchIntentForPackage(
								setupAppPacName.get(position));
				Log.e("app-intent", intent.toString());
				ComponentName component = new ComponentName(setupAppPacName
						.get(position), intent.getComponent().getClassName());
				Log.e("app-componet", component.toString());
				PackageManager packageManager = mLauncher.getPackageManager();
				ActivityInfo activityInfo = null;
				try {
					activityInfo = packageManager
							.getActivityInfo(component, 0 /*
														 * no flags
														 */);
				} catch (NameNotFoundException e) {
					e("matching-app",
							"Couldn't find ActivityInfo for selected application",
							e);
				}

				if (activityInfo != null) {
					itemInfo.title = activityInfo.loadLabel(packageManager);
					if (itemInfo.title == null) {
						itemInfo.title = activityInfo.name;
					}

					itemInfo.setActivity(component,
							Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					itemInfo.container = ItemInfo.NO_ID;
				}
				Apptag.intent = itemInfo.intent;
				Apptag.title =  itemInfo.title;
				
				String titleStr = itemInfo.title.toString();
				values.put(LauncherSettings.BaseLauncherColumns.TITLE, titleStr);

				String uri = itemInfo.intent.toUri(0);
				values.put(LauncherSettings.BaseLauncherColumns.INTENT, uri);
				cr.update(
						LauncherSettings.Favorites.getContentUri(App_id, false),
						values, null, null);

				// this.getPackageManager().getLaunchIntentForPackage(packageName);
				// startActivity(intent); 패키지 이름으로 실행시키는 로직
			}
		});
		return Apptag;		

	}

	// 아바타 핸드폰 번호 매칭
	public void InputPhonenumView() {

		LinearLayout linear = (LinearLayout) findViewById(R.id.speechbubbleview);
		linear.getLayoutParams().height = LayoutParams.FILL_PARENT;
		linear.getLayoutParams().width = LayoutParams.FILL_PARENT;
		linear.requestLayout();

		listview = new ListView(mLauncher);
		linear.addView(listview);

		LayoutParams param = (LayoutParams) listview.getLayoutParams();
		param.width = LayoutParams.FILL_PARENT;
		param.height = LayoutParams.FILL_PARENT;
		listview.setLayoutParams(param);
		listview.setBackgroundColor(Color.DKGRAY);
		contactlist.clear();
		readContacts3();
		itemAdapter = new ArrayAdapter<String>(mLauncher,
				android.R.layout.simple_list_item_1, contactlist);
		listview.setAdapter(itemAdapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentView, View view,
					int position, long id) {
				String name = contactlist.get(position);
				int num = name.indexOf(':');
				String str = name.substring(num + 1, name.length());
				Toast.makeText(mLauncher, str, 30).show();
			}
		});
	}

	// 설치 앱 얻어오기
	public void loadApp() {
		PackageManager pm = mLauncher.getPackageManager();
		List appinfo = mLauncher.getPackageManager().getInstalledPackages(
				PackageManager.GET_INSTRUMENTATION
						| PackageManager.GET_UNINSTALLED_PACKAGES);
		for (int i = 0; i < appinfo.size(); i++) {
			PackageInfo pi = (PackageInfo) appinfo.get(i);
			String pacname = pi.packageName;
			String appname = pi.applicationInfo.loadLabel(pm).toString();
			Drawable drawble = pi.applicationInfo.loadIcon(pm);
			setupAppIcon.add(drawble);
			setupAppName.add(appname);
			setupAppPacName.add(pacname);

			// Log.e("image", drawble.toString());
		}
	}

	// 연락처 읽어오기
	void readContacts3() {
		ContentResolver cr = mLauncher.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				StringBuilder sb = new StringBuilder();
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					sb.append(name);
					// get the phone number
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					int typeIndex = pCur
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int numIndex = pCur
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					while (pCur.moveToNext()) {
						String phone = null;
						String num = pCur.getString(numIndex);
						switch (pCur.getInt(typeIndex)) {
						case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
							sb.append(", Mobile:" + num);
							break;
						// case
						// ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
						// sb.append(", Home:" + num);
						// break;
						// case
						// ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
						// sb.append(", Work:" + num);
						// break;
						}
					}
					pCur.close();
				}
				contactlist.add(sb.toString());
			}
		}
	}

	// 설치앱에서 커스텀 어뎁터
	public class MyAdapter extends BaseAdapter {

		ImageView image;
		TextView name;
		LayoutInflater inflater;

		public MyAdapter() {
			// TODO Auto-generated constructor stub
			inflater = (LayoutInflater) mLauncher
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return setupAppIcon.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_layout, parent,
						false);
			}
			image = (ImageView) convertView.findViewById(R.id.applist_image);
			name = (TextView) convertView.findViewById(R.id.applist_name);

			Log.e("name", setupAppName.get(position));
			Log.e("image", setupAppIcon.get(position).toString());
			Drawable icon = Utilities.createIconThumbnail(
					setupAppIcon.get(position), getContext());
			image.setImageDrawable(icon);
			name.setText(setupAppName.get(position));
			return convertView;

		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(b)) {
			removeAllViews();
			CreateSelectView();
		} else if (v.equals(smsButton)) {
			removeAllViews();
			CreateSendView();
		} else if (v.equals(sendButton)) {
			String message = edit.getText().toString();
			if (message.length() != 0) {
				mLauncher.sendtoSMS("5556", message);
				removeAllViews();
				CreateSelectView();
				InputMethodManager imm = (InputMethodManager) mLauncher
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

				this.setVisibility(View.GONE);
			} else {
				Toast.makeText(mLauncher, "문자를 입력하세요", Toast.LENGTH_SHORT)
						.show();
				removeAllViews();
				CreateSelectView();
				this.setVisibility(View.GONE);
			}
		}

	}

}
