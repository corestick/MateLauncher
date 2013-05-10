package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

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

	public SpeechBubbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setLauncher(Launcher launcher) {
		mLauncher = launcher;

	}
	
	public TextView getMainView(){
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
		param2.height = 50;
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
			if(message.length() !=0){
				mLauncher.sendtoSMS("01095485995", message);
				removeAllViews();
				CreateSelectView();
				this.setVisibility(View.GONE);
			}
			else{
				Toast.makeText(mLauncher, "문자를 입력하세요", Toast.LENGTH_SHORT).show();
				removeAllViews();
				CreateSelectView();
				this.setVisibility(View.GONE);
			}
		}

	}
	

}
