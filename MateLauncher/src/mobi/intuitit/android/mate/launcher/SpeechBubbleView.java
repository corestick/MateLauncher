package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechBubbleView extends LinearLayout implements
		View.OnClickListener {

	private Launcher mLauncher;
	private TextView mainview;
	private ImageView smsButton;
	private ImageView faceButton;
	private ImageView twiterButton;
	private ImageView kakaoButton;
	private ImageView callButton;

	private Button sendButton;
	private EditText edit;
	private EditText PhoneNum_edit;
	private Button OkButton;

	private Button b; // 전환용 임시버튼

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

	public void InputPhonenumView() {
		PhoneNum_edit = new EditText(mLauncher);
		OkButton = new Button(mLauncher);

		addView(PhoneNum_edit);
		addView(OkButton);

		LayoutParams param1 = (LayoutParams) PhoneNum_edit.getLayoutParams();
		param1.width = 200;
		param1.height = 70;
		PhoneNum_edit.setLayoutParams(param1);

		LayoutParams param2 = (LayoutParams) OkButton.getLayoutParams();
		param2.width = 100;
		param2.height = 70;
		OkButton.setLayoutParams(param2);

		PhoneNum_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
		OkButton.setText("확인");
	}

	public void CreateSelectView() {
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

		callButton.setImageResource(R.drawable.ico_tweet);
		kakaoButton.setImageResource(R.drawable.ico_tweet);
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
		} else if (v.equals(callButton)) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:01095485995"));
			mLauncher.startActivity(intent);

		} else if (v.equals(kakaoButton)) {
			KakaoLink kakao = new KakaoLink(mLauncher);
		}

	}

}
