package mobi.intuitit.android.mate.launcher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MAvatarMenu extends LinearLayout implements OnClickListener {

	private int mVisibleState = INVISIBLE;

	ImageView imgCall;
	ImageView imgSMS;
	ImageView imgKakaoTalk;
	ImageView imgTwitter;
	ImageView imgFacebook;
	
	private Launcher mLauncher;
	String mContacts;

	EditText edtMsg;

	public MAvatarMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MAvatarMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void initMAvatarMenu(Launcher launcher, String contacts) {
		setVisible(mVisibleState);
		
		this.mLauncher = launcher;
		this.mContacts = contacts;
		
		imgCall = (ImageView) findViewById(R.id.imgCall);
		imgSMS = (ImageView) findViewById(R.id.imgSMS);
		imgKakaoTalk = (ImageView) findViewById(R.id.imgKakaoTalk);
		imgTwitter = (ImageView) findViewById(R.id.imgTwitter);
		imgFacebook = (ImageView) findViewById(R.id.imgFacebook);

		imgCall.setOnClickListener(this);
		imgSMS.setOnClickListener(this);
		imgKakaoTalk.setOnClickListener(this);
		imgTwitter.setOnClickListener(this);
		imgFacebook.setOnClickListener(this);
	}

	public int getVisible() {
		return mVisibleState;
	}

	public void setVisible(int mVisible) {
		mVisibleState = mVisible;
		this.setVisibility(mVisibleState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.e("RRR", "menu OnClick=" + v.toString());
		
		if(v.equals(imgCall)) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + mContacts));
			mLauncher.startActivity(intent);
		} else if (v.equals(imgSMS)) {
			
			Log.e("RRR", "imgSMS");
//			edtMsg = (EditText) findViewById(R.id.edtMsg);
			
			AlertDialog.Builder builder;
			AlertDialog dig;
			
			View layout = mLauncher.mInflater.inflate(R.layout.mavatar_input, (ViewGroup) findViewById(R.id.workspace), false);
			
			builder = new AlertDialog.Builder(mLauncher);
			builder.setView(layout);
			
			dig = builder.create();
			dig.setTitle("메시지 입력");
			
			edtMsg = (EditText) findViewById(R.id.edtAvatarMsg);
			
			dig.setButton("확인", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String msg = edtMsg.getText().toString();
					mLauncher.sendtoSMS(mContacts, msg);
				}
			});
			dig.show();
		} else if (v.equals(imgKakaoTalk)) {
			
		} else if (v.equals(imgTwitter)) {
			
		} else if (v.equals(imgFacebook)) {
			
		}
	}

}
