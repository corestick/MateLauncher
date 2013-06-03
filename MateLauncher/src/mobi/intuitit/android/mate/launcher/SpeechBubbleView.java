package mobi.intuitit.android.mate.launcher;

import static android.util.Log.e;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.AttributeSet;
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

	public Launcher mLauncher;
	private TextView mainview;
	private ImageView smsButton;
	private ImageView faceButton;
	private ImageView twiterButton;
	private ImageView callButton;
	private ImageView kakaoButton;
	private Button sendButton;
	private EditText edit;

	private Button b; // 전환용 임시버튼
	private String Pre;
	private String Contacts; // 보낼전화번호

	private ListView listview;
	App_Adapter App_Adapter;
	Contact_Adapter contact_Adapter;
	ArrayList<appInfo> appInfoArry = new ArrayList<appInfo>();
	ArrayList<Contacts> contactlist = new ArrayList<Contacts>();
	Mobject Apptag = new Mobject();
	Mobject contactsTag = new Mobject();

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

	public void CreateSelectView(String contacts) {
		Contacts = contacts;
		Pre = null;
		callButton = new ImageView(mLauncher);
		smsButton = new ImageView(mLauncher);
		kakaoButton = new ImageView(mLauncher);
		faceButton = new ImageView(mLauncher);
		twiterButton = new ImageView(mLauncher);

		callButton.setOnClickListener(this);
		smsButton.setOnClickListener(this);
		faceButton.setOnClickListener(this);
		kakaoButton.setOnClickListener(this);

		addView(callButton);
		addView(smsButton);
		addView(kakaoButton);
		addView(faceButton);
		addView(twiterButton);

		LayoutParams param = (LayoutParams) callButton.getLayoutParams();
		param.width = 60;
		param.height = 60;

		LayoutParams param1 = (LayoutParams) kakaoButton.getLayoutParams();
		param1.width = 60;
		param1.height = 60;

		LayoutParams param2 = (LayoutParams) smsButton.getLayoutParams();
		param2.width = 60;
		param2.height = 60;

		LayoutParams param3 = (LayoutParams) faceButton.getLayoutParams();
		param3.width = 60;
		param3.height = 60;

		LayoutParams param4 = (LayoutParams) twiterButton.getLayoutParams();
		param4.width = 60;
		param4.height = 60;

		callButton.setLayoutParams(param);
		kakaoButton.setLayoutParams(param1);
		smsButton.setLayoutParams(param2);
		faceButton.setLayoutParams(param3);
		twiterButton.setLayoutParams(param4);

		callButton.setImageResource(R.drawable.ico_call);
		kakaoButton.setImageResource(R.drawable.btn_small_01_android);
		smsButton.setImageResource(R.drawable.ico_sms);
		faceButton.setImageResource(R.drawable.ico_facebook);
		twiterButton.setImageResource(R.drawable.ico_twitter);

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
		App_Adapter = new App_Adapter();
		listview.setAdapter(App_Adapter);
		loadApp();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentView, View view,

			int position, long id) {

				final ContentValues values = new ContentValues();
				final ContentResolver cr = mLauncher.getContentResolver();
				ItemInfo itemInfo = new ItemInfo();

				Intent intent = mLauncher.getPackageManager()
						.getLaunchIntentForPackage(
								appInfoArry.get(position).packagename);
				ComponentName component = new ComponentName(appInfoArry
						.get(position).packagename, intent.getComponent()
						.getClassName());
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
				Apptag.title = itemInfo.title;

				String titleStr = itemInfo.title.toString();
				values.put(LauncherSettings.BaseLauncherColumns.TITLE, titleStr);

				String uri = itemInfo.intent.toUri(0);
				values.put(LauncherSettings.BaseLauncherColumns.INTENT, uri);
				cr.update(
						LauncherSettings.Favorites.getContentUri(App_id, false),
						values, null, null);
				Toast.makeText(mLauncher, "앱매칭성공!!", Toast.LENGTH_SHORT).show();
				mLauncher.mSpeechBubbleview.setVisibility(View.GONE);
				// this.getPackageManager().getLaunchIntentForPackage(packageName);
				// startActivity(intent); 패키지 이름으로 실행시키는 로직
			}
		});
		return Apptag;

	}

	// 아바타 핸드폰 번호 매칭
	public Mobject InputPhonenumView(Object tag) {
		final long App_id = ((Mobject) tag).id;
		contactsTag = (Mobject) tag;

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
		contact_Adapter = new Contact_Adapter();
		listview.setAdapter(contact_Adapter);
		readContacts3();		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentView, View view,
					int position, long id) {
				final ContentValues values = new ContentValues();
				final ContentResolver cr = mLauncher.getContentResolver();
				String name = contactlist.get(position).Name;
				String Num = contactlist.get(position).PhoneNum;
				contactsTag.Contacts = Num;
				values.put(LauncherSettings.BaseLauncherColumns.CONTACTS, Num);
				cr.update(
						LauncherSettings.Favorites.getContentUri(App_id, false),
						values, null, null);
				Toast.makeText(mLauncher, "연락처매칭성공!!", Toast.LENGTH_SHORT)
						.show();
				mLauncher.mSpeechBubbleview.setVisibility(View.GONE);
			}
		});
		return contactsTag;
	}

	// 설치 앱 얻어오기
	public void loadApp() {
		Comparator<appInfo> myComparator = new Comparator<appInfo>() {
			Collator app_Collator = Collator.getInstance();

			@Override
			public int compare(appInfo a, appInfo b) {
				return app_Collator.compare(a.appName, b.appName);
			}
		};
		PackageManager pm = mLauncher.getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> apps = pm.queryIntentActivities(mainIntent, 0);
		final int count = apps.size();
		for (int i = 0; i < count; i++) {
			ResolveInfo info = apps.get(i);
			appInfo appinfo = new appInfo();
			appinfo.packagename = info.activityInfo.packageName;
			appinfo.appName = info.activityInfo.applicationInfo.loadLabel(pm)
					.toString();
			appinfo.appIcon = info.activityInfo.applicationInfo.loadIcon(pm);
			if (i == 0) {
				appInfoArry.add(appinfo);
			} else {
				for (int j = 0; j <= appInfoArry.size(); j++) {
					if ((appInfoArry.get(j).appName).equals(appinfo.appName) == true)
						break;
					else {
						if (j == appInfoArry.size() - 1) {
							appInfoArry.add(appinfo);
						}
					}
				}
			}
		}
		Collections.sort(appInfoArry, myComparator);
		App_Adapter.notifyDataSetChanged();
	}

	// 앱 정보 저장할 클래스
	class appInfo {
		public String packagename;
		public String appName;
		public Drawable appIcon;
	}

	class Contacts {
		public String Name;
		public String PhoneNum;
	}

	// 연락처 읽어오기
	public void readContacts3() {
		Comparator<Contacts> myComparator = new Comparator<Contacts>() {
			Collator app_Collator = Collator.getInstance();

			@Override
			public int compare(Contacts a, Contacts b) {
				return app_Collator.compare(a.Name, b.Name);
			}
		};
		ContentResolver cr = mLauncher.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				Contacts contact = new Contacts();				
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					contact.Name = name;
					// sb.append(name);
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
						String num = pCur.getString(numIndex);
						switch (pCur.getInt(typeIndex)) {
						case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
							contact.PhoneNum = num;
							// sb.append(", Mobile:" + num);
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
				if (contact.PhoneNum != null)
					contactlist.add(contact);
			}
		}
		Collections.sort(contactlist, myComparator);
		contact_Adapter.notifyDataSetChanged();
	}

	// 연락처 adapter
	public class Contact_Adapter extends BaseAdapter {

		TextView Name;
		TextView PhoneNum;
		LayoutInflater inflater;

		public Contact_Adapter() {
			inflater = (LayoutInflater) mLauncher
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.contact_list_layout,
						parent, false);
			}
			Name = (TextView) convertView.findViewById(R.id.contact_list_name);
			PhoneNum = (TextView) convertView
					.findViewById(R.id.contact_list_phonenum);

			Name.setText(contactlist.get(position).Name);
			PhoneNum.setText(contactlist.get(position).PhoneNum);
			return convertView;
		}

	}

	// 설치앱에서 커스텀 어뎁터
	public class App_Adapter extends BaseAdapter {

		ImageView image;
		TextView name;
		LayoutInflater inflater;

		public App_Adapter() {
			inflater = (LayoutInflater) mLauncher
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appInfoArry.size();
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
				convertView = inflater.inflate(R.layout.app_list_layout,
						parent, false);
			}
			image = (ImageView) convertView.findViewById(R.id.applist_image);
			name = (TextView) convertView.findViewById(R.id.applist_name);

			Drawable icon = Utilities.createIconThumbnail(
					appInfoArry.get(position).appIcon, getContext());
			image.setImageDrawable(icon);
			name.setText(appInfoArry.get(position).appName);
			return convertView;

		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(b)) {
			removeAllViews();
		} else if (v.equals(smsButton)) {
			Pre = "sms";
			removeAllViews();
			CreateSendView();
		} else if (v.equals(sendButton)) {
			String message = edit.getText().toString();
			if (message.length() != 0) {
				if (Pre.equals("sms")) {
					mLauncher.sendtoSMS(Contacts, message);
					removeAllViews();
					InputMethodManager imm = (InputMethodManager) mLauncher
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
				} else if (Pre.equals("kakkao")) {
					KakaoLink kakao = new KakaoLink(mLauncher);
					kakao.openKakaoLink(mLauncher, message,
							mLauncher.getPackageName(), "0.9.2", "UTF-8");
				}
				mLauncher.mSpeechBubbleview.setVisibility(View.GONE);
			} else {
				Toast.makeText(mLauncher, "문자를 입력하세요", Toast.LENGTH_SHORT)
						.show();
				removeAllViews();
				this.setVisibility(View.GONE);
			}
		} else if (v.equals(callButton)) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + Contacts));
			mLauncher.startActivity(intent);
			mLauncher.mSpeechBubbleview.setVisibility(View.GONE);
			return;
		} else if (v.equals(kakaoButton)) {
			Pre = "kakkao";
			removeAllViews();
			CreateSendView();
			return;
		}
	}

}
